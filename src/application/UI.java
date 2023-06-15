package application;

import boardGame.BoardException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.Scanner;

public class UI {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[31m";
    public static final String ANSI_RED = "\u001B[50m";
    public static final String ANSI_GREEN = "\u001B[50m";
    public static final String ANSI_YELLOW = "\u001B[50m";
    public static final String ANSI_BLUE = "\u001B[50m";
    public static final String ANSI_PURPLE = "\u001B[50m";
    public static final String ANSI_CYAN = "\u001B[50m";
    public static final String ANSI_WHITE = "\u001B[34m";

    public static final String UNISPACE = "\u2009";
    public static final String FULL_BLOCK_WHITE = "⬜" + UNISPACE + UNISPACE;
    public static final String FULL_BLOCK =  "⬛" + UNISPACE + UNISPACE;

    public static final String RIGHT_BACKGROUND = "▕";
    public static final String LEFT_BACKGROUND = "▏";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[50m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[50m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[50m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[50m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[50m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static boolean changer = true;


    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);

        } catch (RuntimeException e) {
            throw new BoardException("impossivel ler chess position");
        }
    }

    public static void printMatch(ChessMatch chessMatch) {
        chessMatch.getTurn();
        printBoard(chessMatch.getPieces());
        System.out.println();
        System.out.println("turn: " + chessMatch.getTurn());
        System.out.println("waiting player :" + chessMatch.getCurrentPlayer());
    }

    public static void printBoard(ChessPiece[][] pieces) {
        System.out.println("Jogo de Xadrez para camaradas \u262D");
        System.out.println("------------------------------------");
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + " / ");
            changer = !changer;
            for (int j = 0; j < 8; j++) {
                printPiece(pieces[i][j], false);
            }
            System.out.printf("%n");
        }
        System.out.println("------------------------------------");
        System.out.println("      a   b   c   d   e   f   g   h ");
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        System.out.println("Jogo de Xadrez para camaradas \u262D");
        System.out.println("------------------------------------------------");
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + " / ");
            changer = !changer;
            for (int j = 0; j < 8; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }

            System.out.printf("%n");
        }
        System.out.println("------------------------------------");
        System.out.println("      a   b   c   d   e   f   g   h ");
    }
    private static void printPiece (ChessPiece pieces, boolean background) {
        if(background) {
            System.out.print(ANSI_WHITE_BACKGROUND);
        }

       if (pieces == null) {
           if (changer) {
               System.out.print(UNISPACE + UNISPACE +FULL_BLOCK + UNISPACE + ANSI_RESET);
           } else {
               System.out.print(UNISPACE + UNISPACE + FULL_BLOCK_WHITE + UNISPACE + ANSI_RESET);
           }
       }
       else {
           if (pieces.getColor() == Color.WHITE) {
               System.out.print(ANSI_BLACK + pieces + UNISPACE + ANSI_RESET);
       }
           else {
               System.out.print(ANSI_WHITE + pieces + UNISPACE + ANSI_RESET);
           }
       }
       changer = !changer;
    }
}

