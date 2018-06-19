package ch.fhnw.oop2.swissmountainsfx.presentationmodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contains the functionality of the application. This inlcudes setting up
 * bindings to the properties and loading the data from csv file.
 * 
 * @author Benjamin Huber
 * @author Tabea Eggler
 *
 */
public class MountainModel {

	/**
	 * used to set the selected mountain id property to -1 if there is no selection
	 */
	public static final int NO_SELECTION = -1;

	/**
	 * the path to the mountains csv file
	 */
	String csvFile = "../resources/data/mountains.csv";

	/**
	 * the list of mountains containing data
	 */
	private ObservableList<Mountain> data = FXCollections.observableArrayList();

	/**
	 * a logger, which is used to catch exceptions
	 */
	private Logger LOGGER = Logger.getLogger(MountainModel.class.getName());

	/**
	 * the title of the application
	 */
	private final StringProperty applicationTitle = new SimpleStringProperty("Swiss Mountains Application");

	/**
	 * a mountain object in which loaded data gets stored
	 */
	public Mountain mountain;
	
	/**
	 * the currently selected mountain
	 */
	private final ObjectProperty<Mountain> selectedMountain = new SimpleObjectProperty<>();

	/**
	 * the id of the currently selected mountain
	 */
	private final IntegerProperty selectedMountainID = new SimpleIntegerProperty(NO_SELECTION);

	/**
	 * a proxy mountain object used for bindings
	 */
	private final Mountain mountainProxy = new Mountain(0, null, 0, null, null, null, null, 0.0, null, 0.0, null, null);

	/**
	 * the mountain, which is set as selected when search methods are used, will be null if there is no searching match
	 */
	private Mountain foundMountain;
	
	/**
	 * the string which is in the search textfield of the toolbar
	 */
	private final StringProperty searchContent = new SimpleStringProperty();

	/**
	 * command stack of for undo
	 */
	private final ObservableList<Command> undoList = FXCollections.observableArrayList();
	
	/**
	 * command stack for redo
	 */
	private final ObservableList<Command> redoList = FXCollections.observableArrayList();

	/**
	 * used to enable and disable the undo button of the toolbar
	 */
	private final BooleanProperty undoDisabled = new SimpleBooleanProperty();
	
	/**
	 * used to enable and disable the redo button of the toolbar
	 */
	private final BooleanProperty redoDisabled = new SimpleBooleanProperty();

	/**
	 * used to set the visibility of the tableview
	 */
	private final BooleanProperty tableVisibility = new SimpleBooleanProperty();

	/**
	 * observes if there are any changes on the properties
	 */
	private final ChangeListener propertyChangeListenerForUndoSupport = (observable, oldValue, newValue) -> {
		redoList.clear();
		undoList.add(0, new ValueChangeCommand(MountainModel.this, (Property) observable, oldValue, newValue));
	};

	
	/**
	 * Constructs an new model object.
	 */
	public MountainModel() {
		data = loadData(getClass().getResourceAsStream(csvFile));

		undoDisabled.bind(Bindings.isEmpty(undoList));
		redoDisabled.bind(Bindings.isEmpty(redoList));

		selectedMountainIDProperty().addListener((observable, oldValue, newValue) -> {
			Mountain oldSelection = getMountain(oldValue.intValue());
			Mountain newSelection = getMountain(newValue.intValue());

			if (oldSelection != null) {
				unbindFromProxy(oldSelection);
				disableUndoSupport(oldSelection);
			}

			if (newSelection != null) {
				bindToProxy(newSelection);
				enableUndoSupport(newSelection);
			}
		});
		selectedMountainIDProperty().addListener(propertyChangeListenerForUndoSupport);
	}

