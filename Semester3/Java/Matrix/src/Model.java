public class Model {
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] matrixC;
    private int[][] transposedMatrixA;
    private int[][] transposedMatrixB;
    private int[][] transposedMatrixC;

    public Model(){
        matrixA = new int[0][0];
        matrixB = new int[0][0];
        matrixC = new int[0][0];
        transposedMatrixA= new int[0][0];
        transposedMatrixB= new int[0][0];
        transposedMatrixC= new int[0][0];
    }


    public void setMatrix(String name, int [][]matrix){
        if(name.equals("A")){matrixA=matrix;}
        if(name.equals("B")){matrixB=matrix;}
        if(name.equals("C")){matrixC=matrix;}
        if(name.equals("TA")){transposedMatrixA=matrix;}
        if(name.equals("TB")){transposedMatrixB=matrix;}
        if(name.equals("TC")){transposedMatrixC=matrix;}
    }

    public int[][] getMatrix(String name){
        if (name.equals("A")){return matrixA;}
        if (name.equals("B")){return matrixB;}
        if (name.equals("C")){return matrixC;}
        if (name.equals("TA")){return transposedMatrixA;}
        if (name.equals("TB")){return transposedMatrixB;}
        if (name.equals("TC")){return transposedMatrixC;}
        return null;
    }
}
