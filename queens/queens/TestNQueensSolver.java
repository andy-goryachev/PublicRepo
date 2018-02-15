// Copyright © 2018 Andy Goryachev <andy@goryachev.com>
package queens;


/**
 * Test NQueensSolver.
 */
public class TestNQueensSolver
{
	// TODO 
	// for N from 2 to 8(?), exhaustively generate all possible combinations, and make sure
	// that the solver produces correct result.

	
	// TODO
	// pick a large N (16?) and measure the time, possibly comparing it to a trivial implementation
	
	
	public static void main(String[] args)
	{
		long start = System.currentTimeMillis();
		
		new NQueensSolver(8).solve();
		
		double elapsed = (System.currentTimeMillis() - start) / 1000.0;
		System.out.printf("Elapsed %.2f seconds.", elapsed);
	}
}
