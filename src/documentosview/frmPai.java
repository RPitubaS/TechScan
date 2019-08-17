package documentosview;

import static documentosview.frmArquivo.btnExcluirdocumento;
import static documentosview.frmArquivo.btnRenomear;
import static documentosview.frmArquivo.btnSalvardocumento;
import static documentosview.frmArquivo.jProgressBar2;
import static documentosview.frmLogin.btnCadastro;
import static documentosview.frmLogin.btnEntrar;
import static documentosview.frmLogin.cbxAdministrador;
import static documentosview.frmLogin.txtConfsenha;
import static documentosview.frmLogin.txtLognick;
import static documentosview.frmLogin.txtLogsenha;
import static documentosview.frmLogin.txtNome;
import static documentosview.frmLogin.txtSenha;
import static documentosview.frmPai.btnAbrirarquivo;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import modelo.bean.Usuario;
import modelo.dao.DocumentosTextosDAO;
import util.GuardarUrl;
import modelo.dao.UrlDao;
import modelo.dao.UrlScannerDAO;
import produzirconeccao.ConexaoFirebird;
import produzirconeccao.RefazerConexao;
import util.GerenteDeJanelas;
import util.GuardarUrlScanner;
import static documentosview.frmEntrar.txtLognickentrar;
import java.awt.event.ComponentListener;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;


public class frmPai extends javax.swing.JFrame {

    GerenteDeJanelas gerentedejanelas;
    GuardarUrl guardarurl = new GuardarUrl();
    Usuario usuario;
    frmScanner frmscanner = new frmScanner();
    frmArquivo frmarquivo = new frmArquivo();
    frmLogin frmlogin = new frmLogin();
    frmEntrar frmentrar = new frmEntrar();
    private static JDesktopPane jdesktoppane;
    public frmPai() {
        initComponents();
        this.gerentedejanelas = new GerenteDeJanelas(dtpPai);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/3D-Documents-iconpqn.png")).getImage());
        frmscanner.setVisible(false);
        btnNovodocumento.setEnabled(false);
        btnAbrirarquivo.setEnabled(false);
        btnHabilitartexto.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnAbrirarquivos.setEnabled(false);
        btnLogin.setEnabled(false);
        botaoscanner();
        
        String resultado = guardarurl.GetProp("conectar");  
        String ip = guardarurl.GetProp("IP");
        try {
            if (!resultado.equals("")) {
                ConexaoFirebird conect = new ConexaoFirebird(resultado, ip);
            } else {
                String servidor = JOptionPane.showInputDialog(null,"Digite aqui o IP do servidor, caso exista um!");
                UrlDao url = new UrlDao();
                if(servidor != ""){
                url.pegaurl(servidor);
                }else{
                url.pegaurl("localhost");
                }
            }
        } catch (ClassNotFoundException ex) {
            String servidor = JOptionPane.showInputDialog(null,"Digite aqui o IP do servidor, caso exista um!");
                UrlDao url = new UrlDao();
                if(servidor != ""){
                url.pegaurl(servidor);
                }else{
                url.pegaurl("localhost");
                }

        } catch (SQLException ex) {
            System.exit(0);
        }  
        
        List<Usuario> selecionandousuario = new ArrayList<>();
        DocumentosTextosDAO dctdao = new DocumentosTextosDAO();
        selecionandousuario = dctdao.selecionaradmin();
        RefazerConexao rfc = new RefazerConexao();
        rfc.refazerconexao();
        if(!selecionandousuario.isEmpty()){
           abrirentrar();       
        }
        if(selecionandousuario.isEmpty()){
            abrirlogin();
           cbxAdministrador.setSelected(false);
           txtNome.setEnabled(true);
           txtSenha.setEnabled(true);
           txtConfsenha.setEnabled(true);
           cbxAdministrador.setEnabled(true);
           btnCadastro.setEnabled(true);
           txtLognickentrar.setText("");
           txtLogsenha.setText("");
           frmlogin.setClosable(true);
           btnNovodocumento.setEnabled(false);
           btnAbrirarquivos.setEnabled(false);
           btnHabilitartexto.setEnabled(false);
           btnSalvar.setEnabled(false);
           btnLogin.setEnabled(true);
           btnEntrar.setEnabled(false);
           btnEntrarlogin.setEnabled(false);
        }
        
                   
    }
    //public void brasao(){
          //ImageIcon icon = new ImageIcon("C:\\Myprogrm\\tessdatadb\\brasao sjn.png");
          //icon.setImage(icon.getImage().getScaledInstance(1300,915, 1));
          //lblBrasao.setIcon(icon); 
    //}
    public void variavelglobal(){
        SingletonModel.ABC obj = SingletonModel.ABC.INSTANCE;
        String constante = obj.i;
        if("nao".equals(constante)){
           btnSalvardocumento.setEnabled(false);
           btnExcluirdocumento.setEnabled(false);
           btnRenomear.setEnabled(false);
        }else{
           if("sim".equals(constante)){
              btnSalvardocumento.setEnabled(true);
              btnExcluirdocumento.setEnabled(true);
              btnRenomear.setEnabled(true);
           }
        }      
    }
    
