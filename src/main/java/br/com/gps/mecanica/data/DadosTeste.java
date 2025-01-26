package br.com.gps.mecanica.data;

import java.time.LocalDate;
import java.util.List;

import br.com.gps.mecanica.MecanicaFxMainApplication;
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
import br.com.gps.mecanica.services.ClienteService;
import br.com.gps.mecanica.services.FuncionarioService;
import br.com.gps.mecanica.services.ProdutoService;
import br.com.gps.mecanica.services.ServicoService;

public class DadosTeste {
    public static List<ClienteModel> getClientes() {
        ClienteModel cliente1 = new ClienteModel(
                PessoaEnum.FISICA,
                "João Silva",
                "947.043.440-45",
                "joao.silva@example.com",
                null, null, null);
        cliente1.setEnderecos(List.of(new EnderecoModel("12345-678", "Rua A", "Bairro B", "Cidade C", "Estado D", "123",
                        "Complemento X", "Ponto de referência", ContatoEnum.RESIDENCIAL, cliente1)));
        cliente1.setTelefones(List.of(new TelefoneModel("123456789", ContatoEnum.RESIDENCIAL, cliente1)));
        cliente1.setVeiculos(List.of(new VeiculoModel("ABC1234", "Civic", "Honda", 2020, CorEnum.PRETO, cliente1)));

        ClienteModel cliente2 = new ClienteModel(
                PessoaEnum.FISICA,
                "Maria Oliveira",
                "650.518.500-30",
                "maria.oliveira@example.com",
                null, null, null);
        cliente2.setEnderecos(List.of(new EnderecoModel("54321-876", "Avenida X", "Bairro Y", "Cidade Z", "Estado W",
                        "456", "Complemento Y", "Perto do parque", ContatoEnum.COMERCIAL, cliente2)));
        cliente2.setTelefones(List.of(new TelefoneModel("987654321", ContatoEnum.RESIDENCIAL, cliente2)));
        cliente2.setVeiculos(List.of(new VeiculoModel("XYZ9876", "Corolla", "Toyota", 2019, CorEnum.BRANCO, cliente2)));
        

        ClienteModel cliente3 = new ClienteModel(
                PessoaEnum.FISICA,
                "Carlos Pereira",
                "265.386.710-98",
                "carlos.pereira@example.com",
                null, null, null);
        cliente3.setEnderecos(List.of(new EnderecoModel("87654-321", "Rua Z", "Bairro W", "Cidade V", "Estado U", "789",
                        "Complemento Z", "Próximo ao mercado", ContatoEnum.COMERCIAL, cliente3)));
        cliente3.setTelefones(List.of(new TelefoneModel("456789123", ContatoEnum.COMERCIAL, cliente3)));
        cliente3.setVeiculos(List.of(new VeiculoModel("JKL3456", "Hilux", "Toyota", 2022, CorEnum.PRETO, cliente3)));
        
        ClienteModel cliente4 = new ClienteModel(
                PessoaEnum.FISICA,
                "Ana Souza",
                "937.268.650-63",
                "ana.souza@example.com",
                null, null, null);
        cliente4.setEnderecos(List.of(new EnderecoModel("98765-432", "Rua W", "Bairro V", "Cidade U", "Estado T", "987",
                        "Complemento W", "Próximo ao shopping", ContatoEnum.RESIDENCIAL, cliente4)));
        cliente4.setTelefones(List.of(new TelefoneModel("654987321", ContatoEnum.RESIDENCIAL, cliente4)));
        cliente4.setVeiculos(List.of(new VeiculoModel("MNO6789", "Onix", "Chevrolet", 2019, CorEnum.BRANCO, cliente4)));

        return List.of(cliente1, cliente2, cliente3, cliente4);
    }

