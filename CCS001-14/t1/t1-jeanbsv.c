#include <stdio.h>

int main() {
	int readValue, inputSize;
	
	scanf("%d", &readValue);
	inputSize = readValue;
	
	while ( inputSize != 0 ) {
		int VECTOR[inputSize];
		
		// preenchimento vetor
		int i = 0;
		for ( i = 0; i < inputSize; i++ ) {
			scanf("%d", &readValue);
			VECTOR[i] = readValue;
		}

		printf("%d\n", calculateMaxSum( VECTOR, inputSize ) );
		
		scanf("%d", &readValue);
		inputSize = readValue;
	}
	
	return 0;
}

int calculateMaxSum( int VECTOR[], int inputSize ) {
	int i = 0;
	int maxSum = 0, positiveSum = 0;
	for ( i = 0 ; i < inputSize ; i++ ) {
		if( positiveSum + VECTOR[ i ] > VECTOR[ i ] ){
			positiveSum = positiveSum + VECTOR[ i ];
		} else {
			positiveSum = VECTOR[ i ];
		}   
		
		if( positiveSum > maxSum ) {
			maxSum = positiveSum;
		}			
	}
	return maxSum;
}