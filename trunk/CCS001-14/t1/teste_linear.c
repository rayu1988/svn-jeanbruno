#include <stdio.h>

mooint main() {
	scanf("%d", &readValue);
	while ( readValue != 0 ) {
		int VECTOR[readValue];
		
		// preenchimento vetor
		for ( i = 0; i < readValue; i++ ) {
			scanf("%d", &VECTOR[i]);
		}
		
		int maxSum = 0;
		int negativeSum = 0;
		int positiveSum = 0;
		
		int i = 0;
		
		#encontrar primeiro positivo
		for ( i = 0; i < VECTOR.lenght; i++ ) {
			if ( VECTOR[ i ] >= 0 ) {
				positiveSum = VECTOR[ i ];
				break;
			}
		}

		for ( i = 0; i < VECTOR.lenght; i++ ) {
			if ( VECTOR[i] >= 0 && VECTOR[i -1] < 0) { // elemento corrente positivo e o anterior negativo
				positiveSum = VECTOR[i];
			} else if ( VECTOR[i] >= 0 && VECTOR[i -1] >= 0 ) { // elemento corrente positivo e o anterior também
				positiveSum += VECTOR[i];
			}
			
			if ( VECTOR[i] < 0 && VECTOR[i - 1] >= 0 ) { // elemento corrente negativo e anterior positivo
				if ( positiveSum > ( maxSum + negativeSum + positiveSum ) ) { // o positiveSum for maior que maxSum, negativeSum e positiveSum atual
					maxSum = positiveSum;
				} else {
					maxSum = maxSum + negativeSum + positiveSum;
				}
				negativeSum = VECTOR[i];
			} else if ( VECTOR[i] < 0 && VECTOR[i - 1] < 0 ) { // elemento corrente negativo e o anterior também
				negativeSum += VECTOR[i];
			}
		}

		printf("%d\n", maxSum);
		maxSum = 0;
		
		scanf("%d", &readValue);
	}
	
	return 0;
}