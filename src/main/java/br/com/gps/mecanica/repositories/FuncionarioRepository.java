package br.com.gps.mecanica.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gps.mecanica.enums.CargoEnum;
import br.com.gps.mecanica.models.FuncionarioModel;

public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, UUID> {
    FuncionarioModel findByCpf(String cpf);

    List<FuncionarioModel> findByNome(String nome);

    FuncionarioModel findByEmail(String email);

    List<FuncionarioModel> findByCargo(CargoEnum cargo);

    void deleteByCpf(String cpf);
}
