package br.com.gps.mecanica.controllers;

import java.util.List;
import java.util.UUID;

import br.com.gps.mecanica.MecanicaFxMainApplication;
import br.com.gps.mecanica.dto.EnderecoDto;
import br.com.gps.mecanica.enums.ContatoEnum;
import br.com.gps.mecanica.enums.MenuSelectionEnum;
import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.services.FornecedorService;
import br.com.gps.mecanica.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ReadFornecedorController {

    @FXML
    private TableColumn<ProdutoModel, Integer> quantidadeColumn;

    @FXML
    private TextField estadoTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField ruaTextField;

    @FXML
    private TextField complementoTextField;

    @FXML
    private ChoiceBox<ContatoEnum> contatoChoiceBox;

    @FXML
    private TextField telefoneTextField;

    @FXML
    private TableColumn<ProdutoModel, Double> valorVendaColumn;

    @FXML
    private Button addButton;

    @FXML
    private TextField cpfTextField;

    @FXML
    private TextField bairroTextField;

    @FXML
    private TextField referenciaTextField;

    @FXML
    private TableColumn<ProdutoModel, Double> valorCompraColumn;

    @FXML
    private TextField nomeTextField;

    @FXML
    private TextField numeroTextField;

    @FXML
    private Label erroLabel;

    @FXML
    private TableView<ProdutoModel> produtosTableView;

    @FXML
    private TextField cepTextField;

    @FXML
    private TableColumn<ProdutoModel, String> nomeColumn;

    @FXML
    private TextField cidadeTextField;

    private FornecedorService fornecedorService;

    private UUID fornecedorId;

    @FXML
    void initialize() {
        this.fornecedorService = MecanicaFxMainApplication.getBean(FornecedorService.class);

        contatoChoiceBox.setValue(ContatoEnum.RESIDENCIAL);
        contatoChoiceBox.getItems().addAll(ContatoEnum.values());

        Utils.formatterInt(telefoneTextField);

        cepTextField.setOnKeyTyped(e -> {
            String cep = cepTextField.getText();

            if (cep.length() == 8) {
                try {
                    EnderecoDto endereco = Utils.apurarEndereco(cep);
                    bairroTextField.setText(endereco.getBairro());
                    cidadeTextField.setText(endereco.getLocalidade());
                    estadoTextField.setText(endereco.getUf());
                    ruaTextField.setText(endereco.getLogradouro());
                    complementoTextField.setText(endereco.getComplemento());
                } catch (Exception ex) {
                    Utils.errorMessage(erroLabel, ex.getMessage());
                }
            }

            System.out.println(cep);
        });

        this.nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        this.quantidadeColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantidade()));
        this.valorCompraColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValorCompra()));
        this.valorVendaColumn
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValorVenda()));

        produtosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        produtosTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && produtosTableView.getSelectionModel().getSelectedItem() != null) {
                ProdutoModel produto = produtosTableView.getSelectionModel().getSelectedItem();
                Utils.lerItem(MenuSelectionEnum.PRODUTO, produto);
                atualizaTabela();
            }
        });
    }

    private void atualizaTabela() {
        try {
            produtosTableView.getItems().setAll(fornecedorService.get(fornecedorId).getProdutos());
        } catch (Exception e) {
            Utils.errorMessage(erroLabel, "Erro ao atualizar tabela de veículos: " + e.getMessage());
        }
    }

    public void carregarFornecedor(FornecedorModel fornecedor) {
        this.fornecedorId = fornecedor.getId();
        this.nomeTextField.setText(fornecedor.getNome());
        this.emailTextField.setText(fornecedor.getEmail());

        if (fornecedor.getTelefones() != null && !fornecedor.getTelefones().isEmpty()) {
            TelefoneModel telefone = fornecedor.getTelefones().get(0);
            telefoneTextField.setText(telefone.getNumero());
            contatoChoiceBox.setValue(telefone.getTipo());
        }

        this.cpfTextField.setText(fornecedor.getCnpj());

        if (fornecedor.getEnderecos() != null && !fornecedor.getEnderecos().isEmpty()) {
            this.cepTextField.setText(fornecedor.getEnderecos().get(0).getCep());
            this.bairroTextField.setText(fornecedor.getEnderecos().get(0).getBairro());
            this.cidadeTextField.setText(fornecedor.getEnderecos().get(0).getCidade());
            this.estadoTextField.setText(fornecedor.getEnderecos().get(0).getEstado());
            this.ruaTextField.setText(fornecedor.getEnderecos().get(0).getRua());
            this.numeroTextField.setText(fornecedor.getEnderecos().get(0).getNumero());
            this.complementoTextField.setText(fornecedor.getEnderecos().get(0).getComplemento());
            this.referenciaTextField.setText(fornecedor.getEnderecos().get(0).getReferencia());
        }

        if (fornecedor.getProdutos() != null && !fornecedor.getProdutos().isEmpty()) {
            this.produtosTableView.getItems().setAll(fornecedor.getProdutos());
        }
    }

    @FXML
    void atualizar(ActionEvent event) {
        try {
            FornecedorModel fornecedorAtualizado = fornecedorService.get(fornecedorId);

            String cnpj = cpfTextField.getText();
            String nome = nomeTextField.getText();
            String email = emailTextField.getText();
            String telefone = telefoneTextField.getText();
            String cep = cepTextField.getText();
            String rua = ruaTextField.getText();
            String numero = numeroTextField.getText();
            String bairro = bairroTextField.getText();
            String cidade = cidadeTextField.getText();
            String estado = estadoTextField.getText();
            String complemento = complementoTextField.getText();
            String referencia = referenciaTextField.getText();
            ContatoEnum contato = contatoChoiceBox.getValue();

            if (cnpj.isEmpty() || nome.isEmpty() || email.isEmpty()) {
                throw new Exception("Preencha todos os campos obrigatórios");
            }

            fornecedorAtualizado.setCnpj(cnpj);
            fornecedorAtualizado.setNome(nome);
            fornecedorAtualizado.setEmail(email);

            if (!telefone.isEmpty()) {
                TelefoneModel telefoneModel = fornecedorAtualizado.getTelefones().isEmpty()
                        ? new TelefoneModel()
                        : fornecedorAtualizado.getTelefones().get(0);
                telefoneModel.setNumero(telefone);
                telefoneModel.setTipo(contatoChoiceBox.getValue());
                telefoneModel.setPessoa(fornecedorAtualizado);
                fornecedorAtualizado.setTelefones(List.of(telefoneModel));
            }

            // Atualiza ou cria o endereço
            if (!cep.isEmpty() || !rua.isEmpty() || !numero.isEmpty() || !bairro.isEmpty() || !cidade.isEmpty()
                    || !estado.isEmpty() || !complemento.isEmpty() || !referencia.isEmpty()) {
                EnderecoModel enderecoModel = fornecedorAtualizado.getEnderecos().isEmpty()
                        ? new EnderecoModel()
                        : fornecedorAtualizado.getEnderecos().get(0);
                enderecoModel.setCep(cep);
                enderecoModel.setRua(rua);
                enderecoModel.setNumero(numero);
                enderecoModel.setBairro(bairro);
                enderecoModel.setCidade(cidade);
                enderecoModel.setEstado(estado);
                enderecoModel.setComplemento(complemento);
                enderecoModel.setReferencia(referencia);
                enderecoModel.setTipo(contato);
                enderecoModel.setPessoa(fornecedorAtualizado);
                fornecedorAtualizado.setEnderecos(List.of(enderecoModel));
            }

            fornecedorService.update(fornecedorId, fornecedorAtualizado);

            nomeTextField.getScene().getWindow().hide();

        } catch (Exception e) {
            Utils.errorMessage(erroLabel, "Erro ao atualizar fornecedor: " + e.getMessage());
        }

    }

    @FXML
    void fechar(ActionEvent event) {
        nomeTextField.getScene().getWindow().hide();
    }

}
