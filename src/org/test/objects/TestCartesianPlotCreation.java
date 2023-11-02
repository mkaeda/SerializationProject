package org.test.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.main.objects.CartesianPlot;
import org.main.objects.Line;
import org.main.objects.Point;
import org.main.objects.Polygon;
import org.main.objects.Polynomial;

public class TestCartesianPlotCreation
{
	float[]		roots 		= new float[] { 1f, -3f };
	Polynomial 	quadratic	= new Polynomial(roots.length, roots);
	
	Point[] 	vertices 	= new Point[] {
			new Point(0f, 0f),
			new Point(0f, 5f),
			new Point(5f, -3f), 
			new Point(0f, -3f)
	};
	Polygon 	rectangle 	= new Polygon(vertices.length, vertices);
	
	// Point 1
	float x1 = 10f;
	float y1 = 10f;
	// Point 2
	float x2 = -10f;
	float y2 = -10f;
	
	Point 	point1	= new Point(x1, y1);
	Point 	point2 	= new Point(x2, y2);
	Line 	line 	= new Line(point1, point2);
	
	@Test
	public void testCreateEmptyCartesianPlot()
	{
		CartesianPlot plot = new CartesianPlot();
		assertTrue(plot.getObjects().isEmpty());
	}

	@Test
	public void testAddGeometricObjects()
	{
		CartesianPlot 	plot 		= new CartesianPlot();
		int				numObjects 	= 0;
		
		plot.add(point1);
		assertTrue(plot.getObjects().size() == ++numObjects);
		assertTrue(plot.getObjects().contains(point1));
		
		plot.add(line);
		assertTrue(plot.getObjects().size() == ++numObjects);
		assertEquals(point1, plot.getObjects().get(0));
		assertEquals(line, plot.getObjects().get(1));
		
		plot.add(rectangle);
		assertTrue(plot.getObjects().size() == ++numObjects);
		assertEquals(point1, plot.getObjects().get(0));
		assertEquals(line, plot.getObjects().get(1));
		assertEquals(rectangle, plot.getObjects().get(2));
		
		plot.add(quadratic);
		assertTrue(plot.getObjects().size() == ++numObjects);
		assertEquals(point1, plot.getObjects().get(0));
		assertEquals(line, plot.getObjects().get(1));
		assertEquals(rectangle, plot.getObjects().get(2));
		assertEquals(quadratic, plot.getObjects().get(3));
	}
}
