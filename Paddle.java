import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle{
    int id;
    int xVelocity;
    int yVelocity;
    int speed = 6;
    
    
    Paddle(int x, int y, int PADDLE_DIAMETER, int id){
        super(x, y, PADDLE_DIAMETER, PADDLE_DIAMETER);
        this.id = id;
    }

    public void keyPressed(KeyEvent e){
        switch(id){
            case 1:
                if(e.getKeyCode() == KeyEvent.VK_W){
                    setYDirection(-speed);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    setYDirection(speed);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_A){
                    setXDirection(-speed);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_D){
                    setXDirection(speed);
                    move();
                }
                break;
            case 2:
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    setYDirection(-speed);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    setYDirection(speed);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    setXDirection(-speed);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    setXDirection(speed);
                    move();
                }
                break;
        }
    }
    
    public void keyReleased(KeyEvent e){
        switch(id){
            case 1:
                if(e.getKeyCode() == KeyEvent.VK_W){
                    setYDirection(0);
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    setYDirection(0);
                }
                if(e.getKeyCode() == KeyEvent.VK_A){
                    setXDirection(0);
                }
                if(e.getKeyCode() == KeyEvent.VK_D){
                    setXDirection(0);
                }
                break;
            case 2:
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    setYDirection(0);   
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    setYDirection(0);  
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    setXDirection(0);   
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    setXDirection(0);
                }
                break;
        }
    }

    public void setYDirection(int yDirection){
        yVelocity = yDirection;
    }

    public void setXDirection(int xDirection){
        xVelocity = xDirection;
    }

    public void move(){
        y = y + yVelocity;
        x = x + xVelocity;
    }

    public void draw(Graphics g){
        if(id == 1){
            g.setColor(Color.black);
        }
        else{
            g.setColor(Color.black);
        }
        g.fillOval(x, y, height, width);
    }
}