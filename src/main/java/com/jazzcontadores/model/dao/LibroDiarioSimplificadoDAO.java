/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.dao;

import com.jazzcontadores.model.entities.LibroDiarioSimplificado;
import java.util.Date;

/**
 *
 * @author Venegas
 */
public interface LibroDiarioSimplificadoDAO extends GenericDAO<LibroDiarioSimplificado, Integer> {

    LibroDiarioSimplificado findByPeriodo(long ruc, Date periodo);
}
