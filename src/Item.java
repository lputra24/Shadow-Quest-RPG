/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Item extends GameObject{
	/**status change that affects the player by having the item*/
	protected int modifier;
	
	/**effectApplied is true if the item has been retrieved by player
	 * added is true if the item is going to be added to inventory*/
	protected boolean effectApplied,added;
	
	public Item(int pos_x,int pos_y,String ImagePath,
			String name,int modifier) throws SlickException{
		
		this.pos_x=pos_x;
		this.pos_y=pos_y;
		this.sprite=new Image(ImagePath);
		this.name=name;
		this.modifier=modifier;
		this.effectApplied=false;
		this.added=false;
	}
	
	/**overrided in the subclass to apply each item effects to player status in respects to item modifier */
	public void applyEffect(Player player,World world){
		if(this.scan(player,PROXIMITY) && this.effectApplied==false){
			if(this.name.equals("Elixir of Life")==false){
				this.effectApplied=true;
			}
			else if(world.getEnding()){
				this.effectApplied=true;
			}
		}
	}
	public void render (int pos_x,int pos_y){
		Image pic=this.sprite;
		pic.draw(pos_x, pos_y);
	}

}
