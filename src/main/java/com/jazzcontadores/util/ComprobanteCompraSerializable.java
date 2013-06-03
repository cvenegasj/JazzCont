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
public class ComprobanteCompraSerializable {

    private Integer idComprobanteCompra;
    private Byte numeroTipoCompPago;
    private String descTipoCompPago;
    private String numero;
    private String serie;
    private String anioEmisionDuaOdsi;
    private Date fechaEmision;
    private Date fechaVencimientoOpago;
    private BigDecimal base;
    private BigDecimal igv;
    private BigDecimal importeTotal;
    // CodigoAduana
    private Short numeroCodigoAduana;
    private String descripcionCodigoAduana;
    // proveedor
    private String razonSocialProveedor;
    private String numeroDocIdentidadProveedor;
    private String numeroTipoDocIdentidadProveedor;
    // detalles
    private List<DetalleComprobanteCompraSerializable> detallesComprobanteCompra = new ArrayList<DetalleComprobanteCompraSerializable>();

    public ComprobanteCompraSerializable() {
    }

    public Integer getIdComprobanteCompra() {
        return idComprobanteCompra;
    }

    public void setIdComprobanteCompra(Integer idComprobanteCompra) {
        this.idComprobanteCompra = idComprobanteCompra;
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

    public String getAnioEmisionDuaOdsi() {
        return anioEmisionDuaOdsi;
    }

    public void setAnioEmisionDuaOdsi(String anioEmisionDuaOdsi) {
        this.anioEmisionDuaOdsi = anioEmisionDuaOdsi;
    }

    @JSON(format = "dd/MM/yyyy")
    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    @JSON(format = "dd/MM/yyyy")
    public Date getFechaVencimientoOpago() {
        return fechaVencimientoOpago;
    }

    public void setFechaVencimientoOpago(Date fechaVencimientoOpago) {
        this.fechaVencimientoOpago = fechaVencimientoOpago;
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

    public Short getNumeroCodigoAduana() {
        return numeroCodigoAduana;
    }

    public void setNumeroCodigoAduana(Short numeroCodigoAduana) {
        this.numeroCodigoAduana = numeroCodigoAduana;
    }

    public String getDescripcionCodigoAduana() {
        return descripcionCodigoAduana;
    }

    public void setDescripcionCodigoAduana(String descripcionCodigoAduana) {
        this.descripcionCodigoAduana = descripcionCodigoAduana;
    }

    public String getRazonSocialProveedor() {
        return razonSocialProveedor;
    }

    public void setRazonSocialProveedor(String razonSocialProveedor) {
        this.razonSocialProveedor = razonSocialProveedor;
    }

    public String getNumeroDocIdentidadProveedor() {
        return numeroDocIdentidadProveedor;
    }

    public void setNumeroDocIdentidadProveedor(String numeroDocIdentidadProveedor) {
        this.numeroDocIdentidadProveedor = numeroDocIdentidadProveedor;
    }

    public String getNumeroTipoDocIdentidadProveedor() {
        return numeroTipoDocIdentidadProveedor;
    }

    public void setNumeroTipoDocIdentidadProveedor(String numeroTipoDocIdentidadProveedor) {
        this.numeroTipoDocIdentidadProveedor = numeroTipoDocIdentidadProveedor;
    }

    public List<DetalleComprobanteCompraSerializable> getDetallesComprobanteCompra() {
        return detallesComprobanteCompra;
    }

    public void setDetallesComprobanteCompra(List<DetalleComprobanteCompraSerializable> detallesComprobanteCompra) {
        this.detallesComprobanteCompra = detallesComprobanteCompra;
    }
}
