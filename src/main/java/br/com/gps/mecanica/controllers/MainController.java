package br.com.gps.mecanica.controllers;

import java.lang.reflect.Field;
import java.util.List;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.enums.ContatoEnum;
import br.com.gps.mecanica.enums.CorEnum;
import br.com.gps.mecanica.enums.MenuSelectionEnum;
import br.com.gps.mecanica.enums.PessoaEnum;
import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.repositories.ClienteRepository;
import br.com.gps.mecanica.repositories.VeiculoRepository;
import br.com.gps.mecanica.services.ClienteService;
import br.com.gps.mecanica.services.VeiculoService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MainController {

    private MenuSelectionEnum selection;
    
    private Object selected;

    @FXML
    private VBox mainVBox;

    @FXML
    private Button fornecedoresButton;

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

    private VeiculoService veiculoService = new VeiculoService(MecanicaFxMainApplication.getBean(VeiculoRepository.class));

    private ClienteService clienteService = new ClienteService(MecanicaFxMainApplication.getBean(ClienteRepository.class));

    @FXML
    void mostraVeiculos(ActionEvent event) {
        if (selection == MenuSelectionEnum.VEICULO) {
            return;
        }

        selection = MenuSelectionEnum.VEICULO;

        //createTestClient();

        mainVBox.getChildren().clear();
        TableView<VeiculoModel> mainTable = new TableView<VeiculoModel>();
        mainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        for (Field field : VeiculoModel.class.getDeclaredFields()) {
            if (field.getName().contains("id") || field.getName().contains("ID")) {
                continue;
            }

            TableColumn<VeiculoModel, Object> column = new TableColumn<>(field.getName().toUpperCase());
            field.setAccessible(true);

            column.setCellValueFactory(data -> {
                try {
                    Object value = field.get(data.getValue());
                    if (value == null) {
                        return new SimpleObjectProperty<>("N/A");
                    } else if (value instanceof ClienteModel) {
                        return new SimpleObjectProperty<>(((ClienteModel) value).getNome());
                    }
                    return new SimpleObjectProperty<>(value.toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            });

            mainTable.getColumns().add(column);
        }

        veiculoService.get().forEach(veiculo -> {
            mainTable.getItems().add(veiculo);
        });

        mainTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // System.out.println("Linha selecionada: " + ((VeiculoModel)newValue));
                selected = ((VeiculoModel)newValue);
            }
        });

        BorderPane buttonBorderPane = new BorderPane();
        buttonBorderPane.prefWidth(Region.USE_COMPUTED_SIZE);
        buttonBorderPane.prefHeight(Region.USE_COMPUTED_SIZE);
        buttonBorderPane.paddingProperty().setValue(new javafx.geometry.Insets(8, 8, 8, 8));

        Button deleteButton = new Button("Deletar");
        deleteButton.setStyle(
                "-fx-background-color:rgb(219, 52, 52); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        deleteButton.setOnAction(e -> {
            if (selected != null) {
                try {
                    veiculoService.delete(((VeiculoModel) selected).getId());
                    mainTable.getItems().remove(selected);
                } catch (Exception ex) {
                }
            }
        });

        mainVBox.getChildren().add(mainTable);
        VBox.setVgrow(mainTable, Priority.ALWAYS);
        buttonBorderPane.setRight(deleteButton);
        mainVBox.getChildren().add(buttonBorderPane);
    }

    private void createTestClient() {
        ClienteModel cliente = new ClienteModel(PessoaEnum.FISICA, "João", "10451721977", "joaoalt0502@gmail.com", null, null, null);

        VeiculoModel veiculo1 = new VeiculoModel("ABC1234", "Uno", "Fiat", 2010, CorEnum.AMARELO, cliente);

        EnderecoModel endereco = new EnderecoModel("07083450", "Rua 123", "Bairro 1", "Cidade 1", "SP", "123", "complemento", "referencia", ContatoEnum.COMERCIAL, cliente);

        cliente.setEnderecos(List.of(endereco));
        cliente.setVeiculos(List.of(veiculo1));

        try {
            clienteService.create(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
