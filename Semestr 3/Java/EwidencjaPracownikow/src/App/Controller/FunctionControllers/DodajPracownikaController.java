package App.Controller.FunctionControllers;

import App.Model.Dyrektor;
import App.Model.EwidencjaPracownikow;
import App.Model.Handlowiec;
import App.Utils.PeselValidator;
import App.View.InputGetters;
import App.View.Menus;
import App.View.Messages.Errors;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Scanner;

public class DodajPracownikaController {
    private final Scanner scanner;
    private final EwidencjaPracownikow ewidencjaPracownikow;
    private final PeselValidator peselValidator;
    private final HashSet<String> pesels = new HashSet<>();

    public DodajPracownikaController(Scanner scanner, EwidencjaPracownikow ewidencjaPracownikow) {
        this.scanner =scanner;
        this.ewidencjaPracownikow= ewidencjaPracownikow;
        this.peselValidator = new PeselValidator(pesels);
    }

    public void dodajPracownika(){
        Menus.dodajPracownikaMenu();
        String wybor=scanner.nextLine().toLowerCase();
        switch (wybor){
            case "d":
                dodajDyrektora();
                break;
            case "h":
                dodajHandlowca();
                break;
            default:
                Errors.zlyWyborError();
                break;
        }
    }

    public void dodajDyrektora() {
        Menus.przerywnik();
        try {
            String pesel = getPesel();
            String imie = InputGetters.getImie();
            String nazwisko = InputGetters.getNazwisko();
            int wynagrodzenie = InputGetters.getWynagrodzenie();
            String telefonSluzbowy = InputGetters.getTelefonSluzbowy();
            BigDecimal dodatekSluzbowy = InputGetters.getDodatekSluzbowy();
            String kartaSluzbowaNumer = InputGetters.getKartaSluzbowa();
            int limitKosztow = InputGetters.getLimitKosztow();

            Dyrektor dyrektor = new Dyrektor(pesel, imie, nazwisko, wynagrodzenie, telefonSluzbowy, dodatekSluzbowy, kartaSluzbowaNumer, limitKosztow);
            ewidencjaPracownikow.dodajPracownika(dyrektor);
            pesels.add(pesel);
        } catch (Exception e) {
            Errors.zlyPeselError();
        }
        Menus.przerywnik();
    }

    public void dodajHandlowca() {
        Menus.przerywnik();
        try {
            String pesel = getPesel();
            String imie = InputGetters.getImie();
            String nazwisko = InputGetters.getNazwisko();
            int wynagrodzenie = InputGetters.getWynagrodzenie();
            String telefonSluzbowy = InputGetters.getTelefonSluzbowy();
            BigDecimal prowizja = InputGetters.getProwizja();
            int limitProwizji = InputGetters.getLimitProwizji();

            Handlowiec handlowiec = new Handlowiec(pesel, imie, nazwisko, wynagrodzenie, telefonSluzbowy, prowizja, limitProwizji);
            ewidencjaPracownikow.dodajPracownika(handlowiec);
            pesels.add(pesel);
        } catch (Exception e) {
            Errors.zlyPeselError();
        }
        Menus.przerywnik();
    }

    public String getPesel() {
        String pesel;
        do {
            InputGetters.getPesel();
            pesel = scanner.next();
            if (!peselValidator.validatePesel(pesel)) {
                Errors.zlyPeselError();
            }
        } while (!peselValidator.validatePesel(pesel));
        return pesel;
    }
}
