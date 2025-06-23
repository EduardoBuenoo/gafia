package veiculos;

import gafia.Usuario;
import java.util.Map;
import java.util.Scanner;

public abstract class Veiculo {
    protected int id;
    protected String ano;
    protected String marca;
    protected String modelo;
    protected double consumoMedio;
    

    public Veiculo(int id, String ano, String marca, String modelo, double consumoMedio) {
        this.id = id;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.consumoMedio = consumoMedio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getConsumoMedio() {
        return consumoMedio;
    }

    public void setConsumoMedio(double consumoMedio) {
        this.consumoMedio = consumoMedio;
    }
    
    
    
    @Override
    public String toString() {
    return marca + " " + modelo + " (" + ano + ")";
    }
    
    private static void escolherVeiculo(Scanner leia, Usuario usuario, Map<Integer, ? extends Veiculo> mapa) {    
    System.out.print("Digite o ID do veículo que deseja adicionar ao seu cadastro: ");
    int id = leia.nextInt();
    leia.nextLine();

    Veiculo escolhido = mapa.get(id);
    if (escolhido != null) {
        usuario.adicionarVeiculo(escolhido);
        System.out.println("Veículo adicionado com sucesso!");
    } else {
        System.err.println("ID inválido!");
    }
}
    
    public abstract double calcularGasto(double distancia, double precoEnergiaOuCombustivel);

    public class GafiaBanner {
    public static void mostrar() {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("|                              ______                                           |");
        System.out.println("|                             /|_||_\\`.__                                       |");
        System.out.println("|                            (   _    _ _\\                                      |");
        System.out.println("|                            =`-(_)--(_)-'                                      |");
        System.out.println("|                                                                               |");
        System.out.println("|                  GAFIA - AUTONOMIA DE CARROS ELÉTRICOS                        |");
        System.out.println("---------------------------------------------------------------------------------");
    }
}


}
    

