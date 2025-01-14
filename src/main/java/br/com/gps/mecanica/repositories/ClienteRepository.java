package br.com.gps.mecanica.repositories;

import java.util.UUID;

import br.com.gps.mecanica.models.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteModel, UUID> {

    ClienteModel findByCpf(String cpf);

    ClienteModel findByEmail(String email);
}