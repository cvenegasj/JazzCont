/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.struts2.json.annotations.JSON;

/**
 *
 * @author Venegas
 */
public class ComprobanteVentaSerializable {

    private Integer idComprobanteVenta;
    private Byte numeroTipoCompPago;
    private String descTipoCompPago;
    private String numero;
    private String serie;
    private Date fechaEmision;
    private Date fechaVencimiento;
    private BigDecimal base;
    private BigDecimal igv;
    private BigDecimal importeTotal;
    // comprador
    private String razonSocialONombresComprador;
    private String numeroDocIdentidadComprador;
    private String numeroTipoDocIdentidadComprador;
    // detalles
    private List<DetalleComprobanteVentaSerializable> detallesComprobanteVenta = new ArrayList<DetalleComprobanteVentaSerializable>();

    public ComprobanteVentaSerializable() {
    }

    public Integer getIdComprobanteVenta() {
        return idComprobanteVenta;
    }

    public void setIdComprobanteVenta(Integer idComprobanteVenta) {
        this.idComprobanteVenta = idComprobanteVenta;
    }

    public Byte getNumeroTipoCompPago() {
        return numeroTipoCompPago;
    }

    public void setNumeroTipoCompPago(Byte numeroTipoCompPago) {
        this.numeroTipoCompPago = numeroTipoCompPago;
    }

    public String getDescTipoCompPago() {
        return descTipoCompPago;
    }

    public void setDescTipoCompPago(String descTipoCompPago) {
        this.descTipoCompPago = descTipoCompPago;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    @JSON(format = "dd/MM/yyyy")
    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    @JSON(format = "dd/MM/yyyy")
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getBase() {
        return base;
    }

    public void setBase(BigDecimal base) {
        this.base = base;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getRazonSocialONombresComprador() {
        return razonSocialONombresComprador;
    }

    public void setRazonSocialONombresComprador(String razonSocialONombresComprador) {
        this.razonSocialONombresComprador = razonSocialONombresComprador;
    }

    public String getNumeroDocIdentidadComprador() {
        return numeroDocIdentidadComprador;
    }

    public void setNumeroDocIdentidadComprador(String numeroDocIdentidadComprador) {
        this.numeroDocIdentidadComprador = numeroDocIdentidadComprador;
    }

    public String getNumeroTipoDocIdentidadComprador() {
        return numeroTipoDocIdentidadComprador;
    }

    public void setNumeroTipoDocIdentidadComprador(String numeroTipoDocIdentidadComprador) {
        this.numeroTipoDocIdentidadComprador = numeroTipoDocIdentidadComprador;
    }

    public List<DetalleComprobanteVentaSerializable> getDetallesComprobanteVenta() {
        return detallesComprobanteVenta;
    }

    public void setDetallesComprobanteVenta(List<DetalleComprobanteVentaSerializable> detallesComprobanteVenta) {
        this.detallesComprobanteVenta = detallesComprobanteVenta;
    }
}
