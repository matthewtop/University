package zadanie1;

import java.util.Arrays;
import java.util.Random;

public class kod {
    public static double[][] dft(int[] probki) {
        int N = probki.length;
        double[][] wyniki = new double[N][2];

        for (int k = 0; k < N; k++) {
            double a = 0; // Część rzeczywista
            double bi = 0; // Część urojona
            for (int n = 0; n < N; n++) {
                double potega = -2 * Math.PI * k * n / N;
                a += probki[n] * Math.cos(potega);
                bi += probki[n] * Math.sin(potega);
            }
            wyniki[k][0] = a;
            wyniki[k][1] = bi;
        }
        return wyniki;
    }
    public static int[] losoweProbki(int N){
        int[] probki = new int[N];
        Random random = new Random();
        for (int i = 0; i < N; i++) {
            probki[i] = random.nextInt(101);
        }
        return probki;
    }

    public static void main(String[] args) {
        int[] probki = new int[]{3,8,0,-1};
        //int[] probki = losoweProbki(100);
        int N = probki.length;

        double[][] dft = dft(probki);
        System.out.println("DFT: "+ Arrays.deepToString(dft));

    }
}
