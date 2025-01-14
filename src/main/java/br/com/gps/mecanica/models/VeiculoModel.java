package br.com.gps.mecanica.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import br.com.gps.mecanica.enums.Cor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "veiculos")
public class VeiculoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID idCliente;
    private String placa;
    private String modelo;
    private String marca;
    private Integer ano;
    private Cor cor;

    public VeiculoModel() {
    }

    public VeiculoModel(UUID idCliente, String placa, String modelo, String marca, Integer ano, Cor cor) {
        this.idCliente = idCliente;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.cor = cor;
    }

    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(UUID idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public Integer getAno() {
        return ano;
    }
    
    public void setAno(Integer ano) {
        this.ano = ano;
    }
    
    public Cor getCor() {
        return cor;
    }
    
    public void setCor(Cor cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return "VeiculoModel [id=" + id + ", idCliente=" + idCliente + ", placa=" + placa + ", modelo=" + modelo
                + ", marca=" + marca + ", ano=" + ano + ", cor=" + cor + "]";
    }
}
