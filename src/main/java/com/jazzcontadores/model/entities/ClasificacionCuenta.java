package com.jazzcontadores.model.entities;

// Generated 07/04/2013 11:07:12 PM by Hibernate Tools 3.2.1.GA
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Venegas
 */
@Entity
@Table(name = "clasificacioncuenta", catalog = "jazzcontadores")
public class ClasificacionCuenta implements java.io.Serializable {

    private Byte idClasificacionCuenta;
    private String denominacion;
    private ElementoPlanCuentas elementoPlanCuentas;

    @Id
    @Column(name = "idClasificacionCuenta", unique = true, nullable = false)
    public Byte getIdClasificacionCuenta() {
        return idClasificacionCuenta;
    }

    public void setIdClasificacionCuenta(Byte idClasificacionCuenta) {
        this.idClasificacionCuenta = idClasificacionCuenta;
    }

    @Column(name = "denominacion", nullable = false, length = 200)
    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idElementoPlanCuentas", nullable = false)
    public ElementoPlanCuentas getElementoPlanCuentas() {
        return elementoPlanCuentas;
    }

    public void setElementoPlanCuentas(ElementoPlanCuentas elementoPlanCuentas) {
        this.elementoPlanCuentas = elementoPlanCuentas;
    }
}
