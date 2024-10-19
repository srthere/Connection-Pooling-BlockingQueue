import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException {
        Driver driver = new Driver();
        driver.executeWithoutConnectionPool();

        ConnectionPool connectionPool = new ConnectionPool();
        connectionPool.createConnectionPool();

        driver.executeWithConnectionPool(connectionPool);
        connectionPool.closeConnections();
    }
}