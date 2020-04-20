#include<mpi.h>
#include<stdio.h>
#include<string.h>
int main(int argc, char *argv[]){
  int rank;
  int size;
  
  MPI_Init(&argc, &argv);
  
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &size);
  char msg[10];;
  if(rank == 0){
    printf("size:%d\n", size);
    printf("master id:%d\n", rank);
    strcpy(msg, "Hello");
  }

  // broadcast to all nodes
  MPI_Bcast(msg, 10, MPI_CHAR, 0, MPI_COMM_WORLD);  

  printf("worker id:%d\tmsg:%s\n", rank,msg);
 
  MPI_Finalize();
}
