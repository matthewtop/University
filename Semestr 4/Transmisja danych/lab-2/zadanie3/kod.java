package zadanie3;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;



public class kod {
    public static void main(String[] args) {
        drugiey();
        drugiez();
        drugiev();
        trzecieu();
        czwarte1();
        czwarte2();
        czwarte3();
    }

    public static void pierwsze(){
        double[] t = linspace(0, 5, 128); //na potrzeby wnioskow do fft potrzebna jest potega 2 wiec czasy fft beda podane dla N=128
        double[] x = new double[t.length];
        int fs = 15000;
        for (int i = 0; i < t.length; i++) {
            x[i] = liczZad1(10, t[i], 0);
        }
        long startTime = System.nanoTime();

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);

        Complex[] transformed = transformer.transform(x, TransformType.FORWARD);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Czas wykonania FFT: " + duration + " ns");

//        long startTime = System.nanoTime();
        double[][] wynikDFT = dft(x);
//        long endTime = System.nanoTime();
        System.out.println("Czas wykonania DFT: " + (endTime - startTime) + " ns");
        double[] a = wynikDFT[0];
        double[] bi = wynikDFT[1];

        double[] widmo = widmo(a, bi);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosc = skalaCzestotliwosci(t.length, fs);

        XYChart chart = QuickChart.getChart("Widmo amplitudowe x(t)", "czestotliwosc w Hz", "amplituda w dB", "x(t)", czestotliwosc, db);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart, "./src/zadanie3/x", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void drugiey(){
        double[] t = linspace(0, 5, 128);
        double[] x = new double[t.length];
        int fs = 15000;
        for (int i = 0; i < t.length; i++) {
            x[i] = liczZad2y(10, t[i], 100);
        }

        long startTime = System.nanoTime();

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);

        Complex[] transformed = transformer.transform(x, TransformType.FORWARD);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Czas wykonania FFT: " + duration + " ns");
        double[][] wynikDFT = dft(x);
        double[] a = wynikDFT[0];
        double[] bi = wynikDFT[1];

        double[] widmo = widmo(a, bi);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosc = skalaCzestotliwosci(t.length, fs);

        XYChart chart = QuickChart.getChart("Widmo amplitudowe y(t)", "czestotliwosc w Hz", "amplituda w dB", "y(t)", czestotliwosc, db);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart, "./src/zadanie3/y", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void drugiez(){
        double[] t = linspace(0, 5, 128);
        double[] x = new double[t.length];
        int fs = 15000;
        for (int i = 0; i < t.length; i++) {
            x[i] = liczZad2z(10, t[i], 0);
        }

        long startTime = System.nanoTime();

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);

        Complex[] transformed = transformer.transform(x, TransformType.FORWARD);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Czas wykonania FFT: " + duration + " ns");
        double[][] wynikDFT = dft(x);
        double[] a = wynikDFT[0];
        double[] bi = wynikDFT[1];

        double[] widmo = widmo(a, bi);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosc = skalaCzestotliwosci(t.length, fs);

        XYChart chart = QuickChart.getChart("Widmo amplitudowe z(t)", "czestotliwosc w Hz", "amplituda w dB", "z(t)", czestotliwosc, db);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart, "./src/zadanie3/z", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void drugiev(){
        double[] t = linspace(0, 5, 128);
        double[] x = new double[t.length];
        int fs = 15000;
        for (int i = 0; i < t.length; i++) {
            x[i] = liczZad2v(10, t[i], 0);
        }

        long startTime = System.nanoTime();

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);

        Complex[] transformed = transformer.transform(x, TransformType.FORWARD);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Czas wykonania FFT: " + duration + " ns");
        double[][] wynikDFT = dft(x);
        double[] a = wynikDFT[0];
        double[] bi = wynikDFT[1];

        double[] widmo = widmo(a, bi);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosc = skalaCzestotliwosci(t.length, fs);

        XYChart chart = QuickChart.getChart("Widmo amplitudowe v(t)", "czestotliwosc w Hz", "amplituda w dB", "v(t)", czestotliwosc, db);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart, "./src/zadanie3/v", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void trzecieu(){
        double[] t = linspace(0, 5, 128);
        double[] x = new double[t.length];
        int fs = 15000;
        for (int i = 0; i < t.length; i++) {
            x[i] = liczZad3(t[i]);
        }

        long startTime = System.nanoTime();

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);

        Complex[] transformed = transformer.transform(x, TransformType.FORWARD);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Czas wykonania FFT: " + duration + " ns");
        double[][] wynikDFT = dft(x);
        double[] a = wynikDFT[0];
        double[] bi = wynikDFT[1];

        double[] widmo = widmo(a, bi);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosc = skalaCzestotliwosci(t.length, fs);

        XYChart chart = QuickChart.getChart("Widmo amplitudowe u(t)", "czestotliwosc w Hz", "amplituda w dB", "u(t)", czestotliwosc, db);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart, "./src/zadanie3/u", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void czwarte1(){
        double[] t = linspace(0, 5, 128);
        double[] x = new double[t.length];
        int fs = 22050;
        for(int i = 0; i< t.length;i++){
            x[i] = Math.sin(1*Math.PI*t[i])/2+Math.cos(2*1*Math.PI*t[i]);
        }

        long startTime = System.nanoTime();

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);

