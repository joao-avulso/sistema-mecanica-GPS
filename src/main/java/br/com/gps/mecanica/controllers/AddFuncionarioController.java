package br.com.gps.mecanica.controllers;

import java.time.LocalDate;
import java.util.List;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.dto.EnderecoDto;
import br.com.gps.mecanica.enums.CargoEnum;
import br.com.gps.mecanica.enums.ContatoEnum;
import br.com.gps.mecanica.enums.PessoaEnum;
import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.FuncionarioModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.services.FuncionarioService;
import br.com.gps.mecanica.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddFuncionarioController {
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
    private ChoiceBox<CargoEnum> cargoChoiceBox;

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

    private FuncionarioService funcionarioService;

    @FXML
    void initialize() {
        funcionarioService = MecanicaFxMainApplication.getBean(FuncionarioService.class);

        contatoChoiceBox.getItems().addAll(ContatoEnum.values());
        contatoChoiceBox.setValue(ContatoEnum.RESIDENCIAL);

        cargoChoiceBox.getItems().addAll(CargoEnum.values());
        cargoChoiceBox.setValue(CargoEnum.ATENDENTE);

        Utils.formatterInt(telefoneTextField);
        Utils.formatterInt(cepTextField);

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
            ContatoEnum contato = contatoChoiceBox.getValue();
            CargoEnum cargo = cargoChoiceBox.getValue();

            if (cpf.isEmpty() || nome.isEmpty() || email.isEmpty()) {
                Utils.errorMessage(erroLabel, "Preencha os campos obrigatórios CPF, NOME e EMAIL");
                return;
            }

            FuncionarioModel funcionario = new FuncionarioModel(PessoaEnum.FISICA, nome, cpf, email, null, null, cargo, LocalDate.now());

            if (!telefone.isEmpty()) {
                funcionario.setTelefones(List.of(new TelefoneModel(telefone, contato, funcionario)));
            }

            if (!cep.isEmpty() || !rua.isEmpty() || !numero.isEmpty() || !bairro.isEmpty() || !cidade.isEmpty()
                    || !estado.isEmpty()) {
                if (cep.isEmpty() || rua.isEmpty() || numero.isEmpty() || bairro.isEmpty() || cidade.isEmpty()
                        || estado.isEmpty()) {
                    Utils.errorMessage(erroLabel, "Preencha todos os campos do endereço");
                    return;
                }

                funcionario.setEnderecos(List.of(new EnderecoModel(cep, rua, bairro, cidade, estado, numero, complemento,
                        referencia, contato, funcionario)));
            }

            funcionarioService.create(funcionario);
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
