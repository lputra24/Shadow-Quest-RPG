/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */
import org.newdawn.slick.SlickException;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
public class Garth extends Villagers{
	public final static String GARTHPATH="assets/units/peasant.png";
	
	private int itemProgress;
	public Garth(int x, int y) throws SlickException{
		this.sprite=new Image(GARTHPATH);
		this.pos_x=x;
		this.pos_y=y;
		this.health=VIL_HEALTH;
		this.max_health=VIL_HEALTH;
		this.damage=VIL_DAMAGE;
		this.cooldown=VIL_COOLDOWN;
		this.name="Garth";
		this.itemProgress=0;
	}
	
	/**get item progress which increased everytime player found the item*/
	public int getItemProgress(){
		return this.itemProgress;
	}
	
	/**render image and dialog*/
	public void action(Player player,boolean interact,int delta){
		if(scan(player,PROXIMITY) && interact){
			if(this.itemProgress==0){
				for (int index = 0; index < player.inventory.size(); index++){
					if(player.inventory.get(index).name.equals("Amulet of Vitality")){
						this.itemProgress+=1;
					}
				}
			}
			if(this.itemProgress==1){
				for (int index = 0; index < player.inventory.size(); index++){
					if(player.inventory.get(index).name.equals("Sword of Strength")){
						this.itemProgress+=1;
					}
				}
			}
			if(this.itemProgress==2){
				for (int index = 0; index < player.inventory.size(); index++){
					if(player.inventory.get(index).name.equals("Tome of Agility")){
						this.itemProgress+=1;
					}
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
			if(this.itemProgress==0){
				this.dialog="Find the Amulet of Vitality, across the river to the west.";
				
			}
			else if(this.itemProgress==1){
				this.dialog="Find the Sword of Strength - cross the bridge to the east, then head south.";
			}
			else if(this.itemProgress==2){
				this.dialog="Find the Tome of Agility, in the Land of Shadows.";
			}
			else{
				this.dialog="You have found all the treasure I know of.";
			}
			makeDialog(g,this.dialog);
			
			
		}
		
	}

}
