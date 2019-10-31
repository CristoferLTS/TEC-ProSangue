package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import javax.swing.JOptionPane;

public class Dao {

    public int insert(String sql, Object[] dados) throws SQLException {
        //metodo para inserir no Banco, recebe como parâmetro um comando em SQL e um vetor de objetos os quais são 
        //verificados a tipagem do dado, e de acordo com a tipagem os campos do PreparedStatement 
        //são setados para então ser feita a inserção
        try {
            Object[] campos = dados;
            Conexao conexao = new Conexao();
            Connection con = conexao.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            for(int i=0;i<campos.length;i++){
                if(campos[i]instanceof String){
                    pst.setString(i+1, String.valueOf(campos[i]));
                }
                if(campos[i]instanceof Integer){
                    pst.setInt(i+1, Integer.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Date){
                    pst.setDate(i+1, Date.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Time){
                    pst.setTime(i+1, Time.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Double){
                    pst.setDouble(i+1, Double.valueOf(String.valueOf(campos[i])));
                }
                if (campos[i] instanceof byte[]) {
                    pst.setBytes(i + 1, (byte[]) campos[i]);
                }
            }
            int rs = pst.executeUpdate();
            con.close();
            return rs;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi Possível realizar a inserção "+ex);
        }
        return 0;
    }
    
    public int update(String sql, Object[] dados) throws SQLException {
        //metodo para atualizar o Banco, recebe como parâmetro um comando em SQL e um vetor de objetos os quais são 
        //verificados a tipagem do dado, e de acordo com a tipagem os campos do PreparedStatement 
        //são setados para então ser feita a atualização
        try {
            Object[] campos = dados;
            Conexao conexao = new Conexao();
            Connection con = conexao.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            for(int i=0;i<campos.length;i++){
                if(campos[i]instanceof String){
                    pst.setString(i+1, String.valueOf(campos[i]));
                }
                if(campos[i]instanceof Integer){
                    pst.setInt(i+1, Integer.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Date){
                    pst.setDate(i+1, Date.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Time){
                    pst.setTime(i+1, Time.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Double){
                    pst.setDouble(i+1, Double.valueOf(String.valueOf(campos[i])));
                }
                if (campos[i] instanceof byte[]) {
                    pst.setBytes(i + 1, (byte[]) campos[i]);
                }
            }
            int rs = pst.executeUpdate();
            con.close();
            return rs;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não Foi Possível Realizar a Atualização "+ex);
        }
        return 0;
    }
    
    
    public int delete(String sql, Object[] dados) throws SQLException {
        //metodo para excluir no Banco, recebe como parâmetro um comando em SQL e um vetor de objetos os quais são 
        //verificados a tipagem do dado, e de acordo com a tipagem os campos do PreparedStatement 
        //são setados para então ser feita a exclusão
        try {
            Object[] campos = dados;
            Conexao conexao = new Conexao();
            Connection con = conexao.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            for(int i=0;i<campos.length;i++){
                if(campos[i]instanceof String){
                    pst.setString(i+1, String.valueOf(campos[i]));
                }
                if(campos[i]instanceof Integer){
                    pst.setInt(i+1, Integer.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Date){
                    pst.setDate(i+1, Date.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Time){
                    pst.setTime(i+1, Time.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Double){
                    pst.setDouble(i+1, Double.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Boolean){
                    pst.setBoolean(+1, Boolean.valueOf(String.valueOf(campos[i])));
                }
                 if(campos[i]instanceof Float){
                    pst.setFloat(i+1, Float.valueOf(String.valueOf(campos[i])));
                }
            }
            int rs = pst.executeUpdate();
            con.close();
            return rs;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não Foi Possível Realizar a Exclusão "+ex);
        }
        return 0;
    }
    
    public ResultSet select(String sql, Object[] dados) throws SQLException {
        //metodo para fazer seleção no Banco, recebe como parâmetro um comando em SQL e um vetor de objetos os quais são 
        //verificados a tipagem do dado, e de acordo com a tipagem os campos do PreparedStatement 
        //são setados para então ser feita a seleção
        try {
            Object[] campos = dados;
            Conexao conexao = new Conexao();
            Connection con = conexao.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            for(int i=0;i<campos.length;i++){
                if(campos[i]instanceof String){
                    pst.setString(i+1, String.valueOf(campos[i]));
                }
                if(campos[i]instanceof Integer){
                    pst.setInt(i+1, Integer.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Date){
                    pst.setDate(i+1, Date.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Time){
                    pst.setTime(i+1, Time.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Double){
                    pst.setDouble(i+1, Double.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Float){
                    pst.setFloat(i+1, Float.valueOf(String.valueOf(campos[i])));
                }
                if(campos[i]instanceof Boolean){
                    pst.setBoolean(+1, Boolean.valueOf(String.valueOf(campos[i])));
                }
            }
            ResultSet resultado = pst.executeQuery();
            return resultado;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não Foi Possível Realizar a Seleção "+ex);
        }
        
        return null;
    }
    
    public ResultSet select(String sql) throws SQLException {
        //método polimorfico do anterior o qual apenas recebe um comando em SQL e realiza a seleção no banco
        try {
            Conexao conexao = new Conexao();
            Connection con = conexao.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            return resultado;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não Foi Possível Realizar a Seleção "+ex);
        }
        return null;
    }
}
