from scipy.spatial import ConvexHull
from sklearn import datasets
from sklearn.cluster import AgglomerativeClustering as agc
from scipy.stats import mode
from sklearn.metrics import jaccard_score
from sklearn.decomposition import PCA
import matplotlib.pyplot as plt
import numpy as np


def klasteryzacja():
    iris = datasets.load_iris()
    X = iris.data
    Y = iris.target

    metody = ["single", "average", "complete", "ward"]
    klastry = {}

    for i in metody:
        wynik = agc(n_clusters=3, linkage=i)
        klastry[i] = wynik.fit_predict(X)

    #3
    fittedKlastry = {}
    for i in metody:
        fittedKlastry[i] = find_perm(3, Y, klastry[i])

    #4
    jaccard = {}

    for metoda in metody:
        ypred = find_perm(3, Y, klastry[metoda])
        jaccard[i] = jaccard_score(Y, ypred, average='macro')

    print(jaccard)


    #5
    pca=PCA(n_components=2)
    Xreduced = pca.fit_transform(X)

    fig, axs = plt.subplots(1, 3)

    #oryginal
    axs[0].scatter(Xreduced[:, 0], Xreduced[:, 1], c=Y, cmap='viridis')
    axs[0].set_title('oryginał')

    for i in range(3):
        punkty = Xreduced[Y == i]
        hull = ConvexHull(punkty)
        for simplex in hull.simplices:
            axs[0].plot(punkty[simplex, 0], punkty[simplex, 1], 'k-')

    
    best_method = max(jaccard, key=jaccard.get)
    Y_pred = np.array(fittedKlastry[best_method]).flatten()
    axs[1].scatter(Xreduced[:, 0], Xreduced[:, 1], c=Y_pred, cmap='viridis')




def find_perm(clusters, Y_real, Y_pred):
    perm = []
    for i in range(clusters):
        idx = Y_pred == i
        new_label = mode(Y_real[idx])[0]
        perm.append(new_label)

    return [perm[label] for label in Y_pred]


klasteryzacja()
