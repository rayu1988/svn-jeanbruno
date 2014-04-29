#include <stdio.h>

/**
#encontrar primeiro positivo
	ler primeira posição
	se: o caractere lido é positivo
		então: positiveMaxSum = readValue
		senão: #continuar percorrendo o vetor até encontrar o primeiro caractere válido.
		
ler próximo caractere ( i )
	se: o caractere ( i ) lido for positivo
		então: positiveMaxSum += readValue
	senão se: o caractere lido ( i ) for negativo e o anterior ( i -1 ) for positivo
		então:
			se: backupMaxSum > (negativeMaxSum * -1)
				então: backupMaxSum += negativeMaxSum + positiveMaxSum
				senão: backupMaxSum = positiveMaxSum
			positiveMaxSum = 0;
			negativeMaxSum = readValue
	senão se: o caractere lido ( i ) for negativo e o anterior ( i - 1 ) também
		então: negativeMaxSum += readValue

se: positiveMaxSum > (negativeMaxSum * -1)
	então: positiveMaxSum = positiveMaxSum + negativeMaxSum + backupMaxSum
			
se: backupMaxSum > positiveMaxSum
	então: retornar backupMaxSum
	senão: retornar positiveMaxSum
*/
int main() {
	int i=0, inputSize=0, readValue=0, previousValue=0, positiveMaxSum=0, negativeMaxSum=0, backupMaxSum=0;
	int maxPositionI, maxPositionJ;
	
	scanf("%d", &readValue);
	inputSize = readValue;
	
	while ( inputSize != 0 ) {
		for ( i = 0; i < inputSize; i++ ) {
			scanf("%d", &readValue);
			if ( readValue >= 0 ) {
				positiveMaxSum = readValue;
				
				// positions
				maxPositionI = i
				maxPositionJ = i;
				
				break;
			}
		}
		
		for ( i++ ; i < inputSize; i++ ) {
			scanf("%d", &readValue);
			if ( readValue >= 0 ) {
				// positions
				maxPositionJ = i;
				
				positiveMaxSum = positiveMaxSum + readValue;
				if ( ( i + 1 == inputSize ) && ( positiveMaxSum >= (negativeMaxSum * -1 ) ) ) {
					positiveMaxSum = positiveMaxSum + negativeMaxSum + backupMaxSum;
				}
			} else {
				if ( previousValue >= 0 ) {
					if ( backupMaxSum >= (negativeMaxSum * -1 ) ) {
						// positions
						maxPositionJ = i;
						
						backupMaxSum = backupMaxSum + negativeMaxSum + positiveMaxSum;
					} else {
						// positions
						maxPositionJ = i;
						maxPositionI = i;
						
						backupMaxSum = positiveMaxSum;
					}
					positiveMaxSum = 0;
					negativeMaxSum = readValue;
				} else {
					negativeMaxSum = negativeMaxSum + readValue;
				}
			}
			previousValue = readValue;
		}
		
		if ( backupMaxSum > positiveMaxSum ) {
			printf("%d\n", backupMaxSum);
		} else {
			printf("%d\n", positiveMaxSum);
		}
		
		i=0, inputSize=0, readValue=0, previousValue=0, positiveMaxSum=0, negativeMaxSum=0, backupMaxSum=0;
		
		scanf("%d", &readValue);
		inputSize = readValue;
	}
	
	return 0;
}
