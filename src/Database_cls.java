import java.sql.*;

public class Database_cls {

    Connection connection;

    public Database_cls() throws SQLException{
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "password123");
    }

    public void connect() {

        if (connection != null) {

            System.out.println("Connection Established\n");

        } else {

            System.out.println("Failed to make a connection");

        }
    }

    public void selectAll() {

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM currency");

            ResultSetMetaData metaData = rs.getMetaData();
            int columnNum = metaData.getColumnCount();
            int columnWidth = 20;

            for (int i = 1; i <= columnNum; i++) {
                System.out.printf("%-" + columnWidth + "s", metaData.getColumnName(i));
            }
            System.out.println();


            for (int i = 1; i <= columnNum; i++) {
                System.out.print("-".repeat(columnWidth));
            }
            System.out.println();


            while (rs.next()) {
                for (int i = 1; i <= columnNum; i++) {
                    String value = rs.getString(i);
                    System.out.printf("%-" + columnWidth + "s", value);
                }
                System.out.println();
            }
            System.out.println("\n");
            rs.close();
            stmt.close();
        } catch (SQLException e) {

            System.out.println("SQL Error: " + e.getMessage());

        }
    }

    public void deleteRow(int id) throws SQLException{

        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM currency WHERE id = ?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();

        int affectedRows = pstmt.executeUpdate();
        pstmt.executeUpdate("SET @new_id = 0");
        pstmt.executeUpdate("UPDATE currency SET id = (@new_id := @new_id + 1) ORDER BY id");

        pstmt.executeUpdate("ALTER TABLE currency AUTO_INCREMENT = 1");

        if (affectedRows > 0) {
            System.out.println(String.format("Row %d deleted successfully.", id));
        } else {
            System.out.println("No row found with that ID.");
        }
        System.out.println("\n");
        pstmt.close();
    }

    public void clearTable() throws  SQLException {

        Statement stmt = connection.createStatement();
        stmt.executeUpdate("TRUNCATE TABLE currency");

        stmt.close();
    }

    public void getLocation() throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM currency WHERE lat BETWEEN 25.031347 AND 25.19399 AND lon BETWEEN 47.746465 AND 47.863905");

        while(rs.next()){
            int id = rs.getInt("id");
            double latitude = rs.getDouble("lat");
            double longitude = rs.getDouble("lon");
            System.out.println("Location ID: " + id + ", Latitude: " + latitude + ", Longitude: " + longitude);
        }
        System.out.println("\n");
    }

    public void updateLocation(int id, double lat,double lon,double element) throws SQLException {

        PreparedStatement pstmt = connection.prepareStatement("UPDATE currency SET lat = ?,lon = ?,element = ? WHERE id = ?");

        pstmt.setDouble(1, lat);
        pstmt.setDouble(2, lon);
        pstmt.setDouble(3, element);
        pstmt.setInt(4, id);


        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println(String.format("Description updated successfully for ID: %d",id));
        } else {
            System.out.println(String.format("No record found with ID:%d ",id));
        }

        pstmt.close();
        System.out.println("\n");
    }

    public void insertRow(double lat,double lon,double element) throws SQLException  {

        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO currency(lat,lon,element) VALUES (?,?,?)");

        pstmt.setDouble(1, lat);
        pstmt.setDouble(2, lon);
        pstmt.setDouble(3, element);

        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Row inserted successfully!");
        } else {
            System.out.println("Insert failed.");
        }

        pstmt.close();

    }

}