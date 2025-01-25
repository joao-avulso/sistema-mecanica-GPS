package br.com.gps.mecanica.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List; 

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
 
import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.enums.ContatoEnum;
import br.com.gps.mecanica.enums.PessoaEnum; 
import br.com.gps.mecanica.repositories.FornecedorRepository;

@SpringBootTest
@Transactional // Garante que as operações no banco sejam revertidas após cada método de teste
public class FornecedorServiceTest {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private FornecedorRepository fornecedorRepository;



    @Test
    void testGet() {
        //Testa o retorno vazio
        List<FornecedorModel> result0 = fornecedorService.get();
        assertEquals(0, result0.size());


        //FORNECEDOR
        PessoaEnum pessoaEnum = PessoaEnum.JURIDICA;
        FornecedorModel fornecedor1 = new FornecedorModel(pessoaEnum, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"33.113.309/0001-47",new ArrayList<>());
        fornecedorRepository.save(fornecedor1);

        List<FornecedorModel> result = fornecedorService.get();
        assertEquals(1, result.size());
        assertEquals("Alvo Dumbledor", result.get(0).getNome());
        assertEquals("33.113.309/0001-47", result.get(0).getCnpj());

        //FORNECEDOR2
        PessoaEnum pessoaEnum2 = PessoaEnum.FISICA;
        FornecedorModel fornecedor2 = new FornecedorModel(pessoaEnum2, "Alvo Dumbledore","dumbledoree@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"33.113.300/0001-47",new ArrayList<>());
        fornecedorRepository.save(fornecedor2);

        result = fornecedorService.get();
        assertEquals(2, result.size());
    }

    @Test
    void testGetById() {
        PessoaEnum pessoaEnum = PessoaEnum.JURIDICA;
        FornecedorModel fornecedor1 = new FornecedorModel(pessoaEnum, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"33.113.309/0001-47",new ArrayList<>());
        FornecedorModel retorno = fornecedorRepository.save(fornecedor1);

        FornecedorModel result = fornecedorService.get(retorno.getId());
        assertEquals("Alvo Dumbledor", result.getNome());
        assertEquals("33.113.309/0001-47", result.getCnpj());
        assertEquals(retorno.getId(), result.getId());

    }

    @Test
    void testGetByCnpj() {
        PessoaEnum pessoaEnum = PessoaEnum.JURIDICA;
        FornecedorModel fornecedor1 = new FornecedorModel(pessoaEnum, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"33.113.309/0001-47",new ArrayList<>());
        fornecedorRepository.save(fornecedor1);

        FornecedorModel result = fornecedorService.get("33.113.309/0001-47");
        assertEquals("Alvo Dumbledor", result.getNome());
        assertEquals("33.113.309/0001-47", result.getCnpj());
    }

    @Test
    void testGetByEmail() {
        PessoaEnum pessoaEnum = PessoaEnum.JURIDICA;
        FornecedorModel fornecedor1 = new FornecedorModel(pessoaEnum, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"33.113.309/0001-47",new ArrayList<>());
        fornecedorRepository.save(fornecedor1);

        FornecedorModel result = fornecedorService.getByEmail("dumbledore@gmail.com");
        assertEquals("Alvo Dumbledor", result.getNome());
        assertEquals("33.113.309/0001-47", result.getCnpj());
    }

    @Test
    void testGetByNome() {
        PessoaEnum pessoaEnum = PessoaEnum.JURIDICA;
        FornecedorModel fornecedor1 = new FornecedorModel(pessoaEnum, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"33.113.309/0001-47",new ArrayList<>());
        fornecedorRepository.save(fornecedor1);

        List<FornecedorModel> result = fornecedorService.getByNome("Alvo Dumbledor");
        assertEquals("Alvo Dumbledor", result.get(0).getNome());
        assertEquals("33.113.309/0001-47", result.get(0).getCnpj());
    }

    @Test
    void testCreate() {
        PessoaEnum pessoaEnum = PessoaEnum.JURIDICA;
        FornecedorModel fornecedor1 = new FornecedorModel(pessoaEnum, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"33.113.309/0001-47",new ArrayList<>());
        fornecedorRepository.save(fornecedor1);

        List<FornecedorModel> result = fornecedorService.getByNome("Alvo Dumbledor");
        assertEquals("Alvo Dumbledor", result.get(0).getNome());
        assertEquals("33.113.309/0001-47", result.get(0).getCnpj());
    }

}



/*        //TELEFONE
        ContatoEnum contatoEnum = ContatoEnum.RESIDENCIAL;
        TelefoneModel telefone1 = new TelefoneModel();
        telefone1.setNumero("44945676362");
        telefone1.setTipo(contatoEnum);
        telefone1.setPessoa(retorno1);

        List<TelefoneModel> telefones = new ArrayList<>();
        telefones.add(telefone1);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(telefones.get(0)); */