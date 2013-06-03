/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.jazzcontadores.model.dao.CompradorDAO;
import com.jazzcontadores.model.dao.ComprobanteVentaDAO;
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

                for (Iterator<DetalleComprobanteVenta> it = this.getDetalleLRV().getComprobanteVenta().getDetallesComprobanteVenta().iterator(); it.hasNext();) {
                    DetalleComprobanteVenta d = it.next();

                    if (d.getProductoVentas().getIdProductoVentas() == 0) {
                        // registrar nuevo producto
                        ProductoVentas pv = new ProductoVentas();
                        pv.setNombre(d.getProductoVentas().getNombre());
                        pv.setPrecio(d.getProductoVentas().getPrecio());
                        d.setProductoVentas(pv);
                    }
                    d.setComprobanteVenta(this.getDetalleLRV().getComprobanteVenta());
                    d.setPrecioUnitario(d.getProductoVentas().getPrecio()); // se copia el precio
                }

                libroRVNuevo.getDetallesLibroRegistroVentas().add(this.getDetalleLRV());

            } else {
                if (libroExistente.isEstaCerrado()) {
                    return "libroCerrado";
                }
                
                if (this.isNuevoComprador()) {
                    compradorDAO.makePersistent(this.getDetalleLRV().getComprobanteVenta().getComprador());
                } 

                this.getDetalleLRV().setLibroRegistroVentas(libroExistente); // no se puede obviar
                this.getDetalleLRV().setFechaHoraRegistro(new Date());

                for (Iterator<DetalleComprobanteVenta> it = this.getDetalleLRV().getComprobanteVenta().getDetallesComprobanteVenta().iterator(); it.hasNext();) {
                    DetalleComprobanteVenta d = it.next();

                    if (d.getProductoVentas().getIdProductoVentas() == 0) {
                        // registrar nuevo producto
                        ProductoVentas pv = new ProductoVentas();
                        pv.setNombre(d.getProductoVentas().getNombre());
                        pv.setPrecio(d.getProductoVentas().getPrecio());
                        d.setProductoVentas(pv);
                    }
                    d.setComprobanteVenta(this.getDetalleLRV().getComprobanteVenta());
                    d.setPrecioUnitario(d.getProductoVentas().getPrecio()); // se copia el precio
                }

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
                    // le asignamos el último número correlativo más 1
                    int ultimoNCorrelativo = libroExistente.getDetallesLibroRegistroVentas()
                            .get(libroExistente.getDetallesLibroRegistroVentas().size() - 1).getNumeroCorrelativo();
                    this.getDetalleLRV().setNumeroCorrelativo(ultimoNCorrelativo + 1);

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

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();

        // validaciones
        if (getDetalleLRV().getComprobanteVenta().getFechaEmision() == null) {
            addActionError("Debe especificar la fecha de emisión del comprobante.");
        }
        if (getDetalleLRV().getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero() <= 0) {
            addActionError("Debe especificar el tipo de comprobante (Tabla 10).");
        }
        if (getDetalleLRV().getComprobanteVenta().getSerie() != null 
                && (!getDetalleLRV().getComprobanteVenta().getSerie().trim().equals("-")
                || !getDetalleLRV().getComprobanteVenta().getSerie().trim().matches("^\\w{1,20}$"))) {
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
        
        // importe total del comprobante
        /*if (getDetalleLRV().getComprobanteVenta().getImporteTotal() == null
         || getDetalleLRV().getComprobanteVenta().getImporteTotal().compareTo(BigDecimal.ZERO) <= 0
         || getDetalleLRV().getComprobanteVenta().getImporteTotal().precision() > 14
         || getDetalleLRV().getComprobanteVenta().getImporteTotal().scale() > 2) {
         addActionError("El formato del importe total del comprobante es incorrecto");
         }*/
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