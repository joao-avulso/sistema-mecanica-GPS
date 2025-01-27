package br.com.gps.mecanica.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
 
import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.models.EnderecoModel;
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
        FornecedorModel fornecedor1 = new FornecedorModel(PessoaEnum.FISICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"33.113.309/0001-47",new ArrayList<>());
        fornecedorRepository.save(fornecedor1);

        List<FornecedorModel> result = fornecedorService.get();
        assertEquals(1, result.size());
        assertEquals("Alvo Dumbledor", result.get(0).getNome());
        assertEquals("33.113.309/0001-47", result.get(0).getCnpj());

        //FORNECEDOR2
        FornecedorModel fornecedor2 = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledore","dumbledoree@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"33.113.300/0001-47",new ArrayList<>());
        fornecedorRepository.save(fornecedor2);

        result = fornecedorService.get();
        assertEquals(2, result.size());
    }

    @Test
    void testGetById() {
        FornecedorModel fornecedor1 = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"33.113.309/0001-47",new ArrayList<>());
        FornecedorModel retorno = fornecedorRepository.save(fornecedor1);

        FornecedorModel result = fornecedorService.get(retorno.getId());
        assertEquals("Alvo Dumbledor", result.getNome());
        assertEquals("33.113.309/0001-47", result.getCnpj());
        assertEquals(retorno.getId(), result.getId());

    }

    @Test
    void testGetByCnpj() {
        FornecedorModel fornecedor1 = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"36.578.284/0001-45",new ArrayList<>());
        fornecedorRepository.save(fornecedor1);

        FornecedorModel result = fornecedorService.get("36.578.284/0001-45");
        assertEquals("Alvo Dumbledor", result.getNome());
        assertEquals("36.578.284/0001-45", result.getCnpj());
    }

    @Test
    void testGetByEmail() {
        FornecedorModel fornecedor1 = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"36.578.284/0001-45",new ArrayList<>());
        fornecedorRepository.save(fornecedor1);

        FornecedorModel result = fornecedorService.getByEmail("dumbledore@gmail.com");
        assertEquals("Alvo Dumbledor", result.getNome());
        assertEquals("36.578.284/0001-45", result.getCnpj());
    }

    @Test
    void testGetByNome() {
        FornecedorModel fornecedor1 = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>(),new ArrayList<>(),"36.578.284/0001-45",new ArrayList<>());
        fornecedorRepository.save(fornecedor1);

        List<FornecedorModel> result = fornecedorService.getByNome("Alvo Dumbledor");
        assertEquals("Alvo Dumbledor", result.get(0).getNome());
        assertEquals("36.578.284/0001-45", result.get(0).getCnpj());
    }

    @Test
    void testCreate() {
        
        //FORNECEDOR
        FornecedorModel fornecedor = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>() ,new ArrayList<>(),"36.578.284/0001-45",new ArrayList<>());

        //TELEFONE
        TelefoneModel telefone1 = new TelefoneModel("44945676362", ContatoEnum.RESIDENCIAL, fornecedor);
        TelefoneModel telefone2 = new TelefoneModel("44945676363", ContatoEnum.RESIDENCIAL, fornecedor);

        //ENDERECO
        EnderecoModel endereco1 = new EnderecoModel("81230370", "Rua dos Jardins", "Zona 44", "Marilha", "SP",
         "1231", "Casa germinada", "", ContatoEnum.RESIDENCIAL, fornecedor);
         EnderecoModel endereco2 = new EnderecoModel("85670370", "Rua dos Rios", "Zona 4", "Marilha", "SP",
         "1231", "Casa germinada", "", ContatoEnum.COMERCIAL, fornecedor);

        //PRODUTOS
        ProdutoModel produto1 = new ProdutoModel("Carburador", "Carburador de fusca veio", 200.00, 20.00, 20,fornecedor);

        fornecedor.setProdutos(List.of(produto1));
        fornecedor.setTelefones(List.of(telefone1, telefone2));
        fornecedor.setEnderecos(List.of(endereco1, endereco2));

        try {
            FornecedorModel novo = fornecedorService.create(fornecedor);    
            assertEquals("ALVO DUMBLEDOR",novo.getNome());

            // Asserts na lista de telefones
            assertEquals(2, novo.getTelefones().size()); 
            assertEquals("44945676362", novo.getTelefones().get(0).getNumero()); 
            assertEquals("44945676363", novo.getTelefones().get(1).getNumero()); 

            // Asserts na lista de endereços
            assertEquals(2, novo.getEnderecos().size()); 
            assertEquals("81230370", novo.getEnderecos().get(0).getCep());  
            assertEquals("85670370", novo.getEnderecos().get(1).getCep());  

            // Asserts na lista de produtos
            assertEquals(1, novo.getProdutos().size());  
            assertEquals("Carburador", novo.getProdutos().get(0).getNome());  

        } catch (Exception e) {
            fail("Nao foi possivel criar o fornecedor no testCreate do FornecedorServiceTest");
        }
    }

    @Test
    void testUpdate() {
        
        //FORNECEDOR
        FornecedorModel fornecedor = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>() ,new ArrayList<>(),"36.578.284/0001-45",new ArrayList<>());


        FornecedorModel fornecedor2 = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor2","dumbledore@gmail.com",
        new ArrayList<>() ,new ArrayList<>(),"36.578.284/0001-45",new ArrayList<>());

        //TELEFONE
        TelefoneModel telefone1 = new TelefoneModel("44945676362", ContatoEnum.RESIDENCIAL, fornecedor);
        TelefoneModel telefone2 = new TelefoneModel("44945676363", ContatoEnum.RESIDENCIAL, fornecedor);

        //ENDERECO
        EnderecoModel endereco1 = new EnderecoModel("81230370", "Rua dos Jardins", "Zona 44", "Marilha", "SP",
         "1231", "Casa germinada", "", ContatoEnum.RESIDENCIAL, fornecedor);
        EnderecoModel endereco2 = new EnderecoModel("85670370", "Rua dos Rios", "Zona 4", "Marilha", "SP",
         "1231", "Casa germinada", "", ContatoEnum.COMERCIAL, fornecedor);

        //PRODUTOS
        ProdutoModel produto1 = new ProdutoModel("Carburador", "Carburador de fusca veio", 200.00, 20.00, 20, fornecedor2);

        fornecedor2.setProdutos(List.of(produto1));
        fornecedor2.setTelefones(List.of(telefone1, telefone2));
        fornecedor2.setEnderecos(List.of(endereco1, endereco2));
        
        try {
            FornecedorModel velho = fornecedorService.create(fornecedor);  
            FornecedorModel novo = fornecedorService.update(velho.getId(), fornecedor2);
            assertEquals("ALVO DUMBLEDOR2",novo.getNome()); 
            assertEquals("36578284000145",novo.getCnpj()); 

        } catch (Exception e) {
            fail("Nao foi possivel fazer o update do fornecedor no testUpdate do FornecedorServiceTest");
        }
    }

    @Test
    void testAddEndereco() {
        
        //FORNECEDOR
        FornecedorModel fornecedor = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>() ,new ArrayList<>(),"36.578.284/0001-45",new ArrayList<>());

         try {
            FornecedorModel result = fornecedorService.create(fornecedor);  

             //ENDERECO
            EnderecoModel endereco = new EnderecoModel("81230370", "Rua dos Jardins", "Zona 44", "Marilha", "SP",
            "1231", "Casa germinada", "", ContatoEnum.RESIDENCIAL, fornecedor);
            fornecedorService.addEndereco(result.getId(), endereco);

            FornecedorModel result2 = fornecedorService.get(result.getId());
            assertEquals("81230370",result2.getEnderecos().get(0).getCep()); 

        } catch (Exception e) {
            fail("Nao foi possivel adicionar endereco no testAddEndereco do FornecedorServiceTest");
        }
    }

    @Test
    void testAddTelefone() {
        
        //FORNECEDOR
        FornecedorModel fornecedor = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>() ,new ArrayList<>(),"36.578.284/0001-45",new ArrayList<>());
        
        try {
            FornecedorModel result = fornecedorService.create(fornecedor);  

            //TELEFONE
            TelefoneModel telefone1 = new TelefoneModel("44945676362", ContatoEnum.RESIDENCIAL, fornecedor);
            fornecedorService.addTelefone(result.getId(), telefone1);

            FornecedorModel result2 = fornecedorService.get(result.getId());
            assertEquals("44945676362",result2.getTelefones().get(0).getNumero()); 

        } catch (Exception e) {
            fail("Nao foi possivel adicionar telefone no testAddTelefone do FornecedorServiceTest");
        }
    }

    @Test
    void testDeleteEndereco() {
        
        //FORNECEDOR
        FornecedorModel fornecedor = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>() ,new ArrayList<>(),"36.578.284/0001-45",new ArrayList<>());

        //ENDERECO
        EnderecoModel endereco = new EnderecoModel("81230370", "Rua dos Jardins", "Zona 44", "Marilha", "SP",
         "1231", "Casa germinada", "", ContatoEnum.RESIDENCIAL, fornecedor);
         EnderecoModel endereco2 = new EnderecoModel("85670370", "Rua dos Rios", "Zona 4", "Marilha", "SP",
         "1231", "Casa germinada", "", ContatoEnum.COMERCIAL, fornecedor);

        fornecedor.setEnderecos(List.of(endereco, endereco2));

         try {
            //TESTE QUANDO TEM MULTIPLOS ENDERECOS
            FornecedorModel result = fornecedorService.create(fornecedor);  
            fornecedorService.deleteEndereco(result.getId(), result.getEnderecos().get(0).getId());
            FornecedorModel result2 = fornecedorService.get(result.getId());
            assertEquals(1,result2.getEnderecos().size()); 

            
            //TESTE QUANDO TEM 1 ENDERECO

            fornecedorService.deleteEndereco(result.getId(), result.getEnderecos().get(0).getId());
            FornecedorModel result3 = fornecedorService.get(result.getId());

            assertEquals(0, result3.getEnderecos().size()); 

        } catch (Exception e) {
            fail("Nao foi possivel deletar endereco no testDeleteEndereco do FornecedorServiceTest");
        }
    }
    
    @Test
    void testDeleteTelefone() {
        
        //FORNECEDOR
        FornecedorModel fornecedor = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>() ,new ArrayList<>(),"36.578.284/0001-45",new ArrayList<>());

        //TELEFONE
        TelefoneModel telefone1 = new TelefoneModel("44945676362", ContatoEnum.RESIDENCIAL, fornecedor);
        TelefoneModel telefone2 = new TelefoneModel("44945676363", ContatoEnum.RESIDENCIAL, fornecedor);

        fornecedor.setTelefones(List.of(telefone1, telefone2));

         try {
            //TESTE QUANDO TEM MULTIPLOS TELEFONES
            FornecedorModel result = fornecedorService.create(fornecedor);  
            fornecedorService.deleteTelefone(result.getId(), result.getTelefones().get(0).getId());
            FornecedorModel result2 = fornecedorService.get(result.getId());

            assertEquals(1,result2.getTelefones().size()); 
            
            //TESTE QUANDO TEM 1 TELEFONES

            fornecedorService.deleteTelefone(result.getId(), result.getTelefones().get(0).getId());
            FornecedorModel result3 = fornecedorService.get(result.getId());

            assertEquals(0, result3.getTelefones().size()); 

        } catch (Exception e) {
            fail("Nao foi possivel deletar telefone no testDeleteTelefone do FornecedorServiceTest");
        }
    }

    @Test
    void testDeleteById() {
        //FORNECEDOR
        FornecedorModel fornecedor = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>() ,new ArrayList<>(),"36.578.284/0001-45",new ArrayList<>());

        //TELEFONE
        TelefoneModel telefone1 = new TelefoneModel("44945676362", ContatoEnum.RESIDENCIAL, fornecedor);
        TelefoneModel telefone2 = new TelefoneModel("44945676363", ContatoEnum.RESIDENCIAL, fornecedor);

        //ENDERECO
        EnderecoModel endereco1 = new EnderecoModel("81230370", "Rua dos Jardins", "Zona 44", "Marilha", "SP",
         "1231", "Casa germinada", "", ContatoEnum.RESIDENCIAL, fornecedor);
         EnderecoModel endereco2 = new EnderecoModel("85670370", "Rua dos Rios", "Zona 4", "Marilha", "SP",
         "1231", "Casa germinada", "", ContatoEnum.COMERCIAL, fornecedor);

        //PRODUTOS
        ProdutoModel produto1 = new ProdutoModel("Carburador", "Carburador de fusca veio", 200.00, 20.00, 20,fornecedor);

        fornecedor.setProdutos(List.of(produto1));
        fornecedor.setTelefones(List.of(telefone1, telefone2));
        fornecedor.setEnderecos(List.of(endereco1, endereco2));

        try {
            FornecedorModel result = fornecedorService.create(fornecedor); 
            fornecedorService.delete(result.getId());

            assertEquals(0, fornecedorService.get().size()); 

        }catch (Exception e) {
            fail("Nao foi possivel deletar por ID no testDeleteByID do FornecedorServiceTest");
        }
    }

    @Test
    void testDeleteByCnpj() {
        //FORNECEDOR
        FornecedorModel fornecedor = new FornecedorModel(PessoaEnum.JURIDICA, "Alvo Dumbledor","dumbledore@gmail.com",
        new ArrayList<>() ,new ArrayList<>(),"36.578.284/0001-45",new ArrayList<>());

        //TELEFONE
        TelefoneModel telefone1 = new TelefoneModel("44945676362", ContatoEnum.RESIDENCIAL, fornecedor);
        TelefoneModel telefone2 = new TelefoneModel("44945676363", ContatoEnum.RESIDENCIAL, fornecedor);

        //ENDERECO
        EnderecoModel endereco1 = new EnderecoModel("81230370", "Rua dos Jardins", "Zona 44", "Marilha", "SP",
         "1231", "Casa germinada", "", ContatoEnum.RESIDENCIAL, fornecedor);
         EnderecoModel endereco2 = new EnderecoModel("85670370", "Rua dos Rios", "Zona 4", "Marilha", "SP",
         "1231", "Casa germinada", "", ContatoEnum.COMERCIAL, fornecedor);

        //PRODUTOS
        ProdutoModel produto1 = new ProdutoModel("Carburador", "Carburador de fusca veio", 200.00, 20.00, 20,fornecedor);

        fornecedor.setProdutos(List.of(produto1));
        fornecedor.setTelefones(List.of(telefone1, telefone2));
        fornecedor.setEnderecos(List.of(endereco1, endereco2));

        try {
            FornecedorModel result = fornecedorService.create(fornecedor); 
            fornecedorService.delete(result.getCnpj());

            assertEquals(0, fornecedorService.get().size()); 

        }catch (Exception e) {
            fail("Nao foi possivel deletar por ID no testDeleteByID do FornecedorServiceTest");
        }

    }


} 