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

def entropy(x,y):
    h =0
    prob = freq2(x,y)
    for p in prob:
        h -= p * np.log2(p)
    return h

print("entropia dwoch zmiennych:")

def infogain(x, y):
    return entropy(x)-entropy(y)




def zadanie3():
    print("Entropia: \n", entropy(zoo['type']))

    print("Przyrost informacji \n", infogain(zoo['feathers'],zoo['tail']))


zadanie3()
