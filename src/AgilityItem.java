/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */
import org.newdawn.slick.SlickException;

public class AgilityItem extends Item{

	public AgilityItem(int pos_x, int pos_y, String ImagePath, 
			String name, int modifier) throws SlickException {
		
		super(pos_x, pos_y, ImagePath, name, modifier);
		// TODO Auto-generated constructor stub
	}
	public void applyEffect(Player player,World world){
		if(this.scan(player,PROXIMITY) && this.effectApplied==false){
			player.maxCD-=this.modifier;
			player.cooldown-=this.modifier;
			this.effectApplied=true;
		}
	}

}
