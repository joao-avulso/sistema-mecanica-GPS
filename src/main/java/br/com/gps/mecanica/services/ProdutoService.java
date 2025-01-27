package br.com.gps.mecanica.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.repositories.FornecedorRepository;
import br.com.gps.mecanica.repositories.ProdutoRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<ProdutoModel> get() {
        return produtoRepository.findAll();
    }

    public ProdutoModel get(UUID id) throws Exception {
        if (!produtoRepository.existsById(id)) {
            throw new Exception("Produto não encontrado");
        }

        return produtoRepository.findById(id).get();
    }

    public List<ProdutoModel> getByNome(String nome) {
        return produtoRepository.findByNome(nome);
    }

    public List<ProdutoModel> getByValorVenda(Double valorVenda) {
        return produtoRepository.findByValorVenda(valorVenda);
    }

    public List<ProdutoModel> getByValorCompra(Double valorCompra) {
        return produtoRepository.findByValorCompra(valorCompra);
    }

    public List<ProdutoModel> getByQuantidade(Integer quantidade) {
        return produtoRepository.findByQuantidade(quantidade);
    }

    public List<ProdutoModel> getProdutosEmEstoque() {
        return produtoRepository.findByQuantidadeGreaterThan(0);
    }

    public List<ProdutoModel> getProdutosForaDeEstoque() {
        return produtoRepository.findByQuantidadeLessThan(1);
    }

    public List<ProdutoModel> getProdutosEmEstoqueEntre(Integer quantidade1, Integer quantidade2) {
        return produtoRepository.findByQuantidadeBetween(quantidade1, quantidade2);
    }

    public List<ProdutoModel> getProdutosByFornecedor(FornecedorModel fornecedor) throws Exception {
        if (fornecedor == null || !fornecedorRepository.existsById(fornecedor.getId())) {
            throw new Exception("Fornecedor não encontrado");
        }

        return produtoRepository.findByFornecedor(fornecedor);
    }

    public ProdutoModel create(ProdutoModel produto) throws Exception {
        produto.setNome(Utils.formatarString(produto.getNome()));
        produto.setDescricao(Utils.formatarString(produto.getDescricao()));
        return produtoRepository.save(produto);
    }

    public void delete(UUID id) throws Exception {
        if (!produtoRepository.existsById(id)) {
            throw new Exception("Produto não encontrado");
        }

        produtoRepository.deleteById(id);
    }

    public ProdutoModel update(UUID id, ProdutoModel produto) throws Exception {
        if (!produtoRepository.existsById(id)) {
            throw new Exception("Produto não encontrado");
        }

        ProdutoModel produtoAtual = produtoRepository.findById(id).get();

        String nome = produto.getNome();

        if (nome != null && !nome.isEmpty()) {
            produtoAtual.setNome(Utils.formatarString(nome));
        }

        String descricao = produto.getDescricao();

        if (descricao != null && !descricao.isEmpty()) {
            produtoAtual.setDescricao(Utils.formatarString(descricao));
        }

        Double valorCompra = produto.getValorCompra();

        if (valorCompra != null) {
            produtoAtual.setValorCompra(valorCompra);
        }

        Double valorVenda = produto.getValorVenda();

        if (valorVenda != null) {
            produtoAtual.setValorVenda(valorVenda);
        }

        Integer quantidade = produto.getQuantidade();

        if (quantidade != null) {
            produtoAtual.setQuantidade(quantidade);
        }

        return produtoRepository.save(produtoAtual);
    }
}
