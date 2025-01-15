package br.com.gps.mecanica.controllers;

import java.lang.reflect.Field;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.enums.MenuSelection;
import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.repositories.VeiculoRepository;
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

    private MenuSelection selection;
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

    @FXML
    void mostraVeiculos(ActionEvent event) {
        if (selection == MenuSelection.VEICULO) {
            return;
        }

        selection = MenuSelection.VEICULO;

        VeiculoService veiculoService = new VeiculoService(MecanicaFxMainApplication.getBean(VeiculoRepository.class));

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
                        return new SimpleObjectProperty<>(((ClienteModel)value).getNome());
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
                System.out.println("Linha selecionada: " + ((VeiculoModel)newValue));
                selected = ((VeiculoModel)newValue);
            }
        });

        BorderPane buttonBorderPane = new BorderPane();
        buttonBorderPane.prefWidth(Region.USE_COMPUTED_SIZE);
        buttonBorderPane.prefHeight(Region.USE_COMPUTED_SIZE);
        buttonBorderPane.paddingProperty().setValue(new javafx.geometry.Insets(8, 8, 8, 8));

        Button deleteButton = new Button("Deletar");
        deleteButton.setStyle("-fx-background-color:rgb(219, 52, 52); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        deleteButton.setOnAction(e -> {
            if (selected != null) {
                veiculoService.delete(((VeiculoModel)selected).getId());
                mainTable.getItems().remove(selected);
            }
        });

        mainVBox.getChildren().add(mainTable);
        VBox.setVgrow(mainTable, Priority.ALWAYS);
        buttonBorderPane.setRight(deleteButton);
        mainVBox.getChildren().add(buttonBorderPane);
    }
}

