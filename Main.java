/******************************************************************************
                              Coded Simplex
                                   by
                             Josh Woolbright
*******************************************************************************/
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println();
		System.out.println("Welcome to the Simplex LP Solver!\n");
		System.out.println();
		System.out.println("Input data in proper simplex form to solve or type E to exit\n");
		System.out.println();
		System.out.println("Input the number of variables in the equation to maximize");

		String exit = null;
		int cSize = singleNumInput(exit, input);
		input.nextLine();
		
		double[] c = new double[cSize];

		System.out.println();
		System.out.println("Please input constants of equation to maximize");
		int i = 0;
		while (i < cSize) {
			try {
				double num = input.nextDouble();
				if (num <= 0) {
					System.out.println("Error. Improper form for Simplex, negative or nonzero value." +  
									   " Reinput values of equation to maximize or type E to exit");
					input.nextLine();
					i = 0;
				}
				else {
					c[i] = num;
					i++;
				}
			} catch (InputMismatchException e) {
				exit = input.nextLine();
				if (exit.equals("E")) {
					System.out.println("Cheers!");
					input.close();
					System.exit(0);
				}
				System.out.println("Invalid input. Please reinput values in proper form or type E to exit");
				i = 0;
			}
		}
		input.nextLine(); 

		System.out.println();
		System.out.println("Input number of constraint variables");
		int columns = singleNumInput(exit, input);
		input.nextLine();

		System.out.println();
		System.out.println("Input number of constraints");
		int rows = singleNumInput(exit, input);
		input.nextLine();

		double[][] A = new double[rows][columns];
		double[] b = new double[rows];
		
		System.out.println();
		i = 0;
		int j = 0;
		int row = i + 1;
		while (i < rows) {
			System.out.println("Input constraint variable constants for row " + row + " (Must include zero constants)");
			while (j < columns) {
				try {
					A[i][j] = input.nextDouble();
					j++; 
				} catch (InputMismatchException e) {
					exit = input.nextLine();
					if (exit.equals("E")) {
						System.out.println("Goodbye");
						input.close();
						System.exit(0);
					}
					System.out.println("Invalid input. Please reinput values in proper form or type E to exit");
					j = 0;
				}
			}
			input.nextLine();
			j = 0;
			i++;
			row++;
		}

		System.out.println();
		System.out.println("Input the right hand side constraint values");

		i = 0;
		while (i < rows) {
			try {
				double num = input.nextDouble();
				if (num <= 0) {
					System.out.println("Error. Improper form for Simplex, negative or nonzero value." +  
									   " Reinput values or type E to exit");
					i = 0;
				}
				else {
					b[i] = num;
					i++;
				}
			} catch (InputMismatchException e) {
				exit = input.nextLine();
				if (exit.equals("E")) {
					System.out.println("Til next time...");
					input.close();
					System.exit(0);
				}
				System.out.println("Invalid input. Please reinput values in proper form or type E to exit");
				i = 0;
			}
		}
		
		Simplex solver = new Simplex(A, c, b);
		solver.runSimplex();
		input.close();
	}

	private static int singleNumInput(String exit, Scanner input) {
		while (true) {
			try {
				int num = input.nextInt();
				if (num < 0) {
					System.out.println("Invalid input. Size cannot be negative");
				}
				else{
					return num;
				}
			} catch (InputMismatchException e) {
				exit = input.nextLine();
				if (exit.equals("E")) {
					System.out.println("Come back soon!");
					input.close();
					System.exit(0);
				}
				System.out.println("Invalid input. Please type in proper input or type E to exit");
			}
		}
	}

}