package App;
import App.Controller.MenuController;
import App.Model.EwidencjaPracownikow;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new EwidencjaPracownikow();
        Scanner scanner= new Scanner(System.in);
        MenuController controller=new MenuController(scanner);
        controller.run();
    }
//    przykladowe pesele
//    67022327618
//    58013039977
//    05230877299
//    52121023446
//    61020144334
//    86061882995
//    03311233266
//    53062152172
//    03272162757
//    74101985517
}