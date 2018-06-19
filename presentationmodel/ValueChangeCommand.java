package ch.fhnw.oop2.swissmountainsfx.presentationmodel;

import javafx.beans.property.Property;

/**
 * Command that can be undone or redone
 * 
 * @author Benjamin Huber
 * @author Tabea Eggler
 *
 * @param <T> datatype of any values that have been changed
 */
public class ValueChangeCommand<T> implements Command {
	
	/**
	 * the presentation model that contains the functions of this application
	 */
	private final MountainModel model;
	
	/**
	 * property, that is observed by the change listener
	 */
	private final Property<T> property;
	
	/**
	 * the old value of a changed value
	 */
	private final T oldValue;
	
	/**
	 * the new changed value
	 */
	private final T newValue;
	
	
	/**
	 * Constructs a new instance of this ValueChangedCommand
	 * 
	 * @param model the presentation model that contains the functions of this application
	 * @param property property, that is observed by the change listener
	 * @param oldValue the old value of a changed value
	 * @param newValue the new changed value
	 */
	public ValueChangeCommand(MountainModel model, Property<T> property, T oldValue, T newValue) {
		this.model = model;
		this.property = property;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	
	/**
	 * Undos this change command.
	 */
	public void undo() {
		model.setPropertyValueWithoutUndoSupport(property, oldValue);
	}
	
	
	/**
	 * Redos this change command.
	 */
	public void redo() {
		model.setPropertyValueWithoutUndoSupport(property, newValue);
	}
}
