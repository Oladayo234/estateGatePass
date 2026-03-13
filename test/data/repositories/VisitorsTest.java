package data.repositories;

import data.models.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisitorsTest {
    private Visitors visitors;
    private Visitor visitor1;
    private Visitor visitor2;

    @BeforeEach
    void setUp() {
        visitors = new Visitors();

        visitor1 = new Visitor();
        visitor1.setName("Christian Samuel");
        visitor1.setPurposeOfComing("Business meeting");
        visitor1.setPhoneNumber("08012345678");

        visitor2 = new Visitor();
        visitor2.setName("Shalewa Wastar");
        visitor2.setPurposeOfComing("Social visit");
        visitor2.setPhoneNumber("08087654321");
    }

    @Test
    void testSaveVisitor() {
        Visitor saved = visitors.save(visitor1);
        assertEquals(1L, visitors.count());
        assertTrue(saved.getId().startsWith("VIS-"));
    }

    @Test
    void testSaveMultipleVisitors() {
        Visitor savedOne = visitors.save(visitor1);
        Visitor savedTwo = visitors.save(visitor2);
        assertEquals(2L, visitors.count());
        assertTrue(savedOne.getId().startsWith("VIS-"));
        assertTrue(savedTwo.getId().startsWith("VIS-"));
    }

    @Test
    void testFindById() {
        Visitor saved = visitors.save(visitor1);
        Visitor found = visitors.findById(saved.getId());
        assertNotNull(found);
        assertEquals("Christian Samuel", found.getName());
    }

    @Test
    void testFindByIdNotFound() {
        Visitor found = visitors.findById("999");
        assertNull(found);
    }

    @Test
    void testFindAll() {
        visitors.save(visitor1);
        visitors.save(visitor2);
        assertEquals(2, visitors.findAll().size());
    }

    @Test
    void testFindAllEmpty() {
        assertEquals(0, visitors.findAll().size());
    }

    @Test
    void testDelete() {
        Visitor saved = visitors.save(visitor1);
        visitors.delete(visitor1);
        assertEquals(0L, visitors.count());
        assertNull(visitors.findById(saved.getId()));
    }

    @Test
    void testDeleteById() {
        Visitor saved = visitors.save(visitor1);
        visitors.save(visitor2);
        visitors.deleteById(saved.getId());
        assertEquals(1L, visitors.count());
        assertNull(visitors.findById(saved.getId()));
    }

    @Test
    void testDeleteAll() {
        visitors.save(visitor1);
        visitors.save(visitor2);
        visitors.deleteAll();
        assertEquals(0L, visitors.count());
    }

    @Test
    void testUpdateVisitor() {
        Visitor saved = visitors.save(visitor1);
        visitor1.setName("Christian Updated");
        visitors.save(visitor1);
        Visitor found = visitors.findById(saved.getId());
        assertEquals("Christian Updated", found.getName());
        assertEquals(1L, visitors.count());
    }
}