package org.main;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.main.objects.CartesianPlot;
import org.main.objects.Line;
import org.main.objects.Point;
import org.main.objects.Polygon;
import org.main.objects.Polynomial;


public class Program 
{
	private static final String[] MAIN_MENU_OPTIONS = {
			Point.class.getSimpleName(),
			Line.class.getSimpleName(),
			Polygon.class.getSimpleName(),
			Polynomial.class.getSimpleName(),
			CartesianPlot.class.getSimpleName(),
			"Quit"
	};
	
	private static final String[] CART_PLOT_OBJECT_OPTIONS = {
			Point.class.getSimpleName(),
			Line.class.getSimpleName(),
			Polygon.class.getSimpleName(),
			Polynomial.class.getSimpleName(),
			"Done"
	};
	
	private static final int QUIT = MAIN_MENU_OPTIONS.length;
	private static final int DONE = CART_PLOT_OBJECT_OPTIONS.length;
	
	public static void main(String[] args) 
	{
		printOpeningText();
		
		Document	document	= null;
        Scanner		scanner 	= new Scanner(System.in);
        Serializer	serializer	= new Serializer();
        int 		choice 		= 0;

        while (choice != QUIT)
        {
            printMenu();
            
            choice = scanner.nextInt();

            switch (choice)
            {
                case 1:
                    document = serializer.serialize(createPointObject(scanner));
                    break;
                case 2:
                	document = serializer.serialize(createLineObject(scanner));
                    break;
                case 3:
                	document = serializer.serialize(createPolygonObject(scanner));
                    break;
                case 4:
                	document = serializer.serialize(createPolynomialObject(scanner));
                    break;
                case 5:
                	document = serializer.serialize(createCartesianPlotObject(scanner));
                	break;
                case 6:
                	System.out.println("Exiting the program. Goodbye!");
                	break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            
            if (document != null)
            {
            	System.out.println("Creating and serializing objects...\r\nDone.");
            	int sendOrSave = -1;
            	while (sendOrSave < 0) 
            	{
            		printSendOrSaveOptions();
                	sendOrSave = scanner.nextInt();
                	if (sendOrSave == 1)
                	{
                		// Send object to deserializer
                		sendDocument(document, scanner);
                	}
                	else if (sendOrSave == 2)
                	{
                		saveDocument(document, scanner);
                	}
                	else
                	{
                        System.out.println("Invalid choice. Please try again.");
                        sendOrSave = -1;
                	}
            	}
            }
            
            System.out.println();
        }

        scanner.close();
    }

	private static void sendDocument(Document doc, Scanner scanner)
	{
		System.out.print(" >> Deserializer IP Address: ");
		String	ipAddr	= scanner.next();
		
		System.out.print(" >> Port number: ");
		int 	portNum	= scanner.nextInt();
		
		try
		{
            System.out.println("Connecting to " + ipAddr + " on port " + portNum + "...");
            Socket client = new Socket(ipAddr, portNum);
            System.out.println("Connected.");
            
            System.out.println("Sending document...");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectOutputStream.writeObject(doc);
            System.out.println("Sent."); 
            
            DataInputStream	in = new DataInputStream(client.getInputStream());
            System.out.println("Deserializer says '" + in.readUTF() + "'");
            client.close();
        }
		catch (IOException e)
		{
            e.printStackTrace();
        }
	}
	
	private static void saveDocument(Document doc, Scanner scanner)
	{
		XMLOutputter	outputter	= new XMLOutputter();
		outputter.setFormat(Format.getPrettyFormat());
        
        System.out.print(" >> Enter complete file name: ");
		String filename	= scanner.nextLine();
		
		try
		{
			System.out.println("Saving file...");
            FileWriter writer = new FileWriter(new File(filename));
            outputter.output(doc, writer);
            writer.flush();
            writer.close();
            System.out.println("File saved.");
        }
		catch (IOException e)
		{
            e.printStackTrace();
        }
	}
	
	private static void printOpeningText()
	{
		String openingText = "Welcome to the Geometric Object Serialization Program!\n\n"
                + "This program allows you to create various geometric objects and serialize them for later use.\r\n"

                + "Please follow the instructions to select and create your desired geometric objects. You can choose\r\n"
                + "from options such as point, squares, triangles, and more. Once you have created your objects, you\r\n"
                + "can serialize them and send or store their data.\r\n"
                + "Let's get started!\r\n";

        System.out.println(openingText);
	}
	
	private static void printMenu()
	{
		System.out.println("Select an object to create and serialise.");
		for (int i = 0; i < MAIN_MENU_OPTIONS.length; i++) {
			System.out.println(String.format("%d. %s", i + 1, MAIN_MENU_OPTIONS[i]));
		}
		System.out.print("Selection: ");
	}
	
	private static Point createPointObject(Scanner scanner)
	{
		System.out.println("Creating a point object...");
		System.out.print(">> Enter the x coordinate: ");
        float x = scanner.nextFloat();

        System.out.print(">> Enter the y coordinate: ");
        float y = scanner.nextFloat();

        return new Point(x,y);
	}
	
	private static Line createLineObject(Scanner scanner)
	{
		System.out.println("Creating a point object...");
		Point point1 = createPointObject(scanner);
		Point point2 = createPointObject(scanner);
		return new Line(point1, point2);
	}
	
	private static Polygon createPolygonObject(Scanner scanner)
	{
		System.out.println("Creating a polygon object...");
		boolean	done 			= false;
		int 	numberOfSides 	= 0;
		while (!done)
		{
			System.out.print(">> Enter the number of sides: ");
	        numberOfSides = scanner.nextInt();
	        if (numberOfSides > 2)
	        {
	        	done = true;
	        }
	        else
	        {
	        	System.out.println("Invalid. Number of sides must be at least 3.");
	        }
		}
        System.out.println("\r\nTo create a " + numberOfSides + "-sided polygon,\r\nspecify the location of each vertex.\r\n");
        Point[] vertices = new Point[numberOfSides];
        for (int i = 0; i < numberOfSides; i++) {
        	vertices[i] = createPointObject(scanner);
        }
        
        return new Polygon(numberOfSides, vertices);
	}
	
	private static Polynomial createPolynomialObject(Scanner scanner)
	{
		System.out.println("Creating a polynomial object...");
		boolean	done	= false;
		int 	degree	= -1;
		while (!done)
		{
			System.out.print(">> Enter the number of sides: ");
	        degree = scanner.nextInt();
	        if (degree >= 0)
	        {
	        	done = true;
	        }
	        else
	        {
	        	System.out.println("Invalid. Degree of polynomial must be an integer value greater than or equal to 0.");
	        }
		}
		
		int numRoots = -1;
		done = false;
		while (!done) {
			System.out.println("Enter the number of roots: ");
			numRoots = scanner.nextInt();
			if (numRoots > degree || (isEven(degree) && numRoots < 0))
			{
				System.out.println("Invalid. Number of roots must be an integer value between 0 and the degree of the polynomial");
			}
			else if (!isEven(degree) && numRoots < 1)
			{
				System.out.println("Invalid. Number of roots must be an integer value between 1 and the degree of the polynomial");
			}
			else 
			{
				done = true;
			}
		}

        float[] roots = new float[numRoots];
        for (int i = 0; i < numRoots; i++) {
        	roots[i] = getPolynomialRoot(scanner);
        }
        
        return new Polynomial(degree, roots);
	}
	
	private static float getPolynomialRoot(Scanner scanner)
	{
		System.out.print(">> Enter the x-coordinate value for the first root: ");
        return scanner.nextFloat();
	}
	
	private static CartesianPlot createCartesianPlotObject(Scanner scanner)
	{
		CartesianPlot plot = new CartesianPlot();
		System.out.print("Would you like to add objects to the cartesian plot? (Y/N)");
		char choice = scanner.next().charAt(0);
		if (choice == 'Y')
		{
			addObjectsToCartesianPlot(scanner, plot);
		}
		return plot;
	}
	
	private static void printCartesianAddObjectMenu()
	{
		System.out.println("Select an object to add to the cartesian plot or 'Done' to finish.");
		for (int i = 0; i < CART_PLOT_OBJECT_OPTIONS.length; i++) {
			System.out.println(String.format("%d. %s", i + 1, CART_PLOT_OBJECT_OPTIONS[i]));
		}
		System.out.print("Selection: ");
	}
	
	private static void addObjectsToCartesianPlot(Scanner scanner, CartesianPlot plot)
	{
        int choice 	= 0;
        while (choice != DONE)
        {
        	printCartesianAddObjectMenu();
            
            choice = scanner.nextInt();

            switch (choice)
            {
                case 1:
                    plot.add(createPointObject(scanner));
                    break;
                case 2:
                    plot.add(createLineObject(scanner));
                    break;
                case 3:
                	plot.add(createPolygonObject(scanner));
                    break;
                case 4:
                	plot.add(createPolynomialObject(scanner));
                    break;             
                case 5:
                	System.out.println("Finished adding objects.");
                	break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
	}
	
	private static boolean isEven(int n)
	{
		return n % 2 == 0;
	}
	
	private static final String[] SEND_OR_SAVE_MENU = new String[] {
		"Send to Deserializer",
		"Save to File"
	};
	
	private static void printSendOrSaveOptions()
	{
		System.out.println("Select an object to create and serialise.");
		for (int i = 0; i < SEND_OR_SAVE_MENU.length; i++) {
			System.out.println(String.format("%d. %s", i + 1, SEND_OR_SAVE_MENU[i]));
		}
		System.out.print("Selection: ");
	}
}
