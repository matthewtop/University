#include <iostream>
#include "View.h"
#include "Heap.h"
using namespace std;

Heap<int> heap;

int main() {
    int wybor;
    bool dzialanie=true;
    do {
        View::mainMenu();
        cin>>wybor;
        switch(wybor) {
            case 0:{
                heap.insertRandomValues(heap);
                break;
            }
            case 1: {
                heap.insert();
                break;
            }
            case 2:{
                heap.extractMax();
                break;
            }
            case 3: {
                heap.clearHeap();
                break;
            }
            case 4: {
                cout<<heap.toString()<<endl;
                break;
            }
            case 5: {
                dzialanie=false;
                break;
            }
            default: {
                View::wrongChoiceError();
                break;
            }
        }
    }while(dzialanie);
}

