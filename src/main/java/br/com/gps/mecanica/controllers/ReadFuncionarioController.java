package br.com.gps.mecanica.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.dto.EnderecoDto;
import br.com.gps.mecanica.enums.CargoEnum;
import br.com.gps.mecanica.enums.ContatoEnum;
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

public class ReadFuncionarioController {

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
    private ChoiceBox<CargoEnum> cargoChoiceBox;

    @FXML
    private Button atualizarButton;

    @FXML
    private TextField cpfTextField;

    @FXML
    private TextField bairroTextField;

    @FXML
    private TextField referenciaTextField;

    @FXML
    private Button fecharButton;

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

    private UUID funcionarioId;

    private LocalDate dataAdmissao;

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

    public void carregarFuncionario(FuncionarioModel funcionario) {
        this.funcionarioId = funcionario.getId();
        this.dataAdmissao = funcionario.getDataAdmissao();
        this.nomeTextField.setText(funcionario.getNome());
        this.emailTextField.setText(funcionario.getEmail());

        if (funcionario.getTelefones() != null && !funcionario.getTelefones().isEmpty()) {
            TelefoneModel telefone = funcionario.getTelefones().get(0);
            telefoneTextField.setText(telefone.getNumero());
            contatoChoiceBox.setValue(telefone.getTipo());
            this.telefoneTextField.setText(telefone.getNumero());
        }

        this.cpfTextField.setText(funcionario.getCpf());
        this.cargoChoiceBox.setValue(funcionario.getCargo());

        if (funcionario.getEnderecos() != null && !funcionario.getEnderecos().isEmpty()) {
            this.cepTextField.setText(funcionario.getEnderecos().get(0).getCep());
            this.bairroTextField.setText(funcionario.getEnderecos().get(0).getBairro());
            this.cidadeTextField.setText(funcionario.getEnderecos().get(0).getCidade());
            this.estadoTextField.setText(funcionario.getEnderecos().get(0).getEstado());
            this.ruaTextField.setText(funcionario.getEnderecos().get(0).getRua());
            this.numeroTextField.setText(funcionario.getEnderecos().get(0).getNumero());
            this.complementoTextField.setText(funcionario.getEnderecos().get(0).getComplemento());
            this.referenciaTextField.setText(funcionario.getEnderecos().get(0).getReferencia());
        }
    }

    @FXML
    void atualizar(ActionEvent event) {
        try {
            FuncionarioModel funcionarioAtualizado = funcionarioService.get(funcionarioId);

            funcionarioAtualizado.setNome(nomeTextField.getText());
            funcionarioAtualizado.setCpf(cpfTextField.getText());
            funcionarioAtualizado.setEmail(emailTextField.getText());
            funcionarioAtualizado.setCargo(cargoChoiceBox.getValue());
            funcionarioAtualizado.setDataAdmissao(dataAdmissao);

            EnderecoModel endereco = funcionarioAtualizado.getEnderecos().isEmpty()
                    ? new EnderecoModel()
                    : funcionarioAtualizado.getEnderecos().get(0);
            endereco.setCep(cepTextField.getText());
            endereco.setRua(ruaTextField.getText());
            endereco.setBairro(bairroTextField.getText());
            endereco.setCidade(cidadeTextField.getText());
            endereco.setEstado(estadoTextField.getText());
            endereco.setNumero(numeroTextField.getText());
            endereco.setComplemento(complementoTextField.getText());
            endereco.setReferencia(referenciaTextField.getText());
            endereco.setPessoa(funcionarioAtualizado);

            funcionarioAtualizado.setEnderecos(List.of(endereco));

            TelefoneModel telefone = funcionarioAtualizado.getTelefones().isEmpty()
                    ? new TelefoneModel()
                    : funcionarioAtualizado.getTelefones().get(0);
            telefone.setNumero(telefoneTextField.getText());
            telefone.setTipo(contatoChoiceBox.getValue());
            telefone.setPessoa(funcionarioAtualizado);

            funcionarioAtualizado.setTelefones(List.of(telefone));

            funcionarioService.update(funcionarioId, funcionarioAtualizado);

            nomeTextField.getScene().getWindow().hide();
        } catch (Exception e) {
            Utils.errorMessage(erroLabel, e.getMessage());
        }

    }

    @FXML
    void fechar(ActionEvent event) {
        nomeTextField.getScene().getWindow().hide();
    }

}
