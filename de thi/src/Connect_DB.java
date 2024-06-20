
import java.sql.*;

public class Connect_DB {
    static Connection connection = null;

    public Connect_DB() throws SQLException {
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=hung;integratedSecurity=true;"+
                "encrypt=true;trustServerCertificate=true;";

        connection = DriverManager.getConnection(connectionUrl);
    }
    public Connect_DB(String databaseName) throws SQLException{
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName="+databaseName+";integratedSecurity=true;"+
                "encrypt=true;trustServerCertificate=true;";

        connection = DriverManager.getConnection(connectionUrl);
    }
    public ResultSet getTable(String tableName) throws SQLException{
        Statement statement = null;
        ResultSet resultSet = null;
        statement = connection.createStatement();
        // Thực thi câu lệnh SQL
        String sql = "SELECT * FROM "+tableName;
        resultSet = statement.executeQuery(sql);
        // Xử lý kết quả
        return resultSet;
    }
    public Connection getConnection(){
        return connection;
    }
}
