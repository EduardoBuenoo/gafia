package Vehicles;

import Database.VeiculoDAO;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VeiculoCombustao extends Veiculo {
    
    private int qntCO2;
    
    public VeiculoCombustao(int id, String ano, int qntCO2, String marca, String modelo, double consumoMedioC) {
        super(id, ano, marca, modelo, consumoMedioC);
        this.qntCO2 = qntCO2;
        this.averageCons = consumoMedioC;
    }

    public int getQntCO2() {
        return qntCO2;
    }

    public void setQntCO2(int qntCO2) {
        this.qntCO2 = qntCO2;
    }
    
    
    @Override
    public String toString() {
    return super.toString() + " - Emissao de CO2: " + qntCO2 + " g/km";
}
    
    @Override
    public double calcularGasto(double distancia, double precoLitro) {
    double litros = distancia / this.averageCons;
    return litros * precoLitro;
    }

}

