package View;

public class Menus {
    public static void showMainMenu() {
        System.out.println("|===================================================|");
        System.out.println("|                   Wybierz opcje:                  |");
        System.out.println("|===================================================|");
        System.out.println("|         1. Wprowadz macierze wejsciowe            |");
        System.out.println("|         2. Operacje arytmetyczne                  |");
        System.out.println("|         3. Wyswietl macierze                      |");
        System.out.println("|         4. Wyjscie z programu                     |");
        System.out.println("|===================================================|");
        System.out.println();
        System.out.print("Twoj wybor:    ");

    }
    public static void showSecondMenu() {
        System.out.println("|===================================================|");
        System.out.println("|           1. Transponuj macierze wejsciowe        |");
        System.out.println("|           2. Pomnoz macierze wejsciowe            |");
        System.out.println("|           3. Transponuj macierz wynikowa          |");
        System.out.println("|===================================================|");
        System.out.println();
        System.out.print("Twoj wybor:    ");
    }

    public static void showThirdMenu(){
        System.out.println("|===================================================|");
        System.out.println("|   1. Wyswietl macierze wejsciowe                  |");
        System.out.println("|   2. Wyswietl macierz wyjsciowa                   |");
        System.out.println("|   3. Wyswietl transponowane macierze wejsciowe    |");
        System.out.println("|===================================================|");
        System.out.println();
        System.out.print("Twoj wybor:    ");
    }
}
