/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.dao;

import com.jazzcontadores.model.entities.LibroRegistroVentas;
import java.util.Date;

/**
 *
 * @author Venegas
 */
public interface LibroRegistroVentasDAO extends GenericDAO<LibroRegistroVentas, Integer> {

    LibroRegistroVentas findByPeriodo(long ruc, Date periodo);
}
