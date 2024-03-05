package App.Controller;

import App.Controller.FunctionControllers.DodajPracownikaController;
import App.Controller.FunctionControllers.KopiaZapasowaController;
import App.Controller.FunctionControllers.ListaPracownikowController;
import App.Controller.FunctionControllers.UsunPracownikaController;
import App.Model.EwidencjaPracownikow;
import App.View.Messages.Errors;
import App.View.ListaPracownikowView;
import App.View.Menus;

import java.io.Serializable;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MenuController implements Serializable {
    private final Scanner scanner;
    private DodajPracownikaController dodajPracownikaController;
    private UsunPracownikaController usunPracownikaController;
    private KopiaZapasowaController kopiaZapasowaController;
    private ListaPracownikowController listaPracownikowController;
    private ExecutorService executor;

    public MenuController(Scanner scanner){
        this.scanner = scanner;
        init();
    }

    private void init(){
        try {
            EwidencjaPracownikow ewidencja = new EwidencjaPracownikow();
            ListaPracownikowView listaPracownikowView = new ListaPracownikowView(scanner);

            executor= Executors.newFixedThreadPool(5);

            listaPracownikowController = new ListaPracownikowController(ewidencja, listaPracownikowView);
            dodajPracownikaController = new DodajPracownikaController(scanner, ewidencja);
            usunPracownikaController = new UsunPracownikaController(scanner, ewidencja, listaPracownikowView);
            kopiaZapasowaController = new KopiaZapasowaController(scanner, ewidencja, executor);
        } catch (Exception e) {
            Errors.bladInicjalizacjiError();
        }
    }

    public void run(){
        while (true){
            chooseMainOption();
        }
    }

    public void chooseMainOption(){
        Menus.mainMenu();
        int wybor=scanner.nextInt();
        scanner.nextLine();
        switch (wybor){
            case 1:
                listaPracownikowController.wyswietlListePracownikow();
                break;
            case 2:
                dodajPracownikaController.dodajPracownika();
                break;
            case 3:
                usunPracownikaController.usunPracownika();
                break;
            case 4:
                kopiaZapasowaController.kopiaZapasowaZachowajOdtworz();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                Errors.zlyWyborError();
                break;
        }
    }
}
