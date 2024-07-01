package App.Controller.FunctionControllers;

import App.Model.EwidencjaPracownikow;
import App.View.Messages.Errors;
import App.View.InputGetters;
import App.View.ListaPracownikowView;
import App.View.Messages.Infos;

import java.util.Scanner;

public class UsunPracownikaController {
    private final EwidencjaPracownikow ewidencjaPracownikow;
    private final ListaPracownikowView listaPracownikowView;
    private final Scanner scanner;

    public UsunPracownikaController(Scanner scanner, EwidencjaPracownikow ewidencjaPracownikow, ListaPracownikowView listaPracownikowView) {
        this.scanner=scanner;
        this.ewidencjaPracownikow = ewidencjaPracownikow;
        this.listaPracownikowView = listaPracownikowView;
    }

    public void usunPracownika() {
        try {
            InputGetters.getPesel();
            String peselToRemove = scanner.nextLine();

            var pracownik = ewidencjaPracownikow.pobierzPracownika(peselToRemove);

            if (pracownik != null) {
                listaPracownikowView.wyswietlDanePracownika(pracownik, 1, 1);

                System.out.println("[Enter] – potwierdź");
                System.out.println("[Q] – porzuć");

                String wybor = scanner.nextLine().toLowerCase();
                if (wybor.equals("q")) {
                    Infos.operacjaAnulowanaInfo();
                } else if (wybor.isEmpty()) {
                    if (ewidencjaPracownikow.usunPracownika(peselToRemove)) {
                        Infos.udaloSieUsunacPracownikaInfo();
                    } else {
                        Errors.nieUdaloSieUsunacPracownikaError();
                    }
                } else {
                    Errors.zlyWyborError();
                }
            } else {
                Errors.pracownikNieIstniejeError();
            }
        } catch (Exception e) {
            Errors.bladPodczasUsuwania();
        }
    }
}
