package org.jfree.data.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.jfree.data.Range;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

//import org.junit.jupiter.api.Timeout;


class RangeTest {

	private Range exampleRange;
	//equals---------------------
	//control range is (-10, 10)
	private static Range equals_control;
	private static Range same_lower_and_upper_bounds;
	private static Range same_lower_but_different_upper_bounds;
	private static String not_range_object;
	private static Range null_example;
	//not an object?
	private static Range bvt_lb_ub;
	private static Range bvt_lb_aub;
	private static Range bvt_blb_ub;
	private static Range bvt_lb_bub;
	private static Range bvt_alb_ub;
	//---------------------------
	
	//contains-------------------
	private static Range containsRange;
	//---------------------------
	
	//getUpperBound--------------
	private static Range getUpperBoundRange;
	//---------------------------
	
	//getCentralValue------------
	private static Range getCentralValueRange;
	//---------------------------
	
	//intersects-----------------
	private static Range intersect_control;
	//---------------------------
	//---------------------------
	//---------------------------

	@BeforeAll 
	public static void setUpBeforeClass () throws Exception {
		
		//equals---------------------
		equals_control = new Range(-10, 10);	//controlled variable
		same_lower_and_upper_bounds = new Range(-10, 10);
		same_lower_but_different_upper_bounds = new Range(-10, 20); //pruned
		not_range_object = "moussavi is the GOAT";  //done
		null_example = null;  //done
		
		bvt_lb_ub = new Range(-10, 10);   //pruned
		bvt_lb_aub = new Range(-10, 10.0001);  //done
		bvt_blb_ub = new Range(-10.0001, 10);  //done
		bvt_lb_bub = new Range(-10, 9.9999);	   //done
		bvt_alb_ub = new Range(-9.9999, 10);   //done
		//---------------------------
		
		//contains-------------------
		containsRange = new Range(-5, 10);
		//---------------------------
		
		//intersects-----------------
		intersect_control = new Range(1, 5);
		//---------------------------
		
	}
	
