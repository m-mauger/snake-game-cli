package org.game;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Game {

    public static final int DIRECTION_NONE
            = 0,
            DIRECTION_RIGHT = 1, DIRECTION_LEFT = -1,
            DIRECTION_UP = 2, DIRECTION_DOWN = -2;
    private Snake snake;
    private Board board;
    private int direction;
    private boolean gameOver;
    private Cell nextCell;

    public Game(Snake snake, Board board)
    {
        this.snake = snake;
        this.board = board;
    }

    public Snake getSnake() { return snake; }

    public void setSnake(Snake snake)
    {
        this.snake = snake;
    }

    public Board getBoard() { return board; }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    public boolean isGameOver() { return gameOver; }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    public int getDirection() { return direction; }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    // We need to update the game at regular intervals,
    // and accept user input from the Keyboard.
    public void update()
    {
        //System.out.println("Going to update the game");

        try {
            nextCell = getNextCell(snake.getHead());
        }
        catch (Exception e) {
            gameOver = true;
        }

        if(!gameOver){

            if (snake.checkCrash(nextCell)) {
                setDirection(DIRECTION_NONE);
                gameOver = true;
            }
            else {
                if (nextCell.getCellType()
                        == CellType.FOOD) {
                    snake.grow();
                    board.generateFood();
                }
                else {
                    board.displayFoodLocation();
                }
                snake.move(nextCell);
            }

        }
    }

    private Cell getNextCell(Cell currentPosition)
    {
        //System.out.println("Going to find next cell");
        int row = currentPosition.getRow();
        int col = currentPosition.getCol();

        if (direction == DIRECTION_RIGHT) {
            col++;
        }
        else if (direction == DIRECTION_LEFT) {
            col--;
        }
        else if (direction == DIRECTION_UP) {
            row--;
        }
        else if (direction == DIRECTION_DOWN) {
            row++;
        }

        Cell nextCell = board.getCells()[row][col];

        return nextCell;
    }

    public static void main(String[] args)
    {

        System.out.println("Going to start game");

        Cell initPos = new Cell(0, 0);
        Snake initSnake = new Snake(initPos);
        Board board = new Board(10, 10);
        Game newGame = new Game(initSnake, board);
        newGame.gameOver = false;
        newGame.direction = DIRECTION_RIGHT;
        final int SNAKE_MAX_SIZE = board.ROW_COUNT * board.COL_COUNT;
        Scanner response = new Scanner(System.in);
        String playerResponse = "";
        Boolean loopQuestion = true;

        System.out.println("Snake is starting at " + initPos.getRow() + " " + initPos.getCol());
        newGame.board.generateFood();

        while (newGame.gameOver != true && initSnake.getSnakePartList().size() != SNAKE_MAX_SIZE){

            do {

                System.out.println("Please enter snake direction (up, right, down, left):");
                playerResponse = response.nextLine();

                if(playerResponse.equalsIgnoreCase("up")){
                    newGame.direction = DIRECTION_UP;
                    loopQuestion = false;
                }
                else if(playerResponse.equalsIgnoreCase("right")){
                    newGame.direction = DIRECTION_RIGHT;
                    loopQuestion = false;
                }
                else if(playerResponse.equalsIgnoreCase("down")){
                    newGame.direction = DIRECTION_DOWN;
                    loopQuestion = false;
                }
                else if(playerResponse.equalsIgnoreCase("left")){
                    newGame.direction = DIRECTION_LEFT;
                    loopQuestion = false;
                }
                else {
                    System.out.println("Please enter a valid response");
                    loopQuestion = true;
                }

            } while (loopQuestion == true);

            newGame.update();
        }

        System.out.println("Game Over!");

    }

}