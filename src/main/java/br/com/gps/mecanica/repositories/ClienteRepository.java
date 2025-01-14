package br.com.gps.mecanica.repositories;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import br.com.gps.mecanica.models.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteModel, UUID> {

    List<ClienteModel> findAll();

    Optional<ClienteModel> findById(UUID id);

    Optional<ClienteModel> findByCpf(String cpf);

    Optional<ClienteModel> findByEmail(String email);
    
}