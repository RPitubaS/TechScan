/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import documentosview.frmScanner;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author Pituba
 */
public class GerenteDeJanelas {
    
    private static JDesktopPane jdesktoppane;
    
    public GerenteDeJanelas(JDesktopPane jdesktoppane){
    
           GerenteDeJanelas.jdesktoppane = jdesktoppane;
    
    }
    
    
    public void abrirjanelas(JInternalFrame jinternalframe) throws PropertyVetoException{
        
        Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
        jinternalframe.setBounds((dimensao.width - dimensao.width),
                               1,
                               dimensao.width,
                               dimensao.height - 118);
        jinternalframe.setMaximizable(true);
    
         if(jinternalframe.isVisible()){
            jinternalframe.toFront();
            jinternalframe.requestFocus();
         }else{
            jdesktoppane.add(jinternalframe);
            jinternalframe.setVisible(true);
            jinternalframe.setMaximum(true);
         }    
    }
    
    public void abrirlogin(JInternalFrame jinternalframe) throws PropertyVetoException{
        
        Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
        jinternalframe.setBounds(((dimensao.width)-770)/2,
                               ((dimensao.height)-778)/2,
                               770,
                               468);
        jinternalframe.setMaximizable(false);
    
         if(jinternalframe.isVisible()){
            jinternalframe.toFront();
            jinternalframe.requestFocus();
         }else{
            jdesktoppane.add(jinternalframe);
            jinternalframe.setVisible(true);
            //jinternalframe.toFront();
            //jinternalframe.requestFocus();
            jinternalframe.setSelected(true);
         }  
    }
    
    public void abrirentrar(JInternalFrame jinternalframe) throws PropertyVetoException{
        
        Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
        jinternalframe.setBounds(((dimensao.width)-390)/2,
                               ((dimensao.height)-778)/2,
                               390,
                               468);
        jinternalframe.setMaximizable(false);
    
         if(jinternalframe.isVisible()){
            jinternalframe.toFront();
            jinternalframe.requestFocus();
         }else{
            jdesktoppane.add(jinternalframe);
            jinternalframe.setVisible(true);
            //jinternalframe.toFront();
            //jinternalframe.requestFocus();
            jinternalframe.setSelected(true);
         }  
    }
    
    public void fecharjanelas(JInternalFrame jinternalframe){
         if(jinternalframe.isVisible() && jinternalframe.isSelected()){
 
            jinternalframe.dispose();
            jdesktoppane.remove(jinternalframe);
         }
    }
}
