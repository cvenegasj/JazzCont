/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.jazzcontadores.extra.JCConstants;
import com.jazzcontadores.model.dao.CompradorDAO;
import com.jazzcontadores.model.dao.EmpresaClienteDAO;
import com.jazzcontadores.model.dao.LibroRegistroVentasDAO;
import com.jazzcontadores.model.dao.TipoComprobantePagoODocumentoDAO;
import com.jazzcontadores.model.dao.TipoDocumentoIdentidadDAO;
import com.jazzcontadores.model.entities.Comprador;
import com.jazzcontadores.model.entities.DetalleComprobanteVenta;
import com.jazzcontadores.model.entities.DetalleLibroRegistroVentas;
import com.jazzcontadores.model.entities.EmpresaCliente;
import com.jazzcontadores.model.entities.LibroRegistroVentas;
import com.jazzcontadores.model.entities.ProductoVentas;
import com.jazzcontadores.model.entities.TipoComprobantePagoODocumento;
import com.jazzcontadores.model.entities.TipoDocumentoIdentidad;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Venegas
 */
public class DetalleLibroRegistroVentasAction extends ActionSupport {

    private long ruc;
    private boolean nuevoComprador;
    private EmpresaCliente empresaCliente;
    private DetalleLibroRegistroVentas detalleLRV;
    private List<TipoComprobantePagoODocumento> tiposComprobantes = new ArrayList<TipoComprobantePagoODocumento>();
    private List<TipoDocumentoIdentidad> tiposDocumentos = new ArrayList<TipoDocumentoIdentidad>();

    public String add() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();
        TipoComprobantePagoODocumentoDAO tDAO = factory.getTipoComprobantePagoODocumentoDAO();
        TipoDocumentoIdentidadDAO docDAO = factory.getTipoDocumentoIdentidadDAO();

        EmpresaCliente e = empresaDAO.findByRuc(ruc);

        if (e != null) {
            this.setEmpresaCliente(e);
        } else {
            return ERROR;
        }

