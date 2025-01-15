package br.com.gps.mecanica.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.ProdutoModel;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, UUID> {

    List<ProdutoModel> findByNome(String nome);

    List<ProdutoModel> findByValor_venda(Double valor_venda);

    List<ProdutoModel> findByValor_compra(Double valor_compra);

    List<ProdutoModel> findByQuantidade(Integer quantidade);

    List<ProdutoModel> findByQuantidadeGreaterThan(Integer quantidade);

    List<ProdutoModel> findByQuantidadeLessThan(Integer quantidade);

    List<ProdutoModel> findByQuantidadeBetween(Integer quantidade1, Integer quantidade2);

    List<ProdutoModel> findByFornecedor(FornecedorModel fornecedor);
}
