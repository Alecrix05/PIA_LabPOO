package DBCon;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    
    public static final String URL = "jdbc:mysql://localhost:3306/Labpoo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String CLAVE = "123456";
    
    public Connection getDbConn(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
        }catch(Exception e){
            System.out.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}