	@BeforeEach
	public void setUp() throws Exception {}

	
	@Nested
	@DisplayName("Tests for method equals()")
	class testEqualsMethod {
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Same lower and upper bounds. Doubles as the BVT for when testing (lb, ub)")
		//can double as our BVT for when lb, ub
		void When_SameLowerAndUpperBounds_Expect_Pass() {
			assertTrue(same_lower_and_upper_bounds.equals(equals_control), "(-10, 10) does equal the control range (-10, 10), should return true");
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Same lower but different upper bounds. Doubles as the BVT for when testing (lb, aub)")
		//can double as our same_lower_but_different_upper_bounds
		void When_SameLowerButDifferentUpperBound_Expect_Fail() {
			assertFalse(bvt_lb_aub.equals(equals_control), "(-10, 10.0001) does not equal the control range (-10, 10), should return false");
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Not same type")
		//can double as our same_lower_but_different_upper_bounds
		void When_NotSameType_Expect_False() {
			assertThrows(Exception.class, ()-> {equals_control.equals(not_range_object);}, "the string '\'moussavi is the GOAT'\' is not of type Range, should throw exception");
			//assertFalse(equals_control.equals(not_range_object), "(-10, 10.0001) does not equal the control range (-10, 10), should return false");
		}
		
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Null range object")
		void When_NullRangeType_Expect_NullPtrException() {
			assertThrows(NullPointerException.class, ()-> {equals_control.equals(null_example);}, "trying to pass a null object as argument, should throw null ptr exception"); 
			//assertFalse(equals_control.equals(null_example), "trying to pass a null object as argument, should return false");
		}
		
		// Technique: Boundary Value Testing
		@Test
		@DisplayName("BVT for when testing (blb, ub)")
		void When_BLB_UB_Expect_False() {
			assertFalse(bvt_blb_ub.equals(equals_control), "(-10.0001, 10) does not equal the control range (-10, 10), should return false");
		}
		
		// Technique: Boundary Value Testing
		@Test
		@DisplayName("BVT for when testing (lb, bub)")
		void When_LB_BUB_Expect_False() {
			assertFalse(bvt_lb_bub.equals(equals_control), "(-10, 9.9999) does not equal the control range (-10, 10), should return false");
		}
		
		// Technique: Boundary Value Testing
		@Test
		@DisplayName("BVT for when testing (lb, bub)")
		void When_ALB_UB_Expect_False() {
			assertFalse(bvt_alb_ub.equals(equals_control), "(-9.9999, 10) does not equal the control range (-10, 10), should return false");
		}
		
	}
	
	@Nested
	@DisplayName("Tests for method contains()")
	class testContainsMethod {
		// Technique: Equivalence Class Partitioning
		/* 	Case 1:	the parameter "value" with data type double is
		 * 			within the bounds of the range object. 
		 * 	Expected outcome = true
		 */
		@Test
		@DisplayName("when value is within range, expects true")
		void When_valueIsWithinRange_Expect_True() {
			assertTrue(containsRange.contains(0), "0 is between -5 and 10, should return true");
		}
		
		// Technique: Equivalence Class Partitioning
		/*	Case 2: the parameter "value" with data type double is not
		 * 			within the bounds of the range object
		 * 	Expected outcome = false
		 */
		@Test
		@DisplayName("when value is not within range, expects false")
		void When_valueIsNotWithinRange_Expect_False() {
			assertFalse(containsRange.contains(-10), "-10 is not between -5 and 10, should return false");
		}
		
//		/*	Case 3: the parameter "value" = null
//		 */
//		@Test
//		@DisplayName("when value equals to null, expects false")
//		void When_valueEqualsNull_Expect_False() {
//			exampleRange = new Range(-5, 10);
//			assertFalse(exampleRange.contains(null), "The value is not between the upper and lower bounds.");
//		}
		
		// Technique: Boundary Value Testing
		/*	Case 3: (LB) the parameter "value" with data type double 
		 * 			equals to the lower boundary
		 * 	Expected outcome = True
		 */
		@Test
		@DisplayName("when value equals to the lower boundary, expects true")
		void When_valueEqualsLowerBoundary_Expect_True() {
			assertTrue(containsRange.contains(-5), "-5 equals to the lower bound -5, should return true");
		}
		
		// Technique: Boundary Value Testing
		/*	Case 4: (UB) the parameter "value" with data type double
		 * 			equals to the upper boundary
		 * 	Expected outcome = True
		 */
		@Test
		@DisplayName("when value equals to the upper boundary, expects true")
		void When_valueEqualsUpperBoundary_Expect_True() {
			assertTrue(containsRange.contains(10), "10 equals to the upper bound 10, should return true");
		}
		
		// Technique: Boundary Value Testing
		/*	Case 5: (ALB) the parameter "value" with data type double
		 * 			just above the lower boundary
		 * 	Expected outcome = True
		 */
		@Test
		@DisplayName("when value is just above the lower boundary, expects true")
		void When_valueJustAboveLowerBoundary_Expect_True() {
			assertTrue(containsRange.contains(-4.999999999), "-4.999999999 is between -5 and 10, should return true");
		}
		
		// Technique: Boundary Value Testing
		/*	Case 6: (BUB) the parameter "value" with data type double
		 * 			just below the upper boundary
		 * 	Expected outcome = True
		 */
		@Test
		@DisplayName("when value is just below the upper boundary, expects true")
		void When_valueJustBelowUpperBoundary_Expect_True() {
			assertTrue(containsRange.contains(9.999999999), "9.999999999 is between -5 and 10, should return true");
		}
		
		// Technique: Robustness Testing
		/*	Case 7: (BLB)the parameter "value" with data type double
		 * 			just below the lower boundary
		 * 	Expected outcome = false
		 */
		@Test
		@DisplayName("when value is just below the lower boundary, expects false")
		void When_valueJustBelowLowerBoundary_Expect_False() {
			assertFalse(containsRange.contains(-5.000000001), "-5.000000001 is not between -5 and 10, should return false");
		}
		
		// Technique: Robustness Testing
		/*	Case 8: (BLB)the parameter "value" with data type double
		 * 			just above the upper boundary
		 * 	Expected outcome = false
		 */
		@Test
		@DisplayName("when value is just above the upper boundary, expects false")
		void When_valueJustAboveUpperBoundary_Expect_False() {
			assertFalse(containsRange.contains(10.000000001), "10.000000001 is not between -5 and 10, should return false");
		}
		
	}
	
	@Nested
	@DisplayName("Tests for method getUpperBound()")
	class testGetUpperBoundMethod {
		// Technique: Equivalence Class Partitioning
		/* 	Case 1:	Range object is null
		 * 	Expected outcome = NullPointerException
		 */
		@Test
		@DisplayName("when range object is null, expects null")
		void When_rangeObjectIsNull_Expect_null() {
			Range nullRange = null;
			assertThrows(NullPointerException.class, () -> {
				nullRange.getUpperBound();
			});
		}
		
		// Technique: Equivalence Class Partitioning
		/* 	Case 2:	Range object's upper boundary is bigger 
		 * 			than the lower boundary.
		 * 	Expected outcome = 	returns the correct upper 
		 * 						boundary value
		 */
		@Test
		@DisplayName("when upper bound is bigger than lower bound, expects upper bound value")
		void When_upperBoundIsBiggerThanLowerBound_Expect_True() {
			getUpperBoundRange = new Range(1,5);
			assertEquals(5.0, getUpperBoundRange.getUpperBound(), .000000001d, "5 is the upper bound, should return 5");
		}
		
		// Technique: Equivalence Class Partitioning
		/* 	Case 3:	Range object's upper boundary equals to 
		 * 			lower boundary.
		 * 	Expected outcome = 	returns the correct upper
		 * 						boundary value
		 */
		@Test
		@DisplayName("when upper bound equals lower bound, expects upper bound value")
		void When_upperBoundEqualsLowerBound_Expect_True() {
			getUpperBoundRange = new Range(1,1);
			assertEquals(1.0, getUpperBoundRange.getUpperBound(), .000000001d, "upper bound and lower bound both equals to 1, should return 1");
		}
	}

	@Nested
	@DisplayName("Test for method getCentralValue()")
	class testGetCentralValueMethod {
		// Technique: Equivalence Class Partitioning
		/* 	Case 1:	When the upper boundary is bigger than the 
		 * 			lower boundary.
		 * 	Example: (lower boundary, upper boundary) = (1,3)
		 * 	Expected outcome = 	2.0, the median or the central
		 * 						value for the range object.				
		 */
		@Test
		@DisplayName("when upper bound is bigger than lower bound , expects median")
		void When_upperBoundBiggerThanLowerBound_Expect_valueOfTwo() {
			getCentralValueRange = new Range(1,3);
			assertEquals(2.0, getCentralValueRange.getCentralValue(), .000000001d, "the central value of 1 and 3 should be 2.0");
		}
			
		// Technique: Equivalence Class Partitioning
		/* 	Case 2:	When the upper boundary equals to the 
		 * 			lower boundary.
		 * 	Example: (lower boundary, upper boundary) = (1,1)
		 * 	Expected outcome = 	1.0
		 */
		@Test
		@DisplayName("when upper bound equals to lower bound, expects median")
		void When_upperBoundEqualsLowerBound_Expect_valueOfOne() {
			getCentralValueRange = new Range(1,1);
			assertEquals(1.0, getCentralValueRange.getCentralValue(), .000000001d, "the central value of 1 and 1 should be 1.0");
		}
			
		// Technique: Equivalence Class Partitioning
		/* 	Case 3:	When the upper boundary is bigger than lower
		 * 			lower boundary, and the lower boundary is a
		 * 			negative number.
		 * 	Example: (lower boundary, upper boundary) = (-1,1)
		 * 	Expected outcome = 	0.0, the median value for the range.
		 */
		@Test
		@DisplayName("when upper bound equals to lower bound, expects null")
		void When_upperBoundBiggerThanLowerBound_withNegative_Expect_valueOfZero() {
			getCentralValueRange = new Range(-1,1);
			assertEquals(0.0, getCentralValueRange.getCentralValue(), .000000001d, "the central value of -1 and 1 should be 0.0");
		}
			
		// Technique: Equivalence Class Partitioning
		/* 	Case 4:	When both the upper and lower boundaries are 0.
		 * 	Expected outcome = 	either the lower or the upper
		 * 						boundary value
		 */
		@Test
		@DisplayName("when range object is null, expects null")
		void When_bothUpperAndLowerEqualsZero_Expect_valueOfZero() {
			getCentralValueRange = new Range(0,0);
			assertEquals(0.0, getCentralValueRange.getCentralValue(), .000000001d, "the central value of 0 and 0 should be 0.0");
		}
		
		// Technique: Equivalence Class Partitioning
		/* 	Case 5:	When the range object is null
		 * 	Expected outcome = 	exception
		 */
		@Test
		@DisplayName("when range object is null, expects some kind of exception")
		void When_rangeObjectIsNull_Expect_null() {
			getCentralValueRange = null;
			assertThrows(NullPointerException.class, () -> {
				getCentralValueRange.getCentralValue();
			});
		}
		
	}
	
	@Nested
	@DisplayName("Tests for intersects() method")
	class testIntersectsMethod{
		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("When lower bound is within range but upper bound is null")
		void When_LowerIsWithinRange_UpperIsNull() {

			assertThrows(NullPointerException.class, () -> {
				intersect_control.intersects(2,(Double) null);
			},"should throw null pointer exception due to one of the arguments being null");
		}

		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("when the input range is within the Range, expect true")
		void When_RangeisContained_Expect_True() {
			assertTrue(intersect_control.intersects(1,3), "the range (1,3) is inside (1,5) therefore they intersect, should return true");
		}

		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Lower bound > other’s lower bound still intersects, expect true")
		void When_LowerBoundIsAboveOtherLower_StillIntersects() {
			assertTrue(intersect_control.intersects(2,5), "the range (2,5) is inside (1,5) therefore they intersect, should return true");
		}

		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Upper bound < other’s upper bound, still intersects, expect true")
		void When_UpperBoundIsBelowOtherUpper_StillIntersects() {
			assertTrue(intersect_control.intersects(0,4), "the range (0,4) intersects (1,5), should return true");
		}

		// Technique: Equivalence Class Partitioning
		@Test
		@DisplayName("Lower bound is greater than the other’s upper bound, expect false")
		void When_LowerBoundIsAboveOtherUpper_DoesntIntersects() {
			assertFalse(intersect_control.intersects(6,9), "the range (6,9) does not intersect (1,5), should return true");
		}

		// Technique: Boundary Value Testing
		@Test
		@DisplayName("Upper bound is less than the other’s lower bound, expect false")
		void When_UpperBoundIsBelowOtherLower_DoesntIntersects() {
			assertFalse(intersect_control.intersects(-5,0), "the range (-5,0) does not intersect (1,5), should return false");
		}

		// Technique: Boundary Value Testing
		@Test
		@DisplayName("equivalent ranges, expect true")
		void When_RangesAreEquivalent_DoesIntersects() {
			assertTrue(intersect_control.intersects(1,5), "the range (1,5) is the same as (1,5) therefore they intersect, should return true");
		}

		// Technique: Boundary Value Testing
		@Test
		@DisplayName("Input range is a single point within Range, expect true")
		void When_RangeIsSinglePointWithinRange_DoesIntersects() {
			assertTrue(intersect_control.intersects(2,2), "the range (2,2) does intersect (1,5), should return true");
		}

		// Technique: Boundary Value Testing
		@Test
		@DisplayName("Input range is a single point outside Range, expect false")
		void When_RangeIsSinglePointOutsideRange_DoesntIntersects() {
			assertFalse(intersect_control.intersects(0,0), "the range (0,0) does not intersect (1,5), should return false");
		}

		// Technique: Robustness 
		@Test
		@DisplayName("Input Lower is higher than input upper, expect false")
		void When_InputLowerIsGreaterThanInputUpper_DoesntIntersects() {
			assertFalse(intersect_control.intersects(5,1), "the range (5,1) doesnt make sense, should return false");
		}

	}
	
	@AfterEach 
	public void tearDown() throws Exception {}
	
	@AfterAll
	public static void tearDownAfterClass() throws Exception {}
}
