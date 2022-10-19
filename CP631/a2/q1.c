/*
 * Josh Barber Assignment 2
 */
#include "stdio.h"
#include "stdlib.h"
#include "string.h"
#include "mpi.h"

#define N 200000

// Structure of object to keep track of values
typedef struct {
  float val[N];
} VALUE_T;


// Quick sort O(n log n) from https://beginnersbook.com/2015/02/quicksort-program-in-c/
void quickSort(float number[],int first,int last){
   int i, j, pivot;
   float temp;

   if(first<last){
      pivot=first;
      i=first;
      j=last;

      while(i<j){
         while(number[i]<=number[pivot]&&i<last)
            i++;
         while(number[j]>number[pivot])
            j--;
         if(i<j){
            temp=number[i];
            number[i]=number[j];
            number[j]=temp;
         }
      }

      temp=number[pivot];
      number[pivot]=number[j];
      number[j]=temp;
      quickSort(number,first,j-1);
      quickSort(number,j+1,last);
   }
}


int main(int argc, char* argv[])
{

  int         rank;          /* rank of process      */
  int         p;             /* number of processes  */
  MPI_Status  status;        /* status for receive   */
  int i, j;

  /* init array of numbers */
  float array_of_numbers[N];

  /* Start up MPI */
  MPI_Init(&argc, &argv);

  /* Find out process rank  */
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);

  /* Find out how many processors */
  MPI_Comm_size(MPI_COMM_WORLD, &p);

  /* MPI requests for non blocking comm */
  MPI_Request req;

  /* seeded random */
  srand((rank + 1));

  for (i = 0; i < N; i++) {
    array_of_numbers[i] = (float)rand() / RAND_MAX;
  }

  /* data type structure */
  const int blocklengths[1] = {N};
  MPI_Datatype types[1] = {MPI_FLOAT};
  MPI_Aint offsets[1] = {0};

  /* commit data type */
  MPI_Datatype mpi_value;
  MPI_Type_create_struct(1, blocklengths, offsets, types, &mpi_value);
  MPI_Type_commit(&mpi_value);

  /* Sort all numbers in array */
  quickSort(array_of_numbers, 0, N - 1);

  /* init array of data to send out */
  float sendToProcessor[p][N];

  /* organize data, and specify which processor it is going to */
  for (i = 0; i < p; i++) {
    for (j = 0; j < N; j++) {
      if (rank != i) {
        float bottom = i == 0 ? 0.0 : ((float)i / (float)p);
        float top = (((float)i + 1.0) / (float)p);
        if (bottom <= array_of_numbers[j] && array_of_numbers[j] <= top) {
          sendToProcessor[i][j] = array_of_numbers[j];
        } else {
          sendToProcessor[i][j] = -1.0;
        }
      } else {
        sendToProcessor[i][j] = -1.0;
      }
    }
  }

   VALUE_T buff[p];
   /* Start communication processes and send to appropriate processors */
   for (i = 0; i < p; i++) {
     if (rank != i) {
       VALUE_T value;
       memcpy(value.val,sendToProcessor[i], sizeof(sendToProcessor[i]));
       MPI_Isend(&value, 1, mpi_value, i, 0, MPI_COMM_WORLD, &req);
       MPI_Irecv(&buff[i], 1, mpi_value, i, 0, MPI_COMM_WORLD, &req);
       MPI_Wait(&req, &status);

       for (j =0; j < N; j++) {
         if (buff[i].val[j] != -1.0) {
           printf("received on rank: %d, value: %f\n",rank, buff[i].val[j]);
         }
       }
     }
   }


  MPI_Type_free(&mpi_value);

  /* Shut down MPI */
  MPI_Finalize();

  return 0;
}