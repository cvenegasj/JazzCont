package com.jazzcontadores.model.entities;
// Generated 07/04/2013 11:07:12 PM by Hibernate Tools 3.2.1.GA

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Libroregistroventas generated by hbm2java
 */
@Entity
@Table(name = "libroregistroventas", catalog = "jazzcontadores", uniqueConstraints =
@UniqueConstraint(columnNames = {"periodo", "idEmpresaCliente"}))
public class LibroRegistroVentas implements java.io.Serializable {

    private Integer idLibroRegistroVentas;
    private EmpresaCliente empresaCliente;
    private Date periodo;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean estaCerrado;
    private List<DetalleLibroRegistroVentas> detallesLibroRegistroVentas = new ArrayList<DetalleLibroRegistroVentas>();

    public LibroRegistroVentas() {
    }

    public LibroRegistroVentas(EmpresaCliente empresacliente, Date periodo, Date fechaInicio, Date fechaFin, boolean estaCerrado) {
        this.empresaCliente = empresacliente;
        this.periodo = periodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estaCerrado = estaCerrado;
    }

    public LibroRegistroVentas(EmpresaCliente empresacliente, Date periodo, Date fechaInicio, Date fechaFin, boolean estaCerrado, List<DetalleLibroRegistroVentas> detallelibroregistroventases) {
        this.empresaCliente = empresacliente;
        this.periodo = periodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estaCerrado = estaCerrado;
        this.detallesLibroRegistroVentas = detallelibroregistroventases;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "idLibroRegistroVentas", unique = true, nullable = false)
    public Integer getIdLibroRegistroVentas() {
        return this.idLibroRegistroVentas;
    }

    public void setIdLibroRegistroVentas(Integer idLibroRegistroVentas) {
        this.idLibroRegistroVentas = idLibroRegistroVentas;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEmpresaCliente", nullable = false)
    public EmpresaCliente getEmpresaCliente() {
        return this.empresaCliente;
    }

    public void setEmpresaCliente(EmpresaCliente empresaCliente) {
        this.empresaCliente = empresaCliente;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "periodo", nullable = false, length = 10)
    public Date getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaInicio", nullable = false, length = 10)
    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaFin", nullable = false, length = 10)
    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Column(name = "estaCerrado", nullable = false)
    public boolean isEstaCerrado() {
        return this.estaCerrado;
    }

    public void setEstaCerrado(boolean estaCerrado) {
        this.estaCerrado = estaCerrado;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "libroRegistroVentas")
    @OrderBy("numeroCorrelativo")
    public List<DetalleLibroRegistroVentas> getDetallesLibroRegistroVentas() {
        return this.detallesLibroRegistroVentas;
    }

    public void setDetallesLibroRegistroVentas(List<DetalleLibroRegistroVentas> detallesLibroRegistroVentas) {
        this.detallesLibroRegistroVentas = detallesLibroRegistroVentas;
    }
}
