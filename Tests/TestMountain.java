package ch.fhnw.oop2.swissmountainsfx.presentationmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Benja
 *
 */
class TestMountain {

	Mountain mountain;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		mountain = new Mountain(3, "Tabea Beni Berg", 1000.0, "Testberg", "Test Region", 
				"Aargau", "Flachland", 300.0, "Isolationspunkt", 500.0, 
				"Scharte123", "Eine Bildunterschrift");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testMountain() {
		assertEquals(3, mountain.getId());
		assertEquals("Tabea Beni Berg", mountain.getName());
		assertEquals(1000.0, mountain.getHeight(), 0.0001);
		assertEquals("Testberg", mountain.getType());
		assertEquals("Test Region", mountain.getRegion());
		assertEquals("Aargau", mountain.getCantons());
		assertEquals("Flachland" , mountain.getRange());
		assertEquals(300.0, mountain.getIsolation(), 0.0001);
		assertEquals("Isolationspunkt", mountain.getIsolationPoint());
		assertEquals(500.0,  mountain.getProminence(), 0.0001);
		assertEquals("Scharte123", mountain.getProminencePoint());
		assertEquals("Eine Bildunterschrift", mountain.getCaption());
	}
	
	@Test
	void testInfoAsLine() {
		assertEquals("3;Tabea Beni Berg;1000.0;Testberg;Test Region;Aargau;Flachland;300.0;Isolationspunkt;500.0;Scharte123;Eine Bildunterschrift", 
				mountain.infoAsLine());
	}

}
