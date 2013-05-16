/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.jazzcontadores.model.dao.CodigoAduanaDAO;
import com.jazzcontadores.model.dao.ComprobanteCompraDAO;
import com.jazzcontadores.model.dao.EmpresaClienteDAO;
import com.jazzcontadores.model.dao.LibroRegistroComprasDAO;
import com.jazzcontadores.model.dao.ProveedorDAO;
import com.jazzcontadores.model.dao.TipoComprobantePagoODocumentoDAO;
import com.jazzcontadores.model.dao.TipoDocumentoIdentidadDAO;
import com.jazzcontadores.model.entities.CodigoAduana;
import com.jazzcontadores.model.entities.DetalleComprobanteCompra;
import com.jazzcontadores.model.entities.DetalleLibroRegistroCompras;
import com.jazzcontadores.model.entities.DetalleLibroRegistroVentas;
import com.jazzcontadores.model.entities.EmpresaCliente;
import com.jazzcontadores.model.entities.LibroRegistroCompras;
import com.jazzcontadores.model.entities.Proveedor;
import com.jazzcontadores.model.entities.TipoComprobantePagoODocumento;
import com.jazzcontadores.model.entities.TipoDocumentoIdentidad;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.math.BigDecimal;
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

                // establecemos el proveedor
                Proveedor proveedor = proveedorDAO.findByTipoDocumentoYNumeroDocumento(
                        this.getDetalleLRC().getComprobanteCompra().getProveedor().getTipoDocumentoIdentidad().getNumero(),
                        this.getDetalleLRC().getComprobanteCompra().getProveedor().getNumeroDocumentoIdentidad());
                if (proveedor == null) {
                    proveedorDAO.makePersistent(this.getDetalleLRC().getComprobanteCompra().getProveedor());
                } else {
                    this.getDetalleLRC().getComprobanteCompra().setProveedor(proveedor);
                }

                if (this.getDetalleLRC().getComprobanteCompra().getCodigoAduana().getNumero() != -1) {
                    // buscar el codigo aduana mediante DAO
                } else {
                    this.getDetalleLRC().getComprobanteCompra().setCodigoAduana(null);
                }

                //this.getDetalleLRC().setBaseImponible1(this.getDetalleLRC().getComprobanteCompra().getBase());
                //this.getDetalleLRC().setIgv1(this.getDetalleLRC().getComprobanteCompra().getIgv());
                //this.getDetalleLRC().setImporteTotal(this.getDetalleLRC().getComprobanteCompra().getImporteTotal());                
                this.getDetalleLRC().setLibroRegistroCompras(libroRCNuevo); // no se puede obviar
                this.getDetalleLRC().setFechaHoraRegistro(new Date());
                this.getDetalleLRC().setNumeroCorrelativo(1); // libro nuevo, primer detalle
                for (Iterator<DetalleComprobanteCompra> it = this.getDetalleLRC().getComprobanteCompra().getDetallesComprobanteCompra().iterator(); it.hasNext();) {
                    DetalleComprobanteCompra d = it.next();
                    d.setComprobanteCompra(this.getDetalleLRC().getComprobanteCompra());
                }

                libroRCNuevo.getDetallesLibroRegistroCompras().add(this.getDetalleLRC());

            } else if (!libroExistente.isEstaCerrado()) {

                // establecemos el proveedor
                Proveedor proveedor = proveedorDAO.findByTipoDocumentoYNumeroDocumento(
                        this.getDetalleLRC().getComprobanteCompra().getProveedor().getTipoDocumentoIdentidad().getNumero(),
                        this.getDetalleLRC().getComprobanteCompra().getProveedor().getNumeroDocumentoIdentidad());
                if (proveedor == null) {
                    proveedorDAO.makePersistent(this.getDetalleLRC().getComprobanteCompra().getProveedor());
                } else {
                    this.getDetalleLRC().getComprobanteCompra().setProveedor(proveedor);
                }

                if (this.getDetalleLRC().getComprobanteCompra().getCodigoAduana().getNumero() != -1) {
                    // buscar el codigo aduana mediante DAO
                } else {
                    this.getDetalleLRC().getComprobanteCompra().setCodigoAduana(null);
                }

                //this.getDetalleLRC().setBaseImponible1(this.getDetalleLRC().getComprobanteCompra().getBase());
                //this.getDetalleLRC().setIgv1(this.getDetalleLRC().getComprobanteCompra().getIgv());
                //this.getDetalleLRC().setImporteTotal(this.getDetalleLRC().getComprobanteCompra().getImporteTotal());
                this.getDetalleLRC().setLibroRegistroCompras(libroExistente);
                this.getDetalleLRC().setFechaHoraRegistro(new Date());
                for (DetalleComprobanteCompra d : this.getDetalleLRC().getComprobanteCompra().getDetallesComprobanteCompra()) {
                    d.setComprobanteCompra(this.getDetalleLRC().getComprobanteCompra());
                }

                // reordenamos la lista de detalles
                Date ultimaFechaRegistrada = libroExistente.getDetallesLibroRegistroCompras()
                        .get(libroExistente.getDetallesLibroRegistroCompras().size() - 1)
                        .getComprobanteCompra().getFechaEmision();
                if (this.getDetalleLRC().getComprobanteCompra().getFechaEmision().before(ultimaFechaRegistrada)) {                    
                    this.getDetalleLRC().setNumeroCorrelativo(1000000); // se pone al último de la lista
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
                    // le asignamos el último número correlativo más 1
                    int ultimoNCorrelativo = libroExistente.getDetallesLibroRegistroCompras()
                            .get(libroExistente.getDetallesLibroRegistroCompras().size() - 1).getNumeroCorrelativo();
                    this.getDetalleLRC().setNumeroCorrelativo(ultimoNCorrelativo + 1);
                }

            } else {
                return "libroCerrado";
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

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();

        // validaciones
        if (getDetalleLRC().getComprobanteCompra().getFechaEmision() == null) {
            addActionError("Debe especificar la fecha de emisión del comprobante.");
        }
        if (getDetalleLRC().getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero() <= 0) {
            addActionError("Debe especificar el tipo de comprobante (Tabla 10).");
        }
        if (getDetalleLRC().getComprobanteCompra().getSerie() != null
                && getDetalleLRC().getComprobanteCompra().getSerie().trim().equals("")) {
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
        /*
         // comparar la base del comprobante con la del detalle
         if (getDetalleLRC().getBaseImponible1().compareTo(getDetalleLRC().getComprobanteCompra().getBase()) != 0) {
         addActionError("La base del comprobante no coincide con la del formulario.");
         }
         if (getDetalleLRC().getIgv1().compareTo(getDetalleLRC().getComprobanteCompra().getIgv()) != 0) {
         addActionError("El IGV del comprobante no coincide con el del formulario.");
         }
         if (getDetalleLRC().getImporteTotal().compareTo(getDetalleLRC().getComprobanteCompra().getImporteTotal()) != 0) {
         addActionError("El importe del comprobante no coincide con el del formulario.");
         }    */
        // base del comprobante
        if (getDetalleLRC().getComprobanteCompra().getBase() == null
                || getDetalleLRC().getComprobanteCompra().getBase().compareTo(BigDecimal.ZERO) <= 0
                || getDetalleLRC().getComprobanteCompra().getBase().precision() > 14
                || getDetalleLRC().getComprobanteCompra().getBase().scale() > 2) {
            addActionError("El formato de la base del comprobante es incorrecto");
        } else {
        }
        // igv del comprobante
        if (getDetalleLRC().getComprobanteCompra().getIgv() == null
                || getDetalleLRC().getComprobanteCompra().getIgv().compareTo(BigDecimal.ZERO) <= 0
                || getDetalleLRC().getComprobanteCompra().getIgv().precision() > 14
                || getDetalleLRC().getComprobanteCompra().getIgv().scale() > 2) {
            addActionError("El formato del IGV del comprobante es incorrecto");
        }
        // importe total del comprobante
        if (getDetalleLRC().getComprobanteCompra().getImporteTotal() == null
                || getDetalleLRC().getComprobanteCompra().getImporteTotal().compareTo(BigDecimal.ZERO) <= 0
                || getDetalleLRC().getComprobanteCompra().getImporteTotal().precision() > 14
                || getDetalleLRC().getComprobanteCompra().getImporteTotal().scale() > 2) {
            addActionError("El formato del importe total del comprobante es incorrecto");
        }
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
}
