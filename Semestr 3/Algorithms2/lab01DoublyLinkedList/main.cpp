#include <iostream>
#include "classes.h"
#include <string>
#include <time.h>

using namespace std;

int main() {

    int wybor;
    bool dzialanie = true;

    List carList;
    Data car1 = Data("Ford", "Mondeo", 150);
    Data car2 = Data("Renault", "Laguna", 200);
    Data car3 = Data("Lamborghini", "Aventador", 750);
    Data car4 = Data("Fiat", "500", 110);
    Data car5 = Data("Audi", "RS6", 500);
    Data car6 = Data("Ferrari", "SF90", 736);
    Data car7 = Data("Bugatti", "Veyron", 1000);
    Data car8 = Data("Citroen", "c5", 111);
    Data car9= Data("Volkswagen", "Passat", 112);
    Data car10 = Data("BMW", "series 5", 343);

    do {
        cout << "************Lista Dwukierunkowa************" << endl;
        cout << "Wybierz opcje:" << endl;
        cout << "1. Dodaj dane do konca listy" << endl;
        cout << "2. Dodaj dane do poczatku listy" << endl;
        cout << "3. Usun ostatni element" << endl;
        cout << "4. Usun pierwszy element" << endl;
        cout << "5. Zwroc dane wedlug indeksu" << endl;
        cout << "6. podmiana" << endl;
        cout << "8. Wyszukaj element i go usun" << endl;
      //  cout << "9. Dodaj element z wymuszeniem porzadku " << endl;
        cout << "10. Wyczysc liste " << endl;
        cout << "11. Zwroc napisowa reprezentacje listy " << endl;
        cout << "12.Wyswietl liste " << endl;
        cout << "*******************************************" << endl;

        cin >> wybor;

        switch (wybor) {
            case 1: {
                cout<<"Wybierz nr od 1 do 10 by dodac przykladowe dane samochodu do konca listy: ";
                int przyklad;
                cin >> przyklad;
                clock_t t1 = clock();
                if (przyklad == 1) {carList.insertAtTail(car1);
                } else if (przyklad == 2) {carList.insertAtTail(car2);
                } else if (przyklad == 3) {carList.insertAtTail(car3);
                } else if (przyklad == 4) {carList.insertAtTail(car4);
                } else if (przyklad == 5) {carList.insertAtTail(car5);
                } else if (przyklad == 6) {carList.insertAtTail(car6);
                } else if (przyklad == 7) {carList.insertAtTail(car7);
                } else if (przyklad == 8) {carList.insertAtTail(car8);
                } else if (przyklad == 9) {carList.insertAtTail(car9);
                } else if (przyklad == 10) {carList.insertAtTail(car10);
                } else {cout << "Niepoprawny wybór." << endl;
                }
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 2: {
                //dodanie nowego elementu na poczatku listy
                cout<<"Wybierz nr od 1 do 10 by dodac przykladowe dane samochodu do poczatku listy: ";
                int przyklad;
                cin >> przyklad;
                clock_t t1 = clock();
                if (przyklad == 1) {carList.insertAtHead(car1);
                } else if (przyklad == 2) {carList.insertAtHead(car2);
                } else if (przyklad == 3) {carList.insertAtHead(car3);
                } else if (przyklad == 4) {carList.insertAtHead(car4);
                } else if (przyklad == 5) {carList.insertAtHead(car5);
                } else if (przyklad == 6) {carList.insertAtHead(car6);
                } else if (przyklad == 7) {carList.insertAtHead(car7);
                } else if (przyklad == 8) {carList.insertAtHead(car8);
                } else if (przyklad == 9) {carList.insertAtHead(car9);
                } else if (przyklad == 10) {carList.insertAtHead(car10);
                } else {
                    cout << "Niepoprawny wybor." << endl;
                }
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 3: {
                //usuniecie ostatniego elementu
                clock_t t1 = clock();
                carList.deleteLastElement();
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double milliseconds = seconds*1000;
                cout << milliseconds << " ms" << endl;
                break;
            }
            case 4: {
                //usuniecie pierwszego elementu
                clock_t t1 = clock();
                carList.deleteFirstElement();
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double milliseconds = seconds*1000;
                cout << milliseconds << " ms" << endl;
                break;
            }
            case 5: {
                //zwrocenie danych itego elementu listy
                int index;
                cout << "Index elementu do wyszukania: ";
                cin >> index;
                clock_t t1 = clock();
                Data carData = carList.getDataFromIndex(index);
                if (carData.brand.empty() || carData.model.empty()) {
                    cout << "Brak danych" << endl;
                } else {
                    cout << "Brand: " << carData.brand << ", Model: " << carData.model << ", Power: " << carData.power << endl;
                }
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double milliseconds = seconds*1000;
                cout << milliseconds << " ms" << endl;
                break;
            }
            case 6:{
                //ustawienie (podmiana) danych i-tego elementu listy
                int index1;
                int index2;
                cout<<"Wprowadz index do podmiany"<<endl;
                cin>>index1;
                cout<<"Wybierz index auta do podmiany: "<<endl;
                cin>>index2;
                clock_t t1 = clock();
                if (index2 < 1 || index2 > 10) {
                    cout << "Niepoprawny index danych auta." << endl;
                    break;
                }
                Data newCar;
                switch (index2) {
                    case 1: newCar = car1; break;
                    case 2: newCar = car2; break;
                    case 3: newCar = car3; break;
                    case 4: newCar = car4; break;
                    case 5: newCar = car5; break;
                    case 6: newCar = car6; break;
                    case 7: newCar = car7; break;
                    case 8: newCar = car8; break;
                    case 9: newCar = car9; break;
                    case 10: newCar = car10; break;
                    default: cout << "Zly index" << endl; break;
                }
                carList.podmiana(index1, newCar);
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double milliseconds = seconds*1000;
                cout << milliseconds << " ms" << endl;
            }
            case 7: {
                //wyszukanie elementu
                break;
            }
            case 8: {
                //wyszukanie elementu i jego usuniecie
                int index;
                cout << "Index elementu do wyszukania i usuniecia: ";
                cin >> index;
                clock_t t1 = clock();
                Data carData = carList.deleteByIndex(index);
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double milliseconds = seconds*1000;
                cout << milliseconds << " ms" << endl;
                break;
            }
            case 9:{
                //dodanie elementu z wymuszeniem porzadku
                break;
            }
            case 10:{
                //czyszczenie listy
                clock_t t1 = clock();
                carList.clearList();
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double milliseconds = seconds*1000;
                cout << milliseconds << " ms" << endl;
                break;
            }
            case 11:{
                //zwrocenie napisowej reprezentacji listy
                clock_t t1 = clock();
                string listString = carList.toString();
                cout << "Lista samochodow:\n" << listString;
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double milliseconds = seconds*1000;
                cout << milliseconds << " ms" << endl;
                break;
            }
            case 12:{
                clock_t t1 = clock();
                carList.printList();
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double milliseconds = seconds*1000;
                cout << milliseconds << " ms" << endl;
                break;
            }
            default:
                cout << "Niepoprawny wybor." << endl;
                break;
        }
    } while (dzialanie);
    return 0;
}