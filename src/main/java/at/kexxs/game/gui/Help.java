package at.kexxs.game.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTextPane;

public class Help {

  private JFrame frame;

  /**
   * Create the application.
   */
  public Help() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 450, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

    final JTextPane txtpnHilfe = new JTextPane();
    txtpnHilfe.setEnabled(false);
    txtpnHilfe.setEditable(false);
    txtpnHilfe.setText("Hilfe");
    frame.getContentPane().add(txtpnHilfe);
  }

  public JFrame getFrame() {
    return frame;
  }

  public void setFrame(JFrame frame) {
    this.frame = frame;
  }

}
