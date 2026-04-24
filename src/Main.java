import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {


        Text title = new Text("Video Library System Menu");
        title.setStyle("-fx-font: normal bold 20px 'serif'");

        Button button1 = new Button("Genres");
        Button button2 = new Button("Movies");
        Button button3 = new Button("Customers");
        Button button4 = new Button("Rentals");

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(600, 400);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(title, 1, 0);
        gridPane.add(button1, 1, 1);
        gridPane.add(button2, 1, 2);
        gridPane.add(button3, 1, 3);
        gridPane.add(button4, 1, 4);

        String btnStyle = "-fx-background-color: CORNFLOWERBLUE; -fx-text-fill: white; -fx-font-size: 13pt; -fx-pref-width: 200px;";
        String hoverStyle = "-fx-background-color: darkslateblue;-fx-text-fill: white; -fx-font-size: 13pt;-fx-pref-width: 200px;";

        for (Button btn : new Button[]{button1, button2, button3, button4}) {
            btn.setStyle(btnStyle);
            btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
            btn.setOnMouseExited(e ->  btn.setStyle(btnStyle));
        }




        gridPane.setStyle("-fx-background-color: WHITE;");

        Scene scene = new Scene(gridPane);


        stage.setTitle("Movie Library System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}