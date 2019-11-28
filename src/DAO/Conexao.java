package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Conexao {
    
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/PROSANGUE";
    private final String USER = "root";
    private final String PASS = "mysql";
    
    public Connection getConnection() {
        //faço a conexao com o banco de dados
        try {
           Class.forName(DRIVER);
           return (Connection) DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"Erro na Conexão");
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