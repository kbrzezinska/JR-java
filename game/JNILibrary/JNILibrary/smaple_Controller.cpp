//#include "stdafx.h"
#include <jni.h>
#include "Sampler.h"
#include <stdio.h>
//#include "sample_Controller.h"
#include <cstring>
#include <math.h>
#include <stdlib.h>
#include <ctime>
#include <algorithm>

int win(jobject b[]){

	for (int row = 0; row<5; row++)
	{
		if ((b[row * 5 + 0] == b[row * 5 + 1] && b[row * 5 + 1] == b[row * 5 + 2] && b[row * 5 + 2] == b[row * 5 + 3]) ||
			(b[row * 5 + 3] == b[row * 5 + 4] && b[row * 5 + 1] == b[row * 5 + 2] && b[row * 5 + 2] == b[row * 5 + 3]))
		{
			if (b[row * 5 + 1] == (jobject)'x')
				return +10;
			else if (b[row * 5 + 1] == (jobject)'o')
				return -10;
		}
	}

	// Checking for Columns for X or O victory.
	for (int col = 0; col<5; col++)
	{
		if ((b[0 * 5 + col] == b[1 * 5 + col] && b[1 * 5 + col] == b[2 * 5 + col] && b[3 * 5 + col] == b[2 * 5 + col]) ||
			(b[3 * 5 + col] == b[4 * 5 + col] && b[1 * 5 + col] == b[2 * 5 + col] && b[3 * 5 + col] == b[2 * 5 + col]))
		{
			if (b[1 * 5 + col] == (jobject)'x')
				return +10;
			else if (b[1 * 5 + col] == (jobject)'o')
				return -10;
		}
	}
	// Checking for Diagonals for X or O victory.
	if ((b[0] == b[6] && b[12] == b[18] && b[12] == b[6]) || (b[24] == b[18] && b[12] == b[18] && b[12] == b[6]))
	{
		if (b[6] == (jobject)'x')
			return +10;
		else if (b[6] == (jobject)'o')
			return -10;
	}

	if (b[1] == b[7] && b[7] == b[13] && b[13] == b[19])

	{
		if (b[1] == (jobject)'x')
			return +10;
		else if (b[1] == (jobject)'o')
			return -10;
	}
	if (b[5] == b[11] && b[11] == b[17] && b[17] == b[23])
	{
		if (b[5] == (jobject)'x')
			return +10;
		else if (b[5] == (jobject)'o')
			return -10;
	}

	//goagonal z drugiej strony

	if ((b[4] == b[8] && b[12] == b[8] && b[12] == b[16]) || (b[8] == b[12] && b[12] == b[16] && b[16] == b[20]))
	{
		if (b[8] == (jobject)'x')
			return +10;
		else if (b[8] == (jobject)'o')
			return -10;
	}

	if (b[3] == b[7] && b[7] == b[11] && b[11] == b[15])

	{
		if (b[3] == (jobject)'x')
			return +10;
		else if (b[3] == (jobject)'o')
			return -10;
	}
	if (b[9] == b[13] && b[13] == b[17] && b[17] == b[21])
	{
		if (b[9] == (jobject)'x')
			return +10;
		else if (b[9] == (jobject)'o')
			return -10;
	}

	// Else if none of them have won then return 0
	return 0;



}

bool isMovesLeft(jobject b[])
{
	for (int i = 0; i<25; i++)
	{
		if (b[i] == NULL)return true;
	}
	return false;
}

int minimax(jobject board[], bool isMax, int dep)
{
	int score = win(board);


	//print("score="+score+" ismax "+isMax);
	// If Maximizer has won the game return his/her
	// evaluated score
	if (score ==  10)
		return score - dep;

	// If Minimizer has won the game return his/her
	// evaluated score
	if (score == -10)
		return score + dep;

	// If there are no more moves and no winner then
	// it is a tie
	if (isMovesLeft(board) == false)
	{
		return 0;
	}

	int best;
	// If this maximizer's move
	if (isMax)
	{
		best = -1000;

		// Traverse all cells
		for (int i = 0; i<5; i++)
		{
			for (int j = 0; j<5; j++)
			{
				// Check if cell is empty
				if (board[i * 5 + j] ==  NULL)
				{
					// Make the move
					board[i * 5 + j] = (jobject)'x';

					// Call minimax recursively and choose
					// the maximum value
					best = std::max(best,
						minimax(board, !isMax, dep + 1));

					// Undo the move
					board[i * 5 + j] = NULL;
				}
			}
		}
		return best;
	}

	// If this minimizer's move
	else
	{
		best = 1000;

		// Traverse all cells
		for (int i = 0; i<5; i++)
		{
			for (int j = 0; j<5; j++)
			{
				// Check if cell is empty
				if (board[i * 5 + j] == NULL)
				{
					// Make the move
					board[i * 5 + j] = (jobject)'o';

					// Call minimax recursively and choose
					// the minimum value
					best = std::min(best,
						minimax(board, !isMax, dep + 1));

					// Undo the move
					board[i * 5 + j] = NULL;
				}
			}
		}
		return best;
	}
}

JNIEXPORT jint JNICALL Java_sample_Controller_minimax
(JNIEnv *env, jobject, jobjectArray List)
{
	int size = env->GetArrayLength(List);
	jobject board[25];
	for (int i = 0; i < 25; i++)
	{
		jobject buf = env->GetObjectArrayElement(List, i);
		board[i] = buf;

	}

	int bestVal = -1000;
	int move = -1;

	// Traverse all cells, evaluate minimax function for
	// all empty cells. And return the cell with optimal
	// value.
	for (int i = 0; i<5; i++)
	{
		for (int j = 0; j<5; j++)
		{
			// Check if cell is empty
			if (board[i * 5 + j] == NULL)
			{
				// Make the move
				board[i * 5 + j] = (jobject)'x';

				// compute evaluation function for this
				// move.
				int moveVal = minimax(board, false, 0);

				// Undo the move
				board[i * 5 + j] = NULL;

				// If the value of the current move is
				// more than the best value, then update
				// best/
				if (moveVal > bestVal)
				{

					move = i * 5 + j;
					bestVal = moveVal;
				}
			}
		}
	}


	return move;
};

JNIEXPORT jint JNICALL Java_sample_Controller_random
(JNIEnv *env, jobject, jobjectArray List)
{
	srand(time(0));
	jobject b[25];
	for (int i = 0; i < 25; i++)
	{
		jobject buf = env->GetObjectArrayElement(List, i);
		b[i] = buf;
		
	}
	int min = 0;
	int max = env->GetArrayLength(List)-1;
	int count = 0;
	while (true)
	{
		int randd = rand() % max + min;

		if (b[randd] == NULL)
		{
			b[randd] = (jobject)('x');
			return randd;
		}
		count++;
		if (count == 100)
		{
			for (int i = 0; i<25; i++){

				if (b[i] == NULL)
				{
					b[i] = (jobject)('x');
					return i;
				}

			}

		}


	}
	return -8;
};