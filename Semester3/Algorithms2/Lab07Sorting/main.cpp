#include <iostream>
#include <vector>
#include <algorithm>
#include "View.h"

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

    Heap(T* array, int size) {
        this->size = size;
        maxSize = size;
        heapArray = array;
        buildHeap();
    }

    ~Heap(){delete heapArray;}


    void buildHeap(){
        for (int i = size / 2 - 1; i >= 0; --i) {
             heapifyTopDown(i);
             //heapifyBottomUp();
        }
    }

    void heapifyTopDown(int index) {
        while (index >= 0) {
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

    void heapifyBottomUp() {
        for (int i = size / 2 - 1; i >= 0; --i) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            int max = i;

            if (leftChild < size && heapArray[leftChild] > heapArray[max]) {
                max = leftChild;
            }

            if (rightChild < size && heapArray[rightChild] > heapArray[max]) {
                max = rightChild;
            }

            if (max != i) {
                T temp = heapArray[i];
                heapArray[i] = heapArray[max];
                heapArray[max] = temp;
                heapifyBottomUp();
            }
        }
    }

    void heapSort() {
        for (int i = size - 1; i > 0; --i) {
            T temp = heapArray[0];
            heapArray[0] = heapArray[i];
            heapArray[i] = temp;

             heapifyTopDown(0);
            // heapifyBottomUp();
        }
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

    void insertRandomValues(Heap<T>& heap, T* array, int size) {
        clock_t t1 = clock();
        heap.size = size;
        heap.maxSize = size;
        delete[] heap.heapArray;
        heap.heapArray = new T[size];
        memcpy(heap.heapArray, array, size * sizeof(T));

        // heap.heapifyTopDown(0);
        heap.heapifyBottomUp();

        clock_t t2 = clock();
        //liczCzas(t1, t2);
    }

    string toString() {
        string result;
        //clock_t t1 = clock();
        for (int i = 0; i < size; i++) {
            result += to_string(heapArray[i]) + " ";
        }
        //clock_t t2 = clock();
        //liczCzas(t1,t2);
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

template<typename T>
void createRandomArray(T*& array, int size) {
    array = new T[size];
    srand(static_cast<unsigned>(time(nullptr)));

    for (int i = 0; i < size; ++i) {
        array[i] = static_cast<T>(rand());
    }
}

template<typename T>
void displayArray(T* array, int size) {
    for (int i = 0; i < size; ++i) {
        cout << array[i] << " ";
    }
    cout << endl;
}

template<typename T>
void countingSort(T* array, int size) {
    if (size <= 1) {
        return;
    }

    T max = array[0];
    T min = array[0];
    for (int i = 1; i < size; ++i) {
        if (array[i] > max) {
            max = array[i];
        }
        if (array[i] < min) {
            min = array[i];
        }
    }

    int range = max - min + 1;
    int* count = new int[range]();
    T* output = new T[size];

    for (int i = 0; i < size; ++i) {
        count[array[i] - min]++;
    }

    for (int i = 1; i < range; ++i) {
        count[i] += count[i - 1];
    }

    for (int i = size - 1; i >= 0; --i) {
        output[count[array[i] - min] - 1] = array[i];
        count[array[i] - min]--;
    }

    for (int i = 0; i < size; ++i) {
        array[i] = output[i];
    }
    delete[] count;
    delete[] output;
}

template<typename T>
void bucketSort(T* array, int size) {
    if (size <= 1) {
        return;
    }

    T max = array[0];
    T min = array[0];
    for (int i = 1; i < size; ++i) {
        if (array[i] > max) {
            max = array[i];
        }
        if (array[i] < min) {
            min = array[i];
        }
    }

    double average = static_cast<double>(max + min) / 2;

    const int numBuckets = size;
    vector<vector<T>> buckets(numBuckets);

    for (int i = 0; i < size; ++i) {
        int bucketIndex = static_cast<int>((array[i] - min) / (average - min) * (numBuckets - 1));
        buckets[bucketIndex].push_back(array[i]);
    }

    int currentIndex = 0;
    for (auto& bucket : buckets) {
        sort(bucket.begin(), bucket.end());

        for (const auto& element : bucket) {
            array[currentIndex++] = element;
        }
    }
}


int main() {
    Heap<int> heap;
   // //Heap<int> integerHeap;
   // Heap<double> objectHeap;
    int* array;
    int size = 10000;

    createRandomArray(array, size);
    cout << "Og tablica: ";
    //displayArray(array, size);
    cout<<endl;

    cout<<"Sortowanie przez zliczanie: "<< endl;
    clock_t t1 = clock();
    countingSort(array, size);
    clock_t t2 = clock();
    heap.liczCzas(t1,t2);



 //   displayArray(array, size);
    cout<<endl;

    cout<<"Kopiec: "<<endl;
    heap.insertRandomValues(heap,array,size);
    //cout<<heap.toString()<<endl;

    cout<<endl;
    clock_t t3 = clock();
    heap.heapSort();
    clock_t t4 = clock();
    heap.liczCzas(t3,t4);
   // cout<<heap.toString()<<endl;

    delete[] array;
    return 0;
}
