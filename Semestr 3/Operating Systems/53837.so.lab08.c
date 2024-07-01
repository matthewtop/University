// SO IS1 222B LAB08
// Mateusz Tołpa
// tm53837@zut.edu.pl
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <ctype.h>

int main(int argc, char *argv[]) {
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

    pid_t childPid1, childPid2;
    if ((childPid1 = fork()) == 0) {
        execl(argv[0], argv[0], polowa1, (char *)NULL);
        exit(1);
    } else if ((childPid2 = fork()) == 0) {
        execl(argv[0], argv[0], polowa2, (char *)NULL);
        exit(1);
    } else {
        int status1, status2;

        waitpid(childPid1, &status1, 0);
        waitpid(childPid2, &status2, 0);

        printf("%d %d %d %d\n", getpid(), childPid1, atoi(polowa1), WEXITSTATUS(status1));
        printf("%d %d %d %d\n", getpid(), childPid2, atoi(polowa2), WEXITSTATUS(status2));

        return WEXITSTATUS(status1) + WEXITSTATUS(status2);
    }
}
