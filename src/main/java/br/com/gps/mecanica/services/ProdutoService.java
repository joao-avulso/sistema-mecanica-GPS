package br.com.gps.mecanica.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.repositories.ProdutoRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class ProdutoService {
    final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoModel> get() {
        return produtoRepository.findAll();
    }

    public ProdutoModel get(UUID id) {
        return produtoRepository.findById(id).get();
    }

    public List<ProdutoModel> getByNome(String nome) {
        try {
            return produtoRepository.findByNome(nome);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProdutoModel> getByValor_venda(Double valor_venda) {
        try {
            return produtoRepository.findByValor_venda(valor_venda);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProdutoModel> getByValor_compra(Double valor_compra) {
        try {
            return produtoRepository.findByValor_compra(valor_compra);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProdutoModel> getByQuantidade(Integer quantidade) {
        try {
            return produtoRepository.findByQuantidade(quantidade);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProdutoModel create(ProdutoModel produto) {
        try {
            produto.setNome(Utils.formatarString(produto.getNome()));
            produto.setDescricao(Utils.formatarString(produto.getDescricao()));
            return produtoRepository.save(produto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(UUID id) {
        try {
            produtoRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProdutoModel update(ProdutoModel produto) {
        try {
            produto.setNome(Utils.formatarString(produto.getNome()));
            produto.setDescricao(Utils.formatarString(produto.getDescricao()));
            return produtoRepository.save(produto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProdutoModel> getProdutosEmEstoque() {
        try {
            return produtoRepository.findByQuantidadeGreaterThan(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProdutoModel> getProdutosForaDeEstoque() {
        try {
            return produtoRepository.findByQuantidadeLessThan(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProdutoModel> getProdutosEmEstoqueEntre(Integer quantidade1, Integer quantidade2) {
        try {
            return produtoRepository.findByQuantidadeBetween(quantidade1, quantidade2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProdutoModel> getProdutosByFornecedor(FornecedorModel fornecedor) {
        try {
            return produtoRepository.findByFornecedor(fornecedor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
