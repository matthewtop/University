#ifndef LAB01_CLASSES_H
#define LAB01_CLASSES_H
#include <string>

using namespace std;

class Data{
public:
    string brand;
    string model;
    int power{};

    Data()= default;

    Data(const string& brand, const string& model, int power)
            : brand(brand), model(model), power(power) {}

    ~Data()=default;
};

class Node{
public:
    Node* prev;
    Node* next;
    Data data;

    Node(){
        prev= nullptr;
        next= nullptr;
    }

    Node(const Data& carData){
        prev= nullptr;
        next= nullptr;
        data=carData;
    }

    ~Node()= default;
};

class List{
public:
    Node* head;
    Node* tail;
    int size;

    List(){
        tail= nullptr;
        head= nullptr;
        size=0;
    }
    ~List()= default;

    void insertAtTail(const Data& carData){
        if (head==nullptr){
            insertAtHead(carData);
            return;
        }

        Node* node= new Node(carData);
        Node* temp = head;
        while (temp->next!= nullptr){
            temp=temp->next;
        }

        temp-> next=node;
        node-> prev=temp;
        tail=node;
        size++;

    }

    void insertAtHead(const Data& carData){
        Node* node = new Node(carData);
        node->next = head;
        if (head != nullptr) {
            head->prev = node;
        } else {
            tail = node;
        }
        head = node;
        size++;
    }

    void printList() const {
        Node* temp = head;
        int index = 1;
        while (temp != nullptr) {
            cout << index << ". Brand: " << temp->data.brand << ", Model: " << temp->data.model << ", Power: " << temp->data.power << endl;
            temp = temp->next;
            index++;
        }
    }

    void deleteFirstElement() {
        if (head != nullptr) {
            Node* nodeToDelete = head;
            head = head->next;
            if (head != nullptr) {
                head->prev = nullptr;
            } else {
                tail = nullptr;
            }
            delete nodeToDelete;
            size--;
        }
    }

    void deleteLastElement() {
        if (tail != nullptr) {
            Node* nodeToDelete = tail;
            tail = tail->prev;
            if (tail != nullptr) {
                tail->next = nullptr;
            } else {
                head = nullptr;
            }
            delete nodeToDelete;
            size--;

            if (size == 0) {
                head = nullptr;
                tail = nullptr;
            }
        }
    }
    Data deleteByIndex(int index) {
        if (index < 1 || index > size) {
            cout << "Index poza zakresem." << endl;
        }

        Node* temp = head;
        for (int i = 1; i < index; i++) {
            temp = temp->next;
        }

        if (temp->prev != nullptr) {
            temp->prev->next = temp->next;
        } else {
            head = temp->next;
        }

        if (temp->next != nullptr) {
            temp->next->prev = temp->prev;
        } else {
            tail = temp->prev;
        }

        Data deletedData = temp->data;
        delete temp;
        size--;

        return deletedData;
    }

     void podmiana(int index, const Data& newData) const {
        if (index < 1 || index > size) {
            cout << "Index poza zakresem." << endl;
        }

        Node* temp = head;
        for (int i = 1; i < index; i++) {
            temp = temp->next;
        }
        temp->data = newData;
    }

    void clearList() {
        while (head != nullptr) {
            Node* nodeToDelete = head;
            head = head->next;
            delete nodeToDelete;
        }
        tail = nullptr;
        size = 0;
    }

    Data getDataFromIndex(int index) const {
        if (index < 1 || index > size) {
            cout << "Index poza zakresem." << endl;
        }

        Node* temp = head;
        for (int i = 1; i < index; i++) {
            temp = temp->next;
        }

        return temp->data;
    }

    string toString() const {
        string result;
        Node* temp = head;
        int index = 1;
        while (temp != nullptr) {
            result += to_string(index) + ". Brand: " + temp->data.brand + ", Model: " + temp->data.model + ", Power: " + to_string(temp->data.power) + "\n";
            temp = temp->next;
            index++;
        }
        return result;
    }
};
#endif //LAB01_CLASSES_H