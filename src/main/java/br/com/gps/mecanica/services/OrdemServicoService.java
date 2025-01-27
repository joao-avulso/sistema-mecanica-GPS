package br.com.gps.mecanica.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.FuncionarioModel;
import br.com.gps.mecanica.models.OrdemServicoModel;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.models.ServicoModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.repositories.ClienteRepository;
import br.com.gps.mecanica.repositories.FuncionarioRepository;
import br.com.gps.mecanica.repositories.OrdemServicoRepository;
import br.com.gps.mecanica.repositories.ProdutoRepository;
import br.com.gps.mecanica.repositories.ServicoRepository;
import br.com.gps.mecanica.repositories.VeiculoRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class OrdemServicoService {
    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<OrdemServicoModel> get() {
        return ordemServicoRepository.findAll();
    }

    public OrdemServicoModel get(UUID id) throws Exception {
        if (ordemServicoRepository.findById(id).isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        return ordemServicoRepository.findById(id).get();
    }

    public List<OrdemServicoModel> getByCpfCliente(String cpf) {
        return ordemServicoRepository.findByCliente(clienteRepository.findByCpf(cpf));
    }

    public List<OrdemServicoModel> getByPlacaVeiculo(String placa) {
        return ordemServicoRepository.findByVeiculo(veiculoRepository.findByPlaca(placa));
    }

    public List<ServicoModel> getServicos(UUID id) throws Exception {
        if (ordemServicoRepository.findById(id).isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        return ordemServicoRepository.findById(id).get().getServicos();
    }

    public List<ProdutoModel> getProdutos(UUID id) throws Exception {
        if (ordemServicoRepository.findById(id).isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        return ordemServicoRepository.findById(id).get().getProdutos();
    }

    public void ContratarOrdemServico(UUID id) throws Exception {
        if (ordemServicoRepository.findById(id).isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        OrdemServicoModel ordemServico = ordemServicoRepository.findById(id).get();

        ordemServico.setContratada();

        ordemServicoRepository.save(ordemServico);
    }

    public void FinalizarOrdemServico(UUID id) throws Exception {
        if (ordemServicoRepository.findById(id).isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        OrdemServicoModel ordemServico = ordemServicoRepository.findById(id).get();

        ordemServico.setFinalizada();

        ordemServicoRepository.save(ordemServico);
    }

    public OrdemServicoModel create(OrdemServicoModel ordemServico) throws Exception {
        ClienteModel cliente = ordemServico.getCliente();

        if (clienteRepository.findByCpf(cliente.getCpf()) == null) {
            throw new Exception("Cliente não encontrado");
        }

        VeiculoModel veiculo = ordemServico.getVeiculo();

        if (veiculoRepository.findByPlaca(veiculo.getPlaca()) == null) {
            throw new Exception("Veículo não encontrado");
        }

        FuncionarioModel funcionario = ordemServico.getFuncionario();

        if (funcionarioRepository.findById(funcionario.getId()).isEmpty()) {
            throw new Exception("Funcionário não encontrado");
        }

        ordemServico.setCliente(clienteRepository.findByCpf(cliente.getCpf()));
        ordemServico.setVeiculo(veiculoRepository.findByPlaca(veiculo.getPlaca()));
        ordemServico.setFuncionario(funcionarioRepository.findById(funcionario.getId()).get());
        ordemServico.setDescricao(Utils.formatarString(ordemServico.getDescricao()));

        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServicoModel update(UUID id, OrdemServicoModel ordemServico) throws Exception {
        if (ordemServicoRepository.findById(id).isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        OrdemServicoModel ordemServicoAtual = ordemServicoRepository.findById(id).get();

        if (ordemServicoAtual.getDescricao() != ordemServico.getDescricao()) {
            ordemServicoAtual.setDescricao(Utils.formatarString(ordemServico.getDescricao()));
        }

        return ordemServicoRepository.save(ordemServicoAtual);
    }

    public OrdemServicoModel addServico(UUID id, ServicoModel servico) throws Exception {
        if (ordemServicoRepository.findById(id).isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        OrdemServicoModel ordemServico = ordemServicoRepository.findById(id).get();

        if (servicoRepository.findById(servico.getId()).isEmpty()) {
            throw new Exception("Serviço não encontrado");
        }

        ordemServico.getServicos().add(servicoRepository.findById(servico.getId()).get());

        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServicoModel removeServico(UUID id, ServicoModel servico) throws Exception {
        if (ordemServicoRepository.findById(id).isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        if (servicoRepository.findById(servico.getId()).isEmpty()) {
            throw new Exception("Serviço não encontrado");
        }

        OrdemServicoModel ordemServico = ordemServicoRepository.findById(id).get();

        ordemServico.getServicos().remove(servicoRepository.findById(servico.getId()).get());

        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServicoModel addProduto(UUID id, ServicoModel produto) throws Exception {
        if (ordemServicoRepository.findById(id).isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        if (produtoRepository.findById(produto.getId()).isEmpty()) {
            throw new Exception("Produto não encontrado");
        }

        OrdemServicoModel ordemServico = ordemServicoRepository.findById(id).get();

        ordemServico.getProdutos().add(produtoRepository.findById(produto.getId()).get());

        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServicoModel removeProduto(UUID id, ServicoModel produto) throws Exception {
        if (ordemServicoRepository.findById(id).isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        if (produtoRepository.findById(produto.getId()).isEmpty()) {
            throw new Exception("Produto não encontrado");
        }

        OrdemServicoModel ordemServico = ordemServicoRepository.findById(id).get();

        ordemServico.getProdutos().remove(produtoRepository.findById(produto.getId()).get());

        return ordemServicoRepository.save(ordemServico);
    }

    public void delete(UUID id) throws Exception {
        OrdemServicoModel ordemServico = ordemServicoRepository.findById(id).get();

        if (ordemServico == null) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        ordemServicoRepository.delete(ordemServico);
    }

}