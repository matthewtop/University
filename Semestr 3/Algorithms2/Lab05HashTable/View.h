#ifndef TEST_VIEW_H
#define TEST_VIEW_H
#include <iostream>
using namespace std;

class View {
public:
    static void mainMenu(){
        cout<<"========================================="<<endl;
        cout<<"|       0. Dodaj losowe elementy         |"<<endl;
        cout<<"|       1. Dodaj element                 |"<<endl;
        cout<<"|       2. Wyszukaj element              |"<<endl;
        cout<<"|       3. Usun element                  |"<<endl;
        cout<<"|       4. Wyczysc tablice               |"<<endl;
        cout<<"|       5. Wyswietl tablice              |"<<endl;
        cout<<"|       6. Wyswietl statystyki           |"<<endl;
        cout<<"|       7. Zakoncz dzialanie             |"<<endl;
        cout<<"=========================================="<<endl;
        cout<<"Twoj wybor: ";
    }

    static void getKey(){
        cout<<"Podaj klucz: ";
    }

    static void getValue(){
        cout<<"Podaj wartosc: ";
    }

    static void getIlosc(){
        cout<<"Podaj ilosc: ";
    }

    static void showStats(int size, int maxSize, int noZero){
        cout << "Obecny rozmiar: " << size << endl;
        cout << "Maxymalny rozmiar: " << maxSize << endl;
        cout << "Liczba niepustych kubelkow: " << noZero << endl;
        cout << "Nullowe kubelki: " << maxSize-noZero<<endl;
    }

    static void nieZnalezionoElInfo(){
        cout<<"Nie znaleziono elementu o tym kluczu"<<endl;
    }

    static void usunietoElementInfo(){
        cout<<"Udalo sie usunac element"<<endl;
    }

    static void wrongValueError(){
        fprintf(stderr, "Niepoprawna wartosc");
    }
    static void wrongChoiceError(){
        fprintf(stderr, "Zly wybor");
    }

};


#endif //TEST_VIEW_H
