package org.test.objects;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.main.objects.Polynomial;

public class TestPolynomialCreation
{
	@Test
	public void testCreatePolynomial()
	{
		float[] roots = new float[] { 1f, -3f };
		
		Polynomial p = new Polynomial(roots.length, roots);
		assertTrue(p.getDegree() == roots.length);
		assertTrue(p.getRoots().length == roots.length);
		assertArrayEquals(roots, p.getRoots(), 0.0f);
	}

	@Test
	public void testCreatePolynomialNullRoots()
	{
		final String EXPECTED_MSG = "roots";

        NullPointerException exception = assertThrows(
        		NullPointerException.class, 
        		() -> { new Polynomial(1, null); }
		);

        assertEquals(EXPECTED_MSG, exception.getMessage());
	}
	
	@Test
	public void testNegativeDegree()
	{
		final String EXPECTED_MSG = "degree must be 0 or higher";

		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class, 
        		() -> { new Polynomial(-1, new float[] { }); }
		);

        assertEquals(EXPECTED_MSG, exception.getMessage());
	}
	
	@Test
	public void testEvenDegreeTooManyRoots()
	{
		final String EXPECTED_MSG = "number of roots must must be between 0 and the degree, inclusive";

		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class, 
        		() -> { new Polynomial(2, new float[] { 1f, -3f, 4f }); }
		);

        assertEquals(EXPECTED_MSG, exception.getMessage());
	}
	
	@Test
	public void testOddDegreeTooManyRoots()
	{
		final String EXPECTED_MSG = "number of roots must must be between 1 and the degree, inclusive";

		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class, 
        		() -> { new Polynomial(3, new float[] { 1f, -3f, 4f, 10f }); }
		);

        assertEquals(EXPECTED_MSG, exception.getMessage());
	}
	
	@Test
	public void testOddDegreeNotEnoughRoots()
	{
		final String EXPECTED_MSG = "number of roots must must be between 1 and the degree, inclusive";

		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class, 
        		() -> { new Polynomial(3, new float[] { }); }
		);

        assertEquals(EXPECTED_MSG, exception.getMessage());
	}
}
