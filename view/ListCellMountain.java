package ch.fhnw.oop2.swissmountainsfx.view;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import ch.fhnw.oop2.swissmountainsfx.presentationmodel.Mountain;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;


/**
 * Defines the layout of the cells in the listview.
 * 
 * @author Benjamin Huber
 * @author Tabea Eggler
 *
 */
public class ListCellMountain extends ListCell<Mountain>{
	
	/**
	 * The basic layout node, where everything else is put in
	 */
	GridPane pane;
	
	/**
	 * Contains the name of this mountain.
	 */
	private Label lblName;
	
	/**
	 * Contains the height of this mountain.
	 */
	private Label lblHeight;
	
	/**
	 * Contains the range of this mountain.
	 */
	private Label lblRange;
	
	/**
	 * Displays a static m for the height.
	 */
	private Label lblMeter;

	/**
	 * hbox to set the height layout of this cell.
	 */
	private HBox hboxHeight;
	
	
	/**
	 * Initializes and layouts the nodes and binds their properties to the mountains properties.
	 * 
	 * @throws MalformedURLException if the formed URL isn't correct
	 * @throws URISyntaxException if the URI syntax contains errors 
	 * 
	 * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
	 */
	@Override
	protected void updateItem(Mountain mountain, boolean empty) {
		super.updateItem(mountain, empty);
		setGraphic(null);
		ImageView image = null;
		
		if(!empty && mountain != null) {
			pane = new GridPane();
			lblName = new Label();
			lblHeight = new Label();
			lblRange = new Label();
			lblMeter = new Label(" m");
			hboxHeight = new HBox();
			
			lblName.textProperty().bind(mountain.nameProperty());
			lblHeight.textProperty().bind(mountain.heightProperty().asString());
			lblRange.textProperty().bind(mountain.rangeProperty());
			
			lblName.setId("listview-name");
			lblHeight.getStyleClass().add("listview-info");
			lblRange.getStyleClass().add("listview-info");
			lblMeter.setId("listview-meter");
			this.setId("list-cell");
			
			hboxHeight.getChildren().addAll(lblHeight, lblMeter);
			
			Circle circle = new Circle(25,25,25);
			circle.getStyleClass().add("circle");
	
			try{
				image = new ImageView(getClass().getResource("../resources/mountainpictures/" + mountain.getId() + ".jpg").toString());
			} catch (NullPointerException npe) {
				image = new ImageView(getClass().getResource("../ownresources/placeholder.png").toString());	
			} 			
			image.setId("listview-image");
			pane.add(image, 0, 0, 1, 4);
			image.setFitHeight(50);
			image.setFitWidth(50);
			image.setClip(circle);
			pane.add(lblName, 1, 0, 1, 2);
			pane.add(hboxHeight, 1, 2);
			pane.add(lblRange, 1, 3);
			setGraphic(pane);	
		}
	}
}