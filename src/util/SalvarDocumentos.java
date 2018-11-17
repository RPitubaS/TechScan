/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import documentosview.frmScanner;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.bean.ArquivoDocumentos;
import modelo.dao.DocumentosTextosDAO;
import produzirconeccao.ConexaoFirebird;

/**
 *
 * @author Pituba
 */
public class SalvarDocumentos {
    
    public void salvadecreto(ArquivoDocumentos aq, String resultado){
           
        try {
            ConexaoFirebird confb = new ConexaoFirebird(resultado);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Sem conexão! "+ex, "TechScan", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro na conexâo! "+ ex, "TechScan", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(null,resultado);
        }
                                 DocumentosTextosDAO doctdao = new DocumentosTextosDAO();
                                 CalendarioAtual data = new CalendarioAtual();
                                 ArquivoDocumentos aqdoc = new ArquivoDocumentos();
                                 aqdoc.setNomeDocumento(aq.getNomeDocumento());//frmscanner.txtNomedoarquivo.getText());
                                 aqdoc.setPdf(aq.getPdf());
                                 aqdoc.setArquivo(aq.getArquivo());
                                 aqdoc.setFiguraDocumento(aq.getFiguraDocumento());//bytesimag);
                                 aqdoc.setTextoDocumento(aq.getTextoDocumento());//frmscanner.txtDocumentoemtexto.getText());
        try {
            aqdoc.setData(data.CalendarioAtual());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null,"Erro de parse! "+ ex, "TechScan", JOptionPane.ERROR_MESSAGE);
        }
                                 doctdao.salvar_decretos(aqdoc);  
                                 //frmscanner.veiodopai();
         }
    
    public void salvaportaria(ArquivoDocumentos aq, String resultado){
    
        try {
            ConexaoFirebird confb = new ConexaoFirebird(resultado);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
                                 DocumentosTextosDAO doctdao = new DocumentosTextosDAO();
                                 CalendarioAtual data = new CalendarioAtual();
                                 ArquivoDocumentos aqdoc = new ArquivoDocumentos();
                                 aqdoc.setNomeDocumento(aq.getNomeDocumento());//frmscanner.txtNomedoarquivo.getText());
                                 aqdoc.setPdf(aq.getPdf());
                                 aqdoc.setArquivo(aq.getArquivo());
                                 aqdoc.setFiguraDocumento(aq.getFiguraDocumento());//bytesimag);
                                 aqdoc.setTextoDocumento(aq.getTextoDocumento());//frmscanner.txtDocumentoemtexto.getText());
        try {
            aqdoc.setData(data.CalendarioAtual());
        } catch (ParseException ex) {
            Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
                                 doctdao.salvar_portarias(aqdoc);   
       
    }
    
}
