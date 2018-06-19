package ch.fhnw.oop2.swissmountainsfx.presentationmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class TestMountainModel {
	
	MountainModel model;
	ObservableList<Mountain> testData = FXCollections.observableArrayList();
	Mountain mountain;
	Mountain mountain2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		model = new MountainModel();
		mountain = new Mountain(100000, "NBergname", 1000.0, "Testberg", "Test Region", 
				"Aargau", "Flachland", 300.0, "Isolationspunkt", 500.0, 
				"Scharte123", "Eine Bildunterschrift");
		mountain2 = new Mountain(200000, "NBergname des zweiten Berges", 109.0, "Testberg2", "Test Region2", 
				"Bern", "Oberland", 400.0, "Isolationspunkt", 300.0, 
				"Scharte123", "Eine Bildunterschrift");
		model.getData().add(mountain);
		model.getData().add(mountain2);
		model.setSelectedMountainID(mountain.getId());
	}

	@AfterEach
	void tearDown() throws Exception {
		model.getData().remove(mountain);
		model.getData().remove(mountain2);

	}

	
	@Test
	void testSave() {
        String nameBefore = model.getData().get(0).getName();
        String nameAfter = "Berg";
        model.getData().get(0).setName(nameAfter);
        model.save();
        
        assertNotSame(nameBefore, nameAfter);

        model.getData().get(0).setName(nameBefore);
        model.save();
	}
	
	
	@Test
	void testAddandRemove(){
		int dataSize = model.getData().size();
		
		model.add();
		
		assertEquals(dataSize+1, model.getData().size());	
		assertEquals(model.getData().get(dataSize).getId(), model.getSelectedMountainID());
		
		model.getData().remove(dataSize);
		assertEquals(dataSize , model.getData().size());
	}
	
	@Test
	void testUndoRedo() {
		model.setSelectedMountainID(0);
		String nameBefore = model.getMountain(model.getSelectedMountainID()).getName();
		String nameAfter = "Albisbergt";

		model.getData().get(0).setName(nameAfter);

		model.undo();
		assertSame(nameBefore, model.getData().get(0).getName());
		
		model.redo();
		assertSame(nameAfter, model.getData().get(0).getName());
	}
	
	@Test
	void testSearchScharf() {
		model.add(); //to select a different mountain
		model.setSearchContent("bergn");
		model.searchScharf();
		assertEquals(mountain.getId(), model.getMountain(model.getSelectedMountainID()).getId());
		
		model.searchScharf();
		assertEquals(mountain2.getId(), model.getMountain(model.getSelectedMountainID()).getId());
		
	}
	
	@Test
	void testSearchUnscharf() {
		model.add(); //to select a different mountain
		//1 character wrong
		model.setSearchContent("bergnme");
		model.searchUnscharf();
		assertEquals(mountain.getId(), model.getMountain(model.getSelectedMountainID()).getId());
		
		model.add(); //to select a different mountain
		//3 characters wrong
		model.setSearchContent("brgme");
		model.searchUnscharf();
		assertNotEquals(mountain.getId(), model.getMountain(model.getSelectedMountainID()).getId());
	}
	

}
