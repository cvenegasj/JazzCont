/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Venegas
 */
@Entity
@Table(name = "codigoaduana", catalog = "jazzcontadores")
public class CodigoAduana implements java.io.Serializable {

    private Short numero;
    private String descripcion;

    public CodigoAduana() {
    }

    public CodigoAduana(Short numero, String descripcion) {
        this.numero = numero;
        this.descripcion = descripcion;
    }

    @Id
    @Column(name = "numero", unique = true, nullable = false)
    public Short getNumero() {
        return numero;
    }

    public void setNumero(Short numero) {
        this.numero = numero;
    }

    @Column(name = "descripcion", nullable = false, length = 150)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
