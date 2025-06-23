package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;

public class EstadoDAO {
    

    public static Map<Integer, String> listarEstados() {
        Map<Integer, String> estados = new HashMap<>();

        String sql = "SELECT id_estado, nm_estado FROM estado ORDER BY id_estado";

        try (Connection conn = BDConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                estados.put(rs.getInt("id_estado"), rs.getString("nm_estado"));
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar estados: " + e.getMessage());
        }

        return estados;
    }

    public void exibirEstado(){
        String sql = "SELECT id_estado, nome_estado, uf_estado FROM estado ORDER BY nome";
        
        try (Connection conn = BDConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            System.out.println("Selecione um estado:");
            while (rs.next()) {
                int id = rs.getInt("id_estado");
                String nome = rs.getString("nome");
                String uf = rs.getString("uf");
                System.out.printf("[%d %s (%s)\n", id, nome, uf);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar estados: " + e.getMessage());
        }
    }
    
    
    
    public static double[] buscarPrecosPorEstado(int id_estado) {
        double[] preco = new double[4]; // gasolina, alcool, diesel, energia
        String sql = "SELECT id_combustivel, valor_digitado " + "FROM valor_combustivel " + "WHERE id_estado = ?";
        
        try (Connection conn = BDConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id_estado);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int tipo = rs.getInt("id_combustivel");
                double valor = rs.getDouble("valor_digitado");
                
                if (tipo >= 1 && tipo <= 4) {
                    preco[tipo - 1] = valor;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar precos: " + e.getMessage());
        }
        
        return preco;
    }
    
    
}

    
