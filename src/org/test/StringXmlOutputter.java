package org.test;

import java.io.IOException;
import java.io.StringReader;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class StringXmlOutputter
{
	public static String formatXmlString(String xmlString)
	{
		StringReader	stringReader	= new StringReader(xmlString);
		SAXBuilder 		saxBuilder 		= new SAXBuilder();
		String			formattedString = "";
		try
		{
			Document		document		= saxBuilder.build(stringReader);
		    XMLOutputter	xmlOutputter	= new XMLOutputter();
			Format 			format 			= Format.getPrettyFormat();
			xmlOutputter.setFormat(format);
			
			formattedString	= xmlOutputter.outputString(document);
		}
		catch (JDOMException | IOException e)
		{
		    e.printStackTrace();
		}
		
		return formattedString;
	}
}
