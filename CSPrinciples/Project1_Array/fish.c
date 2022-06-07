/* 
*David Collins
*05/02/20
*This is the main implementation for that "Hey That's my fish" game
*all relevant code is contained in this file
**/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>

//represents a point
struct position {
     int x;
     int y;
};

//represents the  current position of both ai and player
struct position aiPosition;
struct position playerPosition;

/*
 *Creates the gameboard in its initial state
 *Param board: a 2d char array representing the ga,me board
 */
void  createBoard(char board[6][6]){
     for(int i = 0; i < 6; i++){
          for(int j = 0; j < 6; j++){
               if(i == 0 || i == 5 || j == 0 || j == 5){
                    board[i][j] = '1';
               } 
               else if( i  ==  1 || i == 4 || j == 1 || j == 4){
                   board[i][j] = '2';
               }
               else{
                    board[i][j] = '3';
               }
          }
     }
}

/*
 *print out the current game state
 *Param board: a 2dd char array representing the game board
 */
void printState(char board[6][6]){
     printf("   1 2 3 4 5 6\n");
     for(int i = 0; i < 6; i++){
          printf("%d  ", i+1);
          for(int j = 0; j < 6; j++){
               printf("%c ", board[i][j]);
          }
          printf("\n");
     }
}

/*
 *prompts user for their starting position and gives the AI their starting position.
 *Param board: a 2d char array representing the game board
 */
void startGame(char board[6][6]){
     int xValue;
     int yValue;
     int isValidInput = 0;
     printf("Note: Your starting position must be an octagon worth one point");
     while(isValidInput == 0){
          printf("\nEnter your starting positon in the form: row column:");
          if(scanf("%d %d", &xValue, &yValue) == 2 && board[xValue - 1][yValue - 1] == '1'){
               isValidInput = 1;
               board[xValue - 1][yValue - 1] = 'P';
               playerPosition.x = xValue -1;
               playerPosition.y = yValue -1;
          }
          else{
               scanf("%*[^\n]");
               printf("\nPlease enter a valid starting position note that you can only choose octagons whose value is 1 point.");
          }
     }
     isValidInput = 0;
     while(isValidInput == 0){
          int aiX = rand() % 6;
          int aiY = rand() % 6;
          if(board[aiX][aiY] == '1'){
               isValidInput = 1;
               board[aiX][aiY] = 'A';
               aiPosition.x = aiX;
               aiPosition.y = aiY;
          }
     }
}

/*
 *Gets the valid locations the player can move to
 *Param board: a 2d char array representing the game board
 *Param playerMoves: a 2d char array whose elements correspond to those of the game board with 1 representing
 *a valid move fot the player 0 otherwise
 *Return int: 1 if the player has moves to make 0 otherwise
 */
int getPlayerValidMoves(char board[6][6], char playerMoves[6][6]){
     //resetting the valid moves array
     for(int i =0; i < 6; i++){
          for(int j = 0; j < 6; j++){
               playerMoves[i][j] = '0';
          }
     }
     
     //checks to see if there are any valid moves left
     int validMoveExists = 0;
     //moving east
     for(int i = playerPosition.y + 1; i < 6; i++){
          char point = board[playerPosition.x][i];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          playerMoves[playerPosition.x][i] = '1';
     }
     
     //moving west
     for(int i = playerPosition.y - 1; i >= 0; i--){
          char point = board[playerPosition.x][i];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          playerMoves[playerPosition.x][i] = '1';
     }
     
     //moving south
     for(int i = playerPosition.x + 1; i < 6; i++){
          char point = board[i][playerPosition.y];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          playerMoves[i][playerPosition.y] = '1';
     }
     
     //moving north
     for(int i = playerPosition.x - 1; i >= 0; i--){
          char point = board[i][playerPosition.y];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          playerMoves[i][playerPosition.y] = '1';
     }
     
     //moving northeast
     int x = playerPosition.x - 1;
     int y = playerPosition.y + 1;
     while(x >= 0 && y < 6){
          char point = board[x][y];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          playerMoves[x][y] = '1';
          x = x - 1;
          y = y + 1;
     }
     
     //moving northwest
     x = playerPosition.x - 1;
     y = playerPosition.y - 1;
     while(x >= 0 && y >=0){
          char point = board[x][y];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          playerMoves[x][y] = '1';
          x = x - 1;
          y = y - 1;
     }
     
     //moving southeast
     x = playerPosition.x + 1;
     y = playerPosition.y + 1;
     while(x < 6 && y < 6){
          char point = board[x][y];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          playerMoves[x][y] = '1';
          x = x + 1;
          y = y + 1;
     }
     
     //moving southwest
     x = playerPosition.x + 1;
     y = playerPosition.y - 1;
     while(x <6 && y >= 0){
          char point = board[x][y];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          playerMoves[x][y] = '1';
          x = x + 1;
          y = y - 1;
     }
     return validMoveExists;
}

