package Lab4;
//tc,fs,N
//M - liczba bitów
//M=8 Tc = 1s
//Tb = TC/M -> w sekundach
//Tbp = [Ts* fs] = [N/M]
//fn = W * 1/Tb   W to wielokrotność to ustalam
//ASK
//A1,A2
//FSK fn1, fn2  fn1 = (W+1)/tb   fn2 =(w+2)/tb
//PSK fazowa fi1=0, fi2 = PI
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.IOException;
import java.util.Arrays;

public class kod {
    public static void main(String[] args) {
        int[] b = new int[]{1,1,0,1,0,0,0,1,1,0};
        liczASK(10000,2,b,2,1,2);
//        liczPSK(10000,2,b,2);
//        liczFSK(10000,2,b,2);
//        System.out.println(textToBin("Mateusz"));
    }

    //    kluczowanie z przesuwem ampltiudy
    public static void liczASK(int fs, int tc, int[] b, int W,double A1, double A2){
        int N = fs*tc;
        int widmoN = Integer.highestOneBit(N - 1) << 1;
        int M = b.length;
        double Tb = (double) tc/M;
        int Tbp = N /M;
        double fn = W*(1/Tb);

        double[] xData = new double[N];
        double[] yData = new double[N];

        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            xData[i] = t;
            int j = i / Tbp; // indeks bitu
            if (b[j] == 0) {
                yData[i] = za1(A1, fn, t);
            } else if (b[j] == 1) {
                yData[i] = za2(A2, fn, t);
            }
        }

        XYChart chart=QuickChart.getChart("ASK","czas","amplituda","wykres czasu ASK",xData,yData);
        new SwingWrapper<>(chart).displayChart();

