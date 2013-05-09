/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazzcontadores.extra;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.JdbcUtils;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Venegas helped by: http://pastebin.com/ciWbfr7p
 */
public class CustomSecurityRealm extends JdbcRealm {

    protected boolean permissionsLookupEnabled = false;
    private static final Logger log = LoggerFactory.getLogger(CustomSecurityRealm.class);

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void setPermissionsQuery(String permissionsQuery) {
        this.permissionsQuery = permissionsQuery;
    }

    @Override
    public void setPermissionsLookupEnabled(boolean permissionsLookupEnabled) {
        this.permissionsLookupEnabled = permissionsLookupEnabled;
    }

    public void prepareDataSource() {
        MysqlDataSource jazzDataSource = new MysqlDataSource();
        jazzDataSource.setUser("root");
        jazzDataSource.setPassword("admin");
        jazzDataSource.setDatabaseName("jazzcontadores");
        jazzDataSource.setServerName("localhost");
        setDataSource(jazzDataSource);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        Connection conn = null;
        SimpleAuthenticationInfo info = null;

        try {
            prepareDataSource();
            conn = dataSource.getConnection();

            PasswdSalt passwdSalt = getPasswordForUser(conn, username);

            if (passwdSalt == null || passwdSalt.password == null) {
                throw new UnknownAccountException("Email desconocido. Intente nuevamente.");
            }

            info = new SimpleAuthenticationInfo(username, passwdSalt.password, getName());

            /*
            System.out.println("Returning SimpleAuthInfo: " + info + " for username: " + username);
            System.out.println("Session Id: " + SecurityUtils.getSubject().getSession().getId().toString());
            System.out.println("Password: " + String.valueOf(upToken.getPassword()));
            
            System.out.println("DB Pass: " + passwdSalt.password);
            System.out.println("DB Salt: " + passwdSalt.salt);    */    

            //info.setCredentialsSalt(new SimpleByteSource(passwdSalt.salt));
            info.setCredentialsSalt(ByteSource.Util.bytes(Hex.decode(passwdSalt.salt)));

            HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
            hcm.setHashAlgorithmName(Sha512Hash.ALGORITHM_NAME);
            hcm.setHashIterations(500000);
            hcm.setStoredCredentialsHexEncoded(true);           
            setCredentialsMatcher(hcm);

        } catch (SQLException e) {
            final String message = "Ocurrió un error SQL al tratar de auntenticar al usuario.";
            log.error(message, e);
            // Rethrow any SQL errors as an authentication exception
            throw new AuthenticationException(message, e);
        } finally {
            JdbcUtils.closeConnection(conn);
        }

        return info;
    }

    protected AuthenticationInfo buildAuthenticationInfo(String username, char[] password) {
        return new SimpleAuthenticationInfo(username, password, getName());
    }

    private PasswdSalt getPasswordForUser(Connection conn, String username) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        PasswdSalt pwd = null;
        boolean hasAccount = false;

        try {
            ps = conn.prepareStatement("SELECT password, salt FROM contacto c "
                    + "INNER JOIN empresacliente e "
                    + "ON e.idEmpresaCliente = c.idEmpresaCliente "
                    + "WHERE c.emailPrincipal = ? and e.estado = 'habilitado'");
            ps.setString(1, username);
            // Execute query
            rs = ps.executeQuery();

            hasAccount = rs.next();

            if (hasAccount) {
                String salt = null;
                String password = rs.getString(1);
                if (rs.getMetaData().getColumnCount() > 1) {
                    salt = rs.getString(2);
                }

                if (rs.next()) {
                    throw new AuthenticationException("Más de un usuario encontrado. El email debe ser único.");
                }

                pwd = new PasswdSalt(password, salt);
            } else {
                //si no encontró un registro en Contacto, busca en la tabla Contador   
                ps = conn.prepareStatement("SELECT password, salt FROM contador WHERE emailPrincipal = ?");
                ps.setString(1, username);

                rs = ps.executeQuery();

                hasAccount = rs.next();

                if (hasAccount) {
                    String salt = null;
                    String password = rs.getString(1);
                    if (rs.getMetaData().getColumnCount() > 1) {
                        salt = rs.getString(2);
                    }

                    if (rs.next()) {
                        throw new AuthenticationException("Más de un usuario encontrado. El email debe ser único.");
                    }

                    pwd = new PasswdSalt(password, salt);
                }
            }
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
        }

        return hasAccount ? pwd : null;
    }

    @Override
    protected Set getRoleNamesForUser(Connection conn, String username)
            throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Set roleNames = new LinkedHashSet();

        try {
            ps = conn.prepareStatement("SELECT e.tipoRegimen "
                    + "FROM empresacliente e "
                    + "INNER JOIN contacto c "
                    + "ON e.idEmpresaCliente = c.idEmpresaCliente "
                    + "WHERE c.emailPrincipal = ?");
            ps.setString(1, username);
            // Execute query
            rs = ps.executeQuery();

            boolean clientFound = false;

            // Loop over results and add each returned role to a set
            while (rs.next()) {
                String roleName = rs.getString(1);

                // Add the role to the list of names if it isn't null
                if (roleName != null) {
                    if (roleName.equals("RG")) {
                        roleNames.add(JCConstants.ROLE_CLIENTE_RG);
                    } else if (roleName.equals("NRUS")) {
                        roleNames.add(JCConstants.ROLE_CLIENTE_NRUS);
                    } else if (roleName.equals("RER")) {
                        roleNames.add(JCConstants.ROLE_CLIENTE_RER);
                    }
                    //se encontró al cliente
                    clientFound = true;
                } else {
                    if (log.isWarnEnabled()) {
                        log.warn("Null role name found while retrieving role names for user [" + username + "]");
                    }
                }
            }

            //si no se encontró el usuario en la tabla Cliente, se busca en la tabla Contador
            if (!clientFound) {
                ps = conn.prepareStatement("SELECT esAdministrador FROM Contador WHERE emailPrincipal = ?");
                ps.setString(1, username);
                rs = ps.executeQuery();

                while (rs.next()) {
                    boolean adminRole = rs.getBoolean(1);

                    // Si el role es 1, es administrador
                    if (adminRole) {
                        roleNames.add(JCConstants.ROLE_ADMIN);
                    } else {
                        //roleNames.add("ADMIN_WEAK");
                    }
                }

            }
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
        }

        return roleNames;
    }
}

class PasswdSalt {

    public String password;
    public String salt;

    public PasswdSalt(String password, String salt) {
        super();
        this.password = password;
        this.salt = salt;
    }
}
