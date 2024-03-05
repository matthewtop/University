#include <iostream>
#include <list>
#include <vector>
#include <ctime>
#include <sstream>
#include "View.h"

using namespace std;

template <typename K, typename V>
class HashTable {
public:
    int size;
    int maxSize;
    vector<list<pair<K, V>>> hashtable;

    HashTable() : size(0), maxSize(2), hashtable(2) {}

    ~HashTable() = default;

    int hash(K& key) {
        return (key) % maxSize;
    }

    void rehash() {
        maxSize *= 2;
        vector<list<pair<K, V>>> temp(maxSize);

        for (int i = 0; i < hashtable.size(); i++) {
            for (auto iterator = hashtable[i].begin(); iterator != hashtable[i].end(); iterator++) {
                int index = hash(iterator->first);
                temp[index].push_back(*iterator);
            }
        }
        hashtable = temp;
    }

    void insert(K& key, V& value) {
        if (size >= maxSize) {
            rehash();
        }

        int index = hash(key);
        auto& bucket = hashtable[index];

        auto iterator = bucket.begin();
        while (iterator != bucket.end()) {
            if (iterator->first == key) {
                cout << "Element o kluczu " << key << " juz istnieje." << endl;
                return;
            }
            iterator++;
        }

        bucket.emplace_back(key, value);
        size++;
    }

    int getIlosc(){
        int ilosc;
        View::getIlosc();
        cin>>ilosc;
        return ilosc;
    }


    void insertRandomValues(int numOfElem) {
        clock_t t1 = clock();
        if (numOfElem < 0) {
            View::wrongValueError();
            return;
        }

        srand(static_cast<unsigned>(time(nullptr)));

        for (int i = 0; i < numOfElem; i++) {
            string randomValue;
            for (int j = 0; j < 6; j++) {
                char randomChar = 'a' + (rand() % 26);
                randomValue.push_back(randomChar);
            }

            int randomKey = rand() % 1000;
            insert(randomKey, randomValue);
        }
        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds*1000;
        cout << miliseconds << " ms" << endl;
    }

    void print() {
        clock_t t1 = clock();
        for (int i = 0; i < maxSize; i++) {
            cout << "[" << i << "]";
            for (auto& kvPair : hashtable[i]) {
                cout << " (" << kvPair.first << " -> " << kvPair.second << ") ";
            }
            cout << endl;
        }
        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds*1000;
        cout << miliseconds << " ms" << endl;
    }

    void search(K& key) {
        clock_t t1 = clock();

        int index = hash(key);
        const auto& bucket = hashtable[index];

        auto iterator = bucket.begin();

        if (iterator != bucket.end()) {
            if (iterator->first == key) {
                cout << "Znaleziono element o kluczu " << key << ": (" << iterator->first << ", " << iterator->second << ")" << endl;
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds * 1000;
                cout << miliseconds << " ms" << endl;
                return;
            }
        } else {
            View::nieZnalezionoElInfo();
        }

        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds * 1000;
        cout << miliseconds << " ms" << endl;
    }

    void clear() {
        clock_t t1 = clock();
        for (auto& bucket : hashtable) {
            bucket.clear();
        }
        size = 0;
        maxSize = 2;
        hashtable.resize(2);
        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds*1000;
        cout << miliseconds << " ms" << endl;
    }

    void stats() {
        clock_t t1 = clock();
        int noZero = 0;

        for (int i = 0; i < hashtable.size(); ++i) {
            if (!hashtable[i].empty()) {
                noZero++;
            }
        }
        View::showStats(size, maxSize, noZero);
        cout << endl;

        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds * 1000;
        cout << miliseconds << " ms" << endl;
    }

    void usun(K& key) {
        clock_t t1 = clock();
        int index = hash(key);
        auto& bucket = hashtable[index];

        auto iterator = bucket.begin();

        if (iterator != bucket.end()) {
            if (iterator->first == key) {
                bucket.erase(iterator);
                size--;
                View::usunietoElementInfo();
                clock_t t2 = clock();
                double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
                double miliseconds = seconds * 1000;
                cout << miliseconds << " ms" << endl;
                return;
            }
        } else {
            View::nieZnalezionoElInfo();
        }

    }
};
