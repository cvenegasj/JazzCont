/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.util;

/**
 *
 * @author Venegas
 */
public class CuentaContableSerializable {

    private String numero;
    private String denominacion;
    private Byte clasificacionCuenta;
    private String nombreResumido;

    public CuentaContableSerializable() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Byte getClasificacionCuenta() {
        return clasificacionCuenta;
    }

    public void setClasificacionCuenta(Byte clasificacionCuenta) {
        this.clasificacionCuenta = clasificacionCuenta;
    }

    public String getNombreResumido() {
        return nombreResumido;
    }

    public void setNombreResumido(String nombreResumido) {
        this.nombreResumido = nombreResumido;
    }
}
