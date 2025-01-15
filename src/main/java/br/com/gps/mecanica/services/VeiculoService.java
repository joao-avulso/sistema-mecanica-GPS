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
        veiculo.setModelo(Utils.formatarMarcaModeloVeiculo(veiculo.getModelo()));
        veiculo.setMarca(Utils.formatarMarcaModeloVeiculo(veiculo.getMarca()));

        return veiculoRepository.save(veiculo);
    }

    public List<VeiculoModel> get() {
        return veiculoRepository.findAll();
    }

    public VeiculoModel get(UUID id) throws Exception {
        if (veiculoRepository.findById(id).isEmpty()) {
            throw new Exception("Veículo não encontrado");
        }

        return veiculoRepository.findById(id).get();
    }

    public VeiculoModel getByPlaca(String placa) throws Exception {
        if (veiculoRepository.findByPlaca(placa) == null) {
            throw new Exception("Veículo não encontrado");
        }

        return veiculoRepository.findByPlaca(placa);
    }

    public List<VeiculoModel> getByMarca(String marca) {
        return veiculoRepository.findByMarca(marca);
    }

    public List<VeiculoModel> getByModelo(String modelo) {
        return veiculoRepository.findByModelo(modelo);
    }

    public VeiculoModel update(UUID id, VeiculoModel veiculo) throws Exception {
        if (veiculoRepository.findById(id).isEmpty()) {
            throw new Exception("Veículo não encontrado");
        }

        VeiculoModel veiculoAtual = veiculoRepository.findById(id).get();

        if (veiculo.getPlaca() != null && !veiculo.getPlaca().isEmpty()) {
            String placa = Utils.formatarPlaca(veiculo.getPlaca());

            if (Utils.verificarPlaca(placa) == false) {
                throw new Exception("Placa inválida");
            }

            veiculoAtual.setPlaca(placa);
        }

        if (veiculo.getMarca() != null && !veiculo.getMarca().isEmpty()) {
            veiculoAtual.setMarca(Utils.formatarMarcaModeloVeiculo(veiculo.getMarca()));
        }

        if (veiculo.getModelo() != null && !veiculo.getModelo().isEmpty()) {
            veiculoAtual.setModelo(Utils.formatarMarcaModeloVeiculo(veiculo.getModelo()));
        }

        return veiculoRepository.save(veiculoAtual);
    }

    public void delete(UUID id) throws Exception {
        if (veiculoRepository.findById(id).isEmpty()) {
            throw new Exception("Veículo não encontrado");
        }

        veiculoRepository.deleteById(id);
    }
}
