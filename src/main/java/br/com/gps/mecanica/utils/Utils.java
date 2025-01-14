package br.com.gps.mecanica.utils;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static String formatar_string(String string) {
        Map<String, String> caracteresEspeciais = new HashMap<>() {
            {
                put("á", "a");
                put("à", "a");
                put("ã", "a");
                put("â", "a");
                put("é", "e");
                put("ê", "e");
                put("í", "i");
                put("ó", "o");
                put("ô", "o");
                put("õ", "o");
                put("ú", "u");
                put("ç", "c");
                put("Á", "a");
                put("À", "a");
                put("Ã", "a");
                put("Â", "a");
                put("É", "e");
                put("Ê", "e");
                put("Í", "i");
                put("Ó", "o");
                put("Ô", "o");
                put("Õ", "o");
                put("Ú", "u");
                put("Ç", "c");
            }
        };

        for (Map.Entry<String, String> entry : caracteresEspeciais.entrySet()) {
            string = string.replace(entry.getKey(), entry.getValue());
        }

        string = string.toUpperCase();

        return string;
    }

}
