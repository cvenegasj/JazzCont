/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.daoimpl;

import com.jazzcontadores.model.dao.LibroRegistroComprasDAO;
import com.jazzcontadores.model.entities.DetalleLibroRegistroCompras;
import com.jazzcontadores.model.entities.LibroRegistroCompras;
import java.util.Date;
import java.util.List;

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

    @Override
    public LibroRegistroCompras findByIdAndEmpresa(long ruc, int idLibroRegistroCompras) {
        LibroRegistroCompras libroRegistroCompras;

        libroRegistroCompras = (LibroRegistroCompras) getSession().createQuery("from LibroRegistroCompras l "
                + "where l.empresaCliente.ruc = :ruc "
                + "and l.id = :idLibroRegistroCompras")
                .setLong("ruc", ruc)
                .setInteger("idLibroRegistroCompras", idLibroRegistroCompras)
                .uniqueResult();

        return libroRegistroCompras;
    }

    @Override
    public List<DetalleLibroRegistroCompras> findDetallesByIdLibroAndEmpresa(long ruc, int idLibroRegistroCompras, int start, int limit) {
        List<DetalleLibroRegistroCompras> detallesLibroRegistroCompras;

        detallesLibroRegistroCompras = (List<DetalleLibroRegistroCompras>) getSession().createQuery("from DetalleLibroRegistroCompras d "
                + "where d.libroRegistroCompras.empresaCliente.ruc = :ruc "
                + "and d.libroRegistroCompras.id = :idLibroRegistroCompras "
                + "order by d.numeroCorrelativo")
                .setLong("ruc", ruc)
                .setInteger("idLibroRegistroCompras", idLibroRegistroCompras)
                .setFirstResult(start)
                .setMaxResults(limit)
                .list();

        return detallesLibroRegistroCompras;
    }

    @Override
    public int getTotalCountOfDetallesByLibro(long ruc, int idLibroRegistroCompras) {
        int count = ((Long) getSession().createQuery("select count(*) "
                + "from DetalleLibroRegistroCompras d "
                + "where d.libroRegistroCompras.empresaCliente.ruc = :ruc "
                + "and d.libroRegistroCompras.id = :idLibroRegistroCompras")
                .setLong("ruc", ruc)
                .setInteger("idLibroRegistroCompras", idLibroRegistroCompras)
                .uniqueResult())
                .intValue();

        return count;
    }
}
