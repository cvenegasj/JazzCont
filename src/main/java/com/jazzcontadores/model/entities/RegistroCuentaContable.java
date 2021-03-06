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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Registrocuentacontable generated by hbm2java
 */
@Entity
@Table(name = "registrocuentacontable", catalog = "jazzcontadores")
public class RegistroCuentaContable implements java.io.Serializable {

    private Integer idRegistroCuentaContable;
    private CuentaContable cuentaContable;
    private String mes;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean estaSaldada;
    private boolean estaCerrada;
    private List<DetalleCargo> detallesCargos = new ArrayList<DetalleCargo>();
    private List<DetalleAbono> detallesAbonos = new ArrayList<DetalleAbono>();

    public RegistroCuentaContable() {
    }

    public RegistroCuentaContable(CuentaContable cuentacontable, String mes, Date fechaInicio, Date fechaFin, boolean estaSaldada, boolean estaCerrada) {
        this.cuentaContable = cuentacontable;
        this.mes = mes;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estaSaldada = estaSaldada;
        this.estaCerrada = estaCerrada;
    }

    public RegistroCuentaContable(CuentaContable cuentacontable, String mes, Date fechaInicio, Date fechaFin, boolean estaSaldada, boolean estaCerrada, List<DetalleCargo> detallecargos, List<DetalleAbono> detalleabonos) {
        this.cuentaContable = cuentacontable;
        this.mes = mes;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estaSaldada = estaSaldada;
        this.estaCerrada = estaCerrada;
        this.detallesCargos = detallecargos;
        this.detallesAbonos = detalleabonos;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "idRegistroCuentaContable", unique = true, nullable = false)
    public Integer getIdRegistroCuentaContable() {
        return this.idRegistroCuentaContable;
    }

    public void setIdRegistroCuentaContable(Integer idRegistroCuentaContable) {
        this.idRegistroCuentaContable = idRegistroCuentaContable;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numeroCuentaContable", nullable = false)
    public CuentaContable getCuentaContable() {
        return this.cuentaContable;
    }

    public void setCuentaContable(CuentaContable cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    @Column(name = "mes", nullable = false, length = 25)
    public String getMes() {
        return this.mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
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

    @Column(name = "estaSaldada", nullable = false)
    public boolean isEstaSaldada() {
        return this.estaSaldada;
    }

    public void setEstaSaldada(boolean estaSaldada) {
        this.estaSaldada = estaSaldada;
    }

    @Column(name = "estaCerrada", nullable = false)
    public boolean isEstaCerrada() {
        return this.estaCerrada;
    }

    public void setEstaCerrada(boolean estaCerrada) {
        this.estaCerrada = estaCerrada;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registroCuentaContable")
    public List<DetalleCargo> getDetallesCargos() {
        return this.detallesCargos;
    }

    public void setDetallesCargos(List<DetalleCargo> detallesCargos) {
        this.detallesCargos = detallesCargos;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registroCuentaContable")
    public List<DetalleAbono> getDetallesAbonos() {
        return this.detallesAbonos;
    }

    public void setDetallesAbonos(List<DetalleAbono> detallesAbonos) {
        this.detallesAbonos = detallesAbonos;
    }
}
