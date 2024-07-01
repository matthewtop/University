import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.metrics import silhouette_score


def distp(X, C):
    roznice = X[:, np.newaxis, :] - C[np.newaxis, :, :]
    de = np.linalg.norm(roznice, axis=2)
    return de


def distm(X, C, V):
    n, m = X.shape
    k = C.shape[0]

    #roznice miedzy punktami a centroidami
    XC = X[:, np.newaxis, :] - C[np.newaxis, :, :]

    V_inv = np.linalg.inv(V)  #odwrocona macierz kowariancji

    dm = np.zeros((n, k))

    for i in range(n):
        for j in range(k):
            diff = XC[i, j]
            dm[i, j] = np.sqrt(np.dot(np.dot(diff.T, V_inv), diff))

    return dm


def ksrodki(X, k, iter, wybor=1):
    rozmiar = len(X)
    randCent = np.random.choice(rozmiar, k, replace=False)
    C = X[randCent]

    for i in range(iter):
        if wybor == 1:
            odlegosc = distp(X, C)
        elif wybor == 2:
            odlegosc = distm(X, C, np.eye(X.shape[1]))

    CX = np.argmin(odlegosc, axis=1)

    for j in range(k):
        C[j] = np.mean(X[CX == j], axis=0)

    return C, CX


def zad3():
    data = pd.read_csv('autos.csv')
    algorytmData = data[["engine-size", "width", "height"]].values
    C, CX = ksrodki(algorytmData, 5, 200)

    plt.scatter(algorytmData[:, 0], algorytmData[:, 1], c=CX, cmap='viridis')
    plt.scatter(C[:, 0], C[:, 1], c='black', marker='x', label='srodki')
    plt.legend()
    plt.show()


def zad4():
    data = pd.read_csv('autos.csv')

    algorytmData = data[["engine-size", "width", "height"]].values
    C, CX = ksrodki(algorytmData, 5, 200)

    odleglosci = distp(algorytmData, C)
    odleglosciR = odleglosci.reshape(odleglosci.shape[0], -1)
    poziomy = np.argmin(odleglosciR, axis=1)  #poziomy klastrow

    grupowanie = silhouette_score(algorytmData, poziomy)

    print("Jakosc grupowania :", grupowanie)


zad3()
