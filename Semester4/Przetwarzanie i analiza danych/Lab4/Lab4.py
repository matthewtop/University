import numpy as np
import pandas as pd
from scipy import stats
import matplotlib.pyplot as plt


# Fs->częstotliwość próbkowania
# tc-> czas trwania sygnału
# próbki N -> fs*tc
# f -> liczba cykli/częstotliwość sygnału
# t->tablica czasu
# s-> wartości sygnału

def liczSinus(Fs, f):
    t = np.arange(0, 1, 1 / Fs)
    s = np.sin(2 * np.pi * f * t)
    return t, s


def rysuj(Fs, f):
    t, s = liczSinus(Fs, f)
    plt.plot(t, s)
    plt.show()


def dyskretyzacja123():
    for i in (20, 21, 30, 45, 50, 100, 150, 200, 250, 1000):
        rysuj(i, 10)


def dyskretyzacja45():
    print(
        "Zadanie 4: \nTwierdzenie to nazywane jest Twierdzeniem Nyquista-Shannona i mówi ono o tym, że należy próbkować"
        "sygnał z częstotliwością conajmnniej 2 razy większą niż najwyższa częstotliwość w sygnale analogowym")
    print("Zadanie 5: \nZjawisko to nazywane jest Aliasingiem.")


def dyskretyzacja67():
    methods = [None, 'none', 'nearest', 'bilinear', 'bicubic', 'spline16',
               'spline36', 'hanning', 'hamming', 'hermite', 'kaiser', 'quadric',
               'catrom', 'gaussian', 'bessel', 'mitchell', 'sinc', 'lanczos']

    np.random.seed(19680801)

    grid = np.random.rand(4, 4)

    fig, axs = plt.subplots(nrows=3, ncols=6, figsize=(9, 6),
                            subplot_kw={'xticks': [], 'yticks': []})

    for ax, interp_method in zip(axs.flat, methods):
        ax.imshow(grid, interpolation=interp_method, cmap='viridis')
        ax.set_title(str(interp_method))

    plt.tight_layout()
    plt.show()


dyskretyzacja67()
