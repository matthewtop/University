#ifndef LAB03_VIEW_H
#define LAB03_VIEW_H

using namespace std;
class View{
public:

    static void mainMenu(){
        cout<<"===================================="<<endl;
        cout<<"|       0. Dodaj elementy losowe   |"<<endl;
        cout<<"|       1. Dodaj element           |"<<endl;
        cout<<"|       2. Wyszukaj element        |"<<endl;
        cout<<"|       3. Usun wezel              |"<<endl;
        cout<<"|       4. Przejscie pre-order     |"<<endl;
        cout<<"|       5. Przejscie in-order      |"<<endl;
        cout<<"|       6. Wyczysc drzewo          |"<<endl;
        cout<<"|       7. Wyznacz wysokosc        |"<<endl;
        cout<<"|       8. Wyswietl drzewo         |"<<endl;
        cout<<"|       9. Zmien wartosc korzenia  |"<<endl;
        cout<<"|       10.Zakoncz prace programu  |"<<endl;
        cout<<"===================================="<<endl;
    }

    static void treeIsEmpty(){
        cout<<"Drzewo jest puste"<<endl;
    }

    static void wezelNieIstnieje(){
        cout << "Wezel nie istnieje" << endl;
    }

    static void sukces(){
        cout << "Udalo sie" << endl;
    }
};

#endif //LAB03_VIEW_H
