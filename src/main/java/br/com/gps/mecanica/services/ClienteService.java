package br.com.gps.mecanica.services;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<ClienteModel> get(){
        return clienteRepository.findAll();
    };

    public Optional<ClienteModel> get(UUID id){
        return clienteRepository.findById(id);
    };
    
    public Optional<ClienteModel> get(String cpf){
        return clienteRepository.findByCpf(cpf);
    };
    
    public Optional<ClienteModel> getByEmail(String email){
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
        clienteRepository.deleteById(id);
    }

}
