#include <iostream>
#include "View.h"
#include "classes.h"

using namespace std;

int main() {

    Tree<int> tree(10,0);

    int wybor;
    bool dzialanie = true;
    do {
        View::mainMenu();
        cout<<"Twoj wybor: ";
        cin >> wybor;
        switch (wybor) {
            case 0:{
                clock_t t1 = clock();
                tree.dodajLosowo(tree.getWartosc());
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 1: {
                clock_t t1 = clock();
                tree.dodaj(tree.getWartosc());
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 2: {
                int index = -1;
                clock_t t1 = clock();
                bool znaleziony = tree.znajdz(tree.getWartosc(),index);
                if (znaleziony) {
                    cout << "Znaleziono wezel, index: " << index << endl;
                } else {
                    View::wezelNieIstnieje();
                }
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 3: {
                clock_t t1 = clock();
                tree.usun(tree.getWartosc());
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 4: {
                clock_t t1 = clock();
                tree.preorder();
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout<<endl;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 5: {
                clock_t t1 = clock();
                tree.inOrder();
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout<<endl;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 6: {
                clock_t t1 = clock();
                tree.clear();
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 7: {
                clock_t t1 = clock();
                tree.heightMessage();
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 8: {
                clock_t t1 = clock();
                tree.wyswietl();
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 9:{
                clock_t t1 = clock();
                tree.zmienKorzen(tree.getWartosc());
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds*1000;
                cout << miliseconds << " ms" << endl;
                break;
            }
            case 10: {
                dzialanie = false;
                break;
            }
            default: {
                cout << "Nieprawidlowy wybor sprobuj ponownie" << endl;
            }
        }
    } while (dzialanie);
}
