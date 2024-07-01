import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import static Lab4.kod.*;

public class Help {
    // na potrzeby lab7 ten plik to zlepka najwazniejszych metod z lab6,5 oraz 4, ponieważ funkcje nie zostały napisane
    //w prawidłowy sposób, przez co ich wykorzystanie w lab7 mija się z celem.

    public static double[] modulujASK(int fs, int tc, int[] b, int W, double A1, double A2){
        if (A1>A2){
            double temp = A1;
            A1 = A2;
            A2 = temp;
        }
        int N = fs*tc;
        int M = b.length;
        double Tb = (double) tc/M;
        int Tbp = N /M;
        double fn = W*(1/Tb);

        double[] xData = new double[N];
        double[] yData = new double[N];

        for(int i=0;i<N;i++){
            double t = (double) i / fs;
            xData[i] = t;
            int j = Math.min(i / Tbp, M - 1);
            if (b[j] == 0) {
                yData[i] = za1(A1, fn, t);
            } else if (b[j] == 1) {
                yData[i] = za2(A2, fn, t);
            }
        }
//        XYChart chart= QuickChart.getChart("ASK","czas","amplituda","wykres czasu ASK",xData,yData);
//        new SwingWrapper<>(chart).displayChart();
        return yData;
    }

    public static double[] generujXdata(int fs, int tc){
        int N = fs*tc;
        double[] xData = new double[N];
        for (int i = 0; i < N; i++) {
            xData[i] = (double) i / fs;
        }
        return xData;
    }

    public static int[] demodulujASK(int fs, int tc, int[] b, int W, double A1, double A2,double[] yData){
        int N = fs*tc;
        int M = b.length;
        double Tb = (double) tc/M;
        double fn = W*(1/Tb);
        double[] xDataSygnal=new double[N];
        int NX = yData.length;
        for (int i=0;i<NX;i++){
            double t = (double) i / fs;
            xDataSygnal[i] = t;
            yData[i] = yData[i] * Math.sin(2*Math.PI*fn*t);
        }

        //całka
        double suma = 0;
        int probka = 0;
        double[] calkaDataY = new double[N];

        for (int i =0; i<xDataSygnal.length;i++){
            if (probka >= fs * Tb) {
                suma = 0;
                probka = 0;
            }
            suma += yData[i];
            calkaDataY[i] = suma;
            probka++;
        }
//        XYChart calka = QuickChart.getChart("Calka","czas","wartosc","wykres calki",xDataSygnal,calkaDataY);
//        new SwingWrapper<>(calka).displayChart();

        int h = 100;
        double[] odbiornikDataY = kod2.odbiornik(h, calkaDataY,1);
        int[] bits = ciagBitowy(odbiornikDataY, b);
        return bits;
    }


    public static int[] ciagBitowy(double[] odbiornikDataY, int[] b) {
        int[] bits = new int[b.length];
        int N = odbiornikDataY.length;
        int M = b.length;
        int Tbp = N / M;
        for (int i = 0; i < N; i++) {
            int j = Math.min(i / Tbp, M - 1);
            if (odbiornikDataY[i] > 0) {
                bits[j] = 1;
            } else {
                bits[j] = 0;
            }
        }
        return bits;
    }

    public static double[] modulujPSK(int fs, int tc, int[] b, int W) {
        int N = fs * tc;
        int M = b.length;
        double Tb = (double) tc / M;
        int Tbp = N / M;
        double fn = W * (1 / Tb);

        double[] yData = new double[N];

        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            int j = Math.min(i / Tbp, M - 1);
            if (b[j] == 0) {
                yData[i] = Math.sin(2 * Math.PI * fn * t);
            } else {
                yData[i] = Math.sin(2 * Math.PI * fn * t + Math.PI);
            }
        }
        return yData;
    }

    public static int[] demodulujPSK(int fs, int tc, int[] b, int W, double[] yData) {
        int N = fs * tc;
        int M = b.length;
        double Tb = (double) tc / M;
        int Tbp = N / M;
        double fn = W * (1 / Tb);

        double[] xDataSygnal = new double[N];
        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            xDataSygnal[i] = t;
            yData[i] = yData[i] * Math.sin(2 * Math.PI * fn * t);
        }

        //calka
        double suma = 0;
        int probka = 0;
        double[] calkaDataY = new double[N];

        for (int i = 0; i < xDataSygnal.length; i++) {
            if (probka >= fs * Tb) {
                suma = 0;
                probka = 0;
            }
            suma += yData[i];
            calkaDataY[i] = suma;
            probka++;
        }

        int[] bits = new int[M];
        for (int i = 0; i < M; i++) {
            int start = i * Tbp;
            int end = (i + 1) * Tbp;
            double sum = 0;
            for (int j = start; j < end; j++) {
                sum += calkaDataY[j];
            }
            if (sum < 0) {
                bits[i] = 1;
            } else {
                bits[i] = 0;
            }
        }
        return bits;
    }

    public static double[] modulujFSK(int fs, int tc, int[] b, int W) {
        int N = fs * tc;
        int M = b.length;
        double Tb = (double) tc / M;
        int Tbp = N / M;
        double fn1 = (W + 1) * (1 / Tb);
        double fn2 = (W + 2) * (1 / Tb);

        double[] xData = new double[N];
        double[] yData = new double[N];

        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            xData[i] = t;

            int j = i / Tbp;

            if (j < M) {
                if (b[j] == 0) {
                    yData[i] = fsk1(fn1, t);
                } else if (b[j] == 1) {
                    yData[i] = fsk2(fn2, t);
                }
            }
        }

//        XYChart chart = QuickChart.getChart("modulacja fsk", "czas", "Amplituda", "fsk", xData, yData);
//        new SwingWrapper<>(chart).displayChart();

        return yData;
    }

    public static int[] demodulujFSK(int fs, int tc, int[]b, int W, double[] fsk){
        int N = fs * tc;
        int M = b.length;
        double Tb = (double) tc / M;
        double fn1 = (W + 1) / Tb;
        double fn2 = (W + 2) / Tb;

        double[] yDataSygnal1 = new double[N];
        double[] yDataSygnal2 = new double[N];

        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            yDataSygnal1[i] = fsk[i] * Math.sin(2 * Math.PI * fn1 * t);
            yDataSygnal2[i] = fsk[i] * Math.sin(2 * Math.PI * fn2 * t);
        }

        //integracja
        double[] calkaDataY1 = new double[N];
        double[] calkaDataY2 = new double[N];
        double suma1 = 0;
        double suma2 = 0;
        int probka1 = 0;
        int probka2 = 0;

        for (int i = 0; i < N; i++) {
            if (probka1 >= fs * Tb) {
                suma1 = 0;
                probka1 = 0;
            }
            if (probka2 >= fs * Tb) {
                suma2 = 0;
                probka2 = 0;
            }
            suma1 += yDataSygnal1[i];
            suma2 += yDataSygnal2[i];
            calkaDataY1[i] = suma1;
            calkaDataY2[i] = suma2;
            probka1++;
            probka2++;
        }

        //p(t)
        double[] pDataY = new double[N];
        for (int i = 0; i < N; i++) {
            pDataY[i] = calkaDataY2[i] - calkaDataY1[i];
        }

        // c(t)
        double[] odbiornikDataY = kod2.odbiornik(0, pDataY, 3);

        int[] bits = ciagBitowy(odbiornikDataY, b);
        return bits;
    }

}