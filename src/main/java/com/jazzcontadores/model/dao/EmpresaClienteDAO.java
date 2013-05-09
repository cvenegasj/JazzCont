/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.dao;

import com.jazzcontadores.model.entities.Comprador;
import com.jazzcontadores.model.entities.EmpresaCliente;
import com.jazzcontadores.model.entities.LibroRegistroCompras;
import com.jazzcontadores.model.entities.ProductoCompras;
import com.jazzcontadores.model.entities.ProductoVentas;
import com.jazzcontadores.model.entities.Proveedor;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Venegas
 */
public interface EmpresaClienteDAO extends GenericDAO<EmpresaCliente, Integer> {

    EmpresaCliente findByRuc(long ruc);

    LibroRegistroCompras findRegistroComprasByPeriodo(Integer idEmpresa, Date periodo);

    List<EmpresaCliente> findAllOrderedByRazonSocial();

    List<Proveedor> findProveedoresByTerm(long rucCliente, String q);

    List<Comprador> findCompradoresByTerm(long rucCliente, String q);
    
    List<ProductoCompras> findProductosComprasByTerm(long rucCliente, String q);
    
    List<ProductoVentas> findProductosVentasByTerm(long rucCliente, String q);

    List<EmpresaCliente> findByTerm(String term);

    List<EmpresaCliente> findEmpresasClienteAF();

    List<EmpresaCliente> findEmpresasClienteGM();

    List<EmpresaCliente> findEmpresasClienteNT();

    List<EmpresaCliente> findEmpresasClienteUZ();

    List<EmpresaCliente> findEmpresasClienteRG();

    List<EmpresaCliente> findEmpresasClienteRER();

    List<EmpresaCliente> findEmpresasClienteNRUS();
}
