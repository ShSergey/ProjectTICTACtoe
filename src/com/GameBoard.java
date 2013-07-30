package com;

public class GameBoard {
    private final int size;
    private final static char DEFAULT_CHAR = '☐';
    private final static char SPACE_CHAR = ' ';
    private final static char BEGIN_CHAR = '.';
    private final static int DRAWN_MOVE = -2;
    private final static int ERROR_MOVE = -1;
    private final static int NEXT_MOVE = 0;
    private final static int WIN_MOVE = 1;

    private int countStroke = 0; // количество выполненных ходов
    private char[][] cell;// ячейки для отображения на экране
    private int[] line; //для проверки строк, столбцов и диагоналей

    public GameBoard(int size) {
        this.size = size;
        this.cell = new char[size][size];
        this.line = new int[2*size+2]; //размер равен кол-во строк + кол-во столбцов + первая диагональ + вторая диагональ
        clearCells();
    }

    public void clearCells() {
        for (int i = 0; i<size; i++) {
            clearLine(i);
        }
    }

    private void clearLine(int j) {
        for (int i = 0; i < size; i++) {
            cell[i][j] = DEFAULT_CHAR;
        }
    }

    public void showCells() {
        for (int i = size - 1 ; i >= 0; i--) {
            showLine(i);
        }
        System.out.print(BEGIN_CHAR);
        System.out.print(SPACE_CHAR);
        for (int i = 0; i < size; i++) {
            System.out.print(i);
            System.out.print(SPACE_CHAR);
        }
        System.out.println("");
    }

    private void showLine(int j) {
        System.out.print(j);
        System.out.print(SPACE_CHAR);
        for (int i = 0; i < size; i++) {
            System.out.print(cell[i][j]);
            System.out.print(SPACE_CHAR);
        }
        System.out.println();
    }

    private int move(Player currentPlayer) {
        int j = currentPlayer.row;
        int i = currentPlayer.column;
        if (i>size-1 || i<0 || j>size-1 || j<0 || cell[i][j] != DEFAULT_CHAR) {
            return ERROR_MOVE; // ошибочный ход
        }
        countStroke++; // увеличиваем количество ходов
        cell[i][j]      = currentPlayer.symbol;                 // записываем символ игрока в ячейку
        line[i]         = line[i] + currentPlayer.sign;         // считаем количество x и o в строках
        line[size + j]  = line[size + j] + currentPlayer.sign;  // считаем количество x и o в столбцах
        if (i==j) {
            line[2*size] = line[2*size] + currentPlayer.sign;   // считаем количество x и o в первой диагонали
        }
        if (j==size-i-1) {
            line[2*size+1] = line[2*size+1] + currentPlayer.sign; // считаем количество x и o во второй диагонали
        }

        if ((Math.abs(line[i])          == size) || // проверка строки
            (Math.abs(line[size + j])   == size) || // проверка столбца
            (Math.abs(line[2*size])     == size) || // проверка первой диагонали
            (Math.abs(line[2*size + 1]) == size)) { // проверка второй диагонали
            return WIN_MOVE; // currentPlayer выиграл,
        }

        if (countStroke == size*size) { // если все ходы выпонены но ничья
            return DRAWN_MOVE; // ничья
        }
        return NEXT_MOVE; // currentPlayer не выиграл
    }

    public void game() {
        Player player1 = new Player('x',+1);
        Player player2 = new Player('o',-1);
        Player player  = player1; // начинает игру player1
        System.out.println("Играет игрок - " + player.symbol);
        showCells();
        boolean gameOver = false;
        while (! gameOver) {
            player.inputRowColumn(); // получаем от игрока строку и столбец
            switch (move(player)) {   // делаем ход player
                case NEXT_MOVE: {  // переходим к следующему ходу
                    if (player.equals(player1)) {
                        player = player2;
                    } else {
                        player = player1;
                    }
                    showCells();
                    System.out.println("Играет игрок - " + player.symbol);
                    break;
                }
                case ERROR_MOVE: { // ошибочный ход
                    System.out.println("Ошибочный ход !");
                    showCells();
                    break;
                }
                case WIN_MOVE: {   // выигрышный ход
                    showCells();
                    System.out.println("Игрок - " + player.symbol+ " выиграл !");
                    gameOver = true;
                    break;
                }
                case DRAWN_MOVE: { // ничейный ход
                    showCells();
                    System.out.println("Ничья !");
                    gameOver = true;
                    break;
                }
            }
        }
    }

}
