package br.com.gps.mecanica.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gps.mecanica.models.VeiculoModel;

public interface VeiculoRepository extends JpaRepository<VeiculoModel, UUID> {

    VeiculoModel findByPlaca(String placa);

    List<VeiculoModel> findByModelo(String modelo);

    List<VeiculoModel> findByMarca(String marca);
}
