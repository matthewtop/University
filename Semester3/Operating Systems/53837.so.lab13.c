//SO IS1 222B LAB13
//Mateusz Tołpa
//tm53837@zut.edu.pl
#include <cstdio>
#include <cstdlib>
#include <windows.h>

HANDLE semaphore;
void initSemaphore() {
    semaphore = CreateSemaphore(nullptr, 1, 1, reinterpret_cast<LPCSTR>(L"KolkoKrzyzyk"));
}

void lockSemaphore() {
    WaitForSingleObject(semaphore, INFINITE);
}

void unlockSemaphore() {
    ReleaseSemaphore(semaphore, 1, nullptr);
}

int wygrana(const char *tab) {
    for (int i = 0; i < 3; ++i) {
        if (tab[i] != '-' && tab[i] == tab[i + 3] && tab[i + 3] == tab[i + 6]) {
            return '1';  // pion
        }
    }

    for (int i = 0; i < 9; i += 3) {
        if (tab[i] != '-' && tab[i] == tab[i + 1] && tab[i + 1] == tab[i + 2]) {
            return '1';  // poziom
        }
    }

    if (tab[2] != '-' && tab[2] == tab[4] && tab[4] == tab[6]) {
        return '1';  // przekatna prawa gora
    }

    if (tab[0] != '-' && tab[0] == tab[4] && tab[4] == tab[8]) {
        return '1';  // przekatna lewa gora
    }

    return '0';  // remis
}
int czyPuste(const char *tab) {
    for (int i = 0; i < 9; i++) {
        if (tab[i] == '-') {
            return '0';  // gra dalej trwa
        }
    }
    return '1';  // remis
}
int start(const char *tab) {
    for (int i = 0; i < 9; i++) {
        if (tab[i] != '-') {
            return '0';  // gra rozpoczeta
        }
    }
    return '1';
}
void wyswietlanie(char *tab)
{
    system("cls");
    printf(" %c | %c | %c  \n",
           tab[0],tab[1], tab[2]);
    printf("-----------\n");
    printf(" %c | %c | %c  \n",
           tab[3],tab[4], tab[5]);
    printf("-----------\n");
    printf(" %c | %c | %c  \n\n",
           tab[6],tab[7], tab[8]);
}

int main(int argc, char **argv) {
    if (argc != 2) {
        fprintf(stderr, "Musi byc jeden argument\n");
        return 1;
    }

    char *gra;
    HANDLE MapFile = CreateFileMapping(INVALID_HANDLE_VALUE, nullptr, PAGE_READWRITE, 0, 1000, argv[1]);
    //wykonanie pozornego mapowania niestniejacego pliku na pamiec operacyjna

    gra = static_cast<char *>(MapViewOfFile(MapFile, FILE_MAP_ALL_ACCESS, 0, 0, 1000));
    //uzyskanie adresu zamapowanego pliku
    int n = 0;
    gra[10] = '0'; //wygrana
    gra[11] = '0'; //remis
    gra[12] = '0'; //start
    int do_while1 = 0;

    for (int i = 0; i < 9; i++) {
        gra[i] = '-';
    }

    gra[9] = 'X';

    initSemaphore();

    while (1 == 1) {
        wyswietlanie(gra);
        if (gra[10] == '1' || gra[11] == '1') {
            if (gra[10] == '1') {
                printf("Wygrywa gracz: %c\n ", gra[9]);
                break;
            } else {
                printf("Remis:\n ");
                break;
            }
        }

        lockSemaphore();
        wyswietlanie(gra);  // aktualizacja planszy

        gra[12] = start(gra);

        if (gra[12] == '0') {
            printf("Gra gracz: %c\n ", gra[9]);
        }

        printf("Podaj lokalizacje: ");
        do_while1 = 0;

        do {
            scanf("%d", &n); //pobiera i przypisuje do n

            if (n > 0 && n < 10 && gra[n - 1] == '-') {
                do_while1 = 1;

                if (n == 10) {
                    gra[10] = '1';
                }
            } else {
                printf("Zly ruch. Sprobuj ponownie.\n");
            }
        } while (do_while1 == 0);

        gra[n - 1] = gra[9];

        wyswietlanie(gra);

        gra[10] = wygrana(gra);

        unlockSemaphore();

        if (gra[10] == '1') {
            wyswietlanie(gra);
            printf("Wygrywa gracz: %c\n ", gra[9]);
            break;
        }

        gra[11] = czyPuste(gra);

        if (gra[11] == '1') {
            wyswietlanie(gra);
            printf("Remis\n");
            break;
        }

        if (gra[9] == 'X') {
            gra[9] = 'O';
        } else {
            gra[9] = 'X';
        }

        if (gra[9] == 'X') {
            printf("Czekaj na 1 gracza\n ");
            while (gra[9] == 'X') {
                if (gra[10] == '1' || gra[11] == '1') {
                    if (gra[10] == '1') {
                        printf("Wygrywa gracz: %c\n ", gra[9]);
                        break;
                    } else {
                        printf("Remis:\n ");
                        break;
                    }
                }
            }
        } else {
            printf("Czekaj na 2 gracza\n ");
            while (gra[9] == 'O') {
                if (gra[10] == '1' || gra[11] == '1') {
                    if (gra[10] == '1') {
                        printf("Wygrywa gracz: %c\n ", gra[9]);
                        break;
                    } else {
                        printf("Remis:\n ");
                        break;
                    }
                }
            }
        }
        wyswietlanie(gra);
    }
    UnmapViewOfFile(MapFile);
    CloseHandle(MapFile);
    return 0;
}