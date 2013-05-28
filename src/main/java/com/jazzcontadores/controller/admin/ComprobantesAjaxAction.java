/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.jazzcontadores.model.entities.ComprobanteCompra;
import com.jazzcontadores.model.entities.ComprobanteVenta;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author Venegas
 */
public class ComprobantesAjaxAction extends ActionSupport {

    private int idComp;
    private ComprobanteVenta comprobanteVenta;
    private ComprobanteCompra comprobanteCompra;

    public String showComprobanteVenta() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        
        ComprobanteVenta cv = factory.getComprobanteVentaDAO().findById(this.getIdComp());
        cv.getDetallesComprobanteVenta().size();
        this.setComprobanteVenta(cv);

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "showComprobanteVenta";
    }

    public String showComprobanteCompra() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        
        ComprobanteCompra cc = factory.getComprobanteCompraDAO().findById(this.getIdComp());
        cc.getDetallesComprobanteCompra().size();
        this.setComprobanteCompra(cc);

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "showComprobanteCompra";
    }

    public int getIdComp() {
        return idComp;
    }

    public void setIdComp(int idComp) {
        this.idComp = idComp;
    }

    public ComprobanteVenta getComprobanteVenta() {
        return comprobanteVenta;
    }

    public void setComprobanteVenta(ComprobanteVenta comprobanteVenta) {
        this.comprobanteVenta = comprobanteVenta;
    }

    public ComprobanteCompra getComprobanteCompra() {
        return comprobanteCompra;
    }

    public void setComprobanteCompra(ComprobanteCompra comprobanteCompra) {
        this.comprobanteCompra = comprobanteCompra;
    }
}
