package com;

import java.util.Scanner;

public class Player {
    private static final Scanner sc = new Scanner(System.in); // создаём объект класса Scanner

    public final char symbol;
    public final int sign;

    public int row = 0;
    public int column = 0;


    public Player(char symbol, int sign) {
        this.symbol = symbol;
        this.sign = sign;
    }

    public Player() {
        this('x', 1);
    }

    public void inputRowColumn() {
        while (true) {
            System.out.print("Введите номер столбца: ");
            if(sc.hasNextInt()) { // возвращает истинну если с потока ввода можно считать целое число
                column = sc.nextInt(); // считывает целое число с потока ввода и сохраняем в переменную
                break;
            } else {
                System.out.println("Вы ввели не целое число");
                sc.next();
            }
        }
        while (true) {
            System.out.print("Введите номер строки: ");
            if(sc.hasNextInt()) { // возвращает истинну если с потока ввода можно считать целое число
                row = sc.nextInt(); // считывает целое число с потока ввода и сохраняем в переменную
                break;
            } else {
                System.out.println("Вы ввели не целое число");
                sc.next();
            }
        }
    }
}
