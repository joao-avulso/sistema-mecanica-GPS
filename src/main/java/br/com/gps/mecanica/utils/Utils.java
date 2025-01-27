package br.com.gps.mecanica.utils;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.web.client.RestTemplate;

import br.com.gps.mecanica.MecanicaApplication;
import br.com.gps.mecanica.controllers.ReadClienteController;
import br.com.gps.mecanica.controllers.ReadFornecedorController;
import br.com.gps.mecanica.controllers.ReadProdutoController;
import br.com.gps.mecanica.controllers.ReadServicoController;
import br.com.gps.mecanica.controllers.ReadVeiculoController;
import br.com.gps.mecanica.dto.EnderecoDto;
import br.com.gps.mecanica.enums.MenuSelectionEnum;
import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.models.ServicoModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.models.VeiculoModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Utils {
    public static String formatarString(String string) {
        if (string == null) {
            return null;
        }

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

    public static String formatarNome(String nome) {
        nome = nome.strip();
        nome = nome.toUpperCase();
        return nome;
    }

    public static String formatarPlaca(String placa) {
        if (placa == null) {
            return null;
        }

        placa = formatarString(placa);
        placa = placa.replace("-", "");
        placa = placa.replace(" ", "");
        return placa;
    }

    public static String formatarMarcaModeloVeiculo(String modelo) {
        if (modelo == null) {
            return null;
        }
        modelo = formatarString(modelo);
        modelo = modelo.replace(" ", "");
        return modelo;
    }

    public static TelefoneModel formatarTelefone(TelefoneModel telefone) {
        if (telefone == null) {
            return null;
        }

        String numero = telefone.getNumero();
        numero = formatarString(numero);
        numero = numero.replace("(", "");
        numero = numero.replace(")", "");
        numero = numero.replace("-", "");
        numero = numero.replace(" ", "");

        telefone.setNumero(numero);
        return telefone;
    }

    public static String formatarCpf(String cpf) {
        if (cpf == null) {
            return null;
        }

        cpf = formatarString(cpf);
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");
        return cpf;
    }

    public static Boolean verificarCpf(String cpf) {
        if (cpf == null) {
            return false;
        }

        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (cpf.length() != 11))
            return (false);

        char dig10;
        char dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                return (true);
            else
                return (false);

        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static String formatarCnpj(String cnpj) {
        if (cnpj == null) {
            return null;
        }

        cnpj = formatarString(cnpj);
        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace("/", "");
        cnpj = cnpj.replace("-", "");
        return cnpj;
    }

    public static Boolean verificarCnpj(String cnpj) {
        if (cnpj == null) {
            return false;
        }

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

    public static String formatarCep(String cep) {
        if (cep == null) {
            return null;
        }

        cep = formatarString(cep);
        cep = cep.replace("-", "");
        return cep;
    }

    public static EnderecoDto apurarEndereco(String cep) {
        if (cep == null) {
            return null;
        }
        cep = formatarCep(cep);
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(url, EnderecoDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatarEmail(String email) {
        if (email == null) {
            return null;
        }
        email = email.strip();
        email = email.toLowerCase();
        return email;
    }

    public static Boolean verificarEmail(String email) {
        if (email == null) {
            return false;
        }
        String regex = "^(.+)@(.+)$";
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }

    public static Boolean verificarPlaca(String placa) {
        if (placa == null) {
            return false;
        }
        String regex = "^[a-zA-Z]{3}[0-9]{4}$";
        String regexMercosulCarro = "^[a-zA-Z]{3}[0-9]{1}[a-zA-Z]{1}[0-9]{2}$";
        String regexMercosulMoto = "^[a-zA-Z]{3}[0-9]{2}[a-zA-Z]{1}[0-9]{1}$";
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(placa).matches()
                || Pattern.compile(regexMercosulCarro, Pattern.CASE_INSENSITIVE).matcher(placa).matches()
                || Pattern.compile(regexMercosulMoto, Pattern.CASE_INSENSITIVE).matcher(placa).matches();
    }

    public static void formatarEndereco(EnderecoModel endereco) {
        if (endereco == null) {
            return;
        }
        endereco.setRua(formatarString(endereco.getRua()));
        endereco.setBairro(formatarString(endereco.getBairro()));
        endereco.setCidade(formatarString(endereco.getCidade()));
        endereco.setEstado(formatarString(endereco.getEstado()));
        endereco.setComplemento(formatarString(endereco.getComplemento()));
        endereco.setReferencia(formatarString(endereco.getReferencia()));
    }

    public static void formatarVeiculo(VeiculoModel veiculo) {
        if (veiculo == null) {
            return;
        }
        veiculo.setPlaca(formatarPlaca(veiculo.getPlaca()));
        veiculo.setModelo(formatarMarcaModeloVeiculo(veiculo.getModelo()));
        veiculo.setMarca(formatarMarcaModeloVeiculo(veiculo.getMarca()));
    }

    public static void formatterInt(TextField textField) {
        textField.setTextFormatter(new javafx.scene.control.TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }

    public static void formatterDouble(TextField textField) {
        textField.setTextFormatter(new javafx.scene.control.TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));
    }

    public static void errorMessage(Label erroLabel, String message) {
        erroLabel.setText(message + ".*");
        erroLabel.setVisible(true);

        // Fecha a mensagem de erro após 5 segundos
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                erroLabel.setVisible(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void lerItem(MenuSelectionEnum tipo, Object item) {
        try {
            Parent root = null;
            switch (tipo) {
                case VEICULO:
                    FXMLLoader loader = new FXMLLoader(MecanicaApplication.class.getResource("readVeiculo.fxml"));
                    root = loader.load();

                    ReadVeiculoController controller = loader.getController();
                    controller.carregarVeiculo((VeiculoModel) item);
                    break;
                case SERVICO:
                    FXMLLoader loaderServico = new FXMLLoader(
                            MecanicaApplication.class.getResource("readServico.fxml"));
                    root = loaderServico.load();

                    ReadServicoController controllerServico = loaderServico.getController();
                    controllerServico.carregarServico((ServicoModel) item);
                    break;
                case PRODUTO:
                    FXMLLoader loaderProduto = new FXMLLoader(
                            MecanicaApplication.class.getResource("readProduto.fxml"));
                    root = loaderProduto.load();

                    ReadProdutoController controllerProduto = loaderProduto.getController();
                    controllerProduto.carregarProduto((ProdutoModel) item);
                    break;
                case FORNECEDOR:
                    FXMLLoader loaderFornecedor = new FXMLLoader(
                            MecanicaApplication.class.getResource("readFornecedor.fxml"));
                    root = loaderFornecedor.load();

                    ReadFornecedorController controllerFornecedor = loaderFornecedor.getController();
                    controllerFornecedor.carregarFornecedor((FornecedorModel) item);
                    break;
                case CLIENTE:
                    FXMLLoader loaderCliente = new FXMLLoader(
                            MecanicaApplication.class.getResource("readCliente.fxml"));
                    root = loaderCliente.load();

                    ReadClienteController controllerCliente = loaderCliente.getController();
                    controllerCliente.carregarCliente((ClienteModel) item);
                    break;
                default:
                    break;
            }

            Stage stage = new Stage();
            stage.setTitle("Detalhes");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
