/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.daoimpl;

import com.jazzcontadores.model.dao.ProveedorDAO;
import com.jazzcontadores.model.entities.Proveedor;

/**
 *
 * @author Venegas
 */
public class ProveedorDAOImpl extends GenericDAOImpl<Proveedor, Integer>
        implements ProveedorDAO {

    @Override
    public Proveedor findByTipoDocumentoYNumeroDocumento(long rucCliente, String numeroTipoDoc, String numeroDocumento) {
        Proveedor proveedor;

        proveedor = (Proveedor) getSession().createQuery("select p "
                + "from Proveedor p "
                + "join p.comprobantesCompra cc "
                + "join cc.detalleLibroRegistroCompras d "
                + "join d.libroRegistroCompras l "
                + "join l.empresaCliente e "
                + "where e.ruc = :ruc "
                + "and p.tipoDocumentoIdentidad.numero = :numeroTipoDoc "
                + "and p.numeroDocumentoIdentidad = :numeroDocumento")
                .setLong("ruc", rucCliente)
                .setString("numeroTipoDoc", numeroTipoDoc)
                .setString("numeroDocumento", numeroDocumento)
                .uniqueResult();

        return proveedor;
    }
}
