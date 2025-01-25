package br.com.gps.mecanica.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.ServicoModel;
import br.com.gps.mecanica.repositories.ServicoRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public List<ServicoModel> get() {
        return servicoRepository.findAll();
    }

    public ServicoModel get(UUID id) throws Exception {
        if (!servicoRepository.existsById(id)) {
            throw new Exception("Serviço não encontrado");
        }

        return servicoRepository.findById(id).get();
    }

    public List<ServicoModel> getByNome(String nome) {
        return servicoRepository.findByNome(nome);
    }

    public List<ServicoModel> getByValor(Double valor) {
        return servicoRepository.findByValor(valor);
    }

    public ServicoModel create(ServicoModel servico) {
        servico.setNome(Utils.formatarString(servico.getNome()));
        servico.setDescricao(Utils.formatarString(servico.getDescricao()));
        servico.setValor(servico.getValor());
        return servicoRepository.save(servico);
    }

    public void delete(UUID id) throws Exception {
        if (!servicoRepository.existsById(id)) {
            throw new Exception("Serviço não encontrado");
        }

        servicoRepository.deleteById(id);
    }

    public ServicoModel update(UUID id, ServicoModel servico) throws Exception {
        if (!servicoRepository.existsById(id)) {
            throw new Exception("Serviço não encontrado");
        }

        ServicoModel servicoAtual = servicoRepository.findById(id).get();

        String nome = servico.getNome();

        if (nome != null && !nome.isEmpty()) {
            servicoAtual.setNome(Utils.formatarString(nome));
        }

        String descricao = servico.getDescricao();

        if (descricao != null && !descricao.isEmpty()) {
            servicoAtual.setDescricao(Utils.formatarString(descricao));
        }

        Double valor = servico.getValor();

        if (valor != null) {
            servicoAtual.setValor(valor);
        }

        return servicoRepository.save(servicoAtual);
    }
}
