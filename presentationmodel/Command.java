package ch.fhnw.oop2.swissmountainsfx.presentationmodel;

/**
 * Any action executed is a command.
 * 
 * @author Benjamin Huber
 * @author Tabea Eggler
 *
 */
public interface Command {
	void undo();
	void redo();
}
