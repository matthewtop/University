package Lab3;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.Arrays;

public class kod {
    public static void main(String[] args) {

        pierwszweZA(10000,2.0,3,20,0.5,"za_a");
        pierwszweZA(10000,2.0,3,20,8,"za_b");
        pierwszweZA(10000,2.0,3,20,40,"za_c");

        pierwszeZP(10000,2.0,3,20,0.5,"zp_a");
        pierwszeZP(10000,2.0,3,20,2.14,"zp_b");
        pierwszeZP(10000,2.0,3,20,3*Math.PI,"zp_c");

        pierwszeZF(10000,2.0,3,20,0.5,"zf_a");
        pierwszeZF(10000,2.0,3,20,2.99,"zf_b");
        pierwszeZF(10000,2.0,3,20,9,"zf_c");

        widmoZA(10000,2.0,3,20,0.5,"za_a_widmo");
        widmoZA(10000,2.0,3,20,8,"za_b_widmo");
        widmoZA(10000,2.0,3,20,40,"za_c_widmo");

        widmoZP(10000,2.0,3,20,0.5,"zp_a_widmo");
        widmoZP(10000,2.0,3,20,2.14,"zp_b_widmo");
        widmoZP(10000,2.0,3,20,3*Math.PI,"zp_c_widmo");

        widmoZF(10000,2.0,3,20,0.5,"zf_a_widmo");
        widmoZF(10000,2.0,3,20,2.99,"zf_b_widmo");
        widmoZF(10000,2.0,3,20,9,"zf_c_widmo");

    }
    //m(t) = sin(2Pifmt)
    public static void widmoZA(int fs, double tc, double fm, double fn, double ka, String nazwa) {
        int N = (int) (fs * tc);
        int paddedN = Integer.highestOneBit(N - 1) << 1; //dla biblioteki fft apache math3 potrzebna jest potega lczby 2 dlatego szukam liczby najblizszej
        double[] xData = new double[paddedN];
        double[] yData = new double[paddedN];
        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            xData[i] = t;
            yData[i] = zA(fm, fn, t, ka);
//            yData[i]=Math.sin(2*Math.PI*1000*t);
        }
        // padding dzieki ktoremu obliczenia z fft beda dokladniejsze
        Arrays.fill(yData, N, paddedN, 0);

        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] transformedComplex = fft.transform(yData, TransformType.FORWARD);
        double[] transformed = new double[paddedN];
        for (int i = 0; i < paddedN; i++) {
            transformed[i] = transformedComplex[i].abs(); // modul z
        }

        double[] widmo = widmo(transformed);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosc = skalaCzestotliwosci(fs, widmo.length);

        szerokoscPasma(widmo, -3.0, db);
        szerokoscPasma(widmo, -6.0, db);
        szerokoscPasma(widmo, -12.0, db);



        XYChart chart = QuickChart.getChart("Widmo zA(t) fm=" + fm + ", fn=" + fn + ", ka=" + ka, "Częstotliwość", "Amplituda", nazwa, czestotliwosc, db);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart,"src/Lab3/"+nazwa,BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void szerokoscPasma(double[] widmo, double progDB, double[] skaladb) {
        double maxVal = Arrays.stream(skaladb).max().getAsDouble();
        double prog = Math.pow(10, (maxVal + progDB) / 10);
        int start = 0;
        int end = 0;
        for (int i = 0; i < widmo.length; i++) {
            if (widmo[i] > prog) {
                start = i;
                break;
            }
        }
        for (int i = widmo.length - 1; i >= 0; i--) {
            if (widmo[i] > prog) {
                end = i;
                break;
            }
        }
        System.out.println("Szerokość pasma dla progu " + progDB + "dB: " + (end - start) + "Hz");

    }

    public static void widmoZP(int fs, double tc, double fm, double fn, double kp, String nazwa){
        int N = (int) (fs * tc);
        int paddedN = Integer.highestOneBit(N - 1) << 1;
        double[] xData = new double[paddedN];
        double[] yData = new double[paddedN];
        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            xData[i] = t;
            yData[i] = zP(fm, fn, t, kp);
        }
        Arrays.fill(yData, N, paddedN, 0);
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] transformedComplex = fft.transform(yData, TransformType.FORWARD);
        double[] transformed = new double[paddedN];
        for (int i = 0; i < paddedN; i++) {
            transformed[i] = transformedComplex[i].abs();
        }

        double[] widmo = widmo(transformed);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosc = skalaCzestotliwosci(fs, widmo.length);

        szerokoscPasma(widmo, -3.0, db);
        szerokoscPasma(widmo, -6.0, db);
        szerokoscPasma(widmo, -12.0, db);

        XYChart chart = QuickChart.getChart("Widmo zP(t) fm=" + fm + ", fn=" + fn + ", kp=" + kp, "Częstotliwość", "Amplituda", nazwa, czestotliwosc, db);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart,"src/Lab3/"+nazwa,BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void widmoZF(int fs, double tc, double fm, double fn, double kf, String nazwa){
        int N = (int) (fs * tc);
        int paddedN = Integer.highestOneBit(N - 1) << 1;
        double[] xData = new double[paddedN];
        double[] yData = new double[paddedN];
        for (int i = 0; i < N; i++) {
            double t = (double) i / fs;
            xData[i] = t;
            yData[i] = zF(fm, fn, t, kf);
        }
        Arrays.fill(yData, N, paddedN, 0);
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] transformedComplex = fft.transform(yData, TransformType.FORWARD);
        double[] transformed = new double[paddedN];
        for (int i = 0; i < paddedN; i++) {
            transformed[i] = transformedComplex[i].abs();
        }

        double[] widmo = widmo(transformed);
        double[] db = skalaDecybelowa(widmo);
        double[] czestotliwosc = skalaCzestotliwosci(fs, widmo.length);

        szerokoscPasma(widmo, -3.0, db);
        szerokoscPasma(widmo, -6.0, db);
        szerokoscPasma(widmo, -12.0, db);

        XYChart chart = QuickChart.getChart("Widmo zF(t) fm=" + fm + ", fn=" + fn + ", kf=" + kf, "Częstotliwość", "Amplituda", nazwa, czestotliwosc, db);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart,"src/Lab3/"+nazwa,BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static double[] widmo(double[] a) {
        int N = a.length;
        double[] widmo = new double[N / 2];
        for (int i = 0; i < N / 2; i++) {
            widmo[i] = Math.abs(a[i]);
        }
        return widmo;
    }
    public static double[] skalaDecybelowa(double[] widmo) {
        double[] db = new double[widmo.length/2];
        for (int i = 0; i < widmo.length/2; i++) {
            db[i] = 10 * Math.log10(widmo[i]);
        }
        return db;
    }
    public static double[] skalaCzestotliwosci(int fs, int widmoLength) {
        double[] czestotliwosci = new double[widmoLength/2];
        for (int i = 0; i < widmoLength/2; i++) {
            czestotliwosci[i] = i * ( fs / (widmoLength*2.0));
        }
        return czestotliwosci;
    }
    public static void pierwszweZA(int fs, double tc,double fm, double fn, double ka, String nazwa){
        int N = (int)(fs*tc);
        double[] xData = new double[N];
        double[] yData = new double[N];
        for (int i = 0; i < N; i++) {
            double t = (double) i /fs;
            xData[i]=t;
            yData[i] =zA(fm,fn,t,ka);
        }
        XYChart chart = QuickChart.getChart("zA(t) fm="+fm+", fn="+fn+", ka="+ka,"Czas","Amplituda",nazwa,xData,yData);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart,"src/Lab3/"+nazwa,BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void pierwszeZP(int fs, double tc, double fm, double fn, double kp, String nazwa){
        int N = (int)(fs*tc);
        double[] xData = new double[N];
        double[] yData = new double[N];

        for (int i = 0; i < N; i++) {
            double t = (double) i /fs;
            xData[i]=t;
            yData[i] =zP(fm,fn,t,kp);
        }

        XYChart chart = QuickChart.getChart("zP(t) fm="+fm+", fn="+fn+", ka="+kp,"Czas","Amplituda",nazwa,xData,yData);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart,"src/Lab3/"+nazwa,BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void pierwszeZF(int fs, double tc, double fm, double fn, double kf, String nazwa){
        int N = (int)(fs*tc);
        double[] xData = new double[N];
        double[] yData = new double[N];

        for (int i = 0; i < N; i++) {
            double t = (double) i /fs;
            xData[i]=t;
            yData[i] =zF(fm,fn,t,kf);
        }

        XYChart chart = QuickChart.getChart("zF(t) fm="+fm+", fn="+fn+", ka="+kf,"Czas","Amplituda",nazwa,xData,yData);
        new SwingWrapper<>(chart).displayChart();

        try{
            BitmapEncoder.saveBitmap(chart,"src/Lab3/"+nazwa,BitmapEncoder.BitmapFormat.PNG);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static double zA(double fm, double fn, double t,double ka){
        double mt = Math.sin(2*Math.PI*fm*t);
        return (ka * mt + 1) * Math.cos(2 * Math.PI * fn * t);
    }
    public static double zP(double fm,double fn,double t,double kp){
        double mt = Math.sin(2*Math.PI*fm*t);
        return Math.cos(2*Math.PI*fn*t+kp*mt);
    }
    public static double zF(double fm, double fn,double t,double kf){
        double mt = Math.sin(2*Math.PI*fm*t);
        return Math.cos(2*Math.PI*fn*t+ (kf/fm)*mt);
    }


}
