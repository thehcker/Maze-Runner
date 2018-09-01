/* This is my maze game solution for final project.

I added a couple of move options not specified:
    'C'     "Cheat" - draw out maze solution (I used this to debug the maze game to verify the moves)
    'A'     Adds 10 to the totalMoves count (makes it quicker to debug the move warnings/lose game of 50, 75, 90, 100)
    'Q'     Quit the game
*/

import java.util.*;

public class MazeRunner { static Maze myMap = new Maze(); static int totalMoves = 0;

    public static void main(String[] args)
    {
        intro();
        playTheGame();
    }

    public static void intro()
    {
        System.out.println("Welcome to Maze Runner!");
        System.out.println();
        System.out.println("For the game, your choice of movement is as follows :");
        System.out.println("  R  moves you one space Right");
        System.out.println("  L  moves you one space Left");
        System.out.println("  U  moves you one space Up");
        System.out.println("  D  moves you one space Down");
        System.out.println();
        System.out.println("  Q  quits the game (give up)");
        System.out.println();
        System.out.println("Here is your initial position in the maze :");

        myMap.printMap();
    }

    public static String moveDescription(char move){
        String  description = "INVALID";

        if (move == 'R')
            description = "right";
        else if (move == 'L')
            description = "left";
        else if (move == 'U')
            description = "up";
        else if (move == 'D')
            description = "down";
        else if (move == 'Q')
            description = "QUIT";

        return description;
    }

    public static boolean navigatePit(char move){
        Scanner input = new Scanner(System.in);
        String  moveChoice;
        boolean jumpThePit = false;

        System.out.print("Watch out! There is a pit in direction "+moveDescription(move)+".  Jump it (Y/N) ? ");
        moveChoice = input.next();
        if (moveChoice.toUpperCase().charAt(0) == 'Y')
        {
            myMap.jumpOverPit("move");
            jumpThePit = true;
        }

        return jumpThePit;
    }

    public static char getMove(){
        Scanner input = new Scanner(System.in);
        boolean validMove = false;
        char    move = ' ';

        while ( !validMove )
        {
            boolean jumpThePit = false;

            move = ' ';
            while ( !(move == 'R' || move == 'L' || move == 'U' || move == 'D' || move == 'Q' || move == 'C' || move == 'A') )
            {
                String  moveChoice;

                System.out.print("Enter move #"+totalMoves+" (R, L, U, D, Q) : ");
                moveChoice = input.next();
                move = (moveChoice.toUpperCase()).charAt(0);
            }

            if (move == 'R')
            {
                if (validMove = myMap.canIMoveRight())
                    myMap.moveRight();
                else if (myMap.isThereAPit("move"))
                    jumpThePit = navigatePit(move);
                else
                    System.out.println("** Sorry, you’ve hit a wall and cannot move "+moveDescription(move));
            }
            else if (move == 'L')
            {
                if (validMove = myMap.canIMoveLeft())
                    myMap.moveLeft();
                else if (myMap.isThereAPit("move"))
                    jumpThePit = navigatePit(move);
                else
                    System.out.println("** Sorry, you’ve hit a wall and cannot move "+moveDescription(move));
            }
            else if (move == 'U')
            {
                if (validMove = myMap.canIMoveUp())
                    myMap.moveUp();
                else if (myMap.isThereAPit("move"))
                    jumpThePit = navigatePit(move);
                else
                    System.out.println("** Sorry, you’ve hit a wall and cannot move "+moveDescription(move));
            }

            else if (move == 'D')
            {
                if (validMove = myMap.canIMoveDown())
                    myMap.moveDown();
                else if (myMap.isThereAPit("move"))
                    jumpThePit = navigatePit(move);
                else
                    System.out.println("** Sorry, you’ve hit a wall and cannot move "+moveDescription(move));
            }
            else if (move == 'Q')
                validMove = true;
            else if (move == 'C')   // cheat, show solution (for debug testing)
                myMap.printMap();
            else if (move == 'A')   // add 10 to the move count (for debug testing)
                totalMoves += 10;

            if (jumpThePit)
            {
                System.out.println("You jumped the pit.");
                validMove = true;
            }
        }

        return move;
    }

    public static void playTheGame()
    {
        boolean youQuit = false;
        boolean youLose = false;
        boolean youWin = false;

        do {
            char    move = ' ';

            if (totalMoves > 0)
            {
                System.out.println();
                System.out.println("Here is your current position in the maze :");

                myMap.printMap();
            }
            totalMoves++;
            move = getMove();
            System.out.println("================================================================================ move #"+totalMoves+" was "+move);

            if (move == 'Q')
                youQuit = true;
            else {
                youWin = myMap.didIWin();

                if ( !youWin )
                {
                    if (totalMoves >= 100)
                    {
                        youLose = true;
                        System.out.println("***\n*** Oh no! You took too long to escape, and now the maze exit is closed FOREVER >:[\n***");
                    }
                    else if (totalMoves == 90)
                    {
                        System.out.println("***\n*** DANGER! You have made 90 moves, you only have 10 moves left to escape!!\n***");
                    }
                    else if (totalMoves == 75)
                    {
                        System.out.println("***\n*** Alert! You have made 75 moves, you only have 25 moves left to escape.\n***");
                    }
                    else if (totalMoves == 50)
                    {
                        System.out.println("***\n*** Warning: You have made 50 moves, you have 50 remaining before the maze exit closes\n***");
                    }
                }
            }

        } while ( !(youWin || youQuit || youLose) );

        System.out.println();

        if (youQuit)
            System.out.println("You quit the game and forfeit after "+totalMoves+" moves.");
        else if (youWin)
            System.out.println("You won the game after "+totalMoves+" moves.");
        else
            System.out.println("You lost the game after "+totalMoves+" moves.");
    }
}
