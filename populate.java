import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class populate {

    public static void main(String[] argv) throws IOException {

        Random random = new Random(System.currentTimeMillis());//Random number generator to randomly select from list of values
        PreparedStatement statement;//Creates a statement object to apply a query on the Database
        String []query = new String[2];//Holds all the queries that will be used.
        Connection connection;//Contains an instance of the connection to the database

        String [] fnames ={"fnames.txt","lnames.txt","majors.txt"};//Contains the names of the files needed to randomly generate student pool.

        //stores the values from each file.
        String []first_name = readFile("src/"+fnames[0]).split("\n");
        String []last_name = readFile("src/"+fnames[1]).split("\n");
        String []majors = readFile("src/"+fnames[2]).split("\n");

        //Change statement to match insert requirements
        query[0] = "INSERT INTO Members (sid, lName, fName, major,year,  mentor, mentee) VALUES(?,?,?,?,?,?,?)";
        query[1]= "SELECT * FROM Members";

        //passes the commandline arguments of username and password to create a connection the mysql database
        connection = connect(argv[0],argv[1]);

        //Assigns mysql queries to statement object and assigns values to each column.
        //Will return SQL Exception if there is a type mismatch
        try{
            statement = connection.prepareStatement(query[0]);
            for(int i = 0; i<100;i++)
            {
                String sid = "890000"+String.format("%d",i+101);

                try {
                    statement.setString(1,sid);
                    statement.setString(2,last_name[random.nextInt(99-0)+0]);
                    statement.setString(3,first_name[random.nextInt(99-0)+0]);
                    statement.setString(4,majors[random.nextInt(99-0)+0]);
                    statement.setInt(5, random.nextInt(4)+1);
                    statement.setString(6,null);
                    statement.setString(7,null);
                    statement.addBatch();
                } catch (SQLException ex) {
                    System.out.println("Query/Table type mismatch. Verify that your query matches the column types.");
                }
            }

            //Performs batch insert query and prints table to verify current table.
            try {
                statement.executeBatch();
                ResultSet rs=statement.executeQuery(query[1]);

                System.out.println("\nPrinting Current Table Contents After Batch Query:\n");
                System.out.format("%-15s%-15s%-16s%-16s\n", "sid", "First Name", "Last Name", "Major");

                while(rs.next())
                {
                    String id = rs.getString("sid");
                    String firstName = rs.getString("lName");
                    String lastName = rs.getString("fName");
                    String  major = rs.getString("major");

                    System.out.format("%-15s%-15s%-16s%-16s\n", id, firstName, lastName, major);
                }
                rs.close();
                statement.close();
                connection.close();
            }
            catch(SQLException ex){
                System.out.println("Error while executing batch.");
            }
        }catch(SQLException ex){
            System.out.println("Error while creating statement.");
        }
    }

    private static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    private static Connection connect(String usrn,String pw){
        System.out.println("-------- MySQL JDBC Connection Testing ------------");
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Dependency is missing from project");
            e.printStackTrace();
            return null;
        }
        System.out.println("MySQL JDBC Driver Registered!");


        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestDB",usrn,pw);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }
        if (connection != null) {
            System.out.println("Connection Successful. Applying batch query to database.");
        } else {
            System.out.println("Failed to make connection!");
        }
        return connection;
    }
}


