#include <iostream>
#include <time.h>
#include <string>
#include "DynamicArray.h"
#include "View.h"
using namespace std;

int main() {
    DynamicArray<int> dynamicArray;
    int wybor;
    bool dzialanie=true;

    do {
        View::mainMenu();
        cin>>wybor;
        switch(wybor) {
            case 1: {
                dynamicArray.insert();
                break;
            }
            case 2:{
                dynamicArray.insertRandomValues(dynamicArray);
                break;
            }
            case 3: {
                int index;
                cout<<"Podaj index: ";
                cin>>index;
                clock_t t1 = clock();
                int wynik = dynamicArray.search(index);
                cout<<wynik<<endl;
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 4: {
                dynamicArray.replace();
                break;
            }
            case 5: {
                dynamicArray.clearArray();
                break;
            }
            case 6: {
                dynamicArray.bubblesort();
                break;
            }
            case 7:{
                cout<< dynamicArray.toString() <<endl;
                break;
            }
            case 8:
                dzialanie=false;
                break;
            default: {
                cout << "Nieprawidlowy wybor" << endl;
                break;
            }
        }
    }while(dzialanie);
}
