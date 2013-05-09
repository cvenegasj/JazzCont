/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.daoimpl;

import com.jazzcontadores.model.dao.LibroRegistroComprasDAO;
import com.jazzcontadores.model.entities.LibroRegistroCompras;
import java.util.Date;

/**
 *
 * @author Venegas
 */
public class LibroRegistroComprasDAOImpl extends GenericDAOImpl<LibroRegistroCompras, Integer>
        implements LibroRegistroComprasDAO {

    @Override
    public LibroRegistroCompras findByPeriodo(long ruc, Date periodo) {
        LibroRegistroCompras libroRegistroCompras;
        
        libroRegistroCompras = (LibroRegistroCompras) getSession().createQuery("from LibroRegistroCompras l "
                + "where l.empresaCliente.ruc = :ruc "
                + "and l.periodo = :periodo")
                .setLong("ruc", ruc)
                .setDate("periodo", periodo)
                .uniqueResult();
        
        return libroRegistroCompras;
    }
}
