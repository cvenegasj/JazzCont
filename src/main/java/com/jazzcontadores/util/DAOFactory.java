/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.util;

import com.jazzcontadores.model.dao.*;

/**
 *
 * @author Venegas
 */
public abstract class DAOFactory {

    /**
     * Creates a standalone DAOFactory that returns unmanaged DAO beans for use
     * in any environment Hibernate has been configured for. Uses
     * HibernateUtil/SessionFactory and Hibernate context propagation
     * (CurrentSessionContext), thread-bound or transaction-bound, and
     * transaction scoped.
     */
    public static final Class HIBERNATE = com.jazzcontadores.util.HibernateDAOFactory.class;

    /**
     * Factory method for instantiation of concrete factories.
     */
    public static DAOFactory instance(Class factory) {
        try {
            return (DAOFactory) factory.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Couldn't create DAOFactory: " + factory);
        }
    }
    // Add your DAO interfaces here

    public abstract AsientoContableDAO getAsientoContableDAO();

    public abstract ClasificacionCuentaDAO getClasificacionCuentaDAO();

    public abstract CodigoAduanaDAO getCodigoAduanaDAO();

    public abstract CompradorDAO getCompradorDAO();

    public abstract ComprobanteCompraDAO getComprobanteCompraDAO();

    public abstract ComprobanteVentaDAO getComprobanteVentaDAO();

    public abstract ContactoDAO getContactoDAO();

    public abstract ContadorDAO getContadorDAO();

    public abstract CuentaContableDAO getCuentaContableDAO();

    public abstract DetalleAbonoDAO getDetalleAbonoDAO();

    public abstract DetalleCargoDAO getDetalleCargoDAO();

    public abstract DetalleComprobanteCompraDAO getDetalleComprobanteCompraDAO();

    public abstract DetalleComprobanteVentaDAO getDetalleComprobanteVentaDAO();

    public abstract DetalleLibroRegistroComprasDAO getDetalleLibroRegistroComprasDAO();

    public abstract DetalleLibroRegistroVentasDAO getDetalleLibroRegistroVentasDAO();

    public abstract ElementoPlanCuentasDAO getElementoPlanCuentasDAO();

    public abstract EmpresaClienteDAO getEmpresaClienteDAO();

    public abstract LibroDiarioSimplificadoDAO getLibroDiarioSimplificadoDAO();

    public abstract LibroRegistroComprasDAO getLibroRegistroComprasDAO();

    public abstract LibroRegistroVentasDAO getLibroRegistroVentasDAO();

    public abstract ProductoComprasDAO getProductoComprasDAO();

    public abstract ProductoVentasDAO getProductoVentasDAO();

    public abstract ProveedorDAO getProveedorDAO();

    public abstract RegistroCuentaContableDAO getRegistroCuentaContableDAO();

    public abstract TipoComprobantePagoODocumentoDAO getTipoComprobantePagoODocumentoDAO();

    public abstract TipoDocumentoIdentidadDAO getTipoDocumentoIdentidadDAO();
}
