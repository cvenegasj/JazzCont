/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.dao;

import com.jazzcontadores.model.entities.Comprador;

/**
 *
 * @author Venegas
 */
public interface CompradorDAO extends GenericDAO<Comprador, Integer> {

    Comprador findByTipoDocumentoYNumeroDocumento(String numeroTipoDoc, String numeroDocumento);
}
