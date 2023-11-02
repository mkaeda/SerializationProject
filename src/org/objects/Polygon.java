package org.objects;

public class Polygon extends GeometricObject
{
	private int		numberOfSides;
    private Point[] vertices;

    public Polygon(int numberOfSides, Point[] vertices) 
    {
    	if (vertices == null)
    		throw new NullPointerException("vertices");
    	if (vertices.length != numberOfSides)
    		throw new IllegalArgumentException("number of vertices must equal number of sides");
    	this.numberOfSides	= numberOfSides;
        this.vertices 		= vertices;
    }
    
    public int getNumberOfSides() {
    	return numberOfSides;
    }

    public Point[] getVertices() {
        return vertices;
    }
}

