import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import vls.VLSService;

public class RentalsApp extends Application {

    private static final String HOST = "localhost";
    private VLSService service;

    private void connect() {
        try {
            Registry r = LocateRegistry.getRegistry(HOST, 1099);
            service = (VLSService) r.lookup("VLSService");
        } catch (Exception e) { System.out.println("RMI Error: " + e.getMessage()); }
    }

    @Override
    public void start(Stage stage) {
        connect();

        Text pgTitle = new Text("Rentals Menu");
        pgTitle.setStyle("-fx-font: normal bold 20px 'serif'; -fx-alignment: center");

        Text text1 = new Text("Customer:");
        Text text2 = new Text("Genre:");
        Text text3 = new Text("Movies:");
        Text text4 = new Text("Borrowed:");
        Text text5 = new Text("Returned:");

        ComboBox<String> customer = new ComboBox<>();
        ComboBox<String> genre = new ComboBox<>();
        ComboBox<String> movies = new ComboBox<>();
        ComboBox<String> borrowed = new ComboBox<>();
        ComboBox<String> returned = new ComboBox<>();

        Button button1 = new Button("Save Rental");
        Button button2 = new Button("Return Movie");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 400);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        GridPane.setColumnSpan(pgTitle, 2);
        GridPane.setHalignment(pgTitle, HPos.CENTER);

        gridPane.add(pgTitle, 0, 0);
        gridPane.add(text1, 0, 1);
        gridPane.add(text2, 0, 2);
        gridPane.add(text3, 0, 3);
        gridPane.add(text4, 0, 5);
        gridPane.add(text5, 0, 7);

        gridPane.add(customer, 1, 1);
        gridPane.add(genre, 1, 2);
        gridPane.add(movies, 1, 3);
        gridPane.add(button1, 1, 4);
        gridPane.add(borrowed, 1, 5);
        gridPane.add(button2, 1, 6);
        gridPane.add(returned, 1, 7);

        String btnStyle = "-fx-background-color: CORNFLOWERBLUE; -fx-text-fill: white; -fx-font-size: 13pt; -fx-pref-width: 200px;-fx-background-radius: 8px;";
        String hoverStyle = "-fx-background-color: darkslateblue;-fx-text-fill: white; -fx-font-size: 13pt;-fx-pref-width: 200px;-fx-background-radius: 8px;";

        for (Button btn : new Button[]{button1, button2}) {
            btn.setStyle(btnStyle);
            btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
            btn.setOnMouseExited(e ->  btn.setStyle(btnStyle));
        }
        for (ComboBox<?> cb : new ComboBox[]{customer, genre, movies, borrowed, returned}) {
            cb.setStyle("-fx-pref-width: 200px; -fx-border-color: black; -fx-border-width: 2; -fx-border-style: solid;-fx-border-radius: 8px; -fx-background-radius: 8px;-fx-background-color: White;");
        }
        for (Text t : new Text[]{text1, text2, text3, text4, text5}) {
            t.setStyle("-fx-font: normal bold 20px 'serif'");
        }

        gridPane.setStyle("-fx-background-color: White;");

        new Thread(() -> {
            try {
                List<String> customers = service.getCustomers();
                List<String> genres = service.getGenres();
                Platform.runLater(() -> {
                    customer.getItems().setAll(customers);
                    genre.getItems().setAll(genres);
                });
            } catch (Exception ex) { System.out.println(ex.getMessage()); }
        }).start();

        genre.setOnAction(e -> {
            movies.getItems().clear();
            if (genre.getValue() == null) return;
            new Thread(() -> {
                try {
                    List<String> m = service.getMoviesByGenre(genre.getValue());
                    Platform.runLater(() -> movies.getItems().setAll(m));
                } catch (Exception ex) { System.out.println(ex.getMessage()); }
            }).start();
        });

        customer.setOnAction(e -> {
            borrowed.getItems().clear();
            returned.getItems().clear();
            if (customer.getValue() == null) return;
            new Thread(() -> {
                try {
                    List<String> b = service.getBorrowedMovies(customer.getValue());
                    List<String> r = service.getReturnedMovies(customer.getValue());
                    Platform.runLater(() -> {
                        borrowed.getItems().setAll(b);
                        returned.getItems().setAll(r);
                    });
                } catch (Exception ex) { System.out.println(ex.getMessage()); }
            }).start();
        });

        button1.setOnAction(e -> {
            if (customer.getValue() == null || movies.getValue() == null) return;
            new Thread(() -> {
                try {
                    service.saveRental(customer.getValue(), movies.getValue());
                    List<String> b = service.getBorrowedMovies(customer.getValue());
                    Platform.runLater(() -> borrowed.getItems().setAll(b));
                    customer.setValue(null);movies.setValue(null);genre.setValue(null);
                } catch (Exception ex) { System.out.println(ex.getMessage()); }
            }).start();
        });

        button2.setOnAction(e -> {
            if (customer.getValue() == null || borrowed.getValue() == null) return;
            new Thread(() -> {
                try {
                    service.returnMovie(customer.getValue(), borrowed.getValue());
                    List<String> b = service.getBorrowedMovies(customer.getValue());
                    List<String> r = service.getReturnedMovies(customer.getValue());
                    Platform.runLater(() -> {
                        borrowed.getItems().setAll(b);
                        returned.getItems().setAll(r);
                        borrowed.setValue(null); returned.setValue(null);
                    });
                } catch (Exception ex) { System.out.println(ex.getMessage()); }
            }).start();
        });

        Scene scene = new Scene(gridPane);

        stage.setTitle("Rentals Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}