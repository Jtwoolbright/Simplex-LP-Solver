import java.util.HashMap;
import java.util.HashSet;

public class Simplex {

    private double[][] A;
    private double[] c;
    private double[] b;

    public Simplex(double[][] A, double[] c, double b[]) {
        this.A = A;
        this.c = c;
        this.b = b;
    }

    // Carries out Simplex and prints tableaus at each step
	public void runSimplex() {
	    HashMap<Integer, Double> solution = new HashMap<Integer, Double>();
	    for (int j = 0; j < c.length; j++) {
	        solution.put(j+1,0.0);
	    }
	    
	    int[] basis = new int[b.length];
	    double[][] tableau = initialize(basis);
	    System.out.println("Initial Tableau\n");
	    printTableau(tableau);
	    System.out.println("");
	    
	    int col = enteringVar(tableau);
	    int i = 1;
	    while (col != -1) {
	        int row = leavingVar(tableau, col);
	        if (row == -1) {
	            System.out.println("The LP is unbounded");
	            return;
	        }
	        if (row == -2) {
	            System.out.println("The LP has no feasible solution");
	            return;
	        }
	        basis[row-1] = col;
	        iterate(tableau, row, col);
	        System.out.println("Tableau After " + i + " Iteration(s)\n");
	        printTableau(tableau);
	        System.out.println("");
	        col = enteringVar(tableau);
	        i++;
	    }
        
        for (int j = 0; j < basis.length; j++) {
            int var = basis[j] + 1;
            if(solution.containsKey(var)) {
                solution.replace(var, tableau[var][tableau[0].length-1]);
            }
        }
        if (checkInfinite(tableau, basis)) {
            System.out.println("There are infinitely many solutions to this LP with z = " +
                                tableau[0][tableau[0].length-1]);
            return;
        }
        System.out.println("Optimal Tableau\n");
        printTableau(tableau);
        System.out.println("");
        System.out.println("Optimal Solution");
        System.out.println("z = " + tableau[0][tableau[0].length-1]);
        for (int j=1; j<=solution.size(); j++) {
            System.out.println("x" + j + " = " + solution.get(j));
        }
	}
	
	// Checks for LPs with infine solutions
	private boolean checkInfinite(double[][] tableau, int[] basis) {
	    HashSet<Integer> optBasis = new HashSet<Integer>();
	    for (int i = 0; i < basis.length; i++) {
	        optBasis.add(basis[i]);
	    }
	    for (int i = 0; i < tableau[0].length - 1; i++) {
	        if (tableau[0][i] == 0) {
	            if(!optBasis.contains(i)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	// Performs matrix operations on the tableau
	private void iterate(double[][] tableau, int row, int col) {
	    int rows = tableau.length;
	    int columns = tableau[0].length;
	    double basicVar = tableau[row][col];
	    int i = 0;
	    double[] atEnd = new double[columns];
	    while(i < rows) {
	        if (tableau[i][col] == 0) {
	            i++;
	        }
	        else if (i != row) {
	            double value = tableau[i][col];
	            double rowFactor = value / basicVar;
	            rowFactor*=-1;
	            for (int j = 0; j < columns; j++) {
	                double temp = tableau[i][j] + tableau[row][j] * rowFactor;
	                tableau[i][j] = temp;
	            }
	            i++;
	        }
	        else {
	            if (basicVar == 1) {
	                i++;
	            }
	            else {
	                for(int j = 0; j < columns; j++) {
	                    atEnd[j] = tableau[row][j] / basicVar;
	                }
	                i++;
	            }
	        }
	    }
	    for(int j = 0; j < columns; j++) {
	        tableau[row][j] = atEnd[j];
	    }
	}
	
	// Returns the index for the column of the entering variable
	private int enteringVar(double[][] tableau) {
	    int col = -1;
	    double mostNeg = 0;
	    for (int i = 0; i < tableau[0].length - 1; i++) {
	        if (tableau[0][i] < mostNeg) {
	            mostNeg = tableau[0][i];
	            col = i;
	        }
	    }
	    return col;
	}
	
	// Returns the index for the row of the leaving variable
	// Additionally, checks for unbounded LPs and LPs with no feasible region
	private int leavingVar(double[][] tableau, int col){
	    double min = 0;
	    int row = -1;
	    int infeasible = -1;
	    for (int i = 0; i < tableau.length; i++) {
	        if (tableau[i][tableau[0].length - 1] < 0) {
	            infeasible = -2;
	        }
	        else {
	            infeasible = -1;
	            double ratio = tableau[i][tableau[0].length - 1] / tableau[i][col];
	            if (min > 0 && ratio > 0) {
	                if (ratio < min) {
	                    min = ratio;
	                    row = i;
	                }
	            }
	            else if (min == 0 && ratio > 0) {
	                min = ratio;
	                row = i;
	            }
	        }
	    }
	    if (infeasible == -2) {
	        return infeasible;
	    }
	    return row;
	}
	
	// Inputs A,c,b, and slack variables into tableau represented by double[][] and returns
	// Additionally, initializes current basis with slack variables
	private double[][] initialize(int[] basis) {
	    int columns = c.length + b.length + 1;
	    int rows = b.length + 1;
	    double[][] tableau = new double[rows][columns];
	    for (int i = 0; i < columns; i++) {
	        if (i < c.length)
	            tableau[0][i] = -c[i];
	        else
	            tableau[0][i] = 0;
	    }
	    int slackIndex = A[0].length;
	    
	    for (int i = 0; i < A.length; i++) {
	        for (int j = 0; j < columns; j++) {
	            if (j < A[0].length) {
	               tableau[i+1][j] = A[i][j];
	            }
	            else if (j < columns - 1) {
	                if (slackIndex == j) {
	                    tableau[i+1][j] = 1;
	                    basis[slackIndex - A[0].length] = slackIndex;
	                }
	                else {
	                    tableau[i+1][j] = 0;
	                }
	            }
	            else {
	                tableau[i+1][j] = b[i];
	            }
	        }
	        slackIndex++;
	    }
	    return tableau;
	}
	
	private void printTableau(double[][] tableau) {
	    int rows = tableau.length;
	    int columns = tableau[0].length;
	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < columns; j++) {
	            System.out.print(tableau[i][j] + " ");
	        }
	        System.out.println("");
	    }
	}
}
