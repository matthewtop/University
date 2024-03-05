# Mateusz Tołpa 53837
import numpy as np


# TABLICE
def tablice1():
    a = np.array([1, 2, 3, 4, 5, 6, 7])
    print(a)


def tablice2():
    b = np.array([[1, 2, 3, 4, 5], [6, 7, 8, 9, 10]])
    print(b)


def tablice3():
    b = np.array([[1, 2, 3, 4, 5], [6, 7, 8, 9, 10]])
    print(b)
    b.transpose()
    print(b)


def tablice4():
    c = np.arange(0, 101)
    print(c)


def tablice5():
    d = np.linspace(0.0, 2.0, num=10)
    print(d)


def tablice6():
    e = np.arange(0, 101, 5)
    print(e)


# ------------------------------------------
# LICZBY LOSOWE
def losowe1():
    f = np.round(np.random.normal(size=20), 2)
    print(f)


def losowe2():
    rng = np.random.default_rng()
    g = rng.integers(low=0, high=1001, size=100)
    print(g)


def losowe3():
    h = np.zeros(shape=(2, 3))
    print(h)
    i = np.ones(shape=(2, 3))
    print(i)


def losowe4():
    j = np.random.randint(low=0, high=101, size=(5, 5), dtype=np.int32)
    print(j)


# ------------------------------------------
# ZADANIA

def zad1():
    a = np.random.uniform(low=0, high=11, size=10)
    print(a)


def zad2():
    a = np.random.uniform(low=0, high=11, size=10)
    b = a.astype(int)
    print(b)


def zad3():
    a = np.random.uniform(low=0, high=11, size=10)
    c = np.round(a).astype(int)
    print(c)


def zad4():
    a = np.random.uniform(low=0, high=11, size=10)
    b = a.astype(int)
    c = np.round(a).astype(int)
    print("Porownanie tablicy a i b: ", np.array_equal(b, c))


# ------------------------------------------
# SELEKCJA DANYCH
# 1,2,3,4,5,6,7
def selekcja():
    b = np.array([[1, 2, 3, 4, 5], [6, 7, 8, 9, 10]], dtype=np.int32)
    print(b)
    print("Wymiary macierzy b: ", b.ndim)
    print("Ilość elementów macierzy b: ", b.size)
    print("Wyciagnieta wartosc 2 i 4: ", b[0, 1], b[0, 3])
    print("Pierwszy wiersz macierzy b: ", b[0])
    print("Wszystkie wiersze z kolumny pierwszej: ", b[:, 0])

    macierzLosowa = np.random.randint(low=0, high=101, size=(20, 7))
    print(macierzLosowa[:, :4])


# ------------------------------------------
# DZIAŁANIA MATEMATYCZNE I LOGICZNE
def dzialania():
    a = np.random.randint(low=1, high=11, size=(3, 3))
    b = np.random.randint(low=1, high=11, size=(3, 3))
    print("a:\n", a)
    print("b:\n", b)

    print("Wynik dodawania z uzyciem '+' =\n ", a + b)
    print("Wynik dodawania z uzyciem 'add'=\n", np.add(a, b))

    print("Wynik odejmowania z uzyciem '-' =\n ", a - b)
    print("Wynik odejmowania z uzyciem 'subtract'=\n", np.subtract(a, b))

    print("Wynik mnozenia z uzyciem '*' =\n ", a * b)
    print("Wynik mnozenia z uzyciem 'multiply'=\n", np.multiply(a, b))
    print("Wynik mnozenia z uzyciem 'dot'=\n", np.dot(a, b))
    print("Wynik mnozenia z uzyciem 'matmul'=\n", np.matmul(a, b))

    print("Wynik dzielenia z uzyciem '/' =\n ", a / b)
    print("Wynik dzielenia z uzyciem 'divide'=\n", np.divide(a, b))

    print("Wynik potegowania z uzyciem '**' =\n ", a ** b)
    print("Wynik potegowania z uzyciem 'power'=\n", np.power(a, b))

    print("Wartosci macierzy a są większe lub równe 4: \n", a >= 4)
    print("Wartości macierzy a są większe lub równe 1 i mniejsze bądź równe 4: \n", (a >= 1) & (a <= 4))

    print("Suma głównej przekątnej macierzy b: ", np.trace(b))


