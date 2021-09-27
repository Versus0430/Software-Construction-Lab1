/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength)
    {
        turtle.color(PenColor.BLACK);
        for(int i=0; i<4; i++)
        {
            turtle.forward(sideLength);
            turtle.turn(90);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
       double x=0;
       x = 180 - (double) 360 / sides;
       return x;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        int sides=0,x=0;
        x=(int)(180-angle);
        sides =360 / x;
        return sides;
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        for(int i=0; i<sides; i++)
        {
            turtle.forward(sideLength);
            turtle.turn((double)180 - calculateRegularPolygonAngle(sides));
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        double x=0,t1=0,t2=0,angle=0;
        t1 = (double) targetX - currentX;
        t2 = (double) targetY - currentY;
        angle = Math.toDegrees(Math.atan(t1 / t2));
        if(t2 == 0)
            x = t1 > 0 ? 90 : 270;
        else if((t1>=0 && t2>0) || (t1<0 && t2!=0))
            x = angle;
        else
            x = 270 - angle;
        if(x >= currentBearing)
            return x - currentBearing;
        else
            return 360 - currentBearing + x;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        int currentX = xCoords.get(0), currentY = yCoords.get(0), length = xCoords.size(), targetX, targetY;
        double currentBearing = 0.0, x;
        List<Double> bea = new ArrayList<>();
        for(int i=1; i<length; i++)
        {
            targetX = xCoords.get(i);
            targetY = yCoords.get(i);
            x = calculateBearingToPoint(currentBearing,currentX,currentY,targetX,targetY);
            bea.add(x);
            currentBearing = bea.get(i-1);
            currentX = xCoords.get(i);
            currentY = yCoords.get(i);
        }
        return bea;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        //throw new RuntimeException("implement me!");
        ArrayList<Point> cH = new ArrayList<Point>();
        ArrayList<Point> scH = new ArrayList<Point>();
        scH.addAll(points);
        int length = scH.size();
        Point p;
        if(length > 3)
        {
               p = scH.get(0);
               for(int i=1; i<length; i++) //首先确定左下角的点为极点
               {
                   if(p.x() > scH.get(i).x())
                       p = scH.get(i);
                   else if(p.x() == scH.get(i).x() && p.y() > scH.get(i).y())
                       p = scH.get(i);
               }
               cH.add(p); // 将第一个极点加入凸包
               Point current=p;
               double smallangle,smalldis;
               int i=0;
               scH.remove(current);
               while(true)
               {
                   double currentbearing = 360.0, dis=0.0;
                   Point point = null;
                   for (Point target : scH)
                   {
                       smallangle = calculateBearingToPoint(0, (int) current.x(), (int) current.y(),(int) target.x(), (int) target.y());
                       smalldis = Math.pow(current.x() - target.x(), 2) + Math.pow(current.y() - target.y(), 2);
                       if (smallangle < currentbearing)
                       {
                           point = target;
                           currentbearing = smallangle;
                           dis = smalldis;
                       }
                       else if (smallangle == currentbearing && smalldis > dis) //偏角相同，取距离远的
                       {
                           point = target;
                           dis = smalldis;
                       }
                   }
                   current = point;
                   cH.add(point);
                   scH.remove(point);
                   i++;
                   if(i == 1)
                       scH.add(p);
                   if(cH.get(i) == p) //若再次遇到初始极点，则算法终止
                       break;
               }
               Set<Point> res = new HashSet<Point>();
               res.addAll(cH);
               return res;
        }
        else
            return points;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        //throw new RuntimeException("implement me!");
        PenColor[] color = new PenColor[4];
        color[0] = PenColor.PINK;
        color[1] = PenColor.YELLOW;
        color[2] = PenColor.RED;
        color[3] = PenColor.GREEN;
        int num=0;
        for(int i=0; i<40; i++)
        {
            turtle.color(color[num]);
            drawRegularPolygon(turtle, i+1, i*2);
            turtle.turn(90);
            num++;
            if(num==4)
                num=0;
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        //drawSquare(turtle, 40);
        //drawRegularPolygon(turtle,6,40);
        // draw the window
        drawPersonalArt(turtle);
        turtle.draw();
    }

}
