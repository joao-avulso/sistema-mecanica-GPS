package br.com.gps.mecanica.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import br.com.gps.mecanica.enums.ContatoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "enderecos")
public class EnderecoModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String numero;

    private String complemento;

    private String referencia;

    private ContatoEnum tipo;

    @ManyToOne
    @JoinColumn(name = "idPessoa", nullable = false)
    private PessoaBaseModel pessoa;

    public EnderecoModel() {
    }

    public EnderecoModel(String cep, String rua, String bairro, String cidade, String estado, String numero,
            String complemento, String referencia, ContatoEnum tipo, PessoaBaseModel pessoa) {
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.numero = numero;
        this.complemento = complemento;
        this.referencia = referencia;
        this.pessoa = pessoa;
        this.tipo = tipo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public ContatoEnum getTipo() {
        return tipo;
    }

    public void setTipo(ContatoEnum tipo) {
        this.tipo = tipo;
    }

    public PessoaBaseModel getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaBaseModel pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public String toString() {
        return "EnderecoModel [bairro=" + bairro + ", cep=" + cep + ", cidade=" + cidade + ", complemento="
                + complemento
                + ", estado=" + estado + ", id=" + id + ", numero=" + numero + ", referencia=" + referencia + ", rua="
                + rua + ", tipo=" + tipo + "]";
    }
}
