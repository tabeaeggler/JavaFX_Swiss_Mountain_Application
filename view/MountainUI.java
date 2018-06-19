package ch.fhnw.oop2.swissmountainsfx.view;

import ch.fhnw.oop2.swissmountainsfx.presentationmodel.MountainModel;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


/**
 * Defines the UI of the application.
 * 
 * @author Benjamin Huber
 * @author Tabea Eggler
 *
 */
public class MountainUI extends VBox {
	/**
	 * the presentation model, which contains the functionality of this application
	 */
    private final MountainModel model;
    
    /**
     * the toolbar on the top
     */
    private MountainToolbar toolbar;
    
    /**
     * the editor, where the mountains can be edited
     */
    private MountainEdit edit;
    
    /**
     * the selectable list 
     */
    private MountainSelector selector;
    
	/**
	 * the tableview
	 */
    private MountainTable table;
    
    /**
     * the container in the middle
     */
    private SplitPane splitpane;
    
    /**
     * the container with selector table in it
     */
    private StackPane stackPane;

    
    /**
     * Constructs a new UI.
     * 
     * @param model the presentation model, which contains the functionality of this application
     */
    public MountainUI(MountainModel model) {
        this.model = model;
        initializeSelf();
        initializeControls();
        layoutControls();
        setupBindings();
    }

    
    /**
     * Adds the stylesheet to this UI.
     */
    private void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);
        this.getStyleClass().add("vbox");
    }

    
    /**
     * Initializes the nodes.
     */
    private void initializeControls() {
    	edit = new MountainEdit(model);
    	table = new MountainTable(model);
    	selector = new MountainSelector(model);
    	selector.setItems(model.getData());
    	selector.setCellFactory(e -> new ListCellMountain());
    	toolbar = new MountainToolbar(model, selector, table);
    	stackPane = new StackPane();
    	splitpane = new SplitPane();
    }

    
    /**
     * Layouts the nodes.
     */
    private void layoutControls() {
    	stackPane.getChildren().addAll(selector, table);
    	splitpane.getItems().addAll(stackPane, edit);
    	splitpane.setDividerPositions(0.325f, 0.675f);
    	getChildren().addAll(toolbar, splitpane);
    	table.setVisible(model.getTableVisibility());
    	selector.setVisible(!model.getTableVisibility());
    	
    	this.setVgrow(toolbar, Priority.ALWAYS);
    	this.setVgrow(splitpane, Priority.ALWAYS);
    }

    
	/**
	 * Binds the properties of the nodes of this editor to the properties of the mountain proxy of the model.
	 */
    private void setupBindings() {
    	table.visibleProperty().bind(model.tableVisibilityProperty());
    }
    
}
