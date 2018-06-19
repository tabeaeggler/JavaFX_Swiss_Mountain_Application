package ch.fhnw.oop2.swissmountainsfx.view;

import ch.fhnw.oop2.swissmountainsfx.presentationmodel.Mountain;
import ch.fhnw.oop2.swissmountainsfx.presentationmodel.MountainModel;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;


/**
 * A selectable list, which also adds listeners to the selected mountain property of the model.
 * 
 * @author Benjamin Huber
 * @author Tabea Eggler
 *
 */
public class MountainSelector extends ListView<Mountain>{
   
	/**
	 * the presentation model, which contains the functionality of this application
	 */
	private final MountainModel model;
    

	/**
	 * Constructs a new selectable listview.
	 * 
	 * @param model
	 */
    public MountainSelector(MountainModel model) {
        this.model = model;
        initializeSelf();
        layoutControls();
        setupValueChangedListeners();
    }
    

    /**
     * Defines a single selection 
     */
    private void initializeSelf() {
    	this.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
 
    
    /**
     * Adds the stylesheet to this listview.
     */
    private void layoutControls() {
    	this.getStyleClass().add("listview");
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
