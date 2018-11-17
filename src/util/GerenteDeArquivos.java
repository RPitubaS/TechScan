/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author Pituba
 */
public class GerenteDeArquivos {
    
    FileInputStream entrada;
    FileOutputStream saida;
    File arquivo;
    
    public GerenteDeArquivos(){
    
    
    }
    public String AbrirOTexto(File arquivo){
        
        String contendotexto = "";
        try{
            entrada = new FileInputStream(arquivo);
            int ascci;
            while((ascci = entrada.read()) != - 1){
                char caracter = (char) ascci;
                contendotexto += caracter;
            }
    }catch(Exception e){
    
    }
        return contendotexto;
    }
    
    public String GuardarOTexto(File arquivo, String contendootexto){
          
        String resposta = null;
        
        try{
            saida = new FileOutputStream(arquivo);
            byte[] bytestext = contendootexto.getBytes();
            saida.write(bytestext);
            resposta = "Texto salvo com êxito!";
        }catch(Exception e){
            resposta = "Erro ao salvar o texto! " + e;
        }
        return resposta;   
    }
    
    public byte[] AbrirAImagem(File arquivo){
        
        byte[] bytesimag = new byte[1024*1024*100];
        
        try{
            entrada = new FileInputStream(arquivo);
            entrada.read(bytesimag);       
        }catch(Exception e){
        
        }
        return bytesimag;
    }
    
    public String GuardarAImagem(File arquivo, byte[] bytesimag){
    
      String resposta = null;
      
      try{
          saida = new FileOutputStream(arquivo);
          saida.write(bytesimag);
          resposta = "Imagem salva com êxito!";
      }catch(Exception e){
          resposta = "Erro ao salvar a imagem! " + e;
      }
        return resposta;
    }
}
