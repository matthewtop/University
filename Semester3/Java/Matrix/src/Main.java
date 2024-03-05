import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model);
        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller(model, view, scanner);
        controller.run();
    }
}