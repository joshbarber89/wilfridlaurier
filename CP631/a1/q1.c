/* 
 * Josh Barber Assignment 1
 */
#include "stdio.h"
#include "stdlib.h"
#include "float.h"
#include "math.h"
#include "time.h"
#include "string.h"
#include "mpi.h"

// Structure of object to keep track of values
typedef struct {
  float sol;
  float x;
  float y;
} VALUE_T;
  
int main(int argc, char* argv[])
{

  int         rank;          /* rank of process      */
  int         p;             /* number of processes  */
  MPI_Status  status;        /* status for receive   */
  int t = 0;                 /* default time that has elapsed */ 
  int original_time = time(0); /* actual time */
  float local_min = FLT_MAX;   /* local min on process */



  /* Start up MPI */
  MPI_Init(&argc, &argv);
  
  /* Find out process rank  */
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  
  /* Find out how many processors */
  MPI_Comm_size(MPI_COMM_WORLD, &p);
  
  /* MPI status */
  MPI_Status stats;
  
  /* Default values of the struct */
  VALUE_T value;  
  value.sol = 0.0;
  value.x = 0.0;
  value.y = 0.0;

  // Lets create a data type
  
  /* data type structure */
  const int blocklengths[1] = {3};
  MPI_Datatype types[1] = {MPI_FLOAT};
  MPI_Aint offsets[1] = {0};
 
  /* commit data type */ 
  MPI_Datatype mpi_value;
  MPI_Type_create_struct(1, blocklengths, offsets, types, &mpi_value);
  MPI_Type_commit(&mpi_value);

  /* seeded random */  
  srand((rank + 1));

  /* as long as while loop is less than 5 seconds of finding a significant result, continue */
  while (t <= 5) {
    /* Set random x and y values between -512 - 512 */
    float x = 1024 * (1.0 * rand()) / RAND_MAX - 512;
    float y = 1024 * (1.0 * rand()) / RAND_MAX - 512;
    /* Eggholder eq */
    float sol = -1*(y+47)*sin(sqrt(abs((x/2)+y+47)))-x*sin(sqrt(abs(x-y+47)));    
    
    /* if value is less than the previous minimum and significant, set new minimum */
    if (sol < local_min && (local_min - sol) > 0.1) {
      local_min = sol;
      // reset time to 0
      t = 0;
      // update time
      original_time = time(0);             
      printf("value: %lf rank: %d, x: %lf, y: %lf\n", sol, rank, x ,y);
      // Set struct values
      value.sol = sol;
      value.x = x;
      value.y = y;
    } else {      
      // increase time in seconds
      t = time(0) - original_time;      
    }  
  }
  /* if process is rank is 0 */
  if (rank == 0) {
    VALUE_T buf[p];
    // set process 0's lowest val   
    buf[0] = value;
    int i;
    // set all other processes lowest values
    for(i = 1; i < p; i++) {
      MPI_Recv(&buf[i], 1, mpi_value, i, 0, MPI_COMM_WORLD, &stats);
    }
    i = 0;
    VALUE_T winner;
    float min = FLT_MAX;
    // Find lowest value
    for (i; i < p; i++) {
      if(buf[i].sol < min) {
        min = buf[i].sol;
        winner = buf[i];
      }
    }
    printf("\nLowest value is: %lf, x: %lf y: %lf\n\n", winner.sol, winner.x, winner.y);
  } else {
    // if not process 0, send struct to process 0 to find lowest value
    MPI_Send(&value, 1, mpi_value, 0, 0, MPI_COMM_WORLD);  
  }
 
  // Remove commited data structure
  MPI_Type_free(&mpi_value);
  
  /* Shut down MPI */
  MPI_Finalize();

  return 0;
} 