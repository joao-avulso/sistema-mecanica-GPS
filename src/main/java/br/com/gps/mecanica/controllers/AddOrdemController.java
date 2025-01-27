package br.com.gps.mecanica.controllers;

import java.util.ArrayList;
import java.util.List;

import br.com.gps.mecanica.MecanicaFxMainApplication;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddOrdemController {
    @FXML
    private ComboBox<String> funcionarioComboBox;

    @FXML
    private Button addProdutoButton;

    @FXML
    private VBox produtosVBox;

    @FXML
    private Button addButton;

    @FXML
    private Button delServicoButton;

    @FXML
    private Button delProdutoButton;

    @FXML
    private TextField descricaoTextField;

    @FXML
    private ComboBox<String> veiculoComboBox;

    @FXML
    private ComboBox<String> clienteComboBox;

    @FXML
    private Button addServicoButton;

    @FXML
    private Button cancelarButton;

    @FXML
    private Label erroLabel;

    @FXML
    private VBox servicosVBox;

    @FXML
    private ComboBox<String> servicoComboBox;

    @FXML
    private ComboBox<String> produtoComboBox;

    OrdemServicoService ordemServicoService;

    ClienteService clienteService;

    VeiculoService veiculoService;

    FuncionarioService funcionarioService;

    ProdutoService produtoService;

    ServicoService servicoService;

    private Boolean isOrcamento = false;

    @FXML
    void initialize() {
        this.ordemServicoService = MecanicaFxMainApplication.getBean(OrdemServicoService.class);
        this.clienteService = MecanicaFxMainApplication.getBean(ClienteService.class);
        this.veiculoService = MecanicaFxMainApplication.getBean(VeiculoService.class);
        this.funcionarioService = MecanicaFxMainApplication.getBean(FuncionarioService.class);
        this.produtoService = MecanicaFxMainApplication.getBean(ProdutoService.class);
        this.servicoService = MecanicaFxMainApplication.getBean(ServicoService.class);

        ObservableList<String> clientes = FXCollections.observableArrayList(clienteService.get().stream().map(cliente -> cliente.getCpf()).toList());
        ObservableList<String> veiculos = FXCollections.observableArrayList(veiculoService.get().stream().map(veiculo -> veiculo.getPlaca()).toList());
        ObservableList<String> funcionarios = FXCollections.observableArrayList(funcionarioService.get().stream().map(funcionario -> funcionario.getNome()).toList());
        ObservableList<String> produtos = FXCollections.observableArrayList(produtoService.get().stream().map(produto -> produto.getNome()).toList());
        ObservableList<String> servicos = FXCollections.observableArrayList(servicoService.get().stream().map(servico -> servico.getNome()).toList());

        clienteComboBox.getItems().addAll(clientes);
        veiculoComboBox.getItems().addAll(veiculos);
        funcionarioComboBox.getItems().addAll(funcionarios);
        produtoComboBox.getItems().addAll(produtos);
        servicoComboBox.getItems().addAll(servicos);

        // Adiciona um listener para buscar clientes semelhantes ao texto digitado
        clienteComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                ObservableList<String> filteredClientes = clientes.filtered(cliente -> cliente.contains(newValue));
                clienteComboBox.setItems(filteredClientes);

                if (!filteredClientes.isEmpty()) {
                    clienteComboBox.show();
                }
            } else {
                clienteComboBox.setItems(clientes);
                clienteComboBox.hide();
            }
        });

        clienteComboBox.setOnAction(event -> {
            try {
                String cpf = clienteComboBox.getValue();
                if (cpf == null || cpf.isEmpty()) return;
                ClienteModel clienteModel;
                clienteModel = clienteService.get(cpf);
                ObservableList<String> filteredVeiculos = veiculos.filtered(veiculo -> veiculoService.getByCliente(clienteModel).stream().map(v -> v.getPlaca()).toList().contains(veiculo));
                if (filteredVeiculos.isEmpty()) return;
                veiculoComboBox.setValue(filteredVeiculos.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Adiciona um listener para buscar funcionarios semelhantes ao texto digitado
        funcionarioComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                ObservableList<String> filteredFuncionarios = funcionarios.filtered(funcionario -> funcionario.contains(newValue));
                funcionarioComboBox.setItems(filteredFuncionarios);

                // Abre o menu do ComboBox automaticamente
                if (!filteredFuncionarios.isEmpty()) {
                    funcionarioComboBox.show();
                }
            } else {
                funcionarioComboBox.setItems(funcionarios);
            }
        });

    }

    @FXML
    void addServico(ActionEvent event) {
        ComboBox<String> servico = new ComboBox<String>(servicoComboBox.getItems());
        servico.setEditable(true);
        servico.setPrefWidth(450);
        servico.setPromptText("Digite o nome do serviço");
        servicosVBox.getChildren().add(servico);
        Stage stage = (Stage) servicoComboBox.getScene().getWindow();
        stage.sizeToScene();
    }

    @FXML
    void addProduto(ActionEvent event) {
        ComboBox<String> produto = new ComboBox<String>(produtoComboBox.getItems());
        produto.setEditable(true);
        produto.setPrefWidth(450);
        produto.setPromptText("Digite o nome do produto");
        produtosVBox.getChildren().add(produto);
        Stage stage = (Stage) produtoComboBox.getScene().getWindow();
        stage.sizeToScene();
    }

    @FXML
    void delServico(ActionEvent event) {
        if (servicosVBox.getChildren().size() > 1) {
            servicosVBox.getChildren().remove(servicosVBox.getChildren().size() - 1);
            servicosVBox.getScene().getWindow().sizeToScene();
        }
    }

    @FXML
    void delProduto(ActionEvent event) {
        if (produtosVBox.getChildren().size() > 1) {
            produtosVBox.getChildren().remove(produtosVBox.getChildren().size() - 1);
            produtosVBox.getScene().getWindow().sizeToScene();
        }
    }

    @FXML
    void adicionar(ActionEvent event) {
        
        if (clienteComboBox.getValue() == null || veiculoComboBox.getValue() == null  || funcionarioComboBox.getValue() == null  || servicoComboBox.getValue() == null ) {
            Utils.errorMessage(erroLabel, "Preencha os campos obrigatórios");
            return;
        }
        
        try {
            String clienteCpf = clienteComboBox.getValue();
            String veiculoPlaca = veiculoComboBox.getValue();
            String funcionario = funcionarioComboBox.getValue();
            String descricao = descricaoTextField.getText();
            
            List<ServicoModel> servicos = new ArrayList<ServicoModel>();

            for (Node servico : servicosVBox.getChildren()) {
                @SuppressWarnings("unchecked")
                String servicoNome = ((ComboBox<String>) servico).getValue();
                if (servicoNome == null) continue;
                ServicoModel servicoModel = servicoService.getByNome(servicoNome).get(0);
                servicos.add(servicoModel);
            }

            List<ProdutoModel> produtos = new ArrayList<ProdutoModel>();

            for (Node produto : produtosVBox.getChildren()) {
                @SuppressWarnings("unchecked")
                String produtoNome = ((ComboBox<String>) produto).getValue();
                if (produtoNome == null) continue;
                ProdutoModel produtoModel = produtoService.getByNome(produtoNome).get(0);
                produtos.add(produtoModel);
            }

            ClienteModel clienteModel = clienteService.get(clienteCpf);
            VeiculoModel veiculoModel = veiculoService.getByPlaca(veiculoPlaca);
            FuncionarioModel funcionarioModel = funcionarioService.getByNome(funcionario).get(0);

            if (servicos.isEmpty()) {
                Utils.errorMessage(erroLabel, "Adicione ao menos um serviço");
                return;
            }

            OrdemServicoModel ordem = new OrdemServicoModel(clienteModel, veiculoModel, servicos, produtos, funcionarioModel, descricao);

            if (!isOrcamento) ordem.setContratada();

            ordemServicoService.create(ordem);
            clienteComboBox.getScene().getWindow().hide();
        } catch (Exception e) {
            Utils.errorMessage(erroLabel, e.getMessage());
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        clienteComboBox.getScene().getWindow().hide();
    }

    public void setIsOrcamento(Boolean isOrcamento) {
        this.isOrcamento = isOrcamento;
    }
}
