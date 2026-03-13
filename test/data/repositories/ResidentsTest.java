package data.repositories;

import data.models.Resident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResidentsTest {
    private Residents residents;
    private Resident resident1;
    private Resident resident2;

    @BeforeEach
    void setUp() {
        residents = new Residents();

        resident1 = new Resident();
        resident1.setName("Oluwemawe Johnson");
        resident1.setPhoneNumber("08011111111");
        resident1.setHouseAddress("Block A, Flat 5");

        resident2 = new Resident();
        resident2.setName("Alimzy Papa");
        resident2.setPhoneNumber("08022222222");
        resident2.setHouseAddress("Block B, Flat 10");
    }

    @Test
    void testSaveResident() {
        Resident saved = residents.save(resident1);
        assertEquals(1L, residents.count());
        assertTrue(saved.getId().startsWith("RES-"));
    }

    @Test
    void testSaveMultipleResidents() {
        Resident savedOne = residents.save(resident1);
        Resident savedTwo = residents.save(resident2);
        assertEquals(2L, residents.count());
        assertTrue(savedOne.getId().startsWith("RES-"));
        assertTrue(savedTwo.getId().startsWith("RES-"));
    }

    @Test
    void testFindById() {
        Resident saved = residents.save(resident1);
        Resident found = residents.findById(saved.getId());
        assertNotNull(found);
        assertEquals("Oluwemawe Johnson", found.getName());
        assertEquals("Block A, Flat 5", found.getHouseAddress());
    }

    @Test
    void testFindByIdNotFound() {
        Resident found = residents.findById("999");
        assertNull(found);
    }

    @Test
    void testFindAll() {
        residents.save(resident1);
        residents.save(resident2);
        assertEquals(2, residents.findAll().size());
    }

    @Test
    void testFindAllEmpty() {
        assertEquals(0, residents.findAll().size());
    }

    @Test
    void testDelete() {
        Resident saved = residents.save(resident1);
        residents.delete(resident1);
        assertEquals(0L, residents.count());
        assertNull(residents.findById(saved.getId()));
    }

    @Test
    void testDeleteById() {
        Resident saved = residents.save(resident1);
        residents.save(resident2);
        residents.deleteById(saved.getId());
        assertEquals(1L, residents.count());
        assertNull(residents.findById(saved.getId()));
    }

    @Test
    void testDeleteAll() {
        residents.save(resident1);
        residents.save(resident2);
        residents.deleteAll();
        assertEquals(0L, residents.count());
    }

    @Test
    void testUpdateResident() {
        Resident saved = residents.save(resident1);
        resident1.setName("Oluwemawe Updated");
        resident1.setHouseAddress("Block C, Flat 15");
        residents.save(resident1);
        Resident found = residents.findById(saved.getId());
        assertEquals("Oluwemawe Updated", found.getName());
        assertEquals("Block C, Flat 15", found.getHouseAddress());
        assertEquals(1L, residents.count());
    }
}