package App.View;

import App.Model.Dyrektor;
import App.Model.Handlowiec;
import App.Model.Pracownik;
import App.View.Messages.Errors;

import java.util.List;
import java.util.Scanner;

public class ListaPracownikowView {
    private final Scanner scanner;

    public ListaPracownikowView(Scanner scanner){
        this.scanner=scanner;
    }
    public void wyswietlListePracownikow(List<Pracownik> pracownicy) {
        int pozycja = 0;
        int total = pracownicy.size();

        while (pozycja < total) {
            wyswietlDanePracownika(pracownicy.get(pozycja), pozycja + 1, total);

            System.out.println("                            [Enter] – następny");
            System.out.println("                            [Q] – powrót");

            String wybor = scanner.nextLine().toLowerCase();
            if (wybor.equals("q")) {
                break;
            } else if (wybor.isEmpty()) {
                pozycja++;
            } else {
                Errors.zlyWyborError();
            }
        }
    }

    public void wyswietlDanePracownika(Pracownik pracownik, int pozycja, int total) {
        System.out.println("1. Lista pracowników");
        System.out.println();
        System.out.println("Identyfikator PESEL             : " + pracownik.getPesel());
        System.out.println("Imię                            : " + pracownik.getImie());
        System.out.println("Nazwisko                        : " + pracownik.getNazwisko());
        System.out.println("Stanowisko                      : " + getStanowisko(pracownik));
        System.out.println("Wynagrodzenie (zł)              : " + pracownik.getWynagrodzenie());
        System.out.println("Telefon służbowy numer          : " + getTelefonSluzbowy(pracownik));
        System.out.println("Dodatek służbowy (zł)           : " + getDodatekSluzbowy(pracownik));
        if (!(pracownik instanceof Handlowiec)) {
            System.out.println("Karta służbowa numer            : " + getKartaSluzbowa(pracownik));
        }
        System.out.println("Limit kosztów/miesiąc (zł)      : " + getLimitKosztow(pracownik));
        System.out.printf("[Pozycja: %d/%d]%n", pozycja, total);
    }

    private String getStanowisko(Pracownik pracownik) {
        if (pracownik instanceof Dyrektor) {
            return "Dyrektor";
        } else if (pracownik instanceof Handlowiec) {
            return "Handlowiec";
        } else {
            return "mozliwosc pozniejszego rozszerzenia programu";         }
    }

    public String getTelefonSluzbowy(Pracownik pracownik) {
        if (pracownik instanceof Dyrektor) {
            return pracownik.getTelefonSluzbowy();
        } else if (pracownik instanceof Handlowiec) {
            return pracownik.getTelefonSluzbowy();
        } else {
            return "Brak informacji";
        }
    }

    private String getDodatekSluzbowy(Pracownik pracownik) {
        if (pracownik instanceof Dyrektor) {
            return pracownik.getDodatekSluzbowy().toString();
        } else if (pracownik instanceof Handlowiec) {
            return ((Handlowiec) pracownik).getProwizja().toString();
        } else {
            return "Brak informacji";
        }
    }

    private String getKartaSluzbowa(Pracownik pracownik) {
        if (pracownik instanceof Dyrektor) {
            return pracownik.getKartaSluzbowaNumer();
        } else if (pracownik instanceof Handlowiec) {
            return "Brak informacji";
        } else {
            return "Brak informacji";
        }
    }

    private String getLimitKosztow(Pracownik pracownik) {
        if (pracownik instanceof Dyrektor) {
            return String.valueOf(pracownik.getLimitKosztow());
        } else if (pracownik instanceof Handlowiec) {
            int limitProwizji = ((Handlowiec) pracownik).getLimitProwizji();
            return limitProwizji != 0 ? String.valueOf(limitProwizji) : "Brak informacji";
        } else {
            return "Brak informacji";
        }
    }
}
