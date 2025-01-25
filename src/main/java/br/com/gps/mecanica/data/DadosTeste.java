package br.com.gps.mecanica.data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import br.com.gps.mecanica.enums.CargoEnum;
import br.com.gps.mecanica.enums.ContatoEnum;
import br.com.gps.mecanica.enums.CorEnum;
import br.com.gps.mecanica.enums.PessoaEnum;
import br.com.gps.mecanica.models.ClienteModel;
import br.com.gps.mecanica.models.EnderecoModel;
import br.com.gps.mecanica.models.FornecedorModel;
import br.com.gps.mecanica.models.FuncionarioModel;
import br.com.gps.mecanica.models.OrdemServicoModel;
import br.com.gps.mecanica.models.ProdutoModel;
import br.com.gps.mecanica.models.ServicoModel;
import br.com.gps.mecanica.models.TelefoneModel;
import br.com.gps.mecanica.models.VeiculoModel;

public class DadosTeste {
    public static List<ClienteModel> getClientes() {
        ClienteModel cliente1 = new ClienteModel(
                PessoaEnum.FISICA,
                "João Silva",
                "123.456.789-00",
                "joao.silva@example.com",
                List.of(new TelefoneModel("123456789", ContatoEnum.RESIDENCIAL, null)),
                List.of(new EnderecoModel("12345-678", "Rua A", "Bairro B", "Cidade C", "Estado D", "123",
                        "Complemento X", "Ponto de referência", ContatoEnum.RESIDENCIAL, null)),
                List.of(new VeiculoModel("ABC1234", "Civic", "Honda", 2020, CorEnum.PRETO, null)));

        ClienteModel cliente2 = new ClienteModel(
                PessoaEnum.FISICA,
                "Maria Oliveira",
                "987.654.321-00",
                "maria.oliveira@example.com",
                List.of(new TelefoneModel("987654321", ContatoEnum.RESIDENCIAL, null)),
                List.of(new EnderecoModel("54321-876", "Avenida X", "Bairro Y", "Cidade Z", "Estado W", "456",
                        "Complemento Y", "Perto do parque", ContatoEnum.COMERCIAL, null)),
                List.of(new VeiculoModel("XYZ9876", "Corolla", "Toyota", 2019, CorEnum.BRANCO, null)));

        ClienteModel cliente3 = new ClienteModel(
                PessoaEnum.FISICA,
                "Carlos Pereira",
                "789.654.123-99",
                "carlos.pereira@example.com",
                List.of(new TelefoneModel("567890123", ContatoEnum.COMERCIAL, null)),
                List.of(new EnderecoModel("65432-789", "Travessa Z", "Bairro X", "Cidade Y", "Estado Z", "789", null,
                        "Em frente ao mercado", ContatoEnum.RESIDENCIAL, null)),
                List.of(new VeiculoModel("DEF4567", "EcoSport", "Ford", 2021, CorEnum.VERMELHO, null)));

        ClienteModel cliente4 = new ClienteModel(
                PessoaEnum.FISICA,
                "Ana Souza",
                "321.987.654-11",
                "ana.souza@example.com",
                List.of(new TelefoneModel("321654987", ContatoEnum.COMERCIAL, null)),
                List.of(new EnderecoModel("78912-345", "Rua Central", "Bairro Novo", "Cidade Nova", "Estado Antigo",
                        "101", "Bloco 2", "Próximo ao shopping", ContatoEnum.COMERCIAL, null)),
                List.of(new VeiculoModel("GHI7890", "Cruze", "Chevrolet", 2018, CorEnum.AZUL, null)));

        return List.of(cliente1, cliente2, cliente3, cliente4);
    }

