/*
 * Leitura ótica
 * Código no SPOJ: LOTICA
 *
 * Autora: Miriam Yumi Peixoto
 * Entrada:
 *     N, número de questões na folha; 
 *     Ai, Bi, Ci, Di e Ei, valores dos níveis de cinza para as alternativas da i-ésima questão.
 * Saída: Alternativa marcada ('A', 'B', 'C', 'D' ou 'E') ou '*', caso a resposta seja inválida.
 *
 * Restrições: 1 ≤ N ≤ 255
 *
 * Exemplo:
 *
 *   Entrada:
 *     3
 *     0 255 255 255 255
 *     255 255 255 255 0
 *     200 1 200 200 1
 *     0
 *   Saída:
 *     A
 *     E
 *     *
 *
*/

#include <stdio.h>

int main () {
	int n, x;
	int op, i, j;
	
	scanf("%d", &n);
	while (n != 0) {
		for (j = 0;j < n;j++) {
			op = '\0';
			for (i = 0;i < 5;i++) {
				scanf("%d", &x);
				if (x < 128) {
					if (op == '\0') { op = 'A' + i; }
					else { op = '*'; }
				}
			}
			if (op == '\0') op = '*';
		
			printf("%c\n", op);
		}
		scanf("%d", &n);
	}
	
	return 0;
}
