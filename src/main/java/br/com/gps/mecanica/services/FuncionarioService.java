package br.com.gps.mecanica.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.gps.mecanica.enums.CargoEnum;
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

        List<EnderecoModel> enderecos = funcionario.getEnderecos();

        if (!enderecos.isEmpty() && enderecos != null) {
            List<EnderecoModel> enderecosFormatados = new ArrayList<>();

            for (EnderecoModel endereco : enderecos) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecosFormatados.add(endereco);
            }

            funcionario.setEnderecos(enderecosFormatados);
        }

        List<TelefoneModel> telefones = funcionario.getTelefones();

        if (!telefones.isEmpty() && telefones != null) {
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

        FuncionarioModel funcionarioAtual = funcionarioRepository.findById(id).get();

        String nome = funcionario.getNome();

        if (nome != null && !nome.isEmpty()) {
            funcionarioAtual.setNome(Utils.formatarString(nome));
        }

        String cpf = funcionario.getCpf();

        if (cpf != null && !cpf.isEmpty()) {
            funcionarioAtual.setCpf(Utils.formatarCpf(cpf));
        }

        String email = funcionario.getEmail();

        if (email != null && !email.isEmpty()) {
            funcionarioAtual.setEmail(Utils.formatarEmail(email));
        }

        CargoEnum cargo = funcionario.getCargo();

        if (cargo != null) {
            funcionarioAtual.setCargo(cargo);
        }

        LocalDate dataNascimento = funcionario.getDataAdmissao();

        if (dataNascimento != null && !dataNascimento.toString().isEmpty()) {
            funcionarioAtual.setDataAdmissao(dataNascimento);
        }

        List<EnderecoModel> enderecos = funcionario.getEnderecos();

        if (!enderecos.isEmpty() && enderecos != null) {
            List<EnderecoModel> enderecosFormatados = funcionarioAtual.getEnderecos();

            for (EnderecoModel endereco : enderecos) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecosFormatados.add(endereco);
            }

            funcionarioAtual.setEnderecos(enderecosFormatados);
        }

        List<TelefoneModel> telefones = funcionario.getTelefones();

        if (!telefones.isEmpty() && telefones != null) {
            List<TelefoneModel> telefonesFormatados = funcionarioAtual.getTelefones();

            for (TelefoneModel telefone : funcionario.getTelefones()) {
                Utils.formatarTelefone(telefone);
                telefonesFormatados.add(telefone);
            }

            funcionarioAtual.setTelefones(telefonesFormatados);
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
