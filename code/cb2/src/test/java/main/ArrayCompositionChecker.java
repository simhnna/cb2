package main;

import static org.junit.Assert.*;

import org.junit.Test;

import components.types.ArrayType;
import components.types.IntegerType;

public class ArrayCompositionChecker {

    @Test
    public void oneDimensionalArray() {
        ArrayType type = ArrayType.getOrCreateArrayType(IntegerType.INSTANCE, 1);
        assertEquals(1, type.getDimensions());
        assertEquals(IntegerType.INSTANCE, type.baseType);
        assertEquals(IntegerType.INSTANCE, type.getBasicDataType());
    }
    
    @Test
    public void twoDimensionalArray() {
        ArrayType type = ArrayType.getOrCreateArrayType(IntegerType.INSTANCE, 2);
        assertEquals(2, type.getDimensions());
        assertEquals(IntegerType.INSTANCE, type.getBasicDataType());
        assertEquals(true, type.baseType instanceof ArrayType);
        type = (ArrayType) type.baseType;
        assertEquals(IntegerType.INSTANCE, type.baseType);
    }
    
    @Test
    public void threeDimensionalArray() {
        ArrayType type = ArrayType.getOrCreateArrayType(IntegerType.INSTANCE, 3);
        assertEquals(3, type.getDimensions());
        assertEquals(IntegerType.INSTANCE, type.getBasicDataType());
        assertEquals(true, type.baseType instanceof ArrayType);
        type = (ArrayType) type.baseType;
        assertEquals(true, type.baseType instanceof ArrayType);
        type = (ArrayType) type.baseType;
        assertEquals(IntegerType.INSTANCE, type.baseType);
    }

}
