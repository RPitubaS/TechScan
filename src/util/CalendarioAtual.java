/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Pituba
 */
public class CalendarioAtual {
    
    private String dataatual;
    
     public Date CalendarioAtual() throws ParseException {
                    SimpleDateFormat formatbr = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int month = (cal.get(Calendar.MONTH))+1;
                    int year = cal.get(Calendar.YEAR);
                    dataatual = String.format("%02d/%02d/%02d", day, month, year);
            //dataatual = String.format("%02d/%02d/%02d", day, month, year);
            java.sql.Date inicio = new java.sql.Date(formatbr.parse(dataatual).getTime());
        
 
                    
        return inicio;
     }
                   
}
