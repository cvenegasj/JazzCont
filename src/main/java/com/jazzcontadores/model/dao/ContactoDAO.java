/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.dao;

import com.jazzcontadores.model.entities.Contacto;

/**
 *
 * @author Venegas
 */
public interface ContactoDAO extends GenericDAO<Contacto, Integer> {

    Contacto findByEmail(String email);
}
