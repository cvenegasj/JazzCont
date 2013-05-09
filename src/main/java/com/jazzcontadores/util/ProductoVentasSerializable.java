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
public class ProductoVentasSerializable {

    private Integer idProductoVentas;
    private int stock;
    private String nombre;
    private BigDecimal precio;
    private String unidadDeMedida;

    public ProductoVentasSerializable() {
    }

    public Integer getIdProductoVentas() {
        return idProductoVentas;
    }

    public void setIdProductoVentas(Integer idProductoVentas) {
        this.idProductoVentas = idProductoVentas;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getUnidadDeMedida() {
        return unidadDeMedida;
    }

    public void setUnidadDeMedida(String unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }
}
