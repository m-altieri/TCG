
package com.skiwi.tcg.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Frank van Heeswijk
 */
public class StatesTest {
    static {
        assertTrue(true);
    }

    @Test
    public void testRequireTrue_boolean() {
        assertTrue(States.requireTrue(true));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testRequireTrue_booleanISE() {
        States.requireTrue(false);
    }

    @Test
    public void testRequireTrue_boolean_String() {
        assertTrue(States.requireTrue(true, "test"));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testRequireTrue_boolean_StringISE() {
        States.requireTrue(false, "test");
    }
    
    @Test(expected = NullPointerException.class)
    public void testRequireTrue_boolean_StringNPE() {
        States.requireTrue(true, null);
    }

    @Test
    public void testRequireFalse_boolean() {
        assertFalse(States.requireFalse(false));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testRequireFalse_booleanISE() {
        States.requireFalse(true);
    }

    @Test
    public void testRequireFalse_boolean_String() {
        assertFalse(States.requireFalse(false, "test"));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testRequireFalse_boolean_StringISE() {
        States.requireFalse(true, "test");
    }
    
    @Test(expected = NullPointerException.class)
    public void testRequireFalse_boolean_StringNPE() {
        States.requireFalse(false, null);
    }

    @Test
    public void testRequireEmpty_GenericType() {
        Collection<Integer> c = Arrays.asList();
        assertEquals(c, States.requireEmpty(c));
    }

    @Test(expected = IllegalStateException.class)
    public void testRequireEmpty_GenericTypeISE() {
        States.requireEmpty(Arrays.asList(2, 5));
    }

    @Test
    public void testRequireEmpty_GenericType_String() {
        Collection<Integer> c = Arrays.asList();
        assertEquals(c, States.requireEmpty(c, "test"));
    }

    @Test(expected = IllegalStateException.class)
    public void testRequireEmpty_GenericType_StringISE() {
        States.requireEmpty(Arrays.asList(2, 5), "test");
    }
    
    @Test(expected = NullPointerException.class)
    public void testRequireEmpty_GenericType_StringNPE() {
        States.requireEmpty(Arrays.asList(), null);
    }

    @Test
    public void testRequireNonEmpty_GenericType() {
        Collection<Integer> c = Arrays.asList(2, 5);
        assertEquals(c, States.requireNonEmpty(c));
    }

    @Test(expected = NoSuchElementException.class)
    public void testRequireNonEmpty_GenericTypeISE() {
        States.requireNonEmpty(Arrays.asList());
    }

    @Test
    public void testRequireNonEmpty_GenericType_String() {
        Collection<Integer> c = Arrays.asList(2, 5);
        assertEquals(c, States.requireNonEmpty(c, "test"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testRequireNonEmpty_GenericType_StringISE() {
        States.requireNonEmpty(Arrays.asList(), "test");
    }
    
    @Test(expected = NullPointerException.class)
    public void testRequireNonEmpty_GenericType_StringNPE() {
        States.requireNonEmpty(Arrays.asList(2, 5), null);
    }
}