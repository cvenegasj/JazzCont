/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.dao;

import com.jazzcontadores.model.entities.CuentaContable;
import java.util.List;

/**
 *
 * @author Venegas
 */
public interface CuentaContableDAO extends GenericDAO<CuentaContable, String> {

    List<CuentaContable> findByTerm(String term);
}
