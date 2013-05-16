/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.jazzcontadores.model.dao.EmpresaClienteDAO;
import com.jazzcontadores.model.entities.EmpresaCliente;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author Venegas
 */
public class LibroDiarioSimplificadoAction extends ActionSupport {

    private long ruc;
    private int idLibro;
    private EmpresaCliente empresaCliente;

    public String show() {
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente e = empresaDAO.findByRuc(this.getRuc());

        if (e != null) {
            this.setEmpresaCliente(e);
        } else {
            return ERROR;
        }

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "show";
    }

    public long getRuc() {
        return ruc;
    }

    public void setRuc(long ruc) {
        this.ruc = ruc;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public EmpresaCliente getEmpresaCliente() {
        return empresaCliente;
    }

    public void setEmpresaCliente(EmpresaCliente empresaCliente) {
        this.empresaCliente = empresaCliente;
    }
}
