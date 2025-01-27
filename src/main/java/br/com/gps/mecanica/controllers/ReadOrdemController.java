package br.com.gps.mecanica.controllers;

import java.util.UUID;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.enums.MenuSelectionEnum;
import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.FuncionarioModel;
import br.com.gps.mecanica.models.OrdemServicoModel;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.models.ServicoModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.services.ClienteService;
import br.com.gps.mecanica.services.FuncionarioService;
import br.com.gps.mecanica.services.OrdemServicoService;
import br.com.gps.mecanica.services.ProdutoService;
import br.com.gps.mecanica.services.ServicoService;
import br.com.gps.mecanica.services.VeiculoService;
import br.com.gps.mecanica.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private ComboBox<String> veiculoComboBox;

    @FXML
    private ComboBox<String> clienteComboBox;

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
    private TableColumn<ProdutoModel, String> fornecedorProdutosColumn;

    @FXML
    private TableColumn<ServicoModel, Double> valorServicoColumn;

    @FXML
    private TableColumn<ProdutoModel, Double> valorCompraProdutosColumn;

    private OrdemServicoService ordemServicoService;

    private ClienteService clienteService;

    private VeiculoService veiculoService;

    private FuncionarioService funcionarioService;

    private ProdutoService produtoService;

    private ServicoService servicoService;

    private UUID ordemId;

    @FXML
    void initialize() {
        // Inicialização dos serviços
        this.ordemServicoService = MecanicaFxMainApplication.getBean(OrdemServicoService.class);
        this.clienteService = MecanicaFxMainApplication.getBean(ClienteService.class);
        this.veiculoService = MecanicaFxMainApplication.getBean(VeiculoService.class);
        this.funcionarioService = MecanicaFxMainApplication.getBean(FuncionarioService.class);
        this.produtoService = MecanicaFxMainApplication.getBean(ProdutoService.class);
        this.servicoService = MecanicaFxMainApplication.getBean(ServicoService.class);

        // Configuração das colunas da tabela de serviços
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

        // Configuração das colunas da tabela de produtos
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

        // Desabilitando os ComboBox e TextField para exibição apenas
        clienteComboBox.setDisable(true);
        veiculoComboBox.setDisable(true);
        funcionarioComboBox.setDisable(true);
        descricaoTextField.setDisable(true);
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
                clienteComboBox.setValue(ordemServico.getCliente().getCpf());
            }

            if (ordemServico.getVeiculo() != null) {
                veiculoComboBox.setValue(ordemServico.getVeiculo().getPlaca());
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

            String cpfCliente = clienteComboBox.getValue();
            if (cpfCliente != null && !cpfCliente.isEmpty()) {
                ClienteModel cliente = clienteService.get(cpfCliente);
                ordemServicoAtualizada.setCliente(cliente);
            } else {
                throw new Exception("Cliente não selecionado.");
            }

            String placaVeiculo = veiculoComboBox.getValue();
            if (placaVeiculo != null && !placaVeiculo.isEmpty()) {
                VeiculoModel veiculo = veiculoService.getByPlaca(placaVeiculo);
                ordemServicoAtualizada.setVeiculo(veiculo);
            } else {
                throw new Exception("Veículo não selecionado.");
            }

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

}
