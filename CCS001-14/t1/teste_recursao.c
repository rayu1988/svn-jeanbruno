#include <stdio.h>

int main() {
	int i = 0, readValue = 0;
	scanf("%d", &readValue);
	while ( readValue != 0 ) {
		int VECTOR[ readValue ];
		int inputSize = readValue - 1;
		
		// preenchimento vetor
		//printf("PREENCHENDO VETOR DE TAMANHO: %d\n", inputSize);
		for ( i = 0; i <= inputSize; i++ ) {
			scanf("%d", &readValue);
			VECTOR[ i ] = readValue;
		}
		//printf("PREENCHENDO COMPLETO\n");
		
		int sum = 0, maxSum = 0;
		for ( i = 0; i < inputSize; i++ ) {
			sum = calc( VECTOR , i , inputSize );
			
			//printf("CONCLUIDO CALCULO PARA CARACTERE DE: %d ATE %d\n", i, inputSize);
			if ( sum > maxSum ) {
				maxSum = sum;
			}
		}
		printf("%d\n", maxSum);
		
		scanf("%d", &readValue);
	}
	return 0;
}

int calc( int VECTOR[], int from, int to ) {
	int i = 0, sum = 0, nextSum = 0;
	for ( i = from; i <= to; i++ )
		sum += VECTOR[ i ];
	
	if ( ( to - 1 ) > from ) {
		nextSum = calc( VECTOR , from , to - 1 );
		if ( nextSum > sum )
			return nextSum;
	}
	return sum;
}