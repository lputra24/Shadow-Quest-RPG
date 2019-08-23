/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
public class Elvira extends Villagers{
	public final static String ELVIRAPATH="assets/units/shaman.png";
			
	private boolean playerInjured;
	public Elvira(int x, int y) throws SlickException{
		this.sprite=new Image(ELVIRAPATH);
		this.pos_x=x;
		this.pos_y=y;
		this.health=VIL_HEALTH;
		this.max_health=VIL_HEALTH;
		this.damage=VIL_DAMAGE;
		this.cooldown=TALK_COOLDOWN;
		this.name="Elvira";
		this.talk=false;
		this.playerInjured=false;
	}
	
	/**check if player is injured and heal him if true*/
	public void action(Player player,boolean interact,int delta){
		if(scan(player,PROXIMITY) && interact){
			if(player.health<player.max_health){
				this.playerInjured=true;
			}
			player.health=player.max_health;
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
		pic.drawCentered(this.pos_x, this.pos_y);
		this.renderHealth(g);
		if(this.talk && this.cooldown>0){
			if(this.playerInjured){
				this.dialog="You're looking much healthier now.";
				
				
			}
			else{
				this.dialog="Return to me if you ever need healing.";
				
			}
			
			this.makeDialog(g, this.dialog);
			
			
		}
		if(this.cooldown==TALK_COOLDOWN){
			this.playerInjured=false;
		}
	}

}
