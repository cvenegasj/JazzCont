/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.controller.admin;

import com.jazzcontadores.model.dao.EmpresaClienteDAO;
import com.jazzcontadores.model.entities.Comprador;
import com.jazzcontadores.model.entities.EmpresaCliente;
import com.jazzcontadores.model.entities.ProductoCompras;
import com.jazzcontadores.model.entities.ProductoVentas;
import com.jazzcontadores.model.entities.Proveedor;
import com.jazzcontadores.util.CompradorSerializable;
import com.jazzcontadores.util.DAOFactory;
import com.jazzcontadores.util.EmpresaClienteSerializable;
import com.jazzcontadores.util.HibernateUtil;
import com.jazzcontadores.util.ProductoComprasSerializable;
import com.jazzcontadores.util.ProductoVentasSerializable;
import com.jazzcontadores.util.ProveedorSerializable;
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
public class ClienteAjaxAction extends ActionSupport {

    private String regimen;
    private String cadena;
    private List<EmpresaCliente> clientes = new ArrayList<EmpresaCliente>();
    private String salida;
    private long ruc;
    private String param; // parámetro para recibir el valor del campo a actualizar
    private String paramRetorno;
    private String mensaje; // mensaje para las acciones de edit 
    private String term;
    private List<EmpresaClienteSerializable> listaClientesSrl = new ArrayList<EmpresaClienteSerializable>();
    private List<ProveedorSerializable> listaProveedoresSrl = new ArrayList<ProveedorSerializable>();
    private List<CompradorSerializable> listaCompradoresSrl = new ArrayList<CompradorSerializable>();
    private List<ProductoVentasSerializable> listaProductosVentasSrl = new ArrayList<ProductoVentasSerializable>();
    private List<ProductoComprasSerializable> listaProductosComprasSrl = new ArrayList<ProductoComprasSerializable>();

    public String showDialogClientes() {
        if (getRegimen() == null || getRegimen().trim().equals("")) {
            return ERROR;
        }

        String[] regs = getRegimen().split("-");

        // si la cadena no está vacía
        if (!getCadena().trim().equals("")) {
            // consultar pasandole el tipo de regimen y la cadena   
            for (String s : regs) {
                if (s.equals("all")) {
                    // obtener la lista de todos los clientes rg que contienen la cadena
                    //clientes.add(new Cliente(20808080809L, "ALL - Cadena llena", "RG"));
                }

                if (s.equals("rg")) {
                    // obtener la lista de todos los clientes rg que contienen la cadena
                    //clientes.add(new Cliente(20808080809L, "RG - Cadena llena", "RG"));
                }

                if (s.equals("rer")) {
                    // obtener la lista de todos los clientes rer que contienen la cadena
                    //clientes.add(new Cliente(20808080809L, "RG - Cadena llena", "RER"));
                }

                if (s.equals("nrus")) {
                    //obtener la lista de todos los clientes nrus que contienen la cadena
                    //clientes.add(new Cliente(20808080809L, "RG - Cadena llena", "NRUS"));
                }
            }

        } else {
            for (String s : regs) {
                if (s.equals("all")) {
                    // obtener la lista de todos los clientes rg que contienen la cadena
                    //clientes.add(new Cliente(20808080809L, "ALL - Cadena vacía", "RG"));
                }

                if (s.equals("rg")) {
                    // obtener la lista de todos los clientes rg
                    /*clientes.add(new Cliente(20101010101L, "Empresa XYZ", "RG"));
                     clientes.add(new Cliente(20202020202L, "Trujillo Empresa S.A.C.", "RG"));
                     clientes.add(new Cliente(20202020202L, "Empresa Trabajando SRL", "RG"));
                     clientes.add(new Cliente(20202020202L, "Comprando SRL", "RG"));
                     clientes.add(new Cliente(20303030303L, "Empresa Perú SAC", "RG"));
                     clientes.add(new Cliente(20404040405L, "Empresa ABC", "RG"));
                     clientes.add(new Cliente(20505050506L, "Perú Trabaja SRL", "RG"));
                     clientes.add(new Cliente(20606060607L, "Perú Avanza SA", "RG"));
                     clientes.add(new Cliente(20808080809L, "Perú SAC", "RG"));
                     clientes.add(new Cliente(20808080809L, "RG - Cadena Vacía", "RG"));*/
                }

                if (s.equals("rer")) {
                    // obtener la lista de todos los clientes rer
                    //clientes.add(new Cliente(20808080809L, "RER - Cadena Vacía", "RER"));
                }

                if (s.equals("nrus")) {
                    //obtener la lista de todos los clientes nrus
                    //clientes.add(new Cliente(20808080809L, "NRUS - Cadena Vacía", "NRUS"));
                }
            }
        }

        //continua



        StringBuilder buffer = new StringBuilder("<ul>");

        for (EmpresaCliente c : clientes) {
            //buffer.append(String.format("<li><div class=\"dElementoListaClientes\">%s \n<input type=\"hidden\" class=\"dElcRuc\" value=\"%s\"/>\n<input type=\"hidden\" class=\"dElcRegimen\" value=\"%s\"/>\n</div></li>\n", c.getRazonSocial(), c.getRuc(), c.getTipoRegimen()));
        }

        buffer.append("</ul>");
        salida = buffer.toString();

        return "showDialogClientes";
    }

