#ifndef LAB02_VIEW_H
#define LAB02_VIEW_H
class View {
public:
    static void mainMenu(){
        cout << "Wybierz opcje:" << endl;
        cout<<"1. Dodaj dane "<<endl;
        cout<<"2. Dodaj losowo wygenerowane dane"<<endl;
        cout<<"3. Wyszukaj element po indeksie"<<endl;
        cout<<"4. Podmiana elementu po indeksie"<<endl;
        cout<<"5. Wyczysc tablice"<<endl;
        cout<<"6. Posortuj tablice babelkowo"<<endl;
        cout<<"7. Wyswietl tablice"<<endl;
        cout<<"8. Zakoncz prace programu"<<endl;
    }
};
#endif //LAB02_VIEW_H
