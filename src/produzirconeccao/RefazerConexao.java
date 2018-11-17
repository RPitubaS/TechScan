/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produzirconeccao;

import documentosview.frmArquivo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.GuardarUrl;

/**
 *
 * @author Pituba
 */
public class RefazerConexao {
    public void refazerconexao(){
             
                 Connection conectado = ConexaoFirebird.getConnection();
       
        try {
            if(conectado.isClosed()){
                GuardarUrl guardarurl = new GuardarUrl();
                String resultado = guardarurl.GetProp("conectar");
                if(resultado != null){
                    ConexaoFirebird conect = new ConexaoFirebird(resultado);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmArquivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
             
             }
}
