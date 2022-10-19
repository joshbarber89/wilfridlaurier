
#include "cuda.h" /* CUDA runtime API */
#include "cstdio"
#include "math.h"

#define N 65536

// For Error Checking, print out erorrs
void error_check(cudaError_t cudaStatus, const char* file, int line)
{
    if (cudaStatus != cudaSuccess) {
        ::fprintf(stderr, "CUDA ERROR at %s[%d] : %s\n", file, line, cudaGetErrorString(cudaStatus));
        abort();
    }
}
#define CUDA_CHECK(err) do { error_check(err, __FILE__, __LINE__); } while(0)

// Atomic Operation to find MIN
__device__ __forceinline__ void atomicMinFloat(float* addr, float value) {
    atomicMin((int*)addr, __float_as_int(value));
}


__global__ void saxpy_gpu(float *pointX, float *pointY) {
    // Global var for all threads
    __shared__ float minV;

    //grab thread id
    int i = blockIdx.x * blockDim.x + threadIdx.x;

    if (i<N) {
        //initialize values
        float dist, x1, x2, y1, y2;
        int j;

        float local_min = 2.0f;

        float dist_x, dist_y;

        // set global min
        minV = 2.0f;
        // get point
        x2 = pointX[i];
        y2 = pointY[i];
        // compare point to other points
        for (j = 0; j < N; j++) {
            x1 = pointX[j];
            y1 = pointY[j];

            if (x2 != x1 && y2 != y1) {
                //dist = sqrt(pow((x2 - x1),2) + pow((y2 - y1),2));
                dist_x = x2 - x1;
                dist_y = y2 - y1;
                dist = (dist_x * dist_x) + (dist_y * dist_y);

                if (dist < local_min) {
                    local_min = dist;
                }
            }
        }
        // wait for all other threads to complete
        __syncthreads();
        // assign min value
        atomicMinFloat(&minV, local_min);
        __syncthreads();
        // assign global min val to output
        if (i == 0) {
            pointX[0] = minV;
        }

    }
}

float saxpy_cpu(float *pointX, float *pointY) {
    int i, j;
    float min_dist = 2.0f;
    float x1, y1, x2, y2, dist;

    float dist_x, dist_y;

    for (i = 0; i < N; i++) {
        x1 = pointX[i];
        y1 = pointY[i];

        for (j = 0; j < N; j++) {
            x2 = pointX[j];
            y2 = pointY[j];
            if (x2 != x1 && y2 != y1) {
                // dist = sqrt(pow((x2 - x1),2) + pow((y2 - y1),2));
                dist_x = x2 - x1;
                dist_y = y2 - y1;
                dist = (dist_x * dist_x) + (dist_y * dist_y);
                if (dist < min_dist) {
                    min_dist = dist;
                }
            }
        }
    }
    return min_dist;
}

int main(int argc, char *argv[]) {
    float *x_host, *y_host;   /* arrays for computation on host*/
    float *x_dev, *y_dev;     /* arrays for computation on device */
    float *min_dist; /* array for results */

    size_t memsize;
    int i, blockSize, nBlocks;

    memsize = N * sizeof(float);

    /* allocate arrays on host */

    x_host = (float *)malloc(memsize);
    y_host = (float *)malloc(memsize);
    min_dist = (float *)malloc(memsize);

    /* allocate arrays on device */
    CUDA_CHECK(cudaMalloc((void **) &x_dev, memsize));
    CUDA_CHECK(cudaMalloc((void **) &y_dev, memsize));


    // Add particle values
    for ( i = 0; i < N; i++) {
        x_host[i] = rand() / (float)RAND_MAX;
        y_host[i] = rand() / (float)RAND_MAX;
    }

    /* copy arrays to device memory */
    CUDA_CHECK(cudaMemcpy(x_dev, x_host, memsize, cudaMemcpyHostToDevice));
    CUDA_CHECK(cudaMemcpy(y_dev, y_host, memsize, cudaMemcpyHostToDevice));

    /* set up device execution configuration */
    blockSize = 512;
    nBlocks = N / blockSize + (N % blockSize > 0);

    /* Time kernal execution */
    cudaEvent_t start, stop;
    float kernel_timer;

    cudaEventCreate(&start);
    cudaEventCreate(&stop);
    cudaEventRecord(start, 0);

    /* Call kernal */
    saxpy_gpu<<<nBlocks, blockSize>>>(x_dev, y_dev);
    /* Check for Errors */
    CUDA_CHECK(cudaPeekAtLastError());
    CUDA_CHECK(cudaDeviceSynchronize());

    /* Complete time inteval */
    cudaEventRecord(stop, 0);
    cudaEventSynchronize( stop );
    cudaEventElapsedTime( &kernel_timer, start, stop );

    /* spit out Kernal time results */
    printf("Kernel took %f ms\n",kernel_timer);
    cudaEventDestroy(start);
    cudaEventDestroy(stop);

    /* Error check with CPU serial */
    float error_check = saxpy_cpu(x_host, y_host);

    /* retrieve results from device (synchronous) */
    cudaMemcpy(min_dist, x_dev, memsize, cudaMemcpyDeviceToHost);

    /* guarantee synchronization */
    cudaDeviceSynchronize();

    /* spit out results */
    printf("Min distance GPU calculated is: %f\n", min_dist[0]);

    printf("Min distance CPU serial calculated is : %f\n", error_check);

    /* free memory */
    cudaFree(x_dev);
    cudaFree(y_dev);
    free(x_host);
    free(y_host);
    free(min_dist);

    return 0;
}
