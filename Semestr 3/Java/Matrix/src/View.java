public class View {
    private final Model model;

    public View(Model model){
        this.model = model;
    }

    public static void howManyColumns(){System.out.println("Liczba kolumn: ");}
    public static void howManyRows(){System.out.println("Liczba wierszy: ");}

    public static void getValues(){System.out.println("Wprowadz wartosci: ");}

    public void displayMatrix(String name){
        switch(name){
            case "A":{
                System.out.println("Macierz A: ");
                Controller.showMatrix(model.getMatrix("A"));
                break;
            }
            case "B":{
                System.out.println("Macierz B: ");
                Controller.showMatrix(model.getMatrix("B"));
                break;
            }
            case "C":{
                System.out.println("Macierz C: ");
                Controller.showMatrix(model.getMatrix("C"));
                break;
            }
            case "TA":{
                System.out.println("Transponowana macierz A: ");
                Controller.showMatrix(model.getMatrix("TA"));
                break;
            }
            case "TB":{
                System.out.println("Transponowana macierz B: ");
                Controller.showMatrix(model.getMatrix("TB"));
                break;
            }
            case "TC":{
                System.out.println("Transponowana macierz C: ");
                Controller.showMatrix(model.getMatrix("TC"));
                break;
            }
        }
    }





}
