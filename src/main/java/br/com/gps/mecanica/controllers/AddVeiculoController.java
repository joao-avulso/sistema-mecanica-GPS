package br.com.gps.mecanica.controllers;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.enums.CorEnum;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.services.ClienteService;
import br.com.gps.mecanica.services.VeiculoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ChoiceBox<CorEnum> corChoiceBox;

    @FXML
    private ComboBox<String> cpfComboBox;

    VeiculoService veiculoService;

    ClienteService clienteService;

    @FXML
    void initialize() {
        this.veiculoService = MecanicaFxMainApplication.getBean(VeiculoService.class);
        this.clienteService = MecanicaFxMainApplication.getBean(ClienteService.class);

        corChoiceBox.getItems().addAll(CorEnum.values());
        corChoiceBox.setValue(CorEnum.BRANCO);

        ObservableList<String> cpfs = FXCollections.observableArrayList(clienteService.get().stream().map(cliente -> cliente.getCpf()).toList());

        cpfComboBox.getItems().addAll(cpfs);

        // Adiciona um listener para buscar CPFs semelhantes ao texto digitado
        cpfComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                ObservableList<String> filteredCpfs = cpfs.filtered(cpf -> cpf.contains(newValue));
                cpfComboBox.setItems(filteredCpfs);

                // Abre o menu do ComboBox automaticamente
                if (!filteredCpfs.isEmpty()) {
                    cpfComboBox.show();
                }
            } else {
                cpfComboBox.setItems(cpfs);
                cpfComboBox.hide();
            }
        });

        // Configura um filtro para aceitar apenas números inteiros
        anoTextField.setTextFormatter(new javafx.scene.control.TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }

    @FXML
    void adicionar(ActionEvent event) {
        String placa = placaTextField.getText();
        String modelo = modeloTextField.getText();
        String marca = marcaTextField.getText();
        String cpf = cpfComboBox.getValue().toString();
        CorEnum cor = corChoiceBox.getValue();

        if (placa.isEmpty() || modelo.isEmpty() || marca.isEmpty() || cpf.isEmpty() || anoTextField.getText().isEmpty()) {
            errorMessage("Preencha todos os campos");
            return;
        }

        Integer ano = Integer.parseInt(anoTextField.getText());

        try {
            VeiculoModel veiculo = new VeiculoModel(placa, modelo, marca, ano, cor, clienteService.get(cpf));
            veiculoService.create(veiculo);
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
