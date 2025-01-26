package br.com.gps.mecanica.controllers;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.services.FornecedorService;
import br.com.gps.mecanica.services.ProdutoService;
import br.com.gps.mecanica.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddProdutoController {

    @FXML
    private TextField valorVendaTextField;

    @FXML
    private TextField valorCompraTextField;

    @FXML
    private TextField nomeTextField;

    @FXML
    private ComboBox<String> cnpjComboBox;

    @FXML
    private Label erroLabel;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelarButton;

    @FXML
    private TextField quantidadeTextField;

    @FXML
    private TextField descricaoTextField;

    private FornecedorService fornecedorService;

    private ProdutoService produtoService;

    @FXML
    void initialize() {
        this.fornecedorService = MecanicaFxMainApplication.getBean(FornecedorService.class);
        this.produtoService = MecanicaFxMainApplication.getBean(ProdutoService.class);

        Utils.formatterDouble(valorCompraTextField);
        Utils.formatterDouble(valorVendaTextField);
        Utils.formatterInt(quantidadeTextField);

        ObservableList<String> cnpjs = FXCollections.observableArrayList(fornecedorService.get().stream().map(fornecedor -> fornecedor.getCnpj()).toList());

        cnpjComboBox.getItems().addAll(cnpjs);

        cnpjComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                ObservableList<String> filteredCpfs = cnpjs.filtered(cpf -> cpf.contains(newValue));
                cnpjComboBox.setItems(filteredCpfs);

                if (!filteredCpfs.isEmpty()) {
                    cnpjComboBox.show();
                }
            } else {
                cnpjComboBox.setItems(cnpjs);
                cnpjComboBox.hide();
            }
        });
    }

    @FXML
    void adicionar(ActionEvent event) {
        String nome = nomeTextField.getText();
        String cnpj = cnpjComboBox.getValue();
        String descricao = descricaoTextField.getText();
        
        if (nome.isBlank() || valorVendaTextField.getText().isEmpty() || quantidadeTextField.getText().isEmpty()) {
            Utils.errorMessage(erroLabel,"Preencha os campos obrigatórios");
            return;
        }

        if (valorCompraTextField.getText().isEmpty()) valorCompraTextField.setText("0.0");
        if (valorVendaTextField.getText().isEmpty()) valorVendaTextField.setText("0.0");
        
        double valorCompra = Double.parseDouble(valorCompraTextField.getText());
        double valorVenda = Double.parseDouble(valorVendaTextField.getText());
        int quantidade = Integer.parseInt(quantidadeTextField.getText());

        try {
            ProdutoModel produto = new ProdutoModel(nome, descricao, valorVenda, valorCompra, quantidade, fornecedorService.get(cnpj));
            produtoService.create(produto);
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
