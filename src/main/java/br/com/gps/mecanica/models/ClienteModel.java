package br.com.gps.mecanica.models;

import java.util.List;

import br.com.gps.mecanica.enums.Pessoa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class ClienteModel extends PessoaBaseModel {
    @Column(unique = true, nullable = false)
    private String cpf;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<VeiculoModel> veiculos;

    public ClienteModel() {
    }

    public ClienteModel(Pessoa tipoPessoa, String nome, String cpf, String email, List<TelefoneModel> telefones,
            List<EnderecoModel> enderecos, List<VeiculoModel> veiculos) {
        super(tipoPessoa, nome, email, telefones, enderecos);
        this.cpf = cpf;
        this.veiculos = veiculos;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<VeiculoModel> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<VeiculoModel> veiculos) {
        this.veiculos = veiculos;
    }

    @Override
    public String toString() {
        return "ClienteModel [cpf=" + cpf + ", veiculos=" + veiculos.toString() + "endereco="
                + enderecos.toString()
                + ", telefones=" + telefones.toString() + ", tipoPessoa=" + tipoPessoa + ", nome=" + nome + ", email="
                + email + ", id=" + id + "]";
    }
}
