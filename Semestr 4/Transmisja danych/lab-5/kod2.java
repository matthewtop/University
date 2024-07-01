import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.Arrays;

import static Lab4.kod.*;

public class kod2{
    public static void main(String[] args) {
        int[] b = new int[]{1,1,0,1,0,0,0,1,1,0};
//        demodulatorASK(10000, 2, b, 2, 1, 2);
//        demodulatorPSK(10000,2,b,2);
        demodulatorFSK(10000,2,b,2);
    }


    public static void demodulatorASK(int fs, int tc, int[] b, int W,double A1, double A2){
        int N = fs*tc;
        int M = b.length;
        double Tb = (double) tc/M;
        int Tbp = N /M;
        double fn = W*(1/Tb);

        double[] xData = new double[N];
        double[] yData = new double[N];

        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            xData[i] = t;
            int j = i / Tbp;
            if (b[j] == 0) {
                yData[i] = za1(A1, fn, t);
            } else if (b[j] == 1) {
                yData[i] = za2(A2, fn, t);
            }
        }
        XYChart chart= QuickChart.getChart("z(t)","czas","amplituda","z(t)",xData,yData);
        new SwingWrapper<>(chart).displayChart();

        try {
            BitmapEncoder.saveBitmap(chart, "lab-5/ask_z", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }

        double[] xDataSygnal=new double[N];
        int NX = yData.length;
        for (int i=0;i<NX;i++){
            double t = (double) i / fs;
            xDataSygnal[i] = t;
            yData[i] = yData[i] * Math.sin(2*Math.PI*fn*t);
        }

        XYChart chartSygnal= QuickChart.getChart("x(t)","czas","amplituda","x(t)",xDataSygnal, yData);
        new SwingWrapper<>(chartSygnal).displayChart();

        try {
            BitmapEncoder.saveBitmap(chartSygnal, "lab-5/ask_x", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e) {
            e.printStackTrace();
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

        XYChart calka = QuickChart.getChart("p(t)","czas","amplituda","p(t)",xDataSygnal,calkaDataY);
        new SwingWrapper<>(calka).displayChart();

        try {
            BitmapEncoder.saveBitmap(calka, "lab-5/ask_p", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e) {
            e.printStackTrace();
        }

        int h = 1100;
        double[] odbiornikDataY = odbiornik(h, calkaDataY,1);

        XYChart odbiornik = QuickChart.getChart("c(t)","czas","amplituda","c(t)",xDataSygnal,odbiornikDataY);
        new SwingWrapper<>(odbiornik).displayChart();

        try {
            BitmapEncoder.saveBitmap(odbiornik, "lab-5/ask_c", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e) {
            e.printStackTrace();
        }

        int[] bits = ciagBitowy(odbiornikDataY, b);
        System.out.println("            ASK         ");
        System.out.println("Ciag bitowy przed modulacją: " + Arrays.toString(b));
        System.out.println("Ciag bitowy po    modulacji: " + Arrays.toString(bits));
        System.out.println(Arrays.toString(compare(b, bits)));

    }

    public static void demodulatorPSK(int fs, int tc, int[] b, int W){
        int N = fs*tc;
        int M = b.length;
        double Tb = (double) tc/M;
        int Tbp = N /M;
        double fn = W*(1/Tb);

        double[] xData = new double[N];
        double[] yData = new double[N];

        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            xData[i] = t;
            int j = i / Tbp;
            if (b[j] == 0) {
                yData[i] = psk1(fn, t);
            } else if (b[j] == 1) {
                yData[i] = psk2(fn, t);
            }
        }

        XYChart z = QuickChart.getChart("z(t)","czas","amplituda","z(t)",xData,yData);
        new SwingWrapper<>(z).displayChart();

        try {
            BitmapEncoder.saveBitmap(z, "lab-5/psk_z", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }

        //x(t)
        double[] xDataSygnal=new double[N];
        int NX = yData.length;
        for (int i=0;i<NX;i++){
            double t = (double) i / fs;
            xDataSygnal[i] = t;
            yData[i] = yData[i] * Math.sin(2*Math.PI*fn*t);
        }

        XYChart x = QuickChart.getChart("x(t)","czas","amplituda","x(t)",xDataSygnal, yData);
        new SwingWrapper<>(x).displayChart();
        try {
            BitmapEncoder.saveBitmap(x, "lab-5/psk_x", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }

        //calka p(t)
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

        XYChart calka = QuickChart.getChart("p(t)","czas","amplituda","p(t)",xDataSygnal,calkaDataY);
        new SwingWrapper<>(calka).displayChart();

        try {
            BitmapEncoder.saveBitmap(calka, "lab-5/psk_p", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }

        double[] odbiornikDataY = odbiornik(0, calkaDataY,2);
        XYChart odbiornik = QuickChart.getChart("c(t)","czas","amplituda","c(t)",xDataSygnal,odbiornikDataY);
        new SwingWrapper<>(odbiornik).displayChart();

        try {
            BitmapEncoder.saveBitmap(odbiornik, "lab-5/psk_c", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e) {
            e.printStackTrace();
        }

        int[] bits = ciagBitowy(odbiornikDataY, b);
        System.out.println("            PSK         ");
        System.out.println("Ciag bitowy przed modulacją: " + Arrays.toString(b));
        System.out.println("Ciag bitowy po    modulacji: " + Arrays.toString(bits));
        System.out.println(Arrays.toString(compare(b, bits)));
    }

    public static void demodulatorFSK(int fs, int tc, int[] b, int W){
        int N = fs * tc;
        int M = b.length;
        double Tb = (double) tc / M;
        int Tbp = N / M;
        double fn1 = (W + 1) / Tb;
        double fn2 = (W + 2) / Tb;

        double[] xData = new double[N];
        double[] yData = new double[N];

        // Generowanie sygnału FSK
        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            xData[i] = t;
            int j = i / Tbp;
            if (b[j] == 0) {
                yData[i] = Math.sin(2 * Math.PI * fn1 * t);
            } else {
                yData[i] = Math.sin(2 * Math.PI * fn2 * t);
            }
        }

        XYChart z = QuickChart.getChart("z(t)", "czas", "amplituda", "z(t)", xData, yData);
        new SwingWrapper<>(z).displayChart();
        try {
            BitmapEncoder.saveBitmap(z, "lab-5/fsk_z", BitmapEncoder.BitmapFormat.PNG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // x(t)
        double[] yDataSygnal1 = new double[N];
        double[] yDataSygnal2 = new double[N];

        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            yDataSygnal1[i] = yData[i] * Math.sin(2 * Math.PI * fn1 * t);
            yDataSygnal2[i] = yData[i] * Math.sin(2 * Math.PI * fn2 * t);
        }

        XYChart x1 = QuickChart.getChart("x1(t)", "czas", "amplituda", "x1(t)", xData, yDataSygnal1);
        new SwingWrapper<>(x1).displayChart();
        try {
            BitmapEncoder.saveBitmap(x1, "lab-5/fsk_x1", BitmapEncoder.BitmapFormat.PNG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        XYChart x2 = QuickChart.getChart("x2(t)", "czas", "amplituda", "x2(t)", xData, yDataSygnal2);
        new SwingWrapper<>(x2).displayChart();
        try {
            BitmapEncoder.saveBitmap(x2, "lab-5/fsk_x2", BitmapEncoder.BitmapFormat.PNG);
        } catch (Exception e) {
            e.printStackTrace();
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

        XYChart calka1 = QuickChart.getChart("p1(t)", "czas", "amplituda", "p1(t)", xData, calkaDataY1);
        new SwingWrapper<>(calka1).displayChart();
        try {
            BitmapEncoder.saveBitmap(calka1, "lab-5/fsk_p1", BitmapEncoder.BitmapFormat.PNG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        XYChart calka2 = QuickChart.getChart("p2(t)", "czas", "amplituda", "p2(t)", xData, calkaDataY2);
        new SwingWrapper<>(calka2).displayChart();
        try {
            BitmapEncoder.saveBitmap(calka2, "lab-5/fsk_p2", BitmapEncoder.BitmapFormat.PNG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //p(t)
        double[] pDataY = new double[N];
        for (int i = 0; i < N; i++) {
            pDataY[i] = calkaDataY2[i] - calkaDataY1[i];
        }

        XYChart p = QuickChart.getChart("p(t)", "czas", "amplituda", "p(t)", xData, pDataY);
        new SwingWrapper<>(p).displayChart();
        try {
            BitmapEncoder.saveBitmap(p, "lab-5/fsk_p", BitmapEncoder.BitmapFormat.PNG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // c(t)
        double[] odbiornikDataY = odbiornik(0, pDataY, 3);
        XYChart odbiornik = QuickChart.getChart("c(t)", "czas", "amplituda", "c(t)", xData, odbiornikDataY);
        new SwingWrapper<>(odbiornik).displayChart();
        try {
            BitmapEncoder.saveBitmap(odbiornik, "lab-5/fsk_c", BitmapEncoder.BitmapFormat.PNG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[] bits = ciagBitowy(odbiornikDataY, b);
        System.out.println("            FSK         ");
        System.out.println("Ciag bitowy przed modulacją: " + Arrays.toString(b));
        System.out.println("Ciag bitowy po    modulacji: " + Arrays.toString(bits));
        System.out.println(Arrays.toString(compare(b, bits)));
    }

    public static double[] odbiornik(int h, double[] calkaDataY, int wybor){
        double[] odbiornikDataX = new double[calkaDataY.length];
        if(wybor==1){
            for (int i = 0; i < calkaDataY.length; i++) {
                if (calkaDataY[i] > h) {
                    odbiornikDataX[i] = 1;
                } else {
                    odbiornikDataX[i] = 0;
                }
            }
        } else if (wybor==2) {
            for(int i = 0; i<calkaDataY.length;i++){
                if(calkaDataY[i] < 0){
                    odbiornikDataX[i] = 1;
                } else {
                    odbiornikDataX[i] = 0;
                }
            }

        } else if (wybor==3) {
            for(int i = 0; i<calkaDataY.length;i++){
                if(calkaDataY[i] > 0){
                    odbiornikDataX[i] = 1;
                } else {
                    odbiornikDataX[i] = 0;
                }
            }
        } else {
            System.out.println("Nie ma takiego wyboru");
        }
        return odbiornikDataX;
    }

    public static int[] ciagBitowy(double[] odbiornikDataY, int[] b) {
        int[] bits = new int[b.length];
        int N = odbiornikDataY.length;
        int M = b.length;
        int Tbp = N / M;
        for (int i = 0; i < N; i++) {
            int j = i / Tbp;
            if (odbiornikDataY[i] > 0) {
                bits[j] = 1;
            } else {
                bits[j] = 0;
            }
        }
        return bits;
    }

    public static boolean[] compare(int[] og, int[] after){
        boolean[] result = new boolean[og.length];
        for (int i = 0; i < og.length; i++) {
            if (og[i] == after[i]) {
                result[i] = true;
            } else {
                result[i] = false;
            }
        }
        return result;
    }
}