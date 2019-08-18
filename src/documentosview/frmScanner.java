package documentosview;

import static documentosview.frmPai.dtpPai;
import static documentosview.frmPai.btnSalvar;
import static documentosview.frmPai.btnAbrirarquivo;
import static documentosview.frmPai.btnEntrarlogin;
import static documentosview.frmPai.btnHabilitartexto;
import static documentosview.frmPai.btnLogin;
import java.awt.Color;
import java.awt.Desktop;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import util.GerenteDeArquivos;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import modelo.bean.ArquivoDocumentos;
import modelo.dao.DocumentosTextosDAO;
import modelo.dao.UrlDao;
import produzirconeccao.ConexaoFirebird;
import produzirconeccao.ConexaoFirebirdTexto;
import produzirconeccao.RefazerConexao;
import util.GerenteDeJanelas;
import util.GuardarUrl;
import util.SalvarDocumentos;
import util.SalvarTextoParaArquivo;


public class frmScanner extends javax.swing.JInternalFrame {
    
    List<ArquivoDocumentos> selecionararquivosum = new ArrayList<>();
    JFileChooser selecionado = new JFileChooser();
    File arquivo;
    byte[] bytesimag;
    GerenteDeArquivos gerentedearquivos = new GerenteDeArquivos();
    GerenteDeJanelas gerentedejanelas;
    frmCarregando frmcarregando;
    private static frmScanner frmscanner;
    FileInputStream input;

    public static frmScanner getInstancia(){
          if(frmscanner == null){
             frmscanner = new frmScanner();
          }
        return frmscanner;
    }
    
    public frmScanner() {
        initComponents();
      
    }

   
        
     class PintorDeTexto extends DefaultHighlighter.DefaultHighlightPainter{
          
         public PintorDeTexto(Color color){
            
             super(color);
            
         }
     }
     
         Highlighter.HighlightPainter meuhighligtpainter = new PintorDeTexto(Color.MAGENTA);
         
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
            
