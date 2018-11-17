/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import static java.lang.System.gc;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author Pituba
 */
public class ImprimirNasImpressoras {
    
    public void imprimirNasImpressoras(String arquivo){
 PrintService[] printservice = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.AUTOSENSE,null);
 PrintService impressorapadrao = PrintServiceLookup.lookupDefaultPrintService();
 DocFlavor docflavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
 HashDocAttributeSet hashdocattributeset = new HashDocAttributeSet();

        try {
            FileInputStream fileinputstream = new FileInputStream(arquivo);
            Doc doc = new SimpleDoc(fileinputstream, docflavor, hashdocattributeset);
            PrintRequestAttributeSet printrequestattributeset = new HashPrintRequestAttributeSet();
            PrintService printservico = ServiceUI.printDialog(null,300, 200, printservice,
                    impressorapadrao, docflavor, printrequestattributeset);
            if(printservico != null){
                DocPrintJob docprintjob = printservico.createPrintJob();
                try {
                    docprintjob.print(doc, printrequestattributeset);
                } catch (PrintException ex) {
                    Logger.getLogger(ImprimirNasImpressoras.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImprimirNasImpressoras.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
