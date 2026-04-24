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

public class MoviesApp extends Application {

    @Override
    public void start(Stage stage) {

        Text pgTitle = new Text("Movies Menu");
        pgTitle.setStyle("-fx-font: normal bold 20px 'serif'; -fx-alignment: center");

        Text text1 = new Text("Genres:");
        Text text2 = new Text("Name:");
        Text text3 = new Text("Registered:");
        TextField name = new TextField();
        ComboBox<String> genre = new ComboBox<>();
        ComboBox<String> registered = new ComboBox<>();
        Button button1 = new Button("Save Movie");
        Button button2 = new Button("Remove Movie");

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
        gridPane.add(text3, 0, 4);

        gridPane.add(genre, 1, 1);
        gridPane.add(name, 1, 2);
        gridPane.add(button1, 1, 3);
        gridPane.add(registered, 1, 4);
        gridPane.add(button2, 1, 5);

        String btnStyle = "-fx-background-color: CORNFLOWERBLUE; -fx-text-fill: white; -fx-font-size: 13pt; -fx-pref-width: 200px;-fx-background-radius: 8px;";
        String hoverStyle = "-fx-background-color: darkslateblue;-fx-text-fill: white; -fx-font-size: 13pt;-fx-pref-width: 200px;-fx-background-radius: 8px;";

        for (Button btn : new Button[]{button1, button2}) {
            btn.setStyle(btnStyle);
            btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
            btn.setOnMouseExited(e ->  btn.setStyle(btnStyle));
        }

        genre.setStyle("-fx-pref-width: 200px");
        registered.setStyle("-fx-pref-width: 200px");
        text1.setStyle("-fx-font: normal bold 20px 'serif'");
        text2.setStyle("-fx-font: normal bold 20px 'serif'");
        text3.setStyle("-fx-font: normal bold 20px 'serif'");

        gridPane.setStyle("-fx-background-color: White;");

        Scene scene = new Scene(gridPane);


        stage.setTitle("Movies Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}