# ------------------------------------------
# DANE STATYSTYCZNE
def statystyczne():
    b = np.random.randint(low=0, high=11, size=(3, 3))
    print(b)
    print("Suma macierzy b: ", np.sum(b))
    print("Minimum macierzy b: ", np.min(b))
    print("Maksimum macierzy b: ", np.max(b))
    print("Odchylenie standardowe macierzy b: ", np.std(b))
    print("Średnia wierszy macierzy b: ", np.mean(b, axis=1))
    print("Średnia kolumn macierzy b: ", np.mean(b, axis=0))


# ------------------------------------------
# RZUTOWANIE WYMIARÓW ZA POMOCĄ REAHPE LUB RESIZE
def rzutowanie():
    test = np.arange(50)
    print(test)
    testReshape = np.reshape(test, newshape=(10, 5))
    print(testReshape)
    testResize = np.resize(test, new_shape=(10, 5))
    print(testResize)

    testRavel = np.ravel(testResize)
    print(testRavel)
    # funkcja ravel sprowadza macierz do jednego wymiaru tworząc z niej tablicę

    x = np.arange(5)
    y = np.arange(4)

    x = x[:, np.newaxis]
    y = y[np.newaxis, :]

    print(x)
    print("Suma tablic: \n", x + y)


# polecenie newaxis dodaje nowy wymiar do tablicy dzięki czemu możemy je przykładowo zsumować.
# dzięki temu polecenie x = x[:,np.newaxis] sprawia, że tablica staje się macierzą o wymiarach 5x1 (staje się kolumną)
# i w przeciwną stronę tablica y staje się wierszem, dzięki temu wynikiem sumy jest macierz o wymiarach 5x4
# ------------------------------------------
# SORTOWANIE DANYCH
def sortowanie():
    a = np.random.randint(0, 100, size=(5, 5))
    print(a)
    print("Wiersze posortowane rosnąco: \n", np.sort(a, axis=1))
    print("Kolumny posortowane malejąco: \n", np.sort(a, axis=0)[::-1])


# Zadania
def zadania():
    b = np.array([(1, 'MZ', 'mazowieckie'), (2, 'ZP', 'zachodniopomorskie'), (3, 'ML', 'małopolskie')])
    print(b)
    print("Dane posortowane rosnąco po kolumnie 2\n", b[b[:, 1].argsort()])

    for wiersz in b:
        if wiersz[1] == 'ZP':
            print(wiersz[2])


# ------------------------------------------
# ZADANIA PODSUMOWUJĄCE
def podsumowujace1():
    jeden = np.random.randint(low=0, high=101, size=(10, 5))
    print(jeden)
    print("Suma głównej przekątnej: ", np.trace(jeden))
    print("Wartości za pomocą funkcji diag:\n", np.diag(jeden))


def podsumowujace2():
    tab1 = np.random.normal(size=(5, 5))
    tab2 = np.random.normal(size=(5, 5))
    print("Wynik mnożenia: \n", np.matmul(tab1, tab2))


def podsumowujace3():
    macierz1 = np.random.randint(low=0, high=101, size=(5, 5))
    macierz2 = np.random.randint(low=0, high=101, size=(5, 5))
    print("Suma macierzy: \n", np.add(macierz1, macierz2))


def podsumowujace4():
    macierz1 = np.random.randint(low=0, high=10, size=(4, 5))
    macierz2 = np.random.randint(low=0, high=101, size=(5, 4))
    print(macierz1)
    print(macierz2)
    macierz1 = np.transpose(macierz1)
    print(macierz1)
    print("Suma macierzy: \n", np.add(macierz1, macierz2))


