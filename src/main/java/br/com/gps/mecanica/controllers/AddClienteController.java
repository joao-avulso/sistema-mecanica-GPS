package br.com.gps.mecanica.controllers;

import java.util.List;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.dto.EnderecoDto;
import br.com.gps.mecanica.enums.ContatoEnum;
import br.com.gps.mecanica.enums.CorEnum;
import br.com.gps.mecanica.enums.PessoaEnum;
import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.services.ClienteService;
import br.com.gps.mecanica.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddClienteController {

    @FXML
    private TextField estadoTextField;

    @FXML
    private TextField modeloTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField ruaTextField;

    @FXML
    private TextField complementoTextField;

    @FXML
    private TextField telefoneTextField;

    @FXML
    private TextField anoTextField;

    @FXML
    private Button addButton;

    @FXML
    private TextField cpfTextField;

    @FXML
    private TextField bairroTextField;

    @FXML
    private TextField referenciaTextField;

    @FXML
    private TextField placaTextField;

    @FXML
    private ChoiceBox<CorEnum> corChoiceBox;

    @FXML
    private ChoiceBox<ContatoEnum> contatoChoiceBox;

    @FXML
    private TextField nomeTextField;

    @FXML
    private TextField numeroTextField;

    @FXML
    private TextField marcaTextField;

    @FXML
    private Label erroLabel;

    @FXML
    private TextField cepTextField;

    @FXML
    private TextField cidadeTextField;

    private ClienteService clienteService;

    @FXML
    void initialize() {
        this.clienteService = MecanicaFxMainApplication.getBean(ClienteService.class);

        corChoiceBox.getItems().addAll(CorEnum.values());
        corChoiceBox.setValue(CorEnum.BRANCO);

        contatoChoiceBox.getItems().addAll(ContatoEnum.values());
        contatoChoiceBox.setValue(ContatoEnum.RESIDENCIAL);

        anoTextField.setTextFormatter(new javafx.scene.control.TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));

        telefoneTextField.setTextFormatter(new javafx.scene.control.TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));

        cepTextField.setOnKeyTyped(e -> {
            String cep = cepTextField.getText();

            if (cep.length() == 8) {
                try {
                    EnderecoDto endereco = Utils.apurarEndereco(cep);
                    bairroTextField.setText(endereco.getBairro());
                    cidadeTextField.setText(endereco.getLocalidade());
                    estadoTextField.setText(endereco.getUf());
                    ruaTextField.setText(endereco.getLogradouro());
                    complementoTextField.setText(endereco.getComplemento());
                } catch (Exception ex) {
                    errorMessage(ex.getMessage());
                }
            }

            System.out.println(cep);
        });
    }

    @FXML
    void adicionar(ActionEvent event) {
        try {
            String cpf = cpfTextField.getText();
            String nome = nomeTextField.getText();
            String email = emailTextField.getText();
            String telefone = telefoneTextField.getText();
            String rua = ruaTextField.getText();
            String numero = numeroTextField.getText();
            String complemento = complementoTextField.getText();
            String bairro = bairroTextField.getText();
            String cidade = cidadeTextField.getText();
            String estado = estadoTextField.getText();
            String cep = cepTextField.getText();
            String referencia = referenciaTextField.getText();
            String placa = placaTextField.getText();
            String marca = marcaTextField.getText();
            String modelo = modeloTextField.getText();
            String ano = anoTextField.getText();
            CorEnum cor = corChoiceBox.getValue();
            ContatoEnum contato = contatoChoiceBox.getValue();

            if (cpf.isEmpty() || nome.isEmpty()) {
                errorMessage("Preencha os campos obrigatórios CPF e NOME");
                return;
            }

            ClienteModel cliente = new ClienteModel(PessoaEnum.FISICA, nome, cpf, email, null, null, null);

            if (!telefone.isEmpty()) {
                cliente.setTelefones(List.of(new TelefoneModel(telefone, contato, cliente)));
            }

            if (!cep.isEmpty() || !rua.isEmpty() || !numero.isEmpty() || !bairro.isEmpty() || !cidade.isEmpty() || !estado.isEmpty()) {
                if (cep.isEmpty() || rua.isEmpty() || numero.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || estado.isEmpty()) {
                    errorMessage("Preencha todos os campos do endereço");
                    return;
                }
                
                cliente.setEnderecos(List.of(new EnderecoModel(cep, rua, bairro, cidade, estado, numero, complemento, referencia, contato, cliente)));
            }

            if (!placa.isEmpty() || !marca.isEmpty() || !modelo.isEmpty() || !ano.isEmpty()) {
                if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty() || ano.isEmpty()) {
                    errorMessage("Preencha todos os campos do veículo");
                    return;
                }

                cliente.setVeiculos(List.of(new VeiculoModel(placa, marca, modelo, Integer.parseInt(ano), cor, cliente)));
            }


            clienteService.create(cliente);
            modeloTextField.getScene().getWindow().hide();
        } catch (Exception e) {
            errorMessage(e.getMessage());
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        modeloTextField.getScene().getWindow().hide();
    }

    void errorMessage(String message) {
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
}
