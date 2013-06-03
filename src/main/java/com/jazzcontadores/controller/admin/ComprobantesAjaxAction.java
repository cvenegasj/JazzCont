/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.jazzcontadores.model.entities.ComprobanteCompra;
import com.jazzcontadores.model.entities.ComprobanteVenta;
import com.jazzcontadores.model.entities.DetalleComprobanteCompra;
import com.jazzcontadores.util.ComprobanteCompraSerializable;
import com.jazzcontadores.util.ComprobanteVentaSerializable;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.DetalleComprobanteCompraSerializable;
import com.jazzcontadores.util.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author Venegas
 */
public class ComprobantesAjaxAction extends ActionSupport {

    private int idComp;
    private ComprobanteVentaSerializable comprobanteVentaSerializable;
    private ComprobanteCompraSerializable comprobanteCompraSerializable;

    public String showComprobanteVenta() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        
        ComprobanteVenta cv = factory.getComprobanteVentaDAO().findById(this.getIdComp());
        //ComprobanteVentaSerializable cvs = new 

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "showComprobanteVenta";
    }

    public String showComprobanteCompra() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        
        ComprobanteCompra cc = factory.getComprobanteCompraDAO().findById(this.getIdComp());
        ComprobanteCompraSerializable ccs = new ComprobanteCompraSerializable();
        ccs.setIdComprobanteCompra(cc.getIdComprobanteCompra());
        ccs.setNumeroTipoCompPago(cc.getTipoComprobantePagoODocumento().getNumero());
        ccs.setDescTipoCompPago(cc.getTipoComprobantePagoODocumento().getDescripcion());
        ccs.setNumero(cc.getNumero());
        ccs.setSerie(cc.getSerie());
        ccs.setAnioEmisionDuaOdsi(cc.getAnioEmisionDuaOdsi());
        ccs.setFechaEmision(cc.getFechaEmision());
        ccs.setFechaVencimientoOpago(cc.getFechaVencimientoOpago());
        ccs.setBase(cc.getBase());
        ccs.setIgv(cc.getIgv());
        ccs.setImporteTotal(cc.getImporteTotal());        
        ccs.setNumeroCodigoAduana(cc.getCodigoAduana() == null? null : cc.getCodigoAduana().getNumero());
        ccs.setDescripcionCodigoAduana(cc.getCodigoAduana() == null? null : cc.getCodigoAduana().getDescripcion());
        ccs.setRazonSocialProveedor(cc.getProveedor().getRazonSocial());
        ccs.setNumeroDocIdentidadProveedor(cc.getProveedor().getNumeroDocumentoIdentidad());
        ccs.setNumeroTipoDocIdentidadProveedor(cc.getProveedor().getTipoDocumentoIdentidad().getNumero());
        
        for (DetalleComprobanteCompra dcc : cc.getDetallesComprobanteCompra()) {
            DetalleComprobanteCompraSerializable dccs = new DetalleComprobanteCompraSerializable();
            dccs.setIdDetalleComprobanteCompra(dcc.getIdDetalleComprobanteCompra());
            dccs.setCantidad(dcc.getCantidad());
            dccs.setPrecioUnitario(dcc.getPrecioUnitario());
            dccs.setSubtotal(dcc.getSubtotal());
            dccs.setIdProductoCompras(dcc.getProductoCompras().getIdProductoCompras());
            dccs.setNombreProducto(dcc.getProductoCompras().getNombre());
            dccs.setPrecioProducto(dcc.getProductoCompras().getPrecio());
            dccs.setUnidadDeMedidaProducto(dcc.getProductoCompras().getUnidadDeMedida());
            ccs.getDetallesComprobanteCompra().add(dccs);
        }
        
        this.setComprobanteCompraSerializable(ccs);
        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "showComprobanteCompra";
    }

    public int getIdComp() {
        return idComp;
    }

    public void setIdComp(int idComp) {
        this.idComp = idComp;
    }  

    public ComprobanteVentaSerializable getComprobanteVentaSerializable() {
        return comprobanteVentaSerializable;
    }

    public void setComprobanteVentaSerializable(ComprobanteVentaSerializable comprobanteVentaSerializable) {
        this.comprobanteVentaSerializable = comprobanteVentaSerializable;
    }

    public ComprobanteCompraSerializable getComprobanteCompraSerializable() {
        return comprobanteCompraSerializable;
    }

    public void setComprobanteCompraSerializable(ComprobanteCompraSerializable comprobanteCompraSerializable) {
        this.comprobanteCompraSerializable = comprobanteCompraSerializable;
    }
}
