#include <stdio.h>

mooint main() {
	int currentSum = 0, maxSum = 0, readValue = 0, inputSize = 0, i = 0, j = 0, h = 0;
	
	scanf("%d", &readValue);
	inputSize = readValue;
	
	while ( inputSize != 0 ) {
		int VECTOR[inputSize];
		
		// preenchimento vetor
		for ( i = 0; i < inputSize; i++ ) {
			scanf("%d", &readValue);
			VECTOR[i] = readValue;
		}
		
		// procura maximo somatorio
		for ( i = 0; i < inputSize; i++ ) {
			currentSum = 0;
			for ( j = inputSize -1; j > i; j-- ) {
				currentSum = calculate(VECTOR, i, j);
				if ( currentSum > maxSum ) {
					maxSum = currentSum;
				}
			}
			
		}
		printf("%d\n", maxSum);
		maxSum = 0;
		
		scanf("%d", &readValue);
		inputSize = readValue;
	}
	
	return 0;
}

int calculate(int VECTOR[], int i, int upToRead) {
	int sum = 0;
	for ( i; i <= upToRead; i++ ) {
		sum = sum + VECTOR[i];
	}
	return sum;
}