//
// Created by mateu on 18.12.2023.
//

#ifndef LAB07SORTING_VIEW_H
#define LAB07SORTING_VIEW_H
#include <iostream>

using namespace std;


class View {
public:
    static void mainMenu(){
        cout<<"======================================================"<<endl;
        cout<<"|       1.Sortowanie przez kopcowanie                 |"<<endl;
        cout<<"|       2.Sortowanie przez zliczanie                  |"<<endl;
        cout<<"|       3.Sortowanie kubelkowe                        |"<<endl;
        cout<<"|       4.Eksperyment                                 |"<<endl;
        cout<<"|       5.Koniec pracy programu                       |"<<endl;
        cout<<"======================================================"<<endl;
        cout<<"Twoj wybor: ";
    }

    static void displayMiliseconds(double miliseconds){ cout << miliseconds << " ms" << endl;}

    static void getRandomValues(){ cout << "Podaj ilosc elementow do wygenerowania i wprowadzenia: ";}
    static void getValue(){ cout << "Podaj wartosc: ";}

    static void wrongChoiceError(){fprintf(stderr, "Nieprawidlowy wybor\n");}
    static void wrongValueError(){fprintf(stderr, "Nieprawidlowa wartosc\n");}


};


#endif //LAB07SORTING_VIEW_H
