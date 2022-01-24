package paddleGame;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Rectangle;


import javax.swing.JPanel;
import javax.swing.Timer;

 

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;


    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    public Gameplay(){

    
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }

    public int getBallYdir() {
        return ballYdir;
    }

    public void setBallYdir(int ballYdir) {
        this.ballYdir = ballYdir;
    }

    public int getBallXdir() {
        return ballXdir;
    }

    public void setBallXdir(int ballXdir) {
        this.ballXdir = ballXdir;
    }

    public int getTotalBricks() {
        return totalBricks;
    }

    public void setTotalBricks(int totalBricks) {
        this.totalBricks = totalBricks;
    }


    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.gray);
        g.fillRect(1,1, 692, 592);

        //draw map
        map.draw((Graphics2D)g);
        

        //borders
        g.setColor(Color.black);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        //the paddle
        g.setColor(Color.black);
        g.fillRect(playerX, 550, 100, 8);

        //the ball
        g.setColor(Color.green);
        g.fillOval(ballposX, ballposY, 20, 20);

        g.dispose();




    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play) {
            //ball to paddel
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = -ballYdir;
            }
            //ball/brick collision
            A: for(int i =0; i<map.map.length; i++){
                for(int j = 0; j<map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;
                        //check collision

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            
                            //check collision with left and right side and make ball go oposite direction
                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }

                            break A;


                        }

                    }
                }
            }


            //ball movement
            ballposX += ballXdir;
            ballposY += ballYdir;
            //left border
            if(ballposX < 0) {
                ballXdir = -ballXdir;
            }
            //top border
            if(ballposY < 0) {
                ballXdir = -ballXdir;
            }
            //right border
            if(ballposY < 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
       }
    @Override
    public void keyReleased(KeyEvent e) {
        }
    @Override
    public void keyTyped(KeyEvent e) {    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX= 600;
            } else {
                moveRight();
            }
        }
        
        if(e.getKeyCode()== KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX= 10;
            } else {
                moveLeft();
            }
        }
        
    
    }
    public void moveRight(){
        play = true;
        playerX+=20;
    }

    public void moveLeft(){
        play = true;
        playerX-=20;
    }

    

    
}
