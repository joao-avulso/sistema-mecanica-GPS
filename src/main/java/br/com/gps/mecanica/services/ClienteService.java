package br.com.gps.mecanica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.repositories.ClienteRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteModel> get() {
        return clienteRepository.findAll();
    };

    public ClienteModel get(UUID id) throws Exception {
        if (!clienteRepository.existsById(id)) {
            throw new Exception("Cliente não encontrado");
        }

        return clienteRepository.findById(id).get();
    };

    public ClienteModel get(String cpf) throws Exception {
        if (clienteRepository.findByCpf(cpf) == null) {
            throw new Exception("Cliente não encontrado");
        }

        return clienteRepository.findByCpf(cpf);
    };

    public List<ClienteModel> getByNome(String nome) {
        return clienteRepository.findByNome(nome);
    };

    public ClienteModel getByEmail(String email) throws Exception {
        if (clienteRepository.findByEmail(email) == null) {
            throw new Exception("Cliente não encontrado");
        }
        return clienteRepository.findByEmail(email);
    };

    public ClienteModel getByVeiculo(List<VeiculoModel> veiculos) {
        return clienteRepository.findByVeiculos(veiculos);
    };

    public ClienteModel create(ClienteModel cliente) throws Exception {
        cliente.setNome(cliente.getNome().toUpperCase());
        cliente.setCpf(Utils.formatarCpf(cliente.getCpf()));
        cliente.setEmail(Utils.formatarEmail(cliente.getEmail()));

        List<EnderecoModel> enderecos = cliente.getEnderecos();

        if (enderecos != null && !enderecos.isEmpty()) {
            List<EnderecoModel> enderecosFormatados = new ArrayList<>();

            for (EnderecoModel endereco : enderecos) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecosFormatados.add(endereco);
            }

            cliente.setEnderecos(enderecosFormatados);
        }

        List<TelefoneModel> telefones = cliente.getTelefones();

        if (telefones != null && !telefones.isEmpty()) {
            List<TelefoneModel> telefonesFormatados = new ArrayList<>();

            for (TelefoneModel telefone : telefones) {
                Utils.formatarTelefone(telefone);
                telefonesFormatados.add(telefone);
            }

            cliente.setTelefones(telefonesFormatados);
        }

        List<VeiculoModel> veiculos = cliente.getVeiculos();

        if (veiculos != null && !veiculos.isEmpty()) {
            List<VeiculoModel> veiculosFormatados = new ArrayList<>();

            for (VeiculoModel veiculo : veiculos) {
                Utils.formatarVeiculo(veiculo);
                veiculosFormatados.add(veiculo);
            }

            cliente.setVeiculos(veiculosFormatados);
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
    };

    public ClienteModel update(UUID id, ClienteModel cliente) throws Exception {
        if (!clienteRepository.existsById(id)) {
            throw new Exception("Cliente não encontrado");
        }

        if (Utils.verificarEmail(cliente.getEmail()) == false) {
            throw new Exception("Email inválido");
        }

        if (Utils.verificarCpf(cliente.getCpf()) == false) {
            throw new Exception("CPF inválido");
        }

        ClienteModel clienteAtual = clienteRepository.findById(id).get();

        String nome = cliente.getNome();

        if (nome != null && nome != clienteAtual.getNome()) {
            clienteAtual.setNome(Utils.formatarNome(nome));
        }

        String cpf = cliente.getCpf();

        if (cpf != null && !cpf.equals(clienteAtual.getCpf())) {
            if (clienteRepository.existsByCpf(cpf)) {
                throw new Exception("CPF já cadastrado");
            }

            if (!Utils.verificarCpf(Utils.formatarCpf(cpf))) {
                throw new Exception("CPF inválido");
            }
            clienteAtual.setCpf(Utils.formatarCpf(cpf));
        }

        String email = cliente.getEmail();

        if (email != null && !email.equals(clienteAtual.getEmail())) {
            if (clienteRepository.existsByEmail(email)) {
                throw new Exception("Email já cadastrado");
            }

            if (!Utils.verificarEmail(Utils.formatarEmail(email))) {
                throw new Exception("Email inválido");
            }
            clienteAtual.setEmail(Utils.formatarEmail(email));
        }

        return clienteRepository.save(clienteAtual);
    };

    public ClienteModel addEndereco(UUID id, EnderecoModel endereco) throws Exception {
        if (!clienteRepository.existsById(id)) {
            throw new Exception("Cliente não encontrado");
        }

        ClienteModel cliente = clienteRepository.findById(id).get();
        List<EnderecoModel> enderecos = cliente.getEnderecos();

        if (enderecos == null) {
            enderecos = new ArrayList<>();
        }

        endereco.setCep(Utils.formatarCep(endereco.getCep()));
        Utils.formatarEndereco(endereco);
        enderecos.add(endereco);
        cliente.setEnderecos(enderecos);

        return clienteRepository.save(cliente);
    };

    public ClienteModel addTelefone(UUID id, TelefoneModel telefone) throws Exception {
        if (!clienteRepository.existsById(id)) {
            throw new Exception("Cliente não encontrado");
        }

        ClienteModel cliente = clienteRepository.findById(id).get();
        List<TelefoneModel> telefones = cliente.getTelefones();

        if (telefones == null) {
            telefones = new ArrayList<>();
        }

        Utils.formatarTelefone(telefone);
        telefones.add(telefone);
        cliente.setTelefones(telefones);

        return clienteRepository.save(cliente);
    };

    public ClienteModel addVeiculo(UUID id, VeiculoModel veiculo) throws Exception {
        if (!clienteRepository.existsById(id)) {
            throw new Exception("Cliente não encontrado");
        }

        ClienteModel cliente = clienteRepository.findById(id).get();
        List<VeiculoModel> veiculos = cliente.getVeiculos();

        if (veiculos == null) {
            veiculos = new ArrayList<>();
        }

        Utils.formatarVeiculo(veiculo);
        veiculos.add(veiculo);
        cliente.setVeiculos(veiculos);

        return clienteRepository.save(cliente);
    };

    public void deleteEndereco(UUID id, UUID idEndereco) throws Exception {
        if (!clienteRepository.existsById(id)) {
            throw new Exception("Cliente não encontrado");
        }

        ClienteModel cliente = clienteRepository.findById(id).get();
        List<EnderecoModel> enderecos = cliente.getEnderecos();

        if (enderecos == null || enderecos.isEmpty()) {
            throw new Exception("Endereço não encontrado");
        }

        for (EnderecoModel endereco : enderecos) {
            if (endereco.getId().equals(idEndereco)) {
                enderecos.remove(endereco);
                cliente.setEnderecos(enderecos);
                clienteRepository.save(cliente);
                return;
            }
        }

        throw new Exception("Endereço não encontrado");
    };

    public void deleteTelefone(UUID id, UUID idTelefone) throws Exception {
        if (!clienteRepository.existsById(id)) {
            throw new Exception("Cliente não encontrado");
        }

        ClienteModel cliente = clienteRepository.findById(id).get();
        List<TelefoneModel> telefones = cliente.getTelefones();

        if (telefones == null || telefones.isEmpty()) {
            throw new Exception("Telefone não encontrado");
        }

        for (TelefoneModel telefone : telefones) {
            if (telefone.getId().equals(idTelefone)) {
                telefones.remove(telefone);
                cliente.setTelefones(telefones);
                clienteRepository.save(cliente);
                return;
            }
        }

        throw new Exception("Telefone não encontrado");
    };

    public void deleteVeiculo(UUID id, UUID idVeiculo) throws Exception {
        if (!clienteRepository.existsById(id)) {
            throw new Exception("Cliente não encontrado");
        }

        ClienteModel cliente = clienteRepository.findById(id).get();
        List<VeiculoModel> veiculos = cliente.getVeiculos();

        if (veiculos == null || veiculos.isEmpty()) {
            throw new Exception("Veículo não encontrado");
        }

        for (VeiculoModel veiculo : veiculos) {
            if (veiculo.getId().equals(idVeiculo)) {
                veiculos.remove(veiculo);
                cliente.setVeiculos(veiculos);
                clienteRepository.save(cliente);
                return;
            }
        }

        throw new Exception("Veículo não encontrado");
    };

    public void delete(UUID id) throws Exception {
        if (!clienteRepository.existsById(id)) {
            throw new Exception("Cliente não encontrado");
        }
        // Deletar veiculos e telefones antes de deletar o cliente
        clienteRepository.deleteById(id);
    };

    public void deleteByCpf(String cpf) throws Exception {
        if (clienteRepository.findByCpf(cpf) == null) {
            throw new Exception("Cliente não encontrado");
        }

        clienteRepository.deleteByCpf(cpf);
    };

}
