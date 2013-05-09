/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.daoimpl;

import com.jazzcontadores.model.dao.ContactoDAO;
import com.jazzcontadores.model.entities.Contacto;

/**
 *
 * @author Venegas
 */
public class ContactoDAOImpl extends GenericDAOImpl<Contacto, Integer>
        implements ContactoDAO {

    @Override
    public Contacto findByEmail(String email) {
        Contacto contacto;

        contacto = (Contacto) getSession().createQuery("from Contacto c "
                + "where c.emailPrincipal = :email")
                .setString("email", email)
                .uniqueResult();

        return contacto;
    }
}
