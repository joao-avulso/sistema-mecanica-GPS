package br.com.gps.mecanica.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
public class ProdutoModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idFornecedor")
    private FornecedorModel fornecedor;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private Double valorVenda;

    private Double valorCompra;

    @Column(nullable = false)
    private Integer quantidade;

    public ProdutoModel() {
    }

    public ProdutoModel(String nome, String descricao, Double valor_venda, Double valor_compra, Integer quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.valorVenda = valor_venda;
        this.valorCompra = valor_compra;
        this.quantidade = quantidade;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valor_venda) {
        this.valorVenda = valor_venda;
    }

    public Double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(Double valor_compra) {
        this.valorCompra = valor_compra;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public FornecedorModel getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorModel fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "ProdutoModel [descricao=" + descricao + ", fornecedor=" + fornecedor + ", id=" + id + ", nome=" + nome
                + ", quantidade=" + quantidade + ", valor_compra=" + valorCompra + ", valor_venda=" + valorVenda
                + "]";
    }
}
