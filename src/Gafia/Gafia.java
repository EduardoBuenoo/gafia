package Gafia;

import Database.BDConnection;
import Database.UsuarioDAO;
import Database.EstadoDAO;
import Database.VeiculoDAO;
import ApiConnection.CalcularRota;
import Database.GaragemDAO;
import java.util.*;
import Vehicles.Veiculo;
import Vehicles.VeiculoCombustao;
import Vehicles.VeiculoEletrico;

public class Gafia {

    public static void main(String[] args) {

        try (Scanner read = new Scanner(System.in)) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            // Conexão com o banco
            if (BDConnection.conectar() != null) {
                System.out.println("\033[32m----------------------------------\033[0m");
                System.out.println("\033[32m| Conexao realizada com sucesso! |\033[0m");
                System.out.println("\033[32m----------------------------------\033[0m");
            } else {
                System.err.println("--------------------");
                System.err.println("| Falha na conexão |");
                System.err.println("--------------------");
                return;
            }

            System.out.println("Você possui cadastro?");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");
            
            String resposta = read.nextLine();
            
            Usuario usuarioLogado = null;
            
            if(resposta.equalsIgnoreCase("2")) {
                
                Menu.realizarCadastro(read);
                System.out.println("Cadastro concluido.");
                System.out.println("Faça login para prosseguir.");
                System.out.println("");
                
                System.out.println("Digite seu email:");
                String email = read.nextLine();
                System.out.println("Digite sua senha:");
                String senha = read.nextLine();
                
                usuarioLogado = usuarioDAO.fazerLogin(email, senha);
                if (usuarioLogado != null) {
                    Menu.exibindoMenuPrincipal(usuarioLogado, read);
                } else {
                    System.err.println("Login falhou. Email ou senha incorretos.");
                }
                
            } else if (resposta.equalsIgnoreCase("1")){
                
                usuarioLogado = Menu.realizarloginMenu(read);
                if (usuarioLogado != null) {
                    Menu.exibindoMenuPrincipal(usuarioLogado, read);
                } else {
                System.err.println("Login falhou. Encerrando.");
                }  
            }

           
/*
            if (resposta.equalsIgnoreCase("s")) {
                // LOGIN
                System.out.println("Digite seu email:");
                String email = read.nextLine();
                System.out.println("Digite sua senha:");
                String senha = read.nextLine();

                usuarioLogado = usuarioDAO.fazerLogin(email, senha);

                if (usuarioLogado == null) {
                    System.err.println("Email ou senha incorreto.");
                    return;
                }

                int estadoId = -1; //INSTANCIANDO ESTADOS
                
                estadoId = usuarioLogado.getEstadoId();
                System.out.println("\033[32mLogin realizado com sucesso!\033[0m");
                usuarioLogado.exibirUsuario();

                double[] precos = EstadoDAO.buscarPrecosPorEstado(estadoId);
                System.out.println("================ PREÇO MÉDIO DOS COMBUSTIVEIS NO SEU ESTADO ================");
                System.out.printf("Gasolina: R$ %.2f | Alcool: R$ %.2f | Diesel: R$ %.2f | Energia: R$ %.2f\n",
                        precos[0], precos[1], precos[2], precos[3]);

                boolean menuAtivo = true;
                while (menuAtivo) {
                    System.out.println("--------------------------------------------------------------------");
                    System.out.println("|               Menu               ");
                    System.out.println("|1 - Selecionar veículo a combustao");
                    System.out.println("|2 - Selecionar veículos eletricos ");
                    System.out.println("|3 - Mostrar meus veiculos         ");
                    System.out.println("|4 - Calcular viagem               ");
                    System.out.println("|5 - Sair                          ");
                    System.out.println("--------------------------------------------------------------------");
                    System.out.println();
                    System.out.print("Escolha uma opção: ");

                    int opcao = read.nextInt();
                    read.nextLine();

                    switch (opcao) {
                        case 1 -> {
                            System.out.println("--------------------------Veiculo Combustao-------------------------");
                            Map<Integer, String> veiculosC = VeiculoDAO.listarVeiculosCombustao();
                            System.out.println("Escolha seu veiculo a combustão: ");
                            for (Map.Entry<Integer, String> e : veiculosC.entrySet()) {
                            System.out.println(e.getKey() + " - " + e.getValue());
                            }

                            int veiculosCId = -1; //INSTANCIANDO VEICULOS A COMBUSTAO
                            while (true) {
                                if (read.hasNextInt()) {
                                    veiculosCId = read.nextInt();
                                    read.nextLine();
                                if (veiculosCId >= 1 && veiculosCId <= 15) break;
                                    System.out.println("Escolha uma opção: ");
                                } else {
                                    read.next();
                                    System.err.println("Digite um número válido.");
                                }
                            }
                            
                            GaragemDAO garagemDAO = new GaragemDAO();
                            boolean sucesso = garagemDAO.adicionarVeiculoNaGaragem(usuarioLogado.getId(), null, veiculosCId);
                            
                            if (sucesso) {
                                System.out.println("Veiculo a combustao adicionado com sucesso!");
                            } else {
                                System.err.println("Erro ao adicionar o veiculo na garagem.");
                            }
                            System.out.println("--------------------------------------------------------------------");

                        }

                        case 2 -> {
                            System.out.println("--------------------------Veiculo Eletrico--------------------------");
                            Map<Integer, String> veiculosE = VeiculoDAO.listarVeiculosEletricos();
                            System.out.println("Escolha seu veiculo eletrico: ");
                            for (Map.Entry<Integer, String> e : veiculosE.entrySet()) {
                            System.out.println(e.getKey() + " - " + e.getValue());
                        }
                            
                            int veiculosEId = -1; //INSTANCIANDO VEICULOS ELETRICOS
                            while (true) {
                            if (read.hasNextInt()) {
                                veiculosEId = read.nextInt();
                                read.nextLine();
                            if (veiculosEId >= 1 && veiculosEId <= 33) break;
                                System.out.println("Escolha uma opção: ");
                            } else {
                                read.next();
                                System.err.println("Digite um número válido.");
                                }
                            }
                            GaragemDAO garagemDAO = new GaragemDAO();
                            boolean sucesso = garagemDAO.adicionarVeiculoNaGaragem(usuarioLogado.getId(), veiculosEId, null);
                            
                            if (sucesso) {
                                System.out.println("Veiculo eletrico adicionado com sucesso!");
                            } else {
                                System.err.println("Erro ao adicionar o veiculo na garagem.");
                            }
                            System.out.println("--------------------------------------------------------------------");
                        }

                        case 3 -> {
                            boolean garagem = true;
                            while(garagem) {
                            System.out.println("--------------------------------------------------------------------");
                            System.out.println("|             Garagem              ");
                            System.out.println("|1 - Mostrar todos os veiculos     ");
                            System.out.println("|2 - Mostrar veiculos a combustao  ");
                            System.out.println("|3 - Mostrar veiculos eletricos    ");
                            System.out.println("|4 - Voltar ao menu                ");
                            System.out.println("--------------------------------------------------------------------");
                            System.out.println();
                            System.out.print("Escolha uma opção: ");
                            
                            opcao = read.nextInt();
                            read.nextLine();
                            
                            switch(opcao) {
                                case 1 -> {
                                    System.out.println("-----------------------Veiculos Cadastrados-------------------------");
                                    List<Veiculo> meusVeiculos = GaragemDAO.listarTodosOsVeiculosDoUsuario(usuarioLogado.getId());
                                    
                                    for (Veiculo v : meusVeiculos) {
                                        System.out.println(v);
                                    }
                                }
                                case 2 -> {
                                    System.out.println("------------------Veiculos a Combustao Cadastrados-------------------");
                                    List<VeiculoCombustao> meusCombustao = GaragemDAO.listarVeiculosCombustaoDoUsuario(usuarioLogado.getId());
                                    if (meusCombustao.isEmpty()) {
                                        System.err.println("Você não possui veículos a combustão cadastrados.");
                                    } else {
                                        meusCombustao.forEach(System.out::println);
                                    }
                                }
                                case 3 -> {
                                    System.out.println("-------------------Veiculos Eletricos Cadastrados--------------------");
                                    List<VeiculoEletrico> meusEletricos = GaragemDAO.listarVeiculosEletricosDoUsuario(estadoId);
                                    if (meusEletricos.isEmpty()) {
                                        System.err.println("Você não possui veículos a combustão cadastrados.");
                                    } else {
                                        meusEletricos.forEach(System.out::println);
                                    }
                                }
                                case 4 -> {
                                    System.out.println("Retornando ao menu..");
                                    garagem = false;
                                }

                                default -> System.err.println("Opção invalida!");
                                }
                            }
                            System.out.println("--------------------------------------------------------------------");
                        }

                        case 4 -> {
                            System.out.print("Digite o ponto de origem (Cidade, UF): ");
                            String origem = read.nextLine();

                            System.out.print("Digite o ponto de destino (Cidade, UF): ");
                            String destino = read.nextLine();

                            double distancia = CalcularRota.calcularDistancia(origem, destino);
                            if (distancia <= 0) {
                            System.err.println("Não foi possível calcular a distância.");
                            break;
                        }

                            List<Veiculo> meusVeiculos = usuarioLogado.getVeiculos();
                            if (meusVeiculos.isEmpty()) {
                            System.err.println("Você não possui veículos cadastrados.");
                            break;
                        }

                            double[] preco = EstadoDAO.buscarPrecosPorEstado(usuarioLogado.getEstadoId());

                        // Encontrar veículo mais econômico
                        Veiculo veiculoMaisEconomico = null;
                        double menorCusto = Double.MAX_VALUE;
                        VeiculoCombustao melhorCombustao = null;
                        double menorCustoCombustao = Double.MAX_VALUE;

                        for (Veiculo v : meusVeiculos) {
                        double custo;
                        if (v instanceof VeiculoCombustao combustao) {
                                custo = combustao.calcularGasto(distancia, precos[0]);
                                if (custo < menorCustoCombustao) {
                                    menorCustoCombustao = custo;
                                    melhorCombustao = combustao;
                                }
                            } else if (v instanceof VeiculoEletrico eletrico) {
                                custo = eletrico.calcularGasto(distancia, precos[3]);
                            } else continue;

                            if (custo < menorCusto) {
                            menorCusto = custo;
                            veiculoMaisEconomico = v;
                            }
                        }

                        System.out.println("\nVeículo mais econômico da sua garagem:");
                        System.out.println(veiculoMaisEconomico);
                        System.out.println("--------------------------------------------------------------------");
                        System.out.printf("Custo estimado: R$ %.2f\n\n", menorCusto);
                        System.out.println("--------------------------------------------------------------------");

                        System.out.println("Escolha um veículo para a viagem:");
                        for (int i = 0; i < meusVeiculos.size(); i++) {
                        System.out.println((i + 1) + " - " + meusVeiculos.get(i));
                        }

                        int escolha = read.nextInt();
                        read.nextLine();

                        if (escolha < 1 || escolha > meusVeiculos.size()) {
                        System.err.println("--------------------------------------------------------------------");
                        System.err.println("Escolha inválida.");
                        System.err.println("--------------------------------------------------------------------");
                            break;
                        }

                        Veiculo veiculoEscolhido = meusVeiculos.get(escolha - 1);
                        System.out.println("--------------------------------------------------------------------");
                        System.out.printf("Distância: %.2f km\n", distancia);
                        System.out.println("--------------------------------------------------------------------");

                        double avarege = veiculoEscolhido.getAvaregeCons();

                        if (veiculoEscolhido instanceof VeiculoCombustao combustao) {
                        System.out.println("--------------------------------------------------------------------");
                        System.out.printf("Consumo do veículo: %.2f km/L                            \n", avarege);
                        double litros = distancia / avarege;
                        System.out.printf("Litros necessários: %.2f L                               \n", litros);
                        double custo = combustao.calcularGasto(distancia, precos[0]);
                        System.out.printf("Custo total estimado (com combustível a R$ %.2f): R$ %.2f\n", precos[0], custo);
                        System.out.println("--------------------------------------------------------------------");

                        int emissaoPorKm = melhorCombustao.getQntCO2(); // gramas/km
                        double emissaoTotalGramas = emissaoPorKm * distancia;
                        double emissaoTotalKg = emissaoTotalGramas / 1000;

                        System.out.printf("Este veículo emitiu aproximadamente %.2f kg de CO₂ nesta viagem.\n", emissaoTotalKg);
                        System.out.println("--------------------------------------------------------------------");
                        } else if (veiculoEscolhido instanceof VeiculoEletrico eletrico) {
                        System.out.printf("Consumo do veículo: %.2f km/kWh\n", avarege);
                        System.out.print("Digite o percentual atual da bateria (%): ");
                        int bateriaPercentual = read.nextInt();
                        read.nextLine();

                        int duracaoBateriaKm = eletrico.getDurBat();
                        double autonomiaAtual = ((bateriaPercentual - 10) * duracaoBateriaKm) / 100.0;

                        System.out.printf("Com sua bateria atual o seu veículo pode rodar aproximadamente %.2f km.\n", autonomiaAtual);

                        if (distancia <= autonomiaAtual) {
                        System.out.println("Você não precisará recarregar durante sua viagem!");
                        } else {
                        double distanciaRestante = distancia - autonomiaAtual;
                        int recargas = (int) Math.ceil(distanciaRestante / duracaoBateriaKm);
                        System.out.println("--------------------------------------------------------------------");
                        System.out.println("Você precisará recarregar seu veículo " + recargas + "durante essa viagem!");
                        }
                        double energia = distancia / avarege;
                        System.out.printf("Energia necessária: %.2f kWh\n", energia);

                        double custo = eletrico.calcularGasto(distancia, precos[3]);
                        System.out.printf("Custo total estimado: R$ %.2f\n", custo);
                        System.out.println("--------------------------------------------------------------------");

                        // Comparação de emissão de CO2 com veículo de combustão mais econômico
                        if (melhorCombustao != null) {
                        double custoGasolina = melhorCombustao.calcularGasto(distancia, precos[0]); // gasolina
                        double custoAlcool   = melhorCombustao.calcularGasto(distancia, precos[1]); // álcool
                        double custoDiesel   = melhorCombustao.calcularGasto(distancia, precos[2]); // diesel
                        double custoEletrico = eletrico.calcularGasto(distancia, precos[3]); // energia elétrica


                        System.out.printf("Comparado ao veículo a combustão mais econômico da sua garagem (%s %s):\n",
                        melhorCombustao.getBrand(), melhorCombustao.getModel());

                        System.out.printf("- Você economizou R$ %.2f em relação à gasolina (R$ %.2f).\n",
                        custoGasolina - custoEletrico, custoGasolina);

                        System.out.printf("- Você economizou R$ %.2f em relação ao álcool (R$ %.2f).\n",
                        custoAlcool - custoEletrico, custoAlcool);

                        System.out.printf("- Você economizou R$ %.2f em relação ao diesel (R$ %.2f).\n",
                        custoDiesel - custoEletrico, custoDiesel);
                        } else {
                        System.out.println("Você não possui veiculo á combustão cadastradoã.");
                        System.out.println("Por isso, não é possível comparar os custos e emissões de CO₂.");
                        System.out.println("--------------------------------------------------------------------");
                        }
                    }
                }


                        case 5 -> {
                            System.out.println("Saindo...");
                            menuAtivo = false;
                        }

                        default -> System.err.println("Opção invalida!");
                    }
                }


            } */
                }
            }
        }
    

    