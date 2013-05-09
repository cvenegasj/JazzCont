/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.dao;

import com.jazzcontadores.model.entities.LibroRegistroCompras;
import java.util.Date;

/**
 *
 * @author Venegas
 */
public interface LibroRegistroComprasDAO extends GenericDAO<LibroRegistroCompras, Integer> {

    LibroRegistroCompras findByPeriodo(long ruc, Date periodo);
}
