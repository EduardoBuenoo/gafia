package Gafia;


import ApiConnection.CalcularRota;
import Database.EstadoDAO;
import Database.GaragemDAO;
import Database.UsuarioDAO;
import Database.VeiculoDAO;
import Gafia.Usuario;
import Vehicles.Veiculo;
import Vehicles.VeiculoCombustao;
import Vehicles.VeiculoEletrico;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



/**
 *
 * @author kaio
 * Necessidades urgentes....
 * Arrumar o case 4
 * precisa fazer linkagem com os atuais metodos do banco
 * esta cadastrado com o antigo metodo do sistema e nao acessa os veiculos do usuario
 * mesmo tendo veiculos cadastrados
 */
public class Menu {
    
    //METODO DE CADASTRO
    public static void realizarCadastro(Scanner read) {
        
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    System.out.println("| Vamos criar seu cadastro:");
    System.out.println("| ");

    String nome, sobrenome, cpf, nascimento, email, telefone, senha;
    do {
        System.out.print("| Digite seu nome: ");
        nome = read.nextLine();
    } while (nome.isEmpty());

    do {
        System.out.print("| Digite seu sobrenome: ");
        sobrenome = read.nextLine();
    } while (sobrenome.isEmpty());

    do {
        System.out.print("| Digite seu CPF: ");
        cpf = read.nextLine();
    } while (cpf.isEmpty());

    do {
        System.out.print("| Digite sua data de nascimento: ");
        nascimento = read.nextLine();
    } while (nascimento.isEmpty());

    do {
        System.out.print("| Digite seu email: ");
        email = read.nextLine();
    } while (email.isEmpty());

    do {
        System.out.print("| Digite seu telefone: ");
        telefone = read.nextLine();
    } while (telefone.isEmpty());

    do {
        System.out.print("| Digite uma senha: ");
        senha = read.nextLine();
    } while (senha.isEmpty());

    // Seleção de estado
    Map<Integer, String> estados = EstadoDAO.listarEstados();
    
    System.out.println("| ");
    
    for (Map.Entry<Integer, String> e : estados.entrySet()) {
    System.out.println("| " + e.getKey() + " - " + e.getValue());
    }
    System.out.print("| Selecione seu estado: ");

    int estadoId = -1;
    
    while (true) {
        if (read.hasNextInt()) {
            
            estadoId = read.nextInt();
            read.nextLine();
            
            if (estadoId >= 1 && estadoId <= 27) break;
            
            System.out.println("| Escolha um numero de 1 a 27.");
            
            } else {
            
                read.next();
                System.out.println("| Digite um número válido.");
        }
    }
    
    Usuario novoUsuario = new Usuario(nome, sobrenome, cpf, nascimento, email, telefone, senha, estadoId);
    
    if (usuarioDAO.saveUsuario(novoUsuario)) {
        System.out.println("| Cadastro realizado com sucesso!");
        } else {
            System.out.println("| Erro ao cadastrar.");
        }
    }
    
    //REALIZAR LOGIN
    public static Usuario realizarloginMenu(Scanner read) {
        
    UsuarioDAO usuarioDAO = new UsuarioDAO();
        
    System.out.println("--------------------------------------------------------------------");
    System.out.print("| Digite seu email: ");
    String email = read.nextLine();
        System.out.println("|");
    System.out.print("| Digite sua senha: ");
    String senha = read.nextLine();

    Usuario usuarioLogado = usuarioDAO.fazerLogin(email, senha);

    if (usuarioLogado == null) {
        System.err.println("| Email ou senha incorreto.");
        return null;
        }

    int estadoId = -1; //INSTANCIANDO ESTADOS
                
    estadoId = usuarioLogado.getEstadoId();
    System.out.println("|");
    System.out.println("| " + "\033[32mLogin realizado com sucesso!\033[0m");
    usuarioLogado.exibirUsuario();

    double[] precos = EstadoDAO.buscarPrecosPorEstado(estadoId);
    System.out.println("| ================ PREÇO MÉDIO DOS COMBUSTIVEIS NO SEU ESTADO ================");
    System.out.printf("| Gasolina: R$ %.2f | Alcool: R$ %.2f | Diesel: R$ %.2f | Energia: R$ %.2f\n", precos[0], precos[1], precos[2], precos[3]);
        return usuarioLogado;
    }
    
    // METODOS DO MENU PRINCIPAL
    public static void selecionarVeiculoACombustao(Usuario usuarioLogado, Scanner read) {
        System.out.println("--------------------------Veiculo Combustao-------------------------");
        Map<Integer, String> veiculosC = VeiculoDAO.listarVeiculosCombustao();
        for (Map.Entry<Integer, String> e : veiculosC.entrySet()) {
        System.out.println("| " + e.getKey() + " - " + e.getValue());
        }
        System.out.print("| Escolha seu veiculo a combustão: ");

        int veiculosCId = -1; //INSTANCIANDO VEICULOS A COMBUSTAO
        while (true) {
        if (read.hasNextInt()) {
            veiculosCId = read.nextInt();
            read.nextLine();
            if (veiculosCId >= 1 && veiculosCId <= 15) break;
            System.out.print("| Escolha uma opção: ");
        } else {
            read.next();
            System.err.println("| Digite um número válido.");
            }
        }
        GaragemDAO garagemDAO = new GaragemDAO();
        boolean sucesso = garagemDAO.adicionarVeiculoNaGaragem(usuarioLogado.getId(), null, veiculosCId);
                            
        if (sucesso) {
        System.out.println("| Veiculo a combustao adicionado com sucesso!");
        } else {
        System.err.println("| Erro ao adicionar o veiculo na garagem.");
        }
    }
    
