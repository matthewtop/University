// SO IS1 222B LAB09
// Mateusz Tołpa
// tm53837@zut.edu.pl
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h>


int main(int argc, char* argv[]) {
    int dlugosc = strlen(argv[1]);

    if (argc != 2) {
        fprintf(stderr, "Program wymaga dokladnie jednego argumentu\n");
        exit(1);
    }
    if (dlugosc == 1) {
        int wynik = argv[1][0] - '0';
        return wynik;
    }

    for (int i = 0; i < dlugosc; i++) {
        if (!isdigit(argv[1][i])) {
            fprintf(stderr, "Argument powinien skladac sie tylko z cyfr\n");
            exit(1);
        }
    }
    if (dlugosc < 1 || dlugosc > 25) {
        fprintf(stderr, "Dlugosc argumentu powinna byc w przedziale <1;25>\n");
        exit(1);
    }

    int polowa = dlugosc / 2;
    char polowa1[polowa + 1];
    char polowa2[dlugosc - polowa + 1];
    strncpy(polowa1, argv[1], polowa);
    polowa1[polowa] = '\0';
    strncpy(polowa2, argv[1] + polowa, dlugosc - polowa);
    polowa2[dlugosc - polowa] = '\0';

    char exePath[MAX_PATH];
    GetModuleFileNameA(NULL, exePath, MAX_PATH);

    STARTUPINFO si[2];
    PROCESS_INFORMATION pi[2];

    for (int i = 0; i < 2; ++i) {
        ZeroMemory(&si[i], sizeof(STARTUPINFO));
        si[i].cb = sizeof(STARTUPINFO);
        ZeroMemory(&pi[i], sizeof(PROCESS_INFORMATION));
    }

    DWORD exitCode[2];

    for (int i = 0; i < 2; i++) {
        char* childArg = (i == 0) ? strdup(polowa1) : strdup(polowa2);
        if (childArg == NULL) {
            fprintf(stderr, "blad alokacji\n");
        }
        char fullPath[MAX_PATH + 10];
        snprintf(fullPath, MAX_PATH + 10, "\"%s\" %s", exePath, childArg);

        if (!CreateProcessA(NULL, fullPath, NULL, NULL, FALSE, 0, NULL, NULL, &si[i], &pi[i])) {
            fprintf(stderr, "Nie udalo sie utworzyc child processu\n");
        }
        free(childArg);
    }

    

    for (int i=0; i<2; ++i){
	WaitForSingleObject(pi[i].hProcess, INFINITE);
    }

    for (int i = 0; i < 2; ++i) {
        GetExitCodeProcess(pi[i].hProcess, &exitCode[i]);

        printf("%lu %lu %s %lu\n",
               GetCurrentProcessId(), pi[i].dwProcessId,
               (i == 0) ? strdup(polowa1) : strdup(polowa2),
               exitCode[i]);

        CloseHandle(pi[i].hProcess);
        CloseHandle(pi[i].hThread);
    }

    return exitCode[0] + exitCode[1];
}

