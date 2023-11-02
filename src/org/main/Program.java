package org.main;

import java.util.Scanner;

import org.main.objects.CartesianPlot;
import org.main.objects.Line;
import org.main.objects.Point;
import org.main.objects.Polygon;
import org.main.objects.Polynomial;


public class Program 
{
	private static final String[] options = {
		Point.class.getSimpleName(),
		Line.class.getSimpleName(),
		Polygon.class.getSimpleName(),
		Polynomial.class.getSimpleName(),
		CartesianPlot.class.getSimpleName(),
		"Quit"
	};
	
	private static final int QUIT = options.length;
	
	public static void main(String[] args) 
	{
		printOpeningText();
		
        Scanner scanner = new Scanner(System.in);
        int 	choice 	= 0;

        while (choice != QUIT)
        {
            printMenu();
            
            choice = scanner.nextInt();

            switch (choice)
            {
                case 1:
                    createPointObject(scanner);
                    System.out.println("serializing object...");
                    break;
                case 2:
                    createLineObject(scanner);
                    System.out.println("serializing object...");
                    break;
                case 3:
                    System.out.println("You selected Option 3.");
                    System.out.println("serializing object...");
                    break;
                case 4:
                	System.out.println("You selected Option 4.");
                	System.out.println("serializing object...");
                    break;
                case 5:
                	System.out.println("You selected Option 5.");
                	System.out.println("serializing object...");
                	break;
                case 6:
                	System.out.println("Exiting the program. Goodbye!");
                	break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
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
		for (int i = 0; i < options.length; i++) {
			System.out.println(String.format("%d. %s", i + 1, options[i]));
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
}
