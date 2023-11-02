package org.test.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.main.objects.Point;
import org.main.objects.Polygon;

public class TestPolygonCreation
{
	@Test
	public void testCreatePolygon()
	{
		Point[] vertices = new Point[] {
				new Point(0f, 0f),
				new Point(0f, 5f),
				new Point(5f, -3f), 
				new Point(0f, -3f)
		};
		
		Polygon p = new Polygon(vertices.length, vertices);
		assertTrue(p.getNumberOfSides() == vertices.length);
		assertTrue(p.getVertices().length == vertices.length);
		
		// Ensure that arrays object are equal and in the same order.
		assertTrue(
			Arrays.stream(vertices)
                .filter(
            		u -> Arrays.stream(p.getVertices()).anyMatch(v ->  u == v)
        		)
                .count() == vertices.length
        );
	}

	@Test
	public void testCreatePolygonNullVertices()
	{
		final String EXPECTED_MSG = "vertices";

        NullPointerException exception = assertThrows(
        		NullPointerException.class, 
        		() -> { new Polygon(4, null); }
		);

        assertEquals(EXPECTED_MSG, exception.getMessage());
	}
	
	@Test
	public void testCreatePolygonIncorrectNumSides()
	{
		final String EXPECTED_MSG = "number of vertices must equal number of sides";
		
		Point[] vertices = new Point[] {
				new Point(0f, 0f),
				new Point(0f, 5f),
				new Point(5f, -3f), 
				new Point(0f, -3f)
		};
		
		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class, 
        		() -> { new Polygon(3, vertices); }
		);

        assertEquals(EXPECTED_MSG, exception.getMessage());
	}
	
	@Test
	public void testCreatePolygonZeroSides()
	{
		Point[] vertices = new Point[] { };
		
		Polygon p = new Polygon(vertices.length, vertices);
		assertTrue(p.getNumberOfSides() == vertices.length);
		assertTrue(p.getVertices().length == vertices.length);
	}
}
