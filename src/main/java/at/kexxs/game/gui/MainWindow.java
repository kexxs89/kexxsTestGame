package at.kexxs.game.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import at.kexxs.game.dice.Dice;
import at.kexxs.game.dice.DiceDTO;
import at.kexxs.game.dice.DiceResultDTO;
import at.kexxs.game.impl.Game;
import javafx.util.Callback;

public class MainWindow {

  private JFrame frame;
  
  private Callback callback;
	
	private static final Logger log = Logger.getLogger(Dice.class.getName());
	
	
	/**
   * Create the application.
   */
  public MainWindow() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 18));
    frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

    final Box verticalBox = Box.createVerticalBox();
    frame.getContentPane().add(verticalBox);

    final Component verticalStrut_1 = Box.createVerticalStrut(20);
    verticalBox.add(verticalStrut_1);

    final JLabel gameTitle = new JLabel("Willkommen bei N\u00F6dtastisch");
    gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    verticalBox.add(gameTitle);
    gameTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
    gameTitle.setHorizontalAlignment(SwingConstants.CENTER);

    final Component verticalStrut = Box.createVerticalStrut(20);
    verticalBox.add(verticalStrut);

    final Box horizontalBox = Box.createHorizontalBox();
    horizontalBox.setAlignmentY(Component.CENTER_ALIGNMENT);
    verticalBox.add(horizontalBox);

    final JButton btnStart = new JButton("Spiel starten");
    btnStart.setHorizontalAlignment(SwingConstants.LEFT);
    horizontalBox.add(btnStart);

    final Component verticalStrut_2 = Box.createVerticalStrut(20);
    verticalBox.add(verticalStrut_2);

    final JButton btnHilfe = new JButton("Hilfe");
    btnHilfe.setAlignmentX(Component.CENTER_ALIGNMENT);
    verticalBox.add(btnHilfe);
    btnHilfe.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        final Help help = new Help();
        help.getFrame().setVisible(true);
        frame.setVisible(false);
      }
    });
	
	
		final JButton btnDice = new JButton("Dice");
		btnDice.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(btnDice);
		btnDice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					callback = new Callback() {
						public Object call(Object param) {
							DiceResultDTO dice = (DiceResultDTO) param;
							log.info("Attacker:" + dice.getAttackValue() + " Defense:" + dice.getDefenseValue() + " Reslt: " + dice.isSuccess() );
							return null;
						}
					};
					final Dice dice = new Dice(callback, 1 , 2);

			}
		});

    btnStart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new Game();
        frame.setVisible(false);
      }
    });
    frame.setSize(1200, 800);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public JFrame getFrame() {
    return frame;
  }

  public void setFrame(JFrame frame) {
    this.frame = frame;
  }

}
