package App.View;

import App.Utils.NameAndSurnameValidator;
import App.View.Messages.Errors;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputGetters {
    private static final Scanner scanner = new Scanner(System.in);
    public static void getPesel(){System.out.print("Identyfikator PESEL             : ");}

    public static String getNazwaPliku(){
        System.out.print("Podaj nazwę pliku                   : ");
        return scanner.next();
    }

    public static String getImie() {
        String imie;
        do {
            System.out.print("Imię                            : ");
            imie = scanner.next();
            if (!NameAndSurnameValidator.validateName(imie)) {
                Errors.niepoprawnyFormatError();
            }
        } while (!NameAndSurnameValidator.validateName(imie));
        return imie;
    }

    public static String getNazwisko(){
        String nazwisko;
        do{
            System.out.print("Nazwisko                        : ");
            nazwisko= scanner.next();
            if(!NameAndSurnameValidator.validateSurname(nazwisko)){
                Errors.niepoprawnyFormatError();
            }
        }while (!NameAndSurnameValidator.validateSurname(nazwisko));
        return nazwisko;
    }

    public static int getLimitProwizji() {
        int limitProwizji = 0;
        boolean poprawneDane = false;

        while (!poprawneDane) {
            try {
                System.out.print("Limit prowizji (%)              : ");
                limitProwizji = scanner.nextInt();
                poprawneDane = true;
            } catch (InputMismatchException e) {
                Errors.zlyFormatDanychError();
                scanner.nextLine();
            }
        }
        return limitProwizji;
    }

    public static int getWynagrodzenie() {
        int wynagrodzenie = 0;
        boolean poprawneDane = false;

        while (!poprawneDane) {
            try {
                System.out.print("Wynagrodzenie (zł)              : ");
                wynagrodzenie = scanner.nextInt();
                poprawneDane = true;
            } catch (InputMismatchException e) {
                Errors.zlyFormatDanychError();
                scanner.nextLine();
            }
        }
        return wynagrodzenie;
    }
    public static  BigDecimal getProwizja(){
        BigDecimal prowizja = BigDecimal.valueOf(0);
        boolean poprawneDane = false;

        while (!poprawneDane) {
            try {
                System.out.print("Prowizja                        : ");
                prowizja = BigDecimal.valueOf(scanner.nextInt());
                poprawneDane = true;
            } catch (InputMismatchException e) {
                Errors.zlyFormatDanychError();
                scanner.next();
            }
        }
        return prowizja;
    }

    public static String getTelefonSluzbowy(){
        try {
            System.out.print("Telefon służbowy numer          : ");
            return scanner.next();
        } catch (InputMismatchException e) {
            Errors.zlyFormatDanychError();
            return getTelefonSluzbowy();
        }
    }

    public static BigDecimal getDodatekSluzbowy(){
        BigDecimal dodatekSluzbowy = BigDecimal.valueOf(0);
        boolean poprawneDane = false;

        while (!poprawneDane) {
            try {
                System.out.print("Dodatek służbowy (zł)           : ");
                dodatekSluzbowy= BigDecimal.valueOf(scanner.nextInt());
                poprawneDane= true;
            } catch (InputMismatchException e) {
                Errors.zlyFormatDanychError();
                return BigDecimal.valueOf(scanner.nextInt());
            }
        }
        return dodatekSluzbowy;
    }

    public static String getKartaSluzbowa() {
        try {
            System.out.print("Karta służbowa numer            : ");
            return scanner.next();
        } catch (InputMismatchException e) {
            Errors.zlyFormatDanychError();
            return getKartaSluzbowa();
        }
    }

    public static int getLimitKosztow(){
        int limitKosztow = 0;
        boolean poprawneDane = false;

        while (!poprawneDane) {
            try {
                System.out.print("Limit kosztów/miesiąc (zł)      : ");
                limitKosztow = scanner.nextInt();
                poprawneDane = true;
            } catch (InputMismatchException e) {
                Errors.zlyFormatDanychError();
                return scanner.nextInt();
            }
        }
        return limitKosztow;
    }
}
