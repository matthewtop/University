package App.Controller.FunctionControllers;

import App.Model.EwidencjaPracownikow;
import App.Model.Pracownik;
import App.View.InputGetters;
import App.View.Menus;
import App.View.Messages.Errors;
import App.View.Messages.Infos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.zip.*;

import static App.View.Menus.kopiaZapasowaMenu;
import static App.View.Menus.przerywnik;

public class KopiaZapasowaController {
    private final Scanner scanner;
    private final EwidencjaPracownikow ewidencjaPracownikow;
    private final ExecutorService executor;

    public KopiaZapasowaController(Scanner scanner, EwidencjaPracownikow ewidencjaPracownikow, ExecutorService executor){
        this.scanner = scanner;
        this.ewidencjaPracownikow = ewidencjaPracownikow;
        this.executor = executor;
    }

    public void kopiaZapasowaZachowajOdtworz() {
        kopiaZapasowaMenu();
        String wybor = scanner.next();
        switch (wybor.toLowerCase()) {
            case "z":
                przerywnik();
                wyborKopiaZapasowa();
                break;
            case "o":
                przerywnik();
                wyborOdczyt();
                break;
            default:
                Errors.zlyWyborError();
                break;
        }
    }
    public void wyborKopiaZapasowa() {
        Menus.wyborKompresji();
        String wybor = scanner.next();
        switch (wybor.toLowerCase()) {
            case "g":
                przerywnik();
                kopiaGzip(InputGetters.getNazwaPliku()+".gz");
                break;
            case "z":
                przerywnik();
                kopiaZip(InputGetters.getNazwaPliku()+".zip");
                break;
            default:
                Errors.zlyWyborError();
                break;
        }
    }
    public void wyborOdczyt(){
        Menus.wyborKompresji();
        String wybor = scanner.next();
        switch (wybor.toLowerCase()){
            case "g":
                przerywnik();
                odczytajKopieGzip(InputGetters.getNazwaPliku()+".gz");
                break;
            case "z":
                przerywnik();
                odczytajKopieZip(InputGetters.getNazwaPliku()+".zip");
                break;
            default:
                Errors.zlyWyborError();
                break;
        }
    }

    public CompletableFuture<Void> kopiaZip(String nazwaPliku) {
        List<Pracownik> pracownicy = new ArrayList<>(ewidencjaPracownikow.pobierzListePracownikow());

        return CompletableFuture.runAsync(() -> {
            try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(nazwaPliku))) {
                for (Pracownik pracownik : pracownicy) {
                    String pracownikFileName = pracownik.getPesel() + ".ser";
                    ZipEntry entry = new ZipEntry(pracownikFileName);
                    zipOut.putNextEntry(entry);

                    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                         ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream)) {
                        oos.writeObject(pracownik);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        zipOut.write(bytes, 0, bytes.length);
                    } catch (IOException e) {
                        Errors.cosPoszloNieTakError();
                    } finally {
                        zipOut.closeEntry();
                    }
                }
                Infos.kopiaZapisanaInfo(nazwaPliku);
            } catch (IOException e) {
                Errors.cosPoszloNieTakError();
            }
        }, executor);
    }

    public CompletableFuture<Void> kopiaGzip(String nazwaPliku) {
        List<Pracownik> pracownicy = new ArrayList<>(ewidencjaPracownikow.pobierzListePracownikow());

        return CompletableFuture.runAsync(() -> {
            try (FileOutputStream fos = new FileOutputStream(nazwaPliku);
                 GZIPOutputStream gzipOut = new GZIPOutputStream(fos);
                 ObjectOutputStream oos = new ObjectOutputStream(gzipOut)) {

                for (Pracownik pracownik : pracownicy) {
                    oos.writeObject(pracownik);
                }

                Infos.kopiaZapisanaInfo(nazwaPliku);
            } catch (IOException e) {
                Errors.cosPoszloNieTakError();
            }
        }, executor);
    }


    public void odczytajKopieZip(String nazwaPlikuZip) {
        List<Pracownik> pracownicy = new ArrayList<>();

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(nazwaPlikuZip))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory() && entry.getName().endsWith(".ser")) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        byteArrayOutputStream.write(buffer, 0, len);
                     }

                    try (ObjectInputStream ois = new ObjectInputStream(
                            new ByteArrayInputStream(byteArrayOutputStream.toByteArray()))) {
                        Object obj;
                        while (true) {
                            try {
                                obj = ois.readObject();
                                if (obj instanceof Pracownik pracownik) {
                                    pracownicy.add(pracownik);
                                }
                            } catch (EOFException e) {
                                break;
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        Errors.cosPoszloNieTakError();
                    }
                    zis.closeEntry();
                }
            }
            ewidencjaPracownikow.setPracownicy(pracownicy);
            Infos.kopiaOdczytanaInfo(nazwaPlikuZip);
        } catch (IOException e) {
            Errors.cosPoszloNieTakError();
        }
    }
    public void odczytajKopieGzip(String nazwaPliku) {
        List<Pracownik> pracownicy = new ArrayList<>();

        try (GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(nazwaPliku));
             ObjectInputStream ois = new ObjectInputStream(gzis)) {
            while (true) {
                try {
                    Object obj = ois.readObject();
                    if (obj instanceof Pracownik pracownik) {
                        pracownicy.add(pracownik);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
            ewidencjaPracownikow.setPracownicy(pracownicy);
            Infos.kopiaOdczytanaInfo(nazwaPliku);
        } catch (IOException | ClassNotFoundException e) {
            Errors.cosPoszloNieTakError();
        }
    }

}