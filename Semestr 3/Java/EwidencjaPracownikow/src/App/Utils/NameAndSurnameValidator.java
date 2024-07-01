package App.Utils;

public class NameAndSurnameValidator {
    private static final String NAME_SURNAME = "[A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]+";
    public static boolean validateName(String name){return name.matches(NAME_SURNAME);}
    public static boolean validateSurname(String surname){
        return surname.matches(NAME_SURNAME);
    }
}
