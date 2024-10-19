import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String JDBC_URL = "jdbc:h2:mem:testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public void executeQuery(final Connection connection) {
        try {
            connection.createStatement().executeQuery("select 1");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