	/**
	 * Returns a list of mountains containing loaded data.
	 * 
	 * @param inputStream
	 *            the csv resource as stream
	 * @return loadedDataList the list of mountains containing loaded data
	 * @throws IOException
	 *             if there is an error by reading the input stream
	 */
	private ObservableList<Mountain> loadData(InputStream inputStream) {
		ObservableList<Mountain> loadedDataList = FXCollections.observableArrayList();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader((inputStream), "UTF8"))) {
			reader.lines().skip(1).forEach(s -> {
				String[] parts = s.split(";");
				mountain = new Mountain(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]), parts[3],
						parts[4], parts[5], parts[6], Double.parseDouble(parts[7]), parts[8],
						Double.parseDouble(parts[9]), parts[10], parts[11]);
				loadedDataList.add(mountain);

			});
		} catch (IOException exception) {
			LOGGER.log(Level.SEVERE, "", exception);
		}
		return loadedDataList;
	}
	
	
	/**
	 * Adds a new mountain to the list.
	 */
	public void add() {
		int newId = (int) System.currentTimeMillis();
		Mountain mountain = new Mountain(newId, "", 0.0, "", "", "", "", 0.0, "", 0.0, "", "");
		data.add(mountain);
		setSelectedMountainID(mountain.getId());
	}
	

	/**
	 * Removes the currently selected mountain from the list.
	 */
	public void remove() {
		data.remove(getMountain(selectedMountainID.getValue()));
	}

	
	/**
	 * Saves all changes made into the csv file.
	 */
	public void save() {
		try (BufferedWriter writer = Files.newBufferedWriter(getPath(csvFile))) {
			writer.write(
					"ENTITY_ID;NAME;HEIGHT;TYPE;REGION;CANTONS;RANGE;ISOLATION;ISOLATIONPOINT;PROMINENCE;PROMINENCEPOINT;CAPTION");
			writer.newLine();
			data.stream().map(mountain -> mountain.infoAsLine()).forEach(line -> {
				try {
					writer.write(line);
					writer.newLine();
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			});
		} catch (IOException e) {
			throw new IllegalStateException("save failed");
		}
	}
	
	
	/**
	 * Searches and selects a mountain, which's name contains the content string of the search textfield. If a mountain, that fits to the search content is already selected, 
	 * the mountain will be skipped and a next one that fits the to the search content will be selected. 
	 * If no mountain contains the search string, foundMountain will be null
	 */
	public void searchScharf() {
		if (!getMountain(selectedMountainID.get()).equals(foundMountain)) {
			foundMountain = data.stream()
					.filter(m -> (m.getName().toLowerCase().contains(searchContent.get().toLowerCase()))).findFirst()
					.orElse(null);

			if (foundMountain != null) {
				setSelectedMountainID(foundMountain.getId());
			}
			return;
		} else if (getMountain(selectedMountainID.get()).equals(foundMountain)) {
			foundMountain = data.stream().skip(data.indexOf(foundMountain) + 1)
					.filter(m -> m.getName().toLowerCase().contains(searchContent.get().toLowerCase())).findFirst()
					.orElse(null);

			if (foundMountain != null) {
				setSelectedMountainID(foundMountain.getId());
			}
			return;
		}
	}

	
	/**
	 * Searches and selects a mountain, which's name is close to the content string of the search textfield (2 mistakes allowed).
	 */
	public void searchUnscharf() {
		for(Mountain m : data) {
			if (levenshteinDistance(m.getName().toLowerCase(), searchContent.get().toLowerCase()) < 3) {
				setSelectedMountainID(m.getId());
				return;
			}
		}
	}

	
	/**
	 * Returns the difference between 2 CharSequences as an integer value.
	 * 
	 * @param lhs the first string that will be compared to the second one
	 * @param rhs the second string that will be compared to the first one
	 * 
	 * @return int the difference between 2 CharSequances 
	 */
	private int levenshteinDistance(CharSequence lhs, CharSequence rhs) {
		int len0 = lhs.length() + 1;
		int len1 = rhs.length() + 1;

		// the array of distances
		int[] cost = new int[len0];
		int[] newcost = new int[len0];

		// initial cost of skipping prefix in String s0
		for (int i = 0; i < len0; i++)
			cost[i] = i;

		// dynamically computing the array of distances

		// transformation cost for each letter in s1
		for (int j = 1; j < len1; j++) {
			// initial cost of skipping prefix in String s1
			newcost[0] = j;

			// transformation cost for each letter in s0
			for (int i = 1; i < len0; i++) {
				// matching current letters in both strings
				int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

				// computing cost for each transformation
				int cost_replace = cost[i - 1] + match;
				int cost_insert = cost[i] + 1;
				int cost_delete = newcost[i - 1] + 1;

				// keep minimum cost
				newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
			}

			// swap cost/newcost arrays
			int[] swap = cost;
			cost = newcost;
			newcost = swap;
		}

		// the distance is the cost for transforming all letters in both strings
		return cost[len0 - 1];
	}

	
	/**
	 * Returns the path of the file.
	 * 
	 * @param fileName the name of the file to save
	 *            
	 * @return Path the path of the file
	 */
	private Path getPath(String fileName) {
		try {
			return Paths.get(getClass().getResource(fileName).toURI());
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}

	
	/**
	 * Sets the properties value without undo support.
	 * 
	 * @param property the property, which is listened to
	 * @param newValue a new value which is set in the property
	 */
	<T> void setPropertyValueWithoutUndoSupport(Property<T> property, T newValue) {
		property.removeListener(propertyChangeListenerForUndoSupport);
		property.setValue(newValue);
		property.addListener(propertyChangeListenerForUndoSupport);
	}

	
	/**
	 * Undos the last executed command.
	 */
	public void undo() {
		if (undoList.isEmpty()) {
			return;
		}

		Command command = undoList.get(0);
		undoList.remove(0);
		redoList.add(0, command);

		command.undo();
	}

	
	/**
	 * Redos the last executed command, that has been undone.
	 */
	public void redo() {
		if (redoList.isEmpty()) {
			return;
		}

		Command command = redoList.get(0);
		redoList.remove(0);
		undoList.add(0, command);

		command.redo();
	}

	
	/**
	 * Removes the listener for undo support from given mountain.
	 * 
	 * @param mountain the mountain which the listener for undo support will be removed from
	 */
	private void disableUndoSupport(Mountain mountain) {
		mountain.idProperty().removeListener(propertyChangeListenerForUndoSupport);
		mountain.nameProperty().removeListener(propertyChangeListenerForUndoSupport);
		mountain.heightProperty().removeListener(propertyChangeListenerForUndoSupport);
		mountain.typeProperty().removeListener(propertyChangeListenerForUndoSupport);
		mountain.regionProperty().removeListener(propertyChangeListenerForUndoSupport);
		mountain.cantonsProperty().removeListener(propertyChangeListenerForUndoSupport);
		mountain.rangeProperty().removeListener(propertyChangeListenerForUndoSupport);
		mountain.isolationProperty().removeListener(propertyChangeListenerForUndoSupport);
		mountain.isolationPointProperty().removeListener(propertyChangeListenerForUndoSupport);
		mountain.prominenceProperty().removeListener(propertyChangeListenerForUndoSupport);
		mountain.prominencePointProperty().removeListener(propertyChangeListenerForUndoSupport);
		mountain.captionProperty().removeListener(propertyChangeListenerForUndoSupport);
	}

	
	/**
	 * Adds the listener for undo support to a given mountain.
	 * 
	 * @param mountain the mountain which the listener for undo support will be added to
	 */
	private void enableUndoSupport(Mountain mountain) {
		mountain.idProperty().addListener(propertyChangeListenerForUndoSupport);
		mountain.nameProperty().addListener(propertyChangeListenerForUndoSupport);
		mountain.heightProperty().addListener(propertyChangeListenerForUndoSupport);
		mountain.typeProperty().addListener(propertyChangeListenerForUndoSupport);
		mountain.regionProperty().addListener(propertyChangeListenerForUndoSupport);
		mountain.cantonsProperty().addListener(propertyChangeListenerForUndoSupport);
		mountain.rangeProperty().addListener(propertyChangeListenerForUndoSupport);
		mountain.isolationProperty().addListener(propertyChangeListenerForUndoSupport);
		mountain.isolationPointProperty().addListener(propertyChangeListenerForUndoSupport);
		mountain.prominenceProperty().addListener(propertyChangeListenerForUndoSupport);
		mountain.prominencePointProperty().addListener(propertyChangeListenerForUndoSupport);
		mountain.captionProperty().addListener(propertyChangeListenerForUndoSupport);
	}

	
	/**
	 * Binds the properties of a given mountain to the proxys' properties.
	 * 
	 * @param mountain the given mountain which will be bound to the proxy
	 */
	private void bindToProxy(Mountain mountain) {
		mountainProxy.nameProperty().bindBidirectional(mountain.nameProperty());
		mountainProxy.isolationProperty().bindBidirectional(mountain.isolationProperty());
		mountainProxy.isolationPointProperty().bindBidirectional(mountain.isolationPointProperty());
		mountainProxy.typeProperty().bindBidirectional(mountain.typeProperty());
		mountainProxy.cantonsProperty().bindBidirectional(mountain.cantonsProperty());
		mountainProxy.captionProperty().bindBidirectional(mountain.captionProperty());
		mountainProxy.heightProperty().bindBidirectional(mountain.heightProperty());
		mountainProxy.prominenceProperty().bindBidirectional(mountain.prominenceProperty());
		mountainProxy.prominencePointProperty().bindBidirectional(mountain.prominencePointProperty());
		mountainProxy.regionProperty().bindBidirectional(mountain.regionProperty());
		mountainProxy.rangeProperty().bindBidirectional(mountain.rangeProperty());
		mountainProxy.idProperty().bindBidirectional(mountain.idProperty());
	}

	
	/**
	 * Unbinds the properties of a given mountain from the proxys' properties.
	 * 
	 * @param mountain the given mountain which will be unbound from the proxy
	 */
	private void unbindFromProxy(Mountain mountain) {
		mountainProxy.nameProperty().unbindBidirectional(mountain.nameProperty());
		mountainProxy.isolationProperty().unbindBidirectional(mountain.isolationProperty());
		mountainProxy.isolationPointProperty().unbindBidirectional(mountain.isolationPointProperty());
		mountainProxy.typeProperty().unbindBidirectional(mountain.typeProperty());
		mountainProxy.cantonsProperty().unbindBidirectional(mountain.cantonsProperty());
		mountainProxy.captionProperty().unbindBidirectional(mountain.captionProperty());
		mountainProxy.heightProperty().unbindBidirectional(mountain.heightProperty());
		mountainProxy.prominenceProperty().unbindBidirectional(mountain.prominenceProperty());
		mountainProxy.prominencePointProperty().unbindBidirectional(mountain.prominencePointProperty());
		mountainProxy.regionProperty().unbindBidirectional(mountain.regionProperty());
		mountainProxy.rangeProperty().unbindBidirectional(mountain.rangeProperty());
		mountainProxy.idProperty().unbindBidirectional(mountain.idProperty());
	}

		
	/**
	 * Returns the mountain proxy of this model.
	 * 
	 * @return mountainProxy the mountain proxy of this model.
	 */
	public Mountain getMountainProxy() {
		return mountainProxy;
	}

	
	/**
	 * Searches the mountain with the given id in the datalist and returns it.
	 * 
	 * @param id the id of the mountain
	 * @return Mountain the mountain with the given id
	 */
	public Mountain getMountain(int id) {
		return data.stream().filter(mountain -> mountain.getId() == id).findAny().orElse(null);
	}

	
	/**
	 * Returns the title property of this application.
	 * 
	 * @return applicationTitle the title property of this application
	 */
	public StringProperty applicationTitleProperty() {
		return applicationTitle;
	}

	
	/**
	 * Returns the title of this application.
	 * 
	 * @return applicationTitle the title of this application
	 */
	public String getApplicationTitle() {
		return applicationTitle.get();
	}

	
	/**
	 * Sets the title of this application.
	 * 
	 * @param applicationTitle
	 *            the title of this application
	 */
	public void setApplicationTitle(String applicationTitle) {
		this.applicationTitle.set(applicationTitle);
	}

	
	/**
	 * Returns the list of mountains containing data.
	 * 
	 * @return data the list of mountains containing data
	 */
	public ObservableList<Mountain> getData() {
		return data;
	}

	
	/**
	 * Returns the currently selected mountain property
	 * 
	 * @return selectedMountain the currently selected mountain property
	 */
	public ObjectProperty<Mountain> selectedMountainProperty() {
		return selectedMountain;
	}

	
	/**
	 * Returns the currently selected mountain
	 * 
	 * @return selectedMountain the currently selected mountain
	 */
	public Mountain getSelectedMountain() {
		return selectedMountain.get();
	}

	
	/**
	 * Sets the currently selected mountain
	 * 
	 * @param mountain
	 *            the currently selected mountain
	 */
	public void setSelectedMountain(Mountain mountain) {
		this.selectedMountain.set(mountain);
	}

	
	/**
	 * Returns the currently selected mountain's ID
	 * 
	 * @return selectedMountain the currently selected mountain's ID
	 */
	public IntegerProperty selectedMountainIDProperty() {
		return selectedMountainID;
	}

	
	/**
	 * Returns the currently selected mountain's ID
	 * 
	 * @return selectedMountain the currently selected mountain's ID
	 */
	public int getSelectedMountainID() {
		return selectedMountainID.get();
	}

	
	/**
	 * Sets the currently selected mountain's ID
	 * 
	 * @param mountain
	 *            the currently selected mountain's ID
	 */
	public void setSelectedMountainID(int id) {
		this.selectedMountainID.set(id);
	}

	
	/**
	 * Returns the property that defines if undo is disabled or enabled.
	 * 
	 * @return undoDisabled the property that defines if undo is disabled or enabled
	 */
	public BooleanProperty undoDisabledProperty() {
		return undoDisabled;
	}

	
	/**
	 * Returns the value of the property that defines if undo is disabled or enabled.
	 * 
	 * @return undoDisabled the value of the property that defines if undo is disabled or enabled
	 */
	public boolean getUndoDisabled() {
		return undoDisabled.get();
	}

	
	/**
	 * Sets the value of the property that defines if undo is disabled or enabled.
	 * 
	 * @param b the value of the property that defines if undo is disabled or enabled.
	 */
	public void setUndoDisabled(boolean b) {
		this.undoDisabled.set(b);
	}

	
	/**
	 * Returns the property that defines if redo is disabled or enabled.
	 * 
	 * @return undoDisabled the property that defines if redo is disabled or enabled
	 */
	public BooleanProperty redoDisabledProperty() {
		return redoDisabled;
	}

	
	/**
	 * Returns the value of the property that defines if redo is disabled or enabled.
	 * 
	 * @return undoDisabled the value of the property that defines if redo is disabled or enabled
	 */
	public boolean getRedoDisabled() {
		return redoDisabled.get();
	}

	
	/**
	 * Sets the value of the property that defines if redo is disabled or enabled.
	 * 
	 * @param b the value of the property that defines if redo is disabled or enabled.
	 */
	public void setRedoDisabled(boolean b) {
		this.redoDisabled.set(b);
	}

	
	/**
	 * Returns the property that contains the value of the search textfield of the toolbar.
	 * 
	 * @return searchContent the property that contains the value of the search textfield of the toolbar.
	 */
	public StringProperty searchContentProperty() {
		return searchContent;
	}

	
	/**
	 * Returns the value of the property that contains the value of the search textfield of the toolbar.
	 * 
	 * @return searchContent the value of the property that contains the value of the search textfield of the toolbar.
	 */
	public String getSearchContent() {
		return searchContent.get();
	}

	
	/**
	 * Sets the value of the property that contains the value of the search textfield of the toolbar.
	 * 
	 * @param s the value of the property that contains the value of the search textfield of the toolbar.
	 */
	public void setSearchContent(String s) {
		this.searchContent.set(s);
	}

	
	/**
	 * Returns the property that defines if the tableView is visible. 
	 * 
	 * @return tableVisibility the property that defines if the tableView is visible
	 */
	public BooleanProperty tableVisibilityProperty() {
		return tableVisibility;
	}

	
	/**
	 * Returns the value of the property that defines if the tableView is visible. 
	 * 
	 * @return tableVisibility the value of the property that defines if the tableView is visible
	 */
	public boolean getTableVisibility() {
		return tableVisibility.get();
	}

	
	/**
	 * Sets the value of the property that defines if the tableView is visible. 
	 * 
	 * @param b the value of the property that defines if the tableView is visible
	 */
	public void setTableVisibility(boolean b) {
		this.tableVisibility.set(b);
	}

	
}
