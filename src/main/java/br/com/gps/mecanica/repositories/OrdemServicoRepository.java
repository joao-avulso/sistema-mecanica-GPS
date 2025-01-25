package br.com.gps.mecanica.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.OrdemServicoModel;
import br.com.gps.mecanica.models.VeiculoModel;

public interface OrdemServicoRepository extends JpaRepository<OrdemServicoModel, UUID> {
    List<OrdemServicoModel> findByCliente(ClienteModel cliente);

    List<OrdemServicoModel> findByVeiculo(VeiculoModel veiculo);
}