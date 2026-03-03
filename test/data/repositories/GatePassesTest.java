package data.repositories;

import data.models.GatePass;
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
        pass1.setResidentId(1);
        pass1.setVisitorsId(1);
        pass1.setExpirationDate(LocalDateTime.now().plusHours(24));
        pass1.setValid(true);

        pass2 = new GatePass();
        pass2.setResidentId(2);
        pass2.setVisitorsId(2);
        pass2.setExpirationDate(LocalDateTime.now().plusHours(48));
        pass2.setValid(true);
    }

    @Test
    void testThat_GatePass_AreSaved() {
        GatePass saved = gatePasses.save(pass1);
        assertEquals(1L, gatePasses.count());
        assertEquals(1, saved.getId());
        assertNotNull(saved);
    }

    @Test
    void testThat_Multiple_GatePasses_AreSaved() {
        gatePasses.save(pass1);
        gatePasses.save(pass2);
        assertEquals(2L, gatePasses.count());
        assertEquals(1, pass1.getId());
        assertEquals(2, pass2.getId());
    }

    @Test
    void testThat_FindById_ReturnsCorrect_gatePass() {
        gatePasses.save(pass1);
        GatePass found = gatePasses.findById(1);
        assertNotNull(found);
        assertEquals(1, found.getResidentId());
        assertEquals(1, found.getVisitorsId());
        assertTrue(found.isValid());
    }

    @Test
    void testFindByIdNotFound() {
        GatePass found = gatePasses.findById(999);
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
        gatePasses.save(pass1);
        gatePasses.save(pass2);
        gatePasses.delete(pass1);
        assertEquals(1L, gatePasses.count());
        assertNull(gatePasses.findById(1));
    }

    @Test
    void testDeleteById() {
        gatePasses.save(pass1);
        gatePasses.save(pass2);
        gatePasses.deleteById(1);
        assertEquals(1L, gatePasses.count());
        assertNull(gatePasses.findById(1));
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
        gatePasses.save(pass1);
        pass1.setValid(false);
        gatePasses.save(pass1);

        GatePass found = gatePasses.findById(1);
        assertFalse(found.isValid());
        assertEquals(1L, gatePasses.count());
    }

    @Test
    void testGatePassCreatedAtIsSet() {
        gatePasses.save(pass1);
        GatePass found = gatePasses.findById(1);
        assertNotNull(found.getCreatedAt());
    }

    @Test
    void testGatePassExpirationDate() {
        LocalDateTime expiration = LocalDateTime.now().plusDays(1);
        pass1.setExpirationDate(expiration);
        gatePasses.save(pass1);

        GatePass found = gatePasses.findById(1);
        assertNotNull(found.getExpirationDate());
    }

    @Test
    void testIdAutoIncrement() {
        gatePasses.save(pass1);
        gatePasses.save(pass2);
        gatePasses.deleteById(1);

        GatePass pass3 = new GatePass();
        pass3.setResidentId(3);
        pass3.setVisitorsId(3);
        gatePasses.save(pass3);

        assertEquals(3, pass3.getId());
    }
}