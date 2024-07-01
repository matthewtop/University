package Ćwiczenie2;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.IOException;

import static Ćwiczenie1.kod.liczZad1;


public class kod {
    public static void main(String[] args) {
        cwiczenie2();
    }
    public static void cwiczenie2(){
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
            yData[i] = liczZad2y(f, t, fi);
        }

        XYChart charty = QuickChart.getChart("Wykres", "Czas", "Amplituda", "y(t)", xData, yData);
        new SwingWrapper(charty).displayChart();
        try{
            BitmapEncoder.saveBitmap(charty, "src/Ćwiczenie2/y(t)", BitmapEncoder.BitmapFormat.PNG);
        }catch (IOException e){
            e.printStackTrace();
        }

        for (int i = 0; i < probki; i++){
            double t = (double)i/fs;
            xData[i] = t;
            yData[i] = liczZad2z(f, t, fi);
        }
        XYChart chartz = QuickChart.getChart("Wykres", "Czas", "Amplituda", "z(t)", xData, yData);
        new SwingWrapper(chartz).displayChart();
        try{
            BitmapEncoder.saveBitmap(chartz, "src/Ćwiczenie2/z(t)", BitmapEncoder.BitmapFormat.PNG);
        }catch (IOException e){
            e.printStackTrace();
        }

        for (int i = 0; i < probki; i++){
            double t = (double)i/fs;
            xData[i] = t;
            yData[i] = liczZad2v(f, t, fi);
        }
        XYChart chartv = QuickChart.getChart("Wykres", "Czas", "Amplituda", "v(t)", xData, yData);
        new SwingWrapper(chartv).displayChart();
        try{
            BitmapEncoder.saveBitmap(chartv, "src/Ćwiczenie2/v(t)", BitmapEncoder.BitmapFormat.PNG);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static double liczZad2y(double f, double t, double fi){
        double x = liczZad1(f,t,fi);
        //wybrana funkcja: tabela 2, funkcja 1
        return (x*t)/(3+Math.cos(20*Math.PI*t));
    }

    public static double liczZad2z(double f,double t, double fi){
        double x = liczZad1(f,t,fi);
        double y = liczZad2y(f,t,fi);
        return Math.pow(t,2) * (Math.abs(x * y - ((double) 2 /10+y)));
    }

    public static double liczZad2v(double f, double t, double fi){
        double x = liczZad1(f,t,fi);
        double y = liczZad2y(f,t,fi);
        double z = liczZad2z(f,t,fi);
        return Math.pow(z,3) + 3*Math.sin(z*y)*Math.abs(y-x);
    }


}
