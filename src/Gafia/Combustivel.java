package Gafia;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Combustivel {
    private static final Map<Integer, String> estados = new HashMap<>();

    static {
        estados.put(1, "Acre");
        estados.put(2, "Alagoas");
        estados.put(3, "Amapa");
        estados.put(4, "Amazonas");
        estados.put(5, "Bahia");
        estados.put(6, "Ceara");
        estados.put(7, "Distrito Federal");
        estados.put(8, "Espirito Santo");
        estados.put(9, "Goias");
        estados.put(10, "Maranhao");
        estados.put(11, "Mato Grosso");
        estados.put(12, "Mato Grosso do Sul");
        estados.put(13, "Minas Gerais");
        estados.put(14, "Para");
        estados.put(15, "Paraiba");
        estados.put(16, "Parana");
        estados.put(17, "Pernambuco");
        estados.put(18, "Piaui");
        estados.put(19, "Rio de Janeiro");
        estados.put(20, "Rio Grande do Norte");
        estados.put(21, "Rio Grande do Sul");
        estados.put(22, "Rondonia");
        estados.put(23, "Roraima");
        estados.put(24, "Santa Catarina");
        estados.put(25, "Sao Paulo");
        estados.put(26, "Sergipe");
        estados.put(27, "Tocantins");

    }

    public static String getEstado(int numero) {
        return estados.getOrDefault(numero, "Estado nao encontrado");
    }

}

