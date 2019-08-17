/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import static documentosview.frmArquivo.jProgressBar2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.bean.ArquivoDocumentos;
import modelo.bean.IdArquivoDocumento;
import modelo.bean.Usuario;
import produzirconeccao.ConexaoFirebird;
import produzirconeccao.ConexaoFirebirdTexto;

/**
 *
 * @author Pituba
 */
public class DocumentosTextosDAO {
    
    FileOutputStream output;
    InputStream input;
    
    public void salvar_texto_decretos(ArquivoDocumentos ad){
       
        Connection cnfb = ConexaoFirebirdTexto.getConnection();
     
        PreparedStatement stmt = null;
        
        try {
            stmt = cnfb.prepareStatement("INSERT INTO DECRETOS (NOMEDOCUMENTO, TEXTODOCUMENTO)\n"
                    + " VALUES (?, ?)");
            
            stmt.setString(1, ad.getNomeDocumento());
            //stmt.setString(2, ad.getTextoDocumento());
            //stmt.setString(3, ad.getPdf());
            //if(ad.getFiguraDocumento() == null){
               //stmt.setBinaryStream(4, ad.getArquivo());
            //}else{
              stmt.setBytes(2, ad.getFiguraDocumento());
            //}
            //stmt.setDate(5, ad.getData());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no processo de Arquivamento! " + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebirdTexto.closeConnection(cnfb, stmt);
        }
        
    }   
    
    public void salvar_decretos(ArquivoDocumentos ad){
       
        Connection cnfb = ConexaoFirebird.getConnection();
     
        PreparedStatement stmt = null;
        
        try {
            stmt = cnfb.prepareStatement("INSERT INTO DECRETOS (NOMEDOCUMENTO, TEXTODOCUMENTO, PDF, FIGURADOCUMENTO,  DATA)\n"
                    + " VALUES (?, ?, ?, ?, ?)");
            
            stmt.setString(1, ad.getNomeDocumento());
            stmt.setString(2, ad.getTextoDocumento());
            stmt.setString(3, ad.getPdf());
            if(ad.getFiguraDocumento() == null){
               stmt.setBinaryStream(4, ad.getArquivo());
            }else{
               stmt.setBytes(4, ad.getFiguraDocumento());
            }
            stmt.setDate(5, ad.getData());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no processo de Arquivamento! " + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(cnfb, stmt);
        }
        
    }   
    
    public void salvar_texto_portarias(ArquivoDocumentos ad){
    
        Connection cnfb = ConexaoFirebirdTexto.getConnection();
        
        PreparedStatement stmt = null;
        
        try{
            stmt = cnfb.prepareStatement("INSERT INTO PORTARIA (NOMEDOCUMENTO, TEXTODOCUMENTO)\n"
                     + " VALUES (?, ?)");
            
            stmt.setString(1, ad.getNomeDocumento());
            //stmt.setString(2, ad.getTextoDocumento());
            //stmt.setString(3, ad.getPdf());
            //if(ad.getFiguraDocumento() == null){
               //stmt.setBinaryStream(4, ad.getArquivo());
            //}else{
               stmt.setBytes(2, ad.getFiguraDocumento());
            //}
            //stmt.setDate(5, ad.getData());
            stmt.executeUpdate();
        }catch (SQLException ex){
           JOptionPane.showMessageDialog(null, "Erro no processo de Arquivamento! " + ex,
                   "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebirdTexto.closeConnection(cnfb, stmt);
        
        }
            
      }
    
    public void salvar_portarias(ArquivoDocumentos ad){
    
        Connection cnfb = ConexaoFirebird.getConnection();
        
        PreparedStatement stmt = null;
        
        try{
            stmt = cnfb.prepareStatement("INSERT INTO PORTARIA (NOMEDOCUMENTO, TEXTODOCUMENTO, PDF, FIGURADOCUMENTO,  DATA)\n"
                     + " VALUES (?, ?, ?, ?, ?)");
            
            stmt.setString(1, ad.getNomeDocumento());
            stmt.setString(2, ad.getTextoDocumento());
            stmt.setString(3, ad.getPdf());
            if(ad.getFiguraDocumento() == null){
               stmt.setBinaryStream(4, ad.getArquivo());
            }else{
               stmt.setBytes(4, ad.getFiguraDocumento());
            }
            stmt.setDate(5, ad.getData());
            stmt.executeUpdate();
        }catch (SQLException ex){
           JOptionPane.showMessageDialog(null, "Erro no processo de Arquivamento! " + ex,
                   "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(cnfb, stmt);
        
        }
            
      }
    
    public void excluir_portaria(int arqdoc){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
     
        try {
            
            stmt = con.prepareStatement("DELETE FROM PORTARIA WHERE ID = ?");
         
            stmt.setInt(1, arqdoc);
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir documento " + ex,
                    "TechScan.", JOptionPane.ERROR_MESSAGE);
        }finally{       
            ConexaoFirebird.closeConnection(con, stmt);
            
        }
    }
    
        public void excluir_decreto(int arqdoc){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
     
        try {
            
            stmt = con.prepareStatement("DELETE FROM DECRETOS WHERE ID = ?");
         
            stmt.setInt(1, arqdoc);
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir documento " + ex,
                    "TechScan.", JOptionPane.ERROR_MESSAGE);
        }finally{       
            ConexaoFirebird.closeConnection(con, stmt);
            
        }
    }
        
