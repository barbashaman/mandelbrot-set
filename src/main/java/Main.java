import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Main extends JComponent implements ActionListener {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static int ITERATIONS = 4;
    public static float SCALE = 100;
    private float hueOffset = 0;
    private BufferedImage buffer;
    private Timer timer;


    public Main() {

        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        timer = new Timer(1, this);

        JFrame jframe = new JFrame("Mandelbrot Set");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(true);
        jframe.getContentPane().add(this);
        jframe.pack();
        jframe.setVisible(true);

    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void addNotify() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        timer.start();
    }

    public void renderMandelbrotSet() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                int color = calculatePoint((i - WIDTH / 2f) / SCALE, (j - HEIGHT / 2f) / SCALE);
                System.out.println("color point: " + color);
                buffer.setRGB(i, j, color);
            }
        }
    }

    public int calculatePoint(float x, float y) {
        float cx = x;
        float cy = y;
        int i = 0;
        for (; i < ITERATIONS; i++) {
            float nx = x * x - y * y + cx;
            float ny = 2 * x * y + cy;
            x = nx;
            y = ny;
            if (x * x + y * y > 4) {
                break;
            }
        }
        if (i == ITERATIONS) return 0x00000000;
        return Color.HSBtoRGB(((float) i / ITERATIONS + hueOffset) % 1f, 0.5f, 1);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawImage(buffer, 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        hueOffset += 0.01f;
        renderMandelbrotSet();
        ITERATIONS = ITERATIONS + 2;

        repaint();

    }
}