    public static List<FornecedorModel> getFornecedores() {
        FornecedorModel fornecedor1 = new FornecedorModel(
                PessoaEnum.JURIDICA,
                "ABC Distribuidora",
                "abc@distribuidora.com",
                List.of(new EnderecoModel("11111-111", "Avenida Principal", "Centro", "Cidade Alfa", "Estado Beta",
                        "100", null, "Próximo à praça", ContatoEnum.COMERCIAL, null)),
                List.of(new TelefoneModel("40028922", ContatoEnum.COMERCIAL, null)),
                "12.345.678/0001-99",
                List.of(new ProdutoModel("Óleo de Motor", "Óleo sintético para motor", 50.0, 35.0, 200)));

        FornecedorModel fornecedor2 = new FornecedorModel(
                PessoaEnum.JURIDICA,
                "Tech Auto Parts",
                "contato@techautoparts.com",
                List.of(new EnderecoModel("22222-222", "Rua dos Mecânicos", "Industrial", "Cidade Beta", "Estado Gama",
                        "200", "Sala 3", "Ao lado do terminal rodoviário", ContatoEnum.COMERCIAL, null)),
                List.of(new TelefoneModel("300300300", ContatoEnum.COMERCIAL, null)),
                "98.765.432/0001-55",
                List.of(new ProdutoModel("Filtro de Ar", "Filtro de ar compatível com veículos compactos", 30.0, 20.0,
                        150)));

        FornecedorModel fornecedor3 = new FornecedorModel(
                PessoaEnum.JURIDICA,
                "AutoPro Supplies",
                "sales@autopro.com",
                List.of(new EnderecoModel("33333-333", "Alameda das Indústrias", "Distrito Automotivo", "Cidade Gama",
                        "Estado Delta", "300", "Galpão 5", "Próximo ao porto", ContatoEnum.COMERCIAL, null)),
                List.of(new TelefoneModel("123456789", ContatoEnum.COMERCIAL, null)),
                "56.789.123/0001-77",
                List.of(new ProdutoModel("Pastilha de Freio", "Pastilhas de freio de alta performance", 80.0, 60.0,
                        100)));

        FornecedorModel fornecedor4 = new FornecedorModel(
                PessoaEnum.JURIDICA,
                "Mega Peças",
                "megapecas@auto.com",
                List.of(new EnderecoModel("44444-444", "Rodovia BR-101", "Setor Norte", "Cidade Delta",
                        "Estado Épsilon", "400", null, "Saída 12", ContatoEnum.COMERCIAL, null)),
                List.of(new TelefoneModel("987654321", ContatoEnum.COMERCIAL, null)),
                "11.222.333/0001-88",
                List.of(new ProdutoModel("Pneu 185/65R15", "Pneus de alta durabilidade", 250.0, 180.0, 300)));

        return List.of(fornecedor1, fornecedor2, fornecedor3, fornecedor4);
    }

    public static List<FuncionarioModel> getFuncionarios() {
        FuncionarioModel funcionario1 = new FuncionarioModel(
                PessoaEnum.FISICA,
                "Carlos Silva",
                "123.456.789-00",
                "carlos.silva@empresa.com",
                List.of(new TelefoneModel("111222333", ContatoEnum.COMERCIAL, null)),
                List.of(new EnderecoModel("12345-678", "Rua A", "Centro", "Cidade X", "Estado Y", "100", null,
                        "Próximo à praça", ContatoEnum.RESIDENCIAL, null)),
                CargoEnum.MECANICO,
                LocalDate.of(2020, 1, 15));

        FuncionarioModel funcionario2 = new FuncionarioModel(
                PessoaEnum.FISICA,
                "Fernanda Oliveira",
                "987.654.321-11",
                "fernanda.oliveira@empresa.com",
                List.of(new TelefoneModel("444555666", ContatoEnum.RESIDENCIAL, null)),
                List.of(new EnderecoModel("54321-876", "Avenida B", "Bairro Y", "Cidade Z", "Estado W", "200",
                        "Bloco 1", "Ao lado do parque", ContatoEnum.RESIDENCIAL, null)),
                CargoEnum.ATENDENTE,
                LocalDate.of(2019, 5, 10));

        FuncionarioModel funcionario3 = new FuncionarioModel(
                PessoaEnum.FISICA,
                "Ricardo Mendes",
                "789.456.123-22",
                "ricardo.mendes@empresa.com",
                List.of(new TelefoneModel("777888999", ContatoEnum.COMERCIAL, null)),
                List.of(new EnderecoModel("98765-432", "Travessa C", "Bairro Z", "Cidade Y", "Estado X", "300", null,
                        "Em frente ao mercado", ContatoEnum.COMERCIAL, null)),
                CargoEnum.GERENTE,
                LocalDate.of(2018, 3, 20));

        FuncionarioModel funcionario4 = new FuncionarioModel(
                PessoaEnum.FISICA,
                "Mariana Costa",
                "321.654.987-33",
                "mariana.costa@empresa.com",
                List.of(new TelefoneModel("222333444", ContatoEnum.COMERCIAL, null)),
                List.of(new EnderecoModel("87654-321", "Rua D", "Bairro Novo", "Cidade Alfa", "Estado Beta", "400",
                        "Casa 12", "Perto do shopping", ContatoEnum.RESIDENCIAL, null)),
                CargoEnum.ATENDENTE,
                LocalDate.of(2021, 7, 25));

        return List.of(funcionario1, funcionario2, funcionario3, funcionario4);
    }

