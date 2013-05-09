/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.util;

import com.jazzcontadores.model.dao.*;
import com.jazzcontadores.model.daoimpl.*;
import org.hibernate.Session;

/**
 *
 * @author Venegas
 */
public class HibernateDAOFactory extends DAOFactory {

    @Override
    public AsientoContableDAO getAsientoContableDAO() {
        return (AsientoContableDAO) instantiateDAO(AsientoContableDAOImpl.class);
    }

    @Override
    public ClasificacionCuentaDAO getClasificacionCuentaDAO() {
        return (ClasificacionCuentaDAO) instantiateDAO(ClasificacionCuentaDAOImpl.class);
    }

    @Override
    public CodigoAduanaDAO getCodigoAduanaDAO() {
        return (CodigoAduanaDAO) instantiateDAO(CodigoAduanaDAOImpl.class);
    }

    @Override
    public CompradorDAO getCompradorDAO() {
        return (CompradorDAO) instantiateDAO(CompradorDAOImpl.class);
    }

    @Override
    public ComprobanteCompraDAO getComprobanteCompraDAO() {
        return (ComprobanteCompraDAO) instantiateDAO(ComprobanteCompraDAOImpl.class);
    }

    @Override
    public ComprobanteVentaDAO getComprobanteVentaDAO() {
        return (ComprobanteVentaDAO) instantiateDAO(ComprobanteVentaDAOImpl.class);
    }

    @Override
    public ContactoDAO getContactoDAO() {
        return (ContactoDAO) instantiateDAO(ContactoDAOImpl.class);
    }

    @Override
    public ContadorDAO getContadorDAO() {
        return (ContadorDAO) instantiateDAO(ContadorDAOImpl.class);
    }

    @Override
    public CuentaContableDAO getCuentaContableDAO() {
        return (CuentaContableDAO) instantiateDAO(CuentaContableDAOImpl.class);
    }

    @Override
    public DetalleAbonoDAO getDetalleAbonoDAO() {
        return (DetalleAbonoDAO) instantiateDAO(DetalleAbonoDAOImpl.class);
    }

    @Override
    public DetalleCargoDAO getDetalleCargoDAO() {
        return (DetalleCargoDAO) instantiateDAO(DetalleCargoDAOImpl.class);
    }

    @Override
    public DetalleComprobanteCompraDAO getDetalleComprobanteCompraDAO() {
        return (DetalleComprobanteCompraDAO) instantiateDAO(DetalleComprobanteCompraDAOImpl.class);
    }

    @Override
    public DetalleComprobanteVentaDAO getDetalleComprobanteVentaDAO() {
        return (DetalleComprobanteVentaDAO) instantiateDAO(DetalleComprobanteVentaDAOImpl.class);
    }

    @Override
    public DetalleLibroRegistroComprasDAO getDetalleLibroRegistroComprasDAO() {
        return (DetalleLibroRegistroComprasDAO) instantiateDAO(DetalleLibroRegistroComprasDAOImpl.class);
    }

    @Override
    public DetalleLibroRegistroVentasDAO getDetalleLibroRegistroVentasDAO() {
        return (DetalleLibroRegistroVentasDAO) instantiateDAO(DetalleLibroRegistroVentasDAOImpl.class);
    }

    @Override
    public ElementoPlanCuentasDAO getElementoPlanCuentasDAO() {
        return (ElementoPlanCuentasDAO) instantiateDAO(ElementoPlanCuentasDAOImpl.class);
    }

    @Override
    public EmpresaClienteDAO getEmpresaClienteDAO() {
        return (EmpresaClienteDAO) instantiateDAO(EmpresaClienteDAOImpl.class);
    }

    @Override
    public LibroDiarioSimplificadoDAO getLibroDiarioSimplificadoDAO() {
        return (LibroDiarioSimplificadoDAO) instantiateDAO(LibroDiarioSimplificadoDAOImpl.class);
    }

    @Override
    public LibroRegistroComprasDAO getLibroRegistroComprasDAO() {
        return (LibroRegistroComprasDAO) instantiateDAO(LibroRegistroComprasDAOImpl.class);
    }

    @Override
    public LibroRegistroVentasDAO getLibroRegistroVentasDAO() {
        return (LibroRegistroVentasDAO) instantiateDAO(LibroRegistroVentasDAOImpl.class);
    }

    @Override
    public ProductoComprasDAO getProductoComprasDAO() {
        return (ProductoComprasDAO) instantiateDAO(ProductoComprasDAOImpl.class);
    }

    @Override
    public ProductoVentasDAO getProductoVentasDAO() {
        return (ProductoVentasDAO) instantiateDAO(ProductoVentasDAOImpl.class);
    }

    @Override
    public ProveedorDAO getProveedorDAO() {
        return (ProveedorDAO) instantiateDAO(ProveedorDAOImpl.class);
    }

    @Override
    public RegistroCuentaContableDAO getRegistroCuentaContableDAO() {
        return (RegistroCuentaContableDAO) instantiateDAO(RegistroCuentaContableDAOImpl.class);
    }

    @Override
    public TipoComprobantePagoODocumentoDAO getTipoComprobantePagoODocumentoDAO() {
        return (TipoComprobantePagoODocumentoDAO) instantiateDAO(TipoComprobantePagoODocumentoDAOImpl.class);
    }

    @Override
    public TipoDocumentoIdentidadDAO getTipoDocumentoIdentidadDAO() {
        return (TipoDocumentoIdentidadDAO) instantiateDAO(TipoDocumentoIdentidadDAOImpl.class);
    }

    private GenericDAOImpl instantiateDAO(Class daoClass) {
        try {
            GenericDAOImpl dao = (GenericDAOImpl) daoClass.newInstance();
            dao.setSession(getCurrentSession());
            return dao;
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }

    // You could override this if you don't want HibernateUtil for lookup
    protected Session getCurrentSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
