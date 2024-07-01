#include <iostream>
#include <ctime>
#include <fstream>

using namespace std;

template <typename T>
class dynamicTab {
public:
    T* data;
    int size;
    int currentSize;

    dynamicTab(int size) {
        this -> size = size;
        data = new T[size];
        currentSize = 0;
    }

    ~dynamicTab() {
        delete[] data;
    }

    void add(T value) {
        if (size <= currentSize) {
            size = 2 * size;
            T* newTab = new T[size];
            for (int i = 0; i < currentSize; i++) {
                newTab[i] = data[i];
            }
            delete[] data;
            data = newTab;
        }

        data[currentSize] = value;
        currentSize++;
    }

    void merge(T* arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        T* L = new T[n1];
        T* R = new T[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; j++)
            R[j] = arr[m + 1 + j];

        int i = 0;
        int j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            if (L[i] < R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }

        delete[] L;
        delete[] R;
    }

    void mergeSort(T* arr, int l, int r) {
        for (int curr_size = 1; curr_size <= r - l; curr_size *= 2) {
            for (int left_start = l; left_start < r; left_start += 2 * curr_size) {
                int mid = min(left_start + curr_size - 1, r);
                int right_end = min(left_start + 2 * curr_size - 1, r);

                merge(arr, left_start, mid, right_end);
            }
        }
    }
};

class Punkt {
public:
    float pointX, pointY;

    Punkt() =  default;

    Punkt(float pointX, float pointY) {
        this->pointX = pointX;
        this->pointY = pointY;
    }
};

class Krawedz {
public:
    int edgeX, edgeY;
    float edgeValue;

    Krawedz() = default;

    Krawedz(int edgeX, int edgeY, float edgeValue) {
        this->edgeX = edgeX;
        this->edgeY = edgeY;
        this->edgeValue = edgeValue;
    }

    bool operator>(const Krawedz& other) const {
        return edgeValue > other.edgeValue;
    }

    bool operator<(const Krawedz& other) const {
        return edgeValue < other.edgeValue;
    }

//    bool compare(const Krawedz& other) const {
//        return edgeValue < other.edgeValue;
//    }
};

struct unionFind {
    int* parent;
    int* rank;

public:
    unionFind(int size) {
        this->parent = new int[size];
        this->rank = new int[size];

        for(int i=0; i<size; i++) {
            this->parent[i] = i;
            this->rank[i] = 0;
        }
    }

    int findPathCompression(int x) {
        if(parent[x]!=x) {
            parent[x]= findPathCompression(parent[x]);
        }

        return parent[x];
    }

    void unionMerge(int edgeX, int edgeY) {
        int rootX = findPathCompression(edgeX);
        int rootY = findPathCompression(edgeY);

        if(rootX != rootY) {
            if(rank[rootX] < rank[rootY])
                parent[rootX] = rootY;
            else if(rank[rootX] > rank[rootY])
                parent[rootY] = rootX;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
};

class graf {
    dynamicTab<Krawedz>* edges;
    dynamicTab<Punkt>* points;

public:
    graf() {
        this->edges = new dynamicTab<Krawedz>(1);
        this->points = new dynamicTab<Punkt>(1);
    }

    void addEdge(int edgeX, int edgeY, float edgeValue) {
        Krawedz newEdge(edgeX, edgeY, edgeValue);
        edges->add(newEdge);
    }

    void addPoint(float pointX, float pointY) {
        Punkt newPoint(pointX, pointY);
        points->add(newPoint);
    }

    void readGraphFromFile(const string& filename) {
        ifstream file(filename);

        if (!file.is_open()) {
            cout << "Nie można otworzyć pliku: " << filename << endl;
            return;
        }

        int n;
        file >> n;

        for (int i = 0; i < n; i++) {
            float x, y;
            file >> x >> y;
            addPoint(x, y);
        }

        int e;
        file >> e;

        for (int i = 0; i < e; i++) {
            int edgeX, edgeY;
            float edgeValue;
            file >> edgeX >> edgeY >> edgeValue;

            addEdge(edgeX, edgeY, edgeValue);
        }

        file.close();
    }

    void sortEdges() {
        edges->mergeSort(edges->data, 0, edges->currentSize - 1);
    }

    float kruskal() {
        unionFind uf(points->currentSize);
        sortEdges();

        float totalCost = 0;

        for (int i = 0; i < edges->currentSize; i++) {
            int x = edges->data[i].edgeX;
            int y = edges->data[i].edgeY;

            int rootX = uf.findPathCompression(x);
            int rootY = uf.findPathCompression(y);

            if (rootX != rootY) {
                totalCost += edges->data[i].edgeValue;
                uf.unionMerge(rootX, rootY);
            }
        }

        return totalCost;
    }
};

int main() {
    clock_t t1 = clock();
    srand(static_cast<unsigned>(time(nullptr)));

    graf myGraph;

    myGraph.readGraphFromFile("C://Users//damia//CLionProjects//algo2//excercises//g1.txt");
    float minimumSpanningTreeCost_g1 = myGraph.kruskal();
    cout << "Cost of Minimum Spanning Tree for g1.txt: " << minimumSpanningTreeCost_g1 << endl;

//    myGraph.readGraphFromFile("C://Users//damia//CLionProjects//algo2//excercises//g2.txt");
//    float minimumSpanningTreeCost_g2 = myGraph.kruskal();
//    cout << "Cost of Minimum Spanning Tree for g2.txt: " << minimumSpanningTreeCost_g2 << endl;

    myGraph.readGraphFromFile("C://Users//damia//CLionProjects//algo2//excercises//g3.txt");
    float minimumSpanningTreeCost_g3 = myGraph.kruskal();
    cout << "Cost of Minimum Spanning Tree for g3.txt: " << minimumSpanningTreeCost_g3 << endl;

    clock_t t2 = clock();

    float time = (t2 - t1) / static_cast<float>(CLOCKS_PER_SEC);

    cout << "Total Execution Time: " << time << " seconds" << endl;

    return 0;
}