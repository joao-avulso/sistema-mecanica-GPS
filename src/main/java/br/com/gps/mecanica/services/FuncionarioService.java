package br.com.gps.mecanica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.FuncionarioModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.repositories.FuncionarioRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class FuncionarioService {
    final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    };

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

        if (!funcionario.getEnderecos().isEmpty()) {
            List<EnderecoModel> enderecos = new ArrayList<>();

            for (EnderecoModel endereco : funcionario.getEnderecos()) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecos.add(endereco);
            }

            funcionario.setEnderecos(enderecos);
        }

        if (!funcionario.getTelefones().isEmpty()) {
            List<TelefoneModel> telefones = new ArrayList<>();

            for (TelefoneModel telefone : funcionario.getTelefones()) {
                Utils.formatarTelefone(telefone);
                telefones.add(telefone);
            }

            funcionario.setTelefones(telefones);
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

        FuncionarioModel funcionarioAtual = funcionarioRepository.findById(id).get();

        if (funcionario.getNome() != null && !funcionario.getNome().isEmpty()) {
            funcionarioAtual.setNome(Utils.formatarString(funcionario.getNome()));
        }

        if (funcionario.getCpf() != null && !funcionario.getCpf().isEmpty()) {
            funcionarioAtual.setCpf(Utils.formatarCpf(funcionario.getCpf()));
        }

        if (funcionario.getEmail() != null && !funcionario.getEmail().isEmpty()) {
            funcionarioAtual.setEmail(Utils.formatarEmail(funcionario.getEmail()));
        }

        if (funcionario.getCargo() != null) {
            funcionarioAtual.setCargo(funcionario.getCargo());
        }

        if (funcionario.getDataAdmissao() != null && !funcionario.getDataAdmissao().toString().isEmpty()) {
            funcionarioAtual.setDataAdmissao(funcionario.getDataAdmissao());
        }

        if (!funcionario.getEnderecos().isEmpty() && funcionario.getEnderecos() != null) {
            List<EnderecoModel> enderecos = funcionarioAtual.getEnderecos();

            for (EnderecoModel endereco : funcionario.getEnderecos()) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecos.add(endereco);
            }

            funcionarioAtual.setEnderecos(enderecos);
        }

        if (!funcionario.getTelefones().isEmpty() && funcionario.getTelefones() != null) {
            List<TelefoneModel> telefones = funcionarioAtual.getTelefones();

            for (TelefoneModel telefone : funcionario.getTelefones()) {
                Utils.formatarTelefone(telefone);
                telefones.add(telefone);
            }

            funcionarioAtual.setTelefones(telefones);
        }

        if (funcionarioRepository.findByCpf(funcionario.getCpf()) != null
                && !funcionarioAtual.getCpf().equals(funcionario.getCpf())) {
            throw new Exception("CPF já cadastrado");
        }

        if (funcionarioRepository.findByEmail(funcionario.getEmail()) != null
                && !funcionarioAtual.getEmail().equals(funcionario.getEmail())) {
            throw new Exception("Email já cadastrado");
        }

        if (Utils.verificarEmail(Utils.formatarEmail(funcionario.getEmail())) == false) {
            throw new Exception("Email inválido");
        }

        if (Utils.verificarCpf(Utils.formatarCpf(funcionario.getCpf())) == false) {
            throw new Exception("CPF inválido");
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
