
#include <mpi.h>

int main( int argc, char** argv ) 
{
    MPI_Init( &argc, &argv );

    /* main part of the program */

	/*
	  Use MPI function call depend on your data partitioning and the parallelization architecture
	*/

    MPI_Finalize();
}

