package DataBaseUtil;

// from my other packages

import Main.Entry;
import Util.HandleError;

import java.sql.*;

public class DatabaseUtil {
    
    // database location
    private static String dataBaseLocationFile = "jdbc:sqlite:signInEntries.db";

    /**
     * connection that is required to connect to the database, all operations are done with this connection
     * to prevent error when more than one connection is open
     */
    private static Connection connection;

    /**
     * Gets the largest serial num in existence in the database
     */
    public static int GetNewestSerialNum(String tableName) throws SQLException {
        try {
            ConnectToDB();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT max(serialNum) FROM [%s]", tableName));
            return resultSet.getInt("max(serialNum)");
        } catch (SQLException e) {
            new HandleError(DatabaseUtil.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
            throw new SQLException();
        } finally {
            CloseConnectionToDB();
        }
    }

    /**
     * If DB does not exists, create DB
     * @throws SQLException if any error occurs while operating on database
     */
    public static void CheckIfDBExists() throws SQLException {
        try {
            ConnectToDB();
            
            // if connection can not be established, then we need to create a database
            if (connection == null) {
                CloseConnectionToDB();
                CreateNewDB();
            }
        } catch (SQLException e) {
            new HandleError(DatabaseUtil.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
            throw new SQLException();
        } finally {
            CloseConnectionToDB();
        }
    }

    /**
     * Create database
     * @throws SQLException if any error occurs while operating on database
     */
    private static void CreateNewDB() throws SQLException {
        ConnectToDB();
        try {
            connection = DriverManager.getConnection(dataBaseLocationFile);
        } catch (SQLException e) {
            new HandleError(DatabaseUtil.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
            throw new SQLException();
        } finally {
            CloseConnectionToDB();
        }
    }

    /**
     * Connect to database if a connection does not exist
     * @throws SQLException If connection cannot be established to the database
     */
    private static void ConnectToDB() throws SQLException {
        if (connection != null)
            return;
        try {
            connection = DriverManager.getConnection(dataBaseLocationFile);
        } catch (SQLException e) {
            new HandleError(DatabaseUtil.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
            throw new SQLException();
        }
    }

    /**
     * Terminate any connection to database.
     */
    public static void CloseConnectionToDB() {
        if (connection == null)
            return;
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            new HandleError(DatabaseUtil.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
            connection = null;
        }
    }

    /**
     * Make sure both table exists. If not exists, create both table
     * @throws SQLException if any error occurs while operating on database
     */
    public static void CheckIfTableExists() throws SQLException {
        try {
            ConnectToDB();

            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null,
                    "entries", null);

            int numOfTable = 0;
            while (resultSet.next()) {
                if ("entries".equals(resultSet.getString("TABLE_NAME"))) numOfTable++;
            }
            if (numOfTable == 1) CloseConnectionToDB();
            else CreateNewTable("entries");
        } catch (SQLException e) {
            new HandleError(DatabaseUtil.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
            throw new SQLException();
        } finally {
            CloseConnectionToDB();
        }
    }

    /**
     * create a new table in the database
     * @param tableName indication of which table to create
     * @throws SQLException if any error occurs while operating on database
     */
    public static void CreateNewTable(String tableName) throws SQLException {
        try {
            ConnectToDB();
            Statement statement = connection.createStatement();
            String SQLCommand = "";
            switch (tableName) {
                case "entries":
                    SQLCommand = "CREATE TABLE IF NOT EXISTS entries (" +
                            "serialNum 		INTEGER	PRIMARY KEY	NOT NULL,\n" +
                            "title    		TEXT,\n" +
                            "name    		TEXT,\n" +
                            "email    		TEXT,\n" +
                            "suggestions	TEXT,\n" +
                            "checkInDateYear INTEGER,\n" +
                            "checkInDateMonth INTEGER,\n" +
                            "checkInDateDay INTEGER\n" +
                            ");";
                    break;
            }
            statement.execute(SQLCommand);
            CloseConnectionToDB();
        } catch (SQLException e) {
            new HandleError(DatabaseUtil.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
            throw new SQLException();
        } finally {
            CloseConnectionToDB();
        }
    }

    /**
     * Check if DB, table exists. If no, create and return
     * @return if successful return true, if any error occurs, return false
     */
    public static boolean ConnectionInitAndCreate() {
        try {
            CheckIfDBExists();
            CheckIfTableExists();
            CloseConnectionToDB();
            return true;
        } catch (SQLException e) {
            new HandleError(DatabaseUtil.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
            return false;
        } finally {
            CloseConnectionToDB();
        }
    }

    /**
     * Add a new entry to the DataBase
     * @param entry
     */
    public static void AddEntry(Entry entry) throws SQLException {
        try {
            ConnectToDB();
            String SQLCommand = "INSERT INTO entries (serialNum, title, name, email, suggestions," +
                    "checkInDateYear, checkInDateMonth, checkInDateDay) " +
                    "VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQLCommand);
            preparedStatement.setInt(1, entry.getSerialNum());
            preparedStatement.setString(2, entry.getTitle());
            preparedStatement.setString(3, entry.getName());
            preparedStatement.setString(4, entry.getEmail());
            preparedStatement.setString(5, entry.getSuggestions());
            preparedStatement.setInt(6, entry.getCheckInDate().getYear());
            preparedStatement.setInt(7, entry.getCheckInDate().getMonthValue());
            preparedStatement.setInt(8, entry.getCheckInDate().getDayOfMonth());
            preparedStatement.executeUpdate();
            CloseConnectionToDB();
        } catch (SQLException e) {
            new HandleError(DatabaseUtil.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
            throw new SQLException();
        } finally {
            CloseConnectionToDB();
        }
    }
}
