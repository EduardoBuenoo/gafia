/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Interface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Database.UsuarioDAO;
import Gafia.Usuario;

/**
 * FXML Controller class
 *
 * @author kaio
 */
public class LoginController implements Initializable {
    

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void handleLogin() {
        String login = txtUsuario.getText();
        String senha = txtSenha.getText();
        
        Usuario usuarioLogado = usuarioDAO.fazerLogin(login, senha);

        if (usuarioLogado != null) {
            mostrarMensagem("Login realizado com sucesso!");
            // Aqui futuramente você pode abrir outra tela
        } else {
            mostrarMensagem("Usuário ou senha incorretos.");
        }
    }

    private void mostrarMensagem(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GAFIA");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
