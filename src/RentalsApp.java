import javafx.application.Application;
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

public class RentalsApp extends Application {

    @Override
    public void start(Stage stage) {

        Text pgTitle = new Text("Customers Menu");
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
        };
        for(Text t : new Text[]{text1, text2, text3, text4, text5}) {
            t.setStyle("-fx-font: normal bold 20px 'serif'");
        };
//        registered.setStyle("-fx-pref-width: 200px; -fx-border-color: black; -fx-border-width: 2; -fx-border-style: solid;-fx-border-radius: 8px; -fx-background-radius: 8px;");



        gridPane.setStyle("-fx-background-color: White;");

        Scene scene = new Scene(gridPane);


        stage.setTitle("Customers Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}