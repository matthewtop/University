package App.Model;

import App.View.Messages.Errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EwidencjaPracownikow implements Serializable{
    private final ArrayList<Pracownik> pracownicy = new ArrayList<>();


    @Override
    public String toString() {
        return "EwidencjaPracownikow{" +
                "pracownicy=" + pracownicy +
                '}';
    }
    public void dodajPracownika(Pracownik pracownik) {
        pracownicy.add(pracownik);
    }

    public List<Pracownik> pobierzListePracownikow() {
        return pracownicy;
    }

    public Pracownik pobierzPracownika(String pesel) {
        try {
            for (Pracownik pracownik : pracownicy) {
                if (pracownik.getPesel().equals(pesel)) {
                    return pracownik;
                }
            }
        } catch (Exception e) {
            Errors.pracownikNieIstniejeError();
        }
        return null;
    }

    public boolean usunPracownika(String pesel) {
        Iterator<Pracownik> iterator = pracownicy.iterator();
        while (iterator.hasNext()) {
            Pracownik pracownik = iterator.next();
            if (pracownik.getPesel().equals(pesel)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public void setPracownicy(List<Pracownik> pracownicy) {
        this.pracownicy.clear();
        this.pracownicy.addAll(pracownicy);
    }

}
