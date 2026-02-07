package gui;
import gui.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	//application
	@Override
    public void start(Stage stage) {
		
	    service.SchemaInit.init();   
	    
        stage.setTitle("Library Management System");
        stage.setScene(new Scene(new MainView(), 400, 300));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