    public static List<FornecedorModel> getFornecedores() {
        FornecedorModel fornecedor1 = new FornecedorModel(
                PessoaEnum.JURIDICA,
                "ABC Distribuidora",
                "abc@distribuidora.com",
                null, null, "12.187.313/0001-80", null
        );
        fornecedor1.setEnderecos(List.of(new EnderecoModel(
                "11111-111", "Avenida Principal", "Centro", "Cidade Alfa", "Estado Beta", "100", null, "Próximo à praça", ContatoEnum.COMERCIAL, fornecedor1
        )));
        fornecedor1.setTelefones(List.of(new TelefoneModel("40028922", ContatoEnum.COMERCIAL, fornecedor1)));
        fornecedor1.setProdutos(List.of(new ProdutoModel("Óleo de Motor", "Óleo sintético para motor", 50.0, 35.0, 200, fornecedor1)));

        FornecedorModel fornecedor2 = new FornecedorModel(
                PessoaEnum.JURIDICA,
                "Tech Auto Parts",
                "contato@techautoparts.com",
                null, null, "57.142.635/0001-40", null
        );
        fornecedor2.setEnderecos(List.of(new EnderecoModel(
                "22222-222", "Rua dos Mecânicos", "Industrial", "Cidade Beta", "Estado Gama", "200", "Sala 3", "Ao lado do terminal rodoviário", ContatoEnum.COMERCIAL, fornecedor2
        )));
        fornecedor2.setTelefones(List.of(new TelefoneModel("300300300", ContatoEnum.COMERCIAL, fornecedor2)));
        fornecedor2.setProdutos(List.of(new ProdutoModel("Filtro de Ar", "Filtro de ar compatível com veículos compactos", 30.0, 20.0, 150, fornecedor2)));

        FornecedorModel fornecedor3 = new FornecedorModel(
                PessoaEnum.JURIDICA,
                "AutoPro Supplies",
                "sales@autopro.com",
                null, null, "81.983.613/0001-98", null
        );
        fornecedor3.setEnderecos(List.of(new EnderecoModel(
                "33333-333", "Alameda das Indústrias", "Distrito Automotivo", "Cidade Gama", "Estado Delta", "300", "Galpão 5", "Próximo ao porto", ContatoEnum.COMERCIAL, fornecedor3
        )));
        fornecedor3.setTelefones(List.of(new TelefoneModel("123456789", ContatoEnum.COMERCIAL, fornecedor3)));
        fornecedor3.setProdutos(List.of(new ProdutoModel("Pastilha de Freio", "Pastilhas de freio de alta performance", 80.0, 60.0, 100, fornecedor3)));

        FornecedorModel fornecedor4 = new FornecedorModel(
                PessoaEnum.JURIDICA,
                "Mega Peças",
                "megapecas@auto.com",
                null, null, "58.571.670/0001-48", null
        );
        fornecedor4.setEnderecos(List.of(new EnderecoModel(
                "44444-444", "Rodovia BR-101", "Setor Norte", "Cidade Delta", "Estado Épsilon", "400", null, "Saída 12", ContatoEnum.COMERCIAL, fornecedor4
        )));
        fornecedor4.setTelefones(List.of(new TelefoneModel("987654321", ContatoEnum.COMERCIAL, fornecedor4)));
        fornecedor4.setProdutos(List.of(new ProdutoModel("Pneu 185/65R15", "Pneus de alta durabilidade", 250.0, 180.0, 300, fornecedor4)));

        return List.of(fornecedor1, fornecedor2, fornecedor3, fornecedor4);
}

