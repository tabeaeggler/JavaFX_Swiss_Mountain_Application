package ch.fhnw.oop2.swissmountainsfx.view;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import ch.fhnw.oop2.swissmountainsfx.presentationmodel.MountainModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;
import javafx.util.converter.NumberStringConverter;


/**
 * The editor area, adds bindings to the mountain proxy of the model and loads the image of the currently selected mountain.
 * 
 * @author Benjamin Huber
 * @author Tabea Eggler
 *
 */
public class MountainEdit extends GridPane {
	
	/**
	 * the presentation model, which contains the functionality of this application
	 */
	private final MountainModel model;

	/**
	 * Contains the name of the currently selected mountain for the header.
	 */
	private Label lblHeaderName;
	
	/**
	 * Contains the height of the currently selected mountain for the header.
	 */
	private Label lblHeaderHeight;
	
	/**
	 * Contains the range of the currently selected mountain for the header.
	 */
	private Label lblHeaderRange;

	/**
	 * Displays "Name" next to the name of the currently mountain.
	 */
	private Label lblName;
	
	/**
	 * Displays "Dominanz" next to the isolation of the currently selected mountain.
	 */
	private Label lblIsolation;
	
	/**
	 * Displays "km bis" next to the isolation point of the currently selected mountain.
	 */
	private Label lblIsolationPoint;
	
	/**
	 * Displays "Typ" next to the type of the currently selected mountain.
	 */
	private Label lblType;
	
	/**
	 * Displays "Kantone" next to the cantons of the currently selected mountain.
	 */
	private Label lblCantons;
	
	/**
	 * Displays "Bildunterschrift" next to the captions of the currently selected mountain.
	 */
	private Label lblCaptions;
	
	/**
	 * Displays "Höhe" next to the height of the currently selected mountain.
	 */
	private Label lblHeight;
	
	/**
	 * Displays "Schartenhöhe" next to the prominence of the currently selected mountain.
	 */
	private Label lblProminence;
	
	/**
	 * Displays "m bis" next to the prominence point of the currently selected mountain.
	 */
	private Label lblProminencePoint;
	
	/**
	 * Displays "Region" next to the region of the currently selected mountain.
	 */
	private Label lblRegion;
	
	/**
	 * Displays "Gebiet" next to the range of the currently selected mountain.
	 */
	private Label lblRange;
	
	/**
	 * Displays a static m for the height.
	 */
	private Label lblHeaderMeter;

	/**
	 * Contains the name of the currently selected mountain.
	 */
	private TextField txtName;
	
	/**
	 * Contains the isolation of the currently selected mountain.
	 */
	private TextField txtIsolation;
	
	/**
	 * Contains the isolation point of the currently selected mountain.
	 */
	private TextField txtIsolationPoint;
	
	/**
	 * Contains the type of the currently selected mountain.
	 */
	private TextField txtType;
	
	/**
	 * Contains the cantons of the currently selected mountain.
	 */
	private TextField txtCantons;
	
	/**
	 * Contains the captions of the currently selected mountain.
	 */
	private TextField txtCaptions;
	
	/**
	 * Contains the height of the currently selected mountain.
	 */
	private TextField txtHeight;
	
	/**
	 * Contains the prominence of the currently selected mountain.
	 */
	private TextField txtProminence;
	
	/**
	 * Contains the prominence point of the currently selected mountain.
	 */
	private TextField txtPromienencePoint;
	
	/**
	 * Contains the region of the currently selected mountain.
	 */
	private TextField txtRegion;
	
	/**
	 * Contains the range of the currently selected mountain.
	 */
	private TextField txtRange;
	
	/**
	 * spacer to set the position of image to the right
	 */
	private HBox spacerImg;

	/**
	 * Displays the image of the currently selected mountain.
	 */
	private ImageView imageDisplay;
	
	/**
	 * hbox to set the height layout of this header
	 */
	private HBox hboxHeight;
	
	private Slider slider;

	
	/**
	 * Constructs a new editor area.
	 * 
	 * @param model the presentation model, which contains the functionality of this application
	 */
	public MountainEdit(MountainModel model) {
		this.model = model;
		initializeSelf();
		initializeControls();
		layoutControls();
		setupEventHandlers();
		setupBindings();
	}


