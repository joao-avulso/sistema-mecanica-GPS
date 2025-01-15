package br.com.gps.mecanica.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gps.mecanica.models.FornecedorModel;

public interface FornecedorRepository extends JpaRepository<FornecedorModel, UUID> {

    FornecedorModel findByCnpj(String cnpj);

    FornecedorModel findByEmail(String email);

    List<FornecedorModel> findByNome(String nome);

    void deleteByCnpj(String cnpj);
}
