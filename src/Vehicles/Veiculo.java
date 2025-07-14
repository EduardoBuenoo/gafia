package Vehicles;

import Gafia.Usuario;
import java.util.Map;
import java.util.Scanner;

public abstract class Veiculo {
    protected int id;
    protected String year;
    protected String brand;
    protected String model;
    protected double averageCons;
    

    public Veiculo(int id, String year, String brand, String model, double averageCons) {
        this.id = id;
        this.year = year;
        this.brand = brand;
        this.model = model;
        this.averageCons = averageCons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getAvaregeCons() {
        return averageCons;
    }

    public void setConsumoMedio(double averageCons) {
        this.averageCons = averageCons;
    }
    
    
    
    @Override
    public String toString() {
    return brand + " " + model + " (" + year + ")";
    }
    
    private static void escolherVeiculo(Scanner read, Usuario usuario, Map<Integer, ? extends Veiculo> mapa) {    
    System.out.print("Digite o ID do veículo que deseja adicionar ao seu cadastro: ");
    int id = read.nextInt();
    read.nextLine();

    Veiculo escolhido = mapa.get(id);
    if (escolhido != null) {
        usuario.adicionarVeiculo(escolhido);
        System.out.println("Veículo adicionado com sucesso!");
    } else {
        System.err.println("ID inválido!");
    }
}
    
    public abstract double calcularGasto(double distancia, double precoEnergiaOuCombustivel);


    
        public static void banner() {
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
    

