package br.com.gps.mecanica.models;

import java.io.Serial;
import java.util.List;
import java.util.UUID;

import br.com.gps.mecanica.enums.PessoaEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "pessoas")
public class PessoaBaseModel {

    @Serial
    protected static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID id;

    @Column(nullable = false)
    protected PessoaEnum tipoPessoa;

    @Column(nullable = false)
    protected String nome;

    @Column(unique = true, nullable = true)
    protected String email;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<TelefoneModel> telefones;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<EnderecoModel> enderecos;

    public PessoaBaseModel() {
    }

    public PessoaBaseModel(PessoaEnum tipoPessoa, String nome, String email, List<TelefoneModel> telefones,
            List<EnderecoModel> enderecos) {
        this.tipoPessoa = tipoPessoa;
        this.nome = nome;
        this.email = email;
        this.telefones = telefones;
        this.enderecos = enderecos;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PessoaEnum getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(PessoaEnum tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TelefoneModel> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelefoneModel> telefones) {
        this.telefones = telefones;
    }

    public List<EnderecoModel> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoModel> enderecos) {
        this.enderecos = enderecos;
    }

    @Override
    public String toString() {
        return "PessoaBaseModel [email=" + email + ", enderecos=" + enderecos + ", id=" + id + ", nome=" + nome
                + ", telefones=" + telefones + ", tipoPessoa=" + tipoPessoa + "]";
    }
}
