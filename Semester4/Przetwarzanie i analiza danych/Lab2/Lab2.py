import numpy as np
import pandas as pd
from scipy import stats
import matplotlib.pyplot as plt


def manipulowanie1():
    daty = pd.date_range("20200301", periods=5)

    # print(daty)
    tabela = pd.DataFrame(np.random.randn(5, 3), index=daty, columns=list("ABC"))
    tabela.index.name = "data"
    print(tabela)


def manipulowanie2():
    id = np.arange(1, 21)
    data = np.random.randint(1, 1001, size=(20, 3))
    tabela = pd.DataFrame(data, index=id, columns=list("ABC"))
    tabela.index.name = "id"
    print(tabela)

    print("Trzy pierwsze wiersze z tabeli: \n", tabela.head(3))
    print("Trzy ostatnie wiersze z tabeli: \n", tabela.tail(3))
    print("Nazwa indexu: ", tabela.index.name)
    print("Nazwy kolumn: ", tabela.columns)
    print(tabela.to_numpy())
    print("5 losowych wierszy: \n", tabela.sample(5))
    print("Tylko wartości kolumny A: \n", tabela.values[:, 0])
    print("Kolumna A i B:\n", tabela.iloc[0:20, 0:2])
    print("3 pierwsze wiersze A i B: \n", tabela.iloc[0:3, 0:2])
    print("Wiersz piąty: \n", tabela.iloc[4:5])
    print("Wiersze 0,5,6,7 i kolumny 1,2: \n",
          tabela.iloc[[0, 5, 6, 7], [0, 1]])


def manipulowanie3():
    id = np.arange(1, 21)
    data = np.random.randint(-100, 101, size=(20, 3))
    tabela = pd.DataFrame(data, index=id, columns=list("ABC"))
    tabela.index.name = "id"
    print(tabela)
    print(tabela.describe())
    print("Dane większe od 0: \n", tabela > 0)
    print("Tylko takie dane: \n", tabela[tabela > 0])
    print("Dane tylko z kolumny A większe od 0: \n", tabela[tabela["A"] > 0])
    print("Średnia w kolumnach: \n", tabela.mean())
    print("Średnia każdego wiersza: \n", tabela.mean(axis=1))


def manipulowanie4():
    id = np.arange(1, 4)
    id1 = np.arange(4, 7)
    data = np.random.randint(-10, 11, size=(3, 3))
    data1 = np.random.randint(-20, 21, size=(3, 3))
    tabela1 = pd.DataFrame(data, index=id, columns=list("ABC"))
    tabela2 = pd.DataFrame(data1, index=id1, columns=list("ABC"))
    print("Tabela 1: \n", tabela1)
    print("Tabela 2: \n", tabela2)

    polaczona = pd.concat([tabela1, tabela2])
    print("Połączone tabele: \n", polaczona)

    print("Transponowana tabela: \n", polaczona.transpose())


def sortowanie():
    df = pd.DataFrame({"x": [1, 2, 3, 4, 5], "y": ['a', 'b', 'a',
                                                   'b', 'b']}, index=np.arange(5))
    df.index.name = 'id'
    print(df)
    print("Dane posortowane po id malejąco: \n", df.sort_index(ascending=False))
    print("Dane posortowane po y rosnąco: \n", df.sort_values(by="y"))


def grupowanieDanych():
    slownik = {'Day': ['Mon', 'Tue', 'Mon', 'Tue', 'Mon'], 'Fruit': ['Apple', 'Apple', 'Banana', 'Banana', 'Apple'],
               'Pound': [10, 15, 50, 40, 5], 'Profit': [20, 30, 25, 20, 10]}
    df3 = pd.DataFrame(slownik)
    print(df3)
    print(df3.groupby('Day').sum())
    print(df3.groupby(['Day', 'Fruit']).sum())


def wypelnianieDanych():
    df = pd.DataFrame(np.random.randn(20, 3), index=np.arange(20), columns=['A', 'B', 'C'])
    df.index.name = 'id'
    print(df)
    df['B'] = 1  # Wszystkie wiersze kolumny B mają przypisaną wartość 1
    print(df)
    df.iloc[1, 2] = 10  # Przypisanie wartości 10 w miejscu [1,2]
    print(df)
    df[df < 0] = -df  # Przypisanie w miejscach gdzie jes wartość ujemna, wartości jej przeciwnej
    print(df)


def uzupelnianieDanych():
    df = pd.DataFrame(np.random.randn(5, 3), index=np.arange(5), columns=['A', 'B', 'C'])
    df.index.name = 'id'
    print(df)
    df.iloc[[0, 3], 1] = np.nan  # Przypisanie do wartosci 0 i 3 kolumny 1(tutaj B) wartosci NaN
    print(df)
    df.fillna(0, inplace=True)  # w miejsce przypisanych wcześniej wartosci NaN wstawiona jest wartość 0
    print(df)
    df.iloc[[0, 3], 1] = np.nan
    print(df)
    df = df.replace(to_replace=np.nan, value=-9999)  # zastąpienie wartości NaN wartością -9999
    print(df)
    df.iloc[[0, 3], 1] = np.nan
    print(df)
    print(pd.isnull(df))
    # Zwraca informację o tym gdzie znajdują się wartości NaN, False jeśli wartość jest różna od NaN,


