import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new javafx.scene.Scene(new javafx.scene.control.Label("It works"), 300, 200));
        stage.show();


    }
}

