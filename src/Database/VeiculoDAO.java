package Database;

import Vehicles.VeiculoCombustao;
import Vehicles.VeiculoEletrico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kaio
 */
public class VeiculoDAO {
    
    //Listando os veiculos eletricos
    public static Map<Integer, String> listarVeiculosEletricos() {
        Map<Integer, String> veiculosE = new HashMap<>();
        
        String sql = """
        SELECT 
            ce.id_carro_e,
            ce.ano_carro_e,
            m.nm_marca,
            mo.nm_modelo
        FROM carro_eletrico ce
        JOIN marca m ON ce.id_marca = m.id_marca
        JOIN modelo mo ON ce.id_modelo = mo.id_modelo
        ORDER BY ce.id_carro_e;
        """;
        
        try (Connection conn = BDConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id_carro_e");
                String marca = rs.getString("nm_marca");
                String modelo = rs.getString("nm_modelo");
                int ano = rs.getInt("ano_carro_e");
                
                String infoCarroE = marca + " " + modelo + " " + ano;
                veiculosE.put(id, infoCarroE);
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar os veiculos eletricos: " + e.getMessage());
        }
        
        return veiculosE;
    }
    
    //Listando os veiculos a combustao
    public static Map<Integer, String> listarVeiculosCombustao() {
        Map<Integer, String> veiculosC = new HashMap<>();
        
        String sql = """
        SELECT
            cc.id_carro_c,
            cc.ano_carro_c,
            m.nm_marca,
            mo.nm_modelo
        FROM carro_combustao cc
        JOIN marca m ON cc.id_marca = m.id_marca
        JOIN modelo mo ON cc.id_modelo = mo.id_modelo
        ORDER BY cc.id_carro_c;
        """;
        
        try (Connection conn = BDConnection.conectar();
             PreparedStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id_carro_c");
                String marca = rs.getString("nm_marca");
                String modelo = rs.getString("nm_modelo");
                int ano = rs.getInt("ano_carro_c");
                
                String infoCarroC = marca + " " + modelo + " " + ano;
                veiculosC.put(id, infoCarroC);
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar os veiculos a combustão: " + e.getMessage());
        }
        
        return veiculosC;

    }
}
