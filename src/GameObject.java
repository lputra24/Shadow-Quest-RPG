/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class GameObject {
	
	protected Image sprite=null;
	protected float pos_x, pos_y;
	protected String name;
	public final static double PROXIMITY=50.0;
	public final static float SQUARE_SIDES=36.0f;
	public float getpos_x(){
		return pos_x;
	}
	
	/** Return x position of character in the map. */
	public float getpos_y(){
		return pos_y;
	}
	
	public void render (){
		Image pic=this.sprite;
		pic.drawCentered(pos_x, pos_y);
	}
	
	/**create a rectangle for each object to test collision*/
	public Rectangle bounds(){
		return new Rectangle(pos_x-0.5f*(sprite.getWidth()),
				pos_y-0.5f*(sprite.getHeight()),SQUARE_SIDES,SQUARE_SIDES);
	}
	
	/**scan if 'unit' is close within proximity
	 * @param proximity = how close param 'unit' is to this object*/
	public boolean scan(Unit unit,double proximity){
		double distsquared=calcDistance(unit);
		return Double.compare(distsquared, proximity)<=0;
	}
	
	/**calculate distance with param 'unit'*/
	public double calcDistance(Unit unit){
		double distx=(unit.pos_x)-(this.pos_x);
		double disty=(unit.pos_y)-(this.pos_y);
		double distsquare=Math.pow(distx, 2)+Math.pow(disty, 2);
		return Math.sqrt(distsquare);
	}

}
