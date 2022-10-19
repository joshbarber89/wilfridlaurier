/*
 * Josh Barber Assignment 2
 */
#include "stdio.h"
#include "stdlib.h"
#include "time.h"

#define N 500

int sum(int x, int y, int A[N][N]) {
  int s = 0;

  int i,j;
  #pragma omp parallel for collapse(2) reduction (+:s)
  for (i = 0; i <= x; i++) {
    for(j = 0; j <= y; j++) {
      s += A[i][j];
    }
  }

  return s;
}

int main(int argc, char* argv[])
{

  int i, j, ip, jp;
  srand(time(0));

  int A[N][N];
  int B[N][N];

  for (i = 0; i < N; i++) {
    for (j = 0; j < N; j++) {
      A[i][j] = (int)(20 * (1.0 * rand()) / RAND_MAX);
    }
  }

  i = 0;
  j = 0;
  clock_t begin = clock();

  for (ip = 0; ip < N; ip++) {
    for (jp = 0; jp < N; jp++) {
      if (i <= ip && j <= jp) {
        B[i][j] = sum(i, j , A);
        j++;
        if (j == N) {
            j = 0;
            i++;
        }
      }
    }
  }

  /*for (ip = 0; ip < N; ip++) {
    for (jp = 0; jp < N; jp++) {
      printf("%d ", B[ip][jp]);
    }
    printf("\n");
  }*/

  clock_t end = clock();
  double time_spent = (double)(end - begin) * 1000.0/ CLOCKS_PER_SEC;
  printf("time total: %f", time_spent);

  return 0;
}