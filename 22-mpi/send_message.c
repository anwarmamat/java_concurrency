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
    MPI_Send(msg, 10, MPI_CHAR, 1, 0, MPI_COMM_WORLD);
  }else{
    char msg[100];
    MPI_Recv(msg, 10, MPI_CHAR, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    printf("worker id:%d\tmsg:%s\n", rank,msg);
  } 
  MPI_Finalize();
}
