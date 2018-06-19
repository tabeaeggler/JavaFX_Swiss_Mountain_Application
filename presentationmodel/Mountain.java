package ch.fhnw.oop2.swissmountainsfx.presentationmodel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * A mountain object that contains all the information that was loaded from the data stored in properties.
 * 
 * @author Benjamin Huber
 * @author Tabea Eggler
 * 
 */
public class Mountain {
	
	/**
	 *  the ID of this mountain
	 */
	private final IntegerProperty id = new SimpleIntegerProperty();
	
	/**
	 * the name of this mountain
	 */
	private final StringProperty name = new SimpleStringProperty();
	
	/**
	 * the height of this mountain
	 */
	private final DoubleProperty height = new SimpleDoubleProperty();
	
	/**
	 * the type this mountain is of
	 */
	private final StringProperty type = new SimpleStringProperty();
	
	/**
	 * the region, which contains this mountain
	 */
	private final StringProperty region = new SimpleStringProperty();
	
	/**
	 * cantons the cantons in which this mountain is
	 */
	private final StringProperty cantons = new SimpleStringProperty();
	
	/**
	 * the area in which this mountain is
	 */
	private final StringProperty range = new SimpleStringProperty();
	
	/**
	 * the dominance of this mountain
	 */
	private final DoubleProperty isolation = new SimpleDoubleProperty();
	
	/**
	 * the isolation point from which the isolation of this mountain is measured from
	 */
	private final StringProperty isolationPoint = new SimpleStringProperty();
	
	/**
	 * the prominence of this mountain
	 */
	private final DoubleProperty prominence = new SimpleDoubleProperty();
	
	/**
	 * the prominence point from which the prominence of this mountain is measured from
	 */
	private final StringProperty prominencePoint = new SimpleStringProperty();
	
	/**
	 * the caption of the picture of this mountain
	 */
	private final StringProperty caption = new SimpleStringProperty();
	
	/**
	 * Constructs a new mountain object.
	 * 
	 * @param id the ID of this mountain
	 * @param name the name of this mountain
	 * @param height the height of this mountain
	 * @param type the type this mountain is of
	 * @param region the region, which contains this mountain
	 * @param cantons the cantons in which this mountain is
	 * @param range the area in which this mountain is
	 * @param isolation the dominance of this mountain
	 * @param isolationPoint the isolationpoint from which the isolation of this mountain is measured from
	 * @param prominence the prominence of this mountain
	 * @param prominencePoint the prominence point from which the prominence of this mountain is measured from
	 * @param caption the caption of the picture of this mountain
	 */
	public Mountain(int id, String name, double height, String type, String region,
			String cantons, String range, double isolation, String isolationPoint,
			double prominence, String prominencePoint, String caption) {
		this.id.set(id);
		this.name.set(name);
		this.height.set(height);
		this.type.set(type);
		this.region.set(region);
		this.cantons.set(cantons);
		this.range.set(range);
		this.isolation.set(isolation);
		this.isolationPoint.set(isolationPoint);
		this.prominence.set(prominence);
		this.prominencePoint.set(prominencePoint);
		this.caption.set(caption);
	}
	
	
	public Mountain() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Defines all information per line.
	 * 
	 * @return String with all information of this mountain
	 */
	public String infoAsLine() {
		return String.join(";",
							Integer.toString(getId()),
							getName(),
							Double.toString(getHeight()),
							getType(),
							getRegion(),
							getCantons(),
							getRange(),
							Double.toString(getIsolation()),
							getIsolationPoint(),
							Double.toString(getProminence()),
							getProminencePoint(),
							getCaption()
						);
	}
	
			
	/**
	 * Returns the ID property of this mountain.
	 * 
	 * @return id the ID property of this mountain
	 */
	public IntegerProperty idProperty() {
		return this.id;
	}
	

	/**
	 * Returns the ID of this mountain.
	 * 
	 * @return id returns the ID of this mountain
	 */
	public int getId() {
		return this.idProperty().get();
	}
	
	
	/**
	 * Sets the ID of this mountain.
	 * 
	 * @param id the ID of this mountain
	 */
	public void setId(final int id) {
		this.idProperty().set(id);
	}
	
	
	/**
	 * Returns the name property of this mountain.
	 * 
	 * @return name the name property of this mountain
	 */
	public StringProperty nameProperty() {
		return this.name;
	}
	
	
	/**
	 * Returns the name of this mountain.
	 * 
	 * @return name the name of this mountain 
	 */
	public String getName() {
		return this.nameProperty().get();
	}
	
	
	/**
	 * Sets the name of this mountain.
	 * 
	 * @param name the name of this mountain
	 */
	public void setName(final String name) {
		this.nameProperty().set(name);
	}
	
	
	/**
	 * Returns the height property of this mountain.
	 * 
	 * @return height the height property of this mountain
	 */
	public DoubleProperty heightProperty() {
		return this.height;
	}
	
	
	/**
	 * Returns the height of this mountain.
	 * 
	 * @return height the height of this mountain 
	 */
	public double getHeight() {
		return this.heightProperty().get();
	}
	
	
	/**
	 * Sets the height of this mountain.
	 * 
	 * @param height the height of this mountain
	 */
	public void setHeight(final double height) {
		this.heightProperty().set(height);
	}
	
	
	/**
	 * Returns the type property of this mountain.
	 * 
	 * @return type the type property of this mountain
	 */
	public StringProperty typeProperty() {
		return this.type;
	}
	

