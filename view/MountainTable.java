package ch.fhnw.oop2.swissmountainsfx.view;

import ch.fhnw.oop2.swissmountainsfx.presentationmodel.Mountain;
import ch.fhnw.oop2.swissmountainsfx.presentationmodel.MountainModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

public class MountainTable extends TableView<Mountain>{
	
	/**
	 * the presentation model, which contains the functionality of this application
	 */
	private MountainModel model;
	
	
	/**
	 * Constructs a new selectable tableview.
	 * 
	 * @param model
	 */
	public MountainTable(MountainModel model) {
        this.model = model;
        this.setItems(model.getData());
        getStyleClass().add("table");
        initializeControls();
        setupValueChangedListeners();
    }
	
	
	/**
	 * Defines the content in a table view
	 */
	private void initializeControls() {
		this.setEditable(true);
		TableColumn<Mountain, String> nameColumn = new TableColumn<>("Name"); 
		nameColumn.setCellValueFactory(e -> e.getValue().nameProperty());
		
		TableColumn<Mountain, String> heightColumn = new TableColumn<>("Höhe(m)"); 
		heightColumn.setCellValueFactory(e -> e.getValue().heightProperty().asString());
			
		TableColumn<Mountain, String> rangeColumn = new TableColumn<>("Gebiet"); 
		rangeColumn.setCellValueFactory(e -> e.getValue().rangeProperty());
		
		
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(str -> {
            (str.getTableView().getItems().get(str.getTablePosition().getRow())).
                    setName(str.getNewValue());
        });
        
        heightColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        heightColumn.setOnEditCommit(str -> {
            (str.getTableView()
            		.getItems()
            		.get(str.getTablePosition()
            		.getRow()))
            		.setHeight(Double.valueOf(str.getNewValue()));
        });
        
        rangeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        rangeColumn.setOnEditCommit(str -> {
            (str.getTableView()
            		.getItems()
            		.get(str.getTablePosition()
            		.getRow()))
            		.setRange(str.getNewValue());
        });
		
		this.getColumns().addAll(nameColumn, heightColumn, rangeColumn);
	}

	 
    /**
     * Adds listeners to the selected mountain ID property of the model.
     */
	private void setupValueChangedListeners() {
        getSelectionModel().selectedItemProperty().addListener((source, oldValue, newValue) -> {
                     if (newValue == null) {
                    	 model.setSelectedMountainID(MountainModel.NO_SELECTION);
                     } else {
                    	 model.setSelectedMountainID(newValue.getId());
                     }
               });

        model.selectedMountainIDProperty().addListener((source, oldValue, newValue) -> {
                     if (model.getSelectedMountainID() == MountainModel.NO_SELECTION) {
                    	 getSelectionModel().clearSelection();
                     } else {
                    	 getSelectionModel().select(model.getMountain(newValue.intValue()));
                     }
               });
        }
}
