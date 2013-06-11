/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.util;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.struts2.json.annotations.JSON;

/**
 *
 * @author Venegas
 */
public class DetalleLibroRegistroComprasSerializable {

    private Integer idDetalleLibroRegistroCompras;
    private Integer numeroCorrelativo;
    private String destinoAdquisicionGravada;
    private BigDecimal baseImponible1;
    private BigDecimal igv1;
    private BigDecimal baseImponible2;
    private BigDecimal igv2;
    private BigDecimal baseImponible3;
    private BigDecimal igv3;    
    private BigDecimal tipoCambio;
    private Long numeroCompPagoSujNoDom;
    private Long numeroConstDepDetraccion;
    private Date fechaEmisionConstDepDetraccion;
    private Long numeroFinalOperDiariasSinCredFiscal;
    private Boolean marcaComprobanteSujetoAretencion;
    private Date fechaHoraRegistro;
    // comprobante compra
    private Integer idComprobanteCompra;
    private String numeroComprobante;
    private String serieComprobante;
    private String anioEmisionDuaOdsiComprobante;
    private Date fechaEmisionComprobante;
    private Date fechaVencimientoOpagoComprobante;
    private BigDecimal valorAdquisicionesNoGravadas;
    private BigDecimal isc;
    private BigDecimal otrosTributosYcargos;
    private BigDecimal importeTotal;
    private BigDecimal baseComprobante;
    private BigDecimal igvComprobante;
    private BigDecimal importeTotalComprobante;
    private Byte numeroTipoComprobante;
    private String numeroTipoDocIdentidadProveedor;
    private String numeroDocIdentidadProveedor;
    private String razonSocialProveedor;
    // comprobante referenciado
    private Integer idComprobanteCompraReferenciado;
    private Date fechaComprobanteCompraReferenciado;
    private Byte numeroTipoComprobanteCompraReferenciado;
    private String serieComprobanteCompraReferenciado;
    private String numeroComprobanteCompraReferenciado;

    public DetalleLibroRegistroComprasSerializable() {
    }

    public Integer getIdDetalleLibroRegistroCompras() {
        return idDetalleLibroRegistroCompras;
    }

    public void setIdDetalleLibroRegistroCompras(Integer idDetalleLibroRegistroCompras) {
        this.idDetalleLibroRegistroCompras = idDetalleLibroRegistroCompras;
    }

    public Integer getNumeroCorrelativo() {
        return numeroCorrelativo;
    }

    public void setNumeroCorrelativo(Integer numeroCorrelativo) {
        this.numeroCorrelativo = numeroCorrelativo;
    }

    public String getDestinoAdquisicionGravada() {
        return destinoAdquisicionGravada;
    }

    public void setDestinoAdquisicionGravada(String destinoAdquisicionGravada) {
        this.destinoAdquisicionGravada = destinoAdquisicionGravada;
    }

    public BigDecimal getValorAdquisicionesNoGravadas() {
        return valorAdquisicionesNoGravadas;
    }

    public void setValorAdquisicionesNoGravadas(BigDecimal valorAdquisicionesNoGravadas) {
        this.valorAdquisicionesNoGravadas = valorAdquisicionesNoGravadas;
    }

    public BigDecimal getIsc() {
        return isc;
    }

    public void setIsc(BigDecimal isc) {
        this.isc = isc;
    }

    public BigDecimal getOtrosTributosYcargos() {
        return otrosTributosYcargos;
    }