    public static List<FuncionarioModel> getFuncionarios() {
        FuncionarioModel funcionario1 = new FuncionarioModel(
                PessoaEnum.FISICA,
                "Carlos Silva",
                "503.200.350-69",
                "carlos.silva@empresa.com",
                null, null, CargoEnum.MECANICO, LocalDate.of(2020, 1, 15)
        );
        funcionario1.setEnderecos(List.of(new EnderecoModel(
                "12345-678", "Rua A", "Centro", "Cidade X", "Estado Y", "100", null, "Próximo à praça", ContatoEnum.RESIDENCIAL, funcionario1
        )));
        funcionario1.setTelefones(List.of(new TelefoneModel("111222333", ContatoEnum.COMERCIAL, funcionario1)));

        FuncionarioModel funcionario2 = new FuncionarioModel(
                PessoaEnum.FISICA,
                "Fernanda Oliveira",
                "970.899.240-25",
                "fernanda.oliveira@empresa.com",
                null, null, CargoEnum.ATENDENTE, LocalDate.of(2019, 5, 10)
        );
        funcionario2.setEnderecos(List.of(new EnderecoModel(
                "54321-876", "Avenida B", "Bairro Y", "Cidade Z", "Estado W", "200", "Bloco 1", "Ao lado do parque", ContatoEnum.RESIDENCIAL, funcionario2
        )));
        funcionario2.setTelefones(List.of(new TelefoneModel("444555666", ContatoEnum.RESIDENCIAL, funcionario2)));

        FuncionarioModel funcionario3 = new FuncionarioModel(
                PessoaEnum.FISICA,
                "Ricardo Mendes",
                "428.008.240-50",
                "ricardo.mendes@empresa.com",
                null, null, CargoEnum.GERENTE, LocalDate.of(2018, 3, 20)
        );
        funcionario3.setEnderecos(List.of(new EnderecoModel(
                "98765-432", "Travessa C", "Bairro Z", "Cidade Y", "Estado X", "300", null, "Em frente ao mercado", ContatoEnum.COMERCIAL, funcionario3
        )));
        funcionario3.setTelefones(List.of(new TelefoneModel("777888999", ContatoEnum.COMERCIAL, funcionario3)));

        FuncionarioModel funcionario4 = new FuncionarioModel(
                PessoaEnum.FISICA,
                "Mariana Costa",
                "132.466.210-71",
                "mariana.costa@empresa.com",
                null, null, CargoEnum.ATENDENTE, LocalDate.of(2021, 7, 25)
        );
        funcionario4.setEnderecos(List.of(new EnderecoModel(
                "87654-321", "Rua D", "Bairro Novo", "Cidade Alfa", "Estado Beta", "400", "Casa 12", "Perto do shopping", ContatoEnum.RESIDENCIAL, funcionario4
        )));
        funcionario4.setTelefones(List.of(new TelefoneModel("222333444", ContatoEnum.COMERCIAL, funcionario4)));

        return List.of(funcionario1, funcionario2, funcionario3, funcionario4);
        }

    public static List<ProdutoModel> getProdutos() {
        ProdutoModel produto1 = new ProdutoModel(
                "Óleo de Motor",
                "Óleo sintético para motores a gasolina",
                50.0,
                35.0,
                200,
                null);

        ProdutoModel produto2 = new ProdutoModel(
                "Filtro de Ar",
                "Filtro de ar de alta eficiência para veículos compactos",
                30.0,
                20.0,
                150,
                null);

        ProdutoModel produto3 = new ProdutoModel(
                "Pastilha de Freio",
                "Pastilhas de freio de alta performance para carros esportivos",
                80.0,
                60.0,
                100,
                null);

        ProdutoModel produto4 = new ProdutoModel(
                "Pneu 185/65R15",
                "Pneu de alta durabilidade para veículos de passeio",
                250.0,
                180.0,
                300,
                null);

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
                "JJI-9973",
                "Civic",
                "Honda",
                2020,
                CorEnum.PRETO,
                null // This can be associated with a specific ClienteModel if needed
        );

        VeiculoModel veiculo2 = new VeiculoModel(
                "HSB-7397",
                "Corolla",
                "Toyota",
                2019,
                CorEnum.BRANCO,
                null);

        VeiculoModel veiculo3 = new VeiculoModel(
                "MWM-7398",
                "EcoSport",
                "Ford",
                2021,
                CorEnum.VERMELHO,
                null);

        VeiculoModel veiculo4 = new VeiculoModel(
                "JTH-0238",
                "Cruze",
                "Chevrolet",
                2018,
                CorEnum.AZUL,
                null);

