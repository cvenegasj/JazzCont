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
public class ComprobanteVentaAction extends ActionSupport {
    
    private long ruc;        
    
    public String add() {
        // verificar que el ruc pertenezca al nrus
        
        
        return "add";
    }
    
    public String save() {
        
        return "save";
    }

    public long getRuc() {
        return ruc;
    }

    public void setRuc(long ruc) {
        this.ruc = ruc;
    }
    
}
