/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.bean;

import java.io.File;
import java.io.InputStream;
import java.sql.Date;



/**
 *
 * @author Pituba
 */
public class ArquivoDocumentos {
    private int id;
    private String nomedocumento, textodocumento, pdf;
    private byte[] figuradocumento;
    private InputStream arquivo;
    private Date data;
    
    public int getId(){
      return id;
    }
    
    public void setId(int id){
       this.id = id;
    }
    
    public String getNomeDocumento(){
     
      return nomedocumento;
    }
    
    public void setNomeDocumento(String nomedocumento){
    
      this.nomedocumento = nomedocumento;
    }
    
    public String getPdf(){
       
        return pdf;
    }
    
    public void setPdf(String pdf){
    
        this.pdf = pdf;
    }
    
    public InputStream getArquivo(){
    
      return arquivo;
    }
    
    public void setArquivo(InputStream arquivo){
    
      this.arquivo = arquivo;
    }
    
    public byte[] getFiguraDocumento(){
    
      return figuradocumento;
    }
    
    public void setFiguraDocumento(byte[] figuradocumento){
    
      this.figuradocumento = figuradocumento;
    }
    
    public String getTextoDocumento(){
    
       return textodocumento;
    }
    
    public void setTextoDocumento(String textodocumento){
    
       this.textodocumento = textodocumento;
    }
    
    public Date getData(){
        
       return data;
    }
    
    public void setData(Date data){
        
      this.data = data;
    }
}
