/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author Venegas
 */
public class ContenedorComprobantesVentaAction extends ActionSupport {
    
    private long ruc;
    private String periodo;
    
    public String show() {
        return "show";
    }

    public long getRuc() {
        return ruc;
    }

    public void setRuc(long ruc) {
        this.ruc = ruc;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
}