        public void atualizar_portaria(IdArquivoDocumento arqdoc){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        
     
        try {
            
            stmt = con.prepareStatement("UPDATE PORTARIA SET NOMEDOCUMENTO = ?,TEXTODOCUMENTO = ?\n" +
                    "WHERE ID = ?");
            stmt.setString(1, arqdoc.getNomeDocumento());
            stmt.setString(2, arqdoc.getTextoDocumento());
            stmt.setInt(3,arqdoc.getId());
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no processo de atualização! " + ex, 
                    
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{       
            ConexaoFirebird.closeConnection(con, stmt);
            
        }
    } 
        
        public void atualizar_decreto(IdArquivoDocumento arqdoc){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
     
        try {
            
            stmt = con.prepareStatement("UPDATE DECRETOS SET NOMEDOCUMENTO = ?,TEXTODOCUMENTO = ?\n" +
                    "WHERE ID = ?");
            stmt.setString(1, arqdoc.getNomeDocumento());
            stmt.setString(2, arqdoc.getTextoDocumento());
            stmt.setInt(3,arqdoc.getId());
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no processo de atualização! " + ex,                    
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{       
            ConexaoFirebird.closeConnection(con, stmt);
            
        }
    }
    
    public List<ArquivoDocumentos> selecionadocumentodecreto (String palavradocumento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ArquivoDocumentos> selecionadocumentos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM DECRETOS WHERE UPPER(NOMEDOCUMENTO) LIKE ?");
            
            stmt.setString(1, "%"+palavradocumento+"%");
            rs = stmt.executeQuery();
            while(rs.next()){
            ArquivoDocumentos arqdoc = new ArquivoDocumentos();
            arqdoc.setId(rs.getInt("ID"));
            arqdoc.setNomeDocumento(rs.getString("NOMEDOCUMENTO"));
            arqdoc.setPdf(rs.getString("PDF"));
            arqdoc.setTextoDocumento(rs.getString("TEXTODOCUMENTO"));
            //arqdoc.setFiguraDocumento(rs.getBytes("FIGURADOCUMENTO"));
            //arqdoc.setData(rs.getDate("DATA"));
            selecionadocumentos.add(arqdoc);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no processo de seleção do decreto! " + ex,                    
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
         return selecionadocumentos;
    }
    
    public List<ArquivoDocumentos> selecionadocumentoportaria (String palavradocumento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ArquivoDocumentos> selecionadocumentos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM PORTARIA WHERE UPPER(NOMEDOCUMENTO) LIKE ?");
            
            stmt.setString(1, "%"+palavradocumento+"%");
            rs = stmt.executeQuery();
            while(rs.next()){
            ArquivoDocumentos arqdoc = new ArquivoDocumentos();
            arqdoc.setId(rs.getInt("ID"));
            arqdoc.setNomeDocumento(rs.getString("NOMEDOCUMENTO"));
            arqdoc.setPdf(rs.getString("PDF"));
            arqdoc.setTextoDocumento(rs.getString("TEXTODOCUMENTO"));
            //arqdoc.setFiguraDocumento(rs.getBytes("FIGURADOCUMENTO"));
            //arqdoc.setData(rs.getDate("DATA"));
            selecionadocumentos.add(arqdoc);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no processo de seleção da portaria! " + ex,                    
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
         return selecionadocumentos;
    }
    
    public List<ArquivoDocumentos> selecionardecretotexto(String documento){
         Connection con = ConexaoFirebirdTexto.getConnection();
         PreparedStatement stmt = null;
         ResultSet rs = null;
          List<ArquivoDocumentos> selecionadocumentos = new ArrayList<>();
         
        try {
            stmt = con.prepareStatement("SELECT NOMEDOCUMENTO, TEXTODOCUMENTO FROM DECRETOS \n"+
                    "WHERE NOMEDOCUMENTO = ?");
           
            stmt.setString(1, documento);
            rs = stmt.executeQuery();
            
                             while (rs.next()){
                              ArquivoDocumentos arqdoc = new ArquivoDocumentos();
                              arqdoc.setNomeDocumento(rs.getString("NOMEDOCUMENTO"));
                              arqdoc.setTextoDocumento(rs.getString("TEXTODOCUMENTO"));
                              selecionadocumentos.add(arqdoc);
                             }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro no processo de busca! " + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally {
            ConexaoFirebird.closeConnection(con, stmt, rs);
            
        }                                                    
        return selecionadocumentos;
    }
    
    public List<ArquivoDocumentos> selecionardecreto(String documento){
         Connection con = ConexaoFirebird.getConnection();
         PreparedStatement stmt = null;
         ResultSet rs = null;
          List<ArquivoDocumentos> selecionadocumentos = new ArrayList<>();
         
        try {
            stmt = con.prepareStatement("SELECT NOMEDOCUMENTO, TEXTODOCUMENTO FROM DECRETOS \n"+
                    "WHERE NOMEDOCUMENTO = ?");
           
            stmt.setString(1, documento);
            rs = stmt.executeQuery();
            
                             while (rs.next()){
                              ArquivoDocumentos arqdoc = new ArquivoDocumentos();
                              arqdoc.setNomeDocumento(rs.getString("NOMEDOCUMENTO"));
                              arqdoc.setTextoDocumento(rs.getString("TEXTODOCUMENTO"));
                              selecionadocumentos.add(arqdoc);
                             }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro no processo de busca! " + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally {
            ConexaoFirebird.closeConnection(con, stmt, rs);
            
        }                                                    
        return selecionadocumentos;
    }
    
    public List<ArquivoDocumentos> selecionarportaria(String documento){
         Connection con = ConexaoFirebird.getConnection();
         PreparedStatement stmt = null;
         ResultSet rs = null;
          List<ArquivoDocumentos> selecionadocumentos = new ArrayList<>();
         
        try {
            stmt = con.prepareStatement("SELECT NOMEDOCUMENTO, TEXTODOCUMENTO FROM PORTARIA \n"+
                    "WHERE NOMEDOCUMENTO = ?");
           
            stmt.setString(1, documento);
            rs = stmt.executeQuery();
            
                             while (rs.next()){
                              ArquivoDocumentos arqdoc = new ArquivoDocumentos();
                              arqdoc.setNomeDocumento(rs.getString("NOMEDOCUMENTO"));
                              arqdoc.setTextoDocumento(rs.getString("TEXTODOCUMENTO"));
                              selecionadocumentos.add(arqdoc);
                             }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro no processo de busca! " + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally {
            ConexaoFirebird.closeConnection(con, stmt, rs);
            
        }                                                    
        return selecionadocumentos;
    }
    
    public List<ArquivoDocumentos> selecionarportariatexto(String documento){
         Connection con = ConexaoFirebirdTexto.getConnection();
         PreparedStatement stmt = null;
         ResultSet rs = null;
          List<ArquivoDocumentos> selecionadocumentos = new ArrayList<>();
         
        try {
            stmt = con.prepareStatement("SELECT NOMEDOCUMENTO, TEXTODOCUMENTO FROM PORTARIA \n"+
                    "WHERE NOMEDOCUMENTO = ?");
           
            stmt.setString(1, documento);
            rs = stmt.executeQuery();
            
                             while (rs.next()){
                              ArquivoDocumentos arqdoc = new ArquivoDocumentos();
                              arqdoc.setNomeDocumento(rs.getString("NOMEDOCUMENTO"));
                              arqdoc.setTextoDocumento(rs.getString("TEXTODOCUMENTO"));
                              selecionadocumentos.add(arqdoc);
                             }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro no processo de busca! " + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally {
            ConexaoFirebird.closeConnection(con, stmt, rs);
            
        }                                                    
        return selecionadocumentos;
    }
    
    public List<ArquivoDocumentos> selecionatextodocumentodecreto (String palavradocumento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ArquivoDocumentos> selecionadocumentos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM DECRETOS WHERE UPPER(TEXTODOCUMENTO) LIKE ?");
            
            stmt.setString(1, "%"+palavradocumento+"%");
            rs = stmt.executeQuery();
            while(rs.next()){
            ArquivoDocumentos arqdoc = new ArquivoDocumentos();
            arqdoc.setId(rs.getInt("ID"));
            arqdoc.setNomeDocumento(rs.getString("NOMEDOCUMENTO"));
            arqdoc.setPdf(rs.getString("PDF"));
            arqdoc.setTextoDocumento(rs.getString("TEXTODOCUMENTO"));
            //arqdoc.setFiguraDocumento(rs.getBytes("FIGURADOCUMENTO"));
            //arqdoc.setData(rs.getDate("DATA"));
            selecionadocumentos.add(arqdoc);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro no processo de busca! " + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
         return selecionadocumentos;
    }
    
    public List<ArquivoDocumentos> selecionatextodocumentoportaria (String palavradocumento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ArquivoDocumentos> selecionadocumentos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM PORTARIA WHERE UPPER(TEXTODOCUMENTO) LIKE ?");
            
            stmt.setString(1, "%"+palavradocumento+"%");
            rs = stmt.executeQuery();
            while(rs.next()){
            ArquivoDocumentos arqdoc = new ArquivoDocumentos();
            arqdoc.setId(rs.getInt("ID"));
            arqdoc.setNomeDocumento(rs.getString("NOMEDOCUMENTO"));
            arqdoc.setPdf(rs.getString("PDF"));
            arqdoc.setTextoDocumento(rs.getString("TEXTODOCUMENTO"));
            //arqdoc.setFiguraDocumento(rs.getBytes("FIGURADOCUMENTO"));
            //arqdoc.setData(rs.getDate("DATA"));
            selecionadocumentos.add(arqdoc);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro no processo de busca! " + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
        }
         return selecionadocumentos;
    }
    
    public List<IdArquivoDocumento> selecionatextoimagedecreto (int iddocumento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<IdArquivoDocumento> selecionadocumentos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM DECRETOS WHERE ID = ?");
            
            stmt.setInt(1, iddocumento);
            rs = stmt.executeQuery();
            
            try {
                File arquivotemp = new File("C:\\Myprogrm\\tessdatadb\\arq_banco.pdf");
                output = new FileOutputStream(arquivotemp);
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(DocumentosTextosDAO.class.getName()).log(Level.SEVERE, null, ex);
                //JOptionPane.showMessageDialog(null, "Não gerou o arquivo!");
            }
             while(rs.next()){
            IdArquivoDocumento arqdoc = new IdArquivoDocumento();
            arqdoc.setId(rs.getInt("ID"));
            //arqdoc.setNomeDocumento(rs.getString("NOMEDOCUMENTO"));
            arqdoc.setTextoDocumento(rs.getString("TEXTODOCUMENTO"));
            if(rs.getString("PDF") != null){
               input = rs.getBinaryStream("FIGURADOCUMENTO");
               arqdoc.setArquivo(input);
               arqdoc.setFiguraDocumento(null);
                           byte[] buffer = new byte[1024];
                try {
                    while(input.read(buffer)>0){
                       output.write(buffer);
                    }
                } catch (IOException ex) {
                    //Logger.getLogger(DocumentosTextosDAO.class.getName()).log(Level.SEVERE, null, ex);
                    //JOptionPane.showMessageDialog(null, "Não gerou o arquivo!");
                }
            }else{
            arqdoc.setFiguraDocumento(rs.getBytes("FIGURADOCUMENTO"));
            arqdoc.setArquivo(null);
            }
            //arqdoc.setData(rs.getDate("DATA"));
            selecionadocumentos.add(arqdoc);
            

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro no processo de busca! aqui" + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
            if(input != null){
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(DocumentosTextosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(output != null){
                
                try {
                    output.close();
                } catch (IOException ex) {
                    Logger.getLogger(DocumentosTextosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
 
            }
        }
         return selecionadocumentos;
    }
    
    public List<IdArquivoDocumento> selecionatextoimageportaria (int iddocumento){
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<IdArquivoDocumento> selecionadocumentos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM PORTARIA WHERE ID = ?");
            
            stmt.setInt(1, iddocumento);
            rs = stmt.executeQuery();
            
            try {
                File arquivotemp = new File("C:\\Myprogrm\\tessdatadb\\arq_banco.pdf");
                output = new FileOutputStream(arquivotemp);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DocumentosTextosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
             while(rs.next()){
            IdArquivoDocumento arqdoc = new IdArquivoDocumento();
            arqdoc.setId(rs.getInt("ID"));
            //arqdoc.setNomeDocumento(rs.getString("NOMEDOCUMENTO"));
            arqdoc.setTextoDocumento(rs.getString("TEXTODOCUMENTO"));
            if(rs.getString("PDF") != null){
               input = rs.getBinaryStream("FIGURADOCUMENTO");
               arqdoc.setArquivo(input);
               arqdoc.setFiguraDocumento(null);
                           byte[] buffer = new byte[1024];
                try {
                    while(input.read(buffer)>0){
                       output.write(buffer);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(DocumentosTextosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
            arqdoc.setFiguraDocumento(rs.getBytes("FIGURADOCUMENTO"));
            arqdoc.setArquivo(null);
            }
            //arqdoc.setData(rs.getDate("DATA"));
            selecionadocumentos.add(arqdoc);
            

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro no processo de busca! " + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(con, stmt, rs);
            if(input != null){
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(DocumentosTextosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(output != null){
                
                try {
                    output.close();
                } catch (IOException ex) {
                    Logger.getLogger(DocumentosTextosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
 
            }
        }
         return selecionadocumentos;
    }
    
    public void salvar_usuarios(String usuario, String senha, String admin){
       
        Connection cnfb = ConexaoFirebird.getConnection();
     
        PreparedStatement stmt = null;
        
        try {
            stmt = cnfb.prepareStatement("INSERT INTO USUARIOS (USUARIO, SENHA, ADIMIN)\n"
                    + " VALUES (?, ?, ?)");
            
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            stmt.setString(3, admin);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar salvar usuário! " + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally{
            ConexaoFirebird.closeConnection(cnfb, stmt);
        }
        
    }   
    
    public List<Usuario> selecionarusuario(String senhausuario){
         Connection con = ConexaoFirebird.getConnection();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         List<Usuario> selecionausuario = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT ID, USUARIO, ADIMIN FROM USUARIOS \n"+
                    "WHERE SENHA = ?");
           
            stmt.setString(1, senhausuario);
            rs = stmt.executeQuery();
            Usuario usuario = new Usuario();
                             while (rs.next()){
                              usuario.setId(rs.getInt("ID"));
                              usuario.setUsuario(rs.getString("USUARIO")); 
                              usuario.setAdmin(rs.getString("ADIMIN"));
                              selecionausuario.add(usuario);
                             }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro no processo de busca! " + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally {
            ConexaoFirebird.closeConnection(con, stmt, rs);
            
        }                                                    
        return selecionausuario;
    }
    
    public List<Usuario> selecionaradmin(){
         Connection con = ConexaoFirebird.getConnection();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         List<Usuario> selecionausuario = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT ID, USUARIO, ADIMIN FROM USUARIOS \n"+
                    "WHERE ADIMIN = 'sim'");
           
            //stmt.setString(1, senhausuario);
            rs = stmt.executeQuery();
            Usuario usuario = new Usuario();
                             while (rs.next()){
                              usuario.setId(rs.getInt("ID"));
                              usuario.setUsuario(rs.getString("USUARIO")); 
                              usuario.setAdmin(rs.getString("ADIMIN"));
                              selecionausuario.add(usuario);
                             }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro no processo de busca! " + ex,
                    "TechScan", JOptionPane.ERROR_MESSAGE);
        }finally {
            ConexaoFirebird.closeConnection(con, stmt, rs);
            
        }                                                    
        return selecionausuario;
    }
    
}
