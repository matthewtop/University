#include <iostream>
#include "HashTable.h"
#include "View.h"
#include <string>

using namespace std;

int main() {

    HashTable<int, string> hashTable;
    int wybor;
    bool dzialanie=true;

    do {
        View::mainMenu();
        cin>>wybor;
        switch(wybor) {
            case 0:{
                hashTable.insertRandomValues(hashTable.getIlosc());
                break;
            }
            case 1: {
                int klucz;
                string value;
                View::getKey();
                cin>>klucz;
                View::getValue();
                cin>>value;
                clock_t t1 = clock();
                hashTable.insert(klucz,value);
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 2:{
                int klucz;
                View::getKey();
                cin>>klucz;
                hashTable.search(klucz);
                break;
            }
            case 3: {
                int klucz;
                View::getKey();
                cin>>klucz;
                hashTable.usun(klucz);
                break;
            }
            case 4: {
                hashTable.clear();
                break;
            }
            case 5: {
                hashTable.print();
                break;
            }
            case 6:{
                hashTable.stats();
                break;
            }
            case 7:
                dzialanie=false;
                break;
            default: {
                View::wrongChoiceError();
                break;
            }
        }
    }while(dzialanie);
}
