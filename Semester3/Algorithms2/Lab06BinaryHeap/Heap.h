#ifndef LAB06BINARYHEAP_HEAP_H
#define LAB06BINARYHEAP_HEAP_H

#include <iostream>
#include "View.h"
#include <string>

using namespace std;

template<typename T>
class Heap {
public:
    int size;
    int maxSize;
    T* heapArray;

    Heap() {
        size = 0;
        maxSize = 1;
        heapArray = new T[maxSize];
    }

    void addMaxSize() {
        maxSize *= 2;
        T* temp = new T[maxSize];

        for (int i = 0; i < size; i++) {
            temp[i] = heapArray[i];
        }
        delete[] heapArray;
        heapArray = temp;
    }

    void checkSize() {
        if (size >= maxSize) {
            addMaxSize();
        }
    }

    void insert(T element) {
        checkSize();
        heapArray[size++] = element;
        heapifyUp(size - 1);
    }

    void insert() {
        T value;
        View::getValue();
        cin >> value;
        clock_t t1 = clock();
        checkSize();
        heapArray[size++] = value;
        heapifyUp(size - 1);
        clock_t t2 = clock();
        liczCzas(t1,t2);
    }

    void insertRandomValues(Heap<T>& heap) {
        int numOfElem;
        View::getRandomValues();
        cin >> numOfElem;
        if (numOfElem < 0) {
            View::wrongValueError();
            return;
        }
        clock_t t1 = clock();
        srand(static_cast<unsigned>(time(nullptr)));
        for (int i = 0; i < numOfElem - 1; i++) {
            T randomValue = rand();
            heap.insert(randomValue);
        }
        clock_t t2 = clock();
        liczCzas(t1,t2);
    }

    void clearHeap() {
        clock_t t1 = clock();
        delete[] heapArray;
        size = 0;
        maxSize = 1;
        heapArray = new T[maxSize];
        clock_t t2 = clock();
        liczCzas(t1,t2);
    }

    string toString() {
        string result;
        clock_t t1 = clock();
        for (int i = 0; i < size; i++) {
            result += to_string(heapArray[i]) + " ";
        }
        clock_t t2 = clock();
        liczCzas(t1,t2);
        return result;
    }

    void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heapArray[index] < heapArray[parentIndex]) {
                T temp = heapArray[index];
                heapArray[index] = heapArray[parentIndex];
                heapArray[parentIndex] = temp;
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    T extractMax() {
        clock_t t1 = clock();
        T maxElement = heapArray[0];
        heapArray[0] = heapArray[size - 1];
        size--;
        heapifyDown(size - 1);
        clock_t t2 = clock();
        liczCzas(t1,t2);
        return maxElement;
    }

    void heapifyDown(int index) {
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int max = index;

            if (leftChild < size && heapArray[leftChild] > heapArray[max]) {
                max = leftChild;
            }

            if (rightChild < size && heapArray[rightChild] > heapArray[max]) {
                max = rightChild;
            }

            if (max != index) {
                T temp = heapArray[index];
                heapArray[index] = heapArray[max];
                heapArray[max] = temp;
                index = max;
            } else {
                break;
            }
        }
    }

    void liczCzas(clock_t t1, clock_t t2){
        double seconds = (t2 - t1) / (double)CLOCKS_PER_SEC;
        double milliseconds = seconds * 1000;
        View::displayMiliseconds(milliseconds);
    }
};
#endif //LAB06BINARYHEAP_HEAP_H
