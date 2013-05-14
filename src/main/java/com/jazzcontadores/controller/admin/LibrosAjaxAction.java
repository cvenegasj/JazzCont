/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.jazzcontadores.model.entities.CuentaContable;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.DetalleLibroRegistroVentasSerializable;
import com.jazzcontadores.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Venegas
 */
public class LibrosAjaxAction {

    private boolean success; // for ext
    private long ruc;
    private int idLibro;
    private List<DetalleLibroRegistroVentasSerializable> listaDetallesRVSrl = new ArrayList<DetalleLibroRegistroVentasSerializable>();

    public String listDetallesRV() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);

        //List<CuentaContable> cuentas = factory.getCuentaContableDAO().findByTerm(this.getTerm());

        

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "listDetallesRV";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getRuc() {
        return ruc;
    }

    public void setRuc(long ruc) {
        this.ruc = ruc;
    }

    public List<DetalleLibroRegistroVentasSerializable> getListaDetallesRVSrl() {
        return listaDetallesRVSrl;
    }

    public void setListaDetallesRVSrl(List<DetalleLibroRegistroVentasSerializable> listaDetallesRVSrl) {
        this.listaDetallesRVSrl = listaDetallesRVSrl;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
}
