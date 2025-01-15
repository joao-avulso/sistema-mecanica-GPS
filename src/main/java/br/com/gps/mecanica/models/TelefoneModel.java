package br.com.gps.mecanica.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import br.com.gps.mecanica.enums.Contato;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

@Entity
@Table(name = "telefones")
public class TelefoneModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String numero;

    private Contato tipo;

    @ManyToOne
    @JoinColumn(name = "idPessoa", nullable = false)
    private PessoaBaseModel pessoa;

    public TelefoneModel() {
    }

    public TelefoneModel(String numero, Contato tipo, PessoaBaseModel pessoa) {
        this.numero = numero;
        this.tipo = tipo;
        this.pessoa = pessoa;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Contato getTipo() {
        return tipo;
    }

    public void setTipo(Contato tipo) {
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
        return "TelefoneModel [id=" + id + ", numero=" + numero + ", tipo=" + tipo + ", pessoa=" + pessoa + "]";
    }
}
