package br.com.gps.mecanica.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordens_servico")
public class OrdemServicoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private ClienteModel cliente;

    @ManyToOne
    @JoinColumn(name = "idVeiculo")
    private VeiculoModel veiculo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "servicos_ordem_servico", joinColumns = @JoinColumn(name = "idOrdemServico"), inverseJoinColumns = @JoinColumn(name = "idServico"))
    private List<ServicoModel> servicos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "produtos_ordem_servico", joinColumns = @JoinColumn(name = "idOrdemServico"), inverseJoinColumns = @JoinColumn(name = "idProdutos"))
    private List<ProdutoModel> produtos;

    @ManyToOne
    @JoinColumn(name = "idFuncionario")
    private FuncionarioModel funcionario;

    private String descricao;

    private Boolean contratada = false;

    private Boolean finalizada = false;

    public OrdemServicoModel() {
    }

    public OrdemServicoModel(ClienteModel cliente, VeiculoModel veiculo, List<ServicoModel> servicos,
            List<ProdutoModel> produtos, FuncionarioModel funcionario, String descricao) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.servicos = servicos;
        this.produtos = produtos;
        this.funcionario = funcionario;
        this.descricao = descricao;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public VeiculoModel getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(VeiculoModel veiculo) {
        this.veiculo = veiculo;
    }

    public List<ServicoModel> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicoModel> servicos) {
        this.servicos = servicos;
    }

    public List<ProdutoModel> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoModel> produtos) {
        this.produtos = produtos;
    }

    public FuncionarioModel getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioModel funcionario) {
        this.funcionario = funcionario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getContratada() {
        return contratada;
    }

    public void setContratada() {
        this.contratada = !this.contratada;
    }

    public Boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada() {
        this.finalizada = !this.finalizada;
    }

    @Override
    public String toString() {
        String produtosString = "";
        for (ProdutoModel produto : produtos) {
            produtosString += produto.toString();
            produtosString += ", ";
        }

        String servicosString = "";
        for (ServicoModel servico : servicos) {
            servicosString += servico.toString();
            servicosString += ", ";
        }
        return "OrdemServicoModel [cliente=" + cliente.toString() + ", descricao=" + descricao + ", funcionario="
                + funcionario
                + ", id=" + id + ", produtos=" + produtosString + ", servicos=" + servicosString
                + ", veiculo=" + veiculo.toString()
                + "]";
    }

}