    public static List<ProdutoModel> getProdutos() {
        ProdutoModel produto1 = new ProdutoModel(
                "Óleo de Motor",
                "Óleo sintético para motores a gasolina",
                50.0,
                35.0,
                200);

        ProdutoModel produto2 = new ProdutoModel(
                "Filtro de Ar",
                "Filtro de ar de alta eficiência para veículos compactos",
                30.0,
                20.0,
                150);

        ProdutoModel produto3 = new ProdutoModel(
                "Pastilha de Freio",
                "Pastilhas de freio de alta performance para carros esportivos",
                80.0,
                60.0,
                100);

        ProdutoModel produto4 = new ProdutoModel(
                "Pneu 185/65R15",
                "Pneu de alta durabilidade para veículos de passeio",
                250.0,
                180.0,
                300);

        return List.of(produto1, produto2, produto3, produto4);
    }

    public static List<ServicoModel> getServicos() {
        ServicoModel servico1 = new ServicoModel(
                "Troca de Óleo",
                "Troca de óleo completo para veículos leves",
                120.0);

        ServicoModel servico2 = new ServicoModel(
                "Alinhamento e Balanceamento",
                "Serviço de alinhamento e balanceamento de rodas",
                150.0);

        ServicoModel servico3 = new ServicoModel(
                "Revisão Completa",
                "Revisão geral do veículo incluindo filtros e fluídos",
                500.0);

        ServicoModel servico4 = new ServicoModel(
                "Troca de Pastilha de Freio",
                "Substituição de pastilhas de freio dianteiras e traseiras",
                300.0);

        return List.of(servico1, servico2, servico3, servico4);
    }

    public static List<VeiculoModel> getVeiculos() {
        VeiculoModel veiculo1 = new VeiculoModel(
                "ABC1234",
                "Civic",
                "Honda",
                2020,
                CorEnum.PRETO,
                null // This can be associated with a specific ClienteModel if needed
        );

        VeiculoModel veiculo2 = new VeiculoModel(
                "XYZ9876",
                "Corolla",
                "Toyota",
                2019,
                CorEnum.BRANCO,
                null);

        VeiculoModel veiculo3 = new VeiculoModel(
                "DEF4567",
                "EcoSport",
                "Ford",
                2021,
                CorEnum.VERMELHO,
                null);

        VeiculoModel veiculo4 = new VeiculoModel(
                "GHI7890",
                "Cruze",
                "Chevrolet",
                2018,
                CorEnum.AZUL,
                null);

        return List.of(veiculo1, veiculo2, veiculo3, veiculo4);
    }

