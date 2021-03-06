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
public class DetalleLibroRegistroVentasSerializable {

    private Integer idDetalleLibroRegistroVentas;
    private Integer numeroCorrelativo;
    private BigDecimal valorFacturadoExportacion;
    private BigDecimal baseImponibleOpGravada;
    private BigDecimal totalOperacionExonerada;
    private BigDecimal totalOperacionInafecta;
    private BigDecimal isc;
    private BigDecimal igv_ipm;
    private BigDecimal otrosTributos;
    private BigDecimal importeTotal;
    private Long numeroFinalTicketOcintaDelDia;
    private BigDecimal tipoCambio;
    private BigDecimal baseImponibleArrozPilado;
    private BigDecimal impuestoVentasArrozPilado;
    private String estadoOportunidadDeAnotación;
    private Date fechaHoraRegistro;
    // comprobanteVenta
    private Integer idComprobanteVenta;
    private Date fechaEmisionComprobante;
    private Date fechaVencimientoComprobante;
    private BigDecimal baseComprobante;
    private BigDecimal igvComprobante;
    private BigDecimal importeTotalComprobante;
    private Byte numeroTipoComprobante;
    private String serieComprobante;
    private String numeroComprobante;
    private String numeroTipoDocIdentidadComprador;
    private String numeroDocIdentidadComprador;
    private String razonSocialONombresComprador;
    // comprobanteReferenciado
    private Integer idComprobanteVentaReferenciado;
    private Date fechaComprobanteVentaReferenciado;
    private Byte numeroTipoComprobanteVentaReferenciado;
    private String serieComprobanteVentaReferenciado;
    private String numeroComprobanteVentaReferenciado;

    public DetalleLibroRegistroVentasSerializable() {
    }

    public Integer getIdDetalleLibroRegistroVentas() {
        return idDetalleLibroRegistroVentas;
    }

    public void setIdDetalleLibroRegistroVentas(Integer idDetalleLibroRegistroVentas) {
        this.idDetalleLibroRegistroVentas = idDetalleLibroRegistroVentas;
    }

    public Integer getNumeroCorrelativo() {
        return numeroCorrelativo;
    }

    public void setNumeroCorrelativo(Integer numeroCorrelativo) {
        this.numeroCorrelativo = numeroCorrelativo;
    }

    public BigDecimal getValorFacturadoExportacion() {
        return valorFacturadoExportacion;
    }

    public void setValorFacturadoExportacion(BigDecimal valorFacturadoExportacion) {
        this.valorFacturadoExportacion = valorFacturadoExportacion;
    }

    public BigDecimal getBaseImponibleOpGravada() {
        return baseImponibleOpGravada;
    }

    public void setBaseImponibleOpGravada(BigDecimal baseImponibleOpGravada) {
        this.baseImponibleOpGravada = baseImponibleOpGravada;
    }

    public BigDecimal getTotalOperacionExonerada() {
        return totalOperacionExonerada;
    }

    public void setTotalOperacionExonerada(BigDecimal totalOperacionExonerada) {
        this.totalOperacionExonerada = totalOperacionExonerada;
    }

    public BigDecimal getTotalOperacionInafecta() {
        return totalOperacionInafecta;
    }

    public void setTotalOperacionInafecta(BigDecimal totalOperacionInafecta) {
        this.totalOperacionInafecta = totalOperacionInafecta;
    }

    public BigDecimal getIsc() {
        return isc;
    }

    public void setIsc(BigDecimal isc) {
        this.isc = isc;
    }

    public BigDecimal getIgv_ipm() {
        return igv_ipm;
    }

    public void setIgv_ipm(BigDecimal igv_ipm) {
        this.igv_ipm = igv_ipm;
    }

    public BigDecimal getOtrosTributos() {
        return otrosTributos;
    }

