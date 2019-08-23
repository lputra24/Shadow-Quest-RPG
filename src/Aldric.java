/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
public class Aldric extends Villagers{
	public final static String ALDRICPATH = "assets/units/prince.png";
			
	private boolean objective;
	public Aldric(int x, int y) throws SlickException{
		this.sprite=new Image(ALDRICPATH);
		this.pos_x=x;
		this.pos_y=y;
		this.health=VIL_HEALTH;
		this.max_health=VIL_HEALTH;
		this.damage=VIL_DAMAGE;
		this.cooldown=VIL_COOLDOWN;
		this.name="Prince Aldric";
		this.talk=false;
		
		/**true if elixir has been given*/
		this.objective=false;
	}
	public boolean getObjective(){
		return this.objective;
	}
	
	/**check player's inventory for "Elixir of Life" and take it if it is found*/
	public void action(Player player,boolean interact,int delta){
		if(scan(player,PROXIMITY) && interact){
			for (int index = 0; index < player.inventory.size(); index++){
				if(player.inventory.get(index).name.equals("Elixir of Life")){
					player.inventory.remove(index);
					this.objective=true;
				}
			}
			this.talk=true;
			
			
		}
		if(this.talk){
			this.cooldown-=delta;
		}
		
		if (this.cooldown<=0){
			this.cooldown=TALK_COOLDOWN;
			this.talk=false;
		}
	
	}
	
	/**render image and dialog*/
	public void render (Graphics g){
		Image pic=this.sprite;
		pic.drawCentered(pos_x, pos_y);
		this.renderHealth(g);
		if(this.talk && this.cooldown>0){
			if(this.objective){
				this.dialog="The elixir! My father is cured! Thank you!";
				
			}
			else{
				this.dialog="Please seek out the Elixir of Life to cure the king.";
			}
			makeDialog(g,this.dialog);
			
		}
		
	}

}
