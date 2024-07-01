package App.Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Handlowiec extends Pracownik implements Serializable {
    private final int limitProwizji;
    private final String telefonSluzbowy;
    private final BigDecimal stawkaProwizji;

    public Handlowiec(String pesel, String imie, String nazwisko, int wynagrodzenie,
                      String telefonSluzbowy, BigDecimal stawkaProwizji, int limitProwizji) {
        super(pesel, imie, nazwisko, wynagrodzenie);
        this.limitProwizji = limitProwizji;
        this.telefonSluzbowy= telefonSluzbowy;
        this.stawkaProwizji= stawkaProwizji;
    }

    @Override
    public String getTelefonSluzbowy() {return telefonSluzbowy;}

    @Override
    public BigDecimal getDodatekSluzbowy() {
        return null;
    }

    @Override
    public String getKartaSluzbowaNumer() {
        return null;
    }

    @Override
    public int getLimitKosztow() {
        return 0;
    }

    public BigDecimal getProwizja() {return stawkaProwizji;}
    public int getLimitProwizji() {
        return limitProwizji;
    }


}
