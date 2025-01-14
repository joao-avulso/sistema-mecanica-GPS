package br.com.gps.mecanica.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.repositories.VeiculoRepository;

@Service
public class VeiculoServiceImpl implements VeiculoService {

    final VeiculoRepository veiculoRepository;

    public VeiculoServiceImpl(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Override
    public VeiculoModel create(VeiculoModel veiculo) {
        try {
            return veiculoRepository.save(veiculo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        try {
            veiculoRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<VeiculoModel> get() {
        return veiculoRepository.findAll();
    }

    @Override
    public VeiculoModel get(UUID id) {
        return veiculoRepository.findById(id).get();
    }

    @Override
    public VeiculoModel getByPlaca(String placa) {
        try {
            return veiculoRepository.findByPlaca(placa);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<VeiculoModel> getByMarca(String marca) {
        try {
            return veiculoRepository.findByMarca(marca);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<VeiculoModel> getByModelo(String modelo) {
        try {
            return veiculoRepository.findByModelo(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public VeiculoModel update(UUID id, VeiculoModel veiculo) {
        try {
            veiculo.setId(id);
            return veiculoRepository.save(veiculo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