    public static void selecionarVeiculoEletrico(Usuario usuarioLogado, Scanner read) {
        System.out.println("--------------------------Veiculo Eletrico--------------------------");
        Map<Integer, String> veiculosE = VeiculoDAO.listarVeiculosEletricos();
        for (Map.Entry<Integer, String> e : veiculosE.entrySet()) {
        System.out.println("| " + e.getKey() + " - " + e.getValue());
        }
        System.out.print("| Escolha seu veiculo eletrico: ");
                            
        int veiculosEId = -1; //INSTANCIANDO VEICULOS ELETRICOS
        
        while (true) {
            if (read.hasNextInt()) {
                veiculosEId = read.nextInt();
                read.nextLine();
            if (veiculosEId >= 1 && veiculosEId <= 33) break;
                System.out.println("| Escolha uma opção: ");
            } else {
                read.next();
                System.err.println("| Digite um número válido.");
            }
        }
        
        GaragemDAO garagemDAO = new GaragemDAO();
        boolean sucesso = garagemDAO.adicionarVeiculoNaGaragem(usuarioLogado.getId(), veiculosEId, null);
                            
        if (sucesso) {
            System.out.println("| Veiculo eletrico adicionado com sucesso!");
        } else {
            System.err.println("| Erro ao adicionar o veiculo na garagem.");
        }
    }
    
public static void buscandoRota(Usuario usuarioLogado, Scanner read) {
    if (usuarioLogado == null) {
        System.err.println("Você precisa estar logado para usar essa funcionalidade.");
        return;
    }

    System.out.print("Digite o ponto de origem (Cidade, UF): ");
    String origem = read.nextLine();

    System.out.print("Digite o ponto de destino (Cidade, UF): ");
    String destino = read.nextLine();

    double distancia = CalcularRota.calcularDistancia(origem, destino);
    if (distancia <= 0) {
        System.err.println("Não foi possível calcular a distância.");
        return;
    }

    List<Veiculo> meusVeiculos = GaragemDAO.listarTodosOsVeiculosDoUsuario(usuarioLogado.getId());
    
    if (meusVeiculos == null || meusVeiculos.isEmpty()) {
        System.err.println("Você não possui veículos cadastrados.");
        return;
    }

    double[] precos = EstadoDAO.buscarPrecosPorEstado(usuarioLogado.getEstadoId());

    Veiculo veiculoMaisEconomico = null;
    double menorCusto = Double.MAX_VALUE;
    VeiculoCombustao melhorCombustao = null;
    double menorCustoCombustao = Double.MAX_VALUE;

    for (Veiculo v : meusVeiculos) {
        double custo = 0;
        if (v instanceof VeiculoCombustao combustao) {
            custo = combustao.calcularGasto(distancia, precos[0]);
            if (custo < menorCustoCombustao) {
                menorCustoCombustao = custo;
                melhorCombustao = combustao;
            }
        } else if (v instanceof VeiculoEletrico eletrico) {
            custo = eletrico.calcularGasto(distancia, precos[3]);
        } else {
            continue;
        }

        if (custo < menorCusto) {
            menorCusto = custo;
            veiculoMaisEconomico = v;
        }
    }

    System.out.println("\nVeículo mais econômico da sua garagem:");
    System.out.println(veiculoMaisEconomico);
    System.out.printf("Custo estimado: R$ %.2f\n", menorCusto);

    System.out.println("Escolha um veículo para a viagem:");
    for (int i = 0; i < meusVeiculos.size(); i++) {
        System.out.println((i + 1) + " - " + meusVeiculos.get(i));
    }

    int escolha = read.nextInt();
    read.nextLine();

    if (escolha < 1 || escolha > meusVeiculos.size()) {
        System.err.println("Escolha inválida.");
        return;
    }

    Veiculo veiculoEscolhido = meusVeiculos.get(escolha - 1);
    double avarege = veiculoEscolhido.getAvaregeCons();

    if (veiculoEscolhido instanceof VeiculoCombustao combustao) {
        double litros = distancia / avarege;
        double custo = combustao.calcularGasto(distancia, precos[0]);
        System.out.printf("Consumo: %.2f km/L | Litros: %.2f | Custo: R$ %.2f\n", avarege, litros, custo);

        int emissaoPorKm = melhorCombustao.getQntCO2();
        double emissaoTotalKg = (emissaoPorKm * distancia) / 1000.0;
        System.out.printf("Emissão estimada: %.2f kg CO₂\n", emissaoTotalKg);

    } else if (veiculoEscolhido instanceof VeiculoEletrico eletrico) {
        System.out.print("Digite o percentual atual da bateria (%): ");
        int bateriaPercentual = read.nextInt();
        read.nextLine();

        int duracaoBateriaKm = eletrico.getDurBat();
        double autonomiaAtual = ((bateriaPercentual - 10) * duracaoBateriaKm) / 100.0;

        System.out.printf("Autonomia atual: %.2f km\n", autonomiaAtual);
        if (distancia > autonomiaAtual) {
            double distanciaRestante = distancia - autonomiaAtual;
            int recargas = (int) Math.ceil(distanciaRestante / duracaoBateriaKm);
            System.out.printf("Você precisará recarregar %d vez(es).\n", recargas);
        } else {
            System.out.println("Você não precisará recarregar durante a viagem.");
        }

        double energia = distancia / avarege;
        double custo = eletrico.calcularGasto(distancia, precos[3]);
        System.out.printf("Consumo: %.2f km/kWh | Energia: %.2f kWh | Custo: R$ %.2f\n", avarege, energia, custo);

        if (melhorCombustao != null) {
            double custoGasolina = melhorCombustao.calcularGasto(distancia, precos[0]);
            double custoAlcool   = melhorCombustao.calcularGasto(distancia, precos[1]);
            double custoDiesel   = melhorCombustao.calcularGasto(distancia, precos[2]);

            System.out.printf("Comparado ao veículo %s %s:\n", melhorCombustao.getBrand(), melhorCombustao.getModel());
            System.out.printf("- Economia gasolina: R$ %.2f\n", custoGasolina - custo);
            System.out.printf("- Economia álcool: R$ %.2f\n", custoAlcool - custo);
            System.out.printf("- Economia diesel: R$ %.2f\n", custoDiesel - custo);
        }
    }
}


    
    // METODOS DO SUBMENU DE EXIBIÇAO DE VEICULOS
    
