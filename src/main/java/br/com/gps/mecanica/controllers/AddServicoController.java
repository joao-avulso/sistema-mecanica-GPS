package br.com.gps.mecanica.controllers;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.models.ServicoModel;
import br.com.gps.mecanica.services.ServicoService;
import br.com.gps.mecanica.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddServicoController {

    @FXML
    private TextField valorVendaTextField;

    @FXML
    private TextField nomeTextField;

    @FXML
    private Button cancelarButton;

    @FXML
    private Label erroLabel;

    @FXML
    private Button addButton;

    @FXML
    private TextField descricaoTextField;

    private ServicoService servicoService;

    @FXML
    void initialize() {
        this.servicoService = MecanicaFxMainApplication.getBean(ServicoService.class);
        Utils.formatterDouble(valorVendaTextField);
    }

    @FXML
    void adicionar(ActionEvent event) {
        String nome = nomeTextField.getText();
        String descricao = descricaoTextField.getText();
        Double valor = Double.parseDouble(valorVendaTextField.getText());

        if (nome.isEmpty() || valorVendaTextField.getText().isEmpty()) {
            erroLabel.setText("Preencha os campos obrigatórios");
            return;
        }

        try {
            servicoService.create(new ServicoModel(nome, descricao, valor));
            nomeTextField.getScene().getWindow().hide();
        } catch (Exception e) {
            Utils.errorMessage(erroLabel, descricao);
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        nomeTextField.getScene().getWindow().hide();
    }

}