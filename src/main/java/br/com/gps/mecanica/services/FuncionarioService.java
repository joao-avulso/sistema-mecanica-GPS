package br.com.gps.mecanica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gps.mecanica.enums.CargoEnum;
import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.FuncionarioModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.repositories.FuncionarioRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<FuncionarioModel> get() {
        return funcionarioRepository.findAll();
    };

    public FuncionarioModel get(UUID id) throws Exception {
        if (!funcionarioRepository.existsById(id)) {
            throw new Exception("Funcionário não encontrado");
        }

        return funcionarioRepository.findById(id).get();
    };

    public FuncionarioModel get(String cpf) throws Exception {
        if (funcionarioRepository.findByCpf(cpf) == null) {
            throw new Exception("Funcionário não encontrado");
        }

        return funcionarioRepository.findByCpf(cpf);
    };

    public List<FuncionarioModel> getByNome(String nome) {
        return funcionarioRepository.findByNome(nome);
    };

    public FuncionarioModel getByEmail(String email) throws Exception {
        if (funcionarioRepository.findByEmail(email) == null) {
            throw new Exception("Funcionário não encontrado");
        }

        return funcionarioRepository.findByEmail(email);
    };

    public FuncionarioModel create(FuncionarioModel funcionario) throws Exception {
        funcionario.setNome(Utils.formatarString(funcionario.getNome()));
        funcionario.setCpf(Utils.formatarCpf(funcionario.getCpf()));
        funcionario.setEmail(Utils.formatarEmail(funcionario.getEmail()));

        List<EnderecoModel> enderecos = funcionario.getEnderecos();

        if (enderecos != null && !enderecos.isEmpty()) {
            List<EnderecoModel> enderecosFormatados = new ArrayList<>();

            for (EnderecoModel endereco : enderecos) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecosFormatados.add(endereco);
            }

            funcionario.setEnderecos(enderecosFormatados);
        }

        List<TelefoneModel> telefones = funcionario.getTelefones();

        if (telefones != null && !telefones.isEmpty()) {
            List<TelefoneModel> telefonesFormatados = new ArrayList<>();
            for (TelefoneModel telefone : telefones) {
                telefonesFormatados.add(Utils.formatarTelefone(telefone));
            }
            funcionario.setTelefones(telefonesFormatados);
        }

        if (funcionarioRepository.findByCpf(funcionario.getCpf()) != null) {
            throw new Exception("CPF já cadastrado");
        }

        if (funcionarioRepository.findByEmail(funcionario.getEmail()) != null) {
            throw new Exception("Email já cadastrado");
        }

        if (Utils.verificarEmail(Utils.formatarEmail(funcionario.getEmail())) == false) {
            throw new Exception("Email inválido");
        }

        if (Utils.verificarCpf(Utils.formatarCpf(funcionario.getCpf())) == false) {
            throw new Exception("CPF inválido");
        }

        return funcionarioRepository.save(funcionario);
    };

    public FuncionarioModel update(UUID id, FuncionarioModel funcionario) throws Exception {
        if (!funcionarioRepository.existsById(id)) {
            throw new Exception("Funcionário não encontrado");
        }

        if (!Utils.verificarCpf(funcionario.getCpf())) {
            throw new Exception("CPF inválido");
        }

        if (!Utils.verificarEmail(funcionario.getEmail())) {
            throw new Exception("Email inválido");
        }

        FuncionarioModel funcionarioAtual = funcionarioRepository.findById(id).get();

        String nome = funcionario.getNome();

        if (nome != null && !nome.equals(funcionarioAtual.getNome())) {
            funcionarioAtual.setNome(Utils.formatarString(nome));
        }

        String cpf = funcionario.getCpf();

        if (cpf != null && !cpf.equals(funcionarioAtual.getCpf())) {
            if (!Utils.verificarCpf(cpf)) {
                throw new Exception("CPF inválido");
            }

            if (!funcionarioRepository.existsByCpf(cpf)) {
                throw new Exception("CPF já cadastrado");
            }

            funcionarioAtual.setCpf(Utils.formatarCpf(cpf));
        }

        String email = funcionario.getEmail();

        if (email != null && !email.equals(funcionarioAtual.getEmail())) {
            if (!Utils.verificarEmail(email)) {
                throw new Exception("Email inválido");
            }

            if (!funcionarioRepository.existsByEmail(email)) {
                throw new Exception("Email já cadastrado");
            }

            funcionarioAtual.setEmail(Utils.formatarEmail(email));
        }

        CargoEnum cargo = funcionario.getCargo();

        if (cargo != null && !cargo.equals(funcionarioAtual.getCargo())) {
            funcionarioAtual.setCargo(cargo);
        }

        List<EnderecoModel> enderecos = funcionario.getEnderecos();

        if (enderecos != null) {
            List<EnderecoModel> enderecosFormatados = new ArrayList<>();

            for (EnderecoModel endereco : enderecos) {
                if (endereco.equals(funcionarioAtual.getEnderecos().get(0))) {
                    continue;
                } else {
                    endereco.setCep(Utils.formatarCep(endereco.getCep()));
                    Utils.formatarEndereco(endereco);
                    enderecosFormatados.add(endereco);
                }
            }

            funcionarioAtual.setEnderecos(enderecosFormatados);
        }

        List<TelefoneModel> telefones = funcionario.getTelefones();

        if (!telefones.isEmpty() && telefones != null) {
            List<TelefoneModel> telefonesFormatados = new ArrayList<>();

            for (TelefoneModel telefone : funcionario.getTelefones()) {
                telefonesFormatados.add(Utils.formatarTelefone(telefone));
            }

            funcionarioAtual.setTelefones(telefonesFormatados);
        }

        return funcionarioRepository.save(funcionarioAtual);
    };

    public void delete(UUID id) throws Exception {
        if (!funcionarioRepository.existsById(id)) {
            throw new Exception("Funcionário não encontrado");
        }

        funcionarioRepository.deleteById(id);
    };

    public void delete(String cpf) throws Exception {
        if (funcionarioRepository.findByCpf(cpf) == null) {
            throw new Exception("Funcionário não encontrado");
        }

        funcionarioRepository.deleteByCpf(cpf);
    };
}
