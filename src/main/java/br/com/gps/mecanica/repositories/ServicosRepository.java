package br.com.gps.mecanica.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gps.mecanica.models.ServicoModel;

public interface ServicosRepository extends JpaRepository<ServicoModel, UUID> {

    List<ServicoModel> findByNome(String nome);

    List<ServicoModel> findByValor(Double valor);
}
