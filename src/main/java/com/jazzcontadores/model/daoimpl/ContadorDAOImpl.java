/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.daoimpl;

import com.jazzcontadores.model.dao.ContadorDAO;
import com.jazzcontadores.model.entities.Contador;

/**
 *
 * @author Venegas
 */
public class ContadorDAOImpl extends GenericDAOImpl<Contador, Integer>
        implements ContadorDAO {

    @Override
    public Contador findByEmail(String email) {
        Contador contador;

        contador = (Contador) getSession().createQuery("from Contador c "
                + "where c.emailPrincipal = :email")
                .setString("email", email)
                .uniqueResult();

        return contador;
    }
}
