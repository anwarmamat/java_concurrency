#include <omp.h>
#include <stdio.h>
#include <stdlib.h>

int main (int argc, char *argv[]){
  int nthreads, tid;
  
  omp_set_num_threads(4);
  #pragma omp parallel private(nthreads, tid)
  {
    tid = omp_get_thread_num();
    printf("%d\n", tid);
    
    if (tid == 0) {
      nthreads = omp_get_num_threads();
      printf("%d\n", nthreads);
    }
  }
}

