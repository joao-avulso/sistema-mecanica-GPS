package br.com.gps.mecanica.models;

import java.time.LocalDate;
import java.util.List;

import br.com.gps.mecanica.enums.CargoEnum;
import br.com.gps.mecanica.enums.PessoaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "funcionarios")
public class FuncionarioModel extends PessoaBaseModel {
    @Column(nullable = false, unique = true)
    private String cpf;

    private CargoEnum cargo;

    private LocalDate dataAdmissao;

    public FuncionarioModel() {
    }

    public FuncionarioModel(PessoaEnum tipoPessoa, String nome, String cpf, String email, List<TelefoneModel> telefones,
            List<EnderecoModel> enderecos, CargoEnum cargo, LocalDate dataAdmissao) {
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

    public CargoEnum getCargo() {
        return cargo;
    }

    public void setCargo(CargoEnum cargo) {
        this.cargo = cargo;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }
}
