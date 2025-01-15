package br.com.gps.mecanica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.repositories.ClienteRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteModel> get() {
        return clienteRepository.findAll();
    };

    public ClienteModel get(UUID id) {
        return clienteRepository.findById(id).get();
    };

    public ClienteModel get(String cpf) {
        return clienteRepository.findByCpf(cpf);
    };

    public List<ClienteModel> getByNome(String nome) {
        return clienteRepository.findByNome(nome);
    };

    public ClienteModel getByEmail(String email) {
        return clienteRepository.findByEmail(email);
    };

    public ClienteModel getByVeiculo(List<VeiculoModel> veiculos) {
        return clienteRepository.findByVeiculos(veiculos);
    }

    public ClienteModel create(ClienteModel cliente) throws Exception {
        cliente.setNome(Utils.formatarString(cliente.getNome()));
        cliente.setCpf(Utils.formatarCpf(cliente.getCpf()));
        cliente.setEmail(Utils.formatarEmail(cliente.getEmail()));

        if (!cliente.getEnderecos().isEmpty()) {
            List<EnderecoModel> enderecos = new ArrayList<>();

            for (EnderecoModel endereco : cliente.getEnderecos()) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecos.add(endereco);
            }

            cliente.setEnderecos(enderecos);
        }

        if (!cliente.getTelefones().isEmpty()) {
            List<TelefoneModel> telefones = new ArrayList<>();

            for (TelefoneModel telefone : cliente.getTelefones()) {
                Utils.formatarTelefone(telefone);
                telefones.add(telefone);
            }

            cliente.setTelefones(telefones);
        }

        if (!cliente.getVeiculos().isEmpty()) {
            List<VeiculoModel> veiculos = new ArrayList<>();

            for (VeiculoModel veiculo : cliente.getVeiculos()) {
                Utils.formatarVeiculo(veiculo);
                veiculos.add(veiculo);
            }

            cliente.setVeiculos(veiculos);
        }

        if (clienteRepository.findByCpf(cliente.getCpf()) != null) {
            throw new Exception("CPF já cadastrado");
        }

        if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new Exception("Email já cadastrado");
        }

        if (Utils.verificarEmail(Utils.formatarEmail(cliente.getEmail())) == false) {
            throw new Exception("Email inválido");
        }

        if (Utils.verificarCpf(Utils.formatarCpf(cliente.getCpf())) == false) {
            throw new Exception("CPF inválido");
        }

        return clienteRepository.save(cliente);
    }

    public ClienteModel update(UUID id, ClienteModel cliente) throws Exception {

        cliente.setId(id);
        cliente.setNome(Utils.formatarString(cliente.getNome()));
        cliente.setCpf(Utils.formatarCpf(cliente.getCpf()));
        cliente.setEmail(Utils.formatarEmail(cliente.getEmail()));

        if (!cliente.getEnderecos().isEmpty()) {
            List<EnderecoModel> enderecos = new ArrayList<>();

            for (EnderecoModel endereco : cliente.getEnderecos()) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecos.add(endereco);
            }

            cliente.setEnderecos(enderecos);
        }

        if (!cliente.getTelefones().isEmpty()) {
            List<TelefoneModel> telefones = new ArrayList<>();

            for (TelefoneModel telefone : cliente.getTelefones()) {
                Utils.formatarTelefone(telefone);
                telefones.add(telefone);
            }

            cliente.setTelefones(telefones);
        }

        if (!cliente.getVeiculos().isEmpty()) {
            List<VeiculoModel> veiculos = new ArrayList<>();

            for (VeiculoModel veiculo : cliente.getVeiculos()) {
                Utils.formatarVeiculo(veiculo);
                veiculos.add(veiculo);
            }

            cliente.setVeiculos(veiculos);
        }

        if (clienteRepository.findByCpf(cliente.getCpf()) != null) {
            throw new Exception("CPF já cadastrado");
        }

        if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new Exception("Email já cadastrado");
        }

        if (Utils.verificarEmail(cliente.getEmail()) == false) {
            throw new Exception("Email inválido");
        }

        if (Utils.verificarCpf(cliente.getCpf()) == false) {
            throw new Exception("CPF inválido");
        }

        return clienteRepository.save(cliente);
    }

    public void delete(UUID id) {
        // Deletar veiculos e telefones antes de deletar o cliente
        clienteRepository.deleteById(id);
    }

    public void deleteByCpf(String cpf) {
        clienteRepository.deleteByCpf(cpf);
    }

}
