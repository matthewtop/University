from sklearn import datasets
from sklearn.datasets import load_digits
from sklearn.decomposition import PCA
import numpy as np
import matplotlib.pyplot as plt


def zadanie1():
    np.random.seed()
    rng = np.random.RandomState()
    punkty = np.dot(rng.rand(2, 2), rng.randn(2, 200)).T
    data, wektory, wartosci = wiPCA(punkty, 1)
    plt.figure()
    plt.scatter(punkty[:, 0], punkty[:, 1], color='green', alpha=0.5)

    nowePkt = data * wektory[:, :1].T + np.mean(punkty, axis=0)
    plt.scatter(nowePkt[:, 0], nowePkt[:, 1], color='red', alpha=0.5)

    for i, wektory in zip(wartosci, wektory.T):
        x = wektory * 3 * np.sqrt(i)
        rysujWektor(np.mean(punkty, axis=0), np.mean(punkty, axis=0) + x)
        rysujWektor(np.mean(punkty, axis=0), np.mean(punkty, axis=0) + x)

    plt.axis('equal')
    plt.title('PCA')
    plt.xlabel('X')
    plt.ylabel('Y')
    plt.show()


def rysujWektor(vek0, vek1, axis=None):
    axis = axis or plt.gca()
    arrowprops = dict(arrowstyle='->',
                      linewidth=2,
                      shrinkA=0, shrinkB=0, color='black')
    axis.annotate('', vek1, vek0, arrowprops=arrowprops)


def zadanie2():
    iris = datasets.load_iris()
    Xiris = iris.data
    yiris = iris.target

    X_reduced, V, val = wiPCA(Xiris, 2)

    plt.scatter(X_reduced[:, 0], X_reduced[:, 1], c=yiris)
    plt.title('PCA dla zbioru iris')
    plt.xlabel('Pierwsza składowa')
    plt.ylabel('Druga składowa')

    plt.grid(True)
    plt.show()


def zadanie3():
    digits = load_digits()
    X = digits.data
    y = digits.target

    X_reduced, V, val = wiPCA(X, 2)

    plt.plot(np.cumsum(val) / np.sum(val))
    plt.title('Krzywa wariancji')
    plt.xlabel('Liczba składowych głównych')
    plt.ylabel('Wariancja wyjaśniona')
    plt.grid(True)
    plt.show()

    plt.scatter(X_reduced[:, 0], X_reduced[:, 1], c=y)
    plt.title('PCA dla zbioru digits')
    plt.xlabel('Składowa 1')
    plt.ylabel('Skladowa 2')
    plt.grid(True)
    plt.show()


# pca = PCA(n_components=2)
# X_r = pca.fit(X).transform(X)


def generujObiekty(iloscObiektow):
    obiekty = np.random.rand(iloscObiektow, 2)
    return obiekty


def wizualizuj(iloscObiektow):
    plt.figure()
    plt.scatter(iloscObiektow[:, 0], iloscObiektow[:, 1], color='green')
    plt.show()


def wiPCA(x, elementy):
    srednia = np.mean(x)
    srodek = x - srednia
    covMtr = np.cov(srodek.T)
    walwlasne, vecwlasne = np.linalg.eig(covMtr)

    temp = walwlasne.argsort()[::-1]
    wartoscW = walwlasne[temp]
    vecW = vecwlasne[:, temp]

    v_redukowane = vecW[:, :elementy]
    x_redukowane = np.dot(srodek, v_redukowane)

    return x_redukowane, vecW, wartoscW


zadanie3()
