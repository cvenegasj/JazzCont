/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.util;

/**
 *
 * @author Venegas
 */
public class ProveedorSerializable {

    private Integer idProveedor;
    private String razonSocial;
    private String numeroDocumentoIdentidad;
    private String tipoDocNumero;
    private String tipoDocDescripcion;

    public ProveedorSerializable() {
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNumeroDocumentoIdentidad() {
        return numeroDocumentoIdentidad;
    }

    public void setNumeroDocumentoIdentidad(String numeroDocumentoIdentidad) {
        this.numeroDocumentoIdentidad = numeroDocumentoIdentidad;
    }

    public String getTipoDocNumero() {
        return tipoDocNumero;
    }

    public void setTipoDocNumero(String tipoDocNumero) {
        this.tipoDocNumero = tipoDocNumero;
    }

    public String getTipoDocDescripcion() {
        return tipoDocDescripcion;
    }

    public void setTipoDocDescripcion(String tipoDocDescripcion) {
        this.tipoDocDescripcion = tipoDocDescripcion;
    }
}
