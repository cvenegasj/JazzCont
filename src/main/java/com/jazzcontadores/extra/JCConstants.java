/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.extra;

import java.math.BigDecimal;

/**
 *
 * @author Venegas
 */
public class JCConstants {

    public static final String USER = "sesionUsuario";
    // tipos sesiones
    public static final String SESION_ADMIN = "sesionAdmin";
    public static final String SESION_CLIENTE = "sesionCliente";
    public static final String SESION_CLIENTE_RG = "sesionClienteRG";
    public static final String SESION_CLIENTE_NRUS = "sesionClienteNRUS";
    public static final String SESION_CLIENTE_RER = "sesionClienteRER";
    public static final String FAILURE = "failed";
    // roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_CLIENTE = "CLIENTE";
    public static final String ROLE_CLIENTE_RG = "CLIENTE_RG";
    public static final String ROLE_CLIENTE_NRUS = "CLIENTE_NRUS";
    public static final String ROLE_CLIENTE_RER = "CLIENTE_RER";
    // tipos regimen
    public static final String REGIMEN_RG = "RG";
    public static final String REGIMEN_RER = "RER";
    public static final String REGIMEN_RUS = "NRUS";
    // valores estaticos
    public static final BigDecimal IGV = new BigDecimal("0.18");
    public static final BigDecimal BASE = new BigDecimal("0.82");
}
