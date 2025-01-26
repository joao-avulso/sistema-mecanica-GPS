package br.com.gps.mecanica.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gps.mecanica.enums.CorEnum;
import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.repositories.ClienteRepository;
import br.com.gps.mecanica.repositories.VeiculoRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public VeiculoModel create(VeiculoModel veiculo) throws Exception {

        String placa = Utils.formatarPlaca(veiculo.getPlaca());

        if (Utils.verificarPlaca(placa) == false) {
            throw new Exception("Placa inválida");
        }

        if (veiculoRepository.findByPlaca(veiculo.getPlaca()) != null) {
            throw new Exception("Placa já cadastrada " + veiculo.getPlaca());
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

        String placa = veiculo.getPlaca();

        if (placa != null && !placa.isEmpty()) {
            String placaFormatada = Utils.formatarPlaca(veiculo.getPlaca());

            if (Utils.verificarPlaca(placaFormatada) == false) {
                throw new Exception("Placa inválida");
            }

            veiculoAtual.setPlaca(placaFormatada);
        }

        String marca = veiculo.getMarca();

        if (marca != null && !marca.isEmpty()) {
            veiculoAtual.setMarca(Utils.formatarMarcaModeloVeiculo(marca));
        }

        String modelo = veiculo.getModelo();

        if (modelo != null && !modelo.isEmpty()) {
            veiculoAtual.setModelo(Utils.formatarMarcaModeloVeiculo(modelo));
        }

        Integer ano = veiculo.getAno();

        if (ano != null) {
            veiculoAtual.setAno(ano);
        }

        CorEnum cor = veiculo.getCor();

        if (cor != null) {
            veiculoAtual.setCor(cor);
        }

        return veiculoRepository.save(veiculoAtual);
    }

    public void delete(UUID id) throws Exception {
        VeiculoModel veiculo = veiculoRepository.findById(id).get();
        
        if (veiculo == null) {
            throw new Exception("Veículo não encontrado");
        }
        
        ClienteModel cliente = veiculo.getCliente();

        if (cliente != null) {
            veiculo.setCliente(null);
            veiculoRepository.save(veiculo);
            cliente.getVeiculos().remove(veiculo);
            clienteRepository.save(cliente);
        }

        veiculoRepository.delete(veiculo);
    }
}
