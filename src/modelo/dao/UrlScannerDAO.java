/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import util.GuardarUrlScanner;

/**
 *
 * @author Pituba
 */
public class UrlScannerDAO {
    public static String enviandocaminho;
    GuardarUrlScanner guardarurlscanner = new GuardarUrlScanner();
    String caminho = "C:\\Myprogrm\\tessdatadb\\Url";
    String caminhofinal = "";
    
    public void pegaurlscanner(){
         
        JFileChooser escolherarquivo = new JFileChooser();
        escolherarquivo.setDialogTitle("Setar URL do Scanner!");
        escolherarquivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter extensao = new FileNameExtensionFilter("*.exe", "exe");
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
           
        
                  if(caminhofinal != guardarurlscanner.GetProp("conectarscanner")){
                       guardarurlscanner.SaveProp("conectarscanner", caminhofinal);
                       String arq = "";
                       enviandocaminho = guardarurlscanner.GetProp("conectarscanner");
                       
                           JOptionPane.showMessageDialog(null,"URL do Scanner gravada com sucesso!","TechScan", JOptionPane.PLAIN_MESSAGE);
                   
                  }else{
                   JOptionPane.showMessageDialog(null,"Erro ao salvar arquivo!","TechScan",JOptionPane.ERROR_MESSAGE);
                   }
                }
             } 
    
}
