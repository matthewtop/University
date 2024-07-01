import time
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from scipy.spatial.distance import cdist
from sklearn import datasets
from sklearn.decomposition import PCA
from sklearn.model_selection import LeaveOneOut, cross_val_score
from sklearn.neighbors import KDTree, KNeighborsRegressor, KNeighborsClassifier


#IMPLEMENTACJA
class KNN:
    def __init__(self, n_neighbors=1, use_KDTree=False):
        self.n_neighbors = n_neighbors
        self.use_KDTree = use_KDTree

    def fit(self, X, y):
        if self.use_KDTree is True:
            self.X = KDTree(X)  #atr wejsciowe
            self.y = y  #atr decyzyjne
        else:
            self.X = X
            self.y = y

    def predict(self, X):
        if self.use_KDTree is True:
            return np.round(np.mean(self.y[self.X.query(X, self.n_neighbors)[1]], axis=1)).astype(int)
        else:
            if X.ndim == 1:
                return np.round(np.mean(self.y[cdist([X], self.X).argsort()[:self.n_neighbors]], axis=1)).astype(int)
            else:
                return np.round(np.mean(self.y[cdist(X, self.X).argsort(axis=1)[:self.n_neighbors]], axis=1)).astype(
                    int)

    def score(self, X, y):
        yScore = self.predict(X)
        dokladnosc = np.mean(yScore == y) * 100  # dla osiagniecia wynikow w %
        return dokladnosc

    def blad(y_true, y_pred):
        return np.mean((y_true - y_pred) ** 2)


#KLASYFIKACJA
#1
def klasyfikacja():
    X, y = datasets.make_classification(
        n_samples=100,
        n_features=2,
        n_informative=2,
        n_redundant=0,
        n_repeated=0,
        random_state=3,
    )
    #2
    pkt2 = KNN(n_neighbors=3, use_KDTree=True)
    pkt2.fit(X, y)

    #3
    minX = X[:, 0].min() - 1
    maxX = X[:, 0].max() + 1
    minY = y.min() - 1
    maxY = y.max() + 1

    # wizualizacja
    xx, yy = np.meshgrid(np.linspace(minX, maxX, 1000),
                         np.linspace(minY, maxY, 1000))

    Z = pkt2.predict(np.c_[xx.ravel(), yy.ravel()])
    Z = Z.reshape(xx.shape)
    plt.contourf(xx, yy, Z, alpha=0.8, cmap='viridis')
    plt.scatter(X[:, 0], X[:, 1], c=y, cmap='coolwarm', edgecolor='k')
    plt.title("wizualizacja 2D")
    plt.show()


def klasyfikacja45():
    iris = datasets.load_iris()
    X = iris.data
    y = iris.target

    pca = PCA(n_components=2)
    pcaX = pca.fit_transform(X)
    klasyfikacja = KNeighborsClassifier(n_neighbors=3)
    klasyfikacja.fit(pcaX, y)

    minX = pcaX[:, 0].min() - 1
    maxX = pcaX[:, 0].max() + 1
    minY = pcaX[:, 1].min() - 1
    maxY = pcaX[:, 1].max() + 1
    xx, yy = np.meshgrid(np.arange(minX, maxX, 0.05), np.arange(minY, maxY, 0.05))

    Z = klasyfikacja.predict(np.c_[xx.ravel(), yy.ravel()])
    Z = Z.reshape(xx.shape)

    plt.contourf(xx, yy, Z, alpha=0.5, cmap='turbo')
    plt.scatter(pcaX[:, 0], pcaX[:, 1], c=y, cmap='spring', edgecolor='k')
    plt.title("Klasyfikacja 5")
    plt.show()


def klasyfikacja6():
    iris = datasets.load_iris()
    X = iris.data
    y = iris.target

    oneOut = LeaveOneOut()
    wal = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
    tab = pd.DataFrame(index=wal, columns=['dokladnosc procentowa'])

    for i in wal:
        scores = []
        for train_index, test_index in oneOut.split(X):
            test = KNN(n_neighbors=i, use_KDTree=True)
            test.fit(X[train_index], y[train_index])
            wynik = test.score(X[test_index], y[test_index])
            scores.append(wynik)
        srednia = np.mean(scores)
        tab.at[i, 'dokladnosc procentowa'] = srednia
    print(tab)


def klasyfikacja7():
    iris = datasets.load_iris()
    X = iris.data
    y = iris.target

    czasDrzewo = []
    czasBez = []
    wal = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]

    for i in wal:
        startZ = time.time()
        testZ = KNN(n_neighbors=i, use_KDTree=True)
        testZ.fit(X, y)
        stopZ = time.time()
        czasDrzewo.append(stopZ - startZ)

        startBez = time.time()
        testBez = KNN(n_neighbors=i, use_KDTree=False)
        testBez.fit(X, y)
        stopBez = time.time()
        czasBez.append(stopBez - startBez)

    plt.figure()
    plt.plot(wal, czasDrzewo, label='KDTree = True')
    plt.plot(wal, czasBez, label='KDTree = False')
    plt.xlabel('sąsiedzi')
    plt.ylabel('czas')
    plt.legend()
    plt.show()


#REGRESJA
def regresja():
    X, y = datasets.make_regression(
        n_samples=100,
        n_features=1,
        n_informative=1,
        noise=5,
        random_state=3,
    )

    knn = KNeighborsRegressor(n_neighbors=3)
    knn.fit(X, y)

    yPred = knn.predict(X)

    err = KNN.blad(y, yPred)

    plt.scatter(X[:, 0], y, color='green', label='dane')
    plt.plot(X[:, 0], yPred, color='red', label='knn')
    plt.legend()
    plt.show()

    print("Błąd średniokwadratowy: ", err)

    california = datasets.fetch_california_housing()
    X = california.data
    y = california.target

    wal = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
    wyniki = []

    for k in wal:
        knn = KNeighborsRegressor(n_neighbors=k)
        scores = cross_val_score(knn, X, y, cv=10, scoring='neg_mean_squared_error')
        wyniki.append(-scores.mean())

    tab = pd.DataFrame(wyniki, index=wal, columns=['blad sredniokwadratowy'])
    print(tab)


