package gafia;

import banco.BDConnection;
import banco.UsuarioDAO;
import banco.EstadoDAO;
import conexaoaApi.CalcularRota;
import java.util.*;
import veiculos.Veiculo;
import veiculos.VeiculoCombustao;
import veiculos.VeiculoEletrico;

public class Gafia {
    
    public static void main(String[] args) {
        
        try (Scanner leia = new Scanner(System.in)) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            // Conexão com o banco
            if (BDConnection.conectar() != null) {
                System.out.println("\033[32m----------------------------------\033[0m");
                System.out.println("\033[32m| Conexao realizada com sucesso! |\033[0m");
                System.out.println("\033[32m----------------------------------\033[0m");
            } else {
                System.err.println("--------------------");
                System.err.println("| Falha na conexãão |");
                System.err.println("--------------------");
                return;
            }
            
            veiculos.Veiculo.GafiaBanner.mostrar();
            
            System.out.println("Ja possui cadastro? (s/n)");
            String resposta = leia.nextLine();
            
            int estadoId = -1;
            Usuario usuarioLogado = null;
            
            if (resposta.equalsIgnoreCase("s")) {
                // LOGIN
                System.out.println("Digite seu email:");
                String email = leia.nextLine();
                System.out.println("Digite sua senha:");
                String senha = leia.nextLine();
                
                usuarioLogado = usuarioDAO.fazerLogin(email, senha);
                
                if (usuarioLogado == null) {
                    System.err.println("Email ou senha incorretos.");
                    return;
                }
                
                estadoId = usuarioLogado.getEstadoId();
                System.out.println("\033[32mLogin realizado com sucesso!\033[0m");
                usuarioLogado.exibirUsuario();
                
                double[] precos = EstadoDAO.buscarPrecosPorEstado(estadoId);
                System.out.println("Preços médios dos combustiveis no seu estado:");
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
                    System.out.print("Escolha uma opcao: ");
                    
                    int opcao = leia.nextInt();
                    leia.nextLine(); // limpar buffer
                    
                    switch (opcao) {
                        case 1 -> {
                            System.out.println("--------------------------Veiculo Combustao-------------------------");
                            VeiculoCombustao.getMapaVeiculos().forEach((id, veic) ->
                                    System.out.println(id + " - " + veic));
                            System.out.println("--------------------------------------------------------------------");
                            escolherVeiculo(leia, usuarioLogado, VeiculoCombustao.getMapaVeiculos());
                        }
                            
                        case 2 -> {
                            System.out.println("--------------------------Veiculo Eletrico--------------------------");
                            VeiculoEletrico.getMapaVeiculos().forEach((id, veic) ->
                                    System.out.println(id + " - " + veic));
                            escolherVeiculo(leia, usuarioLogado, VeiculoEletrico.getMapaVeiculos());
                            System.out.println("--------------------------------------------------------------------");
                        }
                            
                        case 3 -> {
                            System.out.println("-----------------------Veiculos cadastrados-------------------------");
                            usuarioLogado.listarVeiculos();
                            System.out.println("--------------------------------------------------------------------");
                        }
                            
                        case 4 -> {
                            System.out.print("Digite o ponto de origem (Cidade, UF): ");
                            String origem = leia.nextLine();

                            System.out.print("Digite o ponto de destino (Cidade, UF): ");
                            String destino = leia.nextLine();

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

                        int escolha = leia.nextInt();
                        leia.nextLine();

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
                        
                        double consumo = veiculoEscolhido.getConsumoMedio();

                        if (veiculoEscolhido instanceof VeiculoCombustao combustao) {
                        System.out.println("--------------------------------------------------------------------");
                        System.out.printf("Consumo do veículo: %.2f km/L                            \n", consumo);
                        double litros = distancia / consumo;
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
                        System.out.printf("Consumo do veículo: %.2f km/kWh\n", consumo);   
                        System.out.print("Digite o percentual atual da bateria (%): ");
                        int bateriaPercentual = leia.nextInt();
                        leia.nextLine();

                        int duracaoBateriaKm = eletrico.getDurBat();
                        double autonomiaAtual = ((bateriaPercentual - 10) * duracaoBateriaKm) / 100.0;

                        System.out.printf("Com %d%% de bateria, seu veículo pode rodar aproximadamente %.2f km.\n",
                        bateriaPercentual, autonomiaAtual);

                        if (distancia <= autonomiaAtual) {
                        System.out.println("Você não precisará recarregar.");
                        } else {
                        double distanciaRestante = distancia - autonomiaAtual;
                        int recargas = (int) Math.ceil(distanciaRestante / duracaoBateriaKm);
                        System.out.println("--------------------------------------------------------------------");
                        System.out.println("Você precisará recarregar seu veículo durante a viagem!");
                        System.out.println("Número de recargas estimadas: " + recargas);
                        }

                        double energia = distancia / consumo;
                        System.out.printf("Energia necessária: %.2f kWh\n", energia);

                        double custo = eletrico.calcularGasto(distancia, precos[3]);
                        System.out.printf("Custo total estimado (com energia a R$ %.2f): R$ %.2f\n", precos[3], custo);
                        System.out.println("--------------------------------------------------------------------");

                        // Comparação de emissão de CO2 com veículo de combustão mais econômico
                        if (melhorCombustao != null) {
                        double custoGasolina = melhorCombustao.calcularGasto(distancia, precos[0]); // gasolina
                        double custoAlcool   = melhorCombustao.calcularGasto(distancia, precos[1]); // álcool
                        double custoDiesel   = melhorCombustao.calcularGasto(distancia, precos[2]); // diesel
                        double custoEletrico = eletrico.calcularGasto(distancia, precos[3]); // energia elétrica

                        System.out.printf("Comparado ao veículo a combustão mais econômico da sua garagem (%s %s):\n",
                        melhorCombustao.getMarca(), melhorCombustao.getModelo());

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
                
                
            } else {
                // CADASTRO
                System.out.println("Vamos criar seu cadastro:");
                
                String nome, sobrenome, cpf, nascimento, email, telefone, senha;
                do {
                    System.out.println("Digite seu nome:");
                    nome = leia.nextLine();
                } while (nome.isEmpty());
                
                do {
                    System.out.println("Digite seu sobrenome:");
                    sobrenome = leia.nextLine();
                } while (sobrenome.isEmpty());
                
                do {
                    System.out.println("Digite seu CPF:");
                    cpf = leia.nextLine();
                } while (cpf.isEmpty());
                
                do {
                    System.out.println("Digite sua data de nascimento:");
                    nascimento = leia.nextLine();
                } while (nascimento.isEmpty());
                
                do {
                    System.out.println("Digite seu email:");
                    email = leia.nextLine();
                } while (email.isEmpty());
                
                do {
                    System.out.println("Digite seu telefone:");
                    telefone = leia.nextLine();
                } while (telefone.isEmpty());
                
                do {
                    System.out.println("Crie uma senha:");
                    senha = leia.nextLine();
                } while (senha.isEmpty());
                
                // Seleção de estado
                Map<Integer, String> estados = EstadoDAO.listarEstados();
                System.out.println("Escolha seu estado:");
                for (Map.Entry<Integer, String> e : estados.entrySet()) {
                    System.out.println(e.getKey() + " - " + e.getValue());
                }
                
                while (true) {
                    if (leia.hasNextInt()) {
                        estadoId = leia.nextInt();
                        leia.nextLine();
                        if (estadoId >= 1 && estadoId <= 27) break;
                        System.out.println("Escolha um numero de 1 a 27.");
                    } else {
                        leia.next();
                        System.out.println("Digite um número válido.");
                    }
                }
                
                Usuario novoUsuario = new Usuario(nome, sobrenome, cpf, nascimento, email, telefone, senha, estadoId);
                if (usuarioDAO.saveUsuario(novoUsuario)) {
                    System.out.println("Cadastro realizado com sucesso!");
                } else {
                    System.out.println("Erro ao cadastrar.");
                }
            }   
        }
    }

    //Método auxiliar para escolher um veículo
    
    public static void escolherVeiculo(Scanner leia, Usuario usuario, Map<Integer, ? extends Veiculo> mapa) {
        System.out.print("Digite o ID do veículo que deseja adicionar ao seu cadastro: ");
        
        int idEscolhido = leia.nextInt();
        leia.nextLine();

        Veiculo veiculoEscolhido = mapa.get(idEscolhido);
        
        if (veiculoEscolhido != null) {
            usuario.adicionarVeiculo(veiculoEscolhido);
            System.out.println("Veículo adicionado com sucesso!");
        } else {
            System.out.println("ID inválido! Veículo não encontrado.");
        }
    }
}

