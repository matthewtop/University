#ifndef CLASSES_H
#define CLASSES_H

#include <iostream>
using namespace std;

enum Color {
    RED, BLACK
};
template<typename T>
class TreeNode {
    public:
    T data;
    TreeNode<T>* left;
    TreeNode<T>* right;
    TreeNode<T>* parent;
    Color color;

    TreeNode(T data) : data(data), left(nullptr), right(nullptr), parent(nullptr), color(RED) {}

    ~TreeNode() {
        delete left;
        delete right;
        delete parent;
    }
};

template<typename T>
class BST {
public:
    TreeNode<T>* root;
    BST() {root = nullptr;}

    ~BST() {delete root;}

    virtual TreeNode<T>* insert(T data) {
        auto * newNode = new TreeNode(data);

        if (root == nullptr) {
            root = newNode;
            root->color=BLACK;
            return root;
        }

        TreeNode<T>* current = root;
        TreeNode<T>* parent = nullptr;

        while(current != nullptr){
            parent = current;

            if(newNode->data < current->data){
                current = current->left;
            } else if (newNode->data > current->data) {
                current = current->right;
            }
            else {
                delete newNode;
                return nullptr;
            }
        }

        newNode->parent = parent;

        if(newNode->data < parent->data){
            parent->left = newNode;
        } else {
            parent->right = newNode;
        }
            return newNode;
        }

        void print(const string &prefix, TreeNode<T> * parent, bool isLeft, bool isRoot) {
            if (parent == nullptr)
                return;

            if (isRoot) {
                cout << "R O O T";
            } else {
                cout << prefix <<
                     (isLeft ? "L-------" : "R------");
            }

            cout << " " << parent->color << " - " <<  parent -> data << endl;

            print(prefix + (isLeft ? " |   " : "    "),
                  parent -> left, true, false);
            print(prefix + (isLeft ? " |   " : "    "),
                  parent -> right, false, false);
        }

        void display() {
            print("", root, false, true);;
        }


        int height(TreeNode<T> *node) {
            if (node == nullptr)
                return 0;

            int leftHeight = height(node->left);
            int rightHeight = height(node->right);

            return 1 + max(leftHeight, rightHeight);
        }

        int getHeight() {
            return height(root);
        }
};

template<typename T>
class RBT : public BST<T> {
public:

    RBT(){}

   ~RBT(){}

    TreeNode<T>* insert(T data) override {
        clock_t t1 = clock();
        TreeNode<T>* newNode = BST<T>::insert(data);
           if (newNode != nullptr) {
               dodajFix(newNode);
           }
           return newNode;
        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds*1000;
        cout << miliseconds << " ms" << endl;
    }


    void dodajFix(TreeNode<T>* z){
            while (z->parent != nullptr && z->parent->color == RED) {
                if (z->parent == z->parent->parent->left) {
                    TreeNode<T> *y = z->parent->parent->right;
                    if (y != nullptr && y->color == RED) {
                        z->parent->color = BLACK;
                        y->color = BLACK;
                        z->parent->parent->color = RED;
                        z = z->parent->parent;
                    } else {
                        if (z == z->parent->right) {
                            z = z->parent;
                            leftRotate(z);
                        }
                        z->parent->color = BLACK;
                        z->parent->parent->color = RED;
                        rightRotate(z->parent->parent);
                    }
                } else {
                    TreeNode<T> *y = z->parent->parent->left;
                    if (y != nullptr && y->color == RED) {
                        z->parent->color = BLACK;
                        y->color = BLACK;
                        z->parent->parent->color = RED;
                        z = z->parent->parent;
                    } else {
                        if (z == z->parent->left) {
                            z = z->parent;
                            rightRotate(z);
                        }
                        z->parent->color = BLACK;
                        z->parent->parent->color = RED;
                        leftRotate(z->parent->parent);
                    }
                }
            }
        this->root->color = BLACK;
    }

    void leftRotate(TreeNode<T> * x){
        TreeNode<T> * y = x->right;
        x->right = y->left;

        if(y->left != nullptr){
            y->left->parent = x;
        }

        y->parent = x->parent;

        if(x->parent == nullptr){
            BST<T>::root = y;
        } else if(x == x->parent->left){
            x->parent->left = y;
        } else {
            x->parent->right = y;
        }

        y->left = x;
        x->parent = y;
    }

    void rightRotate(TreeNode<T> * x){
        TreeNode<T> * y = x->left;

        x->left = y->right;

        if(y->right != nullptr){
            y->right->parent = x;
        }

        y->parent = x->parent;

        if(x->parent == nullptr){
            BST<T>::root = y;
        } else if(x == x->parent->right){
            x->parent->right = y;
        } else {
            x->parent->left = y;
        }

        y->right = x;
        x->parent = y;
    }

    void preOrder(TreeNode<T> *node){
        if (node != nullptr){
            cout<<node->data<<" ";
            preOrder(node->left);
            preOrder(node->right);
        }
    }
    void inOrder(TreeNode<T> *node) {
        if (node != nullptr) {
            inOrder(node->left);
            cout << node->data << " ";
            inOrder(node->right);
        }
    }

    void clearTree() {
        clock_t t1 = clock();
        clear(this->root);
        this->root = nullptr;
        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds*1000;
        cout << miliseconds << " ms" << endl;
    }

    void clear(TreeNode<T> *node){
        if(node!= nullptr){
            clear(node->left);
            clear(node->right);
        }
    }
    void insertRandomValues(){
        int ilosc;
        View::getIlosc();
        cin>>ilosc;
        clock_t t1 = clock();
        srand(static_cast<unsigned>(time(nullptr)));
        for(int i=0; i<ilosc; i++){
            insert(rand()%1000);
        }
        clock_t t2 = clock();
        double seconds = (t2 - t1) / (double) CLOCKS_PER_SEC;
        double miliseconds = seconds*1000;
        cout << miliseconds << " ms" << endl;
    }

    void search(T value) {
        TreeNode<T>* wynik = searchNode(this->root, value);
        if (wynik != nullptr) {
            cout << "Node: " << wynik->data << " (Color: " << (wynik->color ? "RED" : "BLACK") << ")" << endl;
        } else {
            View::elementNieIstniejeError();
        }
    }

    T getValue(){
        T wartosc;
        View::getIlosc();
        cin>>wartosc;
        return wartosc;
    }

    TreeNode<T>* searchNode(TreeNode<T>* node, int value) {
        if (node == nullptr || node->data == value) {
            return node;
        }

            if (value < node->data) {
                return searchNode(node->left, value);
            } else {
                return searchNode(node->right, value);
            }
        }
};
#endif // CLASSES_H

