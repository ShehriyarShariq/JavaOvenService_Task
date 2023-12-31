package main.java;

import java.util.Scanner;

import main.java.objects.Configuration;
import main.java.objects.Oven;
import main.java.objects.Product;
import main.java.objects.Scheduler;
import main.java.objects.Transporter;

/**
 * @author Shehriyar Shariq
 * @version 1.0.0
 * 
 *          This is the main entrypoint into the program. The program is about a
 *          burning service that is based on the following assumptions: 1. Each
 *          Product has some pre-heating requirements. 2. Each Oven has the
 *          capability of changing its internal temperature with due time. Time
 *          ramping has been added as part of the implementation. 3. The
 *          scheduler sorts the list of ovens only when a new product is added.
 *          It then picks the oven with the shortest total time. 4. Total time
 *          of an oven is calculated based on a number of factors. 5. A
 *          real-world oven has been attempted to be mimicked as close as
 *          possible.
 */
public class Main {
	/**
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {

		/**
		 * Configuration - Oven Temperature Ramping Time = 1 second - Product Transition
		 * Time = 5 seconds
		 */
		Configuration config = new Configuration(1, 5);

		Scheduler scheduler = new Scheduler(config);

		Transporter transporter = new Transporter();

		// Initialise the system with 5 initial ovens
		for (int i = 0; i < 5; i++) {
			Oven oven = new Oven(i + 1, config, transporter);

			// To ensure that the threads don't affect the execution of the main program.
			oven.setDaemon(true);
			oven.start();

			scheduler.addNewOven(oven);
		}

		/**
		 * The program below is meant to keep the threads alive by getting input from
		 * the user
		 */

		// To get input from the user.
		Scanner scanner = new Scanner(System.in);

		int choice = 0;

		System.out.println("Welcome to the Burning Service");
		System.out.println("Please select from one of the options below.");
		System.out.println("\t1. View All Ovens");
		System.out.println("\t2. Add Product");
		System.out.println("\t3. View Completed Products");
		System.out.println("\t4. Exit");
		System.out.println("");

		System.out.print("Your choice: ");
		choice = scanner.nextInt();
		scanner.nextLine();

		while (true) {
			switch (choice) {
			case 1: {
				showGap();
				System.out.println("Ovens in the Burning Service");
				System.out.println("Please find the list of all the ovens below.");

				scheduler.showAllOvens();

				System.out.println("----------------------------------------");
				System.out.println("Please select from one of the options below.");
				System.out.println("\t1. View All Ovens");
				System.out.println("\t2. Add Product");
				System.out.println("\t3. View Completed Products");
				System.out.println("\t4. Exit");
				System.out.println("");

				System.out.print("Your choice: ");
				choice = scanner.nextInt();
				scanner.nextLine();

				showGap();
				break;
			}
			case 2:
				showGap();

				System.out.println("Add New Product");
				System.out.println("Please enter the details of the product below.");

				// Gather information from the user
				System.out.print("Product Name: ");
				String productName = scanner.nextLine();
				System.out.print("Preheat Temperature (in C): ");
				float preheatTemp = scanner.nextFloat();
				System.out.print("Preheat Time (in seconds): ");
				float preheatTime = scanner.nextFloat();
				System.out.print("Oven Time (in seconds): ");
				float ovenTime = scanner.nextFloat();

				// Create a new Product object based on user input
				Product newProduct = new Product(productName, preheatTemp, preheatTime, ovenTime);

				// Add the new product to the scheduler
				scheduler.scheduleNewProduct(newProduct);

				showGap();
				// Display a confirmation message
				System.out.println("Product added to the scheduler.");
				System.out.println("----------------------------------------");

				choice = 1;

				break;
			case 3: {
				showGap();
				System.out.println("Completed Products");
				System.out.println("Please find the list of all completed products below.");

				transporter.showAllProducts();

				System.out.println("----------------------------------------");
				System.out.println("Please select from one of the options below.");
				System.out.println("\t1. View All Ovens");
				System.out.println("\t2. Add Product");
				System.out.println("\t3. View Completed Products");
				System.out.println("\t4. Exit");
				System.out.println("");

				System.out.print("Your choice: ");
				choice = scanner.nextInt();
				scanner.nextLine();

				showGap();
				break;
			}
			case 4: {
				System.out.println("----------------------------------------");
				System.out.println("Exiting...");

				scanner.close();
				System.exit(0);
				break;
			}
			default:
				showGap();
				System.out.println("Invalid Input! Please choose from the available options...");
				System.out.println("----------------------------------------");
			}
		}

	}

	private static void showGap() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}

}
