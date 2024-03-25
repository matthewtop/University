import numpy as np
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
    pies = plt.imread('pies.png')

    methods = [None, 'none', 'nearest', 'bilinear', 'bicubic', 'spline16',
               'spline36', 'hanning', 'hamming', 'hermite', 'kaiser', 'quadric',
               'catrom', 'gaussian', 'bessel', 'mitchell', 'sinc', 'lanczos']

    fig, axs = plt.subplots(nrows=3, ncols=6, figsize=(9, 6),
                            subplot_kw={'xticks': [], 'yticks': []})

    for ax, interp_method in zip(axs.flat, methods):
        ax.imshow(pies, interpolation=interp_method, cmap='viridis')
        ax.set_title(str(interp_method))

    plt.tight_layout()
    plt.show()


def kwantyzacja123():
    tecza = plt.imread('test.PNG')
    macierz = tecza.shape
    print("Wymiary obrazka: ", macierz)
    valPixel = tecza.shape[-1]
    print("Ilosc wartosci opisujacych pojedynczy pixel: ", valPixel)


def kwantyzacja45():
    tecza = plt.imread('test.PNG')
    # niezbędna była zmiana obrazu, ponieważ dla 'pies.png' histogramy pokazywaly wszędzie 0.
    jasnosc = (np.max(tecza, axis=2) + np.min(tecza, axis=2)) / 2
    usrednienie = (np.mean(tecza, axis=2))
    lumin = 0.21 * tecza[:, :, 0] + 0.72 * tecza[:, :, 1] + 0.07 * tecza[:, :, 2]

    histJasnosc = np.histogram(jasnosc.ravel(), bins=256)
    histUsrednienie = np.histogram(usrednienie.ravel(), bins=256)
    histLumin = np.histogram(lumin.ravel(), bins=256)

    plt.figure(figsize=(15, 5))

    plt.subplot(1, 3, 1)
    plt.plot(histJasnosc[1][:-1], histJasnosc[0], color='black')
    plt.title("Jasność")
    plt.xlabel("Wartosc")
    plt.ylabel("Liczba pikseli")
    # plt.subplot(2, 3, 1)
    # plt.imshow(jasnosc, cmap='gray')

    plt.subplot(1, 3, 2)
    plt.plot(histUsrednienie[1][:-1], histUsrednienie[0], color='black')
    plt.title("Uśrednienie")
    plt.xlabel("Wartosc")
    plt.ylabel("Liczba pikseli")
    # plt.subplot(2, 3, 2)
    # plt.imshow(usrednienie, cmap='gray')

    plt.subplot(1, 3, 3)
    plt.plot(histLumin[1][:-1], histLumin[0], color='black')
    plt.title("Luminacja")
    plt.xlabel("Wartosc")
    plt.ylabel("Liczba pikseli")
    # plt.subplot(2, 3, 3)
    # plt.imshow(lumin, cmap='gray')

    plt.show()


def kwantyzacja6():
    tecza = plt.imread('test.PNG')
    lumin = 0.21 * tecza[:, :, 0] + 0.72 * tecza[:, :, 1] + 0.07 * tecza[:, :, 2]
    luminRedukcja = np.histogram(lumin.ravel(), bins=16)

    plt.figure()
    plt.plot(luminRedukcja[1][:-1], luminRedukcja[0], color='black')
    plt.title("Zakres nowych kolorow luminacji")
    plt.xlabel("Wartosc")
    plt.ylabel("Liczba pikseli")
    plt.show()


def kwantyzacja7():
    tecza = plt.imread('test.PNG')
    lumin = 0.21 * tecza[:, :, 0] + 0.72 * tecza[:, :, 1] + 0.07 * tecza[:, :, 2]
    luminRedukcja, bins = np.histogram(lumin.ravel(), bins=16)

    srodek = (bins[:-1] + bins[1:]) / 2  # środek przedziału

    luminKwantyzacja = np.digitize(lumin, bins) - 1

    nowyLumin = srodek[luminKwantyzacja - 1]

    plt.imshow(nowyLumin, cmap='gray')
    plt.title("Nowa macierz 16 kolorow po srodku przedzialu histogramu")
    plt.show()


def kwantyzacja8():
    kwantyzacja123()
    kwantyzacja45()
    kwantyzacja6()
    kwantyzacja7()


def binaryzacja12():
    auto = plt.imread('ailambo.PNG')
    autoHistogram,bins = np.histogram(auto.ravel(), bins=256)
    plt.plot(bins[:-1], autoHistogram, color='black')
    plt.title("Histogram ")
    plt.xlabel("Wartosc piksela")
    plt.ylabel("Liczba pikseli")
    plt.show()



binaryzacja12()
