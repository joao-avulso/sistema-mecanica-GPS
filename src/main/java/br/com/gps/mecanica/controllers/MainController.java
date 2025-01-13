package br.com.gps.mecanica.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public class MainController {

    @FXML
    private TableView<?> mainTable;

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
    private ListView<?> mainList;

    @FXML
    void mostraVeiculos(ActionEvent event) {
        System.out.println("Veiculos");
    }
}

