import matplotlib.pyplot as plt
from sklearn import svm, metrics
from sklearn.datasets import make_classification
from sklearn.linear_model import LogisticRegression, Perceptron
from sklearn.model_selection import train_test_split
from sklearn.multiclass import OneVsOneClassifier, OneVsRestClassifier


def rysujKlasyfikacje(X_test, y_test, y_preds, title):
    plt.figure(figsize=(12, 4))

    plt.subplot(1, 3, 1)
    plt.scatter(X_test[:, 0], X_test[:, 1], c=y_test, cmap='turbo', alpha=0.5)
    plt.title('oczekiwane')

    plt.subplot(1, 3, 2)
    plt.scatter(X_test[:, 0], X_test[:, 1], c=y_preds, cmap='turbo', alpha=0.5)
    plt.title('obliczone')

    plt.subplot(1, 3, 3)
    correct = y_test == y_preds
    plt.scatter(X_test[correct, 0], X_test[correct, 1], c='green', label='Correct', alpha=0.5)
    plt.scatter(X_test[~correct, 0], X_test[~correct, 1], c='red', label='Incorrect', alpha=0.5)
    plt.title('różnice')
    plt.legend()

    plt.colorbar()
    plt.suptitle(title)
    plt.show()

def zadania():
    X, y = make_classification(
        n_samples=2000,
        n_classes=4,
        n_informative=10,
        n_redundant=0,
        n_clusters_per_class=2,
        random_state=10
    )

    X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=10, test_size=0.5)

    klasyfikatory = [
        (OneVsOneClassifier(svm.SVC(kernel='linear', probability=True)),
         OneVsRestClassifier(svm.SVC(kernel='linear', probability=True))),
        (OneVsOneClassifier(svm.SVC(kernel='rbf', probability=True)),
         OneVsRestClassifier(svm.SVC(kernel='rbf', probability=True))),
        (OneVsOneClassifier(LogisticRegression()), OneVsRestClassifier(LogisticRegression())),
        (OneVsOneClassifier(Perceptron()), OneVsRestClassifier(Perceptron()))
    ]

    wyniki = []
    ovo_y_preds = []
    ovr_y_preds = []
    for ovo, ovr in klasyfikatory:
        ovo.fit(X_train, y_train)
        ovo_y_pred = ovo.predict(X_test)
        ovoWyniki = {
            'dokladnosc': metrics.accuracy_score(y_test, ovo_y_pred),
            'czulosc': metrics.recall_score(y_test, ovo_y_pred, average='weighted'),
            'precyzja': metrics.precision_score(y_test, ovo_y_pred, average='weighted'),
            'f1': metrics.f1_score(y_test, ovo_y_pred, average='weighted')
        }
        ovo_y_preds.append(ovo_y_pred)

        ovr.fit(X_train, y_train)
        ovr_y_pred = ovr.predict(X_test)
        if hasattr(ovr, "predict_proba"):
            ovr_y_prob = ovr.predict_proba(X_test)
            ovrWyniki = {
                'dokladnosc': metrics.accuracy_score(y_test, ovr_y_pred),
                'czulosc': metrics.recall_score(y_test, ovr_y_pred, average='weighted'),
                'precyzja': metrics.precision_score(y_test, ovr_y_pred, average='weighted'),
                'f1': metrics.f1_score(y_test, ovr_y_pred, average='weighted'),
                'auc': metrics.roc_auc_score(y_test, ovr_y_prob, multi_class='ovo')
            }
        else:
            ovrWyniki = {
                'dokladnosc': metrics.accuracy_score(y_test, ovr_y_pred),
                'czulosc': metrics.recall_score(y_test, ovr_y_pred, average='weighted'),
                'precyzja': metrics.precision_score(y_test, ovr_y_pred, average='weighted'),
                'f1': metrics.f1_score(y_test, ovr_y_pred, average='weighted')
            }
        ovr_y_preds.append(ovr_y_pred)

        wyniki.append({
            'ovo': ovoWyniki,
            'ovr': ovrWyniki
        })

    final_ovo_y_pred = ovo_y_preds[-1]
    final_ovr_y_pred = ovr_y_preds[-1]

    rysujKlasyfikacje(X_test, y_test, final_ovo_y_pred, 'OneVsOne')
    rysujKlasyfikacje(X_test, y_test, final_ovr_y_pred, 'OneVsRest')



    # fpr, tpr, thresholds = metrics.roc_curve(y_test, final_ovo_y_pred)
    # roc_auc = metrics.auc(fpr, tpr)
    # plt.plot(fpr, tpr, label='ROC curve (area = %0.2f)' % roc_auc)
    # plt.plot([0, 1], [0, 1], color='red', linestyle='--')
    # plt.xlabel('False Positive Rate')
    # plt.ylabel('True Positive Rate')
    # plt.legend()
    # plt.show()



    for i, wynik in enumerate(wyniki):
        print(f"Klasyfikator {i + 1}:")
        print("OneVsOne:")
        print(wynik['ovo'])
        print("OneVsRest:")
        print(wynik['ovr'])
        print("\n")

zadania()
