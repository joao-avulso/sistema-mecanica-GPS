package br.com.gps.mecanica.controllers;

import java.util.concurrent.atomic.AtomicBoolean;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.services.FornecedorService;
import br.com.gps.mecanica.services.ProdutoService;
import br.com.gps.mecanica.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
    private ComboBox<String> fornecedorComboBox;

    @FXML
    private Label erroLabel;

    @FXML
    private Label cnpjFornecedorLabel;

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

    private AtomicBoolean isUpdating = new AtomicBoolean(false);

    @FXML
    void initialize() {
        this.fornecedorService = MecanicaFxMainApplication.getBean(FornecedorService.class);
        this.produtoService = MecanicaFxMainApplication.getBean(ProdutoService.class);

        Utils.formatterDouble(valorCompraTextField);
        Utils.formatterDouble(valorVendaTextField);
        Utils.formatterInt(quantidadeTextField);

        ObservableList<String> fornecedores = FXCollections.observableArrayList(fornecedorService.get().stream().map(fornecedor -> fornecedor.getNome()).toList());

        FilteredList<String> filteredFornecedores = new FilteredList<>(fornecedores, p -> true);

        fornecedorComboBox.setItems(filteredFornecedores);

        Utils.searchComboBox(fornecedorComboBox, filteredFornecedores, isUpdating);

        fornecedorComboBox.setOnAction(event -> {
            try {
                String nome = fornecedorComboBox.getValue();
                if (nome == null || nome.isEmpty()) {
                    cnpjFornecedorLabel.setVisible(false);
                    return;
                }
                FornecedorModel fornecedorModel;
                fornecedorModel = fornecedorService.getByNome(nome).get(0);

                cnpjFornecedorLabel.setVisible(true);
                cnpjFornecedorLabel.setText(fornecedorModel.getCnpj());
            } catch (Exception e) {
                Utils.errorMessage(erroLabel, e.getMessage());
            }
        });
    }

    @FXML
    void adicionar(ActionEvent event) {
        String nome = nomeTextField.getText();
        String nomeFornecedor = fornecedorComboBox.getValue();
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
            ProdutoModel produto = new ProdutoModel(nome, descricao, valorVenda, valorCompra, quantidade, fornecedorService.getByNome(nomeFornecedor).get(0));
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
