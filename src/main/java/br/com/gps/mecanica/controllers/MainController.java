package br.com.gps.mecanica.controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.gps.mecanica.enums.Cor;
import br.com.gps.mecanica.models.VeiculoModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class MainController {

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
        System.out.println("Veiculos");

        TableView<VeiculoModel> mainTable = new TableView<VeiculoModel>();

        mainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<VeiculoModel> veiculos = new ArrayList<>();

        veiculos.add(new VeiculoModel(new UUID(0, 0), "ABC-1234", "Gol", "Volkswagen", 2010, Cor.AZUL));
        veiculos.add(new VeiculoModel(new UUID(0, 0), "DEF-5678", "Uno", "Fiat", 2015, Cor.VERMELHO));
        veiculos.add(new VeiculoModel(new UUID(0, 0), "GHI-9101", "Civic", "Honda", 2018, Cor.PRETO));

        for (Field field : VeiculoModel.class.getDeclaredFields()) {
            if (field.getName().contains("id") || field.getName().contains("ID")) {
                continue;
            }

            TableColumn<VeiculoModel, String> column = new TableColumn<>(capitalize(field.getName()));
            field.setAccessible(true);

            column.setCellValueFactory(data -> {
                try {
                    Object value = field.get(data.getValue());
                    if (value == null) {
                        return new SimpleObjectProperty<>("N/A"); // Substitui valores nulos
                    }
                    return new SimpleObjectProperty<>(value.toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            });

            column.setVisible(true);

            mainTable.getColumns().add(column);
        }

        mainTable.getItems().addAll(veiculos);

        mainTable.refresh();

        mainVBox.getChildren().add(mainTable);
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}

