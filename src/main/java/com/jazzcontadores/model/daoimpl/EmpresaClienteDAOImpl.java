/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.model.daoimpl;

import com.jazzcontadores.model.dao.EmpresaClienteDAO;
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
public class EmpresaClienteDAOImpl extends GenericDAOImpl<EmpresaCliente, Integer>
        implements EmpresaClienteDAO {

    @Override
    public LibroRegistroCompras findRegistroComprasByPeriodo(Integer idEmpresa, Date periodo) {
        LibroRegistroCompras libro;

        libro = (LibroRegistroCompras) getSession().createQuery("from LibroRegistroCompras l "
                + "where l.empresaCliente.id = :idEmpresa and l.periodo = :periodo")
                .setInteger("idEmpresa", idEmpresa)
                .setDate("periodo", periodo)
                .setMaxResults(1)
                .uniqueResult();

        return libro;
    }

    @Override
    public List<EmpresaCliente> findEmpresasClienteAF() {
        List<EmpresaCliente> listaClientes;

        listaClientes = (List<EmpresaCliente>) getSession().createSQLQuery("select * from EmpresaCliente e "
                + "where e.razonSocial regexp '^[a-f]' "
                + "order by e.razonSocial")
                .addEntity(EmpresaCliente.class).list();

        return listaClientes;
    }

    @Override
    public List<EmpresaCliente> findEmpresasClienteGM() {
        List<EmpresaCliente> listaClientes;

        listaClientes = (List<EmpresaCliente>) getSession().createSQLQuery("select * from EmpresaCliente e "
                + "where e.razonSocial regexp '^[g-m]' "
                + "order by e.razonSocial")
                .addEntity(EmpresaCliente.class).list();

        return listaClientes;
    }

    @Override
    public List<EmpresaCliente> findEmpresasClienteNT() {
        List<EmpresaCliente> listaClientes;

        listaClientes = (List<EmpresaCliente>) getSession().createSQLQuery("select * from EmpresaCliente e "
                + "where e.razonSocial regexp '^[n-t]' "
                + "order by e.razonSocial")
                .addEntity(EmpresaCliente.class).list();

        return listaClientes;
    }

    @Override
    public List<EmpresaCliente> findEmpresasClienteUZ() {
        List<EmpresaCliente> listaClientes;

        listaClientes = (List<EmpresaCliente>) getSession().createSQLQuery("select * from EmpresaCliente e "
                + "where e.razonSocial regexp '^[u-z]' "
                + "order by e.razonSocial")
                .addEntity(EmpresaCliente.class).list();

        return listaClientes;
    }

    @Override
    public List<EmpresaCliente> findEmpresasClienteRG() {
        List<EmpresaCliente> listaClientes;

        listaClientes = (List<EmpresaCliente>) getSession().createQuery("from EmpresaCliente e "
                + "where e.tipoRegimen = 'RG' "
                + "order by e.razonSocial").list();

        return listaClientes;
    }

    @Override
    public List<EmpresaCliente> findEmpresasClienteRER() {
        List<EmpresaCliente> listaClientes;

        listaClientes = (List<EmpresaCliente>) getSession().createQuery("from EmpresaCliente e "
                + "where e.tipoRegimen = 'RER' "
                + "order by e.razonSocial").list();

        return listaClientes;
    }

    @Override
    public List<EmpresaCliente> findEmpresasClienteNRUS() {
        List<EmpresaCliente> listaClientes;

        listaClientes = (List<EmpresaCliente>) getSession().createQuery("from EmpresaCliente e "
                + "where e.tipoRegimen = 'NRUS' "
                + "order by e.razonSocial").list();

        return listaClientes;
    }

    @Override
    public EmpresaCliente findByRuc(long ruc) {
        EmpresaCliente empresaCliente;

        empresaCliente = (EmpresaCliente) getSession().createQuery("from EmpresaCliente e "
                + "where e.ruc = :ruc")
                .setLong("ruc", ruc)
                .uniqueResult();

        return empresaCliente;
    }

    @Override
    public List<EmpresaCliente> findAllOrderedByRazonSocial() {
        List<EmpresaCliente> listaClientes;

        listaClientes = (List<EmpresaCliente>) getSession().createQuery("from EmpresaCliente e "
                + "order by e.razonSocial").list();

        return listaClientes;
    }

    @Override
    public List<EmpresaCliente> findByTerm(String term) {
        List<EmpresaCliente> listaClientes;

        listaClientes = (List<EmpresaCliente>) getSession().createQuery("from EmpresaCliente e "
                + "where e.razonSocial like :term "
                + "order by e.razonSocial")
                .setString("term", "%" + term + "%").list();

        return listaClientes;
    }

    @Override
    public List<Proveedor> findProveedoresByTerm(long rucCliente, String q) {
        List<Proveedor> listaProveedores;

        // se hace múltiples joins debido a multiplicidad many
        listaProveedores = (List<Proveedor>) getSession().createQuery("select distinct p "
                + "from Proveedor p "
                + "join p.comprobantesCompra c "
                + "join c.detalleLibroRegistroCompras d "
                + "join d.libroRegistroCompras l "
                + "join l.empresaCliente e "
                + "where e.ruc = :ruc "
                + "and p.razonSocial like :term "
                + "order by p.razonSocial")
                .setLong("ruc", rucCliente)
                .setString("term", "%" + q + "%").list();

        return listaProveedores;
    }

    @Override
    public List<Comprador> findCompradoresByTerm(long rucCliente, String q) {
        List<Comprador> listaCompradores;

        listaCompradores = (List<Comprador>) getSession().createQuery("select distinct c "
                + "from Comprador c "
                + "join c.comprobantesVenta cv "
                + "join cv.detalleLibroRegistroVentas d "
                + "join d.libroRegistroVentas l "
                + "join l.empresaCliente e "
                + "where e.ruc = :ruc "
                + "and c.razonSocialONombres like :term "
                + "order by c.razonSocialONombres")
                .setLong("ruc", rucCliente)
                .setString("term", "%" + q + "%").list();

        return listaCompradores;
    }

    @Override
    public List<ProductoCompras> findProductosComprasByTerm(long rucCliente, String q) {
        List<ProductoCompras> listaProductosCompras;

        listaProductosCompras = (List<ProductoCompras>) getSession().createQuery("select distinct prod "
                + "from ProductoCompras prod "
                + "join prod.detallesComprobanteCompra d "
                + "join d.comprobanteCompra cc "
                + "join cc.detalleLibroRegistroCompras dl "
                + "join dl.libroRegistroCompras l "
                + "join l.empresaCliente e "
                + "where e.ruc = :ruc "
                + "and prod.nombre like :term "
                + "order by prod.nombre")
                .setLong("ruc", rucCliente)
                .setString("term", "%" + q + "%").list();

        return listaProductosCompras;
    }

    @Override
    public List<ProductoVentas> findProductosVentasByTerm(long rucCliente, String q) {
        List<ProductoVentas> listaProductosVentas;

        listaProductosVentas = (List<ProductoVentas>) getSession().createQuery("select distinct prod "
                + "from ProductoVentas prod "
                + "join prod.detallesComprobanteVenta d "
                + "join d.comprobanteVenta cv "
                + "join cv.detalleLibroRegistroVentas dl "
                + "join dl.libroRegistroVentas l "
                + "join l.empresaCliente e "
                + "where e.ruc = :ruc "
                + "and prod.nombre like :term "
                + "order by prod.nombre")
                .setLong("ruc", rucCliente)
                .setString("term", "%" + q + "%").list();

        return listaProductosVentas;
    }
}
