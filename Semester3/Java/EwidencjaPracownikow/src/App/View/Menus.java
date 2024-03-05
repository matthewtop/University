package App.View;

public class Menus {
    public static void mainMenu(){
        System.out.println("MENU");
        System.out.println("   1.  Lista pracowników");
        System.out.println("   2.  Dodaj pracownika");
        System.out.println("   3.  Usuń pracownika");
        System.out.println("   4.  Kopia zapasowa");
        System.out.println("   5.  Koniec pracy programu");
        System.out.println();
        System.out.print("Wybór> ");
    }

    public static void dodajPracownikaMenu(){
        System.out.println("2. Dodaj pracownika");
        System.out.print(" [D]yrektor/[H]andlowiec:     ");
    }

    public static void przerywnik(){
        System.out.println("---------------------------------------------");
    }

    public static void kopiaZapasowaMenu(){
        System.out.println("4.  Kopia zapasowa");
        System.out.println();
        System.out.print("[Z]achowaj/[O]dtwórz        :       ");
    }
    public static void wyborKompresji(){
        System.out.print("Kompresja [G]zip/[Z]ip      :       ");
    }
}
