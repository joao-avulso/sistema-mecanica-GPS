package br.com.gps.mecanica.controllers;

import java.util.List;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.dto.EnderecoDto;
import br.com.gps.mecanica.enums.ContatoEnum;
import br.com.gps.mecanica.enums.PessoaEnum;
import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.services.FornecedorService;
import br.com.gps.mecanica.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddFornecedorController {

    @FXML
    private TextField estadoTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField ruaTextField;

    @FXML
    private TextField complementoTextField;

    @FXML
    private ChoiceBox<ContatoEnum> contatoChoiceBox;

    @FXML
    private TextField telefoneTextField;

    @FXML
    private Button addButton;

    @FXML
    private TextField cpfTextField;

    @FXML
    private TextField bairroTextField;

    @FXML
    private TextField referenciaTextField;

    @FXML
    private TextField nomeTextField;

    @FXML
    private TextField numeroTextField;

    @FXML
    private Label erroLabel;

    @FXML
    private TextField cepTextField;

    @FXML
    private TextField cidadeTextField;

    private FornecedorService fornecedorService;

    @FXML
    void initialize() {
        this.fornecedorService = MecanicaFxMainApplication.getBean(FornecedorService.class);

        contatoChoiceBox.setValue(ContatoEnum.RESIDENCIAL);
        contatoChoiceBox.getItems().addAll(ContatoEnum.values());

        Utils.formatterInt(telefoneTextField);

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
                    Utils.errorMessage(erroLabel, ex.getMessage());
                }
            }

            System.out.println(cep);
        });
    }

    @FXML
    void adicionar(ActionEvent event) {
        try {
            String cnpj = cpfTextField.getText();
            String nome = nomeTextField.getText();
            String email = emailTextField.getText();
            String telefone = telefoneTextField.getText();
            String cep = cepTextField.getText();
            String rua = ruaTextField.getText();
            String numero = numeroTextField.getText();
            String bairro = bairroTextField.getText();
            String cidade = cidadeTextField.getText();
            String estado = estadoTextField.getText();
            String complemento = complementoTextField.getText();
            String referencia = referenciaTextField.getText();

            if (cnpj.isEmpty() || nome.isEmpty() || email.isEmpty()) {
                throw new Exception("Preencha todos os campos obrigatórios");
            }

            FornecedorModel fornecedor = new FornecedorModel(PessoaEnum.JURIDICA, nome, email, null, null,
                    cnpj, null);

            if (!telefone.isEmpty()) {
                fornecedor.setTelefones(List.of(new TelefoneModel(telefone, contatoChoiceBox.getValue(), fornecedor)));
            }

            if (!cep.isEmpty() || !rua.isEmpty() || !numero.isEmpty() || !bairro.isEmpty() || !cidade.isEmpty()
                    || !estado.isEmpty()) {
                if (cep.isEmpty() || rua.isEmpty() || numero.isEmpty() || bairro.isEmpty() || cidade.isEmpty()
                        || estado.isEmpty()) {
                    Utils.errorMessage(erroLabel, "Preencha todos os campos do endereço");
                    return;
                }

                fornecedor.setEnderecos(List.of(new EnderecoModel(cep, rua, bairro, cidade, estado, numero, complemento,
                        referencia, contatoChoiceBox.getValue(), fornecedor)));
            }

            fornecedorService.create(fornecedor);
            nomeTextField.getScene().getWindow().hide();
        } catch (Exception e) {
            Utils.errorMessage(erroLabel, e.getMessage());
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        nomeTextField.getScene().getWindow().hide();
    }

}
