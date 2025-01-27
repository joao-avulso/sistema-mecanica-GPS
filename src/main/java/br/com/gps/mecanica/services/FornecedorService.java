package br.com.gps.mecanica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.repositories.FornecedorRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

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

        if (enderecos != null && !enderecos.isEmpty()) {
            List<EnderecoModel> enderecosFormatados = new ArrayList<>();

            for (EnderecoModel endereco : enderecos) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecosFormatados.add(endereco);
            }

            fornecedor.setEnderecos(enderecosFormatados);
        }

        List<TelefoneModel> telefones = new ArrayList<>();

        if (telefones != null && !telefones.isEmpty()) {
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

        if (fornecedorRepository.findByNome(fornecedor.getNome()).size() != 0) {
            throw new Exception("Nome já cadastrado");
        }

        return fornecedorRepository.save(fornecedor);
    }

    public FornecedorModel update(UUID id, FornecedorModel fornecedor) throws Exception {
        FornecedorModel fornecedorAtual = fornecedorRepository.findById(id).get();
        fornecedor.setNome(Utils.formatarString(fornecedor.getNome()));
        fornecedor.setCnpj(Utils.formatarCnpj(fornecedor.getCnpj()));
        fornecedor.setEmail(Utils.formatarEmail(fornecedor.getEmail()));

        if (!Utils.verificarEmail(fornecedor.getEmail())) {
            throw new Exception("Email inválido");
        }

        if (!Utils.verificarCnpj(fornecedor.getCnpj())) {
            throw new Exception("CNPJ inválido");
        }

        String nome = fornecedor.getNome();

        if (nome != null && nome != fornecedorAtual.getNome()) {
            fornecedorAtual.setNome(Utils.formatarString(nome));
        }

        String cnpj = fornecedor.getCnpj();

        if (cnpj != null && !cnpj.equals(fornecedorAtual.getCnpj())) {
            if (fornecedorRepository.existsByCnpj(cnpj)) {
                throw new Exception("CNPJ já cadastrado");
            }

            if (!Utils.verificarCnpj(cnpj)) {
                throw new Exception("CNPJ inválido");
            }

            fornecedorAtual.setCnpj(Utils.formatarCnpj(cnpj));
        }

        String email = fornecedor.getEmail();

        if (email != null && !email.equals(fornecedorAtual.getEmail())) {
            if (fornecedorRepository.existsByEmail(email)) {
                throw new Exception("Email já cadastrado");
            }

            if (!Utils.verificarEmail(email)) {
                throw new Exception("Email inválido");
            }
            fornecedorAtual.setEmail(Utils.formatarEmail(email));
        }

        return fornecedorRepository.save(fornecedorAtual);
    }

    public FornecedorModel addEndereco(UUID id, EnderecoModel endereco) {
        FornecedorModel fornecedor = fornecedorRepository.findById(id).get();
        List<EnderecoModel> enderecos = fornecedor.getEnderecos();
        enderecos.add(endereco);
        fornecedor.setEnderecos(enderecos);
        return fornecedorRepository.save(fornecedor);
    }

    public FornecedorModel addTelefone(UUID id, TelefoneModel telefone) {
        FornecedorModel fornecedor = fornecedorRepository.findById(id).get();
        List<TelefoneModel> telefones = fornecedor.getTelefones();
        telefones.add(telefone);
        fornecedor.setTelefones(telefones);
        return fornecedorRepository.save(fornecedor);
    }

    public void deleteEndereco(UUID id, UUID idEndereco) {
        FornecedorModel fornecedor = fornecedorRepository.findById(id).get();
        List<EnderecoModel> enderecos = fornecedor.getEnderecos();
        List<EnderecoModel> mutableEnderecos = new ArrayList<>(enderecos);
        mutableEnderecos.removeIf(mutableEndereco -> mutableEndereco.getId().equals(idEndereco));
        fornecedor.setEnderecos(mutableEnderecos);
        fornecedorRepository.save(fornecedor);
    }

    public void deleteTelefone(UUID id, UUID idTelefone) {
        FornecedorModel fornecedor = fornecedorRepository.findById(id).get();
        List<TelefoneModel> telefones = fornecedor.getTelefones();
        List<TelefoneModel> mutableTelefones = new ArrayList<>(telefones);
        mutableTelefones.removeIf(mutableTelefone -> mutableTelefone.getId().equals(idTelefone));
        fornecedor.setTelefones(mutableTelefones);
        fornecedorRepository.save(fornecedor);
    }

    public void delete(UUID id) {
        fornecedorRepository.deleteById(id);
    }

    public void delete(String cnpj) {
        fornecedorRepository.deleteByCnpj(cnpj);
    }
}
