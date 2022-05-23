import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Goal extends Rectangle{
    Goal(int x, int y, int width, int height){
        super(x, y, width, height);
    }
    
    public void draw(Graphics g){
        g.setColor(Color.gray);
        g.fillOval(x, y, height, width);
    }
}
