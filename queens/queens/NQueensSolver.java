// Copyright © 2018 Andy Goryachev <andy@goryachev.com>
package queens;
import java.util.BitSet;


/**
 * Solves N-Queens problem with additional requirements:
 * 
 * 1. Place N queens on an NxN chess board so that none of them 
 * attack each other (the classic n-queens problem). 
 * 
 * 2. Additionally, please make sure that no three queens are in a straight line 
 * at ANY angle, so queens on A1, C2 and E3, despite not attacking each other, 
 * form a straight line at some angle.
 */
public class NQueensSolver
{
	protected final int size;
	protected final int bitsetSize;
	
	
	public NQueensSolver(int size)
	{
		this.size = size;
		
		// TODO check for overflow
		this.bitsetSize = size * size;
	}
	
	
	public static void main(String[] args)
	{
		new NQueensSolver(4).solve();
	}
	
	
	/**
	 * Solves the problem by brute forcing all possible (non-prohibited) placements.
	 */
	public void solve()
	{
		// we are going to find only basic solutions, ignoring all other solutions obtained 
		// from rotation and mirroring.
		int midpoint = size / 2;
		
		// the first queen goes to the leftmost column, iterating from top row to the mid point
		// (the rest is a mirror image)
		for(int r=0; r<size; r++)
		{
			Board b = new Board(size, bitsetSize);
			b.setQueen(0, r);
			
			solveRecursively(b, 1);
		}
	}
	
	
	protected void solveRecursively(Board parent, int col)
	{
		if(col >= size)
		{
			// we are done, print resulting board
			parent.print();
			
			// exit recursion
			return;
		}
				
		for(int r=0; r<size; r++)
		{
			Board b = new Board(parent);

			if(b.isPermitted(col, r))
			{
				b.setQueen(col, r);
				
				solveRecursively(b, col + 1);
			}
		}
	}
	
	
	//
	
	
	public static class Board
	{
		protected final int size;
		protected final int[] queens;
		protected final BitSet positions;
		
		
		public Board(int size, int bitsetSize)
		{
			this.size = size;
			queens = new int[size];
			positions = new BitSet(bitsetSize);
		}
		

		public Board(Board parent)
		{
			this.size = parent.size;
			queens = parent.queens.clone();
			positions = (BitSet)parent.positions.clone();
		}
		
		
		public void setQueen(int col, int row)
		{
			queens[col] = row;
			
			markProhibitedPositions();
		}
		
		
		public int index(int col, int row)
		{
			return col * size + row;
		}

		
		public boolean isPermitted(int col, int row)
		{
			int ix = index(col, row);
			if(ix > positions.size())
			{
				return false;
			}
			
			return !positions.get(ix);
		}


		protected void markProhibitedPositions()
		{
			// TODO
		}
		
		
		public void print()
		{
			StringBuilder sb = new StringBuilder(size * (size + 2));
			
			for(int r=0; r<size; r++)
			{
				for(int c=0; c<size; c++)
				{
					if(queens[c] == r)
					{
						sb.append('Q');
					}
					else
					{
						int ix = index(c, r);
						if(isPermitted(c, r))
						{
							sb.append('+');
						}
						else
						{
							sb.append('-');
						}
					}
				}
				
				sb.append('\n');
			}
			
			System.out.println(sb);
		}
	}
}