def podsumowujace5():
    macierz1 = np.random.randint(low=0, high=101, size=(4, 5))
    macierz2 = np.random.randint(low=0, high=101, size=(5, 4))

    macierz1 = np.transpose(macierz1)

    kol3_m1 = macierz1[:, 2]
    kol4_m1 = macierz1[:, 3]

    kol3_m2 = macierz2[:, 2]
    kol4_m2 = macierz2[:, 3]
    print("Wynik pomnożonej kolumny 3: ", np.multiply(kol3_m1, kol3_m2))
    print("Wynik pomnożonej kolumny 4: ", np.multiply(kol4_m1, kol4_m2))

    print(macierz1)
    print(macierz2)


def podsumowujace6():
    normalna = np.random.normal(0, 1, size=(10, 10))
    jednostajna = np.random.uniform(0, 1, size=(10, 10))
    print("Średnia dla :")
    print("Rozkładu normalnego: ", np.mean(normalna), "\nRozkładu jednostajnego: ", np.mean(jednostajna), "\n")

    print("Odchylenie standardowe dla :")
    print("Rozkładu normalnego: ", np.std(normalna), "\nRozkładu jednostajnego: ", np.std(jednostajna), "\n")

    print("Wariancja dla :")
    print("Rozkładu normalnego: ", np.var(normalna), "\nRozkładu jednostajnego: ", np.var(jednostajna), "\n")

    print("Suma dla :")
    print("Rozkładu normalnego: ", np.sum(normalna), "\nRozkładu jednostajnego: ", np.sum(jednostajna), "\n")

    print("Minimum dla :")
    print("Rozkładu normalnego: ", np.min(normalna), "\nRozkładu jednostajnego: ", np.min(jednostajna), "\n")

    print("Maksimum dla :")
    print("Rozkładu normalnego: ", np.max(normalna), "\nRozkładu jednostajnego: ", np.max(jednostajna), "\n")


def podsumowujace7():
    a = np.random.randint(0, 500, size=(4, 4))
    b = np.random.randint(0, 100, size=(4, 4))
    cnorm = a * b
    cdot = np.dot(a, b)
    print("Macierz używając mnożenia z '*':\n", cnorm)
    print("Macierz używając funkcji dot: \n", cdot)

    # Mnożąc używając * wykonujemy mnożenie każdego elementu odpowiadającemu sobie, "element * element",
    # natomiast używając funkcji dot wykonujemy mnożenie w sensie algebraicznym, jest to szczególnie użyteczne w
    # przypadkach mnożenia właśnie macierzy,np w wielu metodach numerycznych, gdzie mnożenie macierzy jest niezbędne


def podsumowujace8():
    a = np.arange(1, 37).reshape(6, 6)
    print("Macierz początkowa: \n", a)
    print("Dane 5 kolumn z 3 pierwszych wierszy: \n",
          np.lib.stride_tricks.as_strided(a, shape=(3, 5), strides=a.strides))


def podsumowujace9():
    a = np.arange(1, 10).reshape(3, 3)
    b = np.arange(10, 19).reshape(3, 3)
    vab = np.vstack((a, b))
    print("Macierz z użyciem vstack: \n", vab)

    sab0 = np.stack((a, b), axis=0)
    print("Macierz z użyciem stack: \n", sab0)

    sab1 = np.stack((a, b), axis=1)
    print("Macierz z użyciem stack: \n", sab1)

#     Funkcja vstack łączy dwie tablice wzdłuż osi pionowej, a funkcja stack
#     w zależnosci od argumentu axis(domyślnie 0), dodatkowo funkcja stack dodaje nowy wymiar.
#     Funkcje te warto stosować kiedy chcemy łączyć tablice ze sobą lub kiedy chcemy dodać nowe dane do juz istniejących

