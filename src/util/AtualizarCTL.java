package util;

import modelo.bean.IdArquivoDocumento;
import modelo.dao.DocumentosTextosDAO;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pituba
 */
public class AtualizarCTL {
    public void atualizardecreto(IdArquivoDocumento arqdoc){    
        DocumentosTextosDAO dao = new DocumentosTextosDAO();
                         dao.atualizar_decreto(arqdoc);  
    }       
    
    public void excluirdecreto(int exid){    
        DocumentosTextosDAO dao = new DocumentosTextosDAO();
                         dao.excluir_decreto(exid);  
    }
    
    public void atualizarportaria(IdArquivoDocumento arqdoc){    
        DocumentosTextosDAO dao = new DocumentosTextosDAO();
                         dao.atualizar_portaria(arqdoc);  
    }       
    
    public void excluirportaria(int exid){    
        DocumentosTextosDAO dao = new DocumentosTextosDAO();
                         dao.excluir_portaria(exid);  
    }
}
