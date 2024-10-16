package configuration;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author CruzF
 */
public class ConnectionBD {
    Connection conection;
    
    public ConnectionBD (){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conection = DriverManager.getConnection("jdbc:mysql://localhost:3306/prueba","root","");
       
        } catch (Exception e) {
            System.err.println("El error está en la conexión: "+e);
        }
    }
    
    /**
     * Método que me permite obtener la conexión con mi BD
     * @return Conexión
     */
    public Connection getConnectionBD(){
        return conection;
    }
}
