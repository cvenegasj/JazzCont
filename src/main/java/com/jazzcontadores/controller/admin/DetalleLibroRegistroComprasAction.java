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
    private String tipoAdquisicion;
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

                if (this.getDetalleLRC().getComprobanteCompra().getProveedor() != null
                        && this.isNuevoProveedor()) {
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

                libroRCNuevo.getDetallesLibroRegistroCompras().add(this.getDetalleLRC());

            } else {
                if (libroExistente.isEstaCerrado()) {
                    return "libroCerrado";
                }

                // establecemos el proveedor
                if (this.getDetalleLRC().getComprobanteCompra().getProveedor() != null
                        && this.isNuevoProveedor()) {
                    proveedorDAO.makePersistent(this.getDetalleLRC().getComprobanteCompra().getProveedor());
                }

                if (this.getDetalleLRC().getComprobanteCompra().getCodigoAduana().getNumero() != -1) {
                    // buscar el codigo aduana mediante DAO
                } else {
                    this.getDetalleLRC().getComprobanteCompra().setCodigoAduana(null);
                }

                this.getDetalleLRC().setLibroRegistroCompras(libroExistente);
                this.getDetalleLRC().setFechaHoraRegistro(new Date());

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
        if (getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() < 0) {
            addActionError("Debe especificar el tipo de comprobante (Tabla 10).");
        }
        if (!getDetalleLRC().getComprobanteCompra().getSerie().trim().matches("-|\\w{1,20}")) {
            addActionError("El formato de la serie del comprobante es incorrecto.");
        }
        if (!getDetalleLRC().getComprobanteCompra().getAnioEmisionDuaOdsi().trim().equals("") // campo no olbigatorio
                && !getDetalleLRC().getComprobanteCompra().getAnioEmisionDuaOdsi().trim().matches("^\\d{4}")) {
            addActionError("El formato del año de emisión de DUA o DSI es incorrecto.");
        }
        if (!getDetalleLRC().getComprobanteCompra().getNumero().trim().matches("^\\d{1,20}")) {
            addActionError("El formato del número de comprobante es incorrecto.");
        }

        // valor adquisiciones no gravadas        
        if (getDetalleLRC().getComprobanteCompra().getValorAdquisicionesNoGravadas() != null) {
            if (getDetalleLRC().getComprobanteCompra().getValorAdquisicionesNoGravadas().compareTo(BigDecimal.ZERO) <= 0
                    || getDetalleLRC().getComprobanteCompra().getValorAdquisicionesNoGravadas().precision() > 14
                    || getDetalleLRC().getComprobanteCompra().getValorAdquisicionesNoGravadas().scale() > 2) {
                addActionError("El formato del valor de las adquisiciones no gravadas es incorrecto.");
            }
        }
        // isc
        if (getDetalleLRC().getComprobanteCompra().getIsc() != null) {
            if (getDetalleLRC().getComprobanteCompra().getIsc().compareTo(BigDecimal.ZERO) <= 0
                    || getDetalleLRC().getComprobanteCompra().getIsc().precision() > 14
                    || getDetalleLRC().getComprobanteCompra().getIsc().scale() > 2) {
                addActionError("El formato del ISC es incorrecto.");
            }
        }
        // otros tributos y cargos
        if (getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos() != null) {
            if (getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos().compareTo(BigDecimal.ZERO) <= 0
                    || getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos().precision() > 14
                    || getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos().scale() > 2) {
                addActionError("El formato del valor de los otros tributos y cargos es incorrecto.");
            }
        }
        // verificar existencia de proveedor
        if (!getDetalleLRC().getComprobanteCompra().getProveedor().getTipoDocumentoIdentidad().getNumero().equals("-1")
                && !getDetalleLRC().getComprobanteCompra().getProveedor().getNumeroDocumentoIdentidad().trim().equals("")) {
            Proveedor proveedor = factory.getProveedorDAO().findByTipoDocumentoYNumeroDocumento(
                    this.getEmpresaCliente().getRuc(),
                    this.getDetalleLRC().getComprobanteCompra().getProveedor().getTipoDocumentoIdentidad().getNumero(),
                    this.getDetalleLRC().getComprobanteCompra().getProveedor().getNumeroDocumentoIdentidad());
            if (proveedor != null) {
                if (this.isNuevoProveedor()) {
                    addActionError("El proveedor a registrar ya existe en la base de datos.");
                } else {
                    this.getDetalleLRC().getComprobanteCompra().setProveedor(proveedor);
                }
            }
        }

        // =========================================================
        // reglas del formato de libros electronicos *********************
        // fecha de emisión
        if (getDetalleLRC().getComprobanteCompra().getFechaEmision() != null) {
            if (getDetalleLRC().getComprobanteCompra().getFechaEmision().after(new Date())) {
                addActionError("No se puede registrar fechas futuras en el sistema.");
            } else {
                // 4. Si fecha de emisión corresponde al periodo señalado en el campo 1, entonces campo 32 = '1'
                // 5. Si fecha de emisión está dentro de los doce meses anteriores al periodo señalado en el campo 1, entonces campo 32 = '6'
                // 6. Si fecha de emisión está fuera de los doce meses anteriores al periodo señalado en el campo 1, entonces campo 32 = '7'
                getDetalleLRC().setEstadoOportunidadDeAnotacion("1");
            }
        }
        // número de serie
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
        // anio emisión de DUA o DSI
        if (getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 50
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 52) {
            if (!getDetalleLRC().getComprobanteCompra().getAnioEmisionDuaOdsi().matches("^\\d{4}$")) {
                addActionError("El formato del año de emisión de la DUA o DSI es incorrecto.");
            } else {
                if (getDetalleLRC().getComprobanteCompra().getFechaEmision() != null) {
                    int añoDUA = Integer.parseInt(getDetalleLRC().getComprobanteCompra().getAnioEmisionDuaOdsi());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(getDetalleLRC().getComprobanteCompra().getFechaEmision());
                    if (añoDUA > cal.get(Calendar.YEAR)) {
                        addActionError("El año de emisión de la DUA o DSI debe ser menor o igual al año de la fecha de emisión.");
                    }
                }
            }
        } else {
            getDetalleLRC().getComprobanteCompra().setAnioEmisionDuaOdsi("0");
        }
        // fecha de vencimiento o pago
        if (getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 14) {
            if (getDetalleLRC().getComprobanteCompra().getFechaVencimientoOpago() == null) {
                addActionError("Debe especificar la fecha de vencimiento o pago para el tipo de comprobante seleccionado.");
            }
        }
        if (getDetalleLRC().getComprobanteCompra().getFechaVencimientoOpago() != null) {
            // verifica que la fecha de vencimiento o pago sea menor que el periodo especificado
            // Corregir cuando la fecha de inicio del periodo no sea 1 *********************
            // en ese caso se debe considerar el periodo anterior
            Calendar cal = Calendar.getInstance();
            cal.setTime(getDetalleLRC().getComprobanteCompra().getFechaEmision()); // el periodo se saca de la fecha de emisión
            int mes = cal.get(Calendar.MONTH);
            int año = cal.get(Calendar.YEAR);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(getDetalleLRC().getComprobanteCompra().getFechaVencimientoOpago());
            int mes2 = cal2.get(Calendar.MONTH);
            int año2 = cal2.get(Calendar.YEAR);

            if (mes2 > mes || año2 > año) {
                addActionError("La fecha de vencimiento o pago debe ser menor al periodo esecificado.");
            }
        }
        // comprobante referenciado


        // proveedor 
        // 1. Obligatorio, excepto cuando 
        // a. campo 5 = '00','03','05','06','07','08','11','12','13','14','15','16','18','19','22','23','26','28','30','34','35','36','37','55','56','87','88', '91', '97' y '98' o 
        if (getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 0
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 3
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 5
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 6
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 7
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 8
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 11
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 12
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 13
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 14
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 15
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 16
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 18
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 19
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 22
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 23
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 26
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 28
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 30
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 34
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 35
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 36
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 37
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 55
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 56
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 87
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 88
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 91
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 97
                || getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 98) {
            // es opcional
            // si alguno de los campos no tiene su valor vacío
            if (!getDetalleLRC().getComprobanteCompra().getProveedor().getTipoDocumentoIdentidad().getNumero().trim().equals("-1")
                    || !getDetalleLRC().getComprobanteCompra().getProveedor().getNumeroDocumentoIdentidad().trim().equals("")
                    || !getDetalleLRC().getComprobanteCompra().getProveedor().getRazonSocial().trim().equals("")) {
                // verificar formación correcta
                if (getDetalleLRC().getComprobanteCompra().getProveedor().getTipoDocumentoIdentidad().getNumero().trim().equals("-1")) {
                    addActionError("Debe especificar el tipo de documento de identidad del proveedor.");
                }
                if (!getDetalleLRC().getComprobanteCompra().getProveedor().getNumeroDocumentoIdentidad().trim().matches("-|\\w{1,15}")) {
                    addActionError("El formato del numero de documento de identidad del proveedor es incorrecto.");
                }
                if (!getDetalleLRC().getComprobanteCompra().getProveedor().getRazonSocial().trim().matches("-|.{1,60}")) {
                    addActionError("El formato de la razón social del proveedor es incorrecto.");
                }
            } else {
                // si todos los campos tienen su valor vacío
                this.getDetalleLRC().getComprobanteCompra().setProveedor(null);
            }
        } else {
            // es obligatorio
            if (getDetalleLRC().getComprobanteCompra().getProveedor().getTipoDocumentoIdentidad().getNumero().trim().equals("-1")) {
                addActionError("Debe especificar el tipo de documento de identidad del proveedor.");
            }
            if (!getDetalleLRC().getComprobanteCompra().getProveedor().getNumeroDocumentoIdentidad().trim().matches("-|\\w{1,15}")) {
                addActionError("El formato del numero de documento de identidad del proveedor es incorrecto.");
            }
            if (!getDetalleLRC().getComprobanteCompra().getProveedor().getRazonSocial().trim().matches("-|.{1,60}")) {
                addActionError("El formato de la razón social del proveedor es incorrecto.");
            }
        }


        if (this.getTipoAdquisicion().equals("gravada")) {

            this.getDetalleLRC().setEsAdquisicionGravada(true);

            BigDecimal total = new BigDecimal("0.00");
            total.setScale(2, RoundingMode.HALF_EVEN);
            if (!this.getDetalleLRC().getComprobanteCompra().getDetallesComprobanteCompra().isEmpty()) {

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

                BigDecimal base = total.multiply(JCConstants.BASE);
                BigDecimal igv = total.multiply(JCConstants.IGV);
                
                base = base.setScale(2, RoundingMode.HALF_EVEN);
                igv = igv.setScale(2, RoundingMode.HALF_EVEN);
                
                this.getDetalleLRC().getComprobanteCompra().setBase(base);
                this.getDetalleLRC().getComprobanteCompra().setIgv(igv);
            }

            // cálculo del total
            if (this.getDetalleLRC().getComprobanteCompra().getBase() != null) {
                total = total.add(this.getDetalleLRC().getComprobanteCompra().getBase());
            }
            if (this.getDetalleLRC().getComprobanteCompra().getIgv() != null) {
                total = total.add(this.getDetalleLRC().getComprobanteCompra().getIgv());
            }
            if (this.getDetalleLRC().getComprobanteCompra().getIsc() != null) {
                total = total.add(this.getDetalleLRC().getComprobanteCompra().getIsc());
            }
            if (this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos() != null) {
                total = total.add(this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos());
            }

            this.getDetalleLRC().getComprobanteCompra().setImporteTotal(total);

            // campos que pueden ser negativos
            if (this.getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 7
                    || this.getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 87
                    || this.getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 97) {
                // acepta negativos
                // base
                if (this.getDetalleLRC().getComprobanteCompra().getBase() != null) {
                    if (this.getDetalleLRC().getComprobanteCompra().getBase().precision() > 14
                            || this.getDetalleLRC().getComprobanteCompra().getBase().scale() > 2) {
                        addActionError("El formato de la Base Imponible es incorrecto.");
                    }
                } else {
                    addActionError("Debe especificar el valor la Base Imponible.");
                }
                // igv
                if (this.getDetalleLRC().getComprobanteCompra().getIgv() != null) {
                    if (this.getDetalleLRC().getComprobanteCompra().getIgv().precision() > 14
                            || this.getDetalleLRC().getComprobanteCompra().getIgv().scale() > 2) {
                        addActionError("El formato del IGV es incorrecto.");
                    }
                } else {
                    addActionError("Debe especificar el valor del IGV.");
                }
                // isc
                if (this.getDetalleLRC().getComprobanteCompra().getIsc() != null) {
                    if (this.getDetalleLRC().getComprobanteCompra().getIsc().precision() > 14
                            || this.getDetalleLRC().getComprobanteCompra().getIsc().scale() > 2) {
                        addActionError("El formato del ISC es incorrecto.");
                    }
                } else {
                    //addActionError("Debe especificar el valor del ISC.");
                }
                // otros tributos y cargos
                if (this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos() != null) {
                    if (this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos().precision() > 14
                            || this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos().scale() > 2) {
                        addActionError("El formato de Otros tributos y cargos es incorrecto.");
                    }
                } else {
                    //addActionError("Debe especificar el valor de Otros tributos y cargos.");
                }

            } else {
                // no acepta negativos
                // base
                if (this.getDetalleLRC().getComprobanteCompra().getBase() != null) {
                    if (this.getDetalleLRC().getComprobanteCompra().getBase().compareTo(BigDecimal.ZERO) < 0
                            || this.getDetalleLRC().getComprobanteCompra().getBase().precision() > 14
                            || this.getDetalleLRC().getComprobanteCompra().getBase().scale() > 2) {
                        addActionError("El formato de la Base Imponible es incorrecto.");
                    }
                } else {
                    addActionError("Debe especificar el valor la Base Imponible.");
                }
                // igv
                if (this.getDetalleLRC().getComprobanteCompra().getIgv() != null) {
                    if (this.getDetalleLRC().getComprobanteCompra().getIgv().compareTo(BigDecimal.ZERO) < 0
                            || this.getDetalleLRC().getComprobanteCompra().getIgv().precision() > 14
                            || this.getDetalleLRC().getComprobanteCompra().getIgv().scale() > 2) {
                        addActionError("El formato del IGV es incorrecto.");
                    }
                } else {
                    addActionError("Debe especificar el valor del IGV.");
                }
                // isc
                if (this.getDetalleLRC().getComprobanteCompra().getIsc() != null) {
                    if (this.getDetalleLRC().getComprobanteCompra().getIsc().compareTo(BigDecimal.ZERO) < 0
                            || this.getDetalleLRC().getComprobanteCompra().getIsc().precision() > 14
                            || this.getDetalleLRC().getComprobanteCompra().getIsc().scale() > 2) {
                        addActionError("El formato del ISC es incorrecto.");
                    }
                } else {
                    //addActionError("Debe especificar el valor del ISC.");
                }
                // otros tributos y cargos
                if (this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos() != null) {
                    if (this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos().precision() > 14
                            || this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos().scale() > 2) {
                        addActionError("El formato de Otros tributos y cargos es incorrecto.");
                    }
                } else {
                    //addActionError("Debe especificar el valor de Otros tributos y cargos.");
                }
            }

        } else if (this.getTipoAdquisicion().equals("no-gravada")) {

            this.getDetalleLRC().setEsAdquisicionGravada(false);
            this.getDetalleLRC().getComprobanteCompra().setBase(null);
            this.getDetalleLRC().getComprobanteCompra().setIgv(null);
            this.getDetalleLRC().getComprobanteCompra().setIsc(null);
            this.getDetalleLRC().setDestinoAdquisicionGravada(null);


            if (this.getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 7
                    || this.getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 87
                    || this.getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() == 97) {
                // acepta negativos
                // valor de adquisiciones no gravadas
                if (this.getDetalleLRC().getComprobanteCompra().getValorAdquisicionesNoGravadas() != null) {
                    if (this.getDetalleLRC().getComprobanteCompra().getValorAdquisicionesNoGravadas().precision() > 14
                            || this.getDetalleLRC().getComprobanteCompra().getValorAdquisicionesNoGravadas().scale() > 2) {
                        addActionError("El formato del valor de las adquisiciones no gravadas es incorrecto.");
                    } else {
                        // qué se debe registrar en el importe total ????????????? **********************
                    }
                } else {
                    addActionError("Debe especificar el valor las adquisiciones no gravadas.");
                }
            } else {
                // no acepta negativos
                if (this.getDetalleLRC().getComprobanteCompra().getValorAdquisicionesNoGravadas() != null) {
                    if (this.getDetalleLRC().getComprobanteCompra().getValorAdquisicionesNoGravadas().compareTo(BigDecimal.ZERO) < 0
                            || this.getDetalleLRC().getComprobanteCompra().getValorAdquisicionesNoGravadas().precision() > 14
                            || this.getDetalleLRC().getComprobanteCompra().getValorAdquisicionesNoGravadas().scale() > 2) {
                        addActionError("El formato del valor de las adquisiciones no gravadas es incorrecto.");
                    } else {
                        BigDecimal importeTotal = this.getDetalleLRC().getComprobanteCompra().getValorAdquisicionesNoGravadas();
                        if (this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos() != null) {
                            importeTotal = importeTotal.add(this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos());
                        }
                        this.getDetalleLRC().getComprobanteCompra().setImporteTotal(importeTotal);                        
                    }
                } else {
                    addActionError("Debe especificar el valor las adquisiciones no gravadas.");
                }
            }

            // otros tributos y cargos
            if (this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos() != null) {
                if (this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos().precision() > 14
                        || this.getDetalleLRC().getComprobanteCompra().getOtrosTributosYCargos().scale() > 2) {
                    addActionError("El formato de Otros tributos y cargos es incorrecto.");
                }
            } else {
                //addActionError("Debe especificar el valor de Otros tributos y cargos.");
            }
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

    public String getTipoAdquisicion() {
        return tipoAdquisicion;
    }

    public void setTipoAdquisicion(String tipoAdquisicion) {
        this.tipoAdquisicion = tipoAdquisicion;
    }
}
