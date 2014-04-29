//	public static void main( String[ ] args ) {
//		int inputSize;
//		int currentSum = 0;
//		int maxSum = 0;
//		int[] VECTOR = {1, -2, 1, 10, 14, -7, -7, 5, 3, 4};
//		
//		inputSize = VECTOR.length;
//		
//		inputSize --;
//		for ( int i = 0; i <= inputSize; i++ ) {
//			for ( int j = inputSize; j >= i; j-- ) {
//				currentSum += VECTOR[ inputSize - j ];
//				System.out.println("i = " + i + ", j = " + j);
//				System.out.println(" VECTOR, POS = " + (inputSize - j) + 
//						", VALUE = " + VECTOR[ inputSize - j ] );
//			}
//			System.out.println("currentSum = " + currentSum);
//			if ( currentSum > maxSum) {
//				maxSum = currentSum;
//			}
//			currentSum = 0;
//		}
//		System.out.println( "MAX SUM = " + maxSum );
//	}
	
	public static void main( String[ ] args ) {
		int[] VECTOR = {10, 12, -3, -4, -5, -6, -7, 8, 8, 2, 2, 5, -9, 10, 2, 3, 9, -1, -2, 4};
		
		int inputSize = VECTOR.length - 1;
		int sum = 0, maxSum = 0;
		
		for ( int i = 0; i < inputSize; i++ ) {
			sum = calc( VECTOR , i , inputSize );
			if ( sum > maxSum ) {
				maxSum = sum;
			}
		}
		System.out.println( "MAX SUM = " + maxSum );
	}
	
	private static int calc( int[] VECTOR, int from, int to ) {
		int sum = 0, nextSum;
		for ( int i = from; i <= to; i++ )
			sum += VECTOR[ i ];
		
		if ( ( to - 1 ) > from ) {
			nextSum = calc( VECTOR , from , to - 1 );
			if ( nextSum > sum )
				return nextSum;
		}
		return sum;
	}