/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.util;

/**
 *
 * @author Venegas
 */
public class CompradorSerializable {

    private Integer idComprador;
    private String razonSocialONombres;
    private String numeroDocumentoIdentidad;
    private String tipoDocNumero;
    private String tipoDocDescripcion;

    public CompradorSerializable() {
    }

    public Integer getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(Integer idComprador) {
        this.idComprador = idComprador;
    }

    public String getRazonSocialONombres() {
        return razonSocialONombres;
    }

    public void setRazonSocialONombres(String razonSocialONombres) {
        this.razonSocialONombres = razonSocialONombres;
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
