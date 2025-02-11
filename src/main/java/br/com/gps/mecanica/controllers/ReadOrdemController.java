package br.com.gps.mecanica.controllers;

import java.util.List;
import java.util.UUID;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.enums.MenuSelectionEnum;
import br.com.gps.mecanica.models.FuncionarioModel;
import br.com.gps.mecanica.models.OrdemServicoModel;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.models.ServicoModel;
import br.com.gps.mecanica.services.FuncionarioService;
import br.com.gps.mecanica.services.OrdemServicoService;
import br.com.gps.mecanica.services.ProdutoService;
import br.com.gps.mecanica.services.ServicoService;
import br.com.gps.mecanica.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ReadOrdemController {

    @FXML
    private TableView<ProdutoModel> produtosTable;

    @FXML
    private ComboBox<String> funcionarioComboBox;

    @FXML
    private TableColumn<ProdutoModel, String> nomeProdutosColumn;

    @FXML
    private TableColumn<ServicoModel, String> descricaoServicoColumn;

    @FXML
    private TableColumn<ProdutoModel, Double> valorVendaProdutosColumn;

    @FXML
    private Button atualizarButton;

    @FXML
    private TextField descricaoTextField;

    @FXML
    private Label nomeClienteLabel;

    @FXML
    private Label placaVeiculoLabel;

    @FXML
    private TableView<ServicoModel> servicosTable;

    @FXML
    private TableColumn<ProdutoModel, Integer> quantidadeProdutosColumn;

    @FXML
    private Button fecharButton;

    @FXML
    private TableColumn<ServicoModel, String> nomeServicoColumn;

    @FXML
    private Label erroLabel;

    @FXML
    private Button addServicoButton;

    @FXML
    private Button addProdutoButton;

    @FXML
    private Button removerServicoButton;

    @FXML
    private Button removerProdutoButton;

    @FXML
    private TableColumn<ProdutoModel, String> fornecedorProdutosColumn;

    @FXML
    private TableColumn<ServicoModel, Double> valorServicoColumn;

    @FXML
    private TableColumn<ProdutoModel, Double> valorCompraProdutosColumn;

    private OrdemServicoService ordemServicoService;

    private FuncionarioService funcionarioService;

    private ProdutoService produtoService;

    private ServicoService servicoService;

    private UUID ordemId;

    @FXML
    void initialize() {
        this.ordemServicoService = MecanicaFxMainApplication.getBean(OrdemServicoService.class);
        this.funcionarioService = MecanicaFxMainApplication.getBean(FuncionarioService.class);
        this.produtoService = MecanicaFxMainApplication.getBean(ProdutoService.class);
        this.servicoService = MecanicaFxMainApplication.getBean(ServicoService.class);

        nomeServicoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        descricaoServicoColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescricao()));
        valorServicoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValor()));

        servicosTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        servicosTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && servicosTable.getSelectionModel().getSelectedItem() != null) {
                ServicoModel servicoSelecionado = servicosTable.getSelectionModel().getSelectedItem();
                Utils.lerItem(MenuSelectionEnum.SERVICO, servicoSelecionado);
                atualizaTabela();
            }
        });

        addServicoButton.setOnAction(event -> abrirPopupAdicionarServicoProduto(true));

        removerServicoButton.setOnAction(event -> {
            try {
                ServicoModel servicoSelecionado = servicosTable.getSelectionModel().getSelectedItem();
                if (servicoSelecionado != null) {
                    ordemServicoService.removeServico(ordemId, servicoSelecionado);
                    servicosTable.getItems().remove(servicoSelecionado);
                    atualizaTabela();
                } else {
                    Utils.errorMessage(erroLabel, "Selecione um serviço para remover");
                }
            } catch (Exception e) {
                Utils.errorMessage(erroLabel, "Erro ao remover serviço: " + e.getMessage());
            }
        });

        nomeProdutosColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNome()));
        valorVendaProdutosColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValorVenda()));
        valorCompraProdutosColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValorCompra()));
        fornecedorProdutosColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
                cellData.getValue().getFornecedor() != null ? cellData.getValue().getFornecedor().getNome() : "N/A"));
        quantidadeProdutosColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantidade()));

        produtosTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        produtosTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && produtosTable.getSelectionModel().getSelectedItem() != null) {
                ProdutoModel produtoSelecionado = produtosTable.getSelectionModel().getSelectedItem();
                Utils.lerItem(MenuSelectionEnum.PRODUTO, produtoSelecionado);
                atualizaTabela();
            }
        });

        addProdutoButton.setOnAction(event -> abrirPopupAdicionarServicoProduto(false));

        removerProdutoButton.setOnAction(event -> {
            try {
                ProdutoModel produtoSelecionado = produtosTable.getSelectionModel().getSelectedItem();
                if (produtoSelecionado != null) {
                    ordemServicoService.removeProduto(ordemId, produtoSelecionado);
                    produtosTable.getItems().remove(produtoSelecionado);
                    atualizaTabela();
                } else {
                    Utils.errorMessage(erroLabel, "Selecione um produto para remover");
                }
            } catch (Exception e) {
                Utils.errorMessage(erroLabel, "Erro ao remover produto: " + e.getMessage());
            }
        });

    }

    private void atualizaTabela() {
        try {
            if (ordemId != null) {
                servicosTable.getItems().setAll(ordemServicoService.getServicos(ordemId));
                produtosTable.getItems().setAll(ordemServicoService.getProdutos(ordemId));
            }
        } catch (Exception e) {
            Utils.errorMessage(erroLabel, "Erro ao atualizar tabelas: " + e.getMessage());
        }
    }

    public void carregarOrdem(OrdemServicoModel ordemServico) {
        this.ordemId = ordemServico.getId();

        try {
            descricaoTextField.setText(ordemServico.getDescricao());

            if (ordemServico.getCliente() != null) {
                nomeClienteLabel.setText(ordemServico.getCliente().getNome());
            }

            if (ordemServico.getVeiculo() != null) {
                placaVeiculoLabel.setText(ordemServico.getVeiculo().getPlaca());
            }

            if (ordemServico.getFuncionario() != null) {
                funcionarioComboBox.setValue(ordemServico.getFuncionario().getNome());
            }

            if (ordemServico.getServicos() != null && !ordemServico.getServicos().isEmpty()) {
                servicosTable.getItems().setAll(ordemServico.getServicos());
            } else {
                servicosTable.getItems().clear();
            }

            if (ordemServico.getProdutos() != null && !ordemServico.getProdutos().isEmpty()) {
                produtosTable.getItems().setAll(ordemServico.getProdutos());
            } else {
                produtosTable.getItems().clear();
            }

        } catch (Exception e) {
            Utils.errorMessage(erroLabel, "Erro ao carregar ordem de serviço: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void atualizar(ActionEvent event) {
        try {
            OrdemServicoModel ordemServicoAtualizada = ordemServicoService.get(ordemId);

            ordemServicoAtualizada.setDescricao(descricaoTextField.getText());

            String nomeFuncionario = funcionarioComboBox.getValue();
            if (nomeFuncionario != null && !nomeFuncionario.isEmpty()) {
                FuncionarioModel funcionario = funcionarioService.getByNome(nomeFuncionario).get(0);
                ordemServicoAtualizada.setFuncionario(funcionario);
            } else {
                throw new Exception("Funcionário não selecionado.");
            }

            ordemServicoAtualizada.setServicos(servicosTable.getItems());
            ordemServicoAtualizada.setProdutos(produtosTable.getItems());

            ordemServicoService.update(ordemId, ordemServicoAtualizada);

            nomeProdutosColumn.getTableView().getScene().getWindow().hide();
        } catch (Exception e) {
            Utils.errorMessage(erroLabel, "Erro ao atualizar ordem de serviço: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void fechar(ActionEvent event) {
        nomeServicoColumn.getTableView().getScene().getWindow().hide();
    }

    private void abrirPopupAdicionarServicoProduto(boolean isServico) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setGraphic(null);
        alert.setTitle("Adicionar " + (isServico ? "Serviço" : "Produto"));
        alert.setHeaderText("Selecione um " + (isServico ? "serviço" : "produto") + " para adicionar:");

        ComboBox<String> comboBox = new ComboBox<>();

        if (isServico) {
            List<ServicoModel> servicosDisponiveis = servicoService.get();
            for (ServicoModel servico : servicosDisponiveis) {
                comboBox.getItems().add(servico.getNome());
            }
        } else {
            List<ProdutoModel> produtosDisponiveis = produtoService.get();
            for (ProdutoModel produto : produtosDisponiveis) {
                comboBox.getItems().add(produto.getNome());
            }
        }

        comboBox.setPromptText("Selecione uma opção");

        VBox content = new VBox(10);
        content.getChildren().add(comboBox);

        alert.getDialogPane().setContent(content);

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && comboBox.getValue() != null) {
                try {
                    if (isServico) {
                        ServicoModel servicoSelecionado = servicoService.getByNome(comboBox.getValue()).get(0);
                        ordemServicoService.addServico(ordemId, servicoSelecionado);
                        servicosTable.getItems().add(servicoSelecionado);
                    } else {
                        ProdutoModel produtoSelecionado = produtoService.getByNome(comboBox.getValue()).get(0);
                        ordemServicoService.addProduto(ordemId, produtoSelecionado);
                        produtosTable.getItems().add(produtoSelecionado);
                    }
                    servicosTable.refresh();
                    produtosTable.refresh();
                } catch (Exception e) {
                    Utils.errorMessage(erroLabel, "Erro ao adicionar: " + e.getMessage());
                }
            }
        });
    }

}
