package br.com.gps.mecanica.models;

import java.time.LocalDate;
import java.util.List;

import br.com.gps.mecanica.enums.Cargo;
import br.com.gps.mecanica.enums.Pessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "funcionarios")
public class FuncionarioModel extends PessoaBaseModel {
    @Column(nullable = false, unique = true)
    private String cpf;

    private Cargo cargo;

    private LocalDate dataAdmissao;

    public FuncionarioModel() {
    }

    public FuncionarioModel(Pessoa tipoPessoa, String nome, String cpf, String email, List<TelefoneModel> telefones,
            List<EnderecoModel> enderecos, Cargo cargo, LocalDate dataAdmissao) {
        super(tipoPessoa, nome, email, telefones, enderecos);
        this.cpf = cpf;
        this.cargo = cargo;
        this.dataAdmissao = dataAdmissao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }
}
