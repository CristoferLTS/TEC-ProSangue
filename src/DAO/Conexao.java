package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Conexao {
    
    private final String DRIVER = "org.postgresql.Driver";
    private final String URL = "jdbc:postgresql://localhost:5432/postgres/ProSangue";
    private final String USER = "postgres";
    private final String PASS = "postgres";
    
    public Connection getConnection() {
        //faço a conexao com o banco de dados
        try {
           Class.forName(DRIVER);
           return (Connection) DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"Erro em SQL");
        }
        return null;
        
    }
    
    public static void closeConnection(Connection con){
        //fecho a conexao com o banco de dados
        if(con != null){
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
      
}