#     True, jeśli wartość wynosi NaN

def zadanie1():
    df = pd.DataFrame({"x": [1, 2, 3, 4, 5], 'y': ['a', 'b', 'a', 'b', 'b']})
    print(df)
    df = df.groupby('y')['x'].mean()
    print("Średnie wartosci x w grupach wyznaczonych przez y: \n", df)


def zadanie2():
    df = pd.DataFrame({"x": [1, 2, 3, 4, 5], 'y': ['a', 'b', 'a', 'b', 'b']})
    print("Rozkład liczności atrybutów: \n", df.value_counts())


def zadanie34567():
    load = np.loadtxt('autos.csv', delimiter=',', skiprows=1, dtype=str)
    print(load)
    read = pd.read_csv('autos.csv')
    print("\n", read)
    zuzycie = read.groupby('make')[['city-mpg', 'highway-mpg']].mean()

    print("Średnie zużycie paliwa dla każdego producenta:\n", zuzycie)

    fuel = read.groupby('make')['fuel-type'].value_counts()
    print("Liczność atrybutu fuel-type po zmiennej make: \n", fuel)

    pierwszy = np.polyfit(read['length'], read['city-mpg'], 1)
    print("Współczynniki wielomianu 1 stopnia: \n", pierwszy)

    drugi = np.polyfit(read['length'], read['city-mpg'], 2)
    print("Współczynniki wielomianu 2 stopnia: \n", drugi)

    wartosci_x = np.linspace(read['length'].min(), read['length'].max(), 100)
    ypierwszy = np.polyval(pierwszy, wartosci_x)
    print("Prognozowane wartości dla wielomianu 1 stopnia: \n", ypierwszy)
    ydrugi = np.polyval(drugi, wartosci_x)
    print("Prognozowane wartości dla wielomianu 2 stopnia \n", ydrugi)

    korelacja = stats.pearsonr(read['length'], read['city-mpg'])
    print("Współczynnik korealcji liniowej: ", korelacja)


def zadanie8():
    read = pd.read_csv('autos.csv')
    x = read['length']
    y = read['city-mpg']
    xval = np.linspace(x.min(), x.max(), 100)
    wspolczynniki1 = np.polyfit(x, y, 1)
    wspolczynniki2 = np.polyfit(x, y, 2)
    yval1 = np.polyval(wspolczynniki1, xval)
    yval2 = np.polyval(wspolczynniki2, xval)

    plt.figure(figsize=(10, 10))
    plt.scatter(x, y, color='b', label='probki')
    plt.plot(xval, yval1, color='g')
    plt.plot(xval, yval2, color='r')

    plt.xlabel('length')
    plt.ylabel('city-mpg')
    plt.legend()
    plt.title("Mateusz Tołpa")
    plt.show()


def zadanie9():
    read = pd.read_csv('autos.csv')
    x = read['length']
    estymator = stats.gaussian_kde(x)
    xval = np.linspace(x.min(), x.max(), 100)
    plt.figure(figsize=(10, 10))
    plt.plot(xval, estymator(xval), color='r')
    plt.scatter(xval, estymator(xval), color='b', label="próbki")
    plt.xlabel('length')
    plt.ylabel('gestosc')
    plt.title("Wizualizacja jednowymiarowego estymatora funkcji")
    plt.legend()
    plt.show()


def zadanie10():
    read = pd.read_csv('autos.csv')
    x1 = read['length']
    x2 = read['width']
    estymator1 = stats.gaussian_kde(x1)
    estymator2 = stats.gaussian_kde(x2)
    xval1 = np.linspace(x1.min(), x1.max(), 100)
    xval2 = np.linspace(x2.min(), x2.max(), 100)

    fig, (ax, ax2) = plt.subplots(1, 2)
    ax.plot(xval1, estymator1(xval1), color='r', label="gestosc")
    ax.scatter(xval1, estymator1(xval1), color='b', label="próbki")
    ax.set_ylabel('gestosc')
    ax.legend()

    ax2.plot(xval2, estymator2(xval2), color='r', label="gestosc")
    ax2.scatter(xval2, estymator2(xval2), color='b', label="probki")
    ax2.set_ylabel('gestosc')
    ax2.legend()

    plt.show()


def zadanie11():
    read = pd.read_csv('autos.csv')
    x1 = read['length']
    x2 = read['width']

    estymator = stats.gaussian_kde(np.vstack([x1, x2]))
    x1mesh, x2mesh = np.meshgrid(np.linspace(x1.min(), x1.max(), 100), np.linspace(x2.min(), x2.max(), 100))
    punkty = np.vstack([x1mesh.ravel(), x2mesh.ravel()])
    gestosc = np.reshape(estymator(punkty), x1mesh.shape)

    plt.figure()
    plt.scatter(x1, x2, label="probki")
    plt.contourf(x1mesh, x2mesh, gestosc)

    plt.ylabel("szerokosc")
    plt.colorbar(label='gestosc')
    plt.legend()
    plt.savefig("zadanie11.png")
    plt.savefig("zadanie11.pdf")
    plt.show()



zadanie11()
