/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.cliente;

import com.jazzcontadores.model.entities.Contacto;
import com.jazzcontadores.model.entities.DetalleLibroRegistroCompras;
import com.jazzcontadores.model.entities.DetalleLibroRegistroVentas;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.DetalleLibroRegistroComprasSerializable;
import com.jazzcontadores.util.DetalleLibroRegistroVentasSerializable;
import com.jazzcontadores.util.HibernateUtil;
import com.jazzcontadores.util.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Venegas
 */
public class LibrosAjaxAction extends ActionSupport implements UserAware {

    Object user;
    private boolean success; // for ext    
    private int idLibro;
    private int page;
    private int start;
    private int limit;
    private int totalCount;
    private List<DetalleLibroRegistroVentasSerializable> listaDetallesRVSrl = new ArrayList<DetalleLibroRegistroVentasSerializable>();
    private List<DetalleLibroRegistroComprasSerializable> listaDetallesRCSrl = new ArrayList<DetalleLibroRegistroComprasSerializable>();

    public String listDetallesRV() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);

        long ruc = ((Contacto) user).getEmpresaCliente().getRuc();

        this.setTotalCount(factory.getLibroRegistroVentasDAO().getTotalCountOfDetallesByLibro(ruc, this.getIdLibro()));
        List<DetalleLibroRegistroVentas> detallesRV = factory.getLibroRegistroVentasDAO()
                .findDetallesByIdLibroAndEmpresa(ruc, this.getIdLibro(), this.getStart(), this.getLimit());

        for (DetalleLibroRegistroVentas detalle : detallesRV) {
            DetalleLibroRegistroVentasSerializable detalleSrl = new DetalleLibroRegistroVentasSerializable();
            detalleSrl.setIdDetalleLibroRegistroVentas(detalle.getIdDetalleLibroRegistroVentas());
            detalleSrl.setNumeroCorrelativo(detalle.getNumeroCorrelativo());
            detalleSrl.setValorFacturadoExportacion(detalle.getValorFacturadoExportacion());
            detalleSrl.setBaseImponibleOpGravada(detalle.getComprobanteVenta().getBase()); // ************* revisar
            detalleSrl.setTotalOperacionExonerada(detalle.getTotalOperacionExonerada());
            detalleSrl.setTotalOperacionInafecta(detalle.getTotalOperacionInafecta());
            detalleSrl.setIsc(detalle.getIsc());
            detalleSrl.setIgv_ipm(detalle.getIgv_ipm());
            detalleSrl.setOtrosTributos(detalle.getOtrosTributos());
            detalleSrl.setImporteTotal(detalle.getImporteTotal());
            detalleSrl.setNumeroFinalTicketOcintaDelDia(detalle.getNumeroFinalTicketOcintaDelDia());
            detalleSrl.setTipoCambio(detalle.getTipoCambio());
            detalleSrl.setBaseImponibleArrozPilado(detalle.getBaseImponibleArrozPilado());
            detalleSrl.setImpuestoVentasArrozPilado(detalle.getImpuestoVentasArrozPilado());
            detalleSrl.setEstadoOportunidadDeAnotación(detalle.getEstadoOportunidadDeAnotacion());
            detalleSrl.setFechaHoraRegistro(detalle.getFechaHoraRegistro());
            // comprobante
            detalleSrl.setIdComprobanteVenta(detalle.getComprobanteVenta().getIdComprobanteVenta());
            detalleSrl.setFechaEmisionComprobante(detalle.getComprobanteVenta().getFechaEmision());
            detalleSrl.setFechaVencimientoComprobante(detalle.getComprobanteVenta().getFechaVencimiento());
            detalleSrl.setBaseComprobante(detalle.getComprobanteVenta().getBase());
            detalleSrl.setIgvComprobante(detalle.getComprobanteVenta().getIgv());
            detalleSrl.setImporteTotalComprobante(detalle.getComprobanteVenta().getImporteTotal());
            detalleSrl.setNumeroTipoComprobante(detalle.getComprobanteVenta().getTipoComprobantePagoODocumento().getNumero());
            detalleSrl.setSerieComprobante(detalle.getComprobanteVenta().getSerie());
            detalleSrl.setNumeroComprobante(detalle.getComprobanteVenta().getNumero());
            detalleSrl.setNumeroTipoDocIdentidadComprador(detalle.getComprobanteVenta().getComprador().getTipoDocumentoIdentidad().getNumero());
            detalleSrl.setNumeroDocIdentidadComprador(detalle.getComprobanteVenta().getComprador().getNumeroDocumentoIdentidad());
            detalleSrl.setRazonSocialONombresComprador(detalle.getComprobanteVenta().getComprador().getRazonSocialONombres());

            if (detalle.getComprobanteVentaReferenciado() != null) {
                detalleSrl.setIdComprobanteVentaReferenciado(detalle.getComprobanteVentaReferenciado().getIdComprobanteVenta());
                detalleSrl.setFechaComprobanteVentaReferenciado(detalle.getComprobanteVentaReferenciado().getFechaEmision());
                detalleSrl.setNumeroTipoComprobanteVentaReferenciado(detalle.getComprobanteVentaReferenciado().getTipoComprobantePagoODocumento().getNumero());
                detalleSrl.setSerieComprobanteVentaReferenciado(detalle.getComprobanteVentaReferenciado().getSerie());
                detalleSrl.setNumeroComprobanteVentaReferenciado(detalle.getComprobanteVentaReferenciado().getNumero());
            }

            this.getListaDetallesRVSrl().add(detalleSrl);
        }

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        this.setSuccess(true);
        return "listDetallesRV";
    }

    public String listDetallesRC() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);

        long ruc = ((Contacto) user).getEmpresaCliente().getRuc();

        this.setTotalCount(factory.getLibroRegistroComprasDAO().getTotalCountOfDetallesByLibro(ruc, this.getIdLibro()));
        List<DetalleLibroRegistroCompras> detallesRC = factory.getLibroRegistroComprasDAO()
                .findDetallesByIdLibroAndEmpresa(ruc, this.getIdLibro(), this.getStart(), this.getLimit());

        for (DetalleLibroRegistroCompras detalle : detallesRC) {
            DetalleLibroRegistroComprasSerializable detalleSrl = new DetalleLibroRegistroComprasSerializable();
            detalleSrl.setIdDetalleLibroRegistroCompras(detalle.getIdDetalleLibroRegistroCompras());
            detalleSrl.setNumeroCorrelativo(detalle.getNumeroCorrelativo());
            detalleSrl.setTipoAdquisicionGravada(detalle.getDestinoAdquisicionGravada());
            detalleSrl.setValorAdquisicionesNoGravadas(detalle.getComprobanteCompra().getValorAdquisicionesNoGravadas());
            detalleSrl.setIsc(detalle.getComprobanteCompra().getIsc());
            detalleSrl.setOtrosTributosYcargos(detalle.getComprobanteCompra().getOtrosTributosYCargos());
            detalleSrl.setImporteTotal(detalle.getImporteTotal());
            detalleSrl.setTipoCambio(detalle.getTipoCambio());
            detalleSrl.setNumeroCompPagoSujNoDom(detalle.getNumeroCompPagoSujNoDom());
            detalleSrl.setNumeroConstDepDetraccion(detalle.getNumeroConstDepDetraccion());
            detalleSrl.setFechaEmisionConstDepDetraccion(detalle.getFechaEmisionConstDepDetraccion());
            detalleSrl.setNumeroFinalOperDiariasSinCredFiscal(detalle.getNumeroFinalOperDiariasSinCredFiscal());
            detalleSrl.setMarcaComprobanteSujetoAretencion(detalle.getMarcaComprobanteSujetoAretencion());
            detalleSrl.setFechaHoraRegistro(detalle.getFechaHoraRegistro());
            if (detalleSrl.getTipoAdquisicionGravada().equals("gravada/exportacion")) {
                detalleSrl.setBaseImponible1(detalle.getComprobanteCompra().getBase());
                detalleSrl.setIgv1(detalle.getComprobanteCompra().getIgv());
            } else if (detalleSrl.getTipoAdquisicionGravada().equals("gravada/exportacion - no gravada")) {
                detalleSrl.setBaseImponible2(detalle.getComprobanteCompra().getBase());
                detalleSrl.setIgv2(detalle.getComprobanteCompra().getIgv());
            } else if (detalleSrl.getTipoAdquisicionGravada().equals("no-gravada")) {
                detalleSrl.setBaseImponible3(detalle.getComprobanteCompra().getBase());
                detalleSrl.setIgv3(detalle.getComprobanteCompra().getIgv());
            }

            // comprobante
            detalleSrl.setIdComprobanteCompra(detalle.getComprobanteCompra().getIdComprobanteCompra());
            detalleSrl.setNumeroComprobante(detalle.getComprobanteCompra().getNumero());
            detalleSrl.setSerieComprobante(detalle.getComprobanteCompra().getSerie());
            detalleSrl.setAnioEmisionDuaOdsiComprobante(detalle.getComprobanteCompra().getAnioEmisionDuaOdsi());
            detalleSrl.setFechaEmisionComprobante(detalle.getComprobanteCompra().getFechaEmision());
            detalleSrl.setFechaVencimientoOpagoComprobante(detalle.getComprobanteCompra().getFechaVencimientoOpago());
            detalleSrl.setBaseComprobante(detalle.getComprobanteCompra().getBase());
            detalleSrl.setIgvComprobante(detalle.getComprobanteCompra().getIgv());
            detalleSrl.setImporteTotalComprobante(detalle.getComprobanteCompra().getImporteTotal());
            detalleSrl.setNumeroTipoComprobante(detalle.getComprobanteCompra().getTipoComprobantePagoODocumento().getNumero());
            detalleSrl.setNumeroTipoDocIdentidadProveedor(detalle.getComprobanteCompra().getProveedor().getTipoDocumentoIdentidad().getNumero());
            detalleSrl.setNumeroDocIdentidadProveedor(detalle.getComprobanteCompra().getProveedor().getNumeroDocumentoIdentidad());
            detalleSrl.setRazonSocialProveedor(detalle.getComprobanteCompra().getProveedor().getRazonSocial());

            if (detalle.getComprobanteCompraReferenciado() != null) {
                detalleSrl.setIdComprobanteCompraReferenciado(detalle.getComprobanteCompraReferenciado().getIdComprobanteCompra());
                detalleSrl.setFechaComprobanteCompraReferenciado(detalle.getComprobanteCompraReferenciado().getFechaEmision());
                detalleSrl.setNumeroTipoComprobanteCompraReferenciado(detalle.getComprobanteCompraReferenciado().getTipoComprobantePagoODocumento().getNumero());
                detalleSrl.setSerieComprobanteCompraReferenciado(detalle.getComprobanteCompraReferenciado().getSerie());
                detalleSrl.setNumeroComprobanteCompraReferenciado(detalle.getComprobanteCompraReferenciado().getNumero());
            }

            this.getListaDetallesRCSrl().add(detalleSrl);
        }

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        this.setSuccess(true);
        return "listDetallesRC";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<DetalleLibroRegistroVentasSerializable> getListaDetallesRVSrl() {
        return listaDetallesRVSrl;
    }

    public void setListaDetallesRVSrl(List<DetalleLibroRegistroVentasSerializable> listaDetallesRVSrl) {
        this.listaDetallesRVSrl = listaDetallesRVSrl;
    }

    public List<DetalleLibroRegistroComprasSerializable> getListaDetallesRCSrl() {
        return listaDetallesRCSrl;
    }

    public void setListaDetallesRCSrl(List<DetalleLibroRegistroComprasSerializable> listaDetallesRCSrl) {
        this.listaDetallesRCSrl = listaDetallesRCSrl;
    }

    @Override
    public void setUser(Object user) {
        this.user = user;
    }
}
