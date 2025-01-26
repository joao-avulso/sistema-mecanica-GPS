package br.com.gps.mecanica.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
 
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.repositories.ProdutoRepository;

@SpringBootTest
@Transactional // Garante que as operações no banco sejam revertidas após cada método de teste
public class ProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void testGet() {
        //Testa o retorno vazio
        List<ProdutoModel> result0 = produtoService.get();
        assertEquals(0, result0.size());

        ProdutoModel produto1 = new ProdutoModel(
                "Óleo de Motor",
                "Óleo sintético para motores a gasolina",
                50.0,
                35.0,
                200,
                null);
                
            
        produtoRepository.save(produto1);

        ProdutoModel produto2 = new ProdutoModel(
                "Filtro de Ar",
                "Filtro de ar de alta eficiência para veículos compactos",
                30.0,
                20.0,
                150,
                null);
        
        produtoRepository.save(produto2);

        //Testa o retorno vazio
        List<ProdutoModel> result1 = produtoService.get();
        assertEquals(2, result1.size());
    }
}