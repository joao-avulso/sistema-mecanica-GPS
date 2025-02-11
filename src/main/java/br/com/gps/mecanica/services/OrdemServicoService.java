package br.com.gps.mecanica.services;

import java.util.List;
import java.util.Optional;
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

        List<ServicoModel> servicos = ordemServico.getServicos();
        for (ServicoModel servico : servicos) {
            if (servicoRepository.findById(servico.getId()).isEmpty()) {
                throw new Exception("Serviço " + servico.getNome() + " não encontrado");
            }
        }

        List<ProdutoModel> produtos = ordemServico.getProdutos();
        for (ProdutoModel produto : produtos) {
            if (produtoRepository.findById(produto.getId()).isEmpty()) {
                throw new Exception("Produto " + produto.getNome() + " não encontrado");
            }
        }

        ordemServico.setCliente(clienteRepository.findByCpf(cliente.getCpf()));
        ordemServico.setVeiculo(veiculoRepository.findByPlaca(veiculo.getPlaca()));
        ordemServico.setFuncionario(funcionarioRepository.findById(funcionario.getId()).get());
        ordemServico.setDescricao(Utils.formatarString(ordemServico.getDescricao()));
        ordemServico.setServicos(servicos);
        ordemServico.setProdutos(produtos);

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

        if (ordemServicoAtual.getDataContratacao() != ordemServico.getDataContratacao()) {
            ordemServicoAtual.setDataContratacao(ordemServico.getDataContratacao());
        }

        if (ordemServicoAtual.getDataFinalizacao() != ordemServico.getDataFinalizacao()) {
            ordemServicoAtual.setDataFinalizacao(ordemServico.getDataFinalizacao());
        }

        if (ordemServicoAtual.getDataPrevista() != ordemServico.getDataPrevista()) {
            ordemServicoAtual.setDataPrevista(ordemServico.getDataPrevista());
        }

        if (ordemServicoAtual.getContratada() != ordemServico.getContratada()) {
            ordemServicoAtual.setContratada();
        }

        return ordemServicoRepository.save(ordemServicoAtual);
    }

    public ServicoModel addServico(UUID id, ServicoModel servico) throws Exception {
        Optional<OrdemServicoModel> ordemServicoOpt = ordemServicoRepository.findById(id);
        if (ordemServicoOpt.isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        OrdemServicoModel ordemServico = ordemServicoOpt.get();

        Optional<ServicoModel> servicoOpt = servicoRepository.findById(servico.getId());
        if (servicoOpt.isEmpty()) {
            throw new Exception("Serviço não encontrado");
        }

        ServicoModel servicoParaAdicionar = servicoOpt.get();

        ordemServico.getServicos().add(servicoParaAdicionar);

        ordemServicoRepository.save(ordemServico);

        return servicoParaAdicionar;
    }

    public ServicoModel removeServico(UUID id, ServicoModel servico) throws Exception {
        Optional<OrdemServicoModel> ordemServicoOpt = ordemServicoRepository.findById(id);
        if (ordemServicoOpt.isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        Optional<ServicoModel> servicoOpt = servicoRepository.findById(servico.getId());
        if (servicoOpt.isEmpty()) {
            throw new Exception("Serviço não encontrado");
        }

        OrdemServicoModel ordemServico = ordemServicoOpt.get();
        ServicoModel servicoParaRemover = servicoOpt.get();

        ordemServico.getServicos().removeIf(s -> s.getId().equals(servicoParaRemover.getId()));

        ordemServicoRepository.save(ordemServico);

        return servicoParaRemover;
    }

    public ProdutoModel addProduto(UUID id, ProdutoModel produto) throws Exception {
        Optional<OrdemServicoModel> ordemServicoOpt = ordemServicoRepository.findById(id);
        if (ordemServicoOpt.isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        Optional<ProdutoModel> produtoOpt = produtoRepository.findById(produto.getId());
        if (produtoOpt.isEmpty()) {
            throw new Exception("Produto não encontrado");
        }

        OrdemServicoModel ordemServico = ordemServicoOpt.get();

        ProdutoModel produtoParaAdicionar = produtoOpt.get();

        ordemServico.getProdutos().add(produtoParaAdicionar);

        ordemServicoRepository.save(ordemServico);

        return produtoParaAdicionar;
    }

    public ProdutoModel removeProduto(UUID id, ProdutoModel produto) throws Exception {
        Optional<OrdemServicoModel> ordemServicoOpt = ordemServicoRepository.findById(id);
        if (ordemServicoOpt.isEmpty()) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        Optional<ProdutoModel> produtoOpt = produtoRepository.findById(produto.getId());
        if (produtoOpt.isEmpty()) {
            throw new Exception("Produto não encontrado");
        }

        OrdemServicoModel ordemServico = ordemServicoOpt.get();
        ProdutoModel produtoParaRemover = produtoOpt.get();

        ordemServico.getProdutos().removeIf(p -> p.getId().equals(produtoParaRemover.getId()));

        ordemServicoRepository.save(ordemServico);

        return produtoParaRemover;
    }

    public void delete(UUID id) throws Exception {
        OrdemServicoModel ordemServico = ordemServicoRepository.findById(id).get();

        if (ordemServico == null) {
            throw new Exception("Ordem de serviço não encontrada");
        }

        ordemServicoRepository.delete(ordemServico);
    }

}