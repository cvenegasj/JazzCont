package com.jazzcontadores.model.entities;
// Generated 07/04/2013 11:07:12 PM by Hibernate Tools 3.2.1.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Contacto generated by hbm2java
 */
@Entity
@Table(name = "contacto", catalog = "jazzcontadores", uniqueConstraints = {
    @UniqueConstraint(columnNames = "idEmpresaCliente"),
    @UniqueConstraint(columnNames = "emailPrincipal")})
public class Contacto implements java.io.Serializable {

    private Integer idContacto;
    private EmpresaCliente empresaCliente;
    private String emailPrincipal;
    private String password;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String celular;
    private String emailSecundario;
    private String emailFacebook;
    private String salt;

    public Contacto() {
    }

    public Contacto(EmpresaCliente empresacliente, String emailPrincipal, String password, String nombres, String apellidos) {
        this.empresaCliente = empresacliente;
        this.emailPrincipal = emailPrincipal;
        this.password = password;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Contacto(EmpresaCliente empresacliente, String emailPrincipal, String password, String nombres, String apellidos, String direccion, String celular, String emailSecundario, String emailFacebook) {
        this.empresaCliente = empresacliente;
        this.emailPrincipal = emailPrincipal;
        this.password = password;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.celular = celular;
        this.emailSecundario = emailSecundario;
        this.emailFacebook = emailFacebook;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "idContacto", unique = true, nullable = false)
    public Integer getIdContacto() {
        return this.idContacto;
    }

    public void setIdContacto(Integer idContacto) {
        this.idContacto = idContacto;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEmpresaCliente", unique = true, nullable = false)
    public EmpresaCliente getEmpresaCliente() {
        return this.empresaCliente;
    }

    public void setEmpresaCliente(EmpresaCliente empresaCliente) {
        this.empresaCliente = empresaCliente;
    }

    @Column(name = "emailPrincipal", unique = true, nullable = false, length = 75)
    public String getEmailPrincipal() {
        return this.emailPrincipal;
    }

    public void setEmailPrincipal(String emailPrincipal) {
        this.emailPrincipal = emailPrincipal;
    }

    @Column(name = "password", nullable = false, length = 200)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "nombres", nullable = false, length = 200)
    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    @Column(name = "apellidos", nullable = false, length = 200)
    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Column(name = "direccion", length = 220)
    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Column(name = "celular", length = 45)
    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @Column(name = "emailSecundario", length = 75)
    public String getEmailSecundario() {
        return this.emailSecundario;
    }

    public void setEmailSecundario(String emailSecundario) {
        this.emailSecundario = emailSecundario;
    }

    @Column(name = "emailFacebook", length = 75)
    public String getEmailFacebook() {
        return this.emailFacebook;
    }

    public void setEmailFacebook(String emailFacebook) {
        this.emailFacebook = emailFacebook;
    }

    @Column(name = "salt", nullable = false, length = 100)
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
