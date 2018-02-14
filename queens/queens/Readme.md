Customer Requirements
=====================
Place N queens on an NxN chess board so that none of them attack each other 
(the classic n-queens problem). Additionally, please make sure that no three queens 
are in a straight line at ANY angle, so queens on A1, C2 and E3, 
despite not attacking each other, form a straight line at some angle 
(approx. 26.6 degrees!)



Research
========
https://en.wikipedia.org/wiki/Eight_queens_puzzle

https://stackoverflow.com/questions/48448584/all-possible-n-queens

http://mathworld.wolfram.com/QueensProblem.html

possibly faster:

http://ieeexplore.ieee.org/document/135698/

somebody was there before:

https://github.com/julienschmidt/N-Queens/blob/master/src/nqueens/NQueens.java



Clarifications
==============
The goal of this step is to clarify initial requirements, make the customer re-think
the problem and ensure that this is indeed the problem to be solved.

- assuming the queens are of the same color, correct?
- find at least one or all solutions?
- is there a limit for N (or the order of magnitude - 100? 1k? 1M?)
Response:
1. Yes .. same color queens
	to be strict, the queens of the same color do not threaten each other, 
	so the problem reduces to avoiding any three queens at the same line ("same angle").  
	I **assume** that in the context of this task the rules of chess are modified such that 
	the queens do threaten each other, right?
		Fair enough - yes your assumption that any queen can threaten any other is correct.
2. Find at least one solution
   -. Extra points: find all solutions
   -. Extra points: only present/find “unique” solutions (cannot be rotated and/or reflected to represent another solution)
3. Choose a limit on N that you think suitable (as long as it is at least 8!)


Design
======
I am going to mark the prohibited positions on the board for each successive column, populating a BitSet at each step.
This might offer an optimization (for large N) where the parent bitset is reused on each iteration.

The marking step allows limits the number of times the code has to descent into recursion.

Example:
The very first cycle (the first queen positioned at the top left corner of the board) reduces the search space
from 3x4=12 to 6 squares:
 
```
Q---
--++
-+-+
-++-
```

where **[Q]** denotes a queen, **[-]** denotes a prohibited position, and **[+]** denotes a possible position.

Going further, placing the second queen in the first available square reduces the search space from 6 to 1:

```
Q---
---+
-Q--
----
```

As you can see the third column has no permitted squares to place the queen, therefore the corresponding 
recursion branch will not be executed.

 
