import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.Arrays;
import java.util.Random;

import static Lab4.kod.za1;
import static Lab4.kod.za2;

public class kod1 {
    public static void main(String[] args) {
//        systemTransmisyjnySzum(44,5000,5,2,1,0.5,1,false,0.5,true,1);
//        int[] dane = generujTablice(44);
//        int[] zakodowane = kodujTablice(dane);
//        systemTransmisyjny(5000, 5, zakodowane, 2, 1, 0.5);
        eksperyment(44,5000,5,2,1,0.5,3);
//        systemTransmisyjnySzum(44,5000,5,2,1,0.5,1,true,1,true,10);
    }


//    public static int[] generujTablice(int n) {
//        int[] tablica = new int[n];
//        for (int i = 0; i < n; i++) {
//            if (i % 2 == 0) {
//                tablica[i] = 1;
//            } else {
//                if (i % 3 == 0) {
//                    tablica[i] = 0;
//                } else {
//                    tablica[i] = 1;
//                }
//            }
//        }
//        return tablica;
//    }


    //zmiana dla uzyskania roznorodnosci wartosci w tablicy
    public static int[] generujTablice(int n) {
        int[] tablica = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            tablica[i] = random.nextInt(2);
        }
        return tablica;
    }

    public static int[] kodujTablice(int[] dane) {
        if (dane.length % 4 != 0) {
            throw new IllegalArgumentException("Długość tablicy musi być wielkokrotnoscia 4");
        }

        int segmenty = dane.length / 4;
        int[] zakodowaneDane = new int[7 * segmenty];

        for (int i = 0; i < segmenty; i++) {
            int[] segment = Arrays.copyOfRange(dane, i * 4, i * 4 + 4);
            int[] zakodowanySegment = kod.koder7_4(segment);
            System.arraycopy(zakodowanySegment, 0, zakodowaneDane, i * 7, 7);
        }
        return zakodowaneDane;
    }

    //zad1
    public static void systemTransmisyjny(int fs, int tc, int[] b, int W, double A1, double A2) {
        double[] yData = Help.modulujASK(fs, tc, b, W, A1, A2);
        int[] demodulowaneBity = Help.demodulujASK(fs, tc, b, W, A1, A2, yData);
        System.out.println("Zdemodulowane bity  = " + Arrays.toString(demodulowaneBity));
        int[] dekodowaneBity = dekodujTablice(demodulowaneBity);
        System.out.println("Dekodowane          = " + Arrays.toString(dekodowaneBity));
        double ber = wskaznikBer(b, demodulowaneBity);
        System.out.println("Wskaźnik BER wynosi = " + ber + "%");
    }

    public static int[] dekodujTablice(int[] dane) {
        if (dane.length % 7 != 0) {
            throw new IllegalArgumentException("Długość tablicy musi być wielkokrotnoscia 7");
        }

        int segmenty = dane.length / 7;
        int[] zdekodowaneDane = new int[4 * segmenty];

        for (int i = 0; i < segmenty; i++) {
            int[] segment = Arrays.copyOfRange(dane, i * 7, i * 7 + 7);
            int[] zdekodowanySegment = kod.dekoder7_4(segment);
            System.arraycopy(zdekodowanySegment, 0, zdekodowaneDane, i * 4, 4);
        }
        return zdekodowaneDane;
    }

    public static double wskaznikBer(int[] wejscie, int[] wyjscie) {
        int dlugosc = Math.min(wejscie.length, wyjscie.length);
        int counter = 0;
        for (int i = 0; i < dlugosc; i++) {
            if (wejscie[i] != wyjscie[i]) {
                counter++;
            }
        }
        return (double) counter / dlugosc * 100;
    }


    //zad2 i 3
    public static void systemTransmisyjnySzum(int n, int fs, int tc, int W, double A1, double A2, int wybor, boolean bialy, double alpha,boolean tlumienie, double beta) {
        int[] dane = generujTablice(n);
        int[] zakodowane = kodujTablice(dane);
        int N = fs * tc;
        Random random = new Random();
        switch (wybor) {
            case 1:
//                System.out.println("ASK");
//                System.out.println("bity wejściowe        = " + Arrays.toString(dane));
//                System.out.println("bity zakodowane       = " + Arrays.toString(zakodowane));
                double[] yDataASK = Help.modulujASK(fs, tc, zakodowane, W, A1, A2);

                double[] xDataASK = Help.generujXdata(fs, tc);
                double[] szumASK = new double[N];
                if (bialy) {
                    for (int i = 0; i < N; i++) {
                        szumASK[i] = 20*random.nextGaussian();
                    }

                    for (int i = 0; i < N; i++) {
                        yDataASK[i] += alpha * szumASK[i];
                    }
                }

                if (tlumienie) {
                    for (int i = 0; i < N; i++) {
                        yDataASK[i] *= Math.exp(-beta * i / (double) N);
                    }
                }

                int[] demodulowaneBityASK = Help.demodulujASK(fs, tc, zakodowane, W, A1, A2, yDataASK);
//                System.out.println("bity demodulowane     = " + Arrays.toString(demodulowaneBityASK));
                int[] dekodowaneBityASK = dekodujTablice(demodulowaneBityASK);
//                System.out.println("bity dekodowane       = " + Arrays.toString(dekodowaneBityASK));
                double berASK = wskaznikBer(dane, dekodowaneBityASK);
                System.out.println("Wskaźnik BER wynosi   = " + berASK + "%");
//                XYChart chartASK = QuickChart.getChart("ASK z szumem białym", "czas", "amplituda", "yData", xDataASK, yDataASK);
//                new SwingWrapper<>(chartASK).displayChart();
                break;
            case 2:
//                System.out.println("PSK");
                double[] yDataPSK = Help.modulujPSK(fs, tc, zakodowane, W);
                double[] xDataPSK = Help.generujXdata(fs, tc);
//                System.out.println("bity wejściowe        = " + Arrays.toString(dane));
//                System.out.println("bity zakodowane       = " + Arrays.toString(zakodowane));

                if(bialy) {
                    double[] szumPSK = new double[N];
                    for (int i = 0; i < N; i++) {
                        szumPSK[i] = 20*random.nextGaussian();
                    }

                    for (int i = 0; i < N; i++) {
                        yDataPSK[i] += alpha * szumPSK[i];
                    }
                }

                int[] demodulowaneBityPSK = Help.demodulujPSK(fs, tc, zakodowane, W, yDataPSK);
//                System.out.println("bity demodulowane     = " + Arrays.toString(demodulowaneBityPSK));
                int[] dekodowaneBityPSK = dekodujTablice(demodulowaneBityPSK);
//                System.out.println("bity dekodowane       = " + Arrays.toString(dekodowaneBityPSK));
                double berPSK = wskaznikBer(dane, dekodowaneBityPSK);
                System.out.println("Wskaźnik BER wynosi = " + berPSK + "%");
//                XYChart chartPSK = QuickChart.getChart("PSK z szumem białym", "czas", "amplituda", "yData", xDataPSK, yDataPSK);
//                new SwingWrapper<>(chartPSK).displayChart();
                break;
            case 3:
//                System.out.println("FSK");
                double[] yDataFSK = Help.modulujFSK(fs, tc, zakodowane, W);
                double[] xDataFSK = Help.generujXdata(fs, tc);
//                System.out.println("bity wejściowe        = " + Arrays.toString(dane));
//                System.out.println("bity zakodowane       = " + Arrays.toString(zakodowane));

                if(bialy) {
                    double[] szumFSK = new double[N];
                    for (int i = 0; i < N; i++) {
                        szumFSK[i] = 20 * random.nextGaussian();
                    }

                    for (int i = 0; i < N; i++) {
                        yDataFSK[i] += alpha * szumFSK[i];
                    }
                }

                int[] demodulowaneBityFSK = Help.demodulujFSK(fs, tc, zakodowane, W, yDataFSK);
//                System.out.println("bity demodulowane     = " + Arrays.toString(demodulowaneBityFSK));
                int[] dekodowaneBityFSK = dekodujTablice(demodulowaneBityFSK);
//                System.out.println("bity dekodowane       = " + Arrays.toString(dekodowaneBityFSK));
                double berFSK = wskaznikBer(dane, dekodowaneBityFSK);
                System.out.println("Wskaźnik BER wynosi = " + berFSK + "%");
//                XYChart chartFSK = QuickChart.getChart("FSK z szumem białym", "czas", "amplituda", "yData", xDataFSK, yDataFSK);
//                new SwingWrapper<>(chartFSK).displayChart();
                break;
            default:
                System.out.println("Błędny wybór.");
                break;
        }
        
    }

    //zad4
    public static void eksperyment(int n, int fs, int tc, int W, double A1, double A2, int wybor) {
        double alpha = 0;
        double beta = 0;
        for (int i = 0; i < 11; i++) {
            System.out.println("alpha = " + alpha + " beta = " + beta);
            systemTransmisyjnySzum(n, fs, tc, W, A1, A2, wybor, true, alpha, true, beta);
            alpha += 0.1;
            beta += 1;
        }
    }


}

