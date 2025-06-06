package banco;

//importar as bibliotecas
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConnection {
    private static final String host = "dpg-cvriq3be5dus738a7r3g-a.oregon-postgres.render.com";
    private static final String user = "dbgafia_tp8h_user";
    private static final String password = "YcBpnDhYNSD1OzYdr2Rijt3jEEiZJhOa";
    private static final String driver = "org.postgresql.Driver";
    private static final String dataBaseName = "dbgafia_tp8h";
    private static final String dataBasePrefix = "jdbc:postgresql://";
    private static final String dataBasePort = "5432";
    
    private static final String url = dataBasePrefix + host + ":"+dataBasePort+"/" + dataBaseName + "?sslmode=require";


public static Connection conectar() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado: " + e.getMessage());
        } catch (SQLException e) {
           
        }
        return null;
     }

   }