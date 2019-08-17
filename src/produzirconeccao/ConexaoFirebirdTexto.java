/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produzirconeccao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Pituba
 */
public class ConexaoFirebirdTexto {
    private static Connection conexao;
           
           public ConexaoFirebirdTexto() throws ClassNotFoundException, SQLException {
               
           Class.forName("org.firebirdsql.jdbc.FBDriver");
           String url = "jdbc:firebirdsql:localhost/3050:C:/Myprogrm/tessdatadb/ARQUIVODOCUMENTOS2.FDB?encoding\\=WIN1252";

        conexao = DriverManager.getConnection(url, "SYSDBA", "masterkey");
    }
            public static Connection getConnection(){
        //JOptionPane.showMessageDialog(null,"passando aqui!");
              
        try {
            
          return conexao;
            //Class.forName(DRIVER);
            //return DriverManager.getConnection(URL,USER, PASS);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na conexão! "+ ex, "TechScan", JOptionPane.ERROR_MESSAGE);
        }
        return conexao;
}
    
        public static void closeConnection(Connection con){
       
                try {
                    if(con != null){
                    con.close();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao fechar conexão! " + ex, "TechScan", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        public static void closeConnection(Connection con, PreparedStatement stmt){
       
            closeConnection(con);
                try {
                  if(stmt != null){
                    stmt.close();
                  }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao fechar conexão e/ou Statement! " + ex, "TechScan", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs){
       
            closeConnection(con, stmt);
                try {
                  if(rs != null){
                    rs.close();
                  }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao fechar conexão e/ou Statement e/ou Resultset! " + ex, "TechScan", JOptionPane.ERROR_MESSAGE);
            }
        }
}
