package org.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartesianPlot {

	private List<GeometricObject> objects;
	
	public CartesianPlot()
	{
		objects = new ArrayList<>();
	}
	
	public void add(GeometricObject obj)
	{
		objects.add(obj);
	}
	
	public List<GeometricObject> getObjects()
	{
		return Collections.unmodifiableList(objects);
	}
}
