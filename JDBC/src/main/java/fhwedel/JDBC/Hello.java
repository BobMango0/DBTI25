package fhwedel.JDBC;
import java.sql.*;

public class Hello {
    public static void main(String[] args) throws SQLException {
        selectPersonalFromVerkauf();
    }

    public static void selectPersonalFromVerkauf() throws SQLException {

        Connection con = connectToDB();
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery(
            "SELECT p.vorname, p.name FROM personal as p join abteilung as a ON p.abt_nr = a.abt_nr WHERE a.name = 'Verkauf'");
    
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        while (rs.next()) {
            for(int i = 1; i < columnsNumber; i++)
                System.out.print(rs.getString(i) + " ");
            System.out.println();
        }
    }

    public static void deleteLutz() throws SQLException {

        Connection con = connectToDB();
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery(
            "DELETE FROM personal WHERE vorname = 'Lutz' AND name = 'Tietze'");
    }

    public static void updateGehaltsstufe() throws SQLException {
        
        Connection con = connectToDB();
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery(
            "UPDATE gehalt SET betrag = (SELECT betrag FROM gehalt WHERE geh_stufe = 'it1') * 1.1 WHERE geh_stufe = 'it1'");
    }

    public static void creatHendrikKrause() throws SQLException {
        Connection con = connectToDB();
        Statement st = con.createStatement();
        
        st.executeQuery(
            "INSERT INTO personal(pnr, name, vorname, geh_stufe, abt_nr, krankenkasse) VALUES(417, 'Krause', 'Hendrik', 'it1', 'd13', 'tkk')");
    }

    public static void readFromPersonal() throws SQLException {
        Connection con = connectToDB();
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery("SELECT * FROM personal");

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        while (rs.next()) {
            for(int i = 1; i < columnsNumber; i++)
                System.out.print(rs.getString(i) + " ");
            System.out.println();
        }
    }

    public static Connection connectToDB() {

        final String DB_URL = "jdbc:mariadb://localhost:3306/firma";
        final String DB_PASSWORD = "password";
        final String DB_USER = "root";

        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
