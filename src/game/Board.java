package game;

import SnakeClient.Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel{

    public int B_WIDTH;
    public int B_HEIGHT;
    public int apple_x;
    public int apple_y;
    public int dots;

    public Double x[];
    public Double y[];


    public boolean inGame = true;

    private Image ball;
    private Image apple;
    private Image head;

    public Board() {

    }
    
    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    }

    public void initGame(Map<String,Object> initValues) {
        dots = (int)initValues.get("dots");
        B_WIDTH = (int)initValues.get("B_WIDTH");
        B_HEIGHT = (int)initValues.get("B_HEIGHT");
        apple_x = (int)initValues.get("apple_x");
        apple_y = (int)initValues.get("apple_y");
        int xs[] = (int[])initValues.get("x");
        int ys[] = (int[])initValues.get("y");
        Double x[] = new Double[xs.length];
        Double y[] = new Double[xs.length];
        for(int i = 0; i<xs.length;i++){
            x[i] = Double.valueOf(xs[i]);
            y[i] = Double.valueOf(ys[i]);
        }
        this.x = x;
        this.y = y;
        initBoard();
    }

    public void move(Map<String,Object> moveValues) {
        dots = ((Double)moveValues.get("dots")).intValue();
        apple_x = ((Double)moveValues.get("apple_x")).intValue();
        apple_y = ((Double)moveValues.get("apple_y")).intValue();
        ArrayList<Double> xs= (ArrayList<Double>) moveValues.get("x");
        ArrayList<Double> ys= (ArrayList<Double>) moveValues.get("y");
        x = xs.toArray(new Double[xs.size()]);
        y = ys.toArray(new Double[ys.size()]);
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        
        if (inGame) {
            g.drawImage(apple,apple_x , apple_y, this);
            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z].intValue(), y[z].intValue(), this);
                } else {
                    g.drawImage(ball, x[z].intValue(), y[z].intValue(), this);
                }
            }
            Toolkit.getDefaultToolkit().sync();

        } else {
            gameOver(g);
        }        
    }

    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            Message msg;
            switch (key){
                case KeyEvent.VK_LEFT:
                    msg = new Message(Message.Message_Type.LeftArrow);
                    Client.Send(msg);
                    break;
                case KeyEvent.VK_RIGHT:
                    msg = new Message(Message.Message_Type.RightArrow);
                    Client.Send(msg);
                    break;
                case KeyEvent.VK_UP:
                    msg = new Message(Message.Message_Type.UpArrow);
                    Client.Send(msg);
                    break;
                case KeyEvent.VK_DOWN:
                    msg = new Message(Message.Message_Type.DownArrow);
                    Client.Send(msg);
                    break;
            }
        }
    }
}