	/**
	 * Returns the type of this mountain.
	 * 
	 * @return type the type of this mountain
	 */
	public String getType() {
		return this.typeProperty().get();
	}
	

	/**
	 * Sets the type of this mountain.
	 * 
	 * @param type the type of this mountain
	 */
	public void setType(final String type) {
		this.typeProperty().set(type);
	}
	

	/**
	 * Returns the region property of this mountain.
	 * 
	 * @return region the region property of this mountain
	 */
	public StringProperty regionProperty() {
		return this.region;
	}
	

	/**
	 * Returns the region of this mountain.
	 * 
	 * @return region the region of this mountain
	 */
	public String getRegion() {
		return this.regionProperty().get();
	}
	

	/**
	 * Sets the region of this mountain.
	 * 
	 * @param region the region of this mountain
	 */
	public void setRegion(final String region) {
		this.regionProperty().set(region);
	}
	

	/**
	 * Returns the cantons property of this mountain.
	 * 
	 * @return cantons the cantons property of this mountain
	 */
	public StringProperty cantonsProperty() {
		return this.cantons;
	}
	

	/**
	 * Returns the cantons of this mountain.
	 * 
	 * @return cantons the cantons of this mountain.
	 */
	public String getCantons() {
		return this.cantonsProperty().get();
	}
	
	
	/**
	 * Sets the cantons of this mountain.
	 * 
	 * @param cantons the cantons of this mountain
	 */
	public void setCantons(final String cantons) {
		this.cantonsProperty().set(cantons);
	}
	

	/**
	 * Returns the range property of this mountain.
	 * 
	 * @return range the range property of this mountain
	 */
	public StringProperty rangeProperty() {
		return this.range;
	}
	

	/**
	 * Returns the range of this mountain.
	 * 
	 * @return range the range of this mountain
	 */
	public String getRange() {
		return this.rangeProperty().get();
	}
	

	/**
	 * Sets the range of this mountain.
	 * 
	 * @param range the range of this mountain
	 */
	public void setRange(final String range) {
		this.rangeProperty().set(range);
	}
	

	/**
	 * Returns the isolation property of this mountain.
	 * 
	 * @return isolation the isolation property of this mountain
	 */
	public DoubleProperty isolationProperty() {
		return this.isolation;
	}
	

	/**
	 * Returns the isolation of this mountain.
	 * 
	 * @return isolation the isolation of this mountain
	 */
	public double getIsolation() {
		return this.isolationProperty().get();
	}
	

	/**
	 * Sets the isolation of this mountain.
	 * 
	 * @param isolation the isolation of this mountain
	 */
	public void setIsolation(final double isolation) {
		this.isolationProperty().set(isolation);
	}
	

	/**
	 * Returns the isolation point property of this mountain.
	 * 
	 * @return isolationPoint the isolation point property of this mountain
	 */
	public StringProperty isolationPointProperty() {
		return this.isolationPoint;
	}
	

	/**
	 * Returns the isolation point od this mountain.
	 * 
	 * @return isolationPoint the isolation point of this mountain
	 */
	public String getIsolationPoint() {
		return this.isolationPointProperty().get();
	}
	

	/**
	 * Sets the isolation point of this mountain.
	 * 
	 * @param isolationPoint the isolationpoint of this mountain
	 */
	public void setIsolationPoint(final String isolationPoint) {
		this.isolationPointProperty().set(isolationPoint);
	}
	

	/**
	 * Returns the prominence property of this mountain.
	 * 
	 * @return prominence the prominence property of this mountain
	 */
	public DoubleProperty prominenceProperty() {
		return this.prominence;
	}
	

	/**
	 * Returns the prominence of this mountain.
	 * 
	 * @return prominence the prominence of this mountain
	 */
	public double getProminence() {
		return this.prominenceProperty().get();
	}
	

	/**
	 * Sets the prominence of this mountain
	 * 
	 * @param promience the prominence of this mountain
	 */
	public void setProminence(final double promience) {
		this.prominenceProperty().set(promience);
	}
	

	/**
	 * Returns the prominence point property of this mountain.
	 * 
	 * @return prominencePoint the prominence point property of this mountain
	 */
	public StringProperty prominencePointProperty() {
		return this.prominencePoint;
	}
	

	/**
	 * Returns the prominence point of this mountain.
	 * 
	 * @return prominencePoint returns the prominence point of this mountain
	 */
	public String getProminencePoint() {
		return this.prominencePointProperty().get();
	}
	

	/**
	 * Sets the prominence point of this mountain.
	 * 
	 * @param promiencePoint the prominence point of this mountain
	 */
	public void setProminencePoint(final String promiencePoint) {
		this.prominencePointProperty().set(promiencePoint);
	}
	

	/**
	 * Returns the caption property of this mountain.
	 * 
	 * @return caption the caption property of this mountain
	 */
	public StringProperty captionProperty() {
		return this.caption;
	}
	

	/**
	 * Returns the caption of this mountain.
	 * 
	 * @return caption the caption of this mountain
	 */
	public String getCaption() {
		return this.captionProperty().get();
	}
	

	/**
	 * Sets the caption of this mountain
	 * 
	 * @param caption the caption of this mountain
	 */
	public void setCaption(final String caption) {
		this.captionProperty().set(caption);
	}	
	
}
