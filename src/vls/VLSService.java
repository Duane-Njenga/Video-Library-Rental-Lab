package vls;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VLSService extends Remote {
    void addGenre(String name) throws RemoteException;
    List<String> getGenres() throws RemoteException;
    void removeGenre(String name) throws RemoteException;

    void addMovie(String title, String genre) throws RemoteException;
    List<String> getMovies() throws RemoteException;
    List<String> getMoviesByGenre(String genre) throws RemoteException;
    void removeMovie(String title) throws RemoteException;

    void addCustomer(String fullname, String phone, String email) throws RemoteException;
    List<String> getCustomers() throws RemoteException;
    void removeCustomer(String fullname) throws RemoteException;

    void saveRental(String customerName, String movieTitle) throws RemoteException;
    List<String> getBorrowedMovies(String customerName) throws RemoteException;
    void returnMovie(String customerName, String movieTitle) throws RemoteException;
    List<String> getReturnedMovies(String customerName) throws RemoteException;

}