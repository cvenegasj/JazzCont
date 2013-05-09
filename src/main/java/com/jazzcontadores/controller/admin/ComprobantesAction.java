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
public class ComprobantesAction extends ActionSupport {
    
    private long ruc;
    
    @Override
    public String execute() {
        // hacer algo con el ruc ...
           
        
        return SUCCESS;
    }

    public long getRuc() {
        return ruc;
    }

    public void setRuc(long ruc) {
        this.ruc = ruc;
    }
    
}