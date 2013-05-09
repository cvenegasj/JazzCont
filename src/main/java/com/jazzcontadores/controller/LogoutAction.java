/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller;

import com.jazzcontadores.extra.JCConstants;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Venegas
 */

public class LogoutAction extends ActionSupport implements SessionAware {   
    
    // For SessionAware
    Map<String, Object> session;
    
    @Override
    public String execute() throws Exception {
        session.remove(JCConstants.USER);
        //No invalidar sesión de Shiro porque causa excepción al cerrar sesión
        //SecurityUtils.getSubject().logout();
        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
    
}
