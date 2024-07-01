//SO IS1 222B LAB11
//Mateusz Tołpa
//tm53837@zut.edu.pl
#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <time.h>

typedef struct {
    int threadIndex;
    float* data;
    int start;
    int end;
    float partial;
} ThreadData;

float* data;
int n, w;
float suma = 0;
HANDLE mutex;

DWORD WINAPI ThreadFunction(LPVOID lpParam) {
    ThreadData* threadData = (ThreadData*)lpParam;

    printf("Thread #%lu size=%d\n", GetCurrentThreadId(), threadData->end - threadData->start);

    threadData->partial = 0;

    for (int i = threadData->start; i < threadData->end; i++) {
        threadData->partial += threadData->data[i];
    }

    WaitForSingleObject(mutex, INFINITE);
    suma += threadData->partial;
    ReleaseMutex(mutex);

    printf("Thread #%lu sum=%f\n", GetCurrentThreadId(), threadData->partial);

    return 0;
}

void CreateThreads() {
    HANDLE threads[100];
    ThreadData threadDataArray[100];

    for (int i = 0; i < w; i++) {
        threadDataArray[i].threadIndex = i;
        threadDataArray[i].data = data;
        threadDataArray[i].start = i * (n / w);
        if (i == w - 1) {
            threadDataArray[i].end = n;
        } else {
            threadDataArray[i].end = (i + 1) * (n / w);
        }

        threads[i] = CreateThread(NULL, 0, ThreadFunction, &threadDataArray[i], 0, NULL);
    }
    WaitForMultipleObjects(w, threads, TRUE, INFINITE);
}


int main(int argc, char* argv[]) {
    if (argc != 3) {
        fprintf(stderr,"Zla ilosc argumentow\n");
        return 1;
    }

    n = atoi(argv[1]);
    w = atoi(argv[2]);

    if (n <= 0 || w <= 0 || w > n) {
        fprintf(stderr,"Niepoprawny format argumentow.\n");
        return 1;
    }

    data = (float*)malloc(n * sizeof(float));

    srand(0);
    for (int i = 0; i < n; i++) {
        data[i] = 1000. * rand() / RAND_MAX;
    }
    mutex = CreateMutex(NULL, FALSE, NULL);

    CreateThreads();

    printf(" w/Threads: sum=%f, time=%.3fsec\n", suma, (float)clock() / CLOCKS_PER_SEC);

    suma = 0;

    srand(0);
    for (int i = 0; i < n; i++) {
        suma += data[i];
    }

    printf("wo/Threads: sum=%f, time=%.3fsec\n", suma, (float)clock() / CLOCKS_PER_SEC);

    CloseHandle(mutex);

    return 0;
}