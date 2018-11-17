/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Pituba
 */
public class GuardarUrlScanner {
   public static Properties prop = new Properties();
    
    public void SaveProp(String nome, String arquivo){
         
        
        try {
            prop.setProperty(nome, arquivo);
            prop.store(new FileOutputStream("C:\\Myprogrm\\tessdatadb\\Url"), null);
        } catch (IOException e) {
            //Logger.getLogger(GuardarUrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String GetProp(String nome){
            String valor = "";
            try{
                prop.load(new FileInputStream("C:\\Myprogrm\\tessdatadb\\Url"));
                valor = prop.getProperty(nome);
            }catch(IOException e){
            
            }
                 return valor;
        }
    
    public void DropProp(String nome, String arquivo){
       try {
           prop.load(new FileInputStream("C:\\Myprogrm\\tessdatadb\\Url"));
           prop.remove(prop.getProperty(nome), prop.getProperty(nome));
       } catch (IOException e) {
           Logger.getLogger(GuardarUrlScanner.class.getName()).log(Level.SEVERE, null, e);
       }
    
       }
}
