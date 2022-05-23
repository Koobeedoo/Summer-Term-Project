import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Corner extends Rectangle{
    Corner(int x, int y, int width, int height){
        super(x, y, width, height);
    }
    
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawOval(x, y, width, height);
    }
}
