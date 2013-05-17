/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.dao;

import com.jazzcontadores.model.entities.DetalleLibroRegistroCompras;
import com.jazzcontadores.model.entities.LibroRegistroCompras;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Venegas
 */
public interface LibroRegistroComprasDAO extends GenericDAO<LibroRegistroCompras, Integer> {

    LibroRegistroCompras findByPeriodo(long ruc, Date periodo);

    LibroRegistroCompras findByIdAndEmpresa(long ruc, int idLibroRegistroCompras);

    List<DetalleLibroRegistroCompras> findDetallesByIdLibroAndEmpresa(long ruc, int idLibroRegistroCompras, int start, int limit);

    int getTotalCountOfDetallesByLibro(long ruc, int idLibroRegistroCompras);
}
