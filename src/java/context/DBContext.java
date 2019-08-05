/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Hoang Cao
 */
public class DBContext {

    private static HashMap<String, String> mapEnv = new HashMap<>();

    private DBContext() {

    }

    static {
        try {
            System.out.println("DBContext.java - READ ONLY ONCE");
            InitialContext init = new InitialContext();
            Context con = (Context) init.lookup("java:/comp/env");
            String serverName = (String) con.lookup("serverName");
            String dbName = (String) con.lookup("dbName");
            String portNumber = (String) con.lookup("portNumber");
            String userName = (String) con.lookup("userName");
            String password = (String) con.lookup("password");

            mapEnv.put("serverName", serverName);
            mapEnv.put("dbName", dbName);
            mapEnv.put("portNumber", portNumber);
            mapEnv.put("userName", userName);
            mapEnv.put("password", password);

        } catch (NamingException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getEnviromentVariable(String name) {
        return mapEnv.get(name);
    }

    public static Connection getConnection() throws NamingException, ClassNotFoundException, SQLException {
        String serverName = (String) getEnviromentVariable("serverName");
        String dbName = (String) getEnviromentVariable("dbName");
        String portNumber = (String) getEnviromentVariable("portNumber");
        String userName = (String) getEnviromentVariable("userName");
        String password = (String) getEnviromentVariable("password");

        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userName, password);
    }
    
    public static void closeAllResources(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
            
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
