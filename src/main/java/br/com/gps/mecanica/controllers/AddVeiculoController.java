package br.com.gps.mecanica.controllers;

import java.util.concurrent.atomic.AtomicBoolean;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.enums.CorEnum;
import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.services.ClienteService;
import br.com.gps.mecanica.services.VeiculoService;
import br.com.gps.mecanica.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddVeiculoController {

    @FXML
    private TextField placaTextField;

    @FXML
    private TextField modeloTextField;

    @FXML
    private TextField marcaTextField;

    @FXML
    private TextField anoTextField;

    @FXML
    private Label erroLabel;

    @FXML
    private Label nomeClienteLabel;

    @FXML
    private ChoiceBox<CorEnum> corChoiceBox;

    @FXML
    private ComboBox<String> cpfComboBox;

    private VeiculoService veiculoService;

    private ClienteService clienteService;

    private AtomicBoolean isUpdating = new AtomicBoolean(false);

    @FXML
    void initialize() {
        this.veiculoService = MecanicaFxMainApplication.getBean(VeiculoService.class);
        this.clienteService = MecanicaFxMainApplication.getBean(ClienteService.class);

        corChoiceBox.getItems().addAll(CorEnum.values());
        corChoiceBox.setValue(CorEnum.BRANCO);

        ObservableList<String> clientes = FXCollections
                .observableArrayList(clienteService.get().stream().map(cliente -> cliente.getCpf()).toList());
        
        FilteredList<String> filteredClientes = new FilteredList<>(clientes, p -> true);

        cpfComboBox.setItems(filteredClientes);

        Utils.searchComboBox(cpfComboBox, filteredClientes, isUpdating);

        cpfComboBox.setOnAction(event -> {
            try {
                String cpf = cpfComboBox.getValue();
                if (cpf == null || cpf.isEmpty()) {
                    nomeClienteLabel.setVisible(false);
                    return;
                }
                ClienteModel clienteModel;
                clienteModel = clienteService.get(cpf);

                nomeClienteLabel.setVisible(true);
                nomeClienteLabel.setText(clienteModel.getNome());
            } catch (Exception e) {
                Utils.errorMessage(erroLabel, e.getMessage());
            }
        });
    }

    @FXML
    void adicionar(ActionEvent event) {
        String placa = placaTextField.getText();
        String modelo = modeloTextField.getText();
        String marca = marcaTextField.getText();
        String cpf = cpfComboBox.getValue().toString();
        CorEnum cor = corChoiceBox.getValue();

        if (placa.isEmpty() || modelo.isEmpty() || marca.isEmpty() || cpf.isEmpty()
                || anoTextField.getText().isEmpty()) {
            Utils.errorMessage(erroLabel, "Preencha todos os campos");
            return;
        }

        Integer ano = Integer.parseInt(anoTextField.getText());

        try {
            VeiculoModel veiculo = new VeiculoModel(placa, modelo, marca, ano, cor, clienteService.get(cpf));
            veiculoService.create(veiculo);
            modeloTextField.getScene().getWindow().hide();
        } catch (Exception e) {
            Utils.errorMessage(erroLabel, e.getMessage());
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        modeloTextField.getScene().getWindow().hide();
    }

}
