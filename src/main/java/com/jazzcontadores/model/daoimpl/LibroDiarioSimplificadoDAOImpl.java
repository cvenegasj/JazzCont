/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.daoimpl;

import com.jazzcontadores.model.dao.LibroDiarioSimplificadoDAO;
import com.jazzcontadores.model.entities.LibroDiarioSimplificado;
import java.util.Date;

/**
 *
 * @author Venegas
 */
public class LibroDiarioSimplificadoDAOImpl extends GenericDAOImpl<LibroDiarioSimplificado, Integer>
        implements LibroDiarioSimplificadoDAO {

    @Override
    public LibroDiarioSimplificado findByPeriodo(long ruc, Date periodo) {
        LibroDiarioSimplificado libroDiarioSimp;

        libroDiarioSimp = (LibroDiarioSimplificado) getSession().createQuery("from LibroDiarioSimplificado l "
                + "where l.empresaCliente.ruc = :ruc "
                + "and l.periodo = :periodo")
                .setLong("ruc", ruc)
                .setDate("periodo", periodo)
                .uniqueResult();

        return libroDiarioSimp;
    }
}
