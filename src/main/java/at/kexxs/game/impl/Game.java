package at.kexxs.game.impl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import at.kexxs.game.IGame;
import at.kexxs.game.board.impl.Board;
import at.kexxs.game.board.impl.Player;
import at.kexxs.game.constant.TextConstant;
import at.kexxs.game.unit.impl.Unit;
import at.kexxs.game.util.UnitAction;

/**
 * @author Markus
 */
public class Game extends JFrame implements IGame {

  private static final Logger log = Logger.getLogger(Game.class.getName());

  private static final long serialVersionUID = 1L;
  Board board;
  Container gameContainer;
  static JTextField textField;
  static JTextArea sideText;
  JPanel actionPanel = new JPanel();
  public final static int WIDTH = 10;
  public final static int HEIGHT = 10;
  Player player1;
  Player player2;

  public Game() {

    initLayout();
    restartGame();
  }
	
  public void initLayout() {
    log.info("Init Layout");
    gameContainer = getContentPane();
    gameContainer.setLayout(new BorderLayout());
    initButton();
    initBoard();
    initBottomText();
    initSideLayout();
    initActionBar();
    setSize(1450, 1000);
    setResizable(false);
    setVisible(true);
  }
	
	private void initActionBar() {
		
		final Dimension dimension = new Dimension(200, 400);
		actionPanel.setPreferredSize(dimension);
		actionPanel.setLayout(new GridLayout(2,0));
		gameContainer.add(actionPanel, BorderLayout.EAST);
		
	}
	
	public void initBoard() {
    board = new Board(this);
		final Dimension dimension = new Dimension(800, 800);
    board.setPreferredSize(dimension);
    gameContainer.add(board, BorderLayout.CENTER);
  }

  public void initButton() {
  	
  	Container buttonContainer = new Container();
  	buttonContainer.setLayout(new GridLayout(0,2));
  	
    final JButton bStartNewGame = new JButton("Start New Game");
    bStartNewGame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        restartGame();
      }
    });
		
		buttonContainer.add(bStartNewGame);

    final JButton bNextRound = new JButton("Runde beenden");
    bNextRound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

        changeActivePlayer();
        log.info("Change Activte Player new Active Player is: " + getAcitvePlayer().getName());
      }
    });
		buttonContainer.add(bNextRound);
		gameContainer.add(buttonContainer, BorderLayout.PAGE_START);
  }

  public void initBottomText() {
    log.info("Init Bottom Text");
    textField = new JTextField();
    textField.setText(TextConstant.GAME_WELCOME);
    textField.setVisible(true);
    textField.setEditable(false);
    gameContainer.add(textField, BorderLayout.PAGE_END);
  }

  public static void setText(String text) {
    log.info("Set Text: " + text);
    textField.setText(text);
  }

  public static void setSideText(String text) {
    log.info("Set Side Text: " + text);
    sideText.setText(text);
  }
  
  public Container getActionBar(){
  	return  actionPanel;
  	
	}

  public Player changeActivePlayer() {
    Player activePlayer = null;
    if (player1.isActive()) {
      player1.setActive(false);
      player2.setActive(true);
      player2.resestAllUnits();
      activePlayer = player2;
    } else {
      player2.setActive(false);
      player1.setActive(true);
      player1.resestAllUnits();
      activePlayer = player1;
    }
    
    board.setAction(UnitAction.IDLE);

    if (!activePlayer.checkIfKingIsAlive()) {
      gameEnded(getNotAcitvePlayer(), activePlayer);
    }

    return activePlayer;
  }

  public Player getAcitvePlayer() {

    if (player1.isActive()) {
      log.info("getActivePlayer " + player1.getName());
      return player1;
    } else {
      log.info("getActivePlayer " + player2.getName());
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

  public void initSideLayout() {
    log.info("Init Site Layout");
    sideText = new JTextArea();
    sideText.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
    sideText.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
    sideText.setText(TextConstant.GAME_SIDE_TEXT);
    sideText.setEditable(false);
    sideText.setLineWrap(true);
    sideText.setMargin(new Insets(10, 10, 10, 10));
    final Dimension dimension = new Dimension(200, 300);
    sideText.setPreferredSize(dimension);
    gameContainer.add(sideText, BorderLayout.WEST);
  }
	

  public void gameEnded(Player winner, Player loser) {

    JOptionPane.showMessageDialog(new Frame(), winner.getName() + " has won this game!");
    restartGame();

  }
	
  public void restartGame() {
    log.info(TextConstant.GAME_START_NEW_GAME);
    board.restart();
    player1 = new Player(1, TextConstant.RED_PLAYER, Color.cyan, true);
    player2 = new Player(2, TextConstant.BLUE_PLAYER, Color.lightGray, false);
    board.fillBoardWithUnits(player1, player2);
    setText(player1.getName() + " ist an der Reihe");
    validate();
  }

}
