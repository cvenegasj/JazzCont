package com.jazzcontadores.model.entities;
// Generated 07/04/2013 11:07:12 PM by Hibernate Tools 3.2.1.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Contador generated by hbm2java
 */
@Entity
@Table(name = "contador", catalog = "jazzcontadores", uniqueConstraints = {
    @UniqueConstraint(columnNames = "emailPrincipal")})
public class Contador implements java.io.Serializable {

    private Integer idContador;
    private String emailPrincipal;
    private String password;
    private String nombres;
    private String apellidos;
    private String emailFacebook;
    private boolean esAdministrador;
    private String salt;

    public Contador() {
    }

    public Contador(String emailPrincipal, String password, String nombres, String apellidos, boolean esAdministrador) {
        this.emailPrincipal = emailPrincipal;
        this.password = password;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.esAdministrador = esAdministrador;
    }

    public Contador(String emailPrincipal, String password, String nombres, String apellidos, String emailFacebook, boolean esAdministrador) {
        this.emailPrincipal = emailPrincipal;
        this.password = password;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.emailFacebook = emailFacebook;
        this.esAdministrador = esAdministrador;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "idContador", unique = true, nullable = false)
    public Integer getIdContador() {
        return this.idContador;
    }

    public void setIdContador(Integer idContador) {
        this.idContador = idContador;
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

    @Column(name = "emailFacebook", length = 75)
    public String getEmailFacebook() {
        return this.emailFacebook;
    }

    public void setEmailFacebook(String emailFacebook) {
        this.emailFacebook = emailFacebook;
    }

    @Column(name = "esAdministrador", nullable = false)
    public boolean isEsAdministrador() {
        return this.esAdministrador;
    }

    public void setEsAdministrador(boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    @Column(name = "salt", nullable = false, length = 100)
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
