
var fun = function(list)
{
//best move
    var board = Java.from(list);
    var bestVal = -1000;
    var move=-1;

    // Traverse all cells, evaluate minimax function for
    // all empty cells. And return the cell with optimal
    // value.
    for (var i = 0; i<5; i++)
    {
        for (var j = 0; j<5; j++)
        {
            // Check if cell is empty
            if (board[i*5+j]==null)
            {
                // Make the move
                board[i*5+j] = 'x';

                // compute evaluation function for this
                // move.
                var moveVal = minimax(board, false,0);


                // Undo the move
                board[i*5+j] = null;

                // If the value of the current move is
                // more than the best value, then update
                // best/
                if (moveVal > bestVal)
                {
                    move=i*5+j;
                    bestVal = moveVal;
                }
            }
        }
    }

    print("The value of the best Move is : %d\n\n",
            bestVal+" best move: "+move);

    return move;

}




var minimax=function(list,isMax,dep)
{ var board = list;

    var score = win(board);


//print("score="+score+" ismax "+isMax);
    // If Maximizer has won the game return his/her
    // evaluated score
    if (score === 10)
        return score-dep;

    // If Minimizer has won the game return his/her
    // evaluated score
    if (score === -10)
        return score+dep;

    // If there are no more moves and no winner then
    // it is a tie
    if (isMovesLeft(board)==false)
    {
        return 0;}

    var best;
    // If this maximizer's move
    if (isMax)
    {
         best = -1000;

        // Traverse all cells
        for (var i = 0; i<5; i++)
        {
            for (var j = 0; j<5; j++)
            {
                // Check if cell is empty
                if (board[i*5+j]===null)
                {
                    // Make the move
                    board[i*5+j] = 'x';

                    // Call minimax recursively and choose
                    // the maximum value
                    best = Math.max( best,
                        minimax(board,!isMax,dep +1) );

                    print("dep "+dep);
                    // Undo the move
                    board[i*5+j] = null;
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
        for (var i = 0; i<5; i++)
        {
            for (var j = 0; j<5; j++)
            {
                // Check if cell is empty
                if (board[i*5+j]===null)
                {
                    // Make the move
                    board[i*5+j] = 'o';

                    // Call minimax recursively and choose
                    // the minimum value
                    best = Math.min(best,
                           minimax(board, !isMax,dep+1));

                    // Undo the move
                    board[i*5+j] = null;
                }
            }
        }
        return best;
    }
}

var isMovesLeft = function(list) {
var jsArray = list;
for (var i = 0; i<25; i++)
    {
    if(jsArray[i]==null)return true;
    }
    return false;
}

var win = function(list)
{ var b = list;
//for (var i = 0; i<5; i++)
      //  {

       //     print(b[i*5+0]+" "+b[i*5+1]+" "+b[i*5+2]+" "+b[i*5+3]+" "+b[i*5+4]);
        //    print( "\n");
         //   }
    // Checking for Rows for X or O victory.
    for (var row = 0; row<5; row++)
    {
        if ((b[row*5+0]==b[row*5+1] && b[row*5+1]==b[row*5+2] && b[row*5+2]==b[row*5+3]) ||
         (b[row*5+3]==b[row*5+4] && b[row*5+1]==b[row*5+2] && b[row*5+2]==b[row*5+3]))
        {
            if (b[row*5+1]=='x')
               return +10;
            else if (b[row*5+1]=='o')
               return -10;
        }
    }

    // Checking for Columns for X or O victory.
    for (var col = 0; col<5; col++)
    {
        if ((b[0*5 +col]==b[1*5+col] && b[1*5+col]==b[2*5+col] && b[3*5+col]==b[2*5+col] ) ||
        (b[3*5 +col]==b[4*5+col] && b[1*5+col]==b[2*5+col] && b[3*5+col]==b[2*5+col] ))
        {
            if (b[1*5+col]=='x')
                return +10;
            else if (b[1*5+col]=='o')
                return -10;
        }
    }
    // Checking for Diagonals for X or O victory.
        if ((b[0]==b[6] && b[12]==b[18] && b[12]==b[6]) || (b[24]==b[18] && b[12]==b[18] && b[12]==b[6]) )
        {
                    if (b[6]=='x')
                        return +10;
                    else if (b[6]=='o')
                        return -10;
                }

        if(b[1]==b[7] && b[7]==b[13] && b[13]==b[19])

        {
            if (b[1]=='x')
                return +10;
            else if (b[1]=='o')
                return -10;
        }
        if (b[5]==b[11] && b[11]==b[17] && b[17]==b[23])
         {
            if (b[5]=='x')
                return +10;
            else if (b[5]=='o')
                return -10;
        }

        //goagonal z drugiej strony

        if ((b[4]==b[8] && b[12]==b[8] && b[12]==b[16]) || (b[8]==b[12] && b[12]==b[16] && b[16]==b[20]) )
                {
                            if (b[8]=='x')
                                return +10;
                            else if (b[8]=='o')
                                return -10;
                        }

                if(b[3]==b[7] && b[7]==b[11] && b[11]==b[15])

                {
                    if (b[3]=='x')
                        return +10;
                    else if (b[3]=='o')
                        return -10;
                }
                if (b[9]==b[13] && b[13]==b[17] && b[17]==b[21])
                 {
                    if (b[9]=='x')
                        return +10;
                    else if (b[9]=='o')
                        return -10;
                }

        // Else if none of them have won then return 0
        return 0;
    }