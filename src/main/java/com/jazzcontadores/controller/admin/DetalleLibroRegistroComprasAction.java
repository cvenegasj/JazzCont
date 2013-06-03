/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.jazzcontadores.extra.JCConstants;
import com.jazzcontadores.model.dao.CodigoAduanaDAO;
import com.jazzcontadores.model.dao.EmpresaClienteDAO;
import com.jazzcontadores.model.dao.LibroRegistroComprasDAO;
import com.jazzcontadores.model.dao.ProveedorDAO;
import com.jazzcontadores.model.dao.TipoComprobantePagoODocumentoDAO;
import com.jazzcontadores.model.dao.TipoDocumentoIdentidadDAO;
import com.jazzcontadores.model.entities.CodigoAduana;
import com.jazzcontadores.model.entities.DetalleComprobanteCompra;
import com.jazzcontadores.model.entities.DetalleLibroRegistroCompras;
import com.jazzcontadores.model.entities.EmpresaCliente;
import com.jazzcontadores.model.entities.LibroRegistroCompras;
import com.jazzcontadores.model.entities.ProductoCompras;
import com.jazzcontadores.model.entities.Proveedor;
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
public class DetalleLibroRegistroComprasAction extends ActionSupport {

    private long ruc;
    private boolean nuevoProveedor;
    private EmpresaCliente empresaCliente;
    private DetalleLibroRegistroCompras detalleLRC;
    private List<TipoComprobantePagoODocumento> tiposComprobantes = new ArrayList<TipoComprobantePagoODocumento>();
    private List<TipoDocumentoIdentidad> tiposDocumentos = new ArrayList<TipoDocumentoIdentidad>();
    private List<CodigoAduana> codigosAduana = new ArrayList<CodigoAduana>();

