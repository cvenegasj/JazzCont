/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.dao;

import com.jazzcontadores.model.entities.Contador;

/**
 *
 * @author Venegas
 */
public interface ContadorDAO extends GenericDAO<Contador, Integer> {

    Contador findByEmail(String email);
}
