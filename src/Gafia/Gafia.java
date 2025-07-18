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

            System.out.println("| Você possui cadastro?");
            System.out.println("| 1 - Sim");
            System.out.println("| 2 - Não");
            System.out.print("| ");
            
            String resposta = read.nextLine();
            
            Usuario usuarioLogado = null;
            
            if(resposta.equalsIgnoreCase("2")) {
                
                Menu.realizarCadastro(read);
                System.out.println("| Cadastro concluido.");
                System.out.println("| Faça login para prosseguir.");
                System.out.println("|");
                
                System.out.println("| Digite seu email:");
                String email = read.nextLine();
                System.out.println("| Digite sua senha:");
                String senha = read.nextLine();
                
                usuarioLogado = usuarioDAO.fazerLogin(email, senha);
                if (usuarioLogado != null) {
                    Menu.exibindoMenuPrincipal(usuarioLogado, read);
                } else {
                    System.err.println("| Login falhou. Email ou senha incorretos.");
                }
                
            } else if (resposta.equalsIgnoreCase("1")){
                
                usuarioLogado = Menu.realizarloginMenu(read);
                if (usuarioLogado != null) {
                    Menu.exibindoMenuPrincipal(usuarioLogado, read);
                } else {
                System.err.println("| Login falhou. Encerrando.");
                }  
            }
        }
    }
}
    

    