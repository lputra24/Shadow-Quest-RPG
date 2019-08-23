/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */

import org.newdawn.slick.SlickException;

/** Represents the camera that controls our viewpoint.
 */
public class Camera
{
	/** CENTER_X and CENTER_Y is the middle point of the screen which is
	 *  (screenwidth/2,screenheight/2) */
	public final static int CENTER_X=400, CENTER_Y=300;
	
	private int MinX,MaxY,MinY,MaxX;

    /** The unit this camera is following */
    private Player unitFollow;
    
    /** The width and height of the screen */
    /** Screen width, in pixels. */
    public final int screenwidth;
    /** Screen height, in pixels. */
    public final int screenheight;

    
    /** The camera's position in the world, in x and y coordinates. */
    private int xPos;
    private int yPos;

    /** Returns the middle x value on screen 
     */
    public int getxPos() {
        return xPos;
    }

    /** Returns the middle y value on screen 
     */
    public int getyPos() {
        return yPos;
    }

    
    /** Create a new Camera object
     * containing its minimal, middle, and maximum
     * position as its properties. */
    public Camera(Player player, int screenwidth, int screenheight)
    {   
    	this.screenheight = screenheight;
    	this.screenwidth = screenwidth;
    	this.MinX = (int)(player.getpos_x())-CENTER_X;
    	this.MinY = (int)(player.getpos_y())-CENTER_Y+(RPG.panelheight/2);
    	this.xPos = (int)(player.getpos_x())-(this.MinX);
    	this.yPos = (int)(player.getpos_y())-(this.MinY);
    	this.MaxX = this.MinX + screenwidth;
    	this.MaxY = this.MinY + screenheight;
    	this.unitFollow = player;
    }

    /** Update the game camera to recentre it's viewpoint around the player 
     */
    public void update()
    throws SlickException
    {
        followUnit(unitFollow);
    }
    
    /** Returns the minimum x value on screen 
     */
    public int getMinX(){
        return MinX;
    }
    
    /** Returns the maximum x value on screen 
     */
    public int getMaxX(){
        return MaxX;
    }
    
    /** Returns the minimum y value on screen 
     */
    public int getMinY(){
        return MinY;
    }
    
    /** Returns the maximum y value on screen 
     */
    public int getMaxY(){
        return MaxY;
    }

    /** Tells the camera to follow a given unit
     * and update its properties. 
     */
    public void followUnit(Object unit)
    throws SlickException
    {
    	this.MinX = (int)(((Player)unit).getpos_x()) - CENTER_X;
    	this.MinY = (int)(((Player)unit).getpos_y()) - CENTER_Y+(RPG.panelheight/2);
    	this.xPos = (int)(((Player)unit).getpos_x()) - (this.MinX);
    	this.yPos = (int)(((Player)unit).getpos_y()) - (this.MinY);
    	this.MaxX = this.MinX + screenwidth;
    	this.MaxY = this.MinY + screenheight;
    }
    
}