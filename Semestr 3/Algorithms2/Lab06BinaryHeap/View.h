#include <iostream>

#ifndef LAB06BINARYHEAP_VIEW_H
#define LAB06BINARYHEAP_VIEW_H

using namespace std;
class View {
public:
    static void mainMenu(){
        cout<<"======================================================"<<endl;
        cout<<"|       0. Dodaj losowe elementy                      |"<<endl;
        cout<<"|       1. Dodaj element                              |"<<endl;
        cout<<"|       2. Pobierz i usun element maxymalny           |"<<endl;
        cout<<"|       3. Wyczysc kopiec                             |"<<endl;
        cout<<"|       4. Napisowa reprezentacja                     |"<<endl;
        cout<<"|       5. Koniec pracy programu                      |"<<endl;
        cout<<"======================================================"<<endl;
        cout<<"Twoj wybor: ";
    }

    static void displayMiliseconds(double miliseconds){ cout << miliseconds << " ms" << endl;}

    static void getRandomValues(){ cout << "Podaj ilosc elementow do wygenerowania i wprowadzenia: ";}
    static void getValue(){ cout << "Podaj wartosc: ";}

    static void wrongChoiceError(){fprintf(stderr, "Nieprawidlowy wybor\n");}
    static void wrongValueError(){fprintf(stderr, "Nieprawidlowa wartosc\n");}

};
#endif //LAB06BINARYHEAP_VIEW_H
