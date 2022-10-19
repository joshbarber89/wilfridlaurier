/* 
 * Josh Barber Assignment 1
 */
#include "stdio.h"
#include "stdlib.h"
#include "mpi.h"

int **write_data(int rank, int p, MPI_Status status);
void read_data(int **finalMatrix, int rank, int p, MPI_Status status);
  
int main(int argc, char* argv[])
{

  int         rank;          /* rank of process      */
  int         p;             /* number of processes  */
  MPI_Status  status;        /* status for receive   */


  /* Start up MPI */
  MPI_Init(&argc, &argv);
  
  /* Find out process rank  */
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  
  /* Find out how many processors */
  MPI_Comm_size(MPI_COMM_WORLD, &p);
  
  int **finalMatrix;
  
  // part a of q2
  finalMatrix = write_data(rank, p, status);
  
  // part b of q2
  read_data(finalMatrix, rank, p, status);  

  MPI_Finalize();
  
  return 0;
}

int **write_data(int rank, int p, MPI_Status status) {
  int A[p][p];
  
  MPI_Datatype matrix;
  MPI_Type_vector(p, p, p, MPI_INT, &matrix);
  MPI_Type_commit(&matrix);
  
  srand(rank+1);
  
  int i, j;
  int stage = 0;
  int m = p * p;
  int n = p * p;
  
  // allocate memory for matrix
  int **finalMatrix = (int**)malloc(sizeof(int*) * m);   
  
  for (i = 0; i < n; i++) {
     finalMatrix[i] = (int*)malloc(sizeof(int) * n); 
  }

  // Going into stages of sending submatrices of random numbers to process 0
  while (stage < p) {
    if (rank == 0) {
      printf("\n\nStage: %d\n\n", stage + 1);
    }
    // Generate 2d array of random numbers
    for (i = 0; i < p; i++) {
       for (j = 0; j < p; j++) {
         int r = rand() % 10;
         A[i][j] = r;
       }  
    }
    
    if (rank != 0) {
      // send submatricie to process 0 if not process 0
      MPI_Send(&A, 1, matrix, 0, 0, MPI_COMM_WORLD);
    } else {
      // print process 0's 2d array
      for (i = 0; i < p; i++) {
         for (j = 0; j < p; j++) {
           printf("%d,", A[i][j]);
           if (j == p - 1) {
             printf("\n");
           }       
           //lets insert 2d random array of integers into the final matrix             
           finalMatrix[stage * p + i][j] = A[i][j];
         }  
      }
      printf("\n");
      int np; 
      // receieve the 2d arrays from the other processes        
      for (np = 1; np < p; np++) {
        int newMatrix[p][p]; 
        MPI_Recv(&newMatrix, 1, matrix, np, 0, MPI_COMM_WORLD, &status);
        // print out the received matrices plus add them into the final matrix        
        for (i = 0; i < p; i++) {
           for (j = 0; j < p; j++) {
             printf("%d,", newMatrix[i][j]);
             if (j == p - 1) {
               printf("\n");
             }
             finalMatrix[stage * p + i][j + (np * p)] = newMatrix[i][j];
           }  
        }
        printf("\n");
      }
      // on the last stage lets print out the final matrix
      if (stage == p - 1) {
        printf("\nFinal Matrix: \n\n");
        for(i = 0; i < m; i++) {
          for (j = 0; j < n; j++) {
            printf("%d,", finalMatrix[i][j]);
            if (j == n - 1) {
               printf("\n");
            }
          }
        }
        printf("\n");
      }
    }
    stage++;    
  }
  // return the final matrix
  return finalMatrix;
}

void read_data(int **finalMatrix, int rank, int p, MPI_Status status) {
  
  
  MPI_Datatype matrix;
  MPI_Type_vector(p, p, p, MPI_INT, &matrix);
  MPI_Type_commit(&matrix);
  
  srand(rank+1);
  
  int i, j;
  int stage = 0;
  int m = p * p;
  int n = p * p;
  
  while (stage < p) {
    // Lets make sure send and recieve are made and we block the process until its complete
    MPI_Barrier(MPI_COMM_WORLD);
    if (rank != 0) {
      // if not process 0, receieve a 2d array of random numbers
      int A[p][p];
      MPI_Recv(&A, 1, matrix, 0, 0, MPI_COMM_WORLD, &status);
      printf("\nStage: %d, Rank: %d\n", stage, rank);
      // print the array within the processer
      for (i = 0; i < p; i++) {
         for (j = 0; j < p; j++) {
           printf("%d,", A[i][j]);
           if (j == p - 1) {
             printf("\n");
           }
         }  
      }
    } else {
      printf("\nStage: %d, Rank: %d\n",stage,  rank);
      // print processs 0's array from final matrix
      for (i = 0; i < p; i++) {
         for (j = 0; j < p; j++) {
           printf("%d,", finalMatrix[stage * p + i][j]);
           if (j == p - 1) {
             printf("\n");
           }
         }  
      }
      int np;
      int B[p][p];
      // pick the final matrix apart and send sub matrix to different processor
      for(np = 1; np < p; np++) {
        for(i =0; i < p; i++) {
          for(j =0; j < p; j++) {
            B[i][j] = finalMatrix[stage * p + i][j + (np * p)];
          }
        }
        MPI_Send(&B, 1, matrix, np, 0, MPI_COMM_WORLD);
      }
    }
    
    stage++;    
  }
  
  return;
}