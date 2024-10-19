import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    final BlockingQueue<Connection> blockingQueue = new ArrayBlockingQueue<>(10);

    public void createConnectionPool() throws SQLException, ClassNotFoundException {
        for (int i = 0; i < 10; i++) {
            DBConnection dbConnection = new DBConnection();
            blockingQueue.add(dbConnection.createConnection());
        }
    }

    public Connection getConnection() throws InterruptedException {
        return blockingQueue.take();
    }

    public void putsConnection(final Connection connection) throws InterruptedException {
        blockingQueue.put(connection);
    }

    public void closeConnections() throws SQLException {
        for (final Connection connection : blockingQueue) {
            if (!connection.isClosed()) {
                connection.close();
            }
        }
    }

}
