/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import documentosview.frmScanner;
//import static documentosview.frmScanner.txtDocumentoemtexto;

public class SalvarTextoParaArquivo {
    //private static frmScanner frmscanner;
    private String contendootexto;
    
     public byte[] textoemarquivo(){             
                  contendootexto = frmScanner.txtDocumentoemtexto.getText();
                  byte[] textoparaarquivo = contendootexto.getBytes();
                  return textoparaarquivo;
             }
}
