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
    public Comprador findByTipoDocumentoYNumeroDocumento(long rucCliente, String numeroTipoDoc, String numeroDocumento) {
        Comprador comprador;
        
        comprador = (Comprador) getSession().createQuery("select c "
                + "from Comprador c "
                + "join c.comprobantesVenta cv "
                + "join cv.detalleLibroRegistroVentas d "
                + "join d.libroRegistroVentas l "
                + "join l.empresaCliente e "
                + "where e.ruc = :ruc "
                + "and c.tipoDocumentoIdentidad.numero = :numeroTipoDoc "
                + "and c.numeroDocumentoIdentidad = :numeroDocumento")
                .setLong("ruc", rucCliente)
                .setString("numeroTipoDoc", numeroTipoDoc)
                .setString("numeroDocumento", numeroDocumento)
                .uniqueResult();
        
        return comprador;
    }
}
