import java.sql.*;
import java.util.Scanner;

public class Main {
    private final static String HOST = "localhost"; // сервер базы данных
    private final static String DATABASENAME = "testDB";// имя базы
    private final static String USERNAME = "postgres"; // учетная запись пользователя
    private final static String PASSWORD = "root"; // пароль
    static Connection connection;

    public static void main(String[] args) {

        //Строка для соединения с бд
        String url = "jdbc:postgresql://" + HOST + "/" + DATABASENAME + "?user=" + USERNAME + "&password=" + PASSWORD;
        try {
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            if (connection == null)
                System.err.println("Нет соединения с БД!");
            else {
                System.out.println("Соединение с БД установлено корректно");
                if (checkvalue(new Scanner(System.in).nextInt())) {
                    System.out.println("Число есть в таблице");
                } else {
                    System.out.println("Число отсутсвует в таблице");
                }
                if (insertvalue(new Scanner(System.in).nextInt())) {
                    System.out.println("Число добавлено в таблицу");
                } else {
                    System.out.println("Число не добавлено");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean checkvalue(int checkedvalue) {
        String SQL = "Select * from test where ID=?;";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, checkedvalue);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean insertvalue(int insertedvalue) {
        String SQL = "insert  into test(id) values(?)";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, insertedvalue);
            int i = statement.executeUpdate();
            if (i >= 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}