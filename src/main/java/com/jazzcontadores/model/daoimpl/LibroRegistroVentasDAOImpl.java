/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.daoimpl;

import com.jazzcontadores.model.dao.LibroRegistroVentasDAO;
import com.jazzcontadores.model.entities.DetalleLibroRegistroVentas;
import com.jazzcontadores.model.entities.LibroRegistroVentas;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Venegas
 */
public class LibroRegistroVentasDAOImpl extends GenericDAOImpl<LibroRegistroVentas, Integer>
        implements LibroRegistroVentasDAO {

    @Override
    public LibroRegistroVentas findByPeriodo(long ruc, Date periodo) {
        LibroRegistroVentas libroRegistroVentas;

        libroRegistroVentas = (LibroRegistroVentas) getSession().createQuery("from LibroRegistroVentas l "
                + "where l.empresaCliente.ruc = :ruc "
                + "and l.periodo = :periodo")
                .setLong("ruc", ruc)
                .setDate("periodo", periodo)
                .uniqueResult();

        return libroRegistroVentas;
    }

    @Override
    public LibroRegistroVentas findByIdAndEmpresa(long ruc, int idLibroRegistroVentas) {
        LibroRegistroVentas libroRegistroVentas;

        libroRegistroVentas = (LibroRegistroVentas) getSession().createQuery("from LibroRegistroVentas l "
                + "where l.empresaCliente.ruc = :ruc "
                + "and l.id = :idLibroRegistroVentas")
                .setLong("ruc", ruc)
                .setInteger("idLibroRegistroVentas", idLibroRegistroVentas)
                .uniqueResult();

        return libroRegistroVentas;
    }

    @Override
    public List<DetalleLibroRegistroVentas> findDetallesByIdLibroAndEmpresa(long ruc, int idLibroRegistroVentas, int start, int limit) {
        List<DetalleLibroRegistroVentas> detallesLibroRegistroVentas;

        detallesLibroRegistroVentas = (List<DetalleLibroRegistroVentas>) getSession().createQuery("from DetalleLibroRegistroVentas d "
                + "where d.libroRegistroVentas.empresaCliente.ruc = :ruc "
                + "and d.libroRegistroVentas.id = :idLibroRegistroVentas "
                + "order by d.numeroCorrelativo")
                .setLong("ruc", ruc)
                .setInteger("idLibroRegistroVentas", idLibroRegistroVentas)
                .setFirstResult(start)
                .setMaxResults(limit)
                .list();

        return detallesLibroRegistroVentas;
    }

    @Override
    public int getTotalCountOfDetallesByLibro(long ruc, int idLibroRegistroVentas) {
        int count = ((Long) getSession().createQuery("select count(*) "
                + "from DetalleLibroRegistroVentas d "
                + "where d.libroRegistroVentas.empresaCliente.ruc = :ruc "
                + "and d.libroRegistroVentas.id = :idLibroRegistroVentas")
                .setLong("ruc", ruc)
                .setInteger("idLibroRegistroVentas", idLibroRegistroVentas)
                .uniqueResult())
                .intValue();

        return count;
    }
}
