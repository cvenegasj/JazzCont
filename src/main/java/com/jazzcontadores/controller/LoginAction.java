/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller;

import com.jazzcontadores.extra.JCConstants;
import com.jazzcontadores.model.dao.ContactoDAO;
import com.jazzcontadores.model.dao.ContadorDAO;
import com.jazzcontadores.model.entities.Contacto;
import com.jazzcontadores.model.entities.Contador;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Venegas
 */
public class LoginAction extends ActionSupport implements SessionAware {

    private String email;
    private String password;
    // For SessionAware
    Map<String, Object> session;

    @Override
    public String execute() throws Exception {

        // Login con Shiro
        UsernamePasswordToken token = new UsernamePasswordToken(getEmail(), getPassword());
        token.setRememberMe(true);
        Subject currentUser = SecurityUtils.getSubject();

        // duración de la sesión: 3 días (72 horas)
        currentUser.getSession(true).setTimeout(259200000);

        try {

            currentUser.login(token);

        } catch (UnknownAccountException uae) {
            addActionError(uae.getMessage());
            return JCConstants.FAILURE;
        } catch (IncorrectCredentialsException ice) {
            addActionError("Contraseña incorrecta. Intente nuevamente");
            return JCConstants.FAILURE;
        } catch (LockedAccountException lae) {
            addActionError("Cuenta bloqueada. Intente con otra cuenta");
            return JCConstants.FAILURE;
        } catch (ExcessiveAttemptsException eae) {
            addActionError("Demasiados intentos");
            return JCConstants.FAILURE;
        } catch (AuthenticationException ae) {
            //unexpected error?
            addActionError(ae.getMessage());
            return JCConstants.FAILURE;
        }



        //logueo correcto (se ha encontrado las credenciales)
        if (currentUser.hasRole(JCConstants.ROLE_ADMIN)) {
            
            //se obtiene el Objeto para almacenar en la sesión struts2
            //llamada a método DAO
            HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);

            ContadorDAO contadorDAO = factory.getContadorDAO();
            Contador contador = contadorDAO.findByEmail(getEmail());
            
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
                                    
            contador.setPassword(String.valueOf(token.getPassword()));
            
            //se inyecta el usuario logueado a la sesion
            session.put(JCConstants.USER, contador);            
            return JCConstants.SESION_ADMIN;
            
        } else if (currentUser.hasRole(JCConstants.ROLE_CLIENTE_RG)) {
            
            //se obtiene el objeto y se almacena en la sesion
            HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

            DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
            ContactoDAO contactoDAO = factory.getContactoDAO();
            Contacto contacto = contactoDAO.findByEmail(getEmail());

            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
            
            contacto.setPassword(String.valueOf(token.getPassword()));
            
            session.put(JCConstants.USER, contacto);            
            return JCConstants.SESION_CLIENTE_RG;
            
        } else if (currentUser.hasRole(JCConstants.ROLE_CLIENTE_NRUS)) {
            
            //se obtiene el objeto y se almacena en la sesion

            return JCConstants.SESION_CLIENTE_NRUS;
            
        } else if (currentUser.hasRole(JCConstants.ROLE_CLIENTE_RER)) {
            
            //se obtiene el objeto y se almacena en la sesion

            return JCConstants.SESION_CLIENTE_RER;
            
        } else {
            
            //No tiene rol definido
            addActionError("El usuario existe, pero no tiene un rol definido");
            return JCConstants.FAILURE;
            
        }

    }

    @Override
    public void validate() {
        if (getEmail() == null || getEmail().trim().equals("")) {
            addActionError("Debe introducir el email");
        }
        if (getPassword() == null || getPassword().trim().equals("")) {
            addActionError("Debe introducir la contraseña");
        }
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
