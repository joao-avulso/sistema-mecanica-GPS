package br.com.gps.mecanica.services;

import java.util.List;
import java.util.UUID;

import br.com.gps.mecanica.models.VeiculoModel;

public interface VeiculoService {

    List<VeiculoModel> get();

    VeiculoModel get(UUID id);

    List<VeiculoModel> getByModelo(String modelo);

    List<VeiculoModel> getByMarca(String marca);

    VeiculoModel create(VeiculoModel veiculo);

    VeiculoModel update(UUID id, VeiculoModel veiculo);

    void delete(UUID id);
}
