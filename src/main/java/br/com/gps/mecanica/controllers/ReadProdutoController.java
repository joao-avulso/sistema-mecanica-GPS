package br.com.gps.mecanica.controllers;

import java.util.UUID;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.services.FornecedorService;
import br.com.gps.mecanica.services.ProdutoService;
import br.com.gps.mecanica.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ReadProdutoController {

    @FXML
    private TextField valorVendaTextField;

    @FXML
    private Button fecharButton;

    @FXML
    private TextField valorCompraTextField;

    @FXML
    private TextField nomeTextField;

    @FXML
    private Label erroLabel;

    @FXML
    private Button atualizarButton;

    @FXML
    private TextField quantidadeTextField;

    @FXML
    private TextField fornecedorTextField;

    @FXML
    private TextField descricaoTextField;

    private FornecedorService fornecedorService;

    private ProdutoService produtoService;

    private UUID produtoId;

    @FXML
    void initialize() {
        this.fornecedorService = MecanicaFxMainApplication.getBean(FornecedorService.class);
        this.produtoService = MecanicaFxMainApplication.getBean(ProdutoService.class);

        Utils.formatterDouble(valorCompraTextField);
        Utils.formatterDouble(valorVendaTextField);
        Utils.formatterInt(quantidadeTextField);
    }

    public void carregarProduto(ProdutoModel produto) {
        this.produtoId = produto.getId();
        this.nomeTextField.setText(produto.getNome());
        this.descricaoTextField.setText(produto.getDescricao());
        this.valorCompraTextField.setText(String.valueOf(produto.getValorCompra()));
        this.valorVendaTextField.setText(String.valueOf(produto.getValorVenda()));
        this.quantidadeTextField.setText(String.valueOf(produto.getQuantidade()));
        this.fornecedorTextField.setText(produto.getFornecedor().getCnpj());
        fornecedorTextField.setEditable(false);
    }

    @FXML
    void atualizar(ActionEvent event) {
        try {
            ProdutoModel produto = new ProdutoModel();
            produto.setId(produtoId);
            produto.setNome(nomeTextField.getText());
            produto.setDescricao(descricaoTextField.getText());
            produto.setValorCompra(Double.parseDouble(valorCompraTextField.getText()));
            produto.setValorVenda(Double.parseDouble(valorVendaTextField.getText()));
            produto.setQuantidade(Integer.parseInt(quantidadeTextField.getText()));
            produto.setFornecedor(fornecedorService.get(fornecedorTextField.getText()));

            produtoService.update(produtoId, produto);
            fecharButton.getScene().getWindow().hide();
        } catch (Exception e) {
            Utils.errorMessage(erroLabel, "Erro ao atualizar produto: " + e.getMessage());
        }

    }

    @FXML
    void fechar(ActionEvent event) {
        fecharButton.getScene().getWindow().hide();
    }

}
