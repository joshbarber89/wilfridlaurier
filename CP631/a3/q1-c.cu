
#include "cuda.h" /* CUDA runtime API */
#include "cstdio"
#include "math.h"

#define N 4096

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

__global__ void saxpy_gpu(float *x1i, float *x2i, float *y1i, float *y2i) {
    // Global var for all threads
    __shared__ float minV;

    //grab thread id
    int i = blockIdx.x * blockDim.x + threadIdx.x;

    if (i< (N * N)) {
        //initialize values
        float dist, x1, x2, y1, y2;

        float dist_x, dist_y;
        // Get all points
        x1 = x1i[i];
        y1 = y1i[i];
        x2 = x2i[i];
        y2 = y2i[i];

        dist = 2.0f;
        minV = 2.0f;
        // Find distance
        if (x1 != x2 && y1 != y2) {
            // dist = sqrt(pow((x2 - x1),2) + pow((y2 - y1),2));
            dist_x = x2 - x1;
            dist_y = y2 - y1;
            dist = (dist_x * dist_x) + (dist_y * dist_y);
        }
        // Wait for all operations to complete
        __syncthreads();
        // Assign min value
        atomicMinFloat(&minV, dist);
        __syncthreads();
        // assign global min value to output
        if (i == 0)
            x1i[0] = minV;

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
                //dist = sqrt(pow((x2 - x1),2) + pow((y2 - y1),2));
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
    /* arrays for computation on host and device*/
    float *x, *y;
    float *all_combinations_host_x1;
    float *all_combinations_host_x2;
    float *all_combinations_host_y1;
    float *all_combinations_host_y2;
    float *all_combinations_device_x1;
    float *all_combinations_device_x2;
    float *all_combinations_device_y1;
    float *all_combinations_device_y2;

    int i, j, blockSize, nBlocks;


    /* allocate arrays on host */
    x = (float *)malloc(N * sizeof(float));
    y = (float *)malloc(N * sizeof(float));

    all_combinations_host_x1 = (float *)malloc(N * N * sizeof(float));
    all_combinations_host_x2 = (float *)malloc(N * N * sizeof(float));
    all_combinations_device_x1 = (float *)malloc(N * N * sizeof(float));
    all_combinations_device_x2 = (float *)malloc(N * N * sizeof(float));

    all_combinations_host_y1 = (float *)malloc(N * N * sizeof(float));
    all_combinations_host_y2 = (float *)malloc(N * N * sizeof(float));
    all_combinations_device_y1 = (float *)malloc(N * N * sizeof(float));
    all_combinations_device_y2 = (float *)malloc(N * N * sizeof(float));

    /* allocate arrays on device */
    CUDA_CHECK(cudaMalloc((void **) &all_combinations_device_x1, N * N * sizeof(float)));
    CUDA_CHECK(cudaMalloc((void **) &all_combinations_device_x2, N * N * sizeof(float)));
    CUDA_CHECK(cudaMalloc((void **) &all_combinations_device_y1, N * N * sizeof(float)));
    CUDA_CHECK(cudaMalloc((void **) &all_combinations_device_y2, N * N * sizeof(float)));

    // Assign particles
    for ( i = 0; i < N; i++) {
        x[i] = rand() / (float)RAND_MAX;
        y[i] = rand() / (float)RAND_MAX;
    }

    // All combinations of x and y within four arrays
    float x1,y1, x2, y2;
    int count = 0;
    for ( i = 0; i < N; i++) {
        x1 = x[i];
        y1 = y[i];
        for ( j = 0; j < N; j++) {
            x2 = x[j];
            y2 = y[j];

            all_combinations_host_x1[count] = x1;
            all_combinations_host_y1[count] = y1;
            all_combinations_host_x2[count] = x2;
            all_combinations_host_y2[count] = y2;
            count++;
        }

    }
    /* copy arrays to device memory */
    CUDA_CHECK(cudaMemcpy(all_combinations_device_x1, all_combinations_host_x1, N * N * sizeof(float), cudaMemcpyHostToDevice));
    CUDA_CHECK(cudaMemcpy(all_combinations_device_x2, all_combinations_host_x2, N * N * sizeof(float), cudaMemcpyHostToDevice));
    CUDA_CHECK(cudaMemcpy(all_combinations_device_y1, all_combinations_host_y1, N * N * sizeof(float), cudaMemcpyHostToDevice));
    CUDA_CHECK(cudaMemcpy(all_combinations_device_y2, all_combinations_host_y2, N * N * sizeof(float), cudaMemcpyHostToDevice));

    /* set up device execution configuration */
    blockSize = 512;
    nBlocks = (N * N) / blockSize + ((N*N) % blockSize > 0);

    /* Time kernal execution */
    cudaEvent_t start, stop;
    float kernel_timer;

    cudaEventCreate(&start);
    cudaEventCreate(&stop);
    cudaEventRecord(start, 0);
    /* Call kernal */
    saxpy_gpu<<<nBlocks, blockSize>>>(all_combinations_device_x1, all_combinations_device_x2, all_combinations_device_y1, all_combinations_device_y2);
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


    /* retrieve results from device (synchronous) */
    cudaMemcpy(all_combinations_host_x1, all_combinations_device_x1, N * N * sizeof(float), cudaMemcpyDeviceToHost);

    /* guarantee synchronization */
    cudaDeviceSynchronize();

    /* Error check with CPU serial */
    float error_check = saxpy_cpu(x, y);

    /* spit out results */
    printf("Min distance GPU calculated is: %f\n", all_combinations_host_x1[0]);

    printf("Min distance CPU serial calculated is : %f\n", error_check);

    /* free memory */
    cudaFree(all_combinations_host_x1);
    cudaFree(all_combinations_host_x2);
    cudaFree(all_combinations_host_y1);
    cudaFree(all_combinations_host_y2);
    cudaFree(all_combinations_device_x1);
    cudaFree(all_combinations_device_x2);
    cudaFree(all_combinations_device_y1);
    cudaFree(all_combinations_device_y2);
    free(x);
    free(y);

    return 0;
}