    public String add() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();
        TipoComprobantePagoODocumentoDAO tDAO = factory.getTipoComprobantePagoODocumentoDAO();
        TipoDocumentoIdentidadDAO docDAO = factory.getTipoDocumentoIdentidadDAO();
        CodigoAduanaDAO aduanaDAO = factory.getCodigoAduanaDAO();

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
        // llenar los códigos aduaneros
        this.setCodigosAduana(aduanaDAO.findAll());

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "add";
    }

    public String save() throws Exception {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        LibroRegistroComprasDAO libroComprasDAO = factory.getLibroRegistroComprasDAO();
        ProveedorDAO proveedorDAO = factory.getProveedorDAO();

        // buscar si existe un Libro Reg. Compras con el periodo adecuado, si no existe creamos uno nuevo
        // ********* modificar algoritmo cuando la fecha frontera sea mayor que 1 ***********        
        Calendar cPeriodo = new GregorianCalendar();
        cPeriodo.setTime(this.getDetalleLRC().getComprobanteCompra().getFechaEmision());
        // para los periodos de los libros, el día es 1 siempre
        cPeriodo.set(Calendar.DAY_OF_MONTH, 1);

        LibroRegistroCompras libroExistente = libroComprasDAO.findByPeriodo(this.getEmpresaCliente().getRuc(), cPeriodo.getTime());

        try {
            if (libroExistente == null) {
                Calendar cFechaFin = (Calendar) cPeriodo.clone();
                cFechaFin.set(Calendar.DAY_OF_MONTH, cFechaFin.getActualMaximum(Calendar.DAY_OF_MONTH));

                LibroRegistroCompras libroRCNuevo = new LibroRegistroCompras();
                libroRCNuevo.setPeriodo(cPeriodo.getTime());
                libroRCNuevo.setFechaInicio(cPeriodo.getTime());
                libroRCNuevo.setFechaFin(cFechaFin.getTime());
                libroRCNuevo.setEstaCerrado(false);
                libroRCNuevo.setEmpresaCliente(this.getEmpresaCliente());

                libroComprasDAO.makePersistent(libroRCNuevo);

                if (this.isNuevoProveedor()) {
                    // viene validado, solo es necesario registrar
                    proveedorDAO.makePersistent(this.getDetalleLRC().getComprobanteCompra().getProveedor());
                }

                if (this.getDetalleLRC().getComprobanteCompra().getCodigoAduana().getNumero() != -1) {
                    // buscar el codigo aduana mediante DAO
                } else {
                    this.getDetalleLRC().getComprobanteCompra().setCodigoAduana(null);
                }

                this.getDetalleLRC().setLibroRegistroCompras(libroRCNuevo); // no se puede obviar
                this.getDetalleLRC().setFechaHoraRegistro(new Date());
                this.getDetalleLRC().setNumeroCorrelativo(1); // libro nuevo, primer detalle

                //para calcular el total
                BigDecimal total = new BigDecimal("0.00");
                total.setScale(2, RoundingMode.HALF_EVEN);

                for (Iterator<DetalleComprobanteCompra> it = this.getDetalleLRC().getComprobanteCompra().getDetallesComprobanteCompra().iterator(); it.hasNext();) {
                    DetalleComprobanteCompra d = it.next();

                    if (d.getProductoCompras().getIdProductoCompras() == 0) {
                        // crear nueva referencia para registrar nuevo producto
                        ProductoCompras pc = new ProductoCompras();
                        pc.setNombre(d.getProductoCompras().getNombre());
                        pc.setPrecio(d.getProductoCompras().getPrecio());
                        d.setProductoCompras(pc);
                    }
                    BigDecimal subtotal = d.getProductoCompras().getPrecio().multiply(new BigDecimal(d.getCantidad()));
                    d.setSubtotal(subtotal);
                    d.setComprobanteCompra(this.getDetalleLRC().getComprobanteCompra());
                    d.setPrecioUnitario(d.getProductoCompras().getPrecio()); // se copia el precio
                    total = total.add(d.getSubtotal());
                }

                this.getDetalleLRC().getComprobanteCompra().setImporteTotal(total);
                this.getDetalleLRC().getComprobanteCompra().setBase(total.multiply(JCConstants.BASE));
                this.getDetalleLRC().getComprobanteCompra().setIgv(total.multiply(JCConstants.IGV));
                libroRCNuevo.getDetallesLibroRegistroCompras().add(this.getDetalleLRC());

            } else {
                if (libroExistente.isEstaCerrado()) {
                    return "libroCerrado";
                }

                // establecemos el proveedor
                if (this.isNuevoProveedor()) {
                    proveedorDAO.makePersistent(this.getDetalleLRC().getComprobanteCompra().getProveedor());
                }

                if (this.getDetalleLRC().getComprobanteCompra().getCodigoAduana().getNumero() != -1) {
                    // buscar el codigo aduana mediante DAO
                } else {
                    this.getDetalleLRC().getComprobanteCompra().setCodigoAduana(null);
                }

                this.getDetalleLRC().setLibroRegistroCompras(libroExistente);
                this.getDetalleLRC().setFechaHoraRegistro(new Date());

                //para calcular el total
                BigDecimal total = new BigDecimal("0.00");
                total.setScale(2, RoundingMode.HALF_EVEN);

                for (Iterator<DetalleComprobanteCompra> it = this.getDetalleLRC().getComprobanteCompra().getDetallesComprobanteCompra().iterator(); it.hasNext();) {
                    DetalleComprobanteCompra d = it.next();

                    if (d.getProductoCompras().getIdProductoCompras() == 0) {
                        // crear nueva referencia para registrar nuevo producto
                        ProductoCompras pc = new ProductoCompras();
                        pc.setNombre(d.getProductoCompras().getNombre());
                        pc.setPrecio(d.getProductoCompras().getPrecio());
                        d.setProductoCompras(pc);
                    }
                    BigDecimal subtotal = d.getProductoCompras().getPrecio().multiply(new BigDecimal(d.getCantidad()));
                    d.setSubtotal(subtotal);
                    d.setComprobanteCompra(this.getDetalleLRC().getComprobanteCompra());
                    d.setPrecioUnitario(d.getProductoCompras().getPrecio()); // se copia el precio
                    total = total.add(d.getSubtotal());
                }

                this.getDetalleLRC().getComprobanteCompra().setImporteTotal(total);
                this.getDetalleLRC().getComprobanteCompra().setBase(total.multiply(JCConstants.BASE));
                this.getDetalleLRC().getComprobanteCompra().setIgv(total.multiply(JCConstants.IGV));

                // reordenamos la lista de detalles
                Date ultimaFechaRegistrada = libroExistente.getDetallesLibroRegistroCompras()
                        .get(libroExistente.getDetallesLibroRegistroCompras().size() - 1)
                        .getComprobanteCompra().getFechaEmision();
                // si la fecha de emision del comprobante es anterior a la fecha del último detalle ordenado
                if (this.getDetalleLRC().getComprobanteCompra().getFechaEmision().before(ultimaFechaRegistrada)) {
                    libroExistente.getDetallesLibroRegistroCompras().add(this.getDetalleLRC());
                    // reordenamos la lista
                    Collections.sort(libroExistente.getDetallesLibroRegistroCompras(), new Comparator<DetalleLibroRegistroCompras>() {
                        @Override
                        public int compare(DetalleLibroRegistroCompras d1, DetalleLibroRegistroCompras d2) {
                            return d1.getComprobanteCompra().getFechaEmision().compareTo(d2.getComprobanteCompra().getFechaEmision());
                        }
                    });
                    // establecemos el numero correlativo
                    for (int i = 0; i < libroExistente.getDetallesLibroRegistroCompras().size(); i++) {
                        // se reordena la lista de detalles
                        libroExistente.getDetallesLibroRegistroCompras().get(i).setNumeroCorrelativo(i + 1);
                    }
                } else {
                    // nos aseguramos que el tamaño sea mayor que 0 para no generar una excepcion
                    if (libroExistente.getDetallesLibroRegistroCompras().size() > 0) {
                        // le asignamos el último número correlativo más 1
                        DetalleLibroRegistroCompras d = libroExistente.getDetallesLibroRegistroCompras().get(libroExistente.getDetallesLibroRegistroCompras().size() - 1);
                        int ultimoNCorrelativo = d.getNumeroCorrelativo();
                        this.getDetalleLRC().setNumeroCorrelativo(ultimoNCorrelativo + 1);
                    } else {
                        this.getDetalleLRC().setNumeroCorrelativo(1);
                    }

                    libroExistente.getDetallesLibroRegistroCompras().add(this.getDetalleLRC());
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
        EmpresaCliente e = factory.getEmpresaClienteDAO().findByRuc(ruc);
        this.setEmpresaCliente(e);

        // llenar la lista de tipos de comprobantes en la vista        
        this.setTiposComprobantes(factory.getTipoComprobantePagoODocumentoDAO().findAll());
        // llenar la lista de tipo de documentos en la vista
        this.setTiposDocumentos(factory.getTipoDocumentoIdentidadDAO().findAll());
        // llenar los códigos aduaneros
        this.setCodigosAduana(factory.getCodigoAduanaDAO().findAll());

        // validaciones
        if (getDetalleLRC().getComprobanteCompra().getFechaEmision() == null) {
            addActionError("Debe especificar la fecha de emisión del comprobante.");
        }
        if (getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() <= 0) {
            addActionError("Debe especificar el tipo de comprobante (Tabla 10).");
        }
        if (!getDetalleLRC().getComprobanteCompra().getSerie().trim().matches("-|\\w{1,20}")) {
            addActionError("El formato de la serie del comprobante es incorrecto.");
        }
        if (!getDetalleLRC().getComprobanteCompra().getAnioEmisionDuaOdsi().trim().equals("")
                && !getDetalleLRC().getComprobanteCompra().getAnioEmisionDuaOdsi().trim().matches("^\\d{4}")) {
            addActionError("El formato del año de emisión de DUA o DSI es incorrecto.");
        }
        if (!getDetalleLRC().getComprobanteCompra().getNumero().trim().equals("")
                && !getDetalleLRC().getComprobanteCompra().getNumero().trim().matches("^\\d{1,20}")) {
            addActionError("El formato del número de comprobante es incorrecto.");
        }
        // tipo documento identidad de proveedor
        if (getDetalleLRC().getComprobanteCompra().getProveedor().getTipoDocumentoIdentidad().getNumero() == null) {
            addActionError("Debe especificar el tipo de documento de indentidad del proveedor.");
        } else {
            if (!getDetalleLRC().getComprobanteCompra().getProveedor().getTipoDocumentoIdentidad().getNumero().trim().matches("^\\w$")) {
                addActionError("El formato del tipo de documento de identidad del proveedor es incorrecto.");
            }
        }
        // numero documento identidad de proveedor
        if (getDetalleLRC().getComprobanteCompra().getProveedor().getNumeroDocumentoIdentidad() == null) {
            addActionError("Debe especificar el número de documento de identidad del proveedor.");
        } else {
            if (!getDetalleLRC().getComprobanteCompra().getProveedor().getNumeroDocumentoIdentidad().trim().matches("^\\w{1,15}$")) {
                addActionError("El formato del número de documento de identidad del proveedor es incorrecto.");
            }
        }
        // razon social del proveedor
        if (getDetalleLRC().getComprobanteCompra().getProveedor().getRazonSocial() != null
                && !getDetalleLRC().getComprobanteCompra().getProveedor().getRazonSocial().trim().matches("^.{1,60}")) {
            addActionError("El formato de la razón social del proveedor es incorrecto.");
        }
        // verificar existencia de proveedor
        if (getDetalleLRC().getComprobanteCompra().getProveedor().getTipoDocumentoIdentidad().getNumero() != null
                && getDetalleLRC().getComprobanteCompra().getProveedor().getNumeroDocumentoIdentidad() != null) {
            Proveedor proveedor = factory.getProveedorDAO().findByTipoDocumentoYNumeroDocumento(
                    this.getDetalleLRC().getComprobanteCompra().getProveedor().getTipoDocumentoIdentidad().getNumero(),
                    this.getDetalleLRC().getComprobanteCompra().getProveedor().getNumeroDocumentoIdentidad());
            if (this.isNuevoProveedor()) {
                if (proveedor != null) {
                    addActionError("El proveedor a registrar ya existe en la base de datos.");
                }
            } else {
                getDetalleLRC().getComprobanteCompra().setProveedor(proveedor); // se inyecta el proveedor
            }
        }
        // reglas del formato de libros electronicos
        if (getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() != null
                && getDetalleLRC().getComprobanteCompra().getNumero() != null) {
            if (getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 1
                    || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 3
                    || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 4
                    || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 7
                    || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 8) {
                if (!getDetalleLRC().getComprobanteCompra().getSerie().matches("-|\\w{1,4}")) {
                    addActionError("El número de serie para el tipo de comprobante seleccionado debe tener hasta 4 dígitos.");
                }
            }
            if (getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 50
                    || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 52) {
                if (!getDetalleLRC().getComprobanteCompra().getSerie().matches("-|\\w{1,3}")) {
                    addActionError("El número de serie para el tipo de comprobante seleccionado debe tener hasta 3 dígitos.");
                }
            }
        }
        // importe total del comprobante
        /*if (getDetalleLRC().getComprobanteCompra().getImporteTotal() == null
         || getDetalleLRC().getComprobanteCompra().getImporteTotal().compareTo(BigDecimal.ZERO) <= 0
         || getDetalleLRC().getComprobanteCompra().getImporteTotal().precision() > 14
         || getDetalleLRC().getComprobanteCompra().getImporteTotal().scale() > 2) {
         addActionError("El formato del importe total del comprobante es incorrecto");
         }*/
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

    public DetalleLibroRegistroCompras getDetalleLRC() {
        return detalleLRC;
    }

    public void setDetalleLRC(DetalleLibroRegistroCompras detalleLRC) {
        this.detalleLRC = detalleLRC;
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

    public List<CodigoAduana> getCodigosAduana() {
        return codigosAduana;
    }

    public void setCodigosAduana(List<CodigoAduana> codigosAduana) {
        this.codigosAduana = codigosAduana;
    }

    public boolean isNuevoProveedor() {
        return nuevoProveedor;
    }

    public void setNuevoProveedor(boolean nuevoProveedor) {
        this.nuevoProveedor = nuevoProveedor;
    }
}
