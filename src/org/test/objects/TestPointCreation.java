package org.test.objects;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.main.objects.Point;

public class TestPointCreation
{
	@Test
	public void testPointCreation()
	{
		float x = 1f;
		float y = 2f;
		
		Point point = new Point(x, y);
		assertTrue(point.getX() == x);
		assertTrue(point.getY() == y);
	}
	
	@Test
	public void testPointCreationNegativeCoords()
	{
		float x = -1f;
		float y = -2f;
		
		Point point = new Point(x, y);
		assertTrue(point.getX() == x);
		assertTrue(point.getY() == y);
	}
	
	@Test
	public void testPointCreationOrigin()
	{
		float x = 0f;
		float y = 0f;
		
		Point point = new Point(x, y);
		assertTrue(point.getX() == x);
		assertTrue(point.getY() == y);
	}
	
	@Test
	public void testChangeCoords()
	{
		float x1 = 0f;
		float y1 = 0f;
		float x2 = 1f;
		float y2 = -1f;
		
		Point point = new Point(x1, y1);
		assertTrue(point.getX() == x1);
		assertTrue(point.getY() == y1);
		
		point.setX(x2);
		assertTrue(point.getX() == x2);
		assertTrue(point.getY() == y1);
		
		point.setY(y2);
		assertTrue(point.getX() == x2);
		assertTrue(point.getY() == y2);
		
	}

}
