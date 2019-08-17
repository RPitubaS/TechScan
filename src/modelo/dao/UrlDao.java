/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.io.File;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import util.GuardarUrl;
import produzirconeccao.ConexaoFirebird;

/**
 *
 * @author Pituba
 */
public class UrlDao {
    public static String enviandocaminho;
    GuardarUrl guardarurl = new GuardarUrl();
    String caminho = "src\\modelo\\bean\\Url";
    String caminhofinal = "";
    
    public void pegaurl(String ip){
         
        JFileChooser escolherarquivo = new JFileChooser();
        escolherarquivo.setDialogTitle("Setar URL do Banco de Dados!");
        escolherarquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter extensao = new FileNameExtensionFilter("*.fdb", "fdb");
        escolherarquivo.setFileFilter(extensao);
        int retorno = escolherarquivo.showOpenDialog(escolherarquivo);
        if(retorno == JFileChooser.APPROVE_OPTION){
           File arquivo = escolherarquivo.getSelectedFile();
           String camiarquivo = arquivo.getPath();
           
           String[] caminhodoarquivo = camiarquivo.split("\\\\");
            for(int i = 0; i < caminhodoarquivo.length; i++){
                if(i<(caminhodoarquivo.length - 1)){
                   caminhofinal += (caminhodoarquivo[i] + "/");
                }else{
                   caminhofinal += (caminhodoarquivo[i]);
                }
            }
        
                  if(caminhofinal != guardarurl.GetProp("conectar")){
                       guardarurl.SaveProp("conectar", caminhofinal + "?encoding=WIN1252");
                       guardarurl.SaveProp("IP", ip);
                       String arq = "";
                       enviandocaminho = caminhofinal + "?encoding=WIN1252";
                       //enviandocaminho = guardarurl.GetProp("conectar");
                       try {
                           ConexaoFirebird conecta = new ConexaoFirebird(enviandocaminho, ip);
                           JOptionPane.showMessageDialog(null,"URL gravada com sucesso!");
                       } catch (ClassNotFoundException ex) {
                           JOptionPane.showMessageDialog(null,"Conexão não encontrada, URL desviada. " + ex,"TechScan",JOptionPane.WARNING_MESSAGE);
                       } catch (SQLException ex) {
                           JOptionPane.showMessageDialog(null,"Erro: " +  ex,"TechScan",JOptionPane.WARNING_MESSAGE);
                       }

                   
                   }else{
                   JOptionPane.showMessageDialog(null,"Erro ao salvar arquivo!","TechScan",JOptionPane.ERROR_MESSAGE);
                   System.exit(0);
                   }
                }else{
                   System.exit(0);
                }
             }   
        }
    