    public static List<OrdemServicoModel> getOrdensServico() {
        OrdemServicoModel ordem1 = new OrdemServicoModel(
                new ClienteModel(
                        PessoaEnum.FISICA,
                        "Carlos Pereira",
                        "789.654.123-99",
                        "carlos.pereira@example.com",
                        null, null, null), // Cliente
                new VeiculoModel("DEF4567", "EcoSport", "Ford", 2021, CorEnum.VERMELHO, null), // Veículo
                Set.of(new ServicoModel("Troca de Óleo", "Troca completa de óleo", 120.0)), // Serviços
                Set.of(new ProdutoModel("Óleo de Motor", "Óleo sintético", 50.0, 35.0, 1)), // Produtos
                new FuncionarioModel(
                        PessoaEnum.FISICA,
                        "Carlos Silva",
                        "123.456.789-00",
                        "carlos.silva@empresa.com",
                        null, null, CargoEnum.MECANICO, LocalDate.of(2020, 1, 15)), // Funcionário
                "Troca de óleo e verificação de fluídos");

        OrdemServicoModel ordem2 = new OrdemServicoModel(
                new ClienteModel(
                        PessoaEnum.FISICA,
                        "Ana Souza",
                        "321.654.987-33",
                        "ana.souza@example.com",
                        null, null, null), // Cliente
                new VeiculoModel("GHI7890", "Cruze", "Chevrolet", 2018, CorEnum.AZUL, null), // Veículo
                Set.of(new ServicoModel("Alinhamento e Balanceamento", "Serviço de alinhamento completo", 150.0)), // Serviços
                Set.of(new ProdutoModel("Pneu 185/65R15", "Pneu de alta durabilidade", 250.0, 180.0, 2)), // Produtos
                new FuncionarioModel(
                        PessoaEnum.FISICA,
                        "Fernanda Oliveira",
                        "987.654.321-11",
                        "fernanda.oliveira@empresa.com",
                        null, null, CargoEnum.ATENDENTE, LocalDate.of(2019, 5, 10)), // Funcionário
                "Alinhamento das rodas e troca de pneus");

        OrdemServicoModel ordem3 = new OrdemServicoModel(
                new ClienteModel(
                        PessoaEnum.FISICA,
                        "Lucas Martins",
                        "111.222.333-44",
                        "lucas.martins@example.com",
                        null, null, null), // Cliente
                new VeiculoModel("JKL3456", "Hilux", "Toyota", 2022, CorEnum.PRETO, null), // Veículo
                Set.of(new ServicoModel("Revisão Completa", "Revisão geral do veículo, incluindo filtros e fluídos",
                        500.0)), // Serviços
                Set.of(new ProdutoModel("Filtro de Ar", "Filtro de ar de alta eficiência", 30.0, 20.0, 1)), // Produtos
                new FuncionarioModel(
                        PessoaEnum.FISICA,
                        "Ricardo Mendes",
                        "789.456.123-22",
                        "ricardo.mendes@empresa.com",
                        null, null, CargoEnum.GERENTE, LocalDate.of(2018, 3, 20)), // Funcionário
                "Revisão completa e troca de filtros");

        OrdemServicoModel ordem4 = new OrdemServicoModel(
                new ClienteModel(
                        PessoaEnum.FISICA,
                        "Paula Ferreira",
                        "555.666.777-88",
                        "paula.ferreira@example.com",
                        null, null, null), // Cliente
                new VeiculoModel("MNO6789", "Onix", "Chevrolet", 2019, CorEnum.BRANCO, null), // Veículo
                Set.of(new ServicoModel("Troca de Pastilha de Freio",
                        "Substituição das pastilhas de freio dianteiras e traseiras", 300.0)), // Serviços
                Set.of(new ProdutoModel("Pastilha de Freio", "Pastilhas de alta performance", 80.0, 60.0, 1)), // Produtos
                new FuncionarioModel(
                        PessoaEnum.FISICA,
                        "Mariana Costa",
                        "321.654.987-33",
                        "mariana.costa@empresa.com",
                        null, null, CargoEnum.ATENDENTE, LocalDate.of(2021, 7, 25)), // Funcionário
                "Substituição completa das pastilhas de freio");

        return List.of(ordem1, ordem2, ordem3, ordem4);
    }

}
