/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.dao;

import com.jazzcontadores.model.entities.Proveedor;

/**
 *
 * @author Venegas
 */
public interface ProveedorDAO extends GenericDAO<Proveedor, Integer> {

    Proveedor findByTipoDocumentoYNumeroDocumento(long rucCliente, String numeroTipoDoc, String numeroDocumento);
}
