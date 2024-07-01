package Ćwiczenie4;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.IOException;

public class kod {
    public static void main(String[] args) {
        cwiczenie4();
    }
    //bk(t) (k=1,2,3)
    public static void cwiczenie4(){
        double fs = 22050; //22.05khz
        double Tc = 1;
        int probki = (int)(fs*Tc);
        double[] xData = new double[probki];
        double[][] yData = new double[3][probki];

        for(int i= 0; i < probki; i++){
            double t = (double)i/fs;//czas
            xData[i] = t;
            for (int k=0; k<3;k++){
                yData[k][i] = liczZad4(t,k+1, getH(k+1));
            }
        }
        try{
            for (int k = 0; k<3; k++){
                XYChart chart = QuickChart.getChart("k="+(k+1)+" H="+getH(k+1), "Czas", "Amplituda", "k="+(k+1), xData, yData[k]);
                new SwingWrapper<>(chart).displayChart();
                BitmapEncoder.saveBitmap(chart, "src/Ćwiczenie4/b"+(k+1)+"(t)", BitmapEncoder.BitmapFormat.PNG);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //kod w liniach 26-34 został wygenerowany przy pomocy ChatGPT (wersja oraz prompt w pliku źródła.txt)

    }

    public static int getH(int k){
        switch(k){
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 22;
            default:
                return 0;
        }
    }
    public static double liczZad4(double t, int k, int h) {
        //TABELA 4 FUNKCJA 6
        double suma = 0;
        for (int j=0; j<k; j++){
            suma += Math.sin(h*Math.PI*t)/2+Math.cos(2*h*Math.PI*t);
        }
        return suma;
    }



}

