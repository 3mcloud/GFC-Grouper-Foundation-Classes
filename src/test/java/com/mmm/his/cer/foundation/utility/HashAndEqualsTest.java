package com.mmm.his.cer.foundation.utility;

import com.mmm.his.cer.foundation.ComponentRuntime;
import org.junit.Test;
import static org.junit.Assert.*;

public class HashAndEqualsTest {
    enum MyTestEnum {One, Two}

    @Test
    public void testEquals_one() {
        ComponentRuntime<MyTestEnum> objA = new ComponentRuntime<MyTestEnum>() {};
        ComponentRuntime<MyTestEnum> objB = new ComponentRuntime<MyTestEnum>() {};
        objA.put(MyTestEnum.One, "one");
        objA.put(MyTestEnum.Two, "two");
        objB.put(MyTestEnum.One, "one");
        objB.put(MyTestEnum.Two, "two");
        assertEquals(objA, objB);
    }

    @Test
    public void testEquals_two() {
        ComponentRuntime<MyTestEnum> objA = new ComponentRuntime<MyTestEnum>() {};
        ComponentRuntime<MyTestEnum> objB = new ComponentRuntime<MyTestEnum>() {};
        objA.put(MyTestEnum.One, "one");
        objA.put(MyTestEnum.Two, "two");
        objB.put(MyTestEnum.One, "one");
        objB.put(MyTestEnum.Two, "three");
        assertNotEquals(objA, objB);
    }

    @Test
    public void testHashCode_one() {
        ComponentRuntime<MyTestEnum> objA = new ComponentRuntime<MyTestEnum>() {};
        ComponentRuntime<MyTestEnum> objB = new ComponentRuntime<MyTestEnum>() {};
        objA.put(MyTestEnum.One, "one");
        objA.put(MyTestEnum.Two, "two");
        objB.put(MyTestEnum.One, "one");
        objB.put(MyTestEnum.Two, "two");
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testHashCode_two() {
        ComponentRuntime<MyTestEnum> objA = new ComponentRuntime<MyTestEnum>() {};
        ComponentRuntime<MyTestEnum> objB = new ComponentRuntime<MyTestEnum>() {};
        objA.put(MyTestEnum.One, "one");
        objA.put(MyTestEnum.Two, "two");
        objB.put(MyTestEnum.One, "one");
        objB.put(MyTestEnum.Two, "three");
        assertNotEquals(objA.hashCode(), objB.hashCode());
    }
}