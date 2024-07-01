import time

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from sklearn import svm, metrics
from sklearn.datasets import make_classification
from sklearn.discriminant_analysis import QuadraticDiscriminantAnalysis
from sklearn.model_selection import GridSearchCV
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier


def badanieKlasyfikatorow1():
    #1
    X, y = make_classification(
        n_samples=200,
        n_classes=2,
        n_features=2,
        n_clusters_per_class=2,
        random_state=4,
        n_informative=2,
        n_redundant=0
    )

    #2
    plt.scatter(X[:, 0], X[:, 1], c=y, cmap='turbo')
    # plt.show()

    #3
    klasyfikatory = [
        GaussianNB(),
        QuadraticDiscriminantAnalysis(),
        KNeighborsClassifier(),
        svm.SVC(probability=True),
        DecisionTreeClassifier()
    ]

    nazwy = [
        'GaussianNB',
        'QuadraticDiscriminantAnalysis',
        'KNeighborsClassifier',
        'svm.SVC(probability=True)',
        'DecisionTreeClassifier'
    ]

    wyniki = []

    for klasyfikator, nazwa in zip(klasyfikatory, nazwy):
        dokladnosc = []
        czulosc = []
        precyzja = []
        f1 = []
        pole = []
        trening = []
        testowanie = []

        for _ in range(100):
            X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=4, test_size=0.2)

            # trenowanie
            start = time.time()
            klasyfikator.fit(X_train, y_train)
            koniec = time.time()

            # testowanie
            startTest = time.time()
            y_pred = klasyfikator.predict(X_test)
            koniecTest = time.time()

            dokladnosc.append(metrics.accuracy_score(y_test, y_pred))
            czulosc.append(metrics.recall_score(y_test, y_pred))
            precyzja.append(metrics.precision_score(y_test, y_pred))
            f1.append(metrics.f1_score(y_test, y_pred))
            pole.append(metrics.roc_auc_score(y_test, y_pred))

            trening.append(koniec - start)
            testowanie.append(koniecTest - startTest)

            ostatniytest = y_test
            ostatniypred = y_pred
            ostatniXtest = X_test

        wyniki.append(
            {
                'Klasyfikator': nazwa,
                'Accuracy': np.mean(dokladnosc),
                'Recall': np.mean(czulosc),
                'Precision': np.mean(precyzja),
                'F1': np.mean(f1),
                'ROC AUC': np.mean(pole),
                'Train Time': np.mean(trening),
                'Test Time': np.mean(testowanie)
            }
        )

        y_proba = klasyfikator.predict_proba(ostatniXtest)[:, 1]
        fpr, tpr, thresholds = metrics.roc_curve(ostatniytest, y_proba)
        roc_auc = metrics.auc(fpr, tpr)

        plt.figure()
        plt.plot(fpr, tpr, color='blue', lw=2, label='ROC curve (area = %0.2f)' % roc_auc)
        plt.plot([0, 1], [0, 1], color='red', lw=2, linestyle='--')
        plt.xlabel('False Positive Rate')
        plt.ylabel('True Positive Rate')
        plt.title('ROC Curve ' + nazwa)
        plt.legend()
        plt.show()

    wynikiDF = pd.DataFrame(wyniki)
    print(wynikiDF)

    wynikiDF.set_index('Klasyfikator', inplace=True)
    wynikiDF_transposed = wynikiDF.transpose()  #transposonowanie w celu osiagniecia wykresu jak w pliku pdf

    print(wynikiDF_transposed)

    ax = wynikiDF_transposed.plot(kind='bar', alpha=0.8, figsize=(12, 8))
    plt.title('Porównanie klasyfikatorów')
    plt.ylabel('Wartość')
    plt.show()
    fig, axes = plt.subplots(1, 3, figsize=(15, 5))

    axes[0].scatter(ostatniXtest[:, 0], ostatniXtest[:, 1], c=ostatniytest, cmap='bwr', alpha=0.5)
    axes[0].set_title('Oczekiwane')

    # obliczone
    axes[1].scatter(ostatniXtest[:, 0], ostatniXtest[:, 1], c=ostatniypred, cmap='bwr', alpha=0.5)
    axes[1].set_title('Obliczone')

    # roznice
    error = (ostatniytest != ostatniypred)
    axes[2].scatter(ostatniXtest[:, 0], ostatniXtest[:, 1], c=error, cmap='bwr', alpha=0.5)
    axes[2].set_title('Różnice')

    plt.show()


def zadanie2():
    X, y = make_classification(n_samples=200, n_features=2, n_classes=2, random_state=20, n_redundant=0)

    DecisionTreeClassifierParam = {
        'criterion': ['gini', 'entropy'],
        'splitter': ['best', 'random'],
        'max_depth': range(1, 11),
    }

    klasyfikator = DecisionTreeClassifier()
    gridAuc = GridSearchCV(klasyfikator, DecisionTreeClassifierParam, scoring='roc_auc')
    gridAuc.fit(X, y)
    best = gridAuc.best_params_
    print("najlepsze parametry = ", best)

    df = pd.DataFrame(gridAuc.cv_results_)
    print(df)

    #uwazam ze najwazniejsze parametry to criterion oraz max_depth
    #gini mierzy 'czystosc' wezlow,a entropy informacyjna wartosc podzialu,
    # natomiast glebokosc pozwala lepiej dostosowac sie do danych treningowych

    tab = df.pivot_table(index=['param_criterion'], columns=['param_max_depth'], values='mean_test_score')
    plt.imshow(tab, cmap='viridis')
    plt.colorbar()
    plt.show()

    optimum = DecisionTreeClassifier(**best)
    score = []

    for i in range(100):
        dokladnosc = []
        czulosc = []
        precyzja = []
        f1 = []
        pole = []
        trening = []
        testowanie = []
        X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=4, test_size=0.2)

        start = time.time()
        optimum.fit(X_train, y_train)
        koniec = time.time()

        # testowanie
        startTest = time.time()
        y_pred = optimum.predict(X_test)
        koniecTest = time.time()

        dokladnosc.append(metrics.accuracy_score(y_test, y_pred))
        czulosc.append(metrics.confusion_matrix(y_test, y_pred))
        precyzja.append(metrics.precision_score(y_test, y_pred))
        f1.append(metrics.f1_score(y_test, y_pred))
        pole.append(metrics.roc_auc_score(y_test, y_pred))
        trening.append(koniec - start)
        testowanie.append(koniecTest - startTest)

        score.append({
            "dokladnosc": np.mean(dokladnosc),
            "czulosc": np.mean(czulosc),
            "precyzja": np.mean(precyzja),
            "f1": np.mean(f1),
            "pole": np.mean(pole),
            "trening": np.mean(trening),
            "testowanie": np.mean(testowanie)
        })

    wyniki = pd.DataFrame(score)
    # print(wyniki)
    usrednione = wyniki.mean()
    print('usrednione wyniki: \n', usrednione)

    fpr, tpr, thresholds = metrics.roc_curve(y_test, y_pred)
    roc_auc = metrics.auc(fpr, tpr)
    plt.plot(fpr, tpr, label='ROC curve (area = %0.2f)' % roc_auc)
    plt.plot([0, 1], [0, 1], color='red', linestyle='--')
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.legend()
    plt.show()


zadanie2()
