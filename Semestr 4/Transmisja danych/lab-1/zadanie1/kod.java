package Ćwiczenie1;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.IOException;

public class kod {
    public static void main(String[] args) {
        cwiczenie1();
    }

    public static void cwiczenie1(){
        double fs = 15000; // Częstotliwość próbkowania 15khZ
        double Tc = 2.5; //czas trwania sygnału
        int probki = (int)(fs*Tc); //warunek potrzebny do wygenerowania odpowiedniej ilości próbek
        double[] xData = new double[probki];
        double[] yData = new double[probki];

        double f = 10; //liczba cykli sygnału
        double fi = 0; //faza

        for(int i= 0; i < probki; i++){
            double t = (double)i/fs;//czas
            xData[i] = t;
            yData[i] = liczZad1(f, t, fi);
        }

        XYChart chart = QuickChart.getChart("Wykres", "Czas", "Amplituda", "x(t)", xData, yData);
        new SwingWrapper(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart, "src/Ćwiczenie1/pierwsze", BitmapEncoder.BitmapFormat.PNG);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static double liczZad1(double f, double t, double fi){
        //wybrana funkcja: tabela 1, funkcja 1
        return Math.cos(2*Math.PI*f*t+fi) * Math.cos(2.5*Math.pow(t,0.2)*Math.PI);
    }


}