        // llenar la lista de tipos de comprobantes en la vista        
        this.setTiposComprobantes(tDAO.findAll());
        // llenar la lista de tipo de documentos en la vista
        this.setTiposDocumentos(docDAO.findAll());

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "add";
    }

    public String save() throws Exception {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        LibroRegistroVentasDAO libroVentasDAO = factory.getLibroRegistroVentasDAO();
        CompradorDAO compradorDAO = factory.getCompradorDAO();

        // buscamos libro de registro de ventas existente, sino creamos uno.
        Calendar cPeriodo = new GregorianCalendar();
        cPeriodo.setTime(this.getDetalleLRV().getComprobanteVenta().getFechaEmision());
        // para los periodos de los libros, el día es 1 siempre
        cPeriodo.set(Calendar.DAY_OF_MONTH, 1);

        LibroRegistroVentas libroExistente = libroVentasDAO.findByPeriodo(this.getEmpresaCliente().getRuc(), cPeriodo.getTime());

        try {
            if (libroExistente == null) {
                Calendar cFechaFin = (Calendar) cPeriodo.clone();
                cFechaFin.set(Calendar.DAY_OF_MONTH, cFechaFin.getActualMaximum(Calendar.DAY_OF_MONTH));

                LibroRegistroVentas libroRVNuevo = new LibroRegistroVentas();
                libroRVNuevo.setPeriodo(cPeriodo.getTime());
                libroRVNuevo.setFechaInicio(cPeriodo.getTime());
                libroRVNuevo.setFechaFin(cFechaFin.getTime());
                libroRVNuevo.setEstaCerrado(false);
                libroRVNuevo.setEmpresaCliente(this.getEmpresaCliente());

                libroVentasDAO.makePersistent(libroRVNuevo);

                if (this.isNuevoComprador()) {
                    compradorDAO.makePersistent(this.getDetalleLRV().getComprobanteVenta().getComprador());
                }

                this.getDetalleLRV().setLibroRegistroVentas(libroRVNuevo); // no se puede obviar
                this.getDetalleLRV().setFechaHoraRegistro(new Date());
                this.getDetalleLRV().setNumeroCorrelativo(1); // libro nuevo, primer detalle

                // para calcular el total
                BigDecimal total = new BigDecimal("0.00");
                total.setScale(2, RoundingMode.HALF_EVEN);

                for (Iterator<DetalleComprobanteVenta> it = this.getDetalleLRV().getComprobanteVenta().getDetallesComprobanteVenta().iterator(); it.hasNext();) {
                    DetalleComprobanteVenta d = it.next();

                    if (d.getProductoVentas().getIdProductoVentas() == 0) {
                        // registrar nuevo producto
                        ProductoVentas pv = new ProductoVentas();
                        pv.setNombre(d.getProductoVentas().getNombre());
                        pv.setPrecio(d.getProductoVentas().getPrecio());
                        d.setProductoVentas(pv);
                    }
                    BigDecimal subtotal = d.getProductoVentas().getPrecio().multiply(new BigDecimal(d.getCantidad()));
                    d.setSubtotal(subtotal);
                    d.setComprobanteVenta(this.getDetalleLRV().getComprobanteVenta());
                    d.setPrecioUnitario(d.getProductoVentas().getPrecio()); // se copia el precio
                    total = total.add(d.getSubtotal());
                }

                this.getDetalleLRV().getComprobanteVenta().setImporteTotal(total);
                this.getDetalleLRV().getComprobanteVenta().setBase(total.multiply(JCConstants.BASE));
                this.getDetalleLRV().getComprobanteVenta().setIgv(total.multiply(JCConstants.IGV));
                libroRVNuevo.getDetallesLibroRegistroVentas().add(this.getDetalleLRV());

            } else {
                if (libroExistente.isEstaCerrado()) {
                    return "libroCerrado";
                }

                // establecemos el comprador
                if (this.isNuevoComprador()) {
                    compradorDAO.makePersistent(this.getDetalleLRV().getComprobanteVenta().getComprador());
                }

                this.getDetalleLRV().setLibroRegistroVentas(libroExistente); // no se puede obviar
                this.getDetalleLRV().setFechaHoraRegistro(new Date());

                //para calcular el total
                BigDecimal total = new BigDecimal("0.00");
                total.setScale(2, RoundingMode.HALF_EVEN);

                for (Iterator<DetalleComprobanteVenta> it = this.getDetalleLRV().getComprobanteVenta().getDetallesComprobanteVenta().iterator(); it.hasNext();) {
                    DetalleComprobanteVenta d = it.next();

                    if (d.getProductoVentas().getIdProductoVentas() == 0) {
                        // registrar nuevo producto
                        ProductoVentas pv = new ProductoVentas();
                        pv.setNombre(d.getProductoVentas().getNombre());
                        pv.setPrecio(d.getProductoVentas().getPrecio());
                        d.setProductoVentas(pv);
                    }
                    BigDecimal subtotal = d.getProductoVentas().getPrecio().multiply(new BigDecimal(d.getCantidad()));
                    d.setSubtotal(subtotal);
                    d.setComprobanteVenta(this.getDetalleLRV().getComprobanteVenta());
                    d.setPrecioUnitario(d.getProductoVentas().getPrecio()); // se copia el precio
                    total = total.add(d.getSubtotal());
                }

                this.getDetalleLRV().getComprobanteVenta().setImporteTotal(total);
                this.getDetalleLRV().getComprobanteVenta().setBase(total.multiply(JCConstants.BASE));
                this.getDetalleLRV().getComprobanteVenta().setIgv(total.multiply(JCConstants.IGV));

                // reordenamos la lista de detalles
                Date ultimaFechaRegistrada = libroExistente.getDetallesLibroRegistroVentas()
                        .get(libroExistente.getDetallesLibroRegistroVentas().size() - 1)
                        .getComprobanteVenta().getFechaEmision();
                // si la fecha de emision del comprobante es anterior a la fecha del último detalle ordenado
                if (this.getDetalleLRV().getComprobanteVenta().getFechaEmision().before(ultimaFechaRegistrada)) {
                    this.getDetalleLRV().setNumeroCorrelativo(1000000);// se pone al último de la lista
                    libroExistente.getDetallesLibroRegistroVentas().add(this.getDetalleLRV());
                    // reordenamos la lista
                    Collections.sort(libroExistente.getDetallesLibroRegistroVentas(), new Comparator<DetalleLibroRegistroVentas>() {
                        @Override
                        public int compare(DetalleLibroRegistroVentas d1, DetalleLibroRegistroVentas d2) {
                            return d1.getComprobanteVenta().getFechaEmision().compareTo(d2.getComprobanteVenta().getFechaEmision());
                        }
                    });
                    // establecemos el numero correlativo
                    for (int i = 0; i < libroExistente.getDetallesLibroRegistroVentas().size(); i++) {
                        // se reordena la lista de detalles
                        libroExistente.getDetallesLibroRegistroVentas().get(i).setNumeroCorrelativo(i + 1);
                    }
                } else {
                    // nos aseguramos que el tamaño sea mayor que 0 para no generar una excepcion
                    if (libroExistente.getDetallesLibroRegistroVentas().size() > 0) {
                        // le asignamos el último número correlativo más 1
                        DetalleLibroRegistroVentas d = libroExistente.getDetallesLibroRegistroVentas().get(libroExistente.getDetallesLibroRegistroVentas().size() - 1);
                        int ultimoNCorrelativo = d.getNumeroCorrelativo();
                        this.getDetalleLRV().setNumeroCorrelativo(ultimoNCorrelativo + 1);
                    } else {
                        this.getDetalleLRV().setNumeroCorrelativo(1);
                    }

                    libroExistente.getDetallesLibroRegistroVentas().add(this.getDetalleLRV());
                }

            }

            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();

        } catch (Exception e) {
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            System.err.println(e + ": " + e.getMessage());
            throw e;
            //return ERROR;

        }

        return "save";
    }

    public void validateSave() {
        // se inyecta el cliente antes de hacer las validaciones
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente e = empresaDAO.findByRuc(ruc);
        this.setEmpresaCliente(e);
        // llenar la lista de tipos de comprobantes en la vista        
        this.setTiposComprobantes(factory.getTipoComprobantePagoODocumentoDAO().findAll());
        // llenar la lista de tipo de documentos en la vista
        this.setTiposDocumentos(factory.getTipoDocumentoIdentidadDAO().findAll());

        // validaciones
        if (getDetalleLRV().getComprobanteVenta().getDetallesComprobanteVenta().isEmpty()) {
            addActionError("Debe especificar las líneas del comprobante.");
        }
        if (getDetalleLRV().getComprobanteVenta().getFechaEmision() == null) {
            addActionError("Debe especificar la fecha de emisión del comprobante.");
        }
        if (getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() < 0) {
            addActionError("Debe especificar el tipo de comprobante (Tabla 10).");
        }
        if (!getDetalleLRV().getComprobanteVenta().getSerie().trim().matches("-|\\w{1,20}")) {
            addActionError("El formato de la serie del comprobante es incorrecto.");
        }
        if (!getDetalleLRV().getComprobanteVenta().getNumero().trim().equals("")
                && !getDetalleLRV().getComprobanteVenta().getNumero().trim().matches("^\\d{1,20}")) {
            addActionError("El formato del número de comprobante es incorrecto.");
        }

        // verificar existencia de comprador
        if (getDetalleLRV().getComprobanteVenta().getComprador().getTipoDocumentoIdentidad().getNumero() != null
                && getDetalleLRV().getComprobanteVenta().getComprador().getNumeroDocumentoIdentidad() != null) {
            Comprador comprador = factory.getCompradorDAO().findByTipoDocumentoYNumeroDocumento(
                    this.getEmpresaCliente().getRuc(),
                    this.getDetalleLRV().getComprobanteVenta().getComprador().getTipoDocumentoIdentidad().getNumero(),
                    this.getDetalleLRV().getComprobanteVenta().getComprador().getNumeroDocumentoIdentidad());
            if (this.isNuevoComprador()) {
                if (comprador != null) {
                    addActionError("El comprador a registrar ya existe en la base de datos.");
                }
            } else {
                getDetalleLRV().getComprobanteVenta().setComprador(comprador); // se inyecta el comprador
            }
        }

        // =========================================================
        // reglas del formato de libros electronicos *********************
        // fecha de emisión
        if (getDetalleLRV().getComprobanteVenta().getFechaEmision() != null) {
            if (getDetalleLRV().getComprobanteVenta().getFechaEmision().after(new Date())) {
                addActionError("La fecha de emisión es incorrecta. No se puede registrar fechas futuras en el sistema.");
            } else {
                // 1. Obligatorio, excepto cuando el campo 27 = '2'
                // 2. Menor o igual al periodo informado
                // 3. Menor o igual al periodo señalado en el campo 1.
                getDetalleLRV().setEstadoOportunidadDeAnotacion("1");
            }
        }

        // serie 
        if (getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() != null
                && getDetalleLRV().getComprobanteVenta().getNumero() != null) {
            if (getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 1
                    || getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 3
                    || getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 4
                    || getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 7
                    || getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 8) {
                if (!getDetalleLRV().getComprobanteVenta().getSerie().matches("-|\\w{1,4}")) {
                    addActionError("El número de serie para el tipo de comprobante seleccionado debe tener hasta 4 dígitos.");
                }
            }
            if (getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 0) {
                if (!getDetalleLRV().getComprobanteVenta().getSerie().matches("-")) {
                    addActionError("El número de serie para el tipo de comprobante seleccionado debe ser '-'.");
                }
            }
        }

        // valor facturado de la exportación        
        if (getDetalleLRV().getValorFacturadoExportacion() != null) {
            // 3. Acepta negativos sólo si campo 5 = '07' o '87' 0 '97'
            if (getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 7
                    || getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 87
                    || getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 97) {
                if (getDetalleLRV().getValorFacturadoExportacion().precision() > 14
                        || getDetalleLRV().getValorFacturadoExportacion().scale() > 2) {
                    addActionError("El formato del valor facturado de la exportación es incorrecto.");
                }
            } else {
                if (getDetalleLRV().getValorFacturadoExportacion().compareTo(BigDecimal.ZERO) < 0
                        || getDetalleLRV().getValorFacturadoExportacion().precision() > 14
                        || getDetalleLRV().getValorFacturadoExportacion().scale() > 2) {
                    addActionError("El formato del valor facturado de la exportación es incorrecto.");
                }
            }
        } else {
            // de no existir registrar '0.00'
            getDetalleLRV().setValorFacturadoExportacion(new BigDecimal("0.00"));
        }

        // importe total de la operación exonerada
        if (getDetalleLRV().getTotalOperacionExonerada() != null) {
            // 3. Acepta negativos sólo si campo 5 = '07' o '87' 0 '97'
            if (getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 7
                    || getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 87
                    || getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 97) {
                if (getDetalleLRV().getTotalOperacionExonerada().precision() > 14
                        || getDetalleLRV().getTotalOperacionExonerada().scale() > 2) {
                    addActionError("El formato del importe total de la operación exonerada es incorrecto.");
                }
            } else {
                if (getDetalleLRV().getTotalOperacionExonerada().compareTo(BigDecimal.ZERO) < 0
                        || getDetalleLRV().getTotalOperacionExonerada().precision() > 14
                        || getDetalleLRV().getTotalOperacionExonerada().scale() > 2) {
                    addActionError("El formato del importe total de la operación exonerada es incorrecto.");
                }
            }
        } else {
            // de no existir registrar '0.00'
            getDetalleLRV().setTotalOperacionExonerada(new BigDecimal("0.00"));
        }

        // importe total de la operación inafecta
        if (getDetalleLRV().getTotalOperacionInafecta() != null) {
            // 3. Acepta negativos sólo si campo 5 = '07' o '87' 0 '97'
            if (getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 7
                    || getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 87
                    || getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 97) {
                if (getDetalleLRV().getTotalOperacionInafecta().precision() > 14
                        || getDetalleLRV().getTotalOperacionInafecta().scale() > 2) {
                    addActionError("El formato del importe total de la operación inafecta es incorrecto.");
                }
            } else {
                if (getDetalleLRV().getTotalOperacionInafecta().compareTo(BigDecimal.ZERO) < 0
                        || getDetalleLRV().getTotalOperacionInafecta().precision() > 14
                        || getDetalleLRV().getTotalOperacionInafecta().scale() > 2) {
                    addActionError("El formato del importe total de la operación inafecta es incorrecto.");
                }
            }
        } else {
            // de no existir registrar '0.00'
            getDetalleLRV().setTotalOperacionInafecta(new BigDecimal("0.00"));
        }

        // ISC
        if (getDetalleLRV().getIsc() != null) {
            // 3. Acepta negativos sólo si campo 5 = '07' o '87' 0 '97'
            if (getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 7
                    || getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 87
                    || getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() == 97) {
                if (getDetalleLRV().getIsc().precision() > 14
                        || getDetalleLRV().getIsc().scale() > 2) {
                    addActionError("El formato del ISC es incorrecto.");
                }
            } else {
                if (getDetalleLRV().getIsc().compareTo(BigDecimal.ZERO) < 0
                        || getDetalleLRV().getIsc().precision() > 14
                        || getDetalleLRV().getIsc().scale() > 2) {
                    addActionError("El formato del ISC es incorrecto.");
                }
            }
        } else {
            // de no existir registrar '0.00'
            getDetalleLRV().setTotalOperacionInafecta(new BigDecimal("0.00"));
        }

        // Base imponible de la operacion gravada ????????

        // IGV ????????


        // otros tributos y cargos
        if (getDetalleLRV().getOtrosTributos() != null) {
            if (getDetalleLRV().getOtrosTributos().compareTo(BigDecimal.ZERO) < 0
                    || getDetalleLRV().getOtrosTributos().precision() > 14
                    || getDetalleLRV().getOtrosTributos().scale() > 2) {
                addActionError("El formato de Otros tributos y cargos es incorrecto.");
            }
        } else {
            // de no existir registrar '0.00'
            getDetalleLRV().setOtrosTributos(new BigDecimal("0.00"));
        }

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    }

    public long getRuc() {
        return ruc;
    }

    public void setRuc(long ruc) {
        this.ruc = ruc;
    }

    public EmpresaCliente getEmpresaCliente() {
        return empresaCliente;
    }

    public void setEmpresaCliente(EmpresaCliente empresaCliente) {
        this.empresaCliente = empresaCliente;
    }

    public DetalleLibroRegistroVentas getDetalleLRV() {
        return detalleLRV;
    }

    public void setDetalleLRV(DetalleLibroRegistroVentas detalleLRV) {
        this.detalleLRV = detalleLRV;
    }

    public List<TipoComprobantePagoODocumento> getTiposComprobantes() {
        return tiposComprobantes;
    }

    public void setTiposComprobantes(List<TipoComprobantePagoODocumento> tiposComprobantes) {
        this.tiposComprobantes = tiposComprobantes;
    }

    public List<TipoDocumentoIdentidad> getTiposDocumentos() {
        return tiposDocumentos;
    }

    public void setTiposDocumentos(List<TipoDocumentoIdentidad> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }

    public boolean isNuevoComprador() {
        return nuevoComprador;
    }

    public void setNuevoComprador(boolean nuevoComprador) {
        this.nuevoComprador = nuevoComprador;
    }
}