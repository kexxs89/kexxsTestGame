package at.kexxs.game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import at.kexxs.game.board.Board;
import at.kexxs.game.board.Player;

/**
 * 
 */
public class Game extends JFrame {

  private static final Logger log = Logger.getLogger(Game.class.getName());

  private static final long serialVersionUID = 1L;
  Board board;
  Container gameContainer;
  static JTextField textField;
  public final static int WIDTH = 10;
  public final static int HEIGHT = 10;
  Player player1;
  Player player2;

  public Game() {

    initLayout();
  }

  public void initLayout() {
    gameContainer = getContentPane();
    gameContainer.setLayout(new BorderLayout());
    initButton();
    initBoard();
    initText();
    setSize(500, 500);
    setVisible(true);
  }

  public void initBoard() {
    board = new Board(this);
    gameContainer.add(board, BorderLayout.CENTER);
  }

  public void initButton() {

    final JButton button = new JButton("Start New Game");
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        board.restart();
        player1 = new Player(1, "Roter Spieler", true);
        player2 = new Player(2, "Blauer Spieler", false);
        board.fillBoardWithUnits(player1, player2);
        setText(player1.getName() + " ist an der Reihe");
        validate();

      }
    });
    gameContainer.add(button, BorderLayout.PAGE_START);

  }

  public void initText() {
    textField = new JTextField();
    textField.setText("Willkommen bei meinen Spiel!");
    textField.setVisible(true);
    textField.setEditable(false);
    gameContainer.add(textField, BorderLayout.PAGE_END);
  }

  public static void setText(String text) {
    textField.setText(text);
  }

  public Player changeActivePlayer() {
    if (player1.isActive()) {
      player1.setActive(false);
      player2.setActive(true);
      return player2;
    } else {
      player2.setActive(false);
      player1.setActive(true);
      return player1;
    }
  }

  public Player getAcitvePlayer() {
    if (player1.isActive()) {
      return player1;
    } else {
      return player2;
    }
  }

  public Player getNotAcitvePlayer() {
    if (player1.isActive()) {
      return player2;
    } else {
      return player1;
    }
  }

}
