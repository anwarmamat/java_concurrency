#include<mpi.h>
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include <assert.h>

// Creates an array of random numbers. Each number has a value from 0 - 1
float *create_rand_nums(int num_elements) {
  float *rand_nums = (float *)malloc(sizeof(float) * num_elements);
  assert(rand_nums != NULL);
  int i;
  for (i = 0; i < num_elements; i++) {
    rand_nums[i] = i + 1;// (rand() / (float)RAND_MAX);
  }
  printf("%d float numbers are created\n", num_elements);
  return rand_nums;
}

// Computes the average of an array of numbers
float compute_avg(float *array, int num_elements) {
  float sum = 0.f;
  int i;
  for (i = 0; i < num_elements; i++) {
    sum += array[i];
  }
  return sum / num_elements;
}

int main(int argc, char *argv[]){
  int rank;
  int size;
  
  MPI_Init(&argc, &argv);
  
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &size);
  
  float *input;

  int  elements_per_proc = 100;
  
  //create the array
  if (rank == 0) {
    input = create_rand_nums(elements_per_proc * size);
  }
  //memory for each node to copy its own chunk of data
  float *sub_input = (float*)malloc (sizeof(float) *  elements_per_proc);
  assert(sub_input != NULL);
  
  // Scatter the random numbers to all processes
  MPI_Scatter(input, elements_per_proc, MPI_FLOAT, sub_input,
            elements_per_proc, MPI_FLOAT, 0, MPI_COMM_WORLD);


  // Compute the total average of all numbers.
  float sub_avg = compute_avg(sub_input, elements_per_proc);


  //collect the results
  float *sub_avgs = NULL;
  if (rank == 0) {
    sub_avgs = (float *)malloc(sizeof(float) * size);
    assert(sub_avgs != NULL);
  }

  
  MPI_Gather(&sub_avg, 1, MPI_FLOAT, sub_avgs, 1, MPI_FLOAT, 0, MPI_COMM_WORLD);


  if (rank == 0) {
    float avg = compute_avg(sub_avgs, size);
    printf("Avg of all elements is %f\n", avg);
    // Compute the average across the original data for comparison
    float original_data_avg = compute_avg(input, elements_per_proc * size);
    printf("Avg computed across original data is %f\n", original_data_avg);
  }
  if (rank == 0) {
    free(input);
    free(sub_avgs);
  }
  free(sub_input);
  MPI_Barrier(MPI_COMM_WORLD);
  MPI_Finalize();
}