        try {
            BitmapEncoder.saveBitmap(chart, "lab-4/za", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }

        double[] xDataWidmo = new double[widmoN];
        double[] yDataWidmo = new double[widmoN];
        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            xDataWidmo[i] = t;
            int j = i / Tbp;
            if (b[j] == 0) {
                yDataWidmo[i] = za1(A1, fn, t);
            } else if (b[j] == 1) {
                yDataWidmo[i] = za2(A2, fn, t);
            }
        }
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] transformedComplex = fft.transform(yDataWidmo, TransformType.FORWARD);
        double[] transformed = new double[N];
        for (int i = 0; i < N; i++) {
            transformed[i] = transformedComplex[i].abs(); // modul z
        }

        double[] widmo = widmo(transformed);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosci = skalaCzestotliwosci(fs, widmo.length);

        szerokoscPasma(widmo, -3, db);
        szerokoscPasma(widmo, -6, db);
        szerokoscPasma(widmo, -12, db);

        XYChart chart1 = QuickChart.getChart("Widmo ASK" ,"Częstotliwość", "Amplituda","WIDMO ASK", czestotliwosci, db);
        new SwingWrapper<>(chart1).displayChart();

        try {
            BitmapEncoder.saveBitmap(chart1, "lab-4/za_widmo", BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    kluczowanie z przesuwem fazy
    public static void liczPSK(int fs, int tc, int[] b, int W){
        int N = fs*tc;
        int widmoN = Integer.highestOneBit(N - 1) << 1;
        int M = b.length;
        double Tb = (double) tc/M;
        int Tbp = N /M;
        double fn = W*(1/Tb);

        double[] xData = new double[N];
        double[] yData = new double[N];

        double[] xDataWidmo = new double[widmoN];
        double[] yDataWidmo = new double[widmoN];


        for(int i=0;i<N;i++){
            double t = (double) i / fs;
            xData[i] = t;
            int j = i / Tbp;
            if (b[j] == 0) {
                yData[i] = psk1(fn, t);
            } else if (b[j] == 1) {
                yData[i] = psk2(fn, t);
            }
        }
        XYChart chart1 = QuickChart.getChart("psk","x","y","psk czas",xData,yData);
        new SwingWrapper<>(chart1).displayChart();

        try {
            BitmapEncoder.saveBitmap(chart1, "lab-4/zp", BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            xDataWidmo[i] = t;
            int j = i / Tbp;
            if (b[j] == 0) {
                yDataWidmo[i] = psk1(fn, t);
            } else if (b[j] == 1) {
                yDataWidmo[i] = psk2(fn, t);
            }
        }
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] transformedComplex = fft.transform(yDataWidmo, TransformType.FORWARD);
        double[] transformed = new double[widmoN];
        for (int i = 0; i < widmoN; i++) {
            transformed[i] = transformedComplex[i].abs();
        }

        double[] widmo = widmo(transformed);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosci = skalaCzestotliwosci(fs, widmo.length);

        XYChart chart = QuickChart.getChart("Widmo PSK" ,"Częstotliwość", "Amplituda","WIDMO PSK", czestotliwosci, db);
        new SwingWrapper<>(chart).displayChart();

        szerokoscPasma(widmo, -3, db);
        szerokoscPasma(widmo, -6, db);
        szerokoscPasma(widmo, -12, db);

        try{
            BitmapEncoder.saveBitmap(chart, "lab-4/zp_widmo", BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //kluczowanie z przesuwem częstotliwości
    public static void liczFSK(int fs,int tc,int[] b, int W){
        int N = fs*tc;
        int widmoN = Integer.highestOneBit(N - 1) << 1;
        int M = b.length;
        double Tb = (double) tc/M;
        int Tbp = N /M;
        double fn1 = (W+1)*(1/Tb);
        double fn2 = (W+2)*(1/Tb);
        double[] xData = new double[N];
        double[] yData = new double[N];

        for (int i = 0; i < N; i++) {
            double t = (double) i /fs;
            xData[i] = t;
            int j = i / Tbp;
            if (b[j] == 0) {
                yData[i] = fsk1(fn1, t);
            } else if (b[j] == 1) {
                yData[i] = fsk2(fn2, t);
            }
        }

        XYChart chart1 = QuickChart.getChart("fsk","x","y","fsk czas",xData,yData);
        new SwingWrapper<>(chart1).displayChart();

        try {
            BitmapEncoder.saveBitmap(chart1, "lab-4/zf", BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }

        double[] xDataWidmo = new double[widmoN];
        double[] yDataWidmo = new double[widmoN];

        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            xDataWidmo[i] = t;
            int j = i / Tbp;
            if (b[j] == 0) {
                yDataWidmo[i] = psk1(fn1, t);
            } else if (b[j] == 1) {
                yDataWidmo[i] = psk2(fn2, t);
            }
        }

        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] transformedComplex = fft.transform(yDataWidmo, TransformType.FORWARD);
        double[] transformed = new double[widmoN];
        for (int i = 0; i < widmoN; i++) {
            transformed[i] = transformedComplex[i].abs();
        }

        double[] widmo = widmo(transformed);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosci = skalaCzestotliwosci(fs, widmo.length);

        XYChart chart = QuickChart.getChart("Widmo FSK" ,"Częstotliwość", "Amplituda[db]","WIDMO FSK", czestotliwosci, db);
        new SwingWrapper<>(chart).displayChart();

        szerokoscPasma(widmo, -3, db);
        szerokoscPasma(widmo, -6, db);
        szerokoscPasma(widmo, -12, db);

        try {
            BitmapEncoder.saveBitmap(chart, "lab-4/zf_widmo", BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String textToBin(String text) {
        StringBuilder strumien = new StringBuilder();
        for (char znak : text.toCharArray()) {
            if (znak >= 32 && znak <= 127) {
                String bin = Integer.toBinaryString(znak);
                while (bin.length() < 7) {
                    bin = "0" + bin;
                }
                strumien.append(bin);
            } else {
                strumien.append("0".repeat(7));
            }
        }
        return strumien.toString();
    }

    public static double za1(double A1, double fn, double t){return A1 * Math.sin(2*Math.PI*fn*t);}

    public static double za2(double A2, double fn, double t){return A2 * Math.sin(2*Math.PI*fn*t);}

    public static double psk1(double fn, double t){return Math.sin(2*Math.PI*fn*t);}

    public static double psk2(double fn, double t){return Math.sin(2*Math.PI*fn*t + Math.PI);}

    public static double fsk1(double fn, double t){return Math.sin(2*Math.PI*fn*t);}

    public static double fsk2(double fn, double t){return Math.sin(2*Math.PI*fn*t);}


    public static double[] widmo(double[] a) {
        return Lab3.kod.widmo(a);
    }
    public static double[] skalaDecybelowa(double[] widmo) {
        return Lab3.kod.skalaDecybelowa(widmo);
    }
    public static double[] skalaCzestotliwosci(int fs, int widmoLength) {
        return Lab3.kod.skalaCzestotliwosci(fs, widmoLength);
    }

    public static void szerokoscPasma(double[] widmo, double progDB, double[] skaladb) {
        Lab3.kod.szerokoscPasma(widmo, progDB, skaladb);
    }
}
