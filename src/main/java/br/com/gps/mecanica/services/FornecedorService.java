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

        if (!fornecedor.getEnderecos().isEmpty()) {
            List<EnderecoModel> enderecos = new ArrayList<>();

            for (EnderecoModel endereco : fornecedor.getEnderecos()) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecos.add(endereco);
            }

            fornecedor.setEnderecos(enderecos);
        }

        if (!fornecedor.getTelefones().isEmpty()) {
            List<TelefoneModel> telefones = new ArrayList<>();
            for (TelefoneModel telefone : fornecedor.getTelefones()) {
                telefones.add(Utils.formatarTelefone(telefone));
            }
            fornecedor.setTelefones(telefones);
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

        if (fornecedor.getNome() != null && !fornecedor.getNome().isEmpty()) {
            fornecedorAtual.setNome(Utils.formatarString(fornecedor.getNome()));
        }

        if (fornecedor.getCnpj() != null && !fornecedor.getCnpj().isEmpty()) {
            fornecedorAtual.setCnpj(Utils.formatarCnpj(fornecedor.getCnpj()));
        }

        if (fornecedor.getEmail() != null && !fornecedor.getEmail().isEmpty()) {
            fornecedorAtual.setEmail(Utils.formatarEmail(fornecedor.getEmail()));
        }

        if (!fornecedor.getEnderecos().isEmpty()) {
            List<EnderecoModel> enderecos = fornecedorAtual.getEnderecos();

            for (EnderecoModel endereco : fornecedor.getEnderecos()) {
                endereco.setCep(Utils.formatarCep(endereco.getCep()));
                Utils.formatarEndereco(endereco);
                enderecos.add(endereco);
            }

            fornecedor.setEnderecos(enderecos);
        }

        if (!fornecedor.getTelefones().isEmpty()) {
            List<TelefoneModel> telefones = fornecedorAtual.getTelefones();
            for (TelefoneModel telefone : fornecedor.getTelefones()) {
                telefones.add(Utils.formatarTelefone(telefone));
            }
            fornecedor.setTelefones(telefones);
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
