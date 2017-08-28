import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import view.HitoriJFrame;

/**
 *
 * @author DoDuy
 */
public class Program {
    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.print(ex.toString());
        }
        /* Create and display the form */
        SwingUtilities.invokeLater(() -> {
            new HitoriJFrame().setVisible(true);
        });
    }
}