    public void setOtrosTributos(BigDecimal otrosTributos) {
        this.otrosTributos = otrosTributos;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Long getNumeroFinalTicketOcintaDelDia() {
        return numeroFinalTicketOcintaDelDia;
    }

    public void setNumeroFinalTicketOcintaDelDia(Long numeroFinalTicketOcintaDelDia) {
        this.numeroFinalTicketOcintaDelDia = numeroFinalTicketOcintaDelDia;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public BigDecimal getBaseImponibleArrozPilado() {
        return baseImponibleArrozPilado;
    }

    public void setBaseImponibleArrozPilado(BigDecimal baseImponibleArrozPilado) {
        this.baseImponibleArrozPilado = baseImponibleArrozPilado;
    }

    public BigDecimal getImpuestoVentasArrozPilado() {
        return impuestoVentasArrozPilado;
    }

    public void setImpuestoVentasArrozPilado(BigDecimal impuestoVentasArrozPilado) {
        this.impuestoVentasArrozPilado = impuestoVentasArrozPilado;
    }

    public String getEstadoOportunidadDeAnotación() {
        return estadoOportunidadDeAnotación;
    }

    public void setEstadoOportunidadDeAnotación(String estadoOportunidadDeAnotación) {
        this.estadoOportunidadDeAnotación = estadoOportunidadDeAnotación;
    }

    public Date getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(Date fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public Integer getIdComprobanteVenta() {
        return idComprobanteVenta;
    }

    public void setIdComprobanteVenta(Integer idComprobanteVenta) {
        this.idComprobanteVenta = idComprobanteVenta;
    }

    public Byte getNumeroTipoComprobante() {
        return numeroTipoComprobante;
    }

    public void setNumeroTipoComprobante(Byte numeroTipoComprobante) {
        this.numeroTipoComprobante = numeroTipoComprobante;
    }

    public String getSerieComprobante() {
        return serieComprobante;
    }

    public void setSerieComprobante(String serieComprobante) {
        this.serieComprobante = serieComprobante;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public String getNumeroTipoDocIdentidadComprador() {
        return numeroTipoDocIdentidadComprador;
    }

    public void setNumeroTipoDocIdentidadComprador(String numeroTipoDocIdentidadComprador) {
        this.numeroTipoDocIdentidadComprador = numeroTipoDocIdentidadComprador;
    }

    public String getNumeroDocIdentidadComprador() {
        return numeroDocIdentidadComprador;
    }

    public void setNumeroDocIdentidadComprador(String numeroDocIdentidadComprador) {
        this.numeroDocIdentidadComprador = numeroDocIdentidadComprador;
    }

    public String getRazonSocialONombresComprador() {
        return razonSocialONombresComprador;
    }

    public void setRazonSocialONombresComprador(String razonSocialONombresComprador) {
        this.razonSocialONombresComprador = razonSocialONombresComprador;
    }

    public Integer getIdComprobanteVentaReferenciado() {
        return idComprobanteVentaReferenciado;
    }

    public void setIdComprobanteVentaReferenciado(Integer idComprobanteVentaReferenciado) {
        this.idComprobanteVentaReferenciado = idComprobanteVentaReferenciado;
    }

    @JSON(format = "dd/MM/yyyy")
    public Date getFechaComprobanteVentaReferenciado() {
        return fechaComprobanteVentaReferenciado;
    }

    public void setFechaComprobanteVentaReferenciado(Date fechaComprobanteVentaReferenciado) {
        this.fechaComprobanteVentaReferenciado = fechaComprobanteVentaReferenciado;
    }

    public Byte getNumeroTipoComprobanteVentaReferenciado() {
        return numeroTipoComprobanteVentaReferenciado;
    }

    public void setNumeroTipoComprobanteVentaReferenciado(Byte numeroTipoComprobanteVentaReferenciado) {
        this.numeroTipoComprobanteVentaReferenciado = numeroTipoComprobanteVentaReferenciado;
    }

    public String getSerieComprobanteVentaReferenciado() {
        return serieComprobanteVentaReferenciado;
    }

    public void setSerieComprobanteVentaReferenciado(String serieComprobanteVentaReferenciado) {
        this.serieComprobanteVentaReferenciado = serieComprobanteVentaReferenciado;
    }

    public String getNumeroComprobanteVentaReferenciado() {
        return numeroComprobanteVentaReferenciado;
    }

    public void setNumeroComprobanteVentaReferenciado(String numeroComprobanteVentaReferenciado) {
        this.numeroComprobanteVentaReferenciado = numeroComprobanteVentaReferenciado;
    }

    @JSON(format = "dd/MM/yyyy")
    public Date getFechaEmisionComprobante() {
        return fechaEmisionComprobante;
    }

    public void setFechaEmisionComprobante(Date fechaEmisionComprobante) {
        this.fechaEmisionComprobante = fechaEmisionComprobante;
    }

    @JSON(format = "dd/MM/yyyy")
    public Date getFechaVencimientoComprobante() {
        return fechaVencimientoComprobante;
    }

    public void setFechaVencimientoComprobante(Date fechaVencimientoComprobante) {
        this.fechaVencimientoComprobante = fechaVencimientoComprobante;
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
}
