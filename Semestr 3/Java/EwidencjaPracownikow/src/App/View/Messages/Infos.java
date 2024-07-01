package App.View.Messages;

public class Infos {
    public static void udaloSieUsunacPracownikaInfo(){System.out.println("[INFO] Udalo sie usunac pracownika.");}
    public static void operacjaAnulowanaInfo(){
        System.out.println("[INFO] Operacja anulowana.");
    }
    public static void kopiaZapisanaInfo(String nazwaPliku) {System.out.println("[INFO] Kopia zapasowa została zapisana do pliku " + nazwaPliku);}
    public static void kopiaOdczytanaInfo(String nazwaPliku) {System.out.println("[INFO] Odczytano kopię zapasową z pliku " + nazwaPliku);}
}
