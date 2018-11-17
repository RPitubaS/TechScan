/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package documentosview;
  
    

import static documentosview.frmPai.btnAbrirarquivos;
import static documentosview.frmPai.btnNovodocumento;
import static documentosview.frmPai.dtpPai;
import java.awt.Color;
import java.awt.Desktop;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import modelo.bean.ArquivoDocumentos;
import modelo.bean.IdArquivoDocumento;
import modelo.dao.DocumentosTextosDAO;
import modelo.dao.UrlDao;
import produzirconeccao.ConexaoFirebird;
import produzirconeccao.RefazerConexao;
import util.AtualizarCTL;
import util.AtualizarDocumentos;
import util.GerenteDeArquivos;
import util.GuardarUrl;
import util.ImprimirNasImpressoras;
import util.SalvarDocumentos;

public class frmArquivo extends javax.swing.JInternalFrame {
    byte[] bytesimag;
    List<ArquivoDocumentos> selecionararquivos = new ArrayList<>();
    List<IdArquivoDocumento> selecionatextofigura = new ArrayList<>();
    GerenteDeArquivos gerentedearquivos = new GerenteDeArquivos();
    frmCarregando frmcarregando;
    String iniciostring, tipododocumento = "", pdf;
    boolean tamanho = true;
    private static frmArquivo frmarquivo;
    FileInputStream input;
    File arquivo;

    public static frmArquivo getInstancia(){
          if(frmarquivo == null){
             frmarquivo = new frmArquivo();
          }
        return frmarquivo;
    }
    
    public frmArquivo() {
                      
       // try {
        //    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
       // } catch (ClassNotFoundException ex) {
       //     Logger.getLogger(frmArquivo.class.getName()).log(Level.SEVERE, null, ex);
       // } catch (InstantiationException ex) {
       //     Logger.getLogger(frmArquivo.class.getName()).log(Level.SEVERE, null, ex);
       // } catch (IllegalAccessException ex) {
       //     Logger.getLogger(frmArquivo.class.getName()).log(Level.SEVERE, null, ex);
       // } catch (UnsupportedLookAndFeelException ex) {
       //     Logger.getLogger(frmArquivo.class.getName()).log(Level.SEVERE, null, ex);
       // }
        
        initComponents();
        rdbDocumentos1.setSelected(true);
        txtQuantidadedocumentos.setText("0 Documentos");
        txtTotalpalavras.setText("0 Palavras");
        jProgressBar2.setVisible(false);
    }

    class PintorDeTexto extends DefaultHighlighter.DefaultHighlightPainter{
          
         public PintorDeTexto(Color color){
            
             super(color);
            
         }
     }
     
         Highlighter.HighlightPainter meuhighligtpainter = new PintorDeTexto(Color.GREEN);
         
         public void removerpintor(JTextComponent textcomponent){
           
               Highlighter hilite = textcomponent.getHighlighter();
               Highlighter.Highlight[] hilites = hilite.getHighlights();
               for(int i = 0; i < hilites.length; i++){
                  if(hilites[i].getPainter() instanceof PintorDeTexto){
                                  
                      hilite.removeHighlight(hilites[i]);
                      
                  }
             }
         }
         
         public void pintor(JTextComponent textcomponent, String padronizar){
             int contador = 0;
             removerpintor(textcomponent);
             try{
                 Highlighter hilite = textcomponent.getHighlighter();
                 Document doc = textcomponent.getDocument();
                 String text = doc.getText(0, doc.getLength());
                 int pos = 0;
                 while((pos = text.toUpperCase().indexOf(padronizar.toUpperCase(), pos)) > 0){
                    hilite.addHighlight(pos, pos + padronizar.length(), meuhighligtpainter);
                    contador += 1;
                    pos += padronizar.length();
                 }
                 txtTotalpalavras.setText(contador + " Palavras");
             }catch(Exception e){
             
             }
         }     
         
