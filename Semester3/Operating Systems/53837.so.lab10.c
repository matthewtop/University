//SO IS1 222B LAB10
//Mateusz Tołpa
//tm53837@zut.edu.pl
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/time.h>
#include <math.h>

typedef struct {
    double *pi;
    int start;
    int end;
    pthread_mutex_t *mutex;
} Watek;


void walidacja(int argc, int n, int w) {
    if (argc != 3) {
        fprintf(stderr, "Potrzeba 2 argumentow\n");
        exit(1);
    }

    if (n <= 1 || w <= 1 || n > 100000000 || w >= n) {
        fprintf(stderr, "Niepoprawne parametry, akceptowany przedzial <1;100000000> w<n\n");
        exit(1);
    }
}

void *liczPi(void *data) {
    Watek *watek = (Watek *)data;

    double sum = 0.0;
    //double localPi = 0.0;
    for (int i = watek->start; i < watek->end; i++) {
        sum += pow(-1,i)*(4.0/(2.0 * i + 1));
    }

    // Secja krytyczna
    pthread_mutex_lock(watek->mutex);
    *(watek->pi) += sum;
    pthread_mutex_unlock(watek->mutex);

    printf("Thread %lx size=%d first==%d\n", pthread_self(), watek->end - watek->start, watek->start);
    printf("Thread %lx sum=%.20f\n", pthread_self(), sum);

    pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
    int n = atoi(argv[1]);
    int w = atoi(argv[2]);

    walidacja(argc, n, w);

    double pi = 0.0;
    pthread_t threads[100];
    Watek watek[100];
    pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
    
    struct timespec start_watki, end_watki;
    clock_gettime(CLOCK_MONOTONIC, &start_watki);

    for (int i = 0; i < w; i++) {
        int start = (n / w) * i;
        int end;

        if (i == w - 1) {
            end = n;
        } else {
            end = (n / w) * (i + 1);
        }

        watek[i].start = start;
        watek[i].end = end;
        watek[i].pi = &pi;
        watek[i].mutex = &mutex;

        pthread_create(&threads[i], NULL, liczPi, (void *)&watek[i]);
    }
    

    for (int i = 0; i < w; i++) {
        pthread_join(threads[i], NULL);
    }

     clock_gettime(CLOCK_MONOTONIC, &end_watki);
    double watki_time = (double)(end_watki.tv_sec - start_watki.tv_sec) + (double)(end_watki.tv_nsec - start_watki.tv_nsec) / 1e9;
    printf(" w/Threads PI=%0.20lf time=%lf\n", pi, watki_time);

    struct timespec start_time, end_time;
    clock_gettime(CLOCK_MONOTONIC, &start_time);

    double sumWoThreads = 0.0;
    for (int i = 0; i < n; i++) {
        sumWoThreads += pow(-1,i)*(4.0/(2.0 * i + 1));
    }

    clock_gettime(CLOCK_MONOTONIC, &end_time);
    double elapsed_time = (end_time.tv_sec - start_time.tv_sec) + (end_time.tv_nsec - start_time.tv_nsec) / 1e9;

    printf("wo/Threads PI=%0.20lf time=%lf\n", sumWoThreads, elapsed_time);

    return 0;
}