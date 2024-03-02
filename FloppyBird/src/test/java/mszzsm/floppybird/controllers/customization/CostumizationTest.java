package mszzsm.floppybird.controllers.customization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CostumizationTest {
    private Costumization costumization;
    @BeforeEach
    void setUp() {
        costumization = new Costumization();
    }

    @Test
    void testCheckIndex()
    {
        String[] array1 = {"A", "B", "C"};
        String[] array2 = {"X", "Y", "Z", "W"};

        assertEquals(2, costumization.checkIndex(5, array1));
        assertEquals(0, costumization.checkIndex(3, array1));
        assertEquals(1, costumization.checkIndex(-1, array1));

        assertEquals(2, costumization.checkIndex(6, array2));
        assertEquals(0, costumization.checkIndex(4, array2));
        assertEquals(3, costumization.checkIndex(-1, array2));
    }

}