/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.bean;

/**
 *
 * @author Pituba
 */
public class Usuario {
    private int id;
    private String usuario;
    private String admin;
    
    public int getId(){
      return id;
    }
    
    public void setId(int id){
       this.id = id;
    }
    
    public String getUsuario(){
       return usuario;
    }
    
    public void setUsuario(String usuario){
       this.usuario = usuario;
    }
    
    public String getAdmin(){
       return admin;
    }
    
    public void setAdmin(String admin){
       this.admin = admin;
    }
}
