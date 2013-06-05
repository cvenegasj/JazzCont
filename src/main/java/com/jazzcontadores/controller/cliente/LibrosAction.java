/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.cliente;

import com.jazzcontadores.model.entities.Contacto;
import com.jazzcontadores.model.entities.LibroDiarioSimplificado;
import com.jazzcontadores.model.entities.LibroRegistroCompras;
import com.jazzcontadores.model.entities.LibroRegistroVentas;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.HibernateUtil;
import com.jazzcontadores.util.UserAware;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Venegas
 */
public class LibrosAction extends ActionSupport implements UserAware {

    Object user; // para manejar el usuario se la sesion
    private List<LibroRegistroCompras> librosCompras = new ArrayList<LibroRegistroCompras>();
    private List<LibroRegistroVentas> librosVentas = new ArrayList<LibroRegistroVentas>();
    private List<LibroDiarioSimplificado> librosDiarioSimplificados = new ArrayList<LibroDiarioSimplificado>();

    @Override
    public String execute() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        
        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        Contacto c = factory.getContactoDAO().findById(((Contacto) user).getIdContacto());
        
        // inicializamos las colecciones
        c.getEmpresaCliente().getLibrosRegistroCompras().size();
        c.getEmpresaCliente().getLibrosRegistroVentas().size();
        c.getEmpresaCliente().getLibrosDiarioSimplificados().size();
        
        this.setLibrosCompras(c.getEmpresaCliente().getLibrosRegistroCompras());
        this.setLibrosVentas(c.getEmpresaCliente().getLibrosRegistroVentas());
        this.setLibrosDiarioSimplificados(c.getEmpresaCliente().getLibrosDiarioSimplificados());

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return SUCCESS;
    }

    public List<LibroRegistroCompras> getLibrosCompras() {
        return librosCompras;
    }

    public void setLibrosCompras(List<LibroRegistroCompras> librosCompras) {
        this.librosCompras = librosCompras;
    }

    public List<LibroRegistroVentas> getLibrosVentas() {
        return librosVentas;
    }

    public void setLibrosVentas(List<LibroRegistroVentas> librosVentas) {
        this.librosVentas = librosVentas;
    }

    public List<LibroDiarioSimplificado> getLibrosDiarioSimplificados() {
        return librosDiarioSimplificados;
    }

    public void setLibrosDiarioSimplificados(List<LibroDiarioSimplificado> librosDiarioSimplificados) {
        this.librosDiarioSimplificados = librosDiarioSimplificados;
    }

    @Override
    public void setUser(Object user) {
        this.user = user;
    }
}
