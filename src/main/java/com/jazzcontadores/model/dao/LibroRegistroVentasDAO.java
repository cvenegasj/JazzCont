/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.dao;

import com.jazzcontadores.model.entities.DetalleLibroRegistroVentas;
import com.jazzcontadores.model.entities.LibroRegistroVentas;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Venegas
 */
public interface LibroRegistroVentasDAO extends GenericDAO<LibroRegistroVentas, Integer> {

    LibroRegistroVentas findByPeriodo(long ruc, Date periodo);

    LibroRegistroVentas findByIdAndEmpresa(long ruc, int idLibroRegistroVentas);

    List<DetalleLibroRegistroVentas> findDetallesByIdLibroAndEmpresa(long ruc, int idLibroRegistroVentas, int start, int limit);

    int getTotalCountOfDetallesByLibro(long ruc, int idLibroRegistroVentas);
}