    public String editRuc() {
        try {
            long nuevoRuc;

            if (getParam().matches("^(1|2)\\d{10}$")) {
                nuevoRuc = Long.parseLong(getParam());
            } else {
                this.setMensaje("error");
                return ERROR;
            }

            // actualizar el campo con un DAO
            // buscar mediante el ruc al cliente
            HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

            DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
            EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

            EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
            empresaCliente.setRuc(nuevoRuc);

            // devolver el nuevo valor escrito en la BD
            this.setParamRetorno(Long.toString(empresaCliente.getRuc()));
            this.setMensaje("ok");

            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
            return "edit";
        } catch (Exception e) {
            this.setMensaje("error");
            return ERROR;
        }
    }

    public String editRazonSocial() {
        String nuevoParam = this.getParam();
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.setRazonSocial(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getRazonSocial());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editNombreEmpresa() {
        String nuevoParam = this.getParam();
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.setNombreEmpresa(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getNombreEmpresa());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editTipoRegimen() {
        String nuevoParam = this.getParam();
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.setTipoRegimen(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getTipoRegimen());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editEstado() {
        String nuevoParam = this.getParam();
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.setEstado(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getEstado());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editDescripcion() {
        String nuevoParam = this.getParam();
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.setDescripcion(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getDescripcion());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editDireccion() {
        String nuevoParam = this.getParam();
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.setDireccion(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getDireccion());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editTelefono1() {
        String nuevoParam = this.getParam();
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.setTelefono1(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getTelefono1());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editTelefono2() {
        String nuevoParam = this.getParam();
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.setTelefono2(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getTelefono2());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editFechaFrontera() {
        try {
            Byte nuevoParam;

            if (getParam() == null || getParam().trim().equals("")) {
                nuevoParam = null;
            } else {
                nuevoParam = Byte.parseByte(getParam());
                // el param no puede ser un número no fecha del mes
                if (nuevoParam < 1 || nuevoParam > 31) {
                    this.setMensaje("error");
                    return ERROR;
                }
            }

            // buscar cliente mediante ruc y actualizar el campo
            HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

            DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
            EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

            EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
            empresaCliente.setFechaFrontera(nuevoParam);

            // devolver el nuevo valor escrito en la BD
            this.setParamRetorno(empresaCliente.getFechaFrontera() == null ? "" : Byte.toString(empresaCliente.getFechaFrontera()));
            this.setMensaje("ok");

            HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
            return "edit";

        } catch (Exception e) {
            this.setMensaje("error");
            return ERROR;
        }
    }

    public String editNombresContacto() {
        String nuevoParam = this.getParam();
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.getContacto().setNombres(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getContacto().getNombres());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editApellidosContacto() {
        String nuevoParam = this.getParam();
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.getContacto().setApellidos(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getContacto().getApellidos());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editDireccionContacto() {
        String nuevoParam = this.getParam();
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.getContacto().setDireccion(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getContacto().getDireccion());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editCelularContacto() {
        String nuevoParam = this.getParam();
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.getContacto().setCelular(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getContacto().getCelular());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editEmailPrincipalContacto() {
        String nuevoParam = this.getParam();

        if (!nuevoParam.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            this.setMensaje("error");
            return ERROR;
        }

        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.getContacto().setEmailPrincipal(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getContacto().getEmailPrincipal());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editPasswordContacto() {
        String nuevoParam = this.getParam().trim();
        
        if (!nuevoParam.matches("^\\w{6,}$")) {
            this.setMensaje("La contraseña no tiene el formato correcto");
            return "edit";
        }
        
        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();
        
        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        // encriptación con Shiro
        ByteSource salt = new SecureRandomNumberGenerator().nextBytes();
        String hashedPassword = new Sha512Hash(nuevoParam, salt, 200000).toHex();
        empresaCliente.getContacto().setPassword(hashedPassword);
        empresaCliente.getContacto().setSalt(salt.toHex());
       
        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getContacto().getPassword());
        this.setMensaje("ok");
        return "edit";
    }

    public String editEmailSecundarioContacto() {
        String nuevoParam = this.getParam();

        if (!nuevoParam.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            this.setMensaje("error");
            return ERROR;
        }

        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.getContacto().setEmailSecundario(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getContacto().getEmailSecundario());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editEmailFacebookContacto() {
        String nuevoParam = this.getParam();

        if (!nuevoParam.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            this.setMensaje("error");
            return ERROR;
        }

        // buscar cliente mediante ruc y actualizar el campo
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());
        empresaCliente.getContacto().setEmailFacebook(nuevoParam);

        // devolver el nuevo valor escrito en la BD
        this.setParamRetorno(empresaCliente.getContacto().getEmailFacebook());
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String editLibrosContablesHabilitados() {
        String nuevoParam = this.getParam();

        String[] boleanos = nuevoParam.split("-");

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        EmpresaCliente empresaCliente = empresaDAO.findByRuc(getRuc());

        if (boleanos[0].equals("true")) {
            empresaCliente.setRegistroComprasHabilitado(true);
        } else {
            empresaCliente.setRegistroComprasHabilitado(false);
        }

        if (boleanos[1].equals("true")) {
            empresaCliente.setRegistroVentasHabilitado(true);
        } else {
            empresaCliente.setRegistroVentasHabilitado(false);
        }

        if (boleanos[2].equals("true")) {
            empresaCliente.setDiarioSimplificadoHabilitado(true);
        } else {
            empresaCliente.setDiarioSimplificadoHabilitado(false);
        }

        this.setParamRetorno(nuevoParam);
        this.setMensaje("ok");

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "edit";
    }

    public String list() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        List<EmpresaCliente> lista = empresaDAO.findByTerm(getTerm());

        for (EmpresaCliente e : lista) {
            EmpresaClienteSerializable ecs = new EmpresaClienteSerializable();
            ecs.setLabel(e.getRazonSocial());
            ecs.setRuc(e.getRuc());
            ecs.setRazonSocial(e.getRazonSocial());
            this.getListaClientesSrl().add(ecs);
        }

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "list";
    }

    public String listProveedoresByCliente() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        List<Proveedor> lista = empresaDAO.findProveedoresByTerm(this.getRuc(), this.getTerm());

        for (Proveedor p : lista) {
            ProveedorSerializable pSrl = new ProveedorSerializable();
            pSrl.setIdProveedor(p.getIdProveedor());
            pSrl.setRazonSocial(p.getRazonSocial());
            pSrl.setNumeroDocumentoIdentidad(p.getNumeroDocumentoIdentidad());
            pSrl.setTipoDocNumero(p.getTipoDocumentoIdentidad().getNumero());
            pSrl.setTipoDocDescripcion(p.getTipoDocumentoIdentidad().getDescripcion());
            this.getListaProveedoresSrl().add(pSrl);
        }

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "listProveedoresByCliente";
    }

    public String listCompradoresByCliente() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        List<Comprador> lista = empresaDAO.findCompradoresByTerm(this.getRuc(), this.getTerm());

        for (Comprador c : lista) {
            CompradorSerializable cSrl = new CompradorSerializable();
            cSrl.setIdComprador(c.getIdComprador());
            cSrl.setRazonSocialONombres(c.getRazonSocialONombres());
            cSrl.setNumeroDocumentoIdentidad(c.getNumeroDocumentoIdentidad());
            cSrl.setTipoDocNumero(c.getTipoDocumentoIdentidad().getNumero());
            cSrl.setTipoDocDescripcion(c.getTipoDocumentoIdentidad().getDescripcion());
            this.getListaCompradoresSrl().add(cSrl);
        }

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "listCompradoresByCliente";
    }

    public String listProductosVentasByCliente() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        List<ProductoVentas> lista = empresaDAO.findProductosVentasByTerm(this.getRuc(), this.getTerm());

        for (ProductoVentas p : lista) {
            ProductoVentasSerializable pSrl = new ProductoVentasSerializable();
            pSrl.setIdProductoVentas(p.getIdProductoVentas());
            pSrl.setStock(p.getStock());
            pSrl.setNombre(p.getNombre());
            pSrl.setPrecio(p.getPrecio());
            pSrl.setUnidadDeMedida(p.getUnidadDeMedida());
            this.getListaProductosVentasSrl().add(pSrl);
        }

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "listProductosVentasByCliente";
    }

    public String listProductosComprasByCliente() {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        EmpresaClienteDAO empresaDAO = factory.getEmpresaClienteDAO();

        List<ProductoCompras> lista = empresaDAO.findProductosComprasByTerm(this.getRuc(), this.getTerm());

        for (ProductoCompras p : lista) {
            ProductoComprasSerializable pSrl = new ProductoComprasSerializable();
            pSrl.setIdProductoCompras(p.getIdProductoCompras());
            pSrl.setStock(p.getStock());
            pSrl.setNombre(p.getNombre());
            pSrl.setPrecio(p.getPrecio());
            pSrl.setUnidadDeMedida(p.getUnidadDeMedida());
            this.getListaProductosComprasSrl().add(pSrl);
        }

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        return "listProductosComprasByCliente";
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public List<EmpresaCliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<EmpresaCliente> clientes) {
        this.clientes = clientes;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public long getRuc() {
        return ruc;
    }

    public void setRuc(long ruc) {
        this.ruc = ruc;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getParamRetorno() {
        return paramRetorno;
    }

    public void setParamRetorno(String paramRetorno) {
        this.paramRetorno = paramRetorno;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<EmpresaClienteSerializable> getListaClientesSrl() {
        return listaClientesSrl;
    }

    public void setListaClientesSrl(List<EmpresaClienteSerializable> listaClientesSrl) {
        this.listaClientesSrl = listaClientesSrl;
    }

    public List<ProveedorSerializable> getListaProveedoresSrl() {
        return listaProveedoresSrl;
    }

    public void setListaProveedoresSrl(List<ProveedorSerializable> listaProveedoresSrl) {
        this.listaProveedoresSrl = listaProveedoresSrl;
    }

    public List<CompradorSerializable> getListaCompradoresSrl() {
        return listaCompradoresSrl;
    }

    public void setListaCompradoresSrl(List<CompradorSerializable> listaCompradoresSrl) {
        this.listaCompradoresSrl = listaCompradoresSrl;
    }

    public List<ProductoVentasSerializable> getListaProductosVentasSrl() {
        return listaProductosVentasSrl;
    }

    public void setListaProductosVentasSrl(List<ProductoVentasSerializable> listaProductosVentasSrl) {
        this.listaProductosVentasSrl = listaProductosVentasSrl;
    }

    public List<ProductoComprasSerializable> getListaProductosComprasSrl() {
        return listaProductosComprasSrl;
    }

    public void setListaProductosComprasSrl(List<ProductoComprasSerializable> listaProductosComprasSrl) {
        this.listaProductosComprasSrl = listaProductosComprasSrl;
    }
}
