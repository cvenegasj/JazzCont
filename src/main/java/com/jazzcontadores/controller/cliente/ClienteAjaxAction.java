/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.cliente;

import com.jazzcontadores.model.entities.Contacto;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.HibernateUtil;
import com.jazzcontadores.util.UserAware;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author Venegas
 */
public class ClienteAjaxAction extends ActionSupport implements UserAware {
    
    Object user;
    private String param;
    private String paramRetorno;
    private String mensaje; // mensaje para las acciones de edit 
    
    public String editEmailSecundarioContacto() {
        String nuevoParam = this.getParam().trim();

        if (!nuevoParam.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            this.setMensaje("error");
            return ERROR;
        }

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);        
        Contacto c = factory.getContactoDAO().findById(((Contacto) user).getIdContacto());
        c.setEmailSecundario(nuevoParam);        

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(c.getEmailSecundario());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        // se actualiza el objeto de la sesión
        ((Contacto) user).setEmailSecundario(c.getEmailSecundario());
        return "edit";
    }
    
    public String editEmailFacebookContacto() {
        String nuevoParam = this.getParam().trim();

        if (!nuevoParam.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            this.setMensaje("error");
            return ERROR;
        }

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);        
        Contacto c = factory.getContactoDAO().findById(((Contacto) user).getIdContacto());
        c.setEmailFacebook(nuevoParam);       

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(c.getEmailFacebook());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        // se actualiza el objeto de la sesión
        ((Contacto) user).setEmailFacebook(c.getEmailFacebook());
        return "edit";
    }

    public String getParamRetorno() {
        return paramRetorno;
    }

    public void setParamRetorno(String paramRetorno) {
        this.paramRetorno = paramRetorno;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public void setUser(Object user) {
        this.user = user;
    }
}