/*
 *Takes in input from the player in the form row column and moves them to that location if it is valid
 *Param board: a 2d char array representing the game board
 *Param playerMoves: a 2d char array whose elements correspond to those of the game board with 1 representing
 *a valid move for the player 0 otherwise
 *Return int: the score the player acheived on this turn
 */
int movePlayer(char board[6][6], char playerMoves[6][6]){
     if(getPlayerValidMoves(board, playerMoves) == 0){
          printf("The player has no more valid moves available\n");
          return 0;
     }
     int x;
     int y;
     int score;
     int isValid = 0;
     while(isValid == 0){
          printf("Choose the move you want to make\n ");
          if(scanf("%d %d", &x, &y) == 2 && playerMoves[x - 1][y - 1] == '1'){
               board[playerPosition.x][playerPosition.y] = '.';
               score = ((int) board[x - 1][y - 1]) - 48;
               board[x - 1][y - 1] = 'P';
               playerPosition.x = x-1;
               playerPosition.y = y-1;
               isValid = 1;
          }
          else{
               scanf("%*[^\n]");
               printf("Please choose a valid move.\n");
          }
     }
     return score;
}

/*
 *fills the aiMoves array with all moves the ai can make
 *Param board: a 2d char array representing the game board
 *Param aiMoves: a 2d char array whose elements correspond to those of the game board with 1 representing
 *a valid move fot the ai 0 otherwise
 *Return int: 1 if the ai has moves to make 0 otherwise
 */
int getAIValidMoves(char board[6][6], char aiMoves[6][6]){
     //resetting the valid moves array
     for(int i =0; i < 6; i++){
          for(int j = 0; j < 6; j++){
               aiMoves[i][j] = '0';
          }
     }
     
     //checks to see if a valid move exists
     int validMoveExists = 0;
     
     //moving east
     for(int i = aiPosition.y + 1; i < 6; i++){
          char point = board[aiPosition.x][i];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          aiMoves[aiPosition.x][i] = '1';
     }
     
     //moving west
     for(int i = aiPosition.y - 1; i >= 0; i--){
          char point = board[aiPosition.x][i];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          aiMoves[aiPosition.x][i] = '1';
     }
     
     //moving south
     for(int i = aiPosition.x + 1; i < 6; i++){
          char point = board[i][aiPosition.y];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          aiMoves[i][aiPosition.y] = '1';
     }
     
     //moving north
     for(int i = aiPosition.x - 1; i >= 0; i--){
          char point = board[i][aiPosition.y];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          aiMoves[i][aiPosition.y] = '1';
     }
     
     //moving northeast
     int x = aiPosition.x - 1;
     int y = aiPosition.y + 1;
     while(x >= 0 && y < 6){
          char point = board[x][y];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          aiMoves[x][y] = '1';
          x = x - 1;
          y = y + 1;
     }
     
     //moving northwest
     x = aiPosition.x - 1;
     y = aiPosition.y - 1;
     while(x >= 0 && y >=0){
          char point = board[x][y];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          aiMoves[x][y] = '1';
          x = x - 1;
          y = y - 1;
     }
     
     //moving southeast
     x = aiPosition.x + 1;
     y = aiPosition.y + 1;
     while(x < 6 && y < 6){
          char point = board[x][y];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          aiMoves[x][y] = '1';
          x = x + 1;
          y = y + 1;
     }
     
     //moving southwest
     x = aiPosition.x + 1;
     y = aiPosition.y - 1;
     while(x <6 && y >= 0){
          char point = board[x][y];
          if(point == '.' || point == 'P' || point == 'A'){
               break;
          }
          validMoveExists = 1;
          aiMoves[x][y] = '1';
          x = x + 1;
          y =  y - 1;
     }
     return validMoveExists;
}

