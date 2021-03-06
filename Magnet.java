import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Magnet extends Rectangle{
    double magnet, ball;
    
    Magnet(int x, int y, int width, int height){
        super(x, y, width, height);
    }
    
    public void deflect(){
        //The position and speed of each of the two balls in the x and y axis before collision
        double xPosition1 = magnet, yPosition1 = magnet;
        double xPosition2 = ball, yPosition2 = ball;
        double xSpeed1 = 0, ySpeed1 = 0;
        double xSpeed2 = ball, ySpeed2 = ball;

        //Calculate the initial momentum of the balls
        double p1InitialMomentum = Math.sqrt(xSpeed1 * xSpeed2 + ySpeed1 * ySpeed2);
        double p2InitialMomentum = Math.sqrt(xSpeed2 * xSpeed2 + ySpeed2 * ySpeed2);

        //Calculate motion vectors
        double[] p1Trajectory = {xSpeed1, ySpeed1};
        double[] p2Trajectory = {xSpeed2, ySpeed2};

        //Calculate impact vector
        double[] impactVector = {xPosition2 - xPosition1, yPosition2 - yPosition1};
        double[] impactVectorNorm = normalizeVector(impactVector);

        //Calculate scalar product of each trajectory and impact vector
        double p1dotImpact = Math.abs(p1Trajectory[0] * impactVectorNorm[0] + p1Trajectory[1] * impactVectorNorm[1]);
        double p2dotImpact = Math.abs(p2Trajectory[0] * impactVectorNorm[0] + p2Trajectory[1] * impactVectorNorm[1]);

        //Calculate the deflection vectors - the amount of energy transferred from one ball to the other in each axis
        double[] p1Deflect = {-impactVectorNorm[0] * p2dotImpact, -impactVectorNorm[1] * p2dotImpact};
        double[] p2Deflect = {impactVectorNorm[0] * p1dotImpact, impactVectorNorm[1] * p1dotImpact};

        //Calculate final trajectories
        double[]p1FinalTrajectory = {p1Trajectory[0] * p1Deflect[0] - p2Deflect[0], p1Trajectory[1] + p1Deflect[1] - p2Deflect[1]};
        double[]p2FinalTrajectory = {p2Trajectory[0] * p2Deflect[0] - p1Deflect[0], p2Trajectory[1] + p2Deflect[1] - p1Deflect[1]};

        //Calculate the final energy in the system
        double p1FinalMomentum = Math.sqrt(p1FinalTrajectory[0] * p1FinalTrajectory[0] + p1FinalTrajectory[1] * p1FinalTrajectory[1]);
        double p2FinalMomentum = Math.sqrt(p2FinalTrajectory[0] * p2FinalTrajectory[0] + p2FinalTrajectory[1] * p2FinalTrajectory[1]);

        //Scale the resultant trajectories if we've accidentally broken the laws of physics
        double mag = (p1InitialMomentum + p2InitialMomentum) / (p1FinalMomentum + p2FinalMomentum);

        //Calculate the final x and y speed settings for the two balls after collision
        xSpeed1 = p1FinalTrajectory[0] * mag;
        ySpeed1 = p1FinalTrajectory[1] * mag;
        xSpeed2 = p2FinalTrajectory[0] * mag;
        ySpeed2 = p2FinalTrajectory[1] * mag;
    }

    //Converts a vector into a unit vector
    //Used by the deflect() method to calculate the resultant direction after a collision
    private double[] normalizeVector(double[] vec){
        double mag = 0.0;
        int dimensions = vec.length;

        double[] result = new double[dimensions];

        for(int i=0; i<dimensions; i++)
            mag += vec[i] * vec[i];

        mag = Math.sqrt(mag);

        if(mag == 0.0){
            result[0] = 1.0;
            for(int i=1; i<dimensions; i++)
                result[i] = 0.0;
        }
        else{
            for(int i=1; i<dimensions; i++)
                result[i] = vec[i] / mag;
        }
        return result;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval(x, y, height, width);
    }
}
