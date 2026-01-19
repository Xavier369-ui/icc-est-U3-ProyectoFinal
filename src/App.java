import controllers.GrafoController;
import views.MainFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GrafoController controller = new GrafoController("data");
            MainFrame frame = new MainFrame(controller);
            frame.setVisible(true);
        });
    }
}