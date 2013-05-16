/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.jazzcontadores.model.entities.DetalleLibroRegistroCompras;
import com.jazzcontadores.model.entities.DetalleLibroRegistroVentas;
import com.jazzcontadores.model.entities.LibroRegistroCompras;
import com.jazzcontadores.model.entities.LibroRegistroVentas;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.DetalleLibroRegistroComprasSerializable;
import com.jazzcontadores.util.DetalleLibroRegistroVentasSerializable;
import com.jazzcontadores.util.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Venegas
 */
public class LibrosAjaxAction extends ActionSupport {

    private boolean success; // for ext
    private long ruc;
    private int idLibro;
    private List<DetalleLibroRegistroVentasSerializable> listaDetallesRVSrl = new ArrayList<DetalleLibroRegistroVentasSerializable>();
    private List<DetalleLibroRegistroComprasSerializable> listaDetallesRCSrl = new ArrayList<DetalleLibroRegistroComprasSerializable>();

    public String listDetallesRV() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);

        LibroRegistroVentas libroVentas = factory.getLibroRegistroVentasDAO()
                .findByIdAndEmpresa(this.getRuc(), this.getIdLibro());

        for (DetalleLibroRegistroVentas detalle : libroVentas.getDetallesLibroRegistroVentas()) {
            DetalleLibroRegistroVentasSerializable detalleSrl = new DetalleLibroRegistroVentasSerializable();
            detalleSrl.setIdDetalleLibroRegistroVentas(detalle.getIdDetalleLibroRegistroVentas());
            detalleSrl.setNumeroCorrelativo(detalle.getNumeroCorrelativo());
            detalleSrl.setValorFacturadoExportacion(detalle.getValorFacturadoExportacion());
            detalleSrl.setBaseImponibleOpGravada(detalle.getBaseImponibleOpGravada());
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
            detalleSrl.setEstadoOportunidadDeAnotación(detalle.getEstadoOportunidadDeAnotación());
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

        LibroRegistroCompras libroCompras = factory.getLibroRegistroComprasDAO()
                .findByIdAndEmpresa(this.getRuc(), this.getIdLibro());

        for (DetalleLibroRegistroCompras detalle : libroCompras.getDetallesLibroRegistroCompras()) {
            DetalleLibroRegistroComprasSerializable detalleSrl = new DetalleLibroRegistroComprasSerializable();
            detalleSrl.setIdDetalleLibroRegistroCompras(detalle.getIdDetalleLibroRegistroCompras());
            detalleSrl.setNumeroCorrelativo(detalle.getNumeroCorrelativo());
            detalleSrl.setTipoAdquisicionGravada(detalle.getTipoAdquisicionGravada());
            detalleSrl.setValorAdquisicionesNoGravadas(detalle.getValorAdquisicionesNoGravadas());
            detalleSrl.setIsc(detalle.getIsc());
            detalleSrl.setOtrosTributosYcargos(detalle.getOtrosTributosYcargos());
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

    public long getRuc() {
        return ruc;
    }

    public void setRuc(long ruc) {
        this.ruc = ruc;
    }

    public List<DetalleLibroRegistroVentasSerializable> getListaDetallesRVSrl() {
        return listaDetallesRVSrl;
    }

    public void setListaDetallesRVSrl(List<DetalleLibroRegistroVentasSerializable> listaDetallesRVSrl) {
        this.listaDetallesRVSrl = listaDetallesRVSrl;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public List<DetalleLibroRegistroComprasSerializable> getListaDetallesRCSrl() {
        return listaDetallesRCSrl;
    }

    public void setListaDetallesRCSrl(List<DetalleLibroRegistroComprasSerializable> listaDetallesRCSrl) {
        this.listaDetallesRCSrl = listaDetallesRCSrl;
    }
}
