package repository;

import domain.weather;
import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.Vector;

public class Repository {
    private static final String JDBC_URL = "jdbc:sqlite:data/test_db.db";

    private static Connection conn = null;


    public void openConnection()
    {
        try
        {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection()
    {
        try
        {
            conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void createSchema(){

        try
        {
            final Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS databaseweather(startHour int, endHour int, temperature int, precipitation int, description varchar(100));");
            stmt.close();
        }
        catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }
    public void AddInSchema(){

        try
        {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO databaseweather VALUES (?, ?, ?,?,?),(?, ?, ?,?,?),(?, ?, ?,?,?),(?, ?, ?,?,?),(?, ?, ?,?,?),(?, ?, ?,?,?)");
            statement.setInt(1, 12);
            statement.setInt(2, 14);
            statement.setInt(3, 18);
            statement.setInt(4, 23);
            statement.setString(5, "sunny, wind 5km/h");
            statement.setInt(6, 6);
            statement.setInt(7, 9);
            statement.setInt(8, 10);
            statement.setInt(9, 13);
            statement.setString(10, "foggy");
            statement.setInt(11, 9);
            statement.setInt(12, 12);
            statement.setInt(13, 13);
            statement.setInt(14, 7);
            statement.setString(15, "cloudy, wind 2km/h");
            statement.setInt(16, 18);
            statement.setInt(17, 20);
            statement.setInt(18, 20);
            statement.setInt(19, 78);
            statement.setString(20, "rainy, hearvy rain");
            statement.setInt(21, 14);
            statement.setInt(22, 18);
            statement.setInt(23, 20);
            statement.setInt(24, 62);
            statement.setString(25, "rainy, wind 10km/h, real feel 18C");


            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Vector<weather> getAll(){

        Vector<weather> weathers;
        try {
            weathers = new Vector<>();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM databaseweather");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                weather w = new weather(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5)

                );
                weathers.add(w);
            }
            rs.close();
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return weathers;
    }
    public void UpdateSchema(String Prec, String UpdatedPrec) {

        try {
            PreparedStatement statement = conn.prepareStatement("UPDATE databaseweather set precipitation = ? WHERE precipitation=?");
            statement.setString(1, UpdatedPrec);
            statement.setString(2,Prec);
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
