package br.com.gps.mecanica.models;

import java.util.List;
import java.util.UUID;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class ClienteModel implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;
    
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String numero;
    private String complemento;
    private String referencia;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<TelefoneModel> telefones;
    
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<VeiculoModel> veiculos;

    public ClienteModel(){}

    public ClienteModel(String nome, String cpf, String email, String cep, String rua, String bairro,
    String cidade, String estado, String numero, String complemento, String referencia, List<TelefoneModel> telefones,  List<VeiculoModel> veiculos) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.numero = numero;
        this.complemento = complemento;
        this.referencia = referencia;
        this.telefones = telefones;
        this.veiculos = veiculos;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<TelefoneModel> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelefoneModel> telefones) {
        this.telefones = telefones;
    }

    public List<VeiculoModel> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<VeiculoModel> veiculos) {
        this.veiculos = veiculos;
    }

    @Override
    public String toString() {
        return "ClienteModel [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", cep=" + cep
                + ", rua=" + rua + ", bairro=" + bairro + ", cidade=" + cidade + ", estado=" + estado + ", numero="
                + numero + ", complemento=" + complemento + ", referencia=" + referencia + "]";
    }
}
