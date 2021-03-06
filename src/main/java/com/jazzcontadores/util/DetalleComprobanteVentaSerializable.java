/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.util;

import java.math.BigDecimal;

/**
 *
 * @author Venegas
 */
public class DetalleComprobanteVentaSerializable {

    private Integer idDetalleComprobanteVenta;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    // producto
    private Integer idProductoVentas;
    private String nombreProducto;
    private BigDecimal precioProducto;
    private String unidadDeMedidaProducto;

    public DetalleComprobanteVentaSerializable() {
    }

    public Integer getIdDetalleComprobanteVenta() {
        return idDetalleComprobanteVenta;
    }

    public void setIdDetalleComprobanteVenta(Integer idDetalleComprobanteVenta) {
        this.idDetalleComprobanteVenta = idDetalleComprobanteVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getIdProductoVentas() {
        return idProductoVentas;
    }

    public void setIdProductoVentas(Integer idProductoVentas) {
        this.idProductoVentas = idProductoVentas;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(BigDecimal precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getUnidadDeMedidaProducto() {
        return unidadDeMedidaProducto;
    }

    public void setUnidadDeMedidaProducto(String unidadDeMedidaProducto) {
        this.unidadDeMedidaProducto = unidadDeMedidaProducto;
    }
}
