package org.jfree.data.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.DataUtilities;
import org.jfree.data.KeyedValues;
import org.jfree.data.Range;
import org.jfree.data.Values2D;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DataUtilitiesTest {
	
	@BeforeAll 
	public static void setUpBeforeClass () throws Exception {}
	
	@BeforeEach
	public void setUp() throws Exception {}
	
	
	@Nested
	@DisplayName("Tests for method getCumulativePercentages")
	class testCumulativePercentages {
		@BeforeEach
		void beforeEach() {}
		
		@AfterEach
		void afterEach() {}
		
		// Technique: Equivalence Class Partitioning
		/* 	Case 1:	When KeyedValues object is not null, and 
		 * 			all values in the object is not null either.
		 * 	Example: [(0,1), (1,2), (2,2)]
		 * 	Expected outcome = 	[(0, 0.2), (1, 0.6), (2, 1.0)]			
		 */
		@Test
		//@Disabled
		@DisplayName("when KeyValues object is not null, expects correct cumulative percentage")
		void When_keyValuesIsNotNull_Expect_correctCumulativePercentage() {
			
			// set up getKeys method in KeyedValues class
			List<Integer> keys = new ArrayList<>();
			keys.add(0);
			keys.add(1);
			keys.add(2);
			
			KeyedValues input = mock(KeyedValues.class);
			when(input.getItemCount()).thenReturn(3);
			when(input.getKeys()).thenReturn(keys);
			when(input.getKey(0)).thenReturn(0);
			when(input.getKey(1)).thenReturn(1);
			when(input.getKey(2)).thenReturn(2);
			
			when(input.getIndex(0)).thenReturn(0);
			when(input.getIndex(1)).thenReturn(1);
			when(input.getIndex(2)).thenReturn(2);
			when(input.getValue(0)).thenReturn(1);
			when(input.getValue(1)).thenReturn(2);
			when(input.getValue(2)).thenReturn(2);
			
			KeyedValues keyedValuesActual = DataUtilities.getCumulativePercentages(input);
			
			assertEquals(0.2, keyedValuesActual.getValue(0), "1 divided by 6 should be 0.2");
			assertEquals(0.6, keyedValuesActual.getValue(1), "1 divided by 6 should be 0.2");
			assertEquals(1.0, keyedValuesActual.getValue(2), "1 divided by 6 should be 0.2");
		}
		
		// Technique: Equivalence Class Partitioning
		/* 	Case 2:	When KeyedValues object is not null, and 
		 * 			all values in the object is not null either.
		 * 			But all values in key value pairs equal to 
		 * 			zero.
		 * 	Example: [(0,0), (1,0), (2,0)]
		 * 	Expected outcome = expects not a number	
		 */
		@Test
		@DisplayName("when all the values equals to zero, expects not a number")
		void When_allValuesEqualsZero_Expect_notANumber() {
			List<Integer> keys = new ArrayList<>();
			keys.add(0);
			keys.add(1);
			keys.add(2);
			
			KeyedValues input = mock(KeyedValues.class);
			when(input.getItemCount()).thenReturn(3);
			when(input.getKeys()).thenReturn(keys);
			when(input.getKey(0)).thenReturn(0);
			when(input.getKey(1)).thenReturn(1);
			when(input.getKey(2)).thenReturn(2);
			
			when(input.getIndex(0)).thenReturn(0);
			when(input.getIndex(1)).thenReturn(1);
			when(input.getIndex(2)).thenReturn(2);
			when(input.getValue(0)).thenReturn(0);
			when(input.getValue(1)).thenReturn(0);
			when(input.getValue(2)).thenReturn(0);
			
			KeyedValues keyedValuesActual = DataUtilities.getCumulativePercentages(input);
			
			double expectedIndex0 = keyedValuesActual.getValue(0).doubleValue();
			assertTrue(Double.isNaN(expectedIndex0));
			double expectedIndex1 = keyedValuesActual.getValue(1).doubleValue();
			assertTrue(Double.isNaN(expectedIndex1));
			double expectedIndex2 = keyedValuesActual.getValue(2).doubleValue();
			assertTrue(Double.isNaN(expectedIndex2));
		}
		
		// Technique: Equivalence Class Partitioning
		/* 	Case 3:	When KeyedValues object equals to null.
		 * 	Expected outcome = IllegalArgumentException	
		 */
		@Test
		@DisplayName("when KeyValues object is null, expects IllegalArgumentException")
		void When_keyValuesIsNull_Expect_IllegalArgumentException() {
			KeyedValues input = null;
			assertThrows(IllegalArgumentException.class, () -> {
				DataUtilities.getCumulativePercentages(input);
			});
		}
		
		// Technique: Robustness
		/* 	Case 4:	When KeyedValues object is not null, but
		 * 			there exists a null value in one of the
		 * 			pairs.
		 * 	Example: [(0,1), (1,1), (2,null)]
		 * 	Expected outcome = 	NullPointerException				
		 */
		@Test
		//@Disabled // Possible Defect!
		@DisplayName("when KeyValues object is not null, expect NullPointerException")
		void When_thereExistsANullValue_Expect_NullPointerException() {
			List<Integer> keys = new ArrayList<>();
			keys.add(0);
			keys.add(1);
			keys.add(2);
			
			KeyedValues input = mock(KeyedValues.class);
			when(input.getItemCount()).thenReturn(3);
			when(input.getKeys()).thenReturn(keys);
			when(input.getKey(0)).thenReturn(0);
			when(input.getKey(1)).thenReturn(1);
			when(input.getKey(2)).thenReturn(2);
			
			when(input.getIndex(0)).thenReturn(0);
			when(input.getIndex(1)).thenReturn(1);
			when(input.getIndex(2)).thenReturn(2);
			when(input.getValue(0)).thenReturn(1);
			when(input.getValue(1)).thenReturn(3);
			when(input.getValue(2)).thenReturn(null);
			
			KeyedValues keyedValuesActual = DataUtilities.getCumulativePercentages(input);

			assertThrows(NullPointerException.class, () -> {
				keyedValuesActual.getValue(0);
				keyedValuesActual.getValue(1);
				keyedValuesActual.getValue(2);
			});
		}
 		
	}

	@Nested
	@DisplayName("Tests for method createNumberArray2D")
	class testCreateNumber2DArray  {
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Double 2D array is filled")
		void TestDouble2DArrayIsFilled() 
		{
			double[][] data1  = { {1.1, 2.2}, {3.3, 4.4} };
			int expected = 1; //This means true
			int actual;
			Number [][] test1 = DataUtilities.createNumberArray2D(data1);
			if ((test1[0][0] == (Number)1.1) && (test1[0][1] == (Number)2.2) &&
				(test1[1][0] == (Number)3.3) && (test1[1][1] == (Number)4.4))
				actual = 1;
			else 
				actual = 0;
			assertEquals(expected, actual);
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Double 2D array is not filled but not null")
		void Double2DArrayIsNotFilledButNotNull() 
		{
			double [][] data2 = new double[1][1];
			int expected = 1; //This means true
			int actual;
			Number [][] test2 = DataUtilities.createNumberArray2D(data2);
			if (test2 != null)
				actual = 1;
			else 
				actual = 0;
			assertEquals(expected, actual);
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Double 2D array is null")
		void Double2DArrayIsNull() 
		{
			double [][] data3 = null;
			int expected = 1; //This means true
			assertThrows(IllegalArgumentException.class, () -> 
			{
				Number [][] test3 = DataUtilities.createNumberArray2D(data3);
			});
		}
	}
	
	@Nested
	@DisplayName("Tests for method calculateColumnTotal")
	class testCalculateColumnTotal {
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Data column has 1 value with valid column int")
		void DataColumnHas1ValueWithValidColumnInt()
		{
			Values2D values2D = mock(Values2D.class);
			when(values2D.getRowCount()).thenReturn(1);
			when(values2D.getValue(0, 0)).thenReturn(1);
			double expected = 1.0;
			assertEquals(expected, DataUtilities.calculateColumnTotal(values2D, 0));
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Data column has 1 value with invalid column int")
		void DataColumnHas1ValueWithInvalidColumnInt()
		{
			Values2D values2D = mock(Values2D.class);
			when(values2D.getRowCount()).thenReturn(1);
			when(values2D.getValue(0, 0)).thenReturn(1);
			double expected = 0;
			assertEquals(expected, DataUtilities.calculateColumnTotal(values2D, 2));
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Data column has multiple value with valid column int")
		void DataColumnHasMultipleValueWithValidColumnInt()
		{
			Values2D values2D = mock(Values2D.class);
			when(values2D.getRowCount()).thenReturn(3);
			when(values2D.getValue(0, 0)).thenReturn(1);
			when(values2D.getValue(1, 0)).thenReturn(2);
			when(values2D.getValue(2, 0)).thenReturn(3);
			double expected = 6;
			assertEquals(expected, DataUtilities.calculateColumnTotal(values2D, 0));
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("The value2D is null and thus the data = null with and without valid column int")
		void TheValue2DIsNullAndThusTheDataIsNullWithAndWithoutValidColumnInt()
		{
			Values2D values2D = null;
//			when(values2D.getRowCount()).thenReturn(3);
			assertThrows(NullPointerException.class, () -> 
			{
				System.out.println(DataUtilities.calculateColumnTotal(values2D, 2));
			});
		}
	}
	
	@Nested
	@DisplayName("Tests for method calculateRowTotal")
	class testCalculateRowTotal {
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Data row has 1 value with valid row int")
		void DataRowHas1ValueWithValidRowInt()
		{
			Values2D values2D = mock(Values2D.class);
			when(values2D.getColumnCount()).thenReturn(1);
			when(values2D.getRowCount()).thenReturn(1);
			when(values2D.getValue(0, 0)).thenReturn(1);
			double expected = 1.0;
			System.out.println(values2D.getValue(0, 0));
			assertEquals(expected, DataUtilities.calculateRowTotal(values2D, 0));
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Data row has 1 value with invalid row int")
		void DataRowHas1ValueWithInvalidRowInt()
		{
			Values2D values2D = mock(Values2D.class);
			when(values2D.getColumnCount()).thenReturn(1);
			when(values2D.getValue(0, 0)).thenReturn(1);
			double expected = 0;
			assertEquals(expected, DataUtilities.calculateRowTotal(values2D, 2));
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Data row has multiple value with valid row int")
		void DataRowHasMultipleValueWithValidRowInt()
		{
			Values2D values2D = mock(Values2D.class);
			
			when(values2D.getColumnCount()).thenReturn(3);
			when(values2D.getValue(0, 0)).thenReturn(1);
			when(values2D.getValue(0, 1)).thenReturn(2);
			when(values2D.getValue(0, 2)).thenReturn(3);
			System.out.println(values2D.getValue(0, 0));
			System.out.println(values2D.getValue(0, 1));
			System.out.println(values2D.getValue(0, 2));
			double expected = 6;
			assertEquals(expected, DataUtilities.calculateRowTotal(values2D, 0));
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Data is null")
		void case4()
		{
			Values2D values2D = null;
//			when(values2D.getRowCount()).thenReturn(3);
			assertThrows(NullPointerException.class, () -> 
			{
				System.out.println(DataUtilities.calculateRowTotal(values2D, 2));
			});
		}
	}
	
	@Nested
	@DisplayName("Tests for method createNumberArray")
	class testcreateNumberArray {
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Double Array that is filled")
		void DoubleArrayThatIsFilled() 
		{
			double[] data1  =  {1.1, 2.2};
			int expected = 1;
			int actual;
			Number [] test1 = DataUtilities.createNumberArray(data1);
			if ((test1[0] == (Number)1.1) && (test1[1] == (Number)2.2))
				actual = 1;
			else 
				actual = 0;
			assertEquals(expected, actual);
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Double Array that is empty but not null")
		void DoubleArrayThatIsEmptyButNotNull() 
		{
			double [] data2 = {};
			Number [] num1 = {};
			Number [] test2 = DataUtilities.createNumberArray(data2);
			assertArrayEquals(num1, test2);
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		//@Disabled
		@DisplayName("Double Array that is null")
		void DoubleArrayThatIsNull() 
		{
			double [] data3 = null;
			assertThrows(IllegalArgumentException.class, () -> 
			{
				Number [] test3 = DataUtilities.createNumberArray(data3);
			});
		}
	}
}
