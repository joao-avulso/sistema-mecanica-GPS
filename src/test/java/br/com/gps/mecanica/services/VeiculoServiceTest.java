package br.com.gps.mecanica.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.gps.mecanica.enums.CorEnum;
import br.com.gps.mecanica.enums.PessoaEnum;
import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.VeiculoModel;
import br.com.gps.mecanica.repositories.ClienteRepository;
import br.com.gps.mecanica.repositories.VeiculoRepository;

@SpringBootTest
@Transactional 
public class VeiculoServiceTest {

    @Autowired
    VeiculoService veiculoService;

    @Autowired
    VeiculoRepository veiculoRepository;  

    @Autowired
    ClienteRepository clienteRepository;   

    @Test
    void testCreate() {

        VeiculoModel veiculo1 = new VeiculoModel(
                "JJI-9973",
                "Civic",
                "Honda",
                2020,
                CorEnum.PRETO,
                null 
        );

        try {
            VeiculoModel result = veiculoService.create(veiculo1);
            assertEquals("CIVIC", veiculoService.get(result.getId()).getModelo());  

        } catch (Exception e) {
            fail("Nao foi possivel criar o Veiculo no testCreate do VeiculoServiceTest");
        }
    }

    @Test
    void testUpdate() {

        VeiculoModel veiculo1 = new VeiculoModel(
                "JJI-9973",
                "Civic",
                "Honda",
                2020,
                CorEnum.PRETO,
                null 
        );

        ClienteModel cliente = new ClienteModel(
            PessoaEnum.FISICA,
            "Joao Silva",
            "947.043.440-45",
            "joao.silva@example.com",
            null, null, null);

        clienteRepository.save(cliente);

        VeiculoModel veiculo2 = new VeiculoModel(
            "HSB-7397",
            "Corolla",
            "Toyota",
            2019,
            CorEnum.BRANCO,
            cliente);
            
        try {
            VeiculoModel result = veiculoService.create(veiculo1);
            VeiculoModel result2 = veiculoService.update(result.getId(), veiculo2);

            assertEquals("HSB7397", veiculoService.get(result2.getId()).getPlaca()); 
            assertEquals("COROLLA", veiculoService.get(result2.getId()).getModelo()); 
            assertEquals("TOYOTA", veiculoService.get(result2.getId()).getMarca()); 
            assertEquals(2019, veiculoService.get(result2.getId()).getAno()); 
            assertEquals(CorEnum.BRANCO, veiculoService.get(result2.getId()).getCor()); 
            assertEquals(cliente, veiculoService.get(result2.getId()).getCliente()); 

        } catch (Exception e) {
            fail("Nao foi possivel fazer o update do Veiculo no testUpdate do VeiculoServiceTest");
        }
    }
}
