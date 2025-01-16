package br.com.gps.mecanica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.repositories.FornecedorRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class FornecedorService {
    final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<FornecedorModel> get() {
        return fornecedorRepository.findAll();
    }

    public FornecedorModel get(UUID id) {
        return fornecedorRepository.findById(id).get();
    }

    public FornecedorModel get(String cnpj) {
        return fornecedorRepository.findByCnpj(cnpj);
    }

    public List<FornecedorModel> getByNome(String nome) {
        return fornecedorRepository.findByNome(nome);
    }

    public FornecedorModel getByEmail(String email) {
        return fornecedorRepository.findByEmail(email);
    }

    public FornecedorModel create(FornecedorModel fornecedor) throws Exception {

        fornecedor.setNome(Utils.formatarString(fornecedor.getNome()));
        fornecedor.setCnpj(Utils.formatarCnpj(fornecedor.getCnpj()));
        fornecedor.setEmail(Utils.formatarEmail(fornecedor.getEmail()));

        List<EnderecoModel> enderecos = new ArrayList<>();

        if (!enderecos.isEmpty() && enderecos != null) {
            List<EnderecoModel> enderecosFormatados = new ArrayList<>();

            for (EnderecoModel endereco : enderecos) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecosFormatados.add(endereco);
            }

            fornecedor.setEnderecos(enderecosFormatados);
        }

        List<TelefoneModel> telefones = new ArrayList<>();

        if (!telefones.isEmpty() && telefones != null) {
            List<TelefoneModel> telefonesFormatados = new ArrayList<>();
            for (TelefoneModel telefone : telefones) {
                telefonesFormatados.add(Utils.formatarTelefone(telefone));
            }
            fornecedor.setTelefones(telefonesFormatados);
        }

        if (Utils.verificarEmail(fornecedor.getEmail()) == false) {
            throw new Exception("Email inválido");
        }

        if (Utils.verificarCnpj(fornecedor.getCnpj()) == false) {
            throw new Exception("CNPJ inválido");
        }

        if (fornecedorRepository.findByCnpj(fornecedor.getCnpj()) != null) {
            throw new Exception("CNPJ já cadastrado");
        }

        if (fornecedorRepository.findByEmail(fornecedor.getEmail()) != null) {
            throw new Exception("Email já cadastrado");
        }

        if (fornecedorRepository.findByNome(fornecedor.getNome()) != null) {
            throw new Exception("Nome já cadastrado");
        }

        return fornecedorRepository.save(fornecedor);
    }

    public FornecedorModel update(UUID id, FornecedorModel fornecedor) throws Exception {
        FornecedorModel fornecedorAtual = fornecedorRepository.findById(id).get();

        String nome = fornecedor.getNome();

        if (nome != null && !nome.isEmpty()) {
            fornecedorAtual.setNome(Utils.formatarString(nome));
        }

        String cnpj = fornecedor.getCnpj();

        if (cnpj != null && !cnpj.isEmpty()) {
            fornecedorAtual.setCnpj(Utils.formatarCnpj(cnpj));
        }

        String email = fornecedor.getEmail();

        if (email != null && !email.isEmpty()) {
            fornecedorAtual.setEmail(Utils.formatarEmail(email));
        }

        List<EnderecoModel> enderecos = fornecedor.getEnderecos();

        if (!enderecos.isEmpty() && enderecos != null) {
            List<EnderecoModel> enderecosFormatados = fornecedorAtual.getEnderecos();

            for (EnderecoModel endereco : enderecos) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecosFormatados.add(endereco);
            }

            fornecedor.setEnderecos(enderecosFormatados);
        }

        List<TelefoneModel> telefones = fornecedor.getTelefones();

        if (!telefones.isEmpty() && telefones != null) {
            List<TelefoneModel> telefonesFormatados = fornecedorAtual.getTelefones();
            for (TelefoneModel telefone : telefones) {
                telefonesFormatados.add(Utils.formatarTelefone(telefone));
            }
            fornecedor.setTelefones(telefonesFormatados);
        }

        if (Utils.verificarEmail(fornecedor.getEmail()) == false) {
            throw new Exception("Email inválido");
        }

        if (Utils.verificarCnpj(fornecedor.getCnpj()) == false) {
            throw new Exception("CNPJ inválido");
        }

        if (fornecedorRepository.findByCnpj(fornecedor.getCnpj()) != null) {
            throw new Exception("CNPJ já cadastrado");
        }

        if (fornecedorRepository.findByEmail(fornecedor.getEmail()) != null) {
            throw new Exception("Email já cadastrado");
        }

        if (fornecedorRepository.findByNome(fornecedor.getNome()) != null) {
            throw new Exception("Nome já cadastrado");
        }

        return fornecedorRepository.save(fornecedor);
    }

    public void delete(UUID id) {
        fornecedorRepository.deleteById(id);
    }

    public void delete(String cnpj) {
        fornecedorRepository.deleteByCnpj(cnpj);
    }
}
