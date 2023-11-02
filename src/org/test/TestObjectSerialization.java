package org.test;

import static org.junit.Assert.assertEquals;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.main.Serializer;
import org.main.objects.CartesianPlot;
import org.main.objects.Line;
import org.main.objects.Point;
import org.main.objects.Polygon;
import org.main.objects.Polynomial;

public class TestObjectSerialization {
	
	float[]			roots 			= new float[] { 1f, -3f };
	Polynomial 		quadratic		= new Polynomial(roots.length, roots);
	Point[] 		vertices 		= new Point[] {
			new Point(0f, 0f),
			new Point(0f, 5f),
			new Point(5f, -3f), 
			new Point(0f, -3f)
	};
	Polygon 		rectangle 		= new Polygon(vertices.length, vertices);
	Point 			point1			= new Point(10f, 10f);
	Point 			point2 			= new Point(-10f, -10f);
	Line 			line 			= new Line(point1, point2);
	CartesianPlot	emptyPlot		= new CartesianPlot();
	CartesianPlot	plotWithObjects;
	Serializer		serializer		= new Serializer();
	

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
		plotWithObjects = null;
		System.gc();
	}

	@Test
	public void testPointSerialization()
	{	
		final String EXPECTED_OUT = StringXmlOutputter.formatXmlString(
				"<serialized>\r\n"
				+ "<object class=\"Point\" id=\"0\">\r\n"
				+ "<field name=\"x\" declaringclass=\"Point\">\r\n"
				+ "<value>10.0</value>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"y\" declaringclass=\"Point\">\r\n"
				+ "<value>10.0</value>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "</serialized>"
		);
		
		Document		document		= serializer.serialize(point1);
		XMLOutputter	xmlOutputter	= new XMLOutputter();
		Format 			format 			= Format.getPrettyFormat();
		xmlOutputter.setFormat(format);
		
		String xmlString = xmlOutputter.outputString(document);
		assertEquals(EXPECTED_OUT, xmlString);
	}
	
	@Test
	public void testLineSerialization()
	{
		final String EXPECTED_OUT = StringXmlOutputter.formatXmlString(
				"<serialized>\r\n"
				+ "<object class=\"Line\" id=\"0\">\r\n"
				+ "<field name=\"startPoint\" declaringclass=\"Line\">\r\n"
				+ "<reference>1</reference>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"endPoint\" declaringclass=\"Line\">\r\n"
				+ "<reference>2</reference>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"Point\" id=\"1\">\r\n"
				+ "<field name=\"x\" declaringclass=\"Point\">\r\n"
				+ "<value>10.0</value>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"y\" declaringclass=\"Point\">\r\n"
				+ "<value>10.0</value>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"Point\" id=\"2\">\r\n"
				+ "<field name=\"x\" declaringclass=\"Point\">\r\n"
				+ "<value>-10.0</value>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"y\" declaringclass=\"Point\">\r\n"
				+ "<value>-10.0</value>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "</serialized>"		
		);
		
		Document		document		= serializer.serialize(line);
		XMLOutputter	xmlOutputter	= new XMLOutputter();
		Format 			format 			= Format.getPrettyFormat();
		xmlOutputter.setFormat(format);
		
		String xmlString = xmlOutputter.outputString(document);
		assertEquals(EXPECTED_OUT, xmlString);
	}

	@Test
	public void testPolygonSerialization()
	{
		final String EXPECTED_OUT = StringXmlOutputter.formatXmlString(
				"<serialized>\r\n"
				+ "<object class=\"Polygon\" id=\"0\">\r\n"
				+ "<field name=\"numberOfSides\" declaringclass=\"Polygon\">\r\n"
				+ "<value>4</value>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"vertices\" declaringclass=\"Polygon\">\r\n"
				+ "<reference>1</reference>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"Point[]\" id=\"1\" length=\"4\">\r\n"
				+ "<reference>2</reference>\r\n"
				+ "<reference>3</reference>\r\n"
				+ "<reference>4</reference>\r\n"
				+ "<reference>5</reference>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"Point\" id=\"2\">\r\n"
				+ "<field name=\"x\" declaringclass=\"Point\">\r\n"
				+ "<value>0.0</value>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"y\" declaringclass=\"Point\">\r\n"
				+ "<value>0.0</value>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"Point\" id=\"3\">\r\n"
				+ "<field name=\"x\" declaringclass=\"Point\">\r\n"
				+ "<value>0.0</value>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"y\" declaringclass=\"Point\">\r\n"
				+ "<value>5.0</value>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"Point\" id=\"4\">\r\n"
				+ "<field name=\"x\" declaringclass=\"Point\">\r\n"
				+ "<value>5.0</value>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"y\" declaringclass=\"Point\">\r\n"
				+ "<value>-3.0</value>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"Point\" id=\"5\">\r\n"
				+ "<field name=\"x\" declaringclass=\"Point\">\r\n"
				+ "<value>0.0</value>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"y\" declaringclass=\"Point\">\r\n"
				+ "<value>-3.0</value>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "</serialized>"
		);
		
		Document		document		= serializer.serialize(rectangle);
		XMLOutputter	xmlOutputter	= new XMLOutputter();
		Format 			format 			= Format.getPrettyFormat();
		xmlOutputter.setFormat(format);
		
		String xmlString = xmlOutputter.outputString(document);
		assertEquals(EXPECTED_OUT, xmlString);
	}
	
	@Test
	public void testPolynomialSerialization()
	{
		final String EXPECTED_OUT = StringXmlOutputter.formatXmlString(
				"<serialized>\r\n"
				+ "<object class=\"Polynomial\" id=\"0\">\r\n"
				+ "<field name=\"degree\" declaringclass=\"Polynomial\">\r\n"
				+ "<value>2</value>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"roots\" declaringclass=\"Polynomial\">\r\n"
				+ "<reference>1</reference>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"float[]\" id=\"1\" length=\"2\">\r\n"
				+ "<value>1.0</value>\r\n"
				+ "<value>-3.0</value>\r\n"
				+ "</object>\r\n"
				+ "</serialized>"
		);
		
		Document		document		= serializer.serialize(quadratic);
		XMLOutputter	xmlOutputter	= new XMLOutputter();
		Format 			format 			= Format.getPrettyFormat();
		xmlOutputter.setFormat(format);
		
		String xmlString = xmlOutputter.outputString(document);
		assertEquals(EXPECTED_OUT, xmlString);
	}
	
	@Test
	public void testEmptyCartesianPlotSerialization()
	{
		final String EXPECTED_OUT = StringXmlOutputter.formatXmlString(
				"<serialized>\r\n"
				+ "<object class=\"CartesianPlot\" id=\"0\">\r\n"
				+ "<field name=\"objects\" declaringclass=\"CartesianPlot\">\r\n"
				+ "<reference>1</reference>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"ArrayList\" id=\"1\" length=\"0\">\r\n"
				+ "</object>\r\n"
				+ "</serialized>"
		);
		
		Document		document		= serializer.serialize(emptyPlot);
		XMLOutputter	xmlOutputter	= new XMLOutputter();
		Format 			format 			= Format.getPrettyFormat();
		xmlOutputter.setFormat(format);
		
		String xmlString = xmlOutputter.outputString(document);
		assertEquals(EXPECTED_OUT, xmlString);
	}
	
	@Test
	public void testCartesianPlotSerialization()
	{
		final String EXPECTED_OUT = StringXmlOutputter.formatXmlString(
				"<serialized>\r\n"
				+ "<object class=\"CartesianPlot\" id=\"0\">\r\n"
				+ "<field name=\"objects\" declaringclass=\"CartesianPlot\">\r\n"
				+ "<reference>1</reference>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"ArrayList\" id=\"1\" length=\"2\">\r\n"
				+ "<reference>2</reference>\r\n"
				+ "<reference>3</reference>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"Point\" id=\"2\">\r\n"
				+ "<field name=\"x\" declaringclass=\"Point\">\r\n"
				+ "<value>10.0</value>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"y\" declaringclass=\"Point\">\r\n"
				+ "<value>10.0</value>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"Line\" id=\"3\">\r\n"
				+ "<field name=\"startPoint\" declaringclass=\"Line\">\r\n"
				+ "<reference>2</reference>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"endPoint\" declaringclass=\"Line\">\r\n"
				+ "<reference>4</reference>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "<object class=\"Point\" id=\"4\">\r\n"
				+ "<field name=\"x\" declaringclass=\"Point\">\r\n"
				+ "<value>-10.0</value>\r\n"
				+ "</field>\r\n"
				+ "<field name=\"y\" declaringclass=\"Point\">\r\n"
				+ "<value>-10.0</value>\r\n"
				+ "</field>\r\n"
				+ "</object>\r\n"
				+ "</serialized>"
		);
		
		plotWithObjects = new CartesianPlot();
		plotWithObjects.add(point1);
		plotWithObjects.add(line);
		
		Document		document		= serializer.serialize(plotWithObjects);
		XMLOutputter	xmlOutputter	= new XMLOutputter();
		Format 			format 			= Format.getPrettyFormat();
		xmlOutputter.setFormat(format);
		
		String xmlString = xmlOutputter.outputString(document);
		assertEquals(EXPECTED_OUT, xmlString);
	}
	
	@Test
	public void testNullObjectSerialization()
	{
		final String EXPECTED_OUT = StringXmlOutputter.formatXmlString(
				"<serialized>\r\n"
				+ "</serialized>"
		);
		
		Document		document		= serializer.serialize(null);
		XMLOutputter	xmlOutputter	= new XMLOutputter();
		Format 			format 			= Format.getPrettyFormat();
		xmlOutputter.setFormat(format);
		
		String xmlString = xmlOutputter.outputString(document);
		assertEquals(EXPECTED_OUT, xmlString);
	}
}
