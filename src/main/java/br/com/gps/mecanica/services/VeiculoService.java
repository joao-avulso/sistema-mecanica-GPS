package br.com.gps.mecanica.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.repositories.VeiculoRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class VeiculoService {

    final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public VeiculoModel create(VeiculoModel veiculo) throws Exception {

        String placa = Utils.formatarPlaca(veiculo.getPlaca());

        if (Utils.verificarPlaca(placa) == false) {
            throw new Exception("Placa inválida");
        }

        if (veiculoRepository.findByPlaca(veiculo.getPlaca()) != null) {
            throw new Exception("Placa já cadastrada");
        }

        veiculo.setPlaca(placa);
        veiculo.setModelo(Utils.formatarModeloVeiculo(veiculo.getModelo()));
        veiculo.setMarca(Utils.formatarModeloVeiculo(veiculo.getMarca()));

        return veiculoRepository.save(veiculo);
    }

    public void delete(UUID id) {
        try {
            veiculoRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<VeiculoModel> get() {
        return veiculoRepository.findAll();
    }

    public VeiculoModel get(UUID id) {
        return veiculoRepository.findById(id).get();
    }

    public VeiculoModel getByPlaca(String placa) {
        try {
            return veiculoRepository.findByPlaca(placa);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VeiculoModel> getByMarca(String marca) {
        try {
            return veiculoRepository.findByMarca(marca);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VeiculoModel> getByModelo(String modelo) {
        try {
            return veiculoRepository.findByModelo(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public VeiculoModel update(UUID id, VeiculoModel veiculo) throws Exception {

        String placa = Utils.formatarPlaca(veiculo.getPlaca());

        if (Utils.verificarPlaca(placa) == false) {
            throw new Exception("Placa inválida");
        }

        veiculo.setPlaca(placa);
        veiculo.setModelo(Utils.formatarModeloVeiculo(veiculo.getModelo()));
        veiculo.setMarca(Utils.formatarModeloVeiculo(veiculo.getMarca()));

        veiculo.setId(id);

        return veiculoRepository.save(veiculo);
    }
}