    public static void mostrarTodosOsVeiculos(Usuario usuarioLogado, Scanner read) {
        System.out.println("-----------------------Veiculos Cadastrados-------------------------");
        List<Veiculo> meusVeiculos = GaragemDAO.listarTodosOsVeiculosDoUsuario(usuarioLogado.getId());
                                    
        for (Veiculo v : meusVeiculos) {
        System.out.println(v);
        }
    }
    
    public static void mostrarVeiculoACombustao(Usuario usuarioLogado, Scanner read) {
        System.out.println("------------------Veiculos a Combustao Cadastrados-------------------");
        List<VeiculoCombustao> meusCombustao = GaragemDAO.listarVeiculosCombustaoDoUsuario(usuarioLogado.getId());
        if (meusCombustao.isEmpty()) {
        System.err.println("Você não possui veículos a combustão cadastrados.");
        } else {
        meusCombustao.forEach(System.out::println);
        }
    }
    
    public static void mostrarVeiculoEletrico(Usuario usuarioLogado, Scanner read, int estadoId) {
        System.out.println("-------------------Veiculos Eletricos Cadastrados--------------------");
        List<VeiculoEletrico> meusEletricos = GaragemDAO.listarVeiculosEletricosDoUsuario(estadoId);
        if (meusEletricos.isEmpty()) {
            System.err.println("Você não possui veículos a combustão cadastrados.");
        } else {
        meusEletricos.forEach(System.out::println);
        }
    }
    
    public static void menuDaGaragem(Usuario usuarioLogado, Scanner read, int opcao) {
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
                    Menu.mostrarTodosOsVeiculos(usuarioLogado, read);
                }
                               
                case 2 -> {
                    Menu.mostrarVeiculoACombustao(usuarioLogado, read);
                }
                
                case 3 -> {
                    Menu.mostrarVeiculoEletrico(usuarioLogado, read, opcao);
                }
            
                case 4 -> {
                    System.out.println("Retornando ao menu..");
                    garagem = false;
                }

                default -> System.err.println("Opção invalida!");
                }
            }
        }
    
    
    
    //EXIBINDO O MENU PRINCIPAL
    public static void exibindoMenuPrincipal(Usuario usuarioLogado,Scanner read) {
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
                    System.out.println("|");
                    System.out.print("Escolha uma opção: ");

                    int opcao = read.nextInt();
                    read.nextLine();

                    switch (opcao) {
                        case 1 -> {
                            Menu.selecionarVeiculoACombustao(usuarioLogado, read);

                        }

                        case 2 -> {
                            Menu.selecionarVeiculoEletrico(usuarioLogado, read);
                        }

                        case 3 -> {
                            Menu.menuDaGaragem(usuarioLogado, read, opcao);
                        }

                        case 4 -> {
                            Menu.buscandoRota(usuarioLogado, read);
                }


                        case 5 -> {
                            System.out.println("Saindo...");
                            menuAtivo = false;
                        }

                        default -> System.err.println("Opção invalida!");
                    }
                }
    }
}