package banco;

import gafia.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    
    //SALVAR O USUARIO NO BANCO DE DADOS
    public boolean saveUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, sobrenome, cpf, dtnasc, email, telefone, senha, id_estado) " +  "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = BDConnection.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSobrenome());
            stmt.setString(3, usuario.getCPF());
            stmt.setString(4, usuario.getDataNasc());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getTelefone());
            stmt.setString(7, usuario.getSenha());
            stmt.setInt(8, usuario.getEstadoId());
            
            stmt.executeUpdate();
            System.out.println("Cadastro salvo com sucesso!");
            return true;
           
        } catch (SQLException e) {
            System.out.println("Erro no cadastro de usuario: " + e.getMessage());
            return false;
        }
    }
    //METODO DE LOGIN
  public Usuario fazerLogin(String email, String senha) {
    String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

    try (Connection conn = BDConnection.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, email);
        stmt.setString(2, senha);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Usuario usuario = new Usuario(
                rs.getString("nome"),
                rs.getString("sobrenome"),
                rs.getString("cpf"),
                rs.getString("dtnasc"),
                rs.getString("email"),
                rs.getString("telefone"),
                rs.getString("senha"),
                rs.getInt("id_estado")
            );
            return usuario;
        } else {
            return null; // Login inválido
        }

    } catch (SQLException e) {
        System.out.println("Erro ao fazer login: " + e.getMessage());
        return null;
      }
    }
  //buscar o estado cadastrado pelo usuario
 
    public static int buscarEstadoIdPorUsuario(int usuarioId) {
        int estadoId = -1;
        String sql = "SELECT estado_id FROM usuarios WHERE id = ?";
        
        try (Connection conn = BDConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                estadoId = rs.getInt("estado_id");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar estado do usuario: " + e.getMessage());
        }
        
        return estadoId;
    } 
}
    
