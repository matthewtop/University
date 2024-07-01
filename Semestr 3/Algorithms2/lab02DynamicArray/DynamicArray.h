#ifndef LAB02_DYNAMICARRAY_H
#define LAB02_DYNAMICARRAY_H
#include <string>

using namespace std;

template<typename T>
class DynamicArray{
public:
    int size;
    int maxSize;
    T* dynamicArray;

    DynamicArray(){
        size=0;
        maxSize=50;
        dynamicArray= new T[maxSize];
    }

    void addMaxSize(){
        maxSize *=2;
        T *temp = new T[maxSize];

        for(int i=0; i<size;i++){
            temp[i] = dynamicArray[i];
        }
        delete[] dynamicArray;
        dynamicArray=temp;
    }

    void checkSize(){
        if(size >= maxSize)
            addMaxSize();
    }

    void checkIfArrayIsNotEmpty(){
        if(size==0){
            cout<<"Tablica jest pusta"<<endl;
            return;
        }
    }

    void insert(T element){
        checkSize();
        dynamicArray[size++]=element;
    }

    void insert(){
        T element;
        cout<<"Podaj element: "<<endl;
        cin>>element;
        clock_t t1 = clock();
        checkSize();
        dynamicArray[size++]=element;
        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds*1000;
        cout << miliseconds << " ms" << endl;
    }

    void insertRandomValues(DynamicArray<T> &dynamicArray){
        int numOfElem;
        cout<<"Podaj ilosc elementow do wygenerowania i wprowadzenia: "<<endl;
        cin>>numOfElem;
        if(numOfElem<0)
            cout<<"Niepoprawna wartosc"<<endl;
        clock_t t1 = clock();
        srand(time(nullptr));
        for (int i = 0; i<numOfElem-1 ; i++) {
            T randomValue = rand();
            dynamicArray.insert(randomValue);
        }
        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds*1000;
        cout << miliseconds << " ms" << endl;
    }

    T& search(int index){
        checkIfArrayIsNotEmpty();
        if(index>=size)
            cout<<"Niepoprawny index";
        return dynamicArray[index];
    }

    void replace(){
        checkIfArrayIsNotEmpty();
        int index;
        T value;
        cout << "Podaj index do podmiany: ";
        cin >> index;
        cout << "Wprowadz wartosc: ";
        cin >> value;
        clock_t t1 = clock();
        if (index<0 || index >= size){
            cout<<"Zly index"<<endl;
            return;
        }
        dynamicArray[index]=value;
        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds*1000;
        cout << miliseconds << " ms" << endl;
    }

    void clearArray(){
        checkIfArrayIsNotEmpty();
        clock_t t1 = clock();
        delete[] dynamicArray;
        size=0;
        maxSize=50;
        dynamicArray = new T[maxSize];
        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds*1000;
        cout << miliseconds << " ms" << endl;
    }

    string toString() {
        checkIfArrayIsNotEmpty();
        string result;
        clock_t t1 = clock();
        for (int i = 0; i < size; i++) {
            result += to_string(dynamicArray[i]) + " ";
        }
        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds*1000;
        cout << miliseconds << " ms" << endl;
        return result;
    }

    void bubblesort() {
        checkIfArrayIsNotEmpty();
        clock_t t1 = clock();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (dynamicArray[j] > dynamicArray[j + 1]) {
                    T temp = dynamicArray[j];
                    dynamicArray[j] = dynamicArray[j + 1];
                    dynamicArray[j + 1] = temp;
                }
            }
        }
        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds*1000;
        cout << miliseconds << " ms" << endl;
    }
};
#endif //LAB02_DYNAMICARRAY_H
