package veiculos;

import java.util.HashMap;
import java.util.Map;

public class VeiculoCombustao extends Veiculo {
    
    private int qntCO2;
    
    public VeiculoCombustao(int id, String ano, int qntCO2, String marca, String modelo, double consumoMedio) {
        super(id, ano, marca, modelo, consumoMedio);
        this.qntCO2 = qntCO2;
        this.consumoMedio = consumoMedio;
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
    double litros = distancia / this.consumoMedio;
    return litros * precoLitro;
}


    
    // Mapas estáticos
    private static final Map<Integer, VeiculoCombustao> carroc = new HashMap<>();

    static {
    // Preenchendo carroc com consumo médio (km/l)
    carroc.put(1, new VeiculoCombustao(1, "2024", 121, "Toyota", "Corolla Hybrid", 15.6));
    carroc.put(2, new VeiculoCombustao(2, "2024", 129, "Toyota", "Corolla Cross Hybrid", 13.7));
    carroc.put(3, new VeiculoCombustao(3, "2024", 136, "Renault", "Kwid", 13.2));
    carroc.put(4, new VeiculoCombustao(4, "2024", 142, "Chevrolet", "Onix Plus", 11.8));
    carroc.put(5, new VeiculoCombustao(5, "2024", 145, "Volkswagen", "Polo TSI", 12.2));
    carroc.put(6, new VeiculoCombustao(6, "2024", 146, "Chevrolet", "Onix", 13.3));
    carroc.put(7, new VeiculoCombustao(7, "2024", 147, "Fiat", "Cronos", 14.5));
    carroc.put(8, new VeiculoCombustao(8, "2024", 147, "Peugeot", "208", 11.9));
    carroc.put(9, new VeiculoCombustao(9, "2024", 150, "Fiat", "Mobi", 12.13));
    carroc.put(10, new VeiculoCombustao(10, "2024", 151, "Hyundai", "HB20", 13.5));
    carroc.put(11, new VeiculoCombustao(11, "2023", 232, "Toyota", "Hilux", 10.3));
    carroc.put(12, new VeiculoCombustao(12, "2023", 184, "Ford", "Ranger", 11.7));
    carroc.put(13, new VeiculoCombustao(13, "2023", 0, "Mitsubishi", "L200 Triton", 10.2));
    carroc.put(14, new VeiculoCombustao(14, "2023", 0, "Nissan", "Frontier", 9.9));
    carroc.put(15, new VeiculoCombustao(15, "2023", 0, "Fiat", "Pulse", 11.6));
}

    //metodo para puxar em outras classes
    public static Map<Integer, VeiculoCombustao> getMapaVeiculos() {
        return carroc;
    }

}

