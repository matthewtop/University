//(n,k) -> 7 bitow na wyjsciu
//n-k = 3 -> liczba bitów nadmiarowych, mają wagi, pozycje i sa potegami liczby 2, czyli 3 pierwsze potegi liczby 2
// 0101 -> bity informacyjne
// po zakodowaniu bedzie 7 bitow

//x1,x2,x3,x4,x5,x6,x7
//p  p  0  p  1   0 1         p = bity parzystosci


import java.util.Arrays;

public class kod {
    public static void main(String[] args) {
        int[] dane74={0,1,0,1};
        System.out.println("Kod wejsciowy   = " + Arrays.toString(dane74));
        int[] kod = koder7_4(dane74);
        System.out.println("Kod hamminga    = " + Arrays.toString(kod));
        int[] dekod = dekoder7_4(kod);
        System.out.println("dekod           = " + Arrays.toString(dekod));
        kod[3]^=1;
        System.out.println("kod z bledem    = " +Arrays.toString(kod));
        System.out.println("Odkodowane      = " +Arrays.toString(dekoder7_4(kod)));

        System.out.println("\nHamming(15,11)");
        int[] dane1511 = {1,1,0,1,1,0,1,0,1,0,1};
        System.out.println("Kod wejsciowy   =   " + Arrays.toString(dane1511));
        int[] kod1511 = koder15_11(dane1511);
        System.out.println("Kod hamminga    =   " + Arrays.toString(kod1511));
        int[] dekod1511 = dekoder15_11(kod1511);
        System.out.println("dekod           =   " +Arrays.toString(dekod1511));
        kod1511[6]^=1;
        System.out.println("Kod z bledem    =   "+Arrays.toString(kod1511));
        System.out.println("Odkodowane      =   "+Arrays.toString(dekoder15_11(kod1511)));
    }

    public static int[] koder7_4(int[] dane) {
        int[] wynik = new int[7];
        wynik[0] = dane[0] ^ dane[1] ^ dane[3];
        wynik[1] = dane[0] ^ dane[2] ^ dane[3];
        wynik[2] = dane[0];
        wynik[3] = dane[1] ^ dane[2] ^ dane[3];
        wynik[4] = dane[1];
        wynik[5] = dane[2];
        wynik[6] = dane[3];
        return wynik;
    }

    public static int[] dekoder7_4(int[] kod) {
        int n_x1 = kod[2]^kod[4]^kod[6];
        int n_x2 = kod[2]^kod[5]^kod[6];
        int n_x4 = kod[4]^kod[5]^kod[6];

        int n2_x1=kod[0]^n_x1;
        int n2_x2=kod[1]^n_x2;
        int n2_x4=kod[3]^n_x4;

        int syndrom= (n2_x1+n2_x2*2+n2_x4*4);

        if (syndrom != 0) {
            System.out.println("blad na pozycji: " + syndrom);
            kod[syndrom - 1] ^= 1;
        }
        return new int[]{kod[2], kod[4], kod[5], kod[6]};
    }

    // | laczenie, konkatenacja tutaj laczenie w poziomie

    //p = parzystosc
    // = jednostkowa rzedu k [11x11]

    //parzystosc 1,2,4 i 8
    //modulo2 x1= suma tam gdzie wystepuje 1

    //wektor tam gdzie 1

    public static int[] koder15_11(int[] signal) {
        int[][] P = genP();

        int k = signal.length;
        int[][] I = genI(k);
        int[][] G = new int[k][15];

        for (int i = 0; i < k; i++) {
            System.arraycopy(P[i], 0, G[i], 0, 4);
            System.arraycopy(I[i], 0, G[i], 4, k);
        }

        int[] kod = new int[15];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < 15; j++) {
                kod[j] ^= signal[i] * G[i][j];
            }
        }

        for (int i = 0; i < 15; i++) {
            kod[i] %= 2;
        }

        return kod;
    }

    public static int[][] genI(int k) {
        int[][] I = new int[k][k];
        for (int i = 0; i < k; i++) {
            I[i][i] = 1;
        }
        return I;
    }

    public static int[][] genP() {
        int[][] P = new int[11][4];
        int[][] preP = {
                {1, 1, 0, 0},//3
                {1, 0, 1, 0},//5
                {0, 1, 1, 0},//6
                {1, 1, 1, 0},//7
                {1, 0, 0, 0},//9
                {0, 1, 0, 1},//10
                {1, 1, 0, 1},//11
                {0, 0, 1, 1},//12
                {1, 0, 1, 1},//13
                {0, 1, 1, 1},//14
                {1, 1, 1, 1} //15
        };

        for (int i = 0; i < 11; i++) {
            System.arraycopy(preP[i], 0, P[i], 0, 4);
        }
        return P;
    }

    public static int[] dekoder15_11(int[] signal) {
        int[][] P = genP();

        int[][] PT = new int[4][11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 4; j++) {
                PT[j][i] = P[i][j];
            }
        }

        int[][] H = new int[15][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 11; j++) {
                H[j + 4][i] = PT[i][j];
            }
        }

        for (int i = 0; i < 4; i++) {
            H[i][i] = 1;
        }

        int[] s = new int[4];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 4; j++) {
                s[j] ^= signal[i] * H[i][j];
            }
        }

        for (int i = 0; i < 4; i++) {
            s[i] %= 2;
        }

        int syndrom = s[0] + s[1] * 2 + s[2] * 4 + s[3] * 8;
        if (syndrom != 0) {
            System.out.println("blad na pozycji: " + syndrom);
            signal[syndrom] ^= 1;
        }

        return Arrays.copyOfRange(signal, 4, 15);
    }
}






