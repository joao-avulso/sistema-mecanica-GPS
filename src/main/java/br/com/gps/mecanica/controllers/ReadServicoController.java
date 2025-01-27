package br.com.gps.mecanica.controllers;

import java.util.UUID;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.models.ServicoModel;
import br.com.gps.mecanica.services.ServicoService;
import br.com.gps.mecanica.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ReadServicoController {

    @FXML
    private TextField valorVendaTextField;

    @FXML
    private Button fecharButton;

    @FXML
    private TextField nomeTextField;

    @FXML
    private Label erroLabel;

    @FXML
    private Button atualizarButton;

    @FXML
    private TextField descricaoTextField;

    private ServicoService servicoService;

    private UUID servicoId;

    @FXML
    void initialize() {
        this.servicoService = MecanicaFxMainApplication.getBean(ServicoService.class);
        Utils.formatterDouble(valorVendaTextField);
    }

    public void carregarServico(ServicoModel servico) {
        this.servicoId = servico.getId();
        nomeTextField.setText(servico.getNome());
        descricaoTextField.setText(servico.getDescricao());
        valorVendaTextField.setText(servico.getValor().toString());
    }

    @FXML
    void atualizar(ActionEvent event) {
        String nome = nomeTextField.getText();
        String descricao = descricaoTextField.getText();
        Double valor = Double.parseDouble(valorVendaTextField.getText());

        try {
            servicoService.update(servicoId, new ServicoModel(nome, descricao, valor));
            nomeTextField.getScene().getWindow().hide();
        } catch (Exception e) {
            Utils.errorMessage(erroLabel, "Erro ao atualizar serviço: " + e.getMessage());
        }

    }

    @FXML
    void fechar(ActionEvent event) {
        nomeTextField.getScene().getWindow().hide();
    }

}
