/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.cliente;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author Venegas
 */
public class LibroVentasAction extends ActionSupport {

    private int idLibro;

    public String show() {
        return "show";
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
}
