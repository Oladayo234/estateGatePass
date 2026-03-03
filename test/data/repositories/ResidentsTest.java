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
        assertEquals(1, saved.getId());
        assertNotNull(saved);
    }

    @Test
    void testSaveMultipleResidents() {
        residents.save(resident1);
        residents.save(resident2);
        assertEquals(2L, residents.count());
        assertEquals(1, resident1.getId());
        assertEquals(2, resident2.getId());
    }

    @Test
    void testFindById() {
        residents.save(resident1);
        Resident found = residents.findById(1);
        assertNotNull(found);
        assertEquals("Oluwemawe Johnson", found.getName());
        assertEquals("Block A, Flat 5", found.getHouseAddress());
    }

    @Test
    void testFindByIdNotFound() {
        Resident found = residents.findById(999);
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
        residents.save(resident1);
        residents.save(resident2);
        residents.delete(resident1);
        assertEquals(1L, residents.count());
        assertNull(residents.findById(1));
    }

    @Test
    void testDeleteById() {
        residents.save(resident1);
        residents.save(resident2);
        residents.deleteById(1);
        assertEquals(1L, residents.count());
        assertNull(residents.findById(1));
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
        residents.save(resident1);
        resident1.setName("Oluwemawe Updated");
        resident1.setHouseAddress("Block C, Flat 15");
        residents.save(resident1);

        Resident found = residents.findById(1);
        assertEquals("Oluwemawe Updated", found.getName());
        assertEquals("Block C, Flat 15", found.getHouseAddress());
        assertEquals(1L, residents.count());
    }

    @Test
    void testIdAutoIncrement() {
        residents.save(resident1);
        residents.save(resident2);
        residents.deleteById(1);

        Resident resident3 = new Resident();
        resident3.setName("Christian Samuel");
        residents.save(resident3);

        assertEquals(3, resident3.getId());
    }
}