package zadanie2;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.Random;

import static zadanie1.kod.dft;


public class kod {
    public static void main(String[] args) {
        //int[] probki = new int[]{3,8,0,-1,1,2,3,4,5,6,7,8,9};
        int[] probki = losoweProbki(100);
        double fs = 3000;
        int N = probki.length;

        double[][] dft = dft(probki);
        double[] widmo = widmo(dft);
        double[] czestotliwosci = skalaCzestotliwosci(fs,N);
        wykres(czestotliwosci,widmo,"Widmo","Hz","Amplituda[dB]","widmo");

    }

    public static double[] widmo(double[][] wyniki){
        int N = wyniki.length;
        double[]widmo=new double[N];
        for (int i = 0; i < N; i++) {
            widmo[i] = Math.sqrt(wyniki[i][0]*wyniki[i][0]+wyniki[i][1]*wyniki[i][1]);
        }
        return widmo;
    }

    public static double[] skalaCzestotliwosci(double fs, int N){
        double[] czestotliwosci = new double[N];
        for (int i = 0; i <N; i++) {
            czestotliwosci[i]=(double) i/N*fs;
        }
        return czestotliwosci;
    }

    public static void wykres(double[] x, double[] y, String tytul, String oX, String oY, String nazwaPliku) {
        XYChart chart = new XYChart(800, 600);
        double[] skalaDB = skalaDecybelowa(y);
        chart.setTitle(tytul);
        chart.setXAxisTitle(oX);
        chart.setYAxisTitle(oY);
        chart.addSeries(tytul, x, skalaDB);
        new SwingWrapper<>(chart).displayChart();
        try {
            BitmapEncoder.saveBitmap(chart, "src/zadanie2/"+nazwaPliku+".PNG", BitmapEncoder.BitmapFormat.PNG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int[] losoweProbki(int N){
        int[] probki = new int[N];
        Random random = new Random();
        for (int i = 0; i < N; i++) {
            probki[i] = random.nextInt(101);
        }
        return probki;
    }

    public static double[] skalaDecybelowa(double[] widmo){
        double[] db = new double[widmo.length];
        for (int i = 0; i < widmo.length; i++) {
            db[i] = 10*Math.log10(widmo[i]);
        }
        return db;
    }

}


