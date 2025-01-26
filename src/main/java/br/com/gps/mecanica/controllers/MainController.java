package br.com.gps.mecanica.controllers;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

import br.com.gps.mecanica.MecanicaApplication;
import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.data.DadosTeste;
import br.com.gps.mecanica.enums.ContatoEnum;
import br.com.gps.mecanica.enums.CorEnum;
import br.com.gps.mecanica.enums.MenuSelectionEnum;
import br.com.gps.mecanica.enums.PessoaEnum;
import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.FuncionarioModel;
import br.com.gps.mecanica.models.OrdemServicoModel;
import br.com.gps.mecanica.models.PessoaBaseModel;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.models.ServicoModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.services.ClienteService;
import br.com.gps.mecanica.services.FornecedorService;
import br.com.gps.mecanica.services.FuncionarioService;
import br.com.gps.mecanica.services.OrdemServicoService;
import br.com.gps.mecanica.services.ProdutoService;
import br.com.gps.mecanica.services.ServicoService;
import br.com.gps.mecanica.services.VeiculoService;
import br.com.gps.mecanica.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController {

    @FXML
    private VBox mainVBox;

    @FXML
    private Button fornecedoresButton;

    @FXML
    private Button funcionariosButton;

    @FXML
    private Button servicosButton;

    @FXML
    private Button notasButton;

    @FXML
    private Button orcamentoButton;

    @FXML
    private Button clientesButton;

    @FXML
    private Button veiculosButton;

    @FXML
    private Button ordensButton;

    @FXML
    private Button produtosButton;

    private MenuSelectionEnum selection;

    private Object selected;

    private VeiculoService veiculoService;

    private ClienteService clienteService;

    private ProdutoService produtoService;

    private ServicoService servicoService;

    private FornecedorService fornecedorService;

    private FuncionarioService funcionarioService;

    private OrdemServicoService ordemServicoService;

    @FXML
    void initialize() {
        this.veiculoService = MecanicaFxMainApplication.getBean(VeiculoService.class);
        this.clienteService = MecanicaFxMainApplication.getBean(ClienteService.class);
        this.produtoService = MecanicaFxMainApplication.getBean(ProdutoService.class);
        this.servicoService = MecanicaFxMainApplication.getBean(ServicoService.class);
        this.fornecedorService = MecanicaFxMainApplication.getBean(FornecedorService.class);
        this.funcionarioService = MecanicaFxMainApplication.getBean(FuncionarioService.class);
        this.ordemServicoService = MecanicaFxMainApplication.getBean(OrdemServicoService.class);
    }

    @FXML
    void mostraClientes(ActionEvent event) {
        if (selection == MenuSelectionEnum.CLIENTE) {
            return;
        }

        createTestData();

        selection = MenuSelectionEnum.CLIENTE;

        criaTabela(List.of(PessoaBaseModel.class, ClienteModel.class), clienteService,
                List.of("id", "ID", "tipoPessoa"));
    }

    @FXML
    void mostraVeiculos(ActionEvent event) {
        if (selection == MenuSelectionEnum.VEICULO) {
            return;
        }

        selection = MenuSelectionEnum.VEICULO;

        criaTabela(List.of(VeiculoModel.class), veiculoService, List.of("id", "ID"));
    }

    @FXML
    void mostraProdutos(ActionEvent event) {
        if (selection == MenuSelectionEnum.PRODUTO) {
            return;
        }

        selection = MenuSelectionEnum.PRODUTO;

        criaTabela(List.of(ProdutoModel.class), produtoService, List.of("id", "ID"));
    }

    @FXML
    void mostraServicos(ActionEvent event) {
        if (selection == MenuSelectionEnum.SERVICO) {
            return;
        }

        selection = MenuSelectionEnum.SERVICO;

        criaTabela(List.of(ServicoModel.class), servicoService, List.of("id", "ID"));
    }

    @FXML
    void mostraFornecedores(ActionEvent event) {
        if (selection == MenuSelectionEnum.FORNECEDOR) {
            return;
        }

        selection = MenuSelectionEnum.FORNECEDOR;

        criaTabela(List.of(PessoaBaseModel.class, FornecedorModel.class), fornecedorService,
                List.of("id", "ID", "tipoPessoa"));
    }

    @FXML
    void mostraFuncionarios(ActionEvent event) {
        if (selection == MenuSelectionEnum.FUNCIONARIO) {
            return;
        }

        selection = MenuSelectionEnum.FUNCIONARIO;

        criaTabela(List.of(PessoaBaseModel.class, FuncionarioModel.class), funcionarioService,
                List.of("id", "ID", "tipoPessoa"));
    }

    @FXML
    void mostraOrdem(ActionEvent event) {
        if (selection == MenuSelectionEnum.ORDEM) {
            return;
        }

        selection = MenuSelectionEnum.ORDEM;

        criaTabela(List.of(OrdemServicoModel.class), ordemServicoService, List.of("id", "ID", "contratada"));
    }

    @FXML
    void mostraOrcamentos(ActionEvent event) {
        if (selection == MenuSelectionEnum.ORCAMENTO) {
            return;
        }

        selection = MenuSelectionEnum.ORCAMENTO;

        criaTabela(List.of(OrdemServicoModel.class), ordemServicoService, List.of("id", "ID", "contratada"));
    }

    private void criaTabela(List<Class<?>> models, Object service, List<String> camposIgnorados) {

        mainVBox.getChildren().clear();
        selected = null;

        TableView<Object> mainTable = new TableView<>();
        mainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        if (camposIgnorados == null)
            camposIgnorados = List.of();

        for (Class<?> modelClass : models) {
            for (Field field : modelClass.getDeclaredFields()) {

                Boolean skip = false;
                for (String campo : camposIgnorados) {
                    if (field.getName().contains(campo)) {
                        skip = true;
                        break;
                    }
                }
                if (skip)
                    continue;

                TableColumn<Object, Object> column = new TableColumn<>(field.getName().toUpperCase());
                field.setAccessible(true);

                column.setCellValueFactory(data -> {
                    try {
                        Object value = field.get(data.getValue());
                        if (value == null) {
                            return new SimpleObjectProperty<>("N/A");
                        } else if (value instanceof ClienteModel) {
                            return new SimpleObjectProperty<>(((ClienteModel) value).getNome());
                        } else if (value instanceof List) {

                            List<?> obj = ((List<?>) value);

                            if (obj.isEmpty())
                                return new SimpleObjectProperty<>("N/A");

                            String str = "";

                            if (obj.get(0) instanceof EnderecoModel) {
                                for (Object o : obj)
                                    str += ((EnderecoModel) o).getRua() + "\n";
                            } else if (obj.get(0) instanceof TelefoneModel) {
                                for (Object o : obj)
                                    str += ((TelefoneModel) o).getNumero() + "\n";
                            } else if (obj.get(0) instanceof VeiculoModel) {
                                for (Object o : obj)
                                    str += ((VeiculoModel) o).getPlaca() + "\n";
                            } else if (obj.get(0) instanceof ProdutoModel) {
                                for (Object o : obj)
                                    str += ((ProdutoModel) o).getNome() + "\n";
                            } else if (obj.get(0) instanceof ServicoModel) {
                                for (Object o : obj)
                                    str += ((ServicoModel) o).getNome() + "\n";
                            }

                            return new SimpleObjectProperty<>(str);
                        } else if (value instanceof FornecedorModel) {
                            return new SimpleObjectProperty<>(((FornecedorModel) value).getNome());
                        } else if (value instanceof VeiculoModel) {
                            return new SimpleObjectProperty<>(((VeiculoModel) value).getPlaca());
                        } else if (value instanceof ProdutoModel) {
                            return new SimpleObjectProperty<>(((ProdutoModel) value).getNome());
                        } else if (value instanceof ServicoModel) {
                            return new SimpleObjectProperty<>(((ServicoModel) value).getNome());
                        } else if (value instanceof FuncionarioModel) {
                            return new SimpleObjectProperty<>(((FuncionarioModel) value).getNome());
                        } else if (value instanceof OrdemServicoModel) {
                            return new SimpleObjectProperty<>(((OrdemServicoModel) value).getId());
                        } else if (value instanceof LocalDate) {
                            return new SimpleObjectProperty<>(((LocalDate) value).getDayOfMonth() + "/"
                                    + ((LocalDate) value).getMonthValue() + "/" + ((LocalDate) value).getYear());
                        }

                        return new SimpleObjectProperty<>(value.toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                });

                mainTable.getColumns().add(column);
            }
        }

        if (service instanceof ClienteService) {
            clienteService.get().forEach(cliente -> {
                mainTable.getItems().add(cliente);
            });
        } else if (service instanceof VeiculoService) {
            veiculoService.get().forEach(veiculo -> {
                mainTable.getItems().add(veiculo);
            });
        } else if (service instanceof ProdutoService) {
            produtoService.get().forEach(produto -> {
                mainTable.getItems().add(produto);
            });
        } else if (service instanceof ServicoService) {
            servicoService.get().forEach(servico -> {
                mainTable.getItems().add(servico);
            });
        } else if (service instanceof FornecedorService) {
            fornecedorService.get().forEach(fornecedor -> {
                mainTable.getItems().add(fornecedor);
            });
        } else if (service instanceof FuncionarioService) {
            funcionarioService.get().forEach(funcionario -> {
                mainTable.getItems().add(funcionario);
            });
        } else if (service instanceof OrdemServicoService) {
            ordemServicoService.get().forEach(ordemServico -> {
                if (selection == MenuSelectionEnum.ORDEM && ordemServico.getContratada()) {
                    mainTable.getItems().add(ordemServico);
                } else if (selection == MenuSelectionEnum.ORCAMENTO && !ordemServico.getContratada()) {
                    mainTable.getItems().add(ordemServico);
                }
            });
        }

        mainTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selected = newValue;
            }
        });

        mainTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && selected != null) {
                if (selected instanceof ClienteModel) {
                    lerCliente((ClienteModel) selected);
                } else if (selected instanceof VeiculoModel) {
                    Utils.lerVeiculo((VeiculoModel) selected);
                    atualizaTabela();
                } else if (selected instanceof ProdutoModel) {
                    // lerProduto((ProdutoModel) selected);
                } else if (selected instanceof ServicoModel) {
                    // lerServico((ServicoModel) selected);
                } else if (selected instanceof FornecedorModel) {
                    // lerFornecedor((FornecedorModel) selected);
                } else if (selected instanceof FuncionarioModel) {
                    // lerFuncionario((FuncionarioModel) selected);
                } else if (selected instanceof OrdemServicoModel) {
                    // lerOrdemServico((OrdemServicoModel) selected);
                }
            }
        });

        BorderPane buttonBorderPane = new BorderPane();
        buttonBorderPane.prefWidth(Region.USE_COMPUTED_SIZE);
        buttonBorderPane.prefHeight(Region.USE_COMPUTED_SIZE);
        buttonBorderPane.paddingProperty().setValue(new javafx.geometry.Insets(8, 8, 8, 8));

        Button botaoDelete = criarBotaoDelete(mainTable, service);
        Button botaoAdd = criarBotaoAdd(mainTable, service);

        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().add(botaoAdd);
        buttonHBox.getChildren().add(botaoDelete);
        buttonHBox.setSpacing(10);

        mainVBox.getChildren().add(mainTable);
        VBox.setVgrow(mainTable, Priority.ALWAYS);
        buttonBorderPane.setRight(buttonHBox);
        mainVBox.getChildren().add(buttonBorderPane);
    }

    private void lerCliente(ClienteModel cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(MecanicaApplication.class.getResource("readCliente.fxml"));
            Parent root = loader.load();

            ReadClienteController controller = loader.getController();
            controller.carregarCliente(cliente);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setTitle("Detalhes do Cliente");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            atualizaTabela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void atualizaTabela() {
        if (selection == MenuSelectionEnum.CLIENTE) {
            criaTabela(List.of(PessoaBaseModel.class, ClienteModel.class), clienteService,
                    List.of("id", "ID", "tipoPessoa"));
        } else if (selection == MenuSelectionEnum.VEICULO) {
            criaTabela(List.of(VeiculoModel.class), veiculoService, List.of("id", "ID"));
        } else if (selection == MenuSelectionEnum.PRODUTO) {
            criaTabela(List.of(ProdutoModel.class), produtoService, List.of("id", "ID"));
        } else if (selection == MenuSelectionEnum.SERVICO) {
            criaTabela(List.of(ServicoModel.class), servicoService, List.of("id", "ID"));
        } else if (selection == MenuSelectionEnum.FORNECEDOR) {
            criaTabela(List.of(PessoaBaseModel.class, FornecedorModel.class), fornecedorService,
                    List.of("id", "ID", "tipoPessoa"));
        } else if (selection == MenuSelectionEnum.FUNCIONARIO) {
            criaTabela(List.of(PessoaBaseModel.class, FuncionarioModel.class), funcionarioService,
                    List.of("id", "ID", "tipoPessoa"));
        } else if (selection == MenuSelectionEnum.ORDEM) {
            criaTabela(List.of(OrdemServicoModel.class), ordemServicoService, List.of("id", "ID", "contratada"));
        } else if (selection == MenuSelectionEnum.ORCAMENTO) {
            criaTabela(List.of(OrdemServicoModel.class), ordemServicoService, List.of("id", "ID", "contratada"));
        }
    }

    private Button criarBotaoAdd(TableView<Object> tabela, Object service) {
        Button addButton = new Button("Adicionar");
        addButton.setStyle(
                "-fx-background-color:rgb(52, 219, 52); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        try {
            FXMLLoader loader;
            if (selection == MenuSelectionEnum.VEICULO)
                loader = new FXMLLoader(MecanicaApplication.class.getResource("addVeiculo.fxml"));
            else if (selection == MenuSelectionEnum.CLIENTE)
                loader = new FXMLLoader(MecanicaApplication.class.getResource("addCliente.fxml"));
            else if (selection == MenuSelectionEnum.PRODUTO)
                loader = new FXMLLoader(MecanicaApplication.class.getResource("addProduto.fxml"));
            else if (selection == MenuSelectionEnum.SERVICO)
                loader = new FXMLLoader(MecanicaApplication.class.getResource("addServico.fxml"));
            else if (selection == MenuSelectionEnum.FORNECEDOR)
                loader = new FXMLLoader(MecanicaApplication.class.getResource("addFornecedor.fxml"));
            else
                return addButton;

            addButton.setOnAction(event -> {
                Parent root;
                try {
                    root = loader.load();
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setResizable(false);
                    stage.setTitle("Adicionar");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                    atualizaTabela();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return addButton;
    }

    private Button criarBotaoDelete(TableView<Object> tabela, Object service) {
        Button deleteButton = new Button("Deletar");
        deleteButton.setStyle(
                "-fx-background-color:rgb(219, 52, 52); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        deleteButton.setOnAction(event -> {
            if (selected != null) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete");
                alert.setHeaderText("Você tem certeza que deseja deletar este item?");

                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setDefaultButton(false);
                ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        deleteAction(service, selected);
                        tabela.getItems().remove(selected);
                    }
                });
            }
        });

        return deleteButton;
    }

    private void deleteAction(Object service, Object obj) {
        try {
            if (service instanceof ClienteService) {
                clienteService.delete(((ClienteModel) obj).getId());
            } else if (service instanceof VeiculoService) {
                veiculoService.delete(((VeiculoModel) obj).getId());
            } else if (service instanceof ProdutoService) {
                produtoService.delete(((ProdutoModel) obj).getId());
            } else if (service instanceof ServicoService) {
                servicoService.delete(((ServicoModel) obj).getId());
            } else if (service instanceof FornecedorService) {
                fornecedorService.delete(((FornecedorModel) obj).getId());
            } else if (service instanceof FuncionarioService) {
                funcionarioService.delete(((FuncionarioModel) obj).getId());
            } else if (service instanceof OrdemServicoService) {
                ordemServicoService.delete(((OrdemServicoModel) obj).getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private void createTestClient() {

        if (!clienteService.get().isEmpty()) {
            return;
        }

        ClienteModel cliente = new ClienteModel(PessoaEnum.FISICA, "João", "10451721977", "joaoalt0502@gmail.com", null,
                null, null);

        VeiculoModel veiculo1 = new VeiculoModel("ABC1234", "Uno", "Fiat", 2010, CorEnum.AMARELO, cliente);

        VeiculoModel veiculo2 = new VeiculoModel("DEF5678", "Gol", "Volkswagen", 2015, CorEnum.AZUL, cliente);

        EnderecoModel endereco = new EnderecoModel("07083450", "Rua 123", "Bairro 1", "Cidade 1", "SP", "123",
                "complemento", "referencia", ContatoEnum.COMERCIAL, cliente);

        EnderecoModel endereco2 = new EnderecoModel("07083450", "Rua 321", "Bairro 2", "Cidade 2", "PR", "321",
                "complemento", "referencia", ContatoEnum.COMERCIAL, cliente);

        TelefoneModel telefone = new TelefoneModel("4498337046", ContatoEnum.RESIDENCIAL, cliente);

        TelefoneModel telefone2 = new TelefoneModel("4498421654", ContatoEnum.RESIDENCIAL, cliente);

        cliente.setEnderecos(List.of(endereco, endereco2));
        cliente.setVeiculos(List.of(veiculo1, veiculo2));
        cliente.setTelefones(List.of(telefone, telefone2));

        try {
            clienteService.create(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private void createTestData() {

        if (!clienteService.get().isEmpty()) {
            return;
        }

        for (ClienteModel cliente : DadosTeste.getClientes()) {
            try {
                clienteService.create(cliente);
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        for (VeiculoModel veiculo : DadosTeste.getVeiculos()) {
            try {
                veiculoService.create(veiculo);
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        for (ProdutoModel produto : DadosTeste.getProdutos()) {
            try {
                produtoService.create(produto);
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        for (ServicoModel servico : DadosTeste.getServicos()) {
            try {
                servicoService.create(servico);
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        for (FornecedorModel fornecedor : DadosTeste.getFornecedores()) {
            try {
                fornecedorService.create(fornecedor);
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        for (FuncionarioModel funcionario : DadosTeste.getFuncionarios()) {
            try {
                funcionarioService.create(funcionario);
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        for (OrdemServicoModel ordemServico : DadosTeste.getOrdensServico()) {
            try {
                ordemServicoService.create(ordemServico);
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
}
