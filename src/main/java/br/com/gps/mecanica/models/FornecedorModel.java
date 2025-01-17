package br.com.gps.mecanica.models;

import java.util.List;

import br.com.gps.mecanica.enums.PessoaEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "fornecedores")
public class FornecedorModel extends PessoaBaseModel {

    @Column(unique = true, nullable = false)
    private String cnpj;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProdutoModel> produtos;

    public FornecedorModel() {
    }

    public FornecedorModel(PessoaEnum tipoPessoa, String nome, String email, List<EnderecoModel> enderecos,
            List<TelefoneModel> telefones, String cnpj, List<ProdutoModel> produtos) {
        super(tipoPessoa, nome, email, telefones, enderecos);
        this.cnpj = cnpj;
        this.produtos = produtos;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<ProdutoModel> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoModel> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "FornecedorModel [cnpj=" + cnpj + ", produtos=" + produtos.toString() + "endereco="
                + enderecos.toString()
                + ", telefones=" + telefones.toString() + ", tipoPessoa=" + tipoPessoa + ", nome=" + nome + ", email="
                + email + ", id=" + id + "]";
    }
}