          public void salvar() throws ParseException{
              

                  GuardarUrl guardarurl = new GuardarUrl();
                        if(!txtBusca.getText().equals("") && !txtTexto.getText().equals("")){
                           String resultado = guardarurl.GetProp("conectar");
                           gerentedeconexao(resultado);
                           DocumentosTextosDAO dtdao = new DocumentosTextosDAO();
                            switch (tipododocumento){
        
                                    case "Portarias":           
                        if(dtdao.selecionardecreto(txtBusca.getText()).equals("")){
                           int resultconfirm = JOptionPane.showConfirmDialog(null, "Salvar " + txtBusca.getText().trim() + " no arquivo de decretos?",
                           "TechScan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                             if(resultconfirm == 0){
                               //if(frmcarregando == null){
                                 new Thread(){
                                     @Override
                                     public void run(){
                                  frmcarregando = new frmCarregando();
                                  dtpPai.add(frmcarregando);
                                  frmcarregando.setVisible(true);
                                  frmcarregando.setPosicao();  
                                  frmarquivo.hide();
                                 ArquivoDocumentos aqdc = new ArquivoDocumentos();
                                 aqdc.setNomeDocumento((txtBusca.getText()).trim());
                                 if("Sim".equals(pdf)){
                                    aqdc.setPdf("Sim");
                                      try {
                                          arquivo = new File("C:\\Myprogrm\\tessdatadb\\arq_banco.pdf");
                                          input = new FileInputStream(arquivo.getAbsoluteFile());
                                          aqdc.setArquivo(input);
                                      } catch (FileNotFoundException ex) {
                                          JOptionPane.showMessageDialog(null, "Não foi possível ler o arquivo PDF! " + ex);
                                      }
                                    bytesimag = null;
                                 }else{
                                    aqdc.setFiguraDocumento(bytesimag);
                                    input = null;
                                 }
                                 aqdc.setTextoDocumento(" " + (txtTexto.getText()).trim());
                                 SalvarDocumentos salvdoc = new SalvarDocumentos();
                                 salvdoc.salvadecreto(aqdc, resultado);
                                 frmcarregando.dispose();
                                 frmarquivo.show();
                                 excluirmudar();
                                   try {
                                       frmcarregando.setClosed(true);
                                   } catch (PropertyVetoException ex) {
                                       Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                  dtpPai.remove(frmcarregando);  
                                   JOptionPane.showMessageDialog(null, "O documento foi incluído no arquivo de decretos!","TechScan",JOptionPane.INFORMATION_MESSAGE);
                                     }
                                  }.start();
                                //}
                              }
                        }else{
                              JOptionPane.showMessageDialog(null,"Este nome de arquivo já esta sendo utilizado!","TechScan",JOptionPane.INFORMATION_MESSAGE);
                             }
                        break;
            case "Decretos":
                        if(dtdao.selecionarportaria(txtBusca.getText()).equals("")){
                           int resultconfirm = JOptionPane.showConfirmDialog(null, "Salvar " + txtBusca.getText().trim() + " no arquivo de portarias?",
                           "TechScan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                             if(resultconfirm == 0){
                                new Thread(){
                                     @Override
                                     public void run(){
                                  frmcarregando = new frmCarregando();
                                  dtpPai.add(frmcarregando);
                                  frmcarregando.setVisible(true);
                                  frmcarregando.setPosicao();  
                                  frmarquivo.hide();
                                 ArquivoDocumentos aqdc = new ArquivoDocumentos();
                                 aqdc.setNomeDocumento((txtBusca.getText()).trim());
                                 if("Sim".equals(pdf)){
                                    aqdc.setPdf("Sim");
                                      try {
                                          arquivo = new File("C:\\Myprogrm\\tessdatadb\\arq_banco.pdf");
                                          input = new FileInputStream(arquivo.getAbsoluteFile());
                                          aqdc.setArquivo(input);
                                      } catch (FileNotFoundException ex) {
                                          JOptionPane.showMessageDialog(null, "Não foi possível ler o arquivo PDF! " + ex);
                                      }
                                    bytesimag = null;
                                 }else{
                                    aqdc.setFiguraDocumento(bytesimag);
                                    input = null;
                                 }
                                 aqdc.setTextoDocumento(" " + (txtTexto.getText()).trim());
                                 SalvarDocumentos salvdoc = new SalvarDocumentos();
                                 salvdoc.salvaportaria(aqdc, resultado);
                                 frmcarregando.dispose();
                                 frmarquivo.show();
                                 excluirmudar();
                                   try {
                                       frmcarregando.setClosed(true);
                                   } catch (PropertyVetoException ex) {
                                       Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                  dtpPai.remove(frmcarregando);  
                                  JOptionPane.showMessageDialog(null, "O documento foi incluído no arquivo de portarias!","TechScan",JOptionPane.INFORMATION_MESSAGE);
                                     }
                                  }.start();
                              }
                        }else{
                              JOptionPane.showMessageDialog(null,"Este nome de arquivo já esta sendo utilizado!","TechScan",JOptionPane.INFORMATION_MESSAGE);
                             }
                break;    
               
        }        
                   }else{
                         JOptionPane.showMessageDialog(null, "É preciso um nome e um arquivo para ser arquivado!","TechScan",JOptionPane.INFORMATION_MESSAGE);
                        }
         
         }
    //}
             public void gerentedeconexao(String resultado){
             
               try {
             
                    if(resultado != null){
                       ConexaoFirebird conect = new ConexaoFirebird(resultado);
                    }else{
                       UrlDao url = new UrlDao();
                       url.pegaurl();
                    }
               } catch (ClassNotFoundException ex) {
                        UrlDao url = new UrlDao();
                        url.pegaurl();
             
               } catch (SQLException ex) {

               }   
             }
             
             public void refazerconexao(){
             
                 Connection conectado = ConexaoFirebird.getConnection();
       
        try {
            if(conectado.isClosed()){
                GuardarUrl guardarurl = new GuardarUrl();
                String resultado = guardarurl.GetProp("conectar");
                if(resultado != null){
                    ConexaoFirebird conect = new ConexaoFirebird(resultado);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmArquivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
             
             }
        public void limparjanela(){
        
            txtBusca.setText("");
            txtTexto.setText("");
            txtQuantidadedocumentos.setText("0 Documentos");
            txtTotalpalavras.setText("0 Palavras");
            lblFigura.setIcon(null);
            DefaultTableModel modelo = (DefaultTableModel) tblModelos.getModel();
            modelo.setNumRows(0);    
            txtBusca.requestFocus();
        }
             
        public void atualizar(){
        //if(!txtBusca.getText().equals("")){
            if(rdbDocumentos1.isSelected()){
                  tipododocumento = "Decretos";
               }else{
                     if(rdbDocumentos2.isSelected()){
                        tipododocumento = "Portarias";
                     }     
               }
                  GuardarUrl guardarurl = new GuardarUrl();
                        if(!txtBusca.getText().equals("") && !txtTexto.getText().equals("")){
                           String resultado = guardarurl.GetProp("conectar");
                           gerentedeconexao(resultado);
                           DocumentosTextosDAO dtdao = new DocumentosTextosDAO();
                            switch (tipododocumento){
        
                                    case "Decretos":           
                        
                           int resultconfirm = JOptionPane.showConfirmDialog(null, "Atualizar " + txtBusca.getText().trim() + " no arquivo de decretos?",
                           "TechScan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                             if(resultconfirm == 0){
                               //if(frmcarregando == null){
                                 new Thread(){
                                     @Override
                                     public void run(){
                                  frmcarregando = new frmCarregando();
                                  dtpPai.add(frmcarregando);
                                  frmcarregando.setVisible(true);
                                  frmcarregando.setPosicao();  
                                  frmarquivo.hide();
                                 ArquivoDocumentos aqdc = new ArquivoDocumentos();
                                 aqdc.setId(selecionararquivos.get(tblModelos.getSelectedRow()).getId());
                                 aqdc.setNomeDocumento((txtBusca.getText()).trim());
                                 aqdc.setTextoDocumento(" " + (txtTexto.getText()).trim());
                                 AtualizarDocumentos atualdoc = new AtualizarDocumentos();
                                 atualdoc.atualizadecreto(aqdc, resultado);
                                 frmcarregando.dispose();
                                 frmarquivo.show();
                                 limparjanela();
                                   try {
                                       frmcarregando.setClosed(true);
                                   } catch (PropertyVetoException ex) {
                                       Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                  dtpPai.remove(frmcarregando);  
                                   JOptionPane.showMessageDialog(null, "O documento foi atualizado no arquivo de decretos!","TechScan",JOptionPane.INFORMATION_MESSAGE);
                                     }    
                                  }.start();
                                //}                              
                              }
                        break;
            case "Portarias":

                           int resultconfirm1 = JOptionPane.showConfirmDialog(null, "Atualizar " + txtBusca.getText().trim() + " no arquivo de portarias?",
                           "TechScan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                             if(resultconfirm1 == 0){
                                new Thread(){
                                     @Override
                                     public void run(){
                                  frmcarregando = new frmCarregando();
                                  dtpPai.add(frmcarregando);
                                  frmcarregando.setVisible(true);
                                  frmcarregando.setPosicao();  
                                  frmarquivo.hide();
                                 ArquivoDocumentos aqdc = new ArquivoDocumentos();
                                 aqdc.setId(selecionararquivos.get(tblModelos.getSelectedRow()).getId());
                                 aqdc.setNomeDocumento((txtBusca.getText()).trim());
                                 aqdc.setTextoDocumento(" " + (txtTexto.getText()).trim());
                                 AtualizarDocumentos atualdoc = new AtualizarDocumentos();
                                 atualdoc.atualizaportaria(aqdc, resultado);
                                 frmcarregando.dispose();
                                 frmarquivo.show();
                                 limparjanela();
                                   try {
                                       frmcarregando.setClosed(true);
                                   } catch (PropertyVetoException ex) {
                                       Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                  dtpPai.remove(frmcarregando);  
                                  JOptionPane.showMessageDialog(null, "O documento foi incluído no arquivo de portarias!","TechScan",JOptionPane.INFORMATION_MESSAGE);
                                     }
                                  }.start();                                
                              }
                break;    
               
        }
                   }else{
                         JOptionPane.showMessageDialog(null, "É preciso um nome e um arquivo para ser arquivado!","TechScan",JOptionPane.INFORMATION_MESSAGE);
                        }
        //}else{
              //JOptionPane.showMessageDialog(null, "É necessário definir o nome do arquivo na caixa de texto!");
             //}
        
        }
             
         public void enchetabeladecreto(String iniciostring){
              DefaultTableModel modelo = (DefaultTableModel) tblModelos.getModel();
              modelo.setNumRows(0);
              DocumentosTextosDAO doctxt = new DocumentosTextosDAO();
              RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                selecionararquivos.clear();
                if(btnBuscardocumentos.hasFocus()){
                   selecionararquivos = doctxt.selecionadocumentodecreto(iniciostring);
                }else{
                   selecionararquivos = doctxt.selecionatextodocumentodecreto(iniciostring);
                }
                   for(ArquivoDocumentos arqdoc : selecionararquivos){
                       modelo.addRow(new Object[]{
                       arqdoc.getNomeDocumento(),
                       });
                   }
                   txtBusca.setText("");
                   txtBusca.requestFocus();
             if(selecionararquivos.isEmpty()){
                JOptionPane.showMessageDialog(null, "Nenhum Decreto encontrado!","TechScan",JOptionPane.INFORMATION_MESSAGE);
                txtBusca.setText("");
                txtTexto.setText("");
                lblFigura.setIcon(null);
                txtBusca.requestFocus();
             }
            
         }
         
         public void enchetabelaportaria(String iniciostring){
              DefaultTableModel modelo = (DefaultTableModel) tblModelos.getModel();
              modelo.setNumRows(0);
              DocumentosTextosDAO doctxt = new DocumentosTextosDAO();
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                   selecionararquivos.clear();
                    if(btnBuscardocumentos.hasFocus()){
                       selecionararquivos = doctxt.selecionadocumentoportaria(iniciostring);   
                    }else{
                       selecionararquivos = doctxt.selecionatextodocumentoportaria(iniciostring);
                    }
                   for(ArquivoDocumentos arqdoc : selecionararquivos){
                       modelo.addRow(new Object[]{
                       arqdoc.getNomeDocumento(),
                       });
                   }
                   txtBusca.setText("");
                   txtBusca.requestFocus();
             if(selecionararquivos.isEmpty()){
               JOptionPane.showMessageDialog(null, "Nenhuma Portaria encontrada!","TechScan",JOptionPane.INFORMATION_MESSAGE);
               txtBusca.setText("");
               txtTexto.setText("");
               lblFigura.setIcon(null);
               txtBusca.requestFocus();
             }
            
         }
         
         public void enchetextofigura(int id){
              DocumentosTextosDAO doctxt = new DocumentosTextosDAO();
                RefazerConexao rfc = new RefazerConexao();
                rfc.refazerconexao();
                   pdf = selecionararquivos.get(tblModelos.getSelectedRow()).getPdf();
                   selecionatextofigura.clear();
                   if(tipododocumento == "Decretos"){
                      selecionatextofigura = doctxt.selecionatextoimagedecreto(id);                      
                   }else{
                         if(tipododocumento == "Portarias"){
                            selecionatextofigura = doctxt.selecionatextoimageportaria(id);                            
                         }     
                   }
                     for(IdArquivoDocumento arqdoc : selecionatextofigura){
                         txtTexto.setText(arqdoc.getTextoDocumento());
                      if(arqdoc.getFiguraDocumento() != null){
                         bytesimag = arqdoc.getFiguraDocumento();
                         ImageIcon icon = new ImageIcon(bytesimag);
                         icon.setImage(icon.getImage().getScaledInstance(frmarquivo.lblFigura.getWidth(),frmarquivo.lblFigura.getHeight(), 1));
                         frmarquivo.lblFigura.setIcon(icon);
                      }else{
                         try {
                               Desktop.getDesktop().open(new File("C:\\Myprogrm\\tessdatadb\\arq_banco.pdf"));
                         } catch (IOException ex) {
                               JOptionPane.showMessageDialog(null, "Erro no tipo de arquivo" + ex);
                         }
                         
                    ImageIcon icon = new ImageIcon("C:\\Myprogrm\\tessdatadb\\PDF.png");
                    icon.setImage(icon.getImage().getScaledInstance(frmarquivo.lblFigura.getWidth(),frmarquivo.lblFigura.getHeight(), 1));
                    frmarquivo.lblFigura.setIcon(icon); 
                      }
                      }
             if(selecionatextofigura.isEmpty()){
               JOptionPane.showMessageDialog(null, "Nenhum documento encontrado!");
               txtBusca.setText("");
               txtTexto.setText("");
               lblFigura.setIcon(null);
               txtBusca.requestFocus();
             }
            
         }
         
        public void excluir(){
         
        RefazerConexao rfc = new RefazerConexao();
        rfc.refazerconexao();
        if(tblModelos.getSelectedRow()!= -1 && lblFigura.getIcon() != null){
            int resultconfirm = JOptionPane.showConfirmDialog(null, "Confirma a exclusão de "
                + (String) tblModelos.getValueAt(tblModelos.getSelectedRow(), 0) + " do seu arguivo de origem?",
                "TechScan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(resultconfirm == 0){              
                if(rdbDocumentos1.isSelected()){
                   AtualizarCTL excluirctl = new AtualizarCTL();
                   excluirctl.excluirdecreto(selecionararquivos.get(tblModelos.getSelectedRow()).getId());
                   selecionararquivos.remove(tblModelos.getSelectedRow());
                   DefaultTableModel modelo = (DefaultTableModel) tblModelos.getModel();
                   modelo.setNumRows(0);
                   for(ArquivoDocumentos arqdoc : selecionararquivos){
                       modelo.addRow(new Object[]{
                       arqdoc.getNomeDocumento(),
                       });
                   }
                   txtBusca.setText("");
                   txtTexto.setText("");
                   lblFigura.setIcon(null);
                   txtBusca.requestFocus();
                }else{
                    if(rdbDocumentos2.isSelected()){
                       AtualizarCTL excluirctl = new AtualizarCTL();
                       excluirctl.excluirportaria(selecionararquivos.get(tblModelos.getSelectedRow()).getId());
                       selecionararquivos.remove(tblModelos.getSelectedRow());
                   DefaultTableModel modelo = (DefaultTableModel) tblModelos.getModel();
                   modelo.setNumRows(0);
                   for(ArquivoDocumentos arqdoc : selecionararquivos){
                       modelo.addRow(new Object[]{
                       arqdoc.getNomeDocumento(),
                       });
                   }
                       txtBusca.setText("");
                       txtTexto.setText("");
                       lblFigura.setIcon(null);
                       txtBusca.requestFocus();        
                    }
                } 
            } else {
            JOptionPane.showMessageDialog(null, "Nenhum documento foi selecionado para exclusão!",
                    "TechScan",JOptionPane.INFORMATION_MESSAGE);
            }
        } 
             
         }
        
        public void excluirmudar(){
         
        RefazerConexao rfc = new RefazerConexao();
        rfc.refazerconexao();
        if(tblModelos.getSelectedRow()!= -1 && lblFigura.getIcon() != null){
            //int resultconfirm = JOptionPane.showConfirmDialog(null, "Confirma a exclusão de "
                //+ (String) tblModelos.getValueAt(tblModelos.getSelectedRow(), 0) + " ?",
                //"TechScan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            //if(resultconfirm == 0){              
                if(rdbDocumentos2.isSelected()){
                   AtualizarCTL excluirctl = new AtualizarCTL();
                   excluirctl.excluirdecreto(selecionararquivos.get(tblModelos.getSelectedRow()).getId());
                   selecionararquivos.remove(tblModelos.getSelectedRow());
                   DefaultTableModel modelo = (DefaultTableModel) tblModelos.getModel();
                   modelo.setNumRows(0);
                   for(ArquivoDocumentos arqdoc : selecionararquivos){
                       modelo.addRow(new Object[]{
                       arqdoc.getNomeDocumento(),
                       });
                   }
                   txtBusca.setText("");
                   txtTexto.setText("");
                   lblFigura.setIcon(null);
                   txtBusca.requestFocus();
                }else{
                    if(rdbDocumentos1.isSelected()){
                       AtualizarCTL excluirctl = new AtualizarCTL();
                       excluirctl.excluirportaria(selecionararquivos.get(tblModelos.getSelectedRow()).getId());
                       selecionararquivos.remove(tblModelos.getSelectedRow());
                   DefaultTableModel modelo = (DefaultTableModel) tblModelos.getModel();
                   modelo.setNumRows(0);
                   for(ArquivoDocumentos arqdoc : selecionararquivos){
                       modelo.addRow(new Object[]{
                       arqdoc.getNomeDocumento(),
                       });
                   }
                       txtBusca.setText("");
                       txtTexto.setText("");
                       lblFigura.setIcon(null);
                       txtBusca.requestFocus();        
                    }
                } 
            //} else {
            //JOptionPane.showMessageDialog(null, "Nenhum documento foi selecionado para exclusão!",
                    //"TechScan",JOptionPane.INFORMATION_MESSAGE);
            //}
        } 
             
         }
            
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblModelos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtBusca = new javax.swing.JTextField();
        btnProcurarpalavra = new javax.swing.JButton();
        btnBuscardocumentos = new javax.swing.JButton();
        btnBuscartextodocumentos = new javax.swing.JButton();
        txtTotalpalavras = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        rdbDocumentos1 = new javax.swing.JRadioButton();
        rdbDocumentos2 = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        btnSalvardocumento = new javax.swing.JButton();
        btnExcluirdocumento = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnImprimirimagem = new javax.swing.JButton();
        btnImprimirtexto = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lblFigura = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar2 = new javax.swing.JProgressBar();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtTexto = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        txtQuantidadedocumentos = new javax.swing.JTextField();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(1272, 857));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tblModelos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Documentos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblModelos.setToolTipText("Lista de documentos");
        tblModelos.getTableHeader().setReorderingAllowed(false);
        tblModelos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblModelosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblModelos);
        if (tblModelos.getColumnModel().getColumnCount() > 0) {
            tblModelos.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        txtBusca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtBusca.setToolTipText("Nome do documento");
        txtBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscaKeyPressed(evt);
            }
        });

        btnProcurarpalavra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/filefind2-32x32x24.png"))); // NOI18N
        btnProcurarpalavra.setToolTipText("Procurar palavra dentro do documento");
        btnProcurarpalavra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcurarpalavraActionPerformed(evt);
            }
        });

        btnBuscardocumentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/3D-Documents-iconpqn.png"))); // NOI18N
        btnBuscardocumentos.setText("Buscar Documento");
        btnBuscardocumentos.setToolTipText("Procurar o documento");
        btnBuscardocumentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscardocumentosActionPerformed(evt);
            }
        });

        btnBuscartextodocumentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/3D-Docments-Alt-iconpqn.png"))); // NOI18N
        btnBuscartextodocumentos.setText("Procurar palavra no Arquivo");
        btnBuscartextodocumentos.setToolTipText("Procurar documentos contendo nome acima");
        btnBuscartextodocumentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscartextodocumentosActionPerformed(evt);
            }
        });

        txtTotalpalavras.setEditable(false);
        txtTotalpalavras.setBackground(new java.awt.Color(255, 153, 153));
        txtTotalpalavras.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtTotalpalavras.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalpalavras.setToolTipText("Quantidade de palavras dentro do documento");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnBuscartextodocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscardocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProcurarpalavra, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(txtTotalpalavras, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtBusca)
                    .addComponent(btnProcurarpalavra, javax.swing.GroupLayout.PREFERRED_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(txtTotalpalavras))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscartextodocumentos)
                    .addComponent(btnBuscardocumentos))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel4.setBackground(new java.awt.Color(255, 255, 153));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        rdbDocumentos1.setBackground(new java.awt.Color(255, 255, 153));
        buttonGroup1.add(rdbDocumentos1);
        rdbDocumentos1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        rdbDocumentos1.setText("Decreto");

        rdbDocumentos2.setBackground(new java.awt.Color(255, 255, 153));
        buttonGroup1.add(rdbDocumentos2);
        rdbDocumentos2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        rdbDocumentos2.setText("Portaria");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rdbDocumentos2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbDocumentos1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(101, 101, 101))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(rdbDocumentos1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbDocumentos2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(26, 26, 26))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSalvardocumento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/16_321.png"))); // NOI18N
        btnSalvardocumento.setText("Salvar");
        btnSalvardocumento.setToolTipText("Atualizar ou trocar o arquivo de pasta");
        btnSalvardocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvardocumentoActionPerformed(evt);
            }
        });

        btnExcluirdocumento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/delete.png"))); // NOI18N
        btnExcluirdocumento.setText("Excluir");
        btnExcluirdocumento.setToolTipText("Excluir um arquivo");
        btnExcluirdocumento.setActionCommand("Excluir ");
        btnExcluirdocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirdocumentoActionPerformed(evt);
            }
        });

        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/16_66-16x16x24.png"))); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.setToolTipText("Deixar tudo em branco");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnImprimirimagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/printer.png"))); // NOI18N
        btnImprimirimagem.setText("Imprimir Figura");
        btnImprimirimagem.setToolTipText("Imprimir somente a imagem");
        btnImprimirimagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirimagemActionPerformed(evt);
            }
        });

        btnImprimirtexto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/printer.png"))); // NOI18N
        btnImprimirtexto.setText("Imprimir texto");
        btnImprimirtexto.setToolTipText("Imprimir somente o texto");
        btnImprimirtexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirtextoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalvardocumento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcluirdocumento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImprimirimagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImprimirtexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(btnSalvardocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluirdocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImprimirimagem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImprimirtexto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Imagem");

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setAutoscrolls(true);

        lblFigura.setBackground(new java.awt.Color(255, 255, 255));
        lblFigura.setToolTipText("Um click para ampliar/Um click para default (imagem)");
        lblFigura.setOpaque(true);
        lblFigura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFiguraMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(lblFigura);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Arquivo");

        jProgressBar2.setForeground(new java.awt.Color(102, 255, 0));
        jProgressBar2.setFocusable(false);
        jProgressBar2.setIndeterminate(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txtTexto.setColumns(20);
        txtTexto.setLineWrap(true);
        txtTexto.setRows(200);
        txtTexto.setTabSize(1);
        txtTexto.setToolTipText("Texto editavel");
        txtTexto.setWrapStyleWord(true);
        jScrollPane4.setViewportView(txtTexto);

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtQuantidadedocumentos.setEditable(false);
        txtQuantidadedocumentos.setBackground(new java.awt.Color(255, 255, 0));
        txtQuantidadedocumentos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtQuantidadedocumentos.setToolTipText("Quantidade de documentos encontrados");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(txtQuantidadedocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(txtQuantidadedocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscardocumentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscardocumentosActionPerformed
    if(!txtBusca.getText().equals("")){
      iniciostring = (txtBusca.getText().trim()).toUpperCase();
      if(rdbDocumentos1.isSelected()){
         enchetabeladecreto(iniciostring);
         tipododocumento = "Decretos";
      }else{
            if(rdbDocumentos2.isSelected()){
               enchetabelaportaria(iniciostring);
               tipododocumento = "Portarias";
            }
            
           }
      txtQuantidadedocumentos.setText(tblModelos.getRowCount() +" Documentos");
    }
    }//GEN-LAST:event_btnBuscardocumentosActionPerformed

    private void tblModelosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblModelosMouseClicked
        txtBusca.setText("");
        txtBusca.requestFocus();
        Thread thread = new Thread(){
               @Override
               public void run(){       
                   jProgressBar2.setVisible(true);
                      enchetextofigura(selecionararquivos.get(tblModelos.getSelectedRow()).getId());
                   jProgressBar2.setVisible(false);
                 }
             
               };
        
        thread.start();
    }//GEN-LAST:event_tblModelosMouseClicked

    private void btnProcurarpalavraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarpalavraActionPerformed
        pintor(txtTexto, txtBusca.getText());
    }//GEN-LAST:event_btnProcurarpalavraActionPerformed

    private void btnSalvardocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvardocumentoActionPerformed
       
        switch (tipododocumento){
        
               case "Decretos": 
                   if(rdbDocumentos2.isSelected()){
               try {  
                    salvar();
               } catch (ParseException ex) {
                    Logger.getLogger(frmArquivo.class.getName()).log(Level.SEVERE, null, ex);
               }
                   }else{
                     atualizar();
                   }                   
               break;
               case "Portarias":
                   if(rdbDocumentos1.isSelected()){
            try {  
                salvar();
            } catch (ParseException ex) {
                Logger.getLogger(frmArquivo.class.getName()).log(Level.SEVERE, null, ex);
            }
                   }else{
                     atualizar();
                   }
               break;
        }
    }//GEN-LAST:event_btnSalvardocumentoActionPerformed

    private void btnExcluirdocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirdocumentoActionPerformed
        excluir();
    }//GEN-LAST:event_btnExcluirdocumentoActionPerformed
 
    
    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparjanela();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnBuscartextodocumentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscartextodocumentosActionPerformed
    if(!txtBusca.getText().equals("")){
      iniciostring = (txtBusca.getText().trim()).toUpperCase();
      if(rdbDocumentos1.isSelected()){
         enchetabeladecreto(iniciostring);
         tipododocumento = "Decretos";
      }else{
            if(rdbDocumentos2.isSelected()){
               enchetabelaportaria(iniciostring);
               tipododocumento = "Portarias";
            }     
           }
      txtQuantidadedocumentos.setText(tblModelos.getRowCount() +" Documentos");
    }  
    }//GEN-LAST:event_btnBuscartextodocumentosActionPerformed

    private void lblFiguraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFiguraMouseClicked
        int largura = frmarquivo.jScrollPane2.getWidth();
        int altura = frmarquivo.jScrollPane2.getHeight();
        if(tamanho == true){
            ImageIcon icon = new ImageIcon(bytesimag);
            icon.setImage(icon.getImage().getScaledInstance(largura + 300, altura + 300, 1));
            frmarquivo.lblFigura.setIcon(icon);
            tamanho = false;
        }else{
            frmarquivo.lblFigura.setIcon(null);
            ImageIcon icon = new ImageIcon(bytesimag);
            icon.setImage(icon.getImage().getScaledInstance(largura - 15, altura - 15, 1));
            frmarquivo.lblFigura.setIcon(icon);
            tamanho = true;
        }
    }//GEN-LAST:event_lblFiguraMouseClicked

    private void btnImprimirtextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirtextoActionPerformed
        String contendootexto;
        File arquivo = new File("C:\\Myprogrm\\docs\\imprimir.doc");
        contendootexto = txtTexto.getText();
        gerentedearquivos.GuardarOTexto(arquivo, contendootexto);
        ImprimirNasImpressoras imprimirnasimpressoras = new ImprimirNasImpressoras();
        imprimirnasimpressoras.imprimirNasImpressoras((arquivo).toString());
        arquivo.delete();
    }//GEN-LAST:event_btnImprimirtextoActionPerformed

    private void btnImprimirimagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirimagemActionPerformed
        File arquivo = new File("C:\\Myprogrm\\docs\\imprimir.jpg");
        for(IdArquivoDocumento arqdoc : selecionatextofigura){
        bytesimag = arqdoc.getFiguraDocumento();
        ImageIcon icon = new ImageIcon(bytesimag);
        gerentedearquivos.GuardarAImagem(arquivo, bytesimag);
                     }
        ImprimirNasImpressoras imprimirnasimpressoras = new ImprimirNasImpressoras();
        imprimirnasimpressoras.imprimirNasImpressoras((arquivo).toString());
        arquivo.delete();
    }//GEN-LAST:event_btnImprimirimagemActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
               btnAbrirarquivos.setEnabled(true);
               btnNovodocumento.setEnabled(true);
               txtBusca.setText("");
               txtTexto.setText("");
               lblFigura.setIcon(null);
               tblModelos.removeAll();
               txtQuantidadedocumentos.setText("0 Documentos");
               txtTotalpalavras.setText("0 Palavras");
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtBuscaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscaKeyPressed
        if(evt.getKeyCode()== evt.VK_ENTER && !txtBusca.getText().isEmpty()){
                btnProcurarpalavra.doClick();
        }
    }//GEN-LAST:event_txtBuscaKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscardocumentos;
    private javax.swing.JButton btnBuscartextodocumentos;
    private javax.swing.JButton btnExcluirdocumento;
    private javax.swing.JButton btnImprimirimagem;
    private javax.swing.JButton btnImprimirtexto;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnProcurarpalavra;
    private javax.swing.JButton btnSalvardocumento;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblFigura;
    private javax.swing.JRadioButton rdbDocumentos1;
    private javax.swing.JRadioButton rdbDocumentos2;
    private javax.swing.JTable tblModelos;
    private javax.swing.JTextField txtBusca;
    private javax.swing.JTextField txtQuantidadedocumentos;
    private javax.swing.JTextArea txtTexto;
    private javax.swing.JTextField txtTotalpalavras;
    // End of variables declaration//GEN-END:variables
}
