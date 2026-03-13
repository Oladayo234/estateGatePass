package data.repositories;

import data.models.GatePass;
import data.models.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GatePassesTest {
    private GatePasses gatePasses;
    private GatePass pass1;
    private GatePass pass2;

    @BeforeEach
    void setUp() {
        gatePasses = new GatePasses();

        pass1 = new GatePass();
        pass1.setResidentId("RES-1234-ABCD");
        pass1.setVisitorsId("VIS-1234-ABCD");
        pass1.setExpirationDate(LocalDateTime.now().plusHours(24));
        pass1.setType(Type.ENTRY);
        pass1.setValid(true);

        pass2 = new GatePass();
        pass2.setResidentId("RES-5678-EFGH");
        pass2.setVisitorsId("VIS-5678-EFGH");
        pass2.setExpirationDate(LocalDateTime.now().plusHours(48));
        pass2.setType(Type.ENTRY);
        pass2.setValid(true);
    }

    @Test
    void testSaveGatePass() {
        GatePass saved = gatePasses.save(pass1);
        assertEquals(1L, gatePasses.count());
        assertTrue(saved.getId().startsWith("GP-"));
    }

    @Test
    void testSaveMultipleGatePasses() {
        GatePass savedOne = gatePasses.save(pass1);
        GatePass savedTwo = gatePasses.save(pass2);
        assertEquals(2L, gatePasses.count());
        assertTrue(savedOne.getId().startsWith("GP-"));
        assertTrue(savedTwo.getId().startsWith("GP-"));
    }

    @Test
    void testFindById() {
        GatePass saved = gatePasses.save(pass1);
        GatePass found = gatePasses.findById(saved.getId());
        assertNotNull(found);
        assertEquals("RES-1234-ABCD", found.getResidentId());
        assertTrue(found.isValid());
    }

    @Test
    void testFindByIdNotFound() {
        GatePass found = gatePasses.findById("999");
        assertNull(found);
    }

    @Test
    void testFindAll() {
        gatePasses.save(pass1);
        gatePasses.save(pass2);
        assertEquals(2, gatePasses.findAll().size());
    }

    @Test
    void testFindAllEmpty() {
        assertEquals(0, gatePasses.findAll().size());
    }

    @Test
    void testDelete() {
        GatePass saved = gatePasses.save(pass1);
        gatePasses.delete(pass1);
        assertEquals(0L, gatePasses.count());
        assertNull(gatePasses.findById(saved.getId()));
    }

    @Test
    void testDeleteById() {
        GatePass saved = gatePasses.save(pass1);
        gatePasses.save(pass2);
        gatePasses.deleteById(saved.getId());
        assertEquals(1L, gatePasses.count());
        assertNull(gatePasses.findById(saved.getId()));
    }

    @Test
    void testDeleteAll() {
        gatePasses.save(pass1);
        gatePasses.save(pass2);
        gatePasses.deleteAll();
        assertEquals(0L, gatePasses.count());
    }

    @Test
    void testUpdateGatePass() {
        GatePass saved = gatePasses.save(pass1);
        pass1.setValid(false);
        gatePasses.save(pass1);
        GatePass found = gatePasses.findById(saved.getId());
        assertFalse(found.isValid());
        assertEquals(1L, gatePasses.count());
    }

    @Test
    void testGatePassIsExpired() {
        GatePass saved = gatePasses.save(pass1);
        pass1.setExpirationDate(LocalDateTime.now().minusHours(1));
        assertTrue(saved.isExpired());
    }

    @Test
    void testGatePassIsNotExpired() {
        GatePass saved = gatePasses.save(pass1);
        assertFalse(saved.isExpired());
    }
}