/*
*Moves the ai such that it gets the maximal number of points for this move
*Param board: A 2d char array representing the game board
*Param aiMoves: a 2d char array whose elements correspond to those of the game board with 1 representing
*a valid move fot the ai 0 otherwise
*return int: returns the value of the octagon the ai moved to this turn
*/
int moveAI(char board[6][6], char aiMoves[6][6]){
     if(getAIValidMoves(board, aiMoves) == 0){
          printf("AI has no more Valid moves.\n");
          return 0;
     }
     int bestScore = 0;
     struct position bestMove;
     for(int i = 0; i < 6; i++){
          for(int j = 0; j < 6; j++){
               int score = ((int) board[i][j]) - 48;
               if(aiMoves[i][j] == '1' && score > bestScore){
                    bestScore = score;
                    bestMove.x = i;
                    bestMove.y = j;
               }
          }
     }
     board[aiPosition.x][aiPosition.y] = '.';
     aiPosition.x = bestMove.x;
     aiPosition.y = bestMove.y;
     board[aiPosition.x][aiPosition.y] = 'A';
     printf("AI moves to position %d, %d\n", aiPosition.x + 1, aiPosition.y + 1);
     return bestScore;
}
     

int main(int argc, const char* argv[]){
     srand(time(NULL));
     //the game board
     char board[6][6];
     char aiMoves[6][6];
     char playerMoves[6][6];
     //the scores of each player (starts with one because each player gets one free point)
     int playerScore = 1;
     int aiScore = 1;
     //holds the calculation for each players score i.e. 1 + 1 + 2...
     char playerCalc[100] = {"1"};
     char aiCalc[100] = {"1"};
     char scoreBuff[2];
     //represents wether there are moves to be made
     int isOver = 0;
     //start the game
     createBoard(board);
     printState(board);
     startGame(board);
     printState(board);
     //run the game to completion
     while(isOver == 0){
          int playerScoreTurn = movePlayer(board, playerMoves);
          printState(board);
          int aiScoreTurn = moveAI(board, aiMoves);
          printState(board);
          if(playerScoreTurn == 0 && aiScoreTurn == 0){
               isOver = 1;
          }
          if(playerScoreTurn > 0){
               playerScore = playerScore + playerScoreTurn;
               strcat(playerCalc, "+");
               sprintf(scoreBuff, "%d", playerScoreTurn);
               strcat(playerCalc, scoreBuff);
          }
          if(aiScoreTurn > 0){
               aiScore = aiScore + aiScoreTurn;
               strcat(aiCalc, "+");
               sprintf(scoreBuff, "%d", aiScoreTurn);
               strcat(aiCalc, scoreBuff);
          }
     }
     printf("Player Score: %s = %d\n", playerCalc, playerScore);
     printf("AI Score: %s = %d\n", aiCalc, aiScore);
     if(playerScore == aiScore){
          printf("It's a tie!\n");
     }
     else if(playerScore > aiScore){
          printf("Player Wins!\n");
     }
     else{
          printf("AI Wins!\n");
     }
}

