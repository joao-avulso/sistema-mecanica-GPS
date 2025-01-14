package br.com.gps.mecanica.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.ServicoModel;
import br.com.gps.mecanica.repositories.ServicosRepository;
import br.com.gps.mecanica.utils.Utils;

@Service
public class ServicoService {

    final ServicosRepository servicoRepository;

    public ServicoService(ServicosRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<ServicoModel> get() {
        return servicoRepository.findAll();
    }

    public ServicoModel get(UUID id) {
        return servicoRepository.findById(id).get();
    }

    public List<ServicoModel> getByNome(String nome) {
        try {
            return servicoRepository.findByNome(nome);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ServicoModel> getByValor(Double valor) {
        try {
            return servicoRepository.findByValor(valor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ServicoModel create(ServicoModel servico) {
        try {
            servico.setNome(Utils.formatar_string(servico.getNome()));
            servico.setDescricao(Utils.formatar_string(servico.getDescricao()));
            return servicoRepository.save(servico);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(UUID id) {
        try {
            servicoRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServicoModel update(ServicoModel servico) {
        try {
            servico.setNome(Utils.formatar_string(servico.getNome()));
            servico.setDescricao(Utils.formatar_string(servico.getDescricao()));
            return servicoRepository.save(servico);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
