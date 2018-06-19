package ch.fhnw.oop2.swissmountainsfx;

import ch.fhnw.oop2.swissmountainsfx.presentationmodel.MountainModel;
import ch.fhnw.oop2.swissmountainsfx.view.MountainUI;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Starts the application.
 */
public class AppStarter extends Application {

	/**
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
    @Override
    public void start(Stage primaryStage) throws Exception {
        MountainModel pm = new MountainModel();
        Parent rootPanel = new MountainUI(pm);
        Scene scene = new Scene(rootPanel);

        primaryStage.titleProperty().bind(pm.applicationTitleProperty());
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("file:./src/main/java/ch/fhnw/oop2/swissmountainsfx/ownresources/swiss.jpg"));
        primaryStage.setMinHeight(510);
        primaryStage.setMinWidth(820);
        primaryStage.show();
        
        pm.setSelectedMountainID(pm.getData().get(0).getId());
        
        primaryStage.setOnCloseRequest(e -> { 
        	Alert alert = new Alert(AlertType.CONFIRMATION);
        	Image img = new Image("file:./src/main/java/ch/fhnw/oop2/swissmountainsfx/ownresources/cow.png");
        	ImageView imgView = new ImageView(img);
        	alert.setTitle("Swiss Mountain");
            alert.setHeaderText("Close Swiss Mountain Application?");
            alert.setContentText("Cheers Tabea & Benjamin");
            alert.setGraphic(imgView);
            alert.showAndWait().
        		filter(t -> t != ButtonType.OK).
        		ifPresent(t -> e.consume());
        	}
        );   
    }

	public static void main(String[] args) {

		launch(args);
	}
}
