package br.com.gps.mecanica.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows; 
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import br.com.gps.mecanica.models.ServicoModel;
import br.com.gps.mecanica.repositories.ServicoRepository;

@SpringBootTest
@Transactional // Garante que as operações no banco sejam revertidas após o teste
public class ServicoServiceTest {

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private ServicoRepository servicoRepository;

    @Test
    @Rollback // Reverte as alterações no banco após o teste
    void testGet() {
        //Testa o retorno vazio
        List<ServicoModel> result0 = servicoService.get();
        assertEquals(0, result0.size());

        //Testa o retorno com dois servicos
        ServicoModel servico1 = new ServicoModel();
        servico1.setNome("Servico 1");
        servico1.setDescricao("Descrição do Serviço 1");
        servico1.setValor(100.0);

        ServicoModel servico2 = new ServicoModel();
        servico2.setNome("Servico 2");
        servico2.setDescricao("Descrição do Serviço 2");
        servico2.setValor(200.0);

        servicoRepository.save(servico1);
        servicoRepository.save(servico2);

        List<ServicoModel> result = servicoService.get();

        assertEquals(2, result.size());
        //obs: as vezes SERVICO 1 == result.get(0) e as vezes SERVICO 2 == result.get(1)
        if (!((result.get(0).getNome() == "Servico 1" || result.get(0).getNome() == "Servico 2" ) &&
            (result.get(1).getNome() == "Servico 1" || result.get(1).getNome() == "Servico 2" ))){
            fail("ServicoService.get() estah errado");
        }
        if (!((result.get(0).getValor() == 100.0 || result.get(0).getValor() == 200.0  ) &&
            (result.get(1).getValor() == 100.0 || result.get(1).getValor() == 200.0  ))){
            fail("ServicoService.get() estah errado");
        }
        if (!(( result.get(0).getDescricao() == "Descrição do Serviço 1" || result.get(0).getDescricao() == "Descrição do Serviço 2" ) &&
            ( result.get(1).getDescricao() == "Descrição do Serviço 1" || result.get(1).getDescricao() == "Descrição do Serviço 2"  ))){
            fail("ServicoService.get() estah errado");
        }
    }

    @Test
    void testGetById() {
        // Testa quando o ID nao existe
        UUID id = UUID.randomUUID();
        Exception exception = assertThrows(Exception.class, () -> servicoService.get(id));
        assertEquals("Serviço não encontrado", exception.getMessage());  

        // Testa quando o ID existe
        ServicoModel servico = new ServicoModel();
        servico.setNome("Servico");
        servico.setDescricao("Descricao");
        servico.setValor(100.0);
        
        ServicoModel retorno = servicoRepository.save(servico);
        try {
            // Chama o serviço com o ID válido
            ServicoModel result = servicoService.get(retorno.getId());
    
            // Valida o resultado
            assertEquals("Servico", result.getNome());
        } catch (Exception e) {
            fail("Nenhuma excecao deveria ser lancada para um ID valido.");
        }
    }

    @Test
    void testGetByNome() {
        // Testa quando o nome nao existe
        List<ServicoModel> result0 = servicoService.getByNome("Servico");
        assertEquals(0, result0.size());

        // Testa quando o nome existe
        ServicoModel servico = new ServicoModel();
        servico.setNome("Servico");
        servico.setDescricao("Descrição do Serviço");
        servico.setValor(100.0);

        servicoRepository.save(servico);

        List<ServicoModel> result = servicoService.getByNome("Servico");
        assertEquals(1, result.size()); 
        assertEquals("Servico", result.get(0).getNome());
    }
    
    
    @Test
    void testGetByValor() {
        // Testa quando o nome nao existe
        List<ServicoModel> result0 = servicoService.getByValor(100.0);
        assertEquals(0, result0.size());

        // Testa quando o nome existe
        ServicoModel servico = new ServicoModel();
        servico.setNome("Servico");
        servico.setDescricao("Descrição do Serviço");
        servico.setValor(100.0);

        servicoRepository.save(servico);

        List<ServicoModel> result = servicoService.getByValor(100.0);
        assertEquals(1, result.size()); 
        assertEquals("Servico", result.get(0).getNome());
    }

    @Test
    void testCreate() {
        ServicoModel servico = new ServicoModel();
        servico.setNome("Servico");
        servico.setDescricao("Descrição do Serviço");
        servico.setValor(100.0);

        //teste a retorno da funcao
        ServicoModel retorno = servicoService.create(servico);
        assertEquals("SERVICO", retorno.getNome());
        assertEquals("DESCRICAO DO SERVICO", retorno.getDescricao());
        assertEquals(100.0, retorno.getValor());

        //testa a permanencia no BD
        Optional<ServicoModel> result = servicoRepository.findById(retorno.getId());
        assertEquals("SERVICO", result.get().getNome());
        assertEquals("DESCRICAO DO SERVICO", result.get().getDescricao());
        assertEquals(100.0, result.get().getValor());
    }

    @Test
    void testDelete() {
        // Testa quando o id nao existe
        UUID id = UUID.randomUUID();
        Exception exception = assertThrows(Exception.class, () -> servicoService.delete(id));
        assertEquals("Serviço não encontrado", exception.getMessage());  

        
        // Testa quando o id existe
        ServicoModel servico = new ServicoModel();
        servico.setNome("Servico");
        servico.setDescricao("Descrição do Serviço");
        servico.setValor(100.0);
        ServicoModel retorno = servicoRepository.save(servico);

        try {
            servicoService.delete(retorno.getId());
            assertEquals(false ,servicoRepository.existsById(retorno.getId()));  

        } catch (Exception e) {
            fail("Nenhuma excecao deveria ser lancada para um ID valido.");
        }
    }

    @Test
    void testUpdate() {
        ServicoModel servico = new ServicoModel();
        servico.setNome("Servico");
        servico.setDescricao("Descricao do Servico");
        servico.setValor(100.0);
        ServicoModel retorno = servicoRepository.save(servico);

        //testa para nome, descricao e valor nulo
        ServicoModel servicoNulo = new ServicoModel();

        try {
            ServicoModel result = servicoService.update(retorno.getId(), servicoNulo);
            assertEquals("Servico", result.getNome());
            assertEquals("Descricao do Servico", result.getDescricao());
            assertEquals(100.0, result.getValor());  

        } catch (Exception e) {
            fail("Nenhuma excecao deveria ser lancada para um ID valido.");
        }

        ServicoModel servicoUpdate = new ServicoModel();
        servicoUpdate.setNome("Servico2");
        servicoUpdate.setDescricao("Descricao do Servico2");
        servicoUpdate.setValor(200.0);

        try {
            ServicoModel result = servicoService.update(retorno.getId(), servicoUpdate);
            assertEquals("SERVICO2", result.getNome());
            assertEquals("DESCRICAO DO SERVICO2", result.getDescricao());
            assertEquals(200.0, result.getValor());  

        } catch (Exception e) {
            fail("Nenhuma excecao deveria ser lancada para um ID valido.");
        }
        


    }
}
