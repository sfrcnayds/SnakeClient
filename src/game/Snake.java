package game;


import javax.swing.JFrame;

public class Snake extends JFrame {

    public Snake() {
        initUI();
    }
    
    private void initUI() {
        

        setResizable(false);
        pack();
        
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