        Complex[] transformed = transformer.transform(x, TransformType.FORWARD);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Czas wykonania FFT: " + duration + " ns");
        double[][] wynikDFT = dft(x);
        double[] a = wynikDFT[0];
        double[] bi = wynikDFT[1];

        double[] widmo = widmo(a, bi);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosc = skalaCzestotliwosci(t.length, fs);
        XYChart chart = QuickChart.getChart("Widmo amplitudowe b1(t)", "czestotliwosc w Hz", "amplituda w dB", "b1(t)", czestotliwosc, db);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart, "./src/zadanie3/b1", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void czwarte2(){
        double[] t = linspace(0, 5, 128);
        double[] x = new double[t.length];
        int fs = 15000;
        for(int i = 0; i< t.length;i++){
            x[i] = Math.sin(2*Math.PI*t[i])/2+Math.cos(2*2*Math.PI*t[i]);
        }

        long startTime = System.nanoTime();

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);

        Complex[] transformed = transformer.transform(x, TransformType.FORWARD);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Czas wykonania FFT: " + duration + " ns");
        double[][] wynikDFT = dft(x);
        double[] a = wynikDFT[0];
        double[] bi = wynikDFT[1];

        double[] widmo = widmo(a, bi);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosc = skalaCzestotliwosci(t.length, fs);
        XYChart chart = QuickChart.getChart("Widmo amplitudowe b2(t)", "czestotliwosc w Hz", "amplituda w dB", "b2(t)", czestotliwosc, db);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart, "./src/zadanie3/b2", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void czwarte3(){
        double[] t = linspace(0, 5, 128);
        double[] x = new double[t.length];
        int fs = 22050;
        for(int i = 0; i< t.length;i++){
            x[i] = Math.sin(22*Math.PI*t[i])/2+Math.cos(2*22*Math.PI*t[i]);
        }

        long startTime = System.nanoTime();

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);

        Complex[] transformed = transformer.transform(x, TransformType.FORWARD);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Czas wykonania FFT: " + duration + " ns");
        double[][] wynikDFT = dft(x);
        double[] a = wynikDFT[0];
        double[] bi = wynikDFT[1];

        double[] widmo = widmo(a, bi);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosc = skalaCzestotliwosci(t.length, fs);
        XYChart chart = QuickChart.getChart("Widmo amplitudowe b3(t)", "czestotliwosc w Hz", "amplituda w dB", "b3(t)", czestotliwosc, db);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart, "./src/zadanie3/b3", BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static double[] linspace(double start, double stop, int N) {
        double[] wynik = new double[N];
        double krok = (stop - start) / (N - 1);
        for (int i = 0; i < N; i++) {
            wynik[i] = start + krok * i;
        }
        return wynik;
    }

    public static double[][] dft(double[] x) {
        int N = x.length;
        double[] a = new double[N];
        double[] bi = new double[N];

        for (int k = 0; k < N; k++) {
            double a_k = 0; // Część rzeczywista
            double bi_k = 0; // Część urojona
            for (int n = 0; n < N; n++) {
                double potega = -2 * Math.PI * k * n / N;
                a_k += x[n] * Math.cos(potega);
                bi_k += x[n] * Math.sin(potega);
            }
            a[k] = a_k;
            bi[k] = bi_k;
        }
        return new double[][] { a, bi };
    }

    public static double[] widmo(double[] a, double[] bi){
        int N = a.length/2-1;
        double[]widmo=new double[N];
        for (int i = 0; i < N; i++) {
            widmo[i] = Math.sqrt(a[i]*a[i]+bi[i]*bi[i]);
        }
        return widmo;
    }

    public static double[] skalaDecybelowa(double[] widmo){
        double[] db = new double[widmo.length];
        for (int i = 0; i < widmo.length; i++) {
            db[i] = 10*Math.log10(widmo[i]);
        }
        return db;
    }

    public static double[] skalaCzestotliwosci(int N, int fs){
        int rozmiar = N / 2 - 1;
        double[] czestotliwosci = new double[rozmiar];
        for (int i = 0; i <rozmiar; i++) {
            czestotliwosci[i]= i*fs/N;
        }
        return czestotliwosci;
    }

    public static double liczZad1(double f, double t, double fi){
        //wybrana funkcja: tabela 1, funkcja 1
        return Math.cos(2*Math.PI*f*t+fi) * Math.cos(2.5*Math.pow(t,0.2)*Math.PI);
    }
    public static double liczZad2y(double f, double t, double fi){
        double x = liczZad1(f,t,fi);
        //wybrana funkcja: tabela 2, funkcja 1
        return (x*t)/(3+Math.cos(20*Math.PI*t));
    }
    public static double liczZad2z(double f,double t, double fi){
        double x = liczZad1(f,t,fi);
        double y = liczZad2y(f,t,fi);
        return Math.pow(t,2) * (Math.abs(x * y - ((double) 2 /(10+y) )));
    }
    public static double liczZad2v(double f, double t, double fi){
        double x = liczZad1(f,t,fi);
        double y = liczZad2y(f,t,fi);
        double z = liczZad2z(f,t,fi);
        return Math.pow(z,3) + 3*Math.sin(z*y)*Math.abs(y-x);
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
