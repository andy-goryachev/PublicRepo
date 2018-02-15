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
		if(size < 2)
		{
			throw new IllegalArgumentException();
		}
		
		this.size = size;
		
		// TODO check for overflow
		this.bitsetSize = size * size;
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
		for(int r=0; r<midpoint; r++)
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
			
			markProhibitedPositions(col, row);
		}
		
		
		public int index(int col, int row)
		{
			return col * size + row;
		}

		
		public boolean isPermitted(int col, int row)
		{
			if(row >= size)
			{
				return false;
			}
			else if(col >= size)
			{
				return false;
			}
			
			int ix = index(col, row);
			return !positions.get(ix);
		}
		
		
		public void set(int col, int row)
		{
			if(row < 0)
			{
				return;
			}
			else if(row >= size)
			{
				return;
			}
			else if(col >= size) // probably unnecessary
			{
				return;
			}
			
			int ix = index(col, row);
			positions.set(ix);
		}


		protected void markProhibitedPositions(int col, int row)
		{
			int d = 1;
			
			// standard queen attack rules: straight line, diagonals
			for(int c=col+1; c<size; c++)
			{
				// straight line (sufficient to mark in one direction due to the implicit rules of the algorithm)
				set(c, row);
				
				// diagonals
				set(c, row + d);
				set(c, row - d);
				
				d++;
			}
			
			// additional requirement: no three queens on the same line
			// due to caching nature of the algorithm we only need to mark prohibited squares
			// added by for the newly placed queen
			for(int i=col-1; i>=0; i--)
			{
				int queenRow = queens[i];
				
				int dx = col - i;
				int dy = row - queenRow;
				
				int x = col;
				int y = row;
				
				for(;;)
				{
					x += dx;
					if(x > size)
					{
						break;
					}
					
					y += dy;
					if(y < 0)
					{
						break;
					}
					else if(y > size)
					{
						break;
					}
					
					set(x, y);
				}
			}
		}
		
		
		/** 
		 * prints the board, also showing prohibited squares marked by the algorithm 
		 * for illustration purposes.
		 */
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
					
					// attempt to fix the output aspect ratio
					sb.append(' ');
				}
				
				sb.append('\n');
			}
			
			System.out.println(sb);
		}
	}
}
