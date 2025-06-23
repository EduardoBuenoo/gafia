package veiculos;

import java.util.HashMap;
import java.util.Map;

public class VeiculoEletrico extends Veiculo {
       
    private double capacidadeBateria;
    private int durBat;
    
    public VeiculoEletrico(int id, String ano, double capacidadeBateria, int durBat, String marca, String modelo, double consumoMedio) {
        super(id, ano, marca, modelo, consumoMedio);
        this.durBat = durBat;
        this.consumoMedio = consumoMedio;
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
    double kwhNecessarios = distancia / this.consumoMedio;
    return kwhNecessarios * precoKWh;
}

    
    private static final Map<Integer, VeiculoEletrico> carroe = new HashMap<>();
  
    static {
        // Preenchendo carroe
    carroe.put(1,  new VeiculoEletrico(1,  "2024", 38.0, 280, "BYD",       "Dolphin Mini", 15.6));
    carroe.put(2,  new VeiculoEletrico(2,  "2023", 44.9, 291, "BYD",       "Dolphin",      14.0));
    carroe.put(3,  new VeiculoEletrico(3,  "2023", 82.5, 451, "BYD",       "Seal",         8.0));
    carroe.put(4,  new VeiculoEletrico(4,  "2023", 60.5, 294, "BYD",       "Yuan Plus",    6.8));
    carroe.put(5,  new VeiculoEletrico(5,  "2023", 45.1, 250, "BYD",       "Yuan Pro",     7.5));
    carroe.put(6,  new VeiculoEletrico(6,  "2023", 26.6, 215, "BYD",       "Song Plus",    6.7));
    carroe.put(7,  new VeiculoEletrico(7,  "2023", 97.6, 300, "BYD",       "Tang",         5.8));
    carroe.put(8,  new VeiculoEletrico(8,  "2023", 85.4, 300, "BYD",       "Han",          7.2));
    carroe.put(9,  new VeiculoEletrico(9,  "2024", 87.0, 500, "BYD",       "Seal U",       7.5));
    carroe.put(10, new VeiculoEletrico(10, "2024", 18.3, 400, "BYD",       "King",         7.0));
    carroe.put(11, new VeiculoEletrico(11, "2023", 48.0, 232, "GWM",       "Ora 03",       9.0));
    carroe.put(12, new VeiculoEletrico(12, "2023", 63.0, 319, "GWM",       "Ora 03 GT",    8.5));
    carroe.put(13, new VeiculoEletrico(13, "2021", 30.2, 161, "JAC",       "E-JS1",        9.5));
    carroe.put(14, new VeiculoEletrico(14, "2019", 41.0, 200, "JAC",       "iEV20",        8.3));
    carroe.put(15, new VeiculoEletrico(15, "2019", 40.0, 300, "JAC",       "iEV40",        7.9));
    carroe.put(16, new VeiculoEletrico(16, "2022", 26.8, 235, "Renault",   "Kwid E-Tech",  8.6));
    carroe.put(17, new VeiculoEletrico(17, "2018", 52.0, 385, "Renault",   "Zoe E-Tech",   7.2));
    carroe.put(18, new VeiculoEletrico(18, "2024", 51.0, 338, "Volvo",     "EX30",         6.9));
    carroe.put(19, new VeiculoEletrico(19, "2024", 69.0, 338, "Volvo",     "EX30 Extended Range", 7.0));
    carroe.put(20, new VeiculoEletrico(20, "2021", 78.0, 400, "Volvo",     "XC40 Recharge", 6.3));
    carroe.put(21, new VeiculoEletrico(21, "2022", 75.0, 420, "Volvo",     "C40 Recharge", 6.5));
    carroe.put(22, new VeiculoEletrico(22, "2019", 40.0, 192, "Nissan",    "Leaf",         6.4));
    carroe.put(23, new VeiculoEletrico(23, "2024", 85.0, 443, "Chevrolet", "Equinox EV",   6.6));
    carroe.put(24, new VeiculoEletrico(24, "2024", 70.4, 590, "BMW",       "i4",           6.8));
    carroe.put(25, new VeiculoEletrico(25, "2021", 80.0, 460, "BMW",       "iX3",          6.1));
    carroe.put(26, new VeiculoEletrico(26, "2022", 111.5, 630, "BMW",      "iX",           5.8));
    carroe.put(27, new VeiculoEletrico(27, "2019", 86.5, 417, "Audi",      "e-tron",       5.9));
    carroe.put(28, new VeiculoEletrico(28, "2022", 82.0, 520, "Audi",      "Q4 e-tron",    6.3));
    carroe.put(29, new VeiculoEletrico(29, "2025", 90.0, 470, "Jaguar",    "I-Pace",       6.0));
    carroe.put(30, new VeiculoEletrico(30, "2022", 50.0, 340, "Peugeot",   "e-208",        8.1));
    carroe.put(31, new VeiculoEletrico(31, "2023", 50.0, 345, "Peugeot",   "e-2008",       7.2));
    carroe.put(32, new VeiculoEletrico(32, "2021", 77.0, 491, "Tesla",     "Model 3",      7.5));
    carroe.put(33, new VeiculoEletrico(33, "2022", 62.5, 505, "Tesla",     "Model Y",      7.2));
    }
    //metodo para puxar em outras classes
    public static Map<Integer, VeiculoEletrico> getMapaVeiculos() {
        return carroe;
    }

}
