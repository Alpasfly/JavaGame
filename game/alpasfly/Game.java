package game.alpasfly;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionID = 1L;

    public static int width  = 300;
    public static int height = width / 16 * 9;
    public static int scale  = 3;

    private boolean running = false;

    private Thread thread;
    private JFrame frame;
    private BufferedImage image = new BufferedImage(width, height,
            BufferedImage.TYPE_3BYTE_BGR);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Game() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);

        frame = new JFrame();
    }

    public synchronized void start() {
        running = true;
        thread  = new Thread(this, "Display");

        thread.start();
    }

    public synchronized void stop() {
        running = true;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Main game loop
    public void run() {
        while (running) {
            update();
            render();
        }
    }

    public void update() {

    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.dispose(); // Release resources
        bs.show();   // Show next avaliable buffer
    }

    public static void main(String[] args) {
        Game game = new Game();

        // Set window parameters
        game.frame.setResizable(false);
        game.frame.setTitle("La Leyenda de Haula: la Espada que Guarda el Cielo :D");
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }
}
