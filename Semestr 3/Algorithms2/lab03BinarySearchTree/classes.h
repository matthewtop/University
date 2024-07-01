#ifndef LAB03_CLASSES_H
#define LAB03_CLASSES_H

#include <iostream>

using namespace std;

template <typename T>
class Tree{
public:
    T key;
    Tree *left;
    Tree *right;
    Tree *parent;
    int index;

    Tree(T key, int index) : key(key), left(nullptr), right(nullptr), parent(nullptr), index(index) {}
    ~Tree()= default;

    void dodaj(T key) {
        if (key < this->key) {
            if (left == nullptr) {
                left = new Tree(key, index+1);
                left->parent = this;
                left->index = index + 1;
            } else {
                left->dodaj(key);
            }
        } else if (key > this->key) {
            if (right == nullptr) {
                right = new Tree(key, index+1);
                right->parent = this;
                right->index = index + 1;
            } else {
                right->dodaj(key);
            }
        }
    }

    void dodajLosowo(int ilosc){
        for (int i = 0; i < ilosc; i++) {
            T losowaWartosc=rand();
            dodaj(losowaWartosc);
        }
    }

    int getIlosc(){
        int ilosc;
        cout<<"Podaj ilosc: ";
        cin>>ilosc;
        return ilosc;
    }

    T getWartosc(){
        T wartosc;
        cout<<"Podaj wartosc: ";
        cin>>wartosc;
        return wartosc;
    }

    void preorder(){
            cout << key << " "; //korzen

            if (left != nullptr) {
                left->preorder();
            }
            if (right != nullptr) {
                right->preorder();
            }
    }

    void inOrder(){
            if (left != nullptr) {
                left->inOrder();
            }
            cout << key << " ";
            if (right != nullptr) {
                right->inOrder();
            }
    }

    void clear(){
        if(checkIfTreeIsNotEmpty()== true) {
            if (left != nullptr) {
                left->clear();
                delete left;
                left = nullptr;
            }
            else if (right != nullptr) {
                right->clear();
                delete right;
                right = nullptr;
            }
        } else{
            View::treeIsEmpty();
        }
    }

    bool znajdz(T key, int&index){
        if (this->key==key){
            index = this->index;
            return true;
        }
        else if (key< this->key){
            if (left!= nullptr){
                return left->znajdz(key, index);
            }
        }
        else{
            if (right != nullptr){
                return right->znajdz(key, index);
            }
        }
        return false;
    }

    bool checkIfTreeIsNotEmpty(){
        if (left == nullptr && right == nullptr) {
            return false;
        }
        else{
            return true;
        }
    }

    int getHeight(){
        if(checkIfTreeIsNotEmpty()== true){
            int leftHeight = 0;
            int rightHeight = 0;

            if (left != nullptr) {
                leftHeight = left->getHeight();
            }

            if (right != nullptr) {
                rightHeight = right->getHeight();
            }

            return max(leftHeight, rightHeight) + 1;
        }
        else{
            return 1;
        }
    }

    void heightMessage(){
        cout<<"Wysokosc drzewa wynosi: "<<getHeight()<<endl;
    }

    bool usun(T key) {
        int index;
        if (znajdz(key, index)) {
            if (key < this->key) {
                return deleteLeft();
            } else if (key > this->key) {
                return deleteRight();
            } else {
                delete this;
                return true;
            }
        } else {
            View::wezelNieIstnieje();
            return false;
        }
    }

    bool deleteLeft() {
        if (left != nullptr) {
            delete left;
            left = nullptr;
            View::sukces();
            return true;
        }
        return false;
    }

    bool deleteRight() {
        if (right != nullptr) {
            delete right;
            right = nullptr;
            View::sukces();
            return true;
        }
        return false;
    }

    void wyswietl() {
        if (right != nullptr) {
            right->wyswietl();
        }

        for (int i = 0; i < index; i++) {
            cout << "    ";
        }
        cout << key << endl;

        if (left != nullptr) {
            left->wyswietl();
        }
    }

    void zmienKorzen(T nowy){
        this->key=nowy;
    }
};
#endif //LAB03_CLASSES_H
