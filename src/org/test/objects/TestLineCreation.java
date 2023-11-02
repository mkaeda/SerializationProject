package org.test.objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.main.objects.Line;
import org.main.objects.Point;

public class TestLineCreation
{
	@Test
	public void testCreateLine()
	{
		// Point 1
		float x1 = 10f;
		float y1 = 10f;
		// Point 2
		float x2 = -10f;
		float y2 = -10f;
		
		Point 	point1	= new Point(x1, y1);
		Point 	point2 	= new Point(x2, y2);
		Line 	line 	= new Line(point1, point2);
		
		assertEquals(point1, line.getStartPoint());
		assertEquals(point2, line.getEndPoint());
		// No need to confirm x1, y1 and x2, y2 values 
		// as Point creation testing is done separately.
	}
	
	@Test
	public void testChangeStartEnd()
	{
		// Point 1
		float x1 = 10f;
		float y1 = 10f;
		// Point 2
		float x2 = -10f;
		float y2 = -10f;
		// Point 3
		float x3 = 10f;
		float y3 = -10f;
		
		Point 	point1	= new Point(x1, y1);
		Point 	point2 	= new Point(x2, y2);
		Point	point3	= new Point(x3, y3);
		Line 	line 	= new Line(point1, point2);
		
		assertEquals(point1, line.getStartPoint());
		assertEquals(point2, line.getEndPoint());
		// No need to confirm x1, y1 and x2, y2 values 
		// as Point creation testing is done separately.
		
		line.setStartPoint(point3);
		assertEquals(point3, line.getStartPoint());
		assertEquals(point2, line.getEndPoint());
		
		line.setEndPoint(point1);
		assertEquals(point3, line.getStartPoint());
		assertEquals(point1, line.getEndPoint());
	}

}
