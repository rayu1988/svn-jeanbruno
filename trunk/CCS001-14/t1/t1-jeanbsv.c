#include <stdio.h>

/**
* leitura do arquivo e preenchimento do vetor
* 
* pega um elemento, efetua soma deste no contador corrente
* se a soma deste for maior que o elemento pego por ultimo, entao este elemento deve entrar na soma
* caso contrario, o elemento pego por ultimo e maior ou igual a soma supracitada, entao este deve iniciar um novo intervalo
*
* se a soma corrente for maior que o maior somatorio corrente, atualizamos o maior somatorio corrente com a soma corrente
*/
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
	int sum = 0, maxSum = 0, checkNewSum = 0;
	
	// percorrendo o vetor e calculando os intervalos
	int i = 0;
	for ( ; i < inputSize ; i++ ) {
		checkNewSum = sum + VECTOR[ i ];
		
		// se tiver um trecho negativo no meio
		// vale apena calcular o proximo? 
		sum = checkNewSum > VECTOR[ i ] ? checkNewSum: VECTOR[ i ];
		
		// a soma corrente e o maior somatorio encontrado?
		maxSum = sum > maxSum ? sum : maxSum;
	}
	return maxSum;
}
