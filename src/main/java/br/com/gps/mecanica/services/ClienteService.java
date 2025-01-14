package br.com.gps.mecanica.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.repositories.ClienteRepository;

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
    
    /* 
    List<VeiculoModel> getVeiculos(String id){
        return clienteRepository. ??? ;

    }
    */

    public ClienteModel create(ClienteModel cliente){
        return clienteRepository.save(cliente);
    }
    
    public ClienteModel update(UUID id, ClienteModel cliente){
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }
   
    public void delete(UUID id){
        // Deletar veiculos e telefones antes de deletar o cliente
        clienteRepository.deleteById(id);
    }

}
