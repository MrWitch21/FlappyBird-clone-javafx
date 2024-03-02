package mszzsm.floppybird.controllers.customization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomizationTest {
    private Customization customization;
    @BeforeEach
    void setUp() {
        customization = new Customization();
    }

    @Test
    void testCheckIndex()
    {
        String[] array1 = {"A", "B", "C"};
        String[] array2 = {"X", "Y", "Z", "W"};

        assertEquals(2, customization.checkIndex(5, array1));
        assertEquals(0, customization.checkIndex(3, array1));
        assertEquals(1, customization.checkIndex(-1, array1));

        assertEquals(2, customization.checkIndex(6, array2));
        assertEquals(0, customization.checkIndex(4, array2));
        assertEquals(3, customization.checkIndex(-1, array2));
    }

}