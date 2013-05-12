/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.daoimpl;

import com.jazzcontadores.model.dao.CuentaContableDAO;
import com.jazzcontadores.model.entities.CuentaContable;
import java.util.List;

/**
 *
 * @author Venegas
 */
public class CuentaContableDAOImpl extends GenericDAOImpl<CuentaContable, String>
        implements CuentaContableDAO {

    @Override
    public List<CuentaContable> findByTerm(String term) {
        List<CuentaContable> listaCuentas;

        listaCuentas = (List<CuentaContable>) getSession().createQuery("from CuentaContable c "
                + "where c.numero like :term "
                + "or c.denominacion like :term "
                + "order by c.numero")
                .setString("term", "%" + term + "%").list();

        return listaCuentas;
    }
}