    public void setOtrosTributosYcargos(BigDecimal otrosTributosYcargos) {
        this.otrosTributosYcargos = otrosTributosYcargos;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public Long getNumeroCompPagoSujNoDom() {
        return numeroCompPagoSujNoDom;
    }

    public void setNumeroCompPagoSujNoDom(Long numeroCompPagoSujNoDom) {
        this.numeroCompPagoSujNoDom = numeroCompPagoSujNoDom;
    }

    public Long getNumeroConstDepDetraccion() {
        return numeroConstDepDetraccion;
    }

    public void setNumeroConstDepDetraccion(Long numeroConstDepDetraccion) {
        this.numeroConstDepDetraccion = numeroConstDepDetraccion;
    }

    @JSON(format = "dd/MM/yyyy")
    public Date getFechaEmisionConstDepDetraccion() {
        return fechaEmisionConstDepDetraccion;
    }

    public void setFechaEmisionConstDepDetraccion(Date fechaEmisionConstDepDetraccion) {
        this.fechaEmisionConstDepDetraccion = fechaEmisionConstDepDetraccion;
    }

    public Long getNumeroFinalOperDiariasSinCredFiscal() {
        return numeroFinalOperDiariasSinCredFiscal;
    }

    public void setNumeroFinalOperDiariasSinCredFiscal(Long numeroFinalOperDiariasSinCredFiscal) {
        this.numeroFinalOperDiariasSinCredFiscal = numeroFinalOperDiariasSinCredFiscal;
    }

    public Boolean getMarcaComprobanteSujetoAretencion() {
        return marcaComprobanteSujetoAretencion;
    }

    public void setMarcaComprobanteSujetoAretencion(Boolean marcaComprobanteSujetoAretencion) {
        this.marcaComprobanteSujetoAretencion = marcaComprobanteSujetoAretencion;
    }

    public Date getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(Date fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public Integer getIdComprobanteCompra() {
        return idComprobanteCompra;
    }

    public void setIdComprobanteCompra(Integer idComprobanteCompra) {
        this.idComprobanteCompra = idComprobanteCompra;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public String getSerieComprobante() {
        return serieComprobante;
    }

    public void setSerieComprobante(String serieComprobante) {
        this.serieComprobante = serieComprobante;
    }

    public String getAnioEmisionDuaOdsiComprobante() {
        return anioEmisionDuaOdsiComprobante;
    }

    public void setAnioEmisionDuaOdsiComprobante(String anioEmisionDuaOdsiComprobante) {
        this.anioEmisionDuaOdsiComprobante = anioEmisionDuaOdsiComprobante;
    }

    @JSON(format = "dd/MM/yyyy")
    public Date getFechaEmisionComprobante() {
        return fechaEmisionComprobante;
    }

    public void setFechaEmisionComprobante(Date fechaEmisionComprobante) {
        this.fechaEmisionComprobante = fechaEmisionComprobante;
    }

    @JSON(format = "dd/MM/yyyy")
    public Date getFechaVencimientoOpagoComprobante() {
        return fechaVencimientoOpagoComprobante;
    }

    public void setFechaVencimientoOpagoComprobante(Date fechaVencimientoOpagoComprobante) {
        this.fechaVencimientoOpagoComprobante = fechaVencimientoOpagoComprobante;
    }

    public BigDecimal getBaseComprobante() {
        return baseComprobante;
    }

    public void setBaseComprobante(BigDecimal baseComprobante) {
        this.baseComprobante = baseComprobante;
    }

    public BigDecimal getIgvComprobante() {
        return igvComprobante;
    }

    public void setIgvComprobante(BigDecimal igvComprobante) {
        this.igvComprobante = igvComprobante;
    }

    public BigDecimal getImporteTotalComprobante() {
        return importeTotalComprobante;
    }

    public void setImporteTotalComprobante(BigDecimal importeTotalComprobante) {
        this.importeTotalComprobante = importeTotalComprobante;
    }

    public Byte getNumeroTipoComprobante() {
        return numeroTipoComprobante;
    }

    public void setNumeroTipoComprobante(Byte numeroTipoComprobante) {
        this.numeroTipoComprobante = numeroTipoComprobante;
    }

    public String getNumeroTipoDocIdentidadProveedor() {
        return numeroTipoDocIdentidadProveedor;
    }

    public void setNumeroTipoDocIdentidadProveedor(String numeroTipoDocIdentidadProveedor) {
        this.numeroTipoDocIdentidadProveedor = numeroTipoDocIdentidadProveedor;
    }

    public String getNumeroDocIdentidadProveedor() {
        return numeroDocIdentidadProveedor;
    }

    public void setNumeroDocIdentidadProveedor(String numeroDocIdentidadProveedor) {
        this.numeroDocIdentidadProveedor = numeroDocIdentidadProveedor;
    }

    public String getRazonSocialProveedor() {
        return razonSocialProveedor;
    }

    public void setRazonSocialProveedor(String razonSocialProveedor) {
        this.razonSocialProveedor = razonSocialProveedor;
    }

    public Integer getIdComprobanteCompraReferenciado() {
        return idComprobanteCompraReferenciado;
    }

    public void setIdComprobanteCompraReferenciado(Integer idComprobanteCompraReferenciado) {
        this.idComprobanteCompraReferenciado = idComprobanteCompraReferenciado;
    }

    @JSON(format = "dd/MM/yyyy")
    public Date getFechaComprobanteCompraReferenciado() {
        return fechaComprobanteCompraReferenciado;
    }

    public void setFechaComprobanteCompraReferenciado(Date fechaComprobanteCompraReferenciado) {
        this.fechaComprobanteCompraReferenciado = fechaComprobanteCompraReferenciado;
    }

    public Byte getNumeroTipoComprobanteCompraReferenciado() {
        return numeroTipoComprobanteCompraReferenciado;
    }

    public void setNumeroTipoComprobanteCompraReferenciado(Byte numeroTipoComprobanteCompraReferenciado) {
        this.numeroTipoComprobanteCompraReferenciado = numeroTipoComprobanteCompraReferenciado;
    }

    public String getSerieComprobanteCompraReferenciado() {
        return serieComprobanteCompraReferenciado;
    }

    public void setSerieComprobanteCompraReferenciado(String serieComprobanteCompraReferenciado) {
        this.serieComprobanteCompraReferenciado = serieComprobanteCompraReferenciado;
    }

    public String getNumeroComprobanteCompraReferenciado() {
        return numeroComprobanteCompraReferenciado;
    }

    public void setNumeroComprobanteCompraReferenciado(String numeroComprobanteCompraReferenciado) {
        this.numeroComprobanteCompraReferenciado = numeroComprobanteCompraReferenciado;
    }

    public BigDecimal getBaseImponible1() {
        return baseImponible1;
    }

    public void setBaseImponible1(BigDecimal baseImponible1) {
        this.baseImponible1 = baseImponible1;
    }

    public BigDecimal getIgv1() {
        return igv1;
    }

    public void setIgv1(BigDecimal igv1) {
        this.igv1 = igv1;
    }

    public BigDecimal getBaseImponible2() {
        return baseImponible2;
    }

    public void setBaseImponible2(BigDecimal baseImponible2) {
        this.baseImponible2 = baseImponible2;
    }

    public BigDecimal getIgv2() {
        return igv2;
    }

    public void setIgv2(BigDecimal igv2) {
        this.igv2 = igv2;
    }

    public BigDecimal getBaseImponible3() {
        return baseImponible3;
    }

    public void setBaseImponible3(BigDecimal baseImponible3) {
        this.baseImponible3 = baseImponible3;
    }

    public BigDecimal getIgv3() {
        return igv3;
    }

    public void setIgv3(BigDecimal igv3) {
        this.igv3 = igv3;
    }
}
