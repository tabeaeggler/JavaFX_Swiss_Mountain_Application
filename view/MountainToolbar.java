package ch.fhnw.oop2.swissmountainsfx.view;

import ch.fhnw.oop2.swissmountainsfx.presentationmodel.MountainModel;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;


/**
 * the toolbar on the top
 * 
 * @author Benjamin Huber
 * @author Tabea Eggler
 */
public class MountainToolbar extends ToolBar{
	
	/**
	 * the presentation model, which contains the functionality of this application
	 */
    private final MountainModel model;
    
    /**
     * the selectable list 
     */
    private final MountainSelector selector;
    
	/**
	 * the tableview
	 */
    private final MountainTable table;
    
    /**
     * button to save changes
     */
    private Button btnSave;
    
    /**
     * button to add a new entry to the data
     */
    private Button btnAdd;
    
    /**
     * button to delete an entry of the data
     */
    private Button btnDelete;
    
    /**
     * button to undo an entry of the data
     */
    private Button btnUndo;
    
    /**
     * button to redo an entry of the data
     */
    private Button btnRedo;
    
    /**
     * button to swap the listview and tableview
     */
    private Button btnSwapView;
    
    /**
     * button to search an object (scharf)
     */
    private Button btnSearchScharf;
    
    /**
     * button to search an object (unscharf)
     */
    private Button btnSearchUnscharf;
    
    /**
     * imageview icon to swap to tableview
     */
    private ImageView pen;
    
    /**
     * imageview icon to swap to listview
     */
    private ImageView list;
    
    /**
     * hbox to align the txtSearch to the right
     */
    private HBox spacer;