	/**
	 * Layouts the gridpane
	 */
	private void initializeSelf() {
		setVgap(10);
		setHgap(20);
		setPadding(new Insets(10));
	}

	
	/**
	 * Inizializes the nodes and loads the image of the currently selected mountain.
	 * 
	 * @throws MalformedURLException if the formed URL isn't correct
	 * @throws URISyntaxException if the URI syntax contains errors
	 */
	private void initializeControls() {
		lblHeaderName = new Label();
		lblHeaderHeight = new Label();
		lblHeaderMeter = new Label(" m");
		lblHeaderRange = new Label();

		lblName = new Label("Name");
		lblIsolation = new Label("Dominanz");
		lblIsolationPoint = new Label("km bis");
		lblType = new Label("Typ");
		lblCantons = new Label("Kantone");
		lblCaptions = new Label("Bildinfo");
		lblHeight = new Label("Höhe (m)");
		lblProminence = new Label("Schartenhöhe");
		lblProminencePoint = new Label("m bis");
		lblRegion = new Label("Region");
		lblRange = new Label("Gebiet");

		txtName = new TextField();
		txtIsolation = new TextField();
		txtIsolationPoint = new TextField();
		txtType = new TextField();
		txtCantons = new TextField();
		txtCaptions = new TextField();
		txtHeight = new TextField();
		txtProminence = new TextField();
		txtPromienencePoint = new TextField();
		txtRegion = new TextField();
		txtRange = new TextField();
		
		spacerImg = new HBox();
		hboxHeight = new HBox();
		HBox.setHgrow(spacerImg, Priority.ALWAYS);
		slider = new Slider(0, 1500, 1500);
		Circle circle = new Circle(70,70,65);
		circle.getStyleClass().add("circle");
		
		try {
			imageDisplay = new ImageView(getClass().getResource("../resources/mountainpictures/" + model.getMountainProxy().getId() + ".jpg").toURI().toURL().toString());
			imageDisplay.setFitHeight(140);
			imageDisplay.setFitWidth(140);
			imageDisplay.setClip(circle);
			imageDisplay.getStyleClass().add("image");
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Layouts the nodes.
	 */
	private void layoutControls() { 
		GridPane.setHgrow(txtName, Priority.ALWAYS);
		GridPane.setHgrow(txtProminence, Priority.ALWAYS);
		GridPane.setHgrow(lblHeaderHeight, Priority.ALWAYS);
		add(lblHeaderName, 0, 0, 4, 1);
		hboxHeight.getChildren().addAll(lblHeaderHeight, lblHeaderMeter);
		add(hboxHeight, 0, 1, 2, 1);
		add(lblHeaderRange, 0, 2, 2, 1);
		add(lblName, 0, 4);
		add(lblHeight, 0, 5);
		add(lblIsolation, 0, 6);
		add(lblIsolationPoint, 0, 7);
		add(lblType, 0, 8);
		add(lblCantons, 0, 9);
		add(lblCaptions, 0, 10, 3, 1);
		add(txtName, 1, 4);
		add(txtHeight, 1, 5);
		add(slider, 3, 5);
		add(txtIsolation, 1, 6);
		add(txtIsolationPoint, 1, 7);
		add(txtType, 1, 8);
		add(txtCantons, 1, 9);
		add(txtCaptions, 1, 10, 3, 1);
		add(lblProminence, 2, 6);
		add(lblProminencePoint, 2, 7);
		add(lblRegion, 2, 8);
		add(lblRange, 2, 9);
		add(txtProminence, 3, 6);
		add(txtPromienencePoint, 3, 7);
		add(txtRegion, 3, 8);
		add(txtRange, 3, 9);
		add(spacerImg, 2, 0, 2, 4);
		add(imageDisplay, 3, 0, 2, 4);
		
		hboxHeight.setAlignment(Pos.BOTTOM_LEFT);
		hboxHeight.getStyleClass().add("lbl-edit-header");
		lblHeaderName.getStyleClass().add("lbl-edit-name");
		lblHeaderHeight.getStyleClass().add("lbl-edit-header");
		lblHeaderRange.getStyleClass().add("lbl-edit-header");
		lblCantons.getStyleClass().add("label-edit");
		lblCaptions.getStyleClass().add("label-edit");
		lblHeaderHeight.getStyleClass().add("label-edit");
		lblHeaderMeter.getStyleClass().add("label-edit");
		lblHeaderName.getStyleClass().add("label-edit");
		lblHeaderRange.getStyleClass().add("label-edit");
		lblHeight.getStyleClass().add("label-edit");
		lblIsolation.getStyleClass().add("label-edit");
		lblIsolationPoint.getStyleClass().add("label-edit");
		lblName.getStyleClass().add("label-edit");
		lblProminence.getStyleClass().add("label-edit");
		lblProminencePoint.getStyleClass().add("label-edit");
		lblRange.getStyleClass().add("label-edit");
		lblRegion.getStyleClass().add("label-edit");
		lblType.getStyleClass().add("label-edit");
		txtCantons.getStyleClass().add("textfield-edit");
		txtCaptions.getStyleClass().add("textfield-edit");
		txtHeight.getStyleClass().add("textfield-edit");
		txtIsolation.getStyleClass().add("textfield-edit");
		txtIsolationPoint.getStyleClass().add("textfield-edit");
		txtName.getStyleClass().add("textfield-edit");
		txtPromienencePoint.getStyleClass().add("textfield-edit");
		txtProminence.getStyleClass().add("textfield-edit");
		txtRange.getStyleClass().add("textfield-edit");
		txtRegion.getStyleClass().add("textfield-edit");
		txtType.getStyleClass().add("textfield-edit");
	}

	
	/**
	 * Adds listener to the mountain proxy to observe and change the image.
	 * 
	 * @throws MalformedURLException if the formed URL isn't correct
	 * @throws URISyntaxException if the URI syntax contains errors
	 */
	private void setupEventHandlers() {
		model.getMountainProxy().idProperty().addListener((source, oldValue, newValue) -> {
			try{
				imageDisplay.setImage(new Image(getClass().getResource("../resources/mountainpictures/" + model.getMountainProxy().getId() + ".jpg").toString()));
			} catch (NullPointerException npe) {
				
					imageDisplay.setImage(new Image(getClass().getResource("../ownresources/placeholder.png").toString()));
			} 			
		});
	}

	
	/**
	 * Binds the properties of the nodes of this editor to the properties of the mountain proxy of the model.
	 */
	private void setupBindings() {
		lblHeaderName.textProperty().bindBidirectional(model.getMountainProxy().nameProperty());
		lblHeaderHeight.textProperty().bindBidirectional(model.getMountainProxy().heightProperty(), new NumberStringConverter());
		lblHeaderRange.textProperty().bindBidirectional(model.getMountainProxy().rangeProperty());
		txtName.textProperty().bindBidirectional(model.getMountainProxy().nameProperty());
		txtIsolation.textProperty().bindBidirectional(model.getMountainProxy().isolationProperty(), new NumberStringConverter());
		slider.valueProperty().bindBidirectional(model.getMountainProxy().prominenceProperty());
		txtIsolationPoint.textProperty().bindBidirectional(model.getMountainProxy().isolationPointProperty());
		txtType.textProperty().bindBidirectional(model.getMountainProxy().typeProperty());
		txtCantons.textProperty().bindBidirectional(model.getMountainProxy().cantonsProperty());
		txtCaptions.textProperty().bindBidirectional(model.getMountainProxy().captionProperty());
		txtHeight.textProperty().bindBidirectional(model.getMountainProxy().heightProperty(), new NumberStringConverter());
		txtProminence.textProperty().bindBidirectional(model.getMountainProxy().prominenceProperty(), new NumberStringConverter());
		txtPromienencePoint.textProperty().bindBidirectional(model.getMountainProxy().prominencePointProperty());
		txtRegion.textProperty().bindBidirectional(model.getMountainProxy().regionProperty());
		txtRange.textProperty().bindBidirectional(model.getMountainProxy().rangeProperty());		
	}
}
