package br.com.gps.mecanica.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.repositories.ClienteRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }
    
    public List<ClienteModel> get(){
        return clienteRepository.findAll();
    };

    public ClienteModel get(UUID id){
        return clienteRepository.findById(id).get();
    };
    
    public ClienteModel get(String cpf){
        return clienteRepository.findByCpf(cpf);
    };
    
    public ClienteModel getByEmail(String email){
        return clienteRepository.findByEmail(email);
    };
    
    public ClienteModel getByVeiculo(List<VeiculoModel> veiculos){
        return clienteRepository.findByVeiculos(veiculos);
    }

    public ClienteModel create(ClienteModel cliente) throws Exception {

        if (clienteRepository.findByCpf(cliente.getCpf()) != null) {
            throw new Exception("CPF já cadastrado");
        }

        if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new Exception("Email já cadastrado");
        }

        if (Utils.verificar_email(Utils.formatar_email(cliente.getEmail())) == false) {
            throw new Exception("Email inválido");
        }

        if (Utils.verificar_cpf(Utils.formatar_cpf(cliente.getCpf())) == false) {
            throw new Exception("CPF inválido");
        }
        
        cliente.setNome(Utils.formatar_string(cliente.getNome()));
        cliente.setCpf(Utils.formatar_cpf(cliente.getCpf()));
        cliente.setEmail(Utils.formatar_email(cliente.getEmail()));
        cliente.setCep(Utils.formatar_cep(cliente.getCep()));
        cliente.setRua(Utils.formatar_string(cliente.getRua()));
        cliente.setBairro(Utils.formatar_string(cliente.getBairro()));
        cliente.setComplemento(Utils.formatar_string(cliente.getComplemento()));
        cliente.setCidade(Utils.formatar_string(cliente.getCidade()));
        cliente.setEstado(Utils.formatar_string(cliente.getEstado()));
        cliente.setReferencia(Utils.formatar_string(cliente.getReferencia()));

        return clienteRepository.save(cliente);
    }
    
    public ClienteModel update(UUID id, ClienteModel cliente) throws Exception {

        if (clienteRepository.findByCpf(cliente.getCpf()) != null) {
            throw new Exception("CPF já cadastrado");
        }

        if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new Exception("Email já cadastrado");
        }

        if (Utils.verificar_email(cliente.getEmail()) == false) {
            throw new Exception("Email inválido");
        }

        if (Utils.verificar_cpf(cliente.getCpf()) == false) {
            throw new Exception("CPF inválido");
        }
        
        cliente.setNome(Utils.formatar_string(cliente.getNome()));
        cliente.setCpf(Utils.formatar_cpf(cliente.getCpf()));
        cliente.setEmail(Utils.formatar_email(cliente.getEmail()));
        cliente.setCep(Utils.formatar_cep(cliente.getCep()));
        cliente.setRua(Utils.formatar_string(cliente.getRua()));
        cliente.setBairro(Utils.formatar_string(cliente.getBairro()));
        cliente.setComplemento(Utils.formatar_string(cliente.getComplemento()));
        cliente.setCidade(Utils.formatar_string(cliente.getCidade()));
        cliente.setEstado(Utils.formatar_string(cliente.getEstado()));
        cliente.setReferencia(Utils.formatar_string(cliente.getReferencia()));

        cliente.setId(id);
        return clienteRepository.save(cliente);
    }
   
    public void delete(UUID id){
        // Deletar veiculos e telefones antes de deletar o cliente
        clienteRepository.deleteById(id);
    }

}
