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
    public Proveedor findByTipoDocumentoYNumeroDocumento(String numeroTipoDoc, String numeroDocumento) {
        Proveedor proveedor;
        
        proveedor = (Proveedor) getSession().createQuery("from Proveedor p "
                + "where p.tipoDocumentoIdentidad.numero = :numeroTipoDoc "
                + "and p.numeroDocumentoIdentidad = :numeroDocumento")
                .setString("numeroTipoDoc", numeroTipoDoc)
                .setString("numeroDocumento", numeroDocumento)
                .uniqueResult();
        
        return proveedor;
    }
}
