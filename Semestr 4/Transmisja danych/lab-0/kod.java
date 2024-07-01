//Mateusz Tołpa
//tm53837@zut.edu.pl
//222A Transmisja Danych lab0
//wykorzystane źródła znajdują się w pliku readme!


//kod zmieniony na potrzeby tego by repo bylo kompletne i umozliwialo calkowite z niego korzystanie
//wykres również został zmieniony, by w każdym zadaniu używać tej samej biblioteki, wcześniej kod był z javaFX


import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.IOException;

public class kod  {
    public static void main(String[] args) {
        double[] xData = new double[21] ;
        double[] yData = new double[21] ;

        for (int i = 0; i < xData.length; i++) {
            xData[i] = i-10;
            yData[i] = policzWartosci(xData[i]);
        }

        XYChart chart = QuickChart.getChart("Wykres x^3", "X", "Y", "y(x)", xData, yData);
        new SwingWrapper(chart).displayChart();

        try {
            BitmapEncoder.saveBitmap(chart, "lab-0/f", BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static double policzWartosci(double x){return  Math.pow(x,3);}

    }
