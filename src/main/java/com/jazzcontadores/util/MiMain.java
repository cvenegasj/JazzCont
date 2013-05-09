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
import com.jazzcontadores.model.entities.Comprador;
import com.jazzcontadores.model.entities.ComprobanteCompra;
import com.jazzcontadores.model.entities.ComprobanteVenta;
import com.jazzcontadores.model.entities.Contador;
import com.jazzcontadores.model.entities.DetalleLibroRegistroCompras;
import com.jazzcontadores.model.entities.DetalleLibroRegistroVentas;
import com.jazzcontadores.model.entities.EmpresaCliente;
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
               
        


        LibroRegistroVentas lrv = factory.getLibroRegistroVentasDAO().findById(1);
        
        // Proveedor            
        TipoDocumentoIdentidad tipoDocumento = factory.getTipoDocumentoIdentidadDAO().findById("6");
        Comprador comp = new Comprador(tipoDocumento, "Empresa X S.A.C", "22023232521");

        factory.getCompradorDAO().makePersistent(comp);

        DetalleLibroRegistroVentas detalle = new DetalleLibroRegistroVentas();
        detalle.setLibroRegistroVentas(lrv);
        detalle.setNumeroCorrelativo(1);
        detalle.setImporteTotal(new BigDecimal("200.00").setScale(2, RoundingMode.HALF_EVEN));

        // Comprobante
        TipoComprobantePagoODocumento tComprobante = factory.getTipoComprobantePagoODocumentoDAO().findById((byte) 1);
        ComprobanteVenta cVenta = new ComprobanteVenta();
        cVenta.setNumero("100");
        cVenta.setSerie("1");
        cVenta.setFechaEmision(new GregorianCalendar().getTime());
        cVenta.setBase(new BigDecimal("180.00").setScale(2, RoundingMode.HALF_EVEN));
        cVenta.setIgv(new BigDecimal("20.00").setScale(2, RoundingMode.HALF_EVEN));
        cVenta.setImporteTotal(new BigDecimal("200.00").setScale(2, RoundingMode.HALF_EVEN));
        cVenta.setTipoComprobantePagoODocumento(tComprobante);
        cVenta.setComprador(comp);

        factory.getComprobanteVentaDAO().makePersistent(cVenta);
        detalle.setComprobanteVenta(cVenta);
        factory.getDetalleLibroRegistroVentasDAO().makePersistent(detalle);
        
        

        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();

        System.out.println("Done :)");
    }
}
