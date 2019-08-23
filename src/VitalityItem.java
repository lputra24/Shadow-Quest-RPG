/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */
import org.newdawn.slick.SlickException;
public class VitalityItem extends Item{
	public VitalityItem(int pos_x, int pos_y, String ImagePath, 
			String name,int modifier) throws SlickException {
		
		super(pos_x, pos_y, ImagePath, name, modifier);
	}

	public void applyEffect(Player player,World world){
		if(this.scan(player,PROXIMITY) && this.effectApplied==false){
			player.max_health+=this.modifier;
			player.health+=this.modifier;
			this.effectApplied=true;
		}
	}
}