             removerpintor(textcomponent);
             try{
                 Highlighter hilite = textcomponent.getHighlighter();
                 Document doc = textcomponent.getDocument();
                 String text = doc.getText(0, doc.getLength());
                 int pos = 0;
                 while((pos = text.toUpperCase().indexOf(padronizar.toUpperCase(), pos)) > 0){
                    hilite.addHighlight(pos, pos + padronizar.length(), meuhighligtpainter);
                    pos += padronizar.length();
                 }
             }catch(Exception e){
             
             }
         }     
         
         public void salvartexto() throws ParseException{
  String umnome = "", umtexto = "";
  
  if(frmscanner.isSelected()){
    if(frmscanner != null){
         String tipododocumento = frmscanner.cmbTipodocumento.getSelectedItem().toString();
         //GuardarUrl guardarurl = new GuardarUrl();
         if(!frmscanner.txtNomedoarquivo.getText().trim().equals("") && !frmscanner.txtDocumentoemtexto.getText().trim().equals("")){
             try {
                 //String resultado = guardarurl.GetProp("conectar");
                 //String ip = guardarurl.GetProp("IP");
                 //RefazerConexao rfc = new RefazerConexao();
                 //rfc.refazerconexao();
                 ConexaoFirebirdTexto conect = new ConexaoFirebirdTexto();
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
             } catch (SQLException ex) {
                 Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
             }
             
                      DocumentosTextosDAO dtdao = new DocumentosTextosDAO();
                      SalvarTextoParaArquivo stpa = new SalvarTextoParaArquivo();
                      
       switch (tipododocumento){
        
            case "Decretos":           
                        selecionararquivosum = dtdao.selecionardecretotexto(frmscanner.txtNomedoarquivo.getText());
                        for(ArquivoDocumentos arqdocs : selecionararquivosum){
                            umnome = arqdocs.getNomeDocumento();
                            umtexto = arqdocs.getTextoDocumento();
                        }
                        if(umnome.equals("")){
                           int resultconfirm = JOptionPane.showConfirmDialog(null, "Salvar "+ frmscanner.txtNomedoarquivo.getText() + " no arquivo de decretos?",
                           "TechScan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                             if(resultconfirm == 0){
                                 new Thread(){
                                     @Override
                                     public void run(){
                                  frmcarregando = new frmCarregando();
                                  dtpPai.add(frmcarregando);
                                  frmcarregando.setVisible(true);
                                  frmcarregando.setPosicao();  
                                  frmscanner.hide();
                                 ArquivoDocumentos aqdc = new ArquivoDocumentos();
                                 aqdc.setNomeDocumento((frmscanner.txtNomedoarquivo.getText()).trim());
                                         //if(arquivo.getName().endsWith("pdf")){
                                         //aqdc.setPdf("Sim");
                                         //try {
                                         //input = new FileInputStream(arquivo);
                                         //aqdc.setArquivo(input);
                                         //} catch (FileNotFoundException ex) {
                                         //JOptionPane.showMessageDialog(null, "Não foi possível ler o arquivo PDF! " + ex);
                                         //}
                                         //bytesimag = null;
                                         //}else{
                                    String contendootexto = frmscanner.txtDocumentoemtexto.getText();
                                    byte[] textoparaarquivo = contendootexto.getBytes();
                                    aqdc.setFiguraDocumento(textoparaarquivo);
                                    //input = null;
                                 //}
                                 //aqdc.setTextoDocumento(" " + (frmscanner.txtDocumentoemtexto.getText()).trim());
                                 SalvarDocumentos salvdoc = new SalvarDocumentos();
                                 salvdoc.salvatextodecreto(aqdc);
                                 frmcarregando.dispose();
                                 btnSalvar.setEnabled(false);
                                 btnHabilitartexto.setEnabled(false);
                                 btnAbrirarquivo.setEnabled(false);
                                 frmscanner.show();
                                   try {
                                       frmcarregando.setClosed(true);
                                   } catch (PropertyVetoException ex) {
                                       Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                  dtpPai.remove(frmcarregando);  
                                  veiodopai();
                                     }
                                  }.start();
                              }
                        }else{
                              JOptionPane.showMessageDialog(null,"Este nome de arquivo já esta sendo utilizado!","TechScan",JOptionPane.INFORMATION_MESSAGE);
                             }
                        break;
            case "Portarias":
                        selecionararquivosum = dtdao.selecionarportariatexto(frmscanner.txtNomedoarquivo.getText());
                        for(ArquivoDocumentos arqdocs : selecionararquivosum){
                            umnome = arqdocs.getNomeDocumento();
                            umtexto = arqdocs.getTextoDocumento();
                        }
                        if(umnome.equals("")){
                           int resultconfirm = JOptionPane.showConfirmDialog(null, "Salvar "+ frmscanner.txtNomedoarquivo.getText() + " no arquivo de portarias?",
                           "TechScan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                             if(resultconfirm == 0){
                                new Thread(){
                                     @Override
                                     public void run(){
                                  frmcarregando = new frmCarregando();
                                  dtpPai.add(frmcarregando);
                                  frmcarregando.setVisible(true);
                                  frmcarregando.setPosicao();  
                                  frmscanner.hide();
                                 ArquivoDocumentos aqdc = new ArquivoDocumentos();
                                 aqdc.setNomeDocumento((frmscanner.txtNomedoarquivo.getText()).trim());
                                 //if(arquivo.getName().endsWith("pdf")){
                                    //aqdc.setPdf("Sim");
                                    //try {
                                          //input = new FileInputStream(arquivo);
                                         // aqdc.setArquivo(input);
                                      //} catch (FileNotFoundException ex) {
                                          //JOptionPane.showMessageDialog(null, "Não foi possível ler o arquivo PDF! " + ex);
                                     //}
                                    //bytesimag = null;
                                 //}else{
                                    String contendootexto = frmscanner.txtDocumentoemtexto.getText();
                                    byte[] textoparaarquivo = contendootexto.getBytes();
                                    aqdc.setFiguraDocumento(textoparaarquivo);
                                    //input = null;
                                 //}
                                 //aqdc.setTextoDocumento(" " + (frmscanner.txtDocumentoemtexto.getText()).trim());
                                 SalvarDocumentos salvdoc = new SalvarDocumentos();
                                 salvdoc.salvatextoportaria(aqdc);
                                 frmcarregando.dispose();
                                 btnSalvar.setEnabled(false);
                                 btnHabilitartexto.setEnabled(false);
                                 btnAbrirarquivo.setEnabled(false);
                                 frmscanner.show();
                                   try {
                                       frmcarregando.setClosed(true);
                                   } catch (PropertyVetoException ex) {
                                       Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                  dtpPai.remove(frmcarregando);  
                                  veiodopai();
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
    }
         }
         
         public void salvar() throws ParseException{
  String umnome = "", umtexto = "";
  if(frmscanner.isSelected()){
    if(frmscanner != null){
         String tipododocumento = frmscanner.cmbTipodocumento.getSelectedItem().toString();
         GuardarUrl guardarurl = new GuardarUrl();
         if(!frmscanner.txtNomedoarquivo.getText().trim().equals("") && !frmscanner.txtDocumentoemtexto.getText().trim().equals("")){
                      String resultado = guardarurl.GetProp("conectar");
                      String ip = guardarurl.GetProp("IP");
                      RefazerConexao rfc = new RefazerConexao();
                      rfc.refazerconexao();
                      DocumentosTextosDAO dtdao = new DocumentosTextosDAO();
        switch (tipododocumento){
        
            case "Decretos":           
                        selecionararquivosum = dtdao.selecionardecreto(frmscanner.txtNomedoarquivo.getText());
                        for(ArquivoDocumentos arqdocs : selecionararquivosum){
                            umnome = arqdocs.getNomeDocumento();
                            umtexto = arqdocs.getTextoDocumento();
                        }
                        if(umnome.equals("")){
                           int resultconfirm = JOptionPane.showConfirmDialog(null, "Salvar "+ frmscanner.txtNomedoarquivo.getText() + " no arquivo de decretos?",
                           "TechScan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                             if(resultconfirm == 0){
                                 new Thread(){
                                     @Override
                                     public void run(){
                                  frmcarregando = new frmCarregando();
                                  dtpPai.add(frmcarregando);
                                  frmcarregando.setVisible(true);
                                  frmcarregando.setPosicao();  
                                  frmscanner.hide();
                                 ArquivoDocumentos aqdc = new ArquivoDocumentos();
                                 aqdc.setNomeDocumento((frmscanner.txtNomedoarquivo.getText()).trim());
                                 if(arquivo.getName().endsWith("pdf")){
                                    aqdc.setPdf("Sim");
                                      try {
                                          input = new FileInputStream(arquivo);
                                          aqdc.setArquivo(input);
                                      } catch (FileNotFoundException ex) {
                                          JOptionPane.showMessageDialog(null, "Não foi possível ler o arquivo PDF! " + ex);
                                      }
                                    bytesimag = null;
                                 }else{
                                    aqdc.setFiguraDocumento(bytesimag);
                                    input = null;
                                 }
                                 aqdc.setTextoDocumento(" " + (frmscanner.txtDocumentoemtexto.getText()).trim());
                                 SalvarDocumentos salvdoc = new SalvarDocumentos();
                                 salvdoc.salvadecreto(aqdc, resultado, ip);
                                 frmcarregando.dispose();
                                 btnSalvar.setEnabled(false);
                                 btnHabilitartexto.setEnabled(false);
                                 btnAbrirarquivo.setEnabled(false);
                                 frmscanner.show();
                                   try {
                                       frmcarregando.setClosed(true);
                                   } catch (PropertyVetoException ex) {
                                       Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                  dtpPai.remove(frmcarregando);  
                                  veiodopai();
                                     }
                                  }.start();
                              }
                        }else{
                              JOptionPane.showMessageDialog(null,"Este nome de arquivo já esta sendo utilizado!","TechScan",JOptionPane.INFORMATION_MESSAGE);
                             }
                        break;
            case "Portarias":
                        selecionararquivosum = dtdao.selecionarportaria(frmscanner.txtNomedoarquivo.getText());
                        for(ArquivoDocumentos arqdocs : selecionararquivosum){
                            umnome = arqdocs.getNomeDocumento();
                            umtexto = arqdocs.getTextoDocumento();
                        }
                        if(umnome.equals("")){
                           int resultconfirm = JOptionPane.showConfirmDialog(null, "Salvar "+ frmscanner.txtNomedoarquivo.getText() + " no arquivo de portarias?",
                           "TechScan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                             if(resultconfirm == 0){
                                new Thread(){
                                     @Override
                                     public void run(){
                                  frmcarregando = new frmCarregando();
                                  dtpPai.add(frmcarregando);
                                  frmcarregando.setVisible(true);
                                  frmcarregando.setPosicao();  
                                  frmscanner.hide();
                                 ArquivoDocumentos aqdc = new ArquivoDocumentos();
                                 aqdc.setNomeDocumento((frmscanner.txtNomedoarquivo.getText()).trim());
                                 if(arquivo.getName().endsWith("pdf")){
                                    aqdc.setPdf("Sim");
                                    try {
                                          input = new FileInputStream(arquivo);
                                          aqdc.setArquivo(input);
                                      } catch (FileNotFoundException ex) {
                                          JOptionPane.showMessageDialog(null, "Não foi possível ler o arquivo PDF! " + ex);
                                     }
                                    bytesimag = null;
                                 }else{
                                    aqdc.setFiguraDocumento(bytesimag);
                                    input = null;
                                 }
                                 aqdc.setTextoDocumento(" " + (frmscanner.txtDocumentoemtexto.getText()).trim());
                                 SalvarDocumentos salvdoc = new SalvarDocumentos();
                                 salvdoc.salvaportaria(aqdc, resultado, ip);
                                 frmcarregando.dispose();
                                 btnSalvar.setEnabled(false);
                                 btnHabilitartexto.setEnabled(false);
                                 btnAbrirarquivo.setEnabled(false);
                                 frmscanner.show();
                                   try {
                                       frmcarregando.setClosed(true);
                                   } catch (PropertyVetoException ex) {
                                       Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                  dtpPai.remove(frmcarregando);  
                                  veiodopai();
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
    }
 }
         
         public void abrirarquivo(){    
        if(frmscanner.isSelected()){
        selecionado.setDialogTitle("Selecionar arquivo...");
        selecionado.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter extensao = new FileNameExtensionFilter("*.jpg, *.jpeg, *.pdf","pdf","jpg","jpeg");
        selecionado.setFileFilter(extensao);
        int retorno = selecionado.showOpenDialog(selecionado);
        if(retorno == JFileChooser.APPROVE_OPTION){
              arquivo = selecionado.getSelectedFile();
              if(arquivo.canRead()){
                 if(arquivo.getName().endsWith("pdf")){
                    
                    ImageIcon icon = new ImageIcon("C:\\Myprogrm\\tessdatadb\\pdf-icono-281x300.png");
                    icon.setImage(icon.getImage().getScaledInstance(frmscanner.lblDocumentoemimagem.getWidth(),frmscanner.lblDocumentoemimagem.getHeight(), 1));
                    frmscanner.lblDocumentoemimagem.setIcon(icon);   
                    try {
                  Desktop.getDesktop().open(arquivo);
                  btnAbrirarquivo.setEnabled(false);
                  btnHabilitartexto.setEnabled(true);
                  btnSalvar.setEnabled(false);
                  } catch (IOException ex) {
                      JOptionPane.showMessageDialog(null, "Erro no tipo de arquivo" + ex);
              }                    
                    //String contendotexto = gerentedearquivos.AbrirOTexto(arquivo);
                    //frmscanner.txtDocumentoemtexto.setText(contendotexto);
                 }else{
                       if(arquivo.getName().endsWith("jpg")|| arquivo.getName().endsWith("jpeg")){
                          bytesimag = gerentedearquivos.AbrirAImagem(arquivo);
                          ImageIcon icon = new ImageIcon(bytesimag);
                          icon.setImage(icon.getImage().getScaledInstance(frmscanner.lblDocumentoemimagem.getWidth(),frmscanner.lblDocumentoemimagem.getHeight(), 1));
                          frmscanner.lblDocumentoemimagem.setIcon(icon); 
                          btnAbrirarquivo.setEnabled(false);
                          btnHabilitartexto.setEnabled(true);
                          btnSalvar.setEnabled(false);
                       }else{
                             JOptionPane.showMessageDialog(null,"Por favor selecione um arquivo de texto ou imagem!","TechScan",JOptionPane.PLAIN_MESSAGE);
                       }
                  }
                }
              }
               selecionado.equals(JFileChooser.ABORT);
            }
        }
         
         public void habilitartexto(){

               if(frmscanner.isSelected()){            
                  if(frmscanner.lblDocumentoemimagem.getIcon() != null){
                      new Thread(){
                                     @Override
                                     public void run(){
                                  frmcarregando = new frmCarregando();
                                  dtpPai.add(frmcarregando);
                                  frmcarregando.setVisible(true);
                                  frmcarregando.setPosicao();  
                                  frmscanner.hide();
                      
                     File imagemarquivo = new File(arquivo.toString());
                     
                     Tesseract imagem = new Tesseract();
                     imagem.setDatapath("C:\\Myprogrm\\tessdata");
                     imagem.setLanguage("por");
                       try{
             
                           String result = imagem.doOCR(imagemarquivo);
                           frmscanner.txtDocumentoemtexto.setText(result);
                           btnAbrirarquivo.setEnabled(false);
                           btnHabilitartexto.setEnabled(false);
                           btnSalvar.setEnabled(true);
                       }catch(TesseractException e){ 
                         System.err.println(e.getMessage());
                       }
                       frmcarregando.dispose();
                                 frmscanner.show();
                                   try {
                                       frmcarregando.setClosed(true);
                                   } catch (PropertyVetoException ex) {
                                       Logger.getLogger(frmScanner.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                  dtpPai.remove(frmcarregando);  
                                     }
                                  }.start();
                  }
               }    

  }
         
         //public void salvar_textoparaarquivo(){
            
         //}
         
         public void veiodopai(){     
         frmscanner.lblDocumentoemimagem.setIcon(null);
         frmscanner.txtNomedoarquivo.setText("");
         frmscanner.txtDocumentoemtexto.setText("");        
         }
         
         public void gerentedeconexao(String resultado, String ip){
             
         //GuardarUrl guardarurl = new GuardarUrl();
             
         //String resultado = guardarurl.GetProp("conectar");
         try {
             
             if(resultado != null){
             ConexaoFirebird conect = new ConexaoFirebird(resultado, ip);
             }else{
                  UrlDao url = new UrlDao();
                  url.pegaurl(ip);
             }
         } catch (ClassNotFoundException ex) {
             UrlDao url = new UrlDao();
             url.pegaurl(ip);
             
         } catch (SQLException ex) {

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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDocumentoemtexto = new javax.swing.JTextArea();
        txtNomedoarquivo = new javax.swing.JTextField();
        cmbTipodocumento = new javax.swing.JComboBox<>();
        lblDocumentos = new javax.swing.JLabel();
        lblArquivo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblDocumentoemimagem = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDocumentoemtexto1 = new javax.swing.JTextArea();
        txtNomedoarquivo1 = new javax.swing.JTextField();
        cmbTipodocumento1 = new javax.swing.JComboBox<>();
        lblDocumentos1 = new javax.swing.JLabel();
        lblArquivo1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblDocumentoemimagem1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameDeactivated(evt);
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtDocumentoemtexto.setColumns(20);
        txtDocumentoemtexto.setLineWrap(true);
        txtDocumentoemtexto.setRows(200);
        txtDocumentoemtexto.setToolTipText("Texto editavel");
        txtDocumentoemtexto.setWrapStyleWord(true);
        txtDocumentoemtexto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDocumentoemtextoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(txtDocumentoemtexto);

        txtNomedoarquivo.setBackground(new java.awt.Color(255, 255, 0));
        txtNomedoarquivo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtNomedoarquivo.setToolTipText("Digite o nome do arquivo");
        txtNomedoarquivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNomedoarquivoMouseClicked(evt);
            }
        });

        cmbTipodocumento.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cmbTipodocumento.setForeground(new java.awt.Color(51, 51, 51));
        cmbTipodocumento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Decretos", "Portarias" }));
        cmbTipodocumento.setToolTipText("Selecionar Decreto ou Portaria");
        cmbTipodocumento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbTipodocumentoMouseClicked(evt);
            }
        });

        lblDocumentos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDocumentos.setText("Nome do Documento:");
        lblDocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDocumentosMouseClicked(evt);
            }
        });

        lblArquivo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblArquivo.setText("Arquivo:");
        lblArquivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblArquivoMouseClicked(evt);
            }
        });

        lblDocumentoemimagem.setToolTipText("Imagem do arquivo");
        lblDocumentoemimagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDocumentoemimagemMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDocumentoemimagem, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDocumentoemimagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblDocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomedoarquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipodocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(299, 299, 299))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblArquivo)
                            .addComponent(cmbTipodocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDocumentos)
                            .addComponent(txtNomedoarquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );

        jInternalFrame1.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrame1.setClosable(true);
        jInternalFrame1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jInternalFrame1formMouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        txtDocumentoemtexto1.setColumns(20);
        txtDocumentoemtexto1.setLineWrap(true);
        txtDocumentoemtexto1.setRows(200);
        txtDocumentoemtexto1.setToolTipText("Texto editavel");
        txtDocumentoemtexto1.setWrapStyleWord(true);
        txtDocumentoemtexto1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDocumentoemtexto1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(txtDocumentoemtexto1);

        txtNomedoarquivo1.setBackground(new java.awt.Color(255, 255, 0));
        txtNomedoarquivo1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtNomedoarquivo1.setToolTipText("Digite o nome do arquivo");
        txtNomedoarquivo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNomedoarquivo1MouseClicked(evt);
            }
        });

        cmbTipodocumento1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cmbTipodocumento1.setForeground(new java.awt.Color(51, 51, 51));
        cmbTipodocumento1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Decretos", "Portarias" }));
        cmbTipodocumento1.setToolTipText("Selecionar Decreto ou Portaria");
        cmbTipodocumento1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbTipodocumento1MouseClicked(evt);
            }
        });

        lblDocumentos1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDocumentos1.setText("Nome do Documento:");
        lblDocumentos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDocumentos1MouseClicked(evt);
            }
        });

        lblArquivo1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblArquivo1.setText("Arquivo:");
        lblArquivo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblArquivo1MouseClicked(evt);
            }
        });

        lblDocumentoemimagem1.setToolTipText("Imagem do arquivo");
        lblDocumentoemimagem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDocumentoemimagem1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDocumentoemimagem1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDocumentoemimagem1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblDocumentos1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomedoarquivo1, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblArquivo1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipodocumento1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblArquivo1)
                            .addComponent(cmbTipodocumento1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDocumentos1)
                            .addComponent(txtNomedoarquivo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setBounds(0, 0, 1272, 853);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    private void lblDocumentoemimagemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDocumentoemimagemMouseClicked

    }//GEN-LAST:event_lblDocumentoemimagemMouseClicked

    private void txtDocumentoemtextoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDocumentoemtextoMouseClicked

    }//GEN-LAST:event_txtDocumentoemtextoMouseClicked

    private void lblDocumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDocumentosMouseClicked

    }//GEN-LAST:event_lblDocumentosMouseClicked

    private void txtNomedoarquivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNomedoarquivoMouseClicked

    }//GEN-LAST:event_txtNomedoarquivoMouseClicked

    private void lblArquivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblArquivoMouseClicked

    }//GEN-LAST:event_lblArquivoMouseClicked

    private void cmbTipodocumentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbTipodocumentoMouseClicked

    }//GEN-LAST:event_cmbTipodocumentoMouseClicked

    private void txtDocumentoemtexto1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDocumentoemtexto1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocumentoemtexto1MouseClicked

    private void txtNomedoarquivo1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNomedoarquivo1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomedoarquivo1MouseClicked

    private void cmbTipodocumento1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbTipodocumento1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipodocumento1MouseClicked

    private void lblDocumentos1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDocumentos1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDocumentos1MouseClicked

    private void lblArquivo1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblArquivo1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblArquivo1MouseClicked

    private void lblDocumentoemimagem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDocumentoemimagem1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDocumentoemimagem1MouseClicked

    private void jInternalFrame1formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jInternalFrame1formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jInternalFrame1formMouseClicked

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        btnAbrirarquivo.setEnabled(false);
        
        if(lblDocumentoemimagem.getIcon() != null || !txtDocumentoemtexto.getText().isEmpty() || !txtNomedoarquivo.getText().isEmpty()){
           JOptionPane.showMessageDialog(null, "As alterações não foram salvas! ",
                    "TechScan", JOptionPane.INFORMATION_MESSAGE);
           btnHabilitartexto.setEnabled(false);
           btnSalvar.setEnabled(false);
           
        }else{
           btnHabilitartexto.setEnabled(false);
           btnSalvar.setEnabled(false);
        }
        btnLogin.setEnabled(true);
        btnEntrarlogin.setEnabled(true);
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameDeactivated


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbTipodocumento;
    private javax.swing.JComboBox<String> cmbTipodocumento1;
    private javax.swing.JButton jButton1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblArquivo;
    private javax.swing.JLabel lblArquivo1;
    private javax.swing.JLabel lblDocumentoemimagem;
    private javax.swing.JLabel lblDocumentoemimagem1;
    private javax.swing.JLabel lblDocumentos;
    private javax.swing.JLabel lblDocumentos1;
    public static javax.swing.JTextArea txtDocumentoemtexto;
    private javax.swing.JTextArea txtDocumentoemtexto1;
    private javax.swing.JTextField txtNomedoarquivo;
    private javax.swing.JTextField txtNomedoarquivo1;
    // End of variables declaration//GEN-END:variables
}