    public void abrirlogin(){
        try {
             gerentedejanelas.abrirlogin(frmLogin.getInstancia());
        } catch (PropertyVetoException ex) {
             Logger.getLogger(frmPai.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void abrirentrar(){
        try {
             gerentedejanelas.abrirentrar(frmEntrar.getInstancia());
        } catch (PropertyVetoException ex) {
             Logger.getLogger(frmPai.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void botaoscanner() {
        String semicomando = "", resultado;
        GuardarUrlScanner guardarurlscanner = new GuardarUrlScanner();
        resultado = guardarurlscanner.GetProp("conectarscanner");

        if (resultado == null) {
            btnScanner.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnNovodocumento = new javax.swing.JButton();
        btnAbrirarquivo = new javax.swing.JButton();
        btnHabilitartexto = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnAbrirarquivos = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnEntrarlogin = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnScanner = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        dtpPai = new javax.swing.JDesktopPane();
        lblBrasao = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TechScan - Sistema de Conversão de Documentos. V1.0");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jSeparator2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar1.add(jSeparator2);

        btnNovodocumento.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnNovodocumento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/3D-Notes-icon.png"))); // NOI18N
        btnNovodocumento.setText("Novo");
        btnNovodocumento.setToolTipText("Novo Arquivo");
        btnNovodocumento.setFocusable(false);
        btnNovodocumento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNovodocumento.setMaximumSize(new java.awt.Dimension(45, 55));
        btnNovodocumento.setMinimumSize(new java.awt.Dimension(45, 55));
        btnNovodocumento.setPreferredSize(new java.awt.Dimension(45, 55));
        btnNovodocumento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNovodocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovodocumentoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNovodocumento);

        btnAbrirarquivo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnAbrirarquivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/3D-Photos-icon.png"))); // NOI18N
        btnAbrirarquivo.setText("Imagem");
        btnAbrirarquivo.setToolTipText("Nova Imagem");
        btnAbrirarquivo.setFocusable(false);
        btnAbrirarquivo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAbrirarquivo.setMaximumSize(new java.awt.Dimension(45, 55));
        btnAbrirarquivo.setMinimumSize(new java.awt.Dimension(45, 55));
        btnAbrirarquivo.setPreferredSize(new java.awt.Dimension(45, 55));
        btnAbrirarquivo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAbrirarquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirarquivoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAbrirarquivo);

        btnHabilitartexto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnHabilitartexto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/3D-Documents-iconpq.png"))); // NOI18N
        btnHabilitartexto.setText("Texto");
        btnHabilitartexto.setToolTipText("Transformar em texto");
        btnHabilitartexto.setFocusable(false);
        btnHabilitartexto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHabilitartexto.setMaximumSize(new java.awt.Dimension(45, 55));
        btnHabilitartexto.setMinimumSize(new java.awt.Dimension(45, 55));
        btnHabilitartexto.setPreferredSize(new java.awt.Dimension(45, 55));
        btnHabilitartexto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHabilitartexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHabilitartextoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnHabilitartexto);

        btnSalvar.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/save_21411.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setToolTipText("Salvar no Arquivo");
        btnSalvar.setFocusable(false);
        btnSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvar.setMaximumSize(new java.awt.Dimension(45, 55));
        btnSalvar.setMinimumSize(new java.awt.Dimension(45, 55));
        btnSalvar.setPreferredSize(new java.awt.Dimension(45, 55));
        btnSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSalvar);
        jToolBar1.add(jSeparator1);

        btnAbrirarquivos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnAbrirarquivos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/cabinet_21466.png"))); // NOI18N
        btnAbrirarquivos.setText("Arquivo");
        btnAbrirarquivos.setToolTipText("Abrir Arquivos");
        btnAbrirarquivos.setFocusable(false);
        btnAbrirarquivos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAbrirarquivos.setMaximumSize(new java.awt.Dimension(50, 55));
        btnAbrirarquivos.setMinimumSize(new java.awt.Dimension(50, 55));
        btnAbrirarquivos.setPreferredSize(new java.awt.Dimension(50, 55));
        btnAbrirarquivos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAbrirarquivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirarquivosActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAbrirarquivos);
        jToolBar1.add(jLabel2);

        btnLogin.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/App-login-manager1-icon.png"))); // NOI18N
        btnLogin.setText("Cadastro");
        btnLogin.setFocusable(false);
        btnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin.setMaximumSize(new java.awt.Dimension(50, 55));
        btnLogin.setMinimumSize(new java.awt.Dimension(50, 55));
        btnLogin.setPreferredSize(new java.awt.Dimension(50, 55));
        btnLogin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jToolBar1.add(btnLogin);

        btnEntrarlogin.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnEntrarlogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/App-login-manager1-icon.png"))); // NOI18N
        btnEntrarlogin.setText("Login");
        btnEntrarlogin.setFocusable(false);
        btnEntrarlogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEntrarlogin.setMaximumSize(new java.awt.Dimension(50, 55));
        btnEntrarlogin.setMinimumSize(new java.awt.Dimension(50, 55));
        btnEntrarlogin.setPreferredSize(new java.awt.Dimension(50, 55));
        btnEntrarlogin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEntrarlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarloginActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEntrarlogin);
        jToolBar1.add(jSeparator3);

        btnScanner.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnScanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/if_scanner_18212.png"))); // NOI18N
        btnScanner.setText("Scanner");
        btnScanner.setToolTipText("Scanner");
        btnScanner.setFocusable(false);
        btnScanner.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnScanner.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnScanner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScannerActionPerformed(evt);
            }
        });
        jToolBar1.add(btnScanner);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/Settings_32x32.png"))); // NOI18N
        jButton2.setText("\n\n\nSet Scan"); // NOI18N
        jButton2.setToolTipText("Procurar Scanner");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setMaximumSize(new java.awt.Dimension(43, 55));
        jButton2.setMinimumSize(new java.awt.Dimension(43, 55));
        jButton2.setPreferredSize(new java.awt.Dimension(43, 55));
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/documentosicons/save_21411.png"))); // NOI18N
        jButton1.setText("S. Texto");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMaximumSize(new java.awt.Dimension(50, 55));
        jButton1.setMinimumSize(new java.awt.Dimension(50, 55));
        jButton1.setPreferredSize(new java.awt.Dimension(50, 55));
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(255, 0, 0));
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUsuario.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblUsuario.setMaximumSize(new java.awt.Dimension(820, 55));
        lblUsuario.setMinimumSize(new java.awt.Dimension(820, 55));
        lblUsuario.setPreferredSize(new java.awt.Dimension(820, 55));
        jToolBar1.add(lblUsuario);
        lblUsuario.getAccessibleContext().setAccessibleDescription("");

        lblBrasao.setIcon(new javax.swing.ImageIcon("C:\\Myprogrm\\tessdatadb\\brasao sjn.png")); // NOI18N
        lblBrasao.setMaximumSize(new java.awt.Dimension(1001, 836));
        lblBrasao.setMinimumSize(new java.awt.Dimension(1001, 836));
        lblBrasao.setPreferredSize(new java.awt.Dimension(1001, 836));

        dtpPai.setLayer(lblBrasao, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dtpPaiLayout = new javax.swing.GroupLayout(dtpPai);
        dtpPai.setLayout(dtpPaiLayout);
        dtpPaiLayout.setHorizontalGroup(
            dtpPaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dtpPaiLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblBrasao, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(260, 260, 260))
        );
        dtpPaiLayout.setVerticalGroup(
            dtpPaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dtpPaiLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblBrasao, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dtpPai)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1291, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(dtpPai))
        );

        setSize(new java.awt.Dimension(962, 628));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovodocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovodocumentoActionPerformed
        
        if(frmArquivo.getInstancia().isSelected()){
                int resultconfirm = JOptionPane.showConfirmDialog(null, "Fechar janela de manipulação de documentos? ",
                    "TechScan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(resultconfirm == 0){
                       gerentedejanelas.fecharjanelas(frmArquivo.getInstancia());
                        try {
                             gerentedejanelas.abrirjanelas(frmScanner.getInstancia());
                             frmscanner.veiodopai();
                             btnAbrirarquivo.setEnabled(true);
                             btnSalvar.setEnabled(false);
                             btnHabilitartexto.setEnabled(false);
                             btnLogin.setEnabled(false);
                             btnEntrarlogin.setEnabled(false);
                             
                             //btnAbrirarquivos.setEnabled(true);
                             //btnNovodocumento.setEnabled(true);
                             //txtBusca.setText("");
                             //txtTexto.setText("");
                             //lblFigura.setIcon(null);
                             //tblModelos.removeAll();
                             //txtQuantidadedocumentos.setText("0 Documentos");
                             //txtTotalpalavras.setText("0 Palavras");
               btnLogin.setEnabled(true);
               btnEntrarlogin.setEnabled(true);
               jProgressBar2.setVisible(false);
                        } catch (PropertyVetoException ex) {
                             Logger.getLogger(frmPai.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            }else{
                  if(frmLogin.getInstancia().isSelected() || frmEntrar.getInstancia().isSelected()){
                     gerentedejanelas.fecharjanelas(frmLogin.getInstancia());
                  }
                  try {
                       gerentedejanelas.abrirjanelas(frmScanner.getInstancia());
                       frmscanner.veiodopai();
                       btnAbrirarquivo.setEnabled(true);
                       btnSalvar.setEnabled(false);
                       btnHabilitartexto.setEnabled(false);
                       btnLogin.setEnabled(false);
                       btnEntrarlogin.setEnabled(false);
                  } catch (PropertyVetoException ex) {
                       Logger.getLogger(frmPai.class.getName()).log(Level.SEVERE, null, ex);
                  }
            }
        
        
            
        
      
    }//GEN-LAST:event_btnNovodocumentoActionPerformed

    private void btnAbrirarquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirarquivoActionPerformed
       
        if (frmscanner != null) {
            try {
                frmscanner.abrirarquivo();
                //btnAbrirarquivo.setEnabled(false);
                //btnHabilitartexto.setEnabled(true);
                //btnSalvar.setEnabled(false);
            } catch (Exception e) {

            }
        }

    }//GEN-LAST:event_btnAbrirarquivoActionPerformed

    private void btnHabilitartextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHabilitartextoActionPerformed

        if (frmscanner != null) {
            try {
                frmscanner.habilitartexto();
                //btnAbrirarquivo.setEnabled(false);
                //btnHabilitartexto.setEnabled(false);
                //btnSalvar.setEnabled(true);
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_btnHabilitartextoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (frmscanner != null) {
            try {
                frmscanner.salvar();
                //btnAbrirarquivo.setEnabled(false);
                //btnHabilitartexto.setEnabled(false);
                //btnSalvar.setEnabled(false);
                //JOptionPane.showMessageDialog(null, "Nenhum arquivo a ser armazenado!");
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnScannerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScannerActionPerformed

        String semicomando = "", resultado;
        GuardarUrlScanner guardarurlscanner = new GuardarUrlScanner();
        resultado = guardarurlscanner.GetProp("conectarscanner");

        if (resultado == null) {
            UrlScannerDAO urlscannerdao = new UrlScannerDAO();
            urlscannerdao.pegaurlscanner();
            resultado = guardarurlscanner.GetProp("conectarscanner");
            String[] caminhodoarquivo = resultado.split("\\\\");
            for (int i = 0; i < caminhodoarquivo.length; i++) {
                if (i == (caminhodoarquivo.length - 1)) {
                    semicomando = (caminhodoarquivo[i]);
                }
            }
            String comando = "cmd /c " + semicomando;
            try {
                Runtime.getRuntime().exec(comando);
            } catch (IOException ex) {
                Logger.getLogger(frmPai.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String[] caminhodoarquivo = resultado.split("\\\\");
            for (int i = 0; i < caminhodoarquivo.length; i++) {
                if (i == (caminhodoarquivo.length - 1)) {
                    semicomando = (caminhodoarquivo[i]);
                }
            }
            String comando = "cmd /c " + semicomando;
            try {
                Runtime.getRuntime().exec(comando);
            } catch (IOException ex) {
                Logger.getLogger(frmPai.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnScannerActionPerformed

    private void btnAbrirarquivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirarquivosActionPerformed
        
            if(frmScanner.getInstancia().isSelected()){
                int resultconfirm = JOptionPane.showConfirmDialog(null, "Fechar janela de salvamento de documentos? ",
                    "TechScan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(resultconfirm == 0){
                       gerentedejanelas.fecharjanelas(frmScanner.getInstancia());
                        try {
                             gerentedejanelas.abrirjanelas(frmArquivo.getInstancia());
                             btnAbrirarquivos.setEnabled(false);
                             btnNovodocumento.setEnabled(false);
                             btnAbrirarquivo.setEnabled(false);
                             btnHabilitartexto.setEnabled(false);
                             btnSalvar.setEnabled(false);
                             btnLogin.setEnabled(false);
                             btnEntrarlogin.setEnabled(false);
                             variavelglobal();
                        } catch (PropertyVetoException ex) {
                             Logger.getLogger(frmPai.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            }else{
                  if(frmLogin.getInstancia().isSelected()){
                     gerentedejanelas.fecharjanelas(frmLogin.getInstancia());
                  }
                  try {
                       gerentedejanelas.abrirjanelas(frmArquivo.getInstancia());
                       btnAbrirarquivos.setEnabled(false);
                       btnNovodocumento.setEnabled(false);
                       btnAbrirarquivo.setEnabled(false);
                       btnHabilitartexto.setEnabled(false);
                       btnSalvar.setEnabled(false);
                       btnLogin.setEnabled(false);
                       btnEntrarlogin.setEnabled(false);
                       variavelglobal();
                  } catch (PropertyVetoException ex) {
                       Logger.getLogger(frmPai.class.getName()).log(Level.SEVERE, null, ex);
                  }
            }         
    }//GEN-LAST:event_btnAbrirarquivosActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String semicomando = "", resultado;
        GuardarUrlScanner guardarurlscanner = new GuardarUrlScanner();
        UrlScannerDAO urlscannerdao = new UrlScannerDAO();
        urlscannerdao.pegaurlscanner();
        resultado = guardarurlscanner.GetProp("conectarscanner");
        String[] caminhodoarquivo = resultado.split("\\\\");
        for (int i = 0; i < caminhodoarquivo.length; i++) {
            if (i == (caminhodoarquivo.length - 1)) {
                semicomando = (caminhodoarquivo[i]);
            }
        }
        String comando = "cmd /c " + semicomando;
        try {
            Runtime.getRuntime().exec(comando);
            btnScanner.setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(frmPai.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        
        gerentedejanelas.fecharjanelas(frmArquivo.getInstancia());
        gerentedejanelas.fecharjanelas(frmScanner.getInstancia());
        gerentedejanelas.fecharjanelas(frmEntrar.getInstancia());
        frmLogin.getInstancia().toFront();
        gerentedejanelas.fecharjanelas(frmLogin.getInstancia());
        Usuario usuario = new Usuario();
        usuario.setId(0);
        usuario.setUsuario(null);
        usuario.setAdmin(null);
        btnNovodocumento.setEnabled(false);
        btnAbrirarquivo.setEnabled(false);
        btnHabilitartexto.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnAbrirarquivos.setEnabled(false);
        //btnLogin.setEnabled(false);
        txtLognickentrar.requestFocus(true);
        btnEntrar.setEnabled(true);
        txtNome.setEnabled(false);
        txtSenha.setEnabled(false);
        txtConfsenha.setEnabled(false);
        cbxAdministrador.setEnabled(false);
        btnCadastro.setEnabled(false);
        lblUsuario.setText("");
        
        if(dtpPai.getComponentCount() != 0){
           dtpPai.removeAll();
        }
           SingletonModel.ABC obj = SingletonModel.ABC.INSTANCE;
           obj.i = "";
           abrirlogin();
           
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnEntrarloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarloginActionPerformed
        gerentedejanelas.fecharjanelas(frmArquivo.getInstancia());
        gerentedejanelas.fecharjanelas(frmScanner.getInstancia());
        gerentedejanelas.fecharjanelas(frmLogin.getInstancia());
        frmEntrar.getInstancia().toFront();
        gerentedejanelas.fecharjanelas(frmEntrar.getInstancia());
        Usuario usuario = new Usuario();
        usuario.setId(0);
        usuario.setUsuario(null);
        usuario.setAdmin(null);
        btnNovodocumento.setEnabled(false);
        btnAbrirarquivo.setEnabled(false);
        btnHabilitartexto.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnAbrirarquivos.setEnabled(false);
        btnLogin.setEnabled(false);
           SingletonModel.ABC obj = SingletonModel.ABC.INSTANCE;
           obj.i = "";
           lblUsuario.setText("");
           abrirentrar();
           btnEntrar.setEnabled(true);
           lblUsuario.setText("");
           txtLognickentrar.requestFocus();
           
    }//GEN-LAST:event_btnEntrarloginActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       if (frmscanner != null) {
            try {
                frmscanner.salvartexto();
                //btnAbrirarquivo.setEnabled(false);
                //btnHabilitartexto.setEnabled(false);
                //btnSalvar.setEnabled(false);
                //JOptionPane.showMessageDialog(null, "Nenhum arquivo a ser armazenado!");
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmPai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
                
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPai().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAbrirarquivo;
    public static javax.swing.JButton btnAbrirarquivos;
    public static javax.swing.JButton btnEntrarlogin;
    public static javax.swing.JButton btnHabilitartexto;
    public static javax.swing.JButton btnLogin;
    public static javax.swing.JButton btnNovodocumento;
    public static javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnScanner;
    public static javax.swing.JDesktopPane dtpPai;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblBrasao;
    public static javax.swing.JLabel lblUsuario;
    // End of variables declaration//GEN-END:variables
}
