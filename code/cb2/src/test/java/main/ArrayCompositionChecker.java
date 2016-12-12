package main;

import static org.junit.Assert.*;

import org.junit.Test;

import components.types.ArrayType;
import components.types.IntegerType;

public class ArrayCompositionChecker {

    @Test
    public void oneDimensionalArray() {
        ArrayType type = ArrayType.getOrCreateArrayType(IntegerType.INSTANCE, 1);
        assertEquals(IntegerType.INSTANCE, type.baseType);
    }
    
    @Test
    public void twoDimensionalArray() {
        ArrayType type = ArrayType.getOrCreateArrayType(IntegerType.INSTANCE, 2);
        assertEquals(true, type.baseType instanceof ArrayType);
        type = (ArrayType) type.baseType;
        assertEquals(IntegerType.INSTANCE, type.baseType);
    }
    
    @Test
    public void threeDimensionalArray() {
        ArrayType type = ArrayType.getOrCreateArrayType(IntegerType.INSTANCE, 3);
        assertEquals(true, type.baseType instanceof ArrayType);
        type = (ArrayType) type.baseType;
        assertEquals(true, type.baseType instanceof ArrayType);
        type = (ArrayType) type.baseType;
        assertEquals(IntegerType.INSTANCE, type.baseType);
    }

}
