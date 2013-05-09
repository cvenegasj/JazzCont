/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller;

import com.jazzcontadores.extra.JCConstants;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author Venegas
 */
public class AccesoAction extends ActionSupport {

    @Override
    public String execute() {
        //User user = (User) session.get(JCConstants.USER);
        Subject currentUser = SecurityUtils.getSubject();

        if (currentUser.isAuthenticated()) {
            //retorna a la página principal de cada rol
            if (currentUser.hasRole(JCConstants.ROLE_ADMIN)) {
                return JCConstants.SESION_ADMIN;
            } else if (currentUser.hasRole(JCConstants.ROLE_CLIENTE_RG)) {
                return JCConstants.SESION_CLIENTE_RG;
            } else if (currentUser.hasRole(JCConstants.ROLE_CLIENTE_NRUS)) {
                return JCConstants.SESION_CLIENTE_NRUS;
            } else if (currentUser.hasRole(JCConstants.ROLE_CLIENTE_RER)) {
                return JCConstants.SESION_CLIENTE_RER;
            } else {
                // supuestamente esto nunca se ejecutará :3
                return ERROR;
            }
        } else {
            return LOGIN;
        }
    }
}
