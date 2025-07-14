package Database;

import Vehicles.Veiculo;
import Vehicles.VeiculoCombustao;
import Vehicles.VeiculoEletrico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kaio
 */
public class GaragemDAO {
    
    //Adicionando um veiculo no cadastro do usuario
    public boolean adicionarVeiculoNaGaragem(int idUsuario, Integer idCarroE, Integer idCarroC) {
        String sql = "INSERT INTO garagem_usuario (id_usuario, id_carro_e, id_carro_c) VALUES (?, ?, ?)";
        
        try (Connection conn = BDConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUsuario);
            
            if (idCarroE != null) {
                stmt.setInt(2, idCarroE);
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            if (idCarroC != null) {
                stmt.setInt(3, idCarroC);
            } else {
               stmt.setNull(3, java.sql.Types.INTEGER);
            }
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar o veiculo a garagem: " + e.getMessage());
            return false;
        }
    }
    
    //Buscando veiculos eletricos
    public static List<VeiculoEletrico> listarVeiculosEletricosDoUsuario(int usuarioId) {
    List<VeiculoEletrico> lista = new ArrayList<>();

    String sql = """
        SELECT ce.id_carro_e, ce.ano_carro_e, ce.carga_bateria, ce.dur_bateria,
               m.nm_marca, mo.nm_modelo, ce.media_consumo
        FROM garagem_usuario gu
        JOIN carro_eletrico ce ON gu.id_carro_e = ce.id_carro_e
        JOIN marca m ON ce.id_marca = m.id_marca
        JOIN modelo mo ON ce.id_modelo = mo.id_modelo
        WHERE gu.id_usuario = ?
    """;

    try (Connection conn = BDConnection.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_carro_e");
            String ano = rs.getString("ano_carro_e");
            double cargaBateria = rs.getDouble("carga_bateria");
            int durBat = rs.getInt("dur_bateria");
            String marca = rs.getString("nm_marca");
            String modelo = rs.getString("nm_modelo");
            double consumoMedio = rs.getDouble("media_consumo");

            VeiculoEletrico ve = new VeiculoEletrico(id, ano, cargaBateria, durBat, marca, modelo, consumoMedio);
            lista.add(ve);
        }

    } catch (SQLException e) {
        System.err.println("Erro ao buscar veículos elétricos do usuário: " + e.getMessage());
    }

    return lista;
}
    
//Buscando veiculos a combustao
    public static List<VeiculoCombustao> listarVeiculosCombustaoDoUsuario(int usuarioId) {
    List<VeiculoCombustao> lista = new ArrayList<>();

    String sql = """
        SELECT cc.id_carro_c, cc.ano_carro_c, cc.qnt_co2, cc.km_litro,
               m.nm_marca, mo.nm_modelo
        FROM garagem_usuario gu
        JOIN carro_combustao cc ON gu.id_carro_c = cc.id_carro_c
        JOIN marca m ON cc.id_marca = m.id_marca
        JOIN modelo mo ON cc.id_modelo = mo.id_modelo
        WHERE gu.id_usuario = ?
    """;

    try (Connection conn = BDConnection.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_carro_c");
            String ano = rs.getString("ano_carro_c");
            int qntCO2 = rs.getInt("qnt_co2");
            double consumo = rs.getDouble("km_litro");
            String marca = rs.getString("nm_marca");
            String modelo = rs.getString("nm_modelo");

            VeiculoCombustao vc = new VeiculoCombustao(id, ano, qntCO2, marca, modelo, consumo);
            lista.add(vc);
        }

    } catch (SQLException e) {
        System.err.println("Erro ao buscar veículos a combustão do usuário: " + e.getMessage());
    }

    return lista;
}

    
    //Buscar o veiculo na garagem
   public static List<Veiculo> listarTodosOsVeiculosDoUsuario(int usuarioId) {
   List<Veiculo> TodosOsVeiculos = new ArrayList<>();
   
   List<VeiculoEletrico> eletricos = listarVeiculosEletricosDoUsuario(usuarioId);
   TodosOsVeiculos.addAll(eletricos);
   
   List<VeiculoCombustao> combustao = listarVeiculosCombustaoDoUsuario(usuarioId);
   TodosOsVeiculos.addAll(combustao);
   
   return TodosOsVeiculos;
   }

}
