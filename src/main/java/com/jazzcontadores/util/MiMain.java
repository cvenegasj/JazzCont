/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.util;

import com.jazzcontadores.model.dao.ContadorDAO;
import com.jazzcontadores.model.dao.EmpresaClienteDAO;
import com.jazzcontadores.model.dao.LibroRegistroComprasDAO;
import com.jazzcontadores.model.daoimpl.ComprobanteCompraDAOImpl;
import com.jazzcontadores.model.daoimpl.DetalleLibroRegistroComprasDAOImpl;
import com.jazzcontadores.model.daoimpl.EmpresaClienteDAOImpl;
import com.jazzcontadores.model.daoimpl.LibroRegistroComprasDAOImpl;
import com.jazzcontadores.model.daoimpl.ProveedorDAOImpl;
import com.jazzcontadores.model.daoimpl.TipoComprobantePagoODocumentoDAOImpl;
import com.jazzcontadores.model.daoimpl.TipoDocumentoIdentidadDAOImpl;
import com.jazzcontadores.model.entities.AsientoContable;
import com.jazzcontadores.model.entities.Comprador;
import com.jazzcontadores.model.entities.ComprobanteCompra;
import com.jazzcontadores.model.entities.ComprobanteVenta;
import com.jazzcontadores.model.entities.Contador;
import com.jazzcontadores.model.entities.DetalleLibroRegistroCompras;
import com.jazzcontadores.model.entities.DetalleLibroRegistroVentas;
import com.jazzcontadores.model.entities.EmpresaCliente;
import com.jazzcontadores.model.entities.LibroDiarioSimplificado;
import com.jazzcontadores.model.entities.LibroRegistroCompras;
import com.jazzcontadores.model.entities.LibroRegistroVentas;
import com.jazzcontadores.model.entities.Proveedor;
import com.jazzcontadores.model.entities.TipoComprobantePagoODocumento;
import com.jazzcontadores.model.entities.TipoDocumentoIdentidad;
import org.hibernate.Session;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;

/**
 *
 * @author Venegas
 */
public class MiMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
        /*EmpresaClienteDAO eDAO = factory.getEmpresaClienteDAO();
         ContadorDAO cDAO = factory.getContadorDAO();
        
         List<Proveedor> proveedores = eDAO.findProveedoresByTerm(20000000010L, "proveedor");
         System.out.println("length: " + proveedores.size());
        
         for (Proveedor p : proveedores) {
         System.out.format("%s\n", p.getRazonSocial());
         }*/

        /*
         Contador contador = cDAO.findById(2);
        
         ByteSource salt = new SecureRandomNumberGenerator().nextBytes();
         String hashedPassword = new Sha512Hash(contador.getPassword(), salt, 500000).toHex();                        
        
         contador.setPassword(hashedPassword);
         contador.setSalt(salt.toHex());*/

        List<EmpresaCliente> listaClientes = factory.getEmpresaClienteDAO().findAll();

        for (EmpresaCliente e : listaClientes) {
            for (LibroRegistroVentas lrv : e.getLibrosRegistroVentas()) {
                Collections.sort(lrv.getDetallesLibroRegistroVentas(), new Comparator<DetalleLibroRegistroVentas>() {
                    @Override
                    public int compare(DetalleLibroRegistroVentas d1, DetalleLibroRegistroVentas d2) {
                        return d1.getComprobanteVenta().getFechaEmision().compareTo(d2.getComprobanteVenta().getFechaEmision());
                    }
                });
                int i = 1;
                for (DetalleLibroRegistroVentas d : lrv.getDetallesLibroRegistroVentas()) {
                    d.setNumeroCorrelativo(i++);
                }
            }
            for (LibroRegistroCompras lrc : e.getLibrosRegistroCompras()) {
                Collections.sort(lrc.getDetallesLibroRegistroCompras(), new Comparator<DetalleLibroRegistroCompras>() {
                    @Override
                    public int compare(DetalleLibroRegistroCompras d1, DetalleLibroRegistroCompras d2) {
                        return d1.getComprobanteCompra().getFechaEmision().compareTo(d2.getComprobanteCompra().getFechaEmision());
                    }
                });
                int i = 1;
                for (DetalleLibroRegistroCompras d : lrc.getDetallesLibroRegistroCompras()) {
                    d.setNumeroCorrelativo(i++);
                }
            }
        }


        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();

        System.out.println("Done :)");
    }
}
