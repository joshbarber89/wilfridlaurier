
#include "cuda.h" /* CUDA runtime API */
#include "cstdio"
#include "math.h"

#define N 65536

float saxpy_cpu(float *pointX, float *pointY) {
    // Create vars
    int i;
    int j;
    float dist, y2, x2;
    float min_dist = 2.0f;
    for (i = 0; i < N; i++) {
        // Grab first point then compare to the rest
        float x1 = pointX[i];
        float y1 = pointY[i];

        for (j = 0; j < N; j++) {
            // Grab second point and compute
            x2 = pointX[j];
            y2 = pointY[j];
            if (x2 != x1 && y2 != y1) {
                dist = sqrt(pow((x2 - x1),2) + pow((y2 - y1),2));
                //float dist_x = x2 - x1;
                //float dist_y = y2 - y1;
                //dist = (dist_x * dist_x) + (dist_y * dist_y);

                //Find min
                if (dist < min_dist) {
                    min_dist = dist;
                }
            }
        }
    }
    return min_dist;
}

int main(int argc, char *argv[]) {
    // Set x and y pointers
    float *x, *y;

    size_t memsize;
    int i;

    // Initialize data mmemory
    memsize = N * sizeof(float);

    x = (float *)malloc(memsize);
    y = (float *)malloc(memsize);

    // Set x y coords
    for ( i = 0; i < N; i++) {
        x[i] = rand() / (float)RAND_MAX;
        y[i] = rand() / (float)RAND_MAX;
    }

    // begin clock
    clock_t begin = clock();

    // Call algorithm
    float min_dist = saxpy_cpu(x, y);

    // Ending stats and freeing memory
    clock_t end = clock();
    double time_spent = (double)(end - begin) / CLOCKS_PER_SEC;

    printf("Min distance: %f\n", min_dist);
    printf("Time of execution: %fs", time_spent);

    free(x);
    free(y);

    return 0;
}
