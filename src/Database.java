import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    public static void main(String[] args) {
        String pathWord = "/Users/Teerachet/IdeaProjects/Dictionary/lib/words20k.txt";
        File file20k = new File(pathWord); // ไฟล์คำศัพท์ 20000 คำ

        Connection connection;
        Statement statement;

        String sql, line;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            createDB();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/Dictionary", "root", "root");
            statement = connection.createStatement();

            sql = "CREATE TABLE WordsTB (idCOL INTEGER AUTO_INCREMENT, " +
                        "wordCOL VARCHAR(50) NOT NULL, PRIMARY KEY (idCOL))"; // sql create table
            statement.execute(sql);

            System.out.println("Import text file to database...");
            BufferedReader br = new BufferedReader(new FileReader(file20k));
            while ((line = br.readLine()) != null) { // วน loop อ่านทุกบรรทัด
                sql = "INSERT INTO WordsTB(wordCOL) VALUES('"+ line +"')";
                statement.execute(sql);
            }
            System.out.println("Import text file to database success!");

            select(statement);

            br.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDB() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "root");
            Statement statement = connection.createStatement();

            String sql = "CREATE DATABASE Dictionary";
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void select(Statement statement) {
        String sql;
        try {
            sql = "SELECT wordCOL FROM WordsTB WHERE Length(wordCOL) > 5";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last();
            int size = resultSet.getRow();
            System.out.println("7.1 มีคำกี่คำที่มีความยาว > 5 character");
            System.out.println("\t มี "+ size +" คำ");
            System.out.println();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
