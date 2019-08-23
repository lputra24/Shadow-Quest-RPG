/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;

/** Represents the player that being controlled by
 * user
 */
public class Player extends Unit{
	public final static String IMAGEPATH = "assets/units/player.png";
	public final static int INIT_HEALTH=100,INIT_DAMAGE=26,COOLDOWN=600;
	private final float VELOCITY = 0.25f;
	protected List<Item> inventory = new ArrayList<Item>();
	private Image spritealt,image=null;
	
	public Player(int pos_x, int pos_y) throws SlickException{
		
		this.sprite=new Image(IMAGEPATH);
		image=this.sprite;
		this.spritealt=(this.sprite).getFlippedCopy(true, false);
		this.pos_x=pos_x;
		this.pos_y=pos_y;
		this.health=INIT_HEALTH;
		this.max_health=INIT_HEALTH;
		this.damage=INIT_DAMAGE;
		this.cooldown=COOLDOWN;
		this.maxCD=COOLDOWN;
		this.name="Hero";
		this.attack=false;
		this.cdState=false;
	}
	/** Update the player position based on keyboard inputs. 
	 * @param enemyList = list of all aggresive enemies in the world
	 * */
	public void update(List<Item> itemList,List<AggresiveMonsters> enemyList,
			World world,double dir_x,double dir_y, int delta,boolean attack){
		
		if(dir_x>0){
			image=this.sprite;	
		}
		if(dir_x<0){
			image=this.spritealt;
		}
		this.isAttacking(attack,delta,this.maxCD);
		safeMove(world,dir_x,dir_y,delta,VELOCITY);
		/**look at each enemy and see if they deal damage to player*/
		for (int index = 0; index < enemyList.size(); index++){
			if(this.scan(enemyList.get(index), 50.0)){
				this.damaged(enemyList.get(index));
			}
		}
		
		/**look at each item if it is already retrieved put it in inventory*/
		for (int index = 0; index < itemList.size(); index++){
			if(itemList.get(index).effectApplied && 
					itemList.get(index).added==false){
				this.inventory.add(itemList.get(index));
				itemList.get(index).added=true;
			}
		}
		
		/**respawn character back at starting place if he dies*/
		if(this.health<=0){
			this.pos_x=World.PLAYER_X;
			this.pos_y=World.PLAYER_Y;
			this.health=this.max_health;
		}
	}
	
	
	public void render(Graphics g){
		image.drawCentered(this.pos_x, this.pos_y);
	}
	
	
	
	

}
