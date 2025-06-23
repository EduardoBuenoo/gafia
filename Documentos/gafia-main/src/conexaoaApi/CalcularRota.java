package conexaoaApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CalcularRota {

    private static final String API_KEY = "AIzaSyBJKr9J4jBMoRkpzaBYeVZ0NgMKi4mFaW0";

    public static double calcularDistancia(String origem, String destino) {
        try {
            // Monta a URL da requisição
            String origemEncoded = URLEncoder.encode(origem, "UTF-8");
            String destinoEncoded = URLEncoder.encode(destino, "UTF-8");
            String urlString = String.format(
                "https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&key=%s", origemEncoded, destinoEncoded, API_KEY
            );
            
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Lê a resposta
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder resposta = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                resposta.append(inputLine);
            }
            in.close();

            // Converte a resposta em JSON
            JSONObject json = new JSONObject(resposta.toString());
            JSONArray routes = json.getJSONArray("routes");
            if (routes.length() == 0) {
                System.out.println("Nenhuma rota encontrada.");
                return -1;
            }

           double distanciaMetros = json
                .getJSONArray("routes")
                .getJSONObject(0)
                .getJSONArray("legs")
                .getJSONObject(0)
                .getJSONObject("distance")
                .getDouble("value");

            double distanciaKm = distanciaMetros / 1000.0;
            return distanciaKm;

        } catch (IOException | JSONException e) {
            return -1;

        }
    }
        //Calculo do Combustivel
        public static void calcularGasto(double distanciaKm, double precoCombustivel, double consumoLitros) {
            if (distanciaKm <= 0 || consumoLitros <=0) {
                System.out.println("Dados invalidos.");
                return;
            }
            double litrosNecessarios = distanciaKm / consumoLitros;
            double custoTotal = litrosNecessarios * precoCombustivel;
            
            System.out.printf("Distância: %.2f km%n", distanciaKm);
            System.out.printf("Consumo do veículo: %.2f km/L%n", consumoLitros);
            System.out.printf("Litros necessários: %.2f L%n", litrosNecessarios);
            System.out.printf("Custo total estimado (com combustível a R$ %.2f): R$ %.2f%n", precoCombustivel, custoTotal);
    }
}
    
