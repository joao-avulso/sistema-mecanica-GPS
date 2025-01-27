package br.com.gps.mecanica.controllers;

import java.util.UUID;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.enums.CorEnum;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.services.ClienteService;
import br.com.gps.mecanica.services.VeiculoService;
import br.com.gps.mecanica.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ReadVeiculoController {

    @FXML
    private TextField placaTextField;

    @FXML
    private ChoiceBox<CorEnum> corChoiceBox;

    @FXML
    private TextField modeloTextField;

    @FXML
    private TextField cpfTextField;

    @FXML
    private TextField marcaTextField;

    @FXML
    private Label erroLabel;

    @FXML
    private TextField anoTextField;

    @FXML
    private Button atualizarButton;

    private VeiculoService veiculoService;

    private ClienteService clienteService;

    private UUID veiculoId;

    private String cpfCliente;

    @FXML
    void initialize() {
        this.veiculoService = MecanicaFxMainApplication.getBean(VeiculoService.class);
        this.clienteService = MecanicaFxMainApplication.getBean(ClienteService.class);

        corChoiceBox.getItems().addAll(CorEnum.values());
        corChoiceBox.setValue(CorEnum.BRANCO);

        Utils.formatterInt(anoTextField);
    }

    public void carregarVeiculo(VeiculoModel veiculo) {
        placaTextField.setText(veiculo.getPlaca());
        corChoiceBox.setValue(veiculo.getCor());
        modeloTextField.setText(veiculo.getModelo());
        marcaTextField.setText(veiculo.getMarca());
        anoTextField.setText(veiculo.getAno().toString());

        veiculoId = veiculo.getId();
        cpfCliente = veiculo.getCliente().getCpf();

        cpfTextField.setText(cpfCliente);
        cpfTextField.setEditable(false);
    }

    @FXML
    void atualizar(ActionEvent event) {
        try {
            String placa = placaTextField.getText();
            CorEnum cor = corChoiceBox.getValue();
            String modelo = modeloTextField.getText();
            String marca = marcaTextField.getText();
            Integer ano = Integer.parseInt(anoTextField.getText());

            VeiculoModel veiculo = new VeiculoModel(placa, modelo, marca, ano, cor, clienteService.get(cpfCliente));

            veiculoService.update(veiculoId, veiculo);

            atualizarButton.getScene().getWindow().hide();
        } catch (Exception e) {
            Utils.errorMessage(erroLabel, e.getMessage());
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        atualizarButton.getScene().getWindow().hide();
    }

}
