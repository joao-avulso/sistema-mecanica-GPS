package br.com.gps.mecanica.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import br.com.gps.mecanica.dto.EnderecoDto;

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

        string = string.strip();
        string = string.toUpperCase();

        return string;
    }

    public static String formatar_placa(String placa) {
        placa = placa.replace("-", "");
        placa = placa.replace(" ", "");
        placa = placa.strip();
        placa = placa.toUpperCase();
        return placa;
    }

    public static String formatar_telefone(String telefone) {
        telefone = telefone.replace("(", "");
        telefone = telefone.replace(")", "");
        telefone = telefone.replace("-", "");
        telefone = telefone.replace(" ", "");
        telefone = telefone.strip();
        return telefone;
    }

    public static String formatar_cpf(String cpf) {
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");
        cpf = cpf.strip();
        return cpf;
    }

    public static Boolean verificar_cpf(String cpf) {
        if (cpf.length() != 11) {
            return false;
        }

        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(cpf.substring(i, i + 1)) * i + 1;
        }

        int digito1 = soma % 11;

        if (digito1 == 10) {
            digito1 = 0;
        }

        soma = 0;

        for (int i = 0; i < 10; i++) {
            soma += Integer.parseInt(cpf.substring(i, i + 1)) * i;
        }

        int digito2 = soma % 11;

        if (digito2 == 10) {
            digito2 = 0;
        }

        if (Integer.parseInt(cpf.substring(9, 10)) == digito1 && Integer.parseInt(cpf.substring(10, 11)) == digito2) {
            return true;
        }

        return false;
    }

    public static String formatar_cnpj(String cnpj) {
        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace("/", "");
        cnpj = cnpj.replace("-", "");
        cnpj = cnpj.strip();
        return cnpj;
    }

    public static Boolean verificar_cnpj(String cnpj) {
        if (cnpj.length() != 14) {
            return false;
        }

        int soma = 0;

        int[] peso1 = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

        for (int i = 0; i < 12; i++) {
            soma += Integer.parseInt(cnpj.substring(i, i + 1)) * peso1[i];
        }

        int digito1 = soma % 11;

        if (digito1 < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - digito1;
        }

        soma = 0;

        int[] peso2 = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

        for (int i = 0; i < 13; i++) {
            soma += Integer.parseInt(cnpj.substring(i, i + 1)) * peso2[i];
        }

        int digito2 = soma % 11;

        if (digito2 < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - digito2;
        }

        if (Integer.parseInt(cnpj.substring(12, 13)) == digito1
                && Integer.parseInt(cnpj.substring(13, 14)) == digito2) {
            return true;
        }

        return false;
    }

    public static String formatar_cep(String cep) {
        cep = cep.replace("-", "");
        cep = cep.strip();
        return cep;
    }

    public static EnderecoDto apurar_endereco(String cep) {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(url, EnderecoDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatar_email(String email) {
        email = email.strip();
        email = email.toLowerCase();
        return email;
    }

    public static Boolean verificar_email(String email) {
        if (email.contains("@") && email.contains(".")) {
            return true;
        }
        return false;
    }
}
