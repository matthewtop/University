package App.Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Dyrektor extends Pracownik implements Serializable {
    private final String telefonSluzbowy;
    private final String kartaSluzbowaNumer;
    private final BigDecimal dodatekSluzbowy;
    private final int limitKosztow;


    public Dyrektor(String pesel, String imie, String nazwisko, int wynagrodzenie,
                    String telefonSluzbowy, BigDecimal dodatekSluzbowy, String kartaSluzbowaNumer, int limitKosztow) {
        super(pesel, imie, nazwisko, wynagrodzenie);
        this.telefonSluzbowy = telefonSluzbowy;
        this.kartaSluzbowaNumer = kartaSluzbowaNumer;
        this.dodatekSluzbowy = dodatekSluzbowy;
        this.limitKosztow= limitKosztow;
    }
    @Override
    public String toString() {
        return String.format("Dyrektor: %s %s, PESEL: %s, Wynagrodzenie: %d, Telefon służbowy: %s",
                getImie(), getNazwisko(), getPesel(), getWynagrodzenie(), getTelefonSluzbowy());
    }

    @Override
    public String getTelefonSluzbowy() {
        return telefonSluzbowy;
    }

    @Override
    public BigDecimal getDodatekSluzbowy() {
        return dodatekSluzbowy;
    }

    @Override
    public String getKartaSluzbowaNumer() {
        return kartaSluzbowaNumer;
    }

    @Override
    public int getLimitKosztow() {
        return limitKosztow;
    }

}
