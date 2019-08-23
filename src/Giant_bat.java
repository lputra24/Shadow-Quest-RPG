/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;

public class Giant_bat extends Unit{
	
	public static final int HEALTH=40, 
			DAMAGE=0, RUN_COOLDOWN=5000,MOVE_COOLDOWN=3000;
	public static final String DREADBATPATH = "assets/units/dreadbat.png";
	private int dirx,diry;
	private boolean runaway;
	private final float RAND_MOVE_VELOCITY=0.2f;
	public Giant_bat (int pos_x,int pos_y) throws SlickException{
		this.sprite=new Image(DREADBATPATH);
		
		/**choose random direction*/
		this.dirx=World.randInt(-1,1);
		this.diry=World.randInt(-1,1);
		
		
		this.pos_x=pos_x;
		this.pos_y=pos_y;
		this.health=HEALTH;
		this.max_health=HEALTH;
		this.damage=DAMAGE;
		
		/**cooldown for running away*/
		this.cooldown=RUN_COOLDOWN;
		
		/**cooldown for changing movement*/
		this.maxCD=MOVE_COOLDOWN;
		this.name="Giant Bat";
		this.runaway=false;
		this.velocity=RAND_MOVE_VELOCITY;
	}
	
	/**update behaviour
	 * - run if health if being hit
	 * - if after RUN_COOLDOWN not being hit then continue moving random*/
	public void update(Player player,World world,int delta){
		int healthTemp=this.health;
		if(this.runaway==true){
			this.cooldown-=delta;
			
			/**initiate running away*/
			runOrFight(player,world,delta,false);
			if(this.cooldown<=0){
				this.runaway=false;
			}
		}
		this.damaged(player);
		/**health is reduced time to run*/
		if(this.health!=healthTemp){
			this.runaway=true;
			this.cooldown=RUN_COOLDOWN;
		}
		if(this.runaway==false){
			
			/**random movement*/
			if(this.maxCD<=0){
				this.dirx=World.randInt(-1,1);
				this.diry=World.randInt(-1,1);
				this.maxCD=MOVE_COOLDOWN;
			}
			this.maxCD-=delta;
			int x=this.dirx,y=this.diry;
			safeMove(world,x,y,delta,velocity);
		}
	}
	
}