        return List.of(veiculo1, veiculo2, veiculo3, veiculo4);
    }

    public static List<OrdemServicoModel> getOrdensServico() {
        // Create reusable Cliente instances
        ClienteModel cliente1 = new ClienteModel(
                PessoaEnum.FISICA,
                "Carlos Pereira",
                "291.008.660-75",
                "carlos.pereir@example.com",
                null, null, null
        );
        cliente1.setEnderecos(List.of(new EnderecoModel(
                "11111-111", "Rua Alfa", "Bairro Beta", "Cidade Gama", "Estado Delta", "123", null, "Perto da praça", ContatoEnum.RESIDENCIAL, cliente1
        )));
        cliente1.setTelefones(List.of(new TelefoneModel("111222333", ContatoEnum.RESIDENCIAL, cliente1)));

        ClienteModel cliente2 = new ClienteModel(
                PessoaEnum.FISICA,
                "Ana Souza",
                "913.595.710-78",
                "ana.soua@example.com",
                null, null, null
        );
        cliente2.setEnderecos(List.of(new EnderecoModel(
                "22222-222", "Avenida X", "Bairro Y", "Cidade Z", "Estado W", "456", "Apto 2", "Próximo ao parque", ContatoEnum.RESIDENCIAL, cliente2
        )));
        cliente2.setTelefones(List.of(new TelefoneModel("444555666", ContatoEnum.RESIDENCIAL, cliente2)));

        ClienteModel cliente3 = new ClienteModel(
                PessoaEnum.FISICA,
                "Lucas Martins",
                "551.563.960-03",
                "lucas.marins@example.com",
                null, null, null
        );
        cliente3.setEnderecos(List.of(new EnderecoModel(
                "33333-333", "Travessa C", "Bairro D", "Cidade E", "Estado F", "789", null, "Perto do mercado", ContatoEnum.COMERCIAL, cliente3
        )));
        cliente3.setTelefones(List.of(new TelefoneModel("777888999", ContatoEnum.COMERCIAL, cliente3)));

        ClienteModel cliente4 = new ClienteModel(
                PessoaEnum.FISICA,
                "Paula Ferreira",
                "863.812.960-20",
                "paula.fereira@example.com",
                null, null, null
        );
        cliente4.setEnderecos(List.of(new EnderecoModel(
                "44444-444", "Rua G", "Bairro H", "Cidade I", "Estado J", "987", "Casa 3", "Próximo ao shopping", ContatoEnum.RESIDENCIAL, cliente4
        )));
        cliente4.setTelefones(List.of(new TelefoneModel("999888777", ContatoEnum.RESIDENCIAL, cliente4)));

        // Create reusable Funcionario instances
        FuncionarioModel funcionario1 = new FuncionarioModel(
                PessoaEnum.FISICA,
                "Carlos Silva",
                "189.081.840-23",
                "carls.silva@empresa.com",
                null, null, CargoEnum.MECANICO, LocalDate.of(2020, 1, 15)
        );

        FuncionarioModel funcionario2 = new FuncionarioModel(
                PessoaEnum.FISICA,
                "Fernanda Oliveira",
                "231.016.870-04",
                "fernanda.oiveira@empresa.com",
                null, null, CargoEnum.ATENDENTE, LocalDate.of(2019, 5, 10)
        );

        FuncionarioModel funcionario3 = new FuncionarioModel(
                PessoaEnum.FISICA,
                "Ricardo Mendes",
                "929.993.800-89",
                "ricaro.mendes@empresa.com",
                null, null, CargoEnum.GERENTE, LocalDate.of(2018, 3, 20)
        );

        FuncionarioModel funcionario4 = new FuncionarioModel(
                PessoaEnum.FISICA,
                "Mariana Costa",
                "367.013.990-70",
                "mariaa.costa@empresa.com",
                null, null, CargoEnum.ATENDENTE, LocalDate.of(2021, 7, 25)
        );

        // Create reusable Servico instances
        ServicoModel servico1 = new ServicoModel("Troca de Óleo", "Troca completa de óleo", 120.0);
        ServicoModel servico2 = new ServicoModel("Alinhamento e Balanceamento", "Serviço de alinhamento completo", 150.0);
        ServicoModel servico3 = new ServicoModel("Revisão Completa", "Revisão geral do veículo", 500.0);
        ServicoModel servico4 = new ServicoModel("Troca de Pastilha de Freio", "Substituição de pastilhas de freio", 300.0);

        // Create reusable Produto instances
        ProdutoModel produto1 = new ProdutoModel("Óleo de Motor", "Óleo sintético", 50.0, 35.0, 1, null);
        ProdutoModel produto2 = new ProdutoModel("Pneu 185/65R15", "Pneu de alta durabilidade", 250.0, 180.0, 2, null);
        ProdutoModel produto3 = new ProdutoModel("Filtro de Ar", "Filtro de ar de alta eficiência", 30.0, 20.0, 1, null);
        ProdutoModel produto4 = new ProdutoModel("Pastilha de Freio", "Pastilhas de alta performance", 80.0, 60.0, 1, null);

        // Create reusable Veiculo instances
        VeiculoModel veiculo1 = new VeiculoModel("NDZ-8901", "EcoSport", "Ford", 2021, CorEnum.VERMELHO, cliente1);
        VeiculoModel veiculo2 = new VeiculoModel("NAZ-9259", "Cruze", "Chevrolet", 2018, CorEnum.AZUL, cliente2);
        VeiculoModel veiculo3 = new VeiculoModel("MOH-1651", "Hilux", "Toyota", 2022, CorEnum.PRETO, cliente3);
        VeiculoModel veiculo4 = new VeiculoModel("MVO-9045", "Onix", "Chevrolet", 2019, CorEnum.BRANCO, cliente4);

        cliente1.setVeiculos(List.of(veiculo1));
        cliente2.setVeiculos(List.of(veiculo2));
        cliente3.setVeiculos(List.of(veiculo3));
        cliente4.setVeiculos(List.of(veiculo4));

        // Create OrdemServico instances
        OrdemServicoModel ordem1 = new OrdemServicoModel(cliente1, veiculo1, List.of(servico1), List.of(produto1), funcionario1, "Troca de óleo e verificação de fluídos");
        OrdemServicoModel ordem2 = new OrdemServicoModel(cliente2, veiculo2, List.of(servico2), List.of(produto2), funcionario2, "Alinhamento das rodas e troca de pneus");
        OrdemServicoModel ordem3 = new OrdemServicoModel(cliente3, veiculo3, List.of(servico3), List.of(produto3), funcionario3, "Revisão completa e troca de filtros");
        OrdemServicoModel ordem4 = new OrdemServicoModel(cliente4, veiculo4, List.of(servico4), List.of(produto4), funcionario4, "Substituição completa das pastilhas de freio");

        ordem1.setContratada();

        ClienteService clienteService = MecanicaFxMainApplication.getBean(ClienteService.class);
        FuncionarioService funcionarioService = MecanicaFxMainApplication.getBean(FuncionarioService.class);
        ProdutoService produtoService = MecanicaFxMainApplication.getBean(ProdutoService.class);
        ServicoService servicoService = MecanicaFxMainApplication.getBean(ServicoService.class);

        try {
                clienteService.create(cliente1);
                clienteService.create(cliente2);
                clienteService.create(cliente3);
                clienteService.create(cliente4);

                funcionarioService.create(funcionario1);
                funcionarioService.create(funcionario2);
                funcionarioService.create(funcionario3);
                funcionarioService.create(funcionario4);

                produtoService.create(produto1);
                produtoService.create(produto2);
                produtoService.create(produto3);
                produtoService.create(produto4);

                servicoService.create(servico1);
                servicoService.create(servico2);
                servicoService.create(servico3);
                servicoService.create(servico4);
        } catch (Exception e) {
                e.printStackTrace();
                // System.out.println(e.getMessage());
        }

        return List.of(ordem1, ordem2, ordem3, ordem4);
        }


}