    /**
     * textfield to search through data
     */
    private TextField txtSearch;
    
    
    /**
     * Constructs a new toolbar.
     * 
     * @param model the presentation model, which contains the functionality of this application
     */
    public MountainToolbar(MountainModel model, MountainSelector selector, MountainTable table) {
        this.model = model;
        this.selector = selector;
        this.table = table;
        initializeSelf();
        initializeControls();
        layoutControls();
        setupEventHandlers();
        setupBindings();
    }

    
    /**
     * Adds the stylesheet to this toolbar.
     */
    private void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);
    }
    

    /**
     * Initializes the nodes.
     */
    private void initializeControls() {
        btnSave = new Button();
        btnAdd = new Button();
        btnDelete = new Button();
        btnUndo = new Button();
        btnRedo = new Button();
        btnSwapView = new Button();
        spacer = new HBox();
        txtSearch = new TextField();
        btnSearchScharf = new Button();
        btnSearchUnscharf = new Button();
        pen = new ImageView(new Image(getClass().getResourceAsStream("../ownresources/pen.png")));
        list = new ImageView(new Image(getClass().getResourceAsStream("../ownresources/list.png")));
    }

    
    /**
     * Layouts the nodes.
     */
    private void layoutControls() {
        getItems().addAll(
        		btnSave,
        		btnAdd,
        		btnDelete,
        		btnUndo,
        		btnRedo,
        		btnSwapView,
        		spacer,
        		txtSearch,
        		btnSearchScharf,
        		btnSearchUnscharf
    		);

        spacer.setHgrow(spacer, Priority.ALWAYS);
        this.setId("toolbar");
        btnAdd.getStyleClass().add("toolbar-icons");
		btnDelete.getStyleClass().add("toolbar-icons");
		btnSave.getStyleClass().add("toolbar-icons");
		btnUndo.getStyleClass().add("toolbar-icons");
		btnRedo.getStyleClass().add("toolbar-icons");
		btnSwapView.getStyleClass().add("toolbar-icons");
		btnSearchScharf.getStyleClass().add("toolbar-icons");
		btnSearchUnscharf.getStyleClass().add("toolbar-icons");
		txtSearch.getStyleClass().add("search");
		
        Image save = new Image(getClass().getResourceAsStream("../ownresources/save.png"));
        Image add = new Image(getClass().getResourceAsStream("../ownresources/add.png"));
        Image delete = new Image(getClass().getResourceAsStream("../ownresources/remove.png"));
        Image undo = new Image(getClass().getResourceAsStream("../ownresources/undo.png"));
        Image redo = new Image(getClass().getResourceAsStream("../ownresources/redo.png"));
        Image searchScharf = new Image(getClass().getResourceAsStream("../ownresources/search.png"));
        Image searchUnscharf = new Image(getClass().getResourceAsStream("../ownresources/search-unscharf.png"));
        
        btnSave.setGraphic(new ImageView(save));
        btnAdd.setGraphic(new ImageView(add));
        btnDelete.setGraphic(new ImageView(delete)); 
        btnUndo.setGraphic(new ImageView(undo));
        btnRedo.setGraphic(new ImageView(redo));
        btnSearchScharf.setGraphic(new ImageView(searchScharf));
        btnSearchUnscharf.setGraphic(new ImageView(searchUnscharf));
        btnSwapView.setGraphic(pen);
        
        btnSave.setTooltip(new Tooltip("Speichern"));
        btnAdd.setTooltip(new Tooltip("Hinzufügen"));
        btnDelete.setTooltip(new Tooltip("Löschen"));
        btnUndo.setTooltip(new Tooltip("Rückgängig"));
        btnRedo.setTooltip(new Tooltip("Wiederherstellen"));
        btnSwapView.setTooltip(new Tooltip("Ansicht wechseln"));
        btnSearchScharf.setTooltip(new Tooltip("Scharfe Suche"));
        btnSearchUnscharf.setTooltip(new Tooltip("Unscharfe Suche"));
    }

    
    /**
     * triggers the button click events
     */
    private void setupEventHandlers() {
    	btnAdd.setOnAction(e -> {
    		model.add();
    		selector.scrollTo(model.getData().size()-1);
    		table.scrollTo(model.getData().size()-1);
    	});
    	
    	btnDelete.setOnAction(e -> {
    		model.remove();
    		selector.scrollTo(model.getMountain(model.getSelectedMountainID()));
    		table.scrollTo(model.getMountain(model.getSelectedMountainID()));
    	});
    	
    	btnSave.setOnAction(e -> {
    		model.save();
    	});
    	
    	btnUndo.setOnAction(e -> {
    		model.undo();
    	});
    	
    	btnRedo.setOnAction(e -> {
    		model.redo();
    	});
    	
    	btnSwapView.setOnAction(e -> {
    		model.tableVisibilityProperty().set(!model.getTableVisibility());
    		txtSearch.textProperty().set("");
    		if(btnSwapView.getGraphic() == list) {
    			btnSwapView.setGraphic(pen);
    		} else {
    			btnSwapView.setGraphic(list);
    		}
    	});
    	
    	btnSearchScharf.setOnAction(e -> {
    		if(txtSearch.textProperty().get().equals("") || txtSearch.textProperty().get().equals(null)) {
    			return;
    		}
    		model.searchScharf();
    		selector.scrollTo(model.getSelectedMountainID());
    		table.scrollTo(model.getSelectedMountainID());
    	});
    	
    	btnSearchUnscharf.setOnAction(e -> {
    		if(txtSearch.textProperty().get().equals("") || txtSearch.textProperty().get().equals(null)) {
    			return;
    		}
    		model.searchUnscharf();
    		selector.scrollTo(model.getSelectedMountainID());
    		table.scrollTo(model.getSelectedMountainID());
    	});
    }

    
	/**
	 * Binds the properties of the nodes of this editor to the properties of the mountain proxy of the model.
	 */
    private void setupBindings() {
    	btnUndo.disableProperty().bind(model.undoDisabledProperty());
    	btnRedo.disableProperty().bind(model.redoDisabledProperty());
    	model.searchContentProperty().bind(txtSearch.textProperty());
    }
}
