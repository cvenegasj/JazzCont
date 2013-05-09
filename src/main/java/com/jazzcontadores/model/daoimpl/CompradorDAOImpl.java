/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.daoimpl;

import com.jazzcontadores.model.dao.CompradorDAO;
import com.jazzcontadores.model.entities.Comprador;

/**
 *
 * @author Venegas
 */
public class CompradorDAOImpl extends GenericDAOImpl<Comprador, Integer>
        implements CompradorDAO {

    @Override
    public Comprador findByTipoDocumentoYNumeroDocumento(String numeroTipoDoc, String numeroDocumento) {
        Comprador comprador;
        
        comprador = (Comprador) getSession().createQuery("from Comprador c "
                + "where c.tipoDocumentoIdentidad.numero = :numeroTipoDoc "
                + "and c.numeroDocumentoIdentidad = :numeroDocumento")
                .setString("numeroTipoDoc", numeroTipoDoc)
                .setString("numeroDocumento", numeroDocumento)
                .uniqueResult();
        
        return comprador;
    }
}
