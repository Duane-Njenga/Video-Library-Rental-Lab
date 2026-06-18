package vls;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VLSServiceImpl extends UnicastRemoteObject implements VLSService {

    private static final String DB_URL  = "jdbc:mysql://localhost:3306/vlsrmi";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public VLSServiceImpl() throws RemoteException { super(); }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public void addGenre(String name) throws RemoteException {
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement("INSERT INTO genres(genre,isactive) VALUES(?,1)")) {
            ps.setString(1, name); ps.executeUpdate();
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
    }

    public List<String> getGenres() throws RemoteException {
        List<String> list = new ArrayList<>();
        try (Connection c = connect(); ResultSet rs = c.createStatement().executeQuery("SELECT genre FROM genres WHERE isactive=1")) {
            while (rs.next()) list.add(rs.getString("genre"));
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
        return list;
    }

    public void removeGenre(String name) throws RemoteException {
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement("UPDATE genres SET isactive=0 WHERE genre=?")) {
            ps.setString(1, name); ps.executeUpdate();
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
    }

    public void addMovie(String title, String genre) throws RemoteException {
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(
                "INSERT INTO movies(genre_id,title,isactive) VALUES((SELECT id FROM genres WHERE genre=?),?,1)")) {
            ps.setString(1, genre); ps.setString(2, title); ps.executeUpdate();
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
    }

    public List<String> getMoviesByGenre(String genre) throws RemoteException {
        List<String> list = new ArrayList<>();
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(
                "SELECT m.title FROM movies m JOIN genres g ON m.genre_id=g.id WHERE g.genre=? AND m.isactive=1")) {
            ps.setString(1, genre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(rs.getString("title"));
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
        return list;
    }

    public void removeMovie(String title) throws RemoteException {
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement("UPDATE movies SET isactive=0 WHERE title=?")) {
            ps.setString(1, title); ps.executeUpdate();
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
    }

    public void addCustomer(String fullname, String phone, String email) throws RemoteException {
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(
                "INSERT INTO clients(fullname,phone,email,isactive) VALUES(?,?,?,1)")) {
            ps.setString(1, fullname); ps.setString(2, phone); ps.setString(3, email); ps.executeUpdate();
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
    }

    public List<String> getCustomers() throws RemoteException {
        List<String> list = new ArrayList<>();
        try (Connection c = connect(); ResultSet rs = c.createStatement().executeQuery("SELECT fullname FROM clients WHERE isactive=1")) {
            while (rs.next()) list.add(rs.getString("fullname"));
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
        return list;
    }

    public void removeCustomer(String fullname) throws RemoteException {
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement("UPDATE clients SET isactive=0 WHERE fullname=?")) {
            ps.setString(1, fullname); ps.executeUpdate();
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
    }

    public void saveRental(String customerName, String movieTitle) throws RemoteException {
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(
                "INSERT INTO rentals(client_id,movie_id,returned) VALUES((SELECT id FROM clients WHERE fullname=?),(SELECT id FROM movies WHERE title=?),0)")) {
            ps.setString(1, customerName); ps.setString(2, movieTitle); ps.executeUpdate();
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
    }

    public List<String> getBorrowedMovies(String customerName) throws RemoteException {
        List<String> list = new ArrayList<>();
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(
                "SELECT m.title FROM rentals r JOIN clients cl ON r.client_id=cl.id JOIN movies m ON r.movie_id=m.id WHERE cl.fullname=? AND r.returned=0")) {
            ps.setString(1, customerName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(rs.getString("title"));
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
        return list;
    }

    public void returnMovie(String customerName, String movieTitle) throws RemoteException {
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(
                "UPDATE rentals r JOIN clients cl ON r.client_id=cl.id JOIN movies m ON r.movie_id=m.id SET r.returned=1 WHERE cl.fullname=? AND m.title=? AND r.returned=0")) {
            ps.setString(1, customerName); ps.setString(2, movieTitle); ps.executeUpdate();
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
    }

    public List<String> getReturnedMovies(String customerName) throws RemoteException {
        List<String> list = new ArrayList<>();
        try (Connection c = connect(); PreparedStatement ps = c.prepareStatement(
                "SELECT m.title FROM rentals r JOIN clients cl ON r.client_id=cl.id JOIN movies m ON r.movie_id=m.id WHERE cl.fullname=? AND r.returned=1")) {
            ps.setString(1, customerName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(rs.getString("title"));
        } catch (SQLException e) { throw new RemoteException(e.getMessage()); }
        return list;
    }
}