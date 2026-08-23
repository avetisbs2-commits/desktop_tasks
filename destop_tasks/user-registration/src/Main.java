import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserRegistrationFrame frame = new UserRegistrationFrame();
                frame.setVisible(true);
            }
        });
    }
}
