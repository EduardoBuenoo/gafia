package Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgre {

    private static final String URL = "jdbc:postgresql://dpg-cvriq3be5dus738a7r3g-a.oregon-postgres.render.com:5432/dbgafia_tp8h";
    private static final String USUARIO = "dbgafia_tp8h_user";
    private static final String SENHA = "YcBpnDhYNSD1OzYdr2Rijt3jEEiZJhOa";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }   
    
}

