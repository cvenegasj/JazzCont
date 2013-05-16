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
import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;

/**
 *
 * @author Venegas
 */
public class EmpresaClienteAction extends ActionSupport {

    private long ruc;
    // criterio para ordenar a los clientes
    private String cr = "Todos";
    // para el registro de cliente
    private EmpresaCliente empresaCliente;
    private List<EmpresaCliente> listaClientes = new ArrayList<EmpresaCliente>();

    public String add() {
        return "add";
    }

    public String save() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        getEmpresaCliente().getContacto().setEmpresaCliente(empresaCliente);
        
        long tiempoInicio = System.currentTimeMillis();
        
        // Hashing password with Shiro
        ByteSource salt = new SecureRandomNumberGenerator().nextBytes();
        String hashedPassword = new Sha512Hash(getEmpresaCliente().getContacto().getPassword(), salt, 500000).toHex();
                
        /*
         * hashedPassword.length = 128
         * salt.hex.length = 32
         */
        long tiempoFin = System.currentTimeMillis();
        System.out.println("Tiempo de hashing: " + (tiempoFin - tiempoInicio) + " ms");
        
        getEmpresaCliente().getContacto().setPassword(hashedPassword);
        getEmpresaCliente().getContacto().setSalt(salt.toHex());
        empresaDAO.makePersistent(empresaCliente);

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();

        return "save";
    }

    public void validateSave() {
        
        if (getEmpresaCliente().getNombreEmpresa() == null
                || getEmpresaCliente().getNombreEmpresa().trim().equals("")) {
            addActionError("Debe especificar el nombre de la empresa.");
        }
        if (getEmpresaCliente().getRazonSocial() == null
                || getEmpresaCliente().getRazonSocial().trim().equals("")) {
            addActionError("Debe especificar la razón social.");
        }
        if (getEmpresaCliente().getTipoRegimen() == null
                || getEmpresaCliente().getTipoRegimen().trim().equals("")) {
            addActionError("Debe especificar el tipo de régimen.");
        }
        if (getEmpresaCliente().getEstado() == null
                || getEmpresaCliente().getEstado().trim().equals("")) {
            addActionError("Debe especificar el estado en el sistema del cliente.");
        }
        if (getEmpresaCliente().getRuc() == 0
                || !Long.toString(getEmpresaCliente().getRuc()).matches("^(1|2)\\d{10}$")) {
            addActionError("El RUC debe tener el formato correcto.");
        }
        if (getEmpresaCliente().getContacto().getEmailPrincipal() == null
                || getEmpresaCliente().getContacto().getEmailPrincipal().trim().equals("")) {
            addActionError("Debe especificar el email principal del contacto.");
        }
        if (getEmpresaCliente().getContacto().getPassword() == null
                || getEmpresaCliente().getContacto().getPassword().trim().equals("")) {
            addActionError("Debe especificar la contraseña del contacto.");
        }
        if (getEmpresaCliente().getContacto().getNombres() == null
                || getEmpresaCliente().getContacto().getNombres().trim().equals("")) {
            addActionError("Debe especificar los nombres del contacto.");
        }
        if (getEmpresaCliente().getContacto().getApellidos() == null
                || getEmpresaCliente().getContacto().getApellidos().trim().equals("")) {
            addActionError("Debe especificar los apellidos del contacto.");
        }
    }

    public String show() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente e = empresaDAO.findByRuc(ruc);

        if (e != null) {
            this.setEmpresaCliente(e);
        } else {
            return ERROR;
        }

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "show";
    }

    public String list() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        if (cr.equals("A-F")) {
            listaClientes = empresaDAO.findEmpresasClienteAF();
        } else if (cr.equals("G-M")) {
            listaClientes = empresaDAO.findEmpresasClienteGM();
        } else if (cr.equals("N-T")) {
            listaClientes = empresaDAO.findEmpresasClienteNT();
        } else if (cr.equals("U-Z")) {
            listaClientes = empresaDAO.findEmpresasClienteUZ();
        } else if (cr.equals("RG")) {
            listaClientes = empresaDAO.findEmpresasClienteRG();
        } else if (cr.equals("RER")) {
            listaClientes = empresaDAO.findEmpresasClienteRER();
        } else if (cr.equals("NRUS")) {
            listaClientes = empresaDAO.findEmpresasClienteNRUS();
        } else {
            listaClientes = empresaDAO.findAllOrderedByRazonSocial();
        }

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "list";
    }

    public long getRuc() {
        return ruc;
    }

    public void setRuc(long ruc) {
        this.ruc = ruc;
    }

    public EmpresaCliente getEmpresaCliente() {
        return empresaCliente;
    }

    public void setEmpresaCliente(EmpresaCliente empresaCliente) {
        this.empresaCliente = empresaCliente;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public List<EmpresaCliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<EmpresaCliente> listaClientes) {
        this.listaClientes = listaClientes;
    }
}
