import java.sql.Connection;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Driver {

    private static final int TOTAL_RUNS = 1000000;

    public void executeWithoutConnectionPool() throws InterruptedException {
        ZonedDateTime startTime = ZonedDateTime.now();
        DBConnection dbConnection = new DBConnection();
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        Callable<Void> task = () -> {
            Connection connection = dbConnection.createConnection();
            dbConnection.executeQuery(connection);
            connection.close();
            return null;
        };
        List<Callable<Void>> callables = createTasks(task);

        executorService.invokeAll(callables);
        executorService.close();
        long totalRunTime = Duration.between(startTime, ZonedDateTime.now()).toMillis();
        System.out.println("Total Execution Time without Connection Pooling is : " + totalRunTime + "ms");
    }

    public void executeWithConnectionPool(final ConnectionPool connectionPool) throws InterruptedException {
        ZonedDateTime startTime = ZonedDateTime.now();
        DBConnection dbConnection = new DBConnection();

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        Callable<Void> task = () -> {
            Connection connection = connectionPool.getConnection();
            dbConnection.executeQuery(connection);
            connectionPool.putsConnection(connection);
            return null;
        };
        List<Callable<Void>> callables = createTasks(task);

        executorService.invokeAll(callables);
        executorService.close();
        long totalRunTime = Duration.between(startTime, ZonedDateTime.now()).toMillis();
        System.out.println("Total Execution Time with Connection Pooling is : " + totalRunTime + "ms");
    }

    private List<Callable<Void>> createTasks(final Callable<Void> task) {
        List<Callable<Void>> callables = new ArrayList<>();

        for (int i = 0; i < TOTAL_RUNS; i++) {
            callables.add(task);
        }
        return callables;
    }

}
