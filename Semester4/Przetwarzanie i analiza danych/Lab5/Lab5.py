import numpy as np
import pandas as pd

zoo = pd.read_csv('zoo.csv')


def freq(x, prob="True"):
    pi = dict()
    n = x.shape[0]
    for xx in x:
        if xx in pi:
            pi[xx] += 1
        else:
            pi[xx] = 1
    if prob:
        for k in pi:
            pi[k] /= n
    return pi


def zadanie1():
    print("Prawdopodobienstwo wystąpienia poszczególnych danych: \n", freq(zoo['type'], True))
    print("Liczność danych: \n", freq(zoo['type'], False))


def freq2(x, y, prob="True"):
    pi = dict()
    n = x.shape[0]

    for i, j in zip(x, y):
        if (i, j) in pi:
            pi[(i, j)] += 1
        else:
            pi[(i, j)] = 1
        if prob:
            for k in pi:
                pi[k] /= n
        return pi


def zadanie2():
    print("Prawdopodobienstwo kolumn \n", freq2(zoo['type'], zoo['tail']))
    print("Licznosc kolumn: \n", freq2(zoo['type'], zoo['tail'], False))


def entropy(x):
    h = 0
    prob = freq(x).values()
    for p in prob:
        h -= p * np.log2(p)
    return h


def entropy2Zmiennych(x, y):
    h = 0
    prob = freq2(x, y).values()
    for p in prob:
        h -= p * np.log2(p)
    return h


def infogain(x, y):
    return entropy(x) - entropy2Zmiennych(x, y)


def zadanie3():
    print("Entropia: \n", entropy(zoo['type']))

    print("Przyrost informacji \n", infogain(zoo['feathers'], zoo['tail']))


def zadanie4():
    type = zoo.drop(columns=['type'])

    przyrost = {}
    for column in type.columns:
        przyrost[column] = infogain(type[column], zoo['type'])

    posortowaneInfo = sorted(przyrost.items(), key=lambda x: x[1], reverse=True)

    print("Przyrost informacji dla poszczególnych kolumn:")
    for column, przyrostVal in posortowaneInfo:
        print("{}: {}".format(column, przyrostVal))


zadanie4()
