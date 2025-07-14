package Vehicles;

import java.util.HashMap;
import java.util.Map;

public class VeiculoEletrico extends Veiculo {
       
    private double capacidadeBateria;
    private int durBat;
    
    public VeiculoEletrico(int id, String ano, double capacidadeBateria, int durBat, String marca, String modelo, double consumoMedioE) {
        super(id, ano, marca, modelo, consumoMedioE);
        this.durBat = durBat;
        this.averageCons = consumoMedioE;
        this.capacidadeBateria = capacidadeBateria;
    }

    public int getDurBat() {
        return durBat;
    }

    public void setDurBat(int durBat) {
        this.durBat = durBat;
    }
    
    public double getCapacidadeBateria() {
        return capacidadeBateria;
    }
    
    public void setCapacidadeBateria(double capacidadeBateria) {
        this.capacidadeBateria = capacidadeBateria;
    }
    
    
    @Override
    public String toString() {
    return super.toString() + " - Duração da bateria: " + durBat + " km";
    }
    
    @Override
    public double calcularGasto(double distancia, double precoKWh) {
    double kwhNecessarios = distancia / this.averageCons;
    return kwhNecessarios * precoKWh;
    }
}
