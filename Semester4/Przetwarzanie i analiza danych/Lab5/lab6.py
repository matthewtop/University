from sklearn import datasets
from sklearn.decomposition import PCA
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

iris = datasets.load_iris()
# print("Nazwy cech (kolumn):", iris.feature_names)
# print("Nazwy klas (target_names):", iris.target_names)
# print(iris.data[:5]) # pierwsze 5 probek danych
# print(iris.target[:5]) #klasy dla pierwszych 5 probek

X = iris.data
y = iris.target
target_names = iris.target_names

pca = PCA(n_components=2)
X_r = pca.fit(X).transform(X)

print(
    "explained variance ratio (first two components): %s"
    % str(pca.explained_variance_ratio_)
)
plt.figure()
colors = ["red", "green", "blue"]
lw = 2

for color, i, target_name in zip(colors, [0, 1, 2], target_names):
    plt.scatter(
        X_r[y == i, 0], X_r[y == i, 1], color=color, alpha=0.8, lw=lw, label=target_name
    )
plt.legend(loc="best", shadow=False, scatterpoints=1)
plt.title("PCA of IRIS dataset")
plt.show()




def generujObiekty(iloscObiektow):
    obiekty = np.random.rand(iloscObiektow, 2)
    # print(obiekty)
    return obiekty


def wizualizuj(iloscObiektow):
    plt.figure()
    plt.scatter(iloscObiektow[:, 0], iloscObiektow[:, 1], color='green')
    plt.show()



def wiPCA(X, n_components):
    print(1)

# wizualizuj(generujObiekty(200))
