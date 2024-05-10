package com.loja.loja.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilities {

    public static String DateFormater(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateFormated = dateFormat.format(date);
        return dateFormated;
    }
}
