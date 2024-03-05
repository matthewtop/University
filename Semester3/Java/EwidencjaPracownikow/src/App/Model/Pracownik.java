package App.Model;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class Pracownik implements Serializable {
    private final String imie;
    private final String nazwisko;
    private final String pesel;
    private final int wynagrodzenie;

    public Pracownik(String pesel, String imie, String nazwisko, int wynagrodzenie) {
        this.pesel = pesel;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.wynagrodzenie = wynagrodzenie;
    }

    @Override
    public String toString() {
        return String.format("%s: %s %s, PESEL: %s, Wynagrodzenie: %d",
                getClass().getSimpleName(), getImie(), getNazwisko(), getPesel(), getWynagrodzenie());
    }

    public String getImie(){return imie;}
    public String getNazwisko(){return nazwisko;}
    public String getPesel(){return pesel;}
    public int getWynagrodzenie(){return wynagrodzenie;}

    public abstract String getTelefonSluzbowy();

    public abstract BigDecimal getDodatekSluzbowy();
    public abstract String getKartaSluzbowaNumer();
    public abstract int getLimitKosztow();
}
