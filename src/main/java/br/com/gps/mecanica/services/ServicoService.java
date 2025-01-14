package br.com.gps.mecanica.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.gps.mecanica.models.ServicoModel;
import br.com.gps.mecanica.repositories.ServicosRepository;

@Service
public class ServicoService {

    private ServicoModel formatar_dados(ServicoModel service) {
        Map<String, String> caracteresEspeciais = new HashMap<>() {
            {
                put("á", "a");
                put("à", "a");
                put("ã", "a");
                put("â", "a");
                put("é", "e");
                put("ê", "e");
                put("í", "i");
                put("ó", "o");
                put("ô", "o");
                put("õ", "o");
                put("ú", "u");
                put("ç", "c");
                put("Á", "a");
                put("À", "a");
                put("Ã", "a");
                put("Â", "a");
                put("É", "e");
                put("Ê", "e");
                put("Í", "i");
                put("Ó", "o");
                put("Ô", "o");
                put("Õ", "o");
                put("Ú", "u");
                put("Ç", "c");
            }
        };

        for (Map.Entry<String, String> entry : caracteresEspeciais.entrySet()) {
            service.setDescricao(service.getDescricao().replace(entry.getKey(), entry.getValue()));
            service.setNome(service.getNome().replace(entry.getKey(), entry.getValue()));
        }

        service.setDescricao(service.getDescricao().toUpperCase());
        service.setNome(service.getNome().toUpperCase());

        return service;
    }

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
            servico = formatar_dados(servico);
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
            servico = formatar_dados(servico);
            return servicoRepository.save(servico);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
