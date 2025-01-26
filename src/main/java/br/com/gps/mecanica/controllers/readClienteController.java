package br.com.gps.mecanica.controllers;

import java.util.List;
import java.util.UUID;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.dto.EnderecoDto;
import br.com.gps.mecanica.enums.ContatoEnum;
import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.services.ClienteService;
import br.com.gps.mecanica.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class readClienteController {

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
    private Button atualizarButton;

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

    @FXML
    private TableView<VeiculoModel> veiculosTable;

    @FXML
    private TableColumn<VeiculoModel, String> placaColumn;

    @FXML
    private TableColumn<VeiculoModel, String> modeloColumn;

    @FXML
    private TableColumn<VeiculoModel, String> marcaColumn;

    @FXML
    private TableColumn<VeiculoModel, Integer> anoColumn;

    @FXML
    private TableColumn<VeiculoModel, String> corColumn;

    private ClienteService clienteService;

    private UUID clienteId;

    @FXML
    void initialize() {
        this.clienteService = MecanicaFxMainApplication.getBean(ClienteService.class);

        contatoChoiceBox.getItems().addAll(ContatoEnum.values());
        contatoChoiceBox.setValue(ContatoEnum.RESIDENCIAL);

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

        placaColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPlaca()));
        modeloColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getModelo()));
        marcaColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMarca()));
        anoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAno()));
        corColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCor().toString()));

        veiculosTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void carregarCliente(ClienteModel cliente) {
        this.clienteId = cliente.getId();
        nomeTextField.setText(cliente.getNome());
        cpfTextField.setText(cliente.getCpf());
        emailTextField.setText(cliente.getEmail());

        if (cliente.getEnderecos() != null && !cliente.getEnderecos().isEmpty()) {
            EnderecoModel endereco = cliente.getEnderecos().get(0);
            cepTextField.setText(endereco.getCep());
            ruaTextField.setText(endereco.getRua());
            bairroTextField.setText(endereco.getBairro());
            cidadeTextField.setText(endereco.getCidade());
            estadoTextField.setText(endereco.getEstado());
            numeroTextField.setText(endereco.getNumero());
            complementoTextField.setText(endereco.getComplemento());
            referenciaTextField.setText(endereco.getReferencia());
        }

        if (cliente.getTelefones() != null && !cliente.getTelefones().isEmpty()) {
            TelefoneModel telefone = cliente.getTelefones().get(0);
            telefoneTextField.setText(telefone.getNumero());
            contatoChoiceBox.setValue(telefone.getTipo());
        }

        if (cliente.getVeiculos() != null && !cliente.getVeiculos().isEmpty()) {
            veiculosTable.getItems().setAll(cliente.getVeiculos());
        }
    }

    @FXML
    void atualizar(ActionEvent event) {
        try {
            ClienteModel clienteAtualizado = new ClienteModel();
            clienteAtualizado.setNome(nomeTextField.getText());
            clienteAtualizado.setCpf(cpfTextField.getText());
            clienteAtualizado.setEmail(emailTextField.getText());

            EnderecoModel endereco = new EnderecoModel();
            endereco.setCep(cepTextField.getText());
            endereco.setRua(ruaTextField.getText());
            endereco.setBairro(bairroTextField.getText());
            endereco.setCidade(cidadeTextField.getText());
            endereco.setEstado(estadoTextField.getText());
            endereco.setNumero(numeroTextField.getText());
            endereco.setComplemento(complementoTextField.getText());
            endereco.setReferencia(referenciaTextField.getText());
            clienteAtualizado.setEnderecos(List.of(endereco));

            TelefoneModel telefone = new TelefoneModel();
            telefone.setNumero(telefoneTextField.getText());
            telefone.setTipo((ContatoEnum) contatoChoiceBox.getValue());
            clienteAtualizado.setTelefones(List.of(telefone));

            clienteService.update(clienteId, clienteAtualizado);

            nomeTextField.getScene().getWindow().hide();
        } catch (Exception e) {
            Utils.errorMessage(erroLabel, "Erro ao atualizar cliente: " + e.getMessage());
            ;
        }

    }

    @FXML
    void cancelar(ActionEvent event) {
        nomeTextField.getScene().getWindow().hide();
    }

}
