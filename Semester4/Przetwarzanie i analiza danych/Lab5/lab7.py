from scipy.spatial.distance import cdist
from sklearn import datasets
from sklearn.decomposition import PCA
from sklearn.model_selection import LeaveOneOut
from sklearn.neighbors import KDTree
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd


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
        return 1


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
    x1, y1 = np.meshgrid(np.linspace(minX, maxX, 1000),
                         np.linspace(minY, maxY, 1000))  # tu ewentualnie zmniejszyc jakby za dlugo sie robilo
    z = pkt2.predict(np.c_[x1.ravel(), y1.ravel()])
    z = z.reshape(x1.shape)
    plt.contourf(x1, y1, z, alpha=0.8, cmap='viridis')
    plt.scatter(X[:, 0], X[:, 1], c=y, cmap='coolwarm', edgecolor='k', s=25)
    plt.title("wizualizacja 2D")
    plt.show()


def klasyfikacja45():
    iris = datasets.load_iris()
    x = iris.data
    y = iris.target

    pca = PCA(n_components=2)
    pca_x = pca.fit_transform(x)
    zad4 = KNN(n_neighbors=3, use_KDTree=True)
    zad4.fit(pca_x, y)

    minX = pca_x[:, 0].min() - 1
    maxX = pca_x[:, 0].max() + 1
    minY = pca_x[:, 1].min() - 1
    maxY = pca_x[:, 1].max() + 1

    x2, y2 = np.meshgrid(np.linspace(minX, maxX, 1000), np.linspace(minY, maxY, 1000))
    Z = zad4.predict(np.c_[x2.ravel(), y2.ravel()])
    Z = Z.reshape(x2.shape)

    plt.contourf(x2, y2, Z, alpha=0.5, cmap='turbo')
    plt.scatter(pca_x[:, 0], pca_x[:, 1], c=y, cmap='spring', edgecolor='k', s=25)
    plt.title("Wizualizacja 3D")
    plt.show()

def klasyfikacja67():
    print("do dokonczenia")




klasyfikacja45()
