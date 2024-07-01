package Ćwiczenie3;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.IOException;

public class kod {
    public static void main(String[] args) {
        cwiczenie3();
    }
    public static void cwiczenie3(){
        double fs = 15000;
        double Tc = 2.5;
        int probki = (int)(fs*Tc);
        double[] xData = new double[probki];
        double[] yData = new double[probki];
        for(int i= 0; i < probki; i++){
            double t = (double)i/fs;//czas
            xData[i] = t;
            yData[i] = liczZad3(t);
        }
        XYChart charty = QuickChart.getChart("Wykres", "Czas", "Amplituda", "u(t)", xData, yData);
        new SwingWrapper(charty).displayChart();
        try{
            BitmapEncoder.saveBitmap(charty, "src/Ćwiczenie3/u(t)", BitmapEncoder.BitmapFormat.PNG);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static double liczZad3(double t){
        //TABELA 3 FUNKCJA 3

        if(t>=0 && t<1.2){
            return (Math.pow(-t,2)+0.5)*Math.sin(30*Math.PI*t)*(Math.log(Math.pow(t,2)+1)/Math.log(2));
        }
        if(t>=1.2 && t<2.0){
            return 1/t * 0.8 * Math.sin(24*Math.PI*t)-0.1*t;
        }
        if (t>=2.0 && t<2.4){
            return Math.abs(Math.sin(2*Math.PI*Math.pow(t,2)));
        }
        if (t>=2.4 && t<3.1){
            return 0.23*Math.sin(20*Math.PI*t)*Math.sin(12*Math.PI*t);
        }
        return 0;
    }


}
