import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel implements Runnable{
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 12;
    static final int PADDLE_DIAMETER = 25;
    static final int GOAL_DIAMETER = 40;
    static final int CORNER_DIAMETER = 80;
    static final int MAGNET_DIAMETER = 8;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    Goal goal1;
    Goal goal2;
    Corner corner1;
    Corner corner2;
    Corner corner3;
    Corner corner4;
    Magnet magnet1;
    Magnet magnet2;
    Magnet magnet3;

    
    GamePanel(){
        newPaddles();
        newBall();
        newGoals();
        newCorners();
        newMagnets();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }


    public void newBall(){
        random = new Random();
        int spawn;
        //Random corner start the game
        //if(String.valueOf(player1)=0){
            spawn = random.nextInt(4);
            switch(spawn){
                case 0:
                    ball = new Ball(10, 10, BALL_DIAMETER, BALL_DIAMETER);
                    break;
                case 1:
                    ball = new Ball(10, 478, BALL_DIAMETER, BALL_DIAMETER);
                    break;
                case 2:
                    ball = new Ball(978, 10, BALL_DIAMETER, BALL_DIAMETER);
                    break;
                case 3:
                    ball = new Ball(978, 478, BALL_DIAMETER, BALL_DIAMETER);
                    break;
            }
        //}
        //If player 1 scores, spawns ball on either corner on player 2's side
        /*else if(newscore.player1 > score.player1){
            spawn = random.nextInt(2);
            switch(spawn){
                case 0:
                    ball = new Ball(GAME_WIDTH-25, 25, BALL_DIAMETER, BALL_DIAMETER);
                    break;
                case 1:
                    ball = new Ball(GAME_WIDTH-25, GAME_HEIGHT-25, BALL_DIAMETER, BALL_DIAMETER);
                    break;
            }
        }
        //If player 2 scores, spawns ball on either corner on player 1's side
        else if(newscore.player2 > score.player2){
            spawn = random.nextInt(2);
            switch(spawn){
                case 0:
                    ball = new Ball(25, 25, BALL_DIAMETER, BALL_DIAMETER);
                    break;
                case 1:
                    ball = new Ball(25, GAME_HEIGHT-25, BALL_DIAMETER, BALL_DIAMETER);
                    break;
            }
        }*/
    }


    public void newMagnets(){
        magnet1 = new Magnet((GAME_WIDTH/2)-(MAGNET_DIAMETER/2), GAME_HEIGHT/2, MAGNET_DIAMETER, MAGNET_DIAMETER);
        magnet2 = new Magnet((GAME_WIDTH/2)-(MAGNET_DIAMETER/2), GAME_HEIGHT/4, MAGNET_DIAMETER, MAGNET_DIAMETER);
        magnet3 = new Magnet((GAME_WIDTH/2)-(MAGNET_DIAMETER/2), GAME_HEIGHT-(GAME_HEIGHT/4), MAGNET_DIAMETER, MAGNET_DIAMETER);
    }


    public void newCorners(){
        corner1 = new Corner(-40, -40, CORNER_DIAMETER, CORNER_DIAMETER);
        corner2 = new Corner(960, -40, CORNER_DIAMETER, CORNER_DIAMETER);
        corner3 = new Corner(960, 460, CORNER_DIAMETER, CORNER_DIAMETER);
        corner4 = new Corner(-40, 460, CORNER_DIAMETER, CORNER_DIAMETER);
    }


    public void newGoals(){
        goal1 = new Goal(20, (GAME_HEIGHT/2)-(GOAL_DIAMETER/2), GOAL_DIAMETER, GOAL_DIAMETER);
        goal2 = new Goal(GAME_WIDTH-60, (GAME_HEIGHT/2)-(GOAL_DIAMETER/2), GOAL_DIAMETER, GOAL_DIAMETER);
    }


    public void newPaddles(){
        paddle1 = new Paddle(175, (GAME_HEIGHT/2)-(PADDLE_DIAMETER/2), PADDLE_DIAMETER, 1);
        paddle2 = new Paddle(GAME_WIDTH-(PADDLE_DIAMETER*8), (GAME_HEIGHT/2)-(PADDLE_DIAMETER/2), PADDLE_DIAMETER, 2);
    }


    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }


    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        goal1.draw(g);
        goal2.draw(g);
        corner1.draw(g);
        corner2.draw(g);
        corner3.draw(g);
        corner4.draw(g);
        magnet1.draw(g);
        magnet2.draw(g);
        magnet3.draw(g);
        Toolkit.getDefaultToolkit().sync();//Helps with the animation
    }


    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
    }


    public void checkCollision(){
        //Stops ball at top and bottom window edges
        if(ball.y <= 0){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= (GAME_HEIGHT-BALL_DIAMETER)){
            ball.setYDirection(-ball.yVelocity);
        }
        //Stops ball at left and right window edges
        if(ball.x <= 0){
            ball.setXDirection(-ball.xVelocity);
        }
        if(ball.x >= (GAME_WIDTH-BALL_DIAMETER)){
            ball.setXDirection(-ball.xVelocity);
        }

        //Ball bounce off paddle 1
        if(ball.intersects(paddle1)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //optional for additional difficulty
            if(ball.yVelocity > 0){
                ball.yVelocity++; //optional for additional difficulty
            }
            else{
                ball.yVelocity--;
            }
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        //Ball bounces off paddle 2
        if(ball.intersects(paddle2)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //optional for additional difficulty
            if(ball.yVelocity > 0){
                ball.yVelocity++; //optional for additional difficulty
            }
            else{
                ball.yVelocity--;
            }
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        //Stops paddles at window edges
        //Paddle 1 Y axis
        if(paddle1.y <= 0){
            paddle1.y = 0;
        }
        if(paddle1.y >= (GAME_HEIGHT - PADDLE_DIAMETER)){
            paddle1.y = GAME_HEIGHT - PADDLE_DIAMETER;
        }
        //Paddle 1 X axis
        if(paddle1.x <= 0){
            paddle1.x = 0;
        }
        if(paddle1.x >= ((GAME_WIDTH/2) - PADDLE_DIAMETER)){
            paddle1.x = (GAME_WIDTH/2) - PADDLE_DIAMETER;
        }
        //Paddle 2 Y axis
        if(paddle2.y <= 0){
            paddle2.y = 0;
        }
        if(paddle2.y >= (GAME_HEIGHT - PADDLE_DIAMETER)){
            paddle2.y = GAME_HEIGHT - PADDLE_DIAMETER;
        }
        //Paddle 2 X axis
        if(paddle2.x >= (GAME_WIDTH - PADDLE_DIAMETER)){
            paddle2.x = (GAME_WIDTH - PADDLE_DIAMETER);
        }
        if(paddle2.x <= ((GAME_WIDTH/2) - 1)){
            paddle2.x = (GAME_WIDTH/2) - 1;
        } 

        //Scoring ball to goal
        //Gives player 2 a point and creates a new ball
        if( ball.x >= 20 && 
            ball.x <= 40 &&
            ball.y >= (GAME_HEIGHT/2)-(GOAL_DIAMETER/2) &&
            ball.y <=(GAME_HEIGHT/2)+(GOAL_DIAMETER/2)){
            score.player2++;
            newBall();
        }
        //Gives player 1 a point and creates a new ball
        if( ball.x >= 960 && 
            ball.x <= 980 &&
            ball.y >= (GAME_HEIGHT/2)-(GOAL_DIAMETER/2) &&
            ball.y <=(GAME_HEIGHT/2)+(GOAL_DIAMETER/2)){
            score.player1++;
            newBall();
        }

        //Scoring paddle to goal
        //Gives player 2 a point and creates a new ball
        if( paddle1.x >= 20 && 
            paddle1.x <= 40 &&
            paddle1.y >= (GAME_HEIGHT/2)-(GOAL_DIAMETER/2) &&
            paddle1.y <=(GAME_HEIGHT/2)+(GOAL_DIAMETER/2)){
            score.player2++;
            newPaddles();
            newBall();
        }
        //Gives player 1 a point and creates a new ball
        if( paddle2.x >= 960 && 
            paddle2.x <= 980 &&
            paddle2.y >= (GAME_HEIGHT/2)-(GOAL_DIAMETER/2) &&
            paddle2.y <=(GAME_HEIGHT/2)+(GOAL_DIAMETER/2)){
            score.player1++;
            newPaddles();
            newBall();
        }
    }

    

    public void run(){
        //Game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if(delta >= 1){
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }


    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}