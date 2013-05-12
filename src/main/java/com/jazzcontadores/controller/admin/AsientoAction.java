/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.jazzcontadores.model.dao.EmpresaClienteDAO;
import com.jazzcontadores.model.dao.LibroDiarioSimplificadoDAO;
import com.jazzcontadores.model.entities.AsientoContable;
import com.jazzcontadores.model.entities.EmpresaCliente;
import com.jazzcontadores.model.entities.LibroDiarioSimplificado;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.HibernateUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Venegas
 */
public class AsientoAction extends ActionSupport {

    private long ruc;
    private EmpresaCliente empresaCliente;
    private AsientoContable asiento;

    public String add() {

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
        return "add";
    }

    public String save() throws Exception {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        LibroDiarioSimplificadoDAO libroDiarioSimpDAO = factory.getLibroDiarioSimplificadoDAO();

        Calendar cPeriodo = new GregorianCalendar();
        cPeriodo.setTime(this.getAsiento().getFecha());
        // para los periodos de los libros, el día es 1 siempre
        cPeriodo.set(Calendar.DAY_OF_MONTH, 1);

        LibroDiarioSimplificado libroExistente = libroDiarioSimpDAO.findByPeriodo(this.getEmpresaCliente().getRuc(), cPeriodo.getTime());

        try {
            if (libroExistente == null) {
                Calendar cFechaFin = (Calendar) cPeriodo.clone();
                cFechaFin.set(Calendar.DAY_OF_MONTH, cFechaFin.getActualMaximum(Calendar.DAY_OF_MONTH));

                LibroDiarioSimplificado libroDSNuevo = new LibroDiarioSimplificado();
                libroDSNuevo.setPeriodo(cPeriodo.getTime());
                libroDSNuevo.setFechaInicio(cPeriodo.getTime());
                libroDSNuevo.setFechaFin(cFechaFin.getTime());
                libroDSNuevo.setEstaCerrado(false);
                libroDSNuevo.setEmpresaCliente(this.getEmpresaCliente());
                
                //libroDiarioSimpDAO.makePersistent(libroDSNuevo);
                
                this.getAsiento().setLibroDiarioSimplificado(libroDSNuevo);
                this.getAsiento().setFechaHoraRegistro(new Date());
                
                // ...
                
                libroDSNuevo.getAsientosContables().add(this.getAsiento());

            } else if (!libroExistente.isEstaCerrado()) {
            } else {
                return "libroCerrado";
            }


        } catch (Exception e) {
            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            System.err.println(e + ": " + e.getMessage());
            throw e;
            //return ERROR;
        }

        return "save";
    }

    public void validateSave() {
        // se inyecta el cliente antes de hacer las validaciones
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaCliente e = factory.getEmpresaClienteDAO().findByRuc(ruc);
        this.setEmpresaCliente(e);

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();



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

    public AsientoContable getAsiento() {
        return asiento;
    }

    public void setAsiento(AsientoContable asiento) {
        this.asiento = asiento;
    }
}
