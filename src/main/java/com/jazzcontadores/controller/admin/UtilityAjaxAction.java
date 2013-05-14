/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.jazzcontadores.model.entities.CuentaContable;
import com.jazzcontadores.util.CuentaContableSerializable;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Venegas
 */
public class UtilityAjaxAction extends ActionSupport {

    private String term;
    private List<CuentaContableSerializable> listaCuentasSrl = new ArrayList<CuentaContableSerializable>();

    public String listCuentasContables() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);

        List<CuentaContable> cuentas = factory.getCuentaContableDAO().findByTerm(this.getTerm());

        for (CuentaContable c : cuentas) {
            CuentaContableSerializable cSrl = new CuentaContableSerializable();
            cSrl.setNumero(c.getNumero());
            cSrl.setDenominacion(c.getDenominacion());
            cSrl.setClasificacionCuenta(c.getClasificacionCuenta().getIdClasificacionCuenta());
            cSrl.setNombreResumido(c.getDenominacion().length() > 40
                    ? String.format("%s - %.40s...", c.getNumero(), c.getDenominacion())
                    : String.format("%s - %s", c.getNumero(), c.getDenominacion()));
            this.getListaCuentasSrl().add(cSrl);
        }

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "listCuentasContables";
    }

    public List<CuentaContableSerializable> getListaCuentasSrl() {
        return listaCuentasSrl;
    }

    public void setListaCuentasSrl(List<CuentaContableSerializable> listaCuentasSrl) {
        this.listaCuentasSrl = listaCuentasSrl;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
