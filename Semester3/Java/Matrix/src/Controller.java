import java.util.Scanner;
import View.*;

public class Controller {
    private final Model model;
    private final View view;
    private final Scanner scanner;

    public Controller(Model model, View view, Scanner scanner){
        this.model=model;
        this.view = view;
        this.scanner=scanner;
    }

    public void run(){
        while (true) {
            Menus.showMainMenu();
            chooseMainOption();
        }
    }

    public void chooseMainOption(){
        int choice=scanner.nextInt();
        switch (choice){
            case 1:
                createEnteringMatrixes();
                break;
            case 2:
                chooseSecondOption();
                break;
            case 3:
                chooseThirdOption();
                break;
            case 4:
                System.exit(0);
            default:
                Errors.wrongChoiceError();
                break;
        }
    }

    public void createEnteringMatrixes(){
        model.setMatrix("A",insertMatrix(getRows(),getColumns()));
        model.setMatrix("B",insertMatrix(getRows(),getColumns()));
    }

    public void chooseSecondOption(){
        Menus.showSecondMenu();
        int choice=scanner.nextInt();
        switch (choice){
            case 1:
                transposeMatrixesAandB();
                break;
            case 2:
                model.setMatrix("C",multiply(model.getMatrix("A"), model.getMatrix("B")));
                break;
            case 3:
                model.setMatrix("TC", transpose(model.getMatrix("C")));
                break;
            default:
                Errors.wrongChoiceError();
                break;
        }
    }
    public void transposeMatrixesAandB(){
        model.setMatrix("TA", transpose(model.getMatrix("A")));
        model.setMatrix("TB", transpose(model.getMatrix("B")));
    }

    public void chooseThirdOption(){
        Menus.showThirdMenu();
        int choice=scanner.nextInt();
        switch (choice){
            case 1:
                view.displayMatrix("A");
                view.displayMatrix("B");
                break;
            case 2:
                view.displayMatrix("C");
                break;
            case 3:
                view.displayMatrix("TA");
                view.displayMatrix("TB");
                break;
            default:
                Errors.wrongChoiceError();
                break;
        }
    }

    public int[][] insertMatrix(int rows, int columns){
        int[][] matrix = new int[rows][columns];
        View.getValues();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print("Element [" +(i+1) + "]" + (j+1) + "]: " );
                matrix[i][j] = scanner.nextInt();
            }
        }
        return matrix;
    }

    public int getRows(){
        View.howManyRows();
        return scanner.nextInt();
    }

    public int getColumns(){
        View.howManyColumns();
        return scanner.nextInt();
    }

    public static void showMatrix(int[][] matrix) {
        if(matrix.length==0 || matrix[0].length==0){
            Errors.matrixIsEmptyError();
        }
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    public int [][] transpose(int[][]matrix){
        int rows=matrix.length;
        int columns=matrix[0].length;
        int [][]transposed = new int[columns][rows];
        for (int i=0; i<rows; i++) {
            for (int j = 0; j < columns; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    public int [][] multiply(int [][]matrixA, int[][]matrixB) {
        int[][] matrixCtemp = new int[matrixA.length][matrixB.length];
        if (matrixA[0].length == matrixB.length) {
            for (int i = 0; i < matrixCtemp.length; i++) {
                for (int j = 0; j < matrixCtemp.length; j++) {
                    for (int k = 0; k < matrixA[0].length; k++) {
                        matrixCtemp[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
        } else {
            Errors.wrongMatrixSizeError();
        }
        return matrixCtemp;
    }
}
