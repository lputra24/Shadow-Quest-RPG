/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
public class AggresiveMonsters extends Unit{
	public final static double AGGRO_RANGE=150.0;
	protected int dirx,diry;
	private final float VELOCITY=0.25f;
	public AggresiveMonsters (int pos_x,int pos_y,String imagePath,String name,
			int health,int damage,int cooldown) throws SlickException{
		this.sprite=new Image(imagePath);
		this.dirx=0;
		this.diry=0;
		this.pos_x=pos_x;
		this.pos_y=pos_y;
		this.health=health;
		this.max_health=health;
		this.damage=damage;
		this.cooldown=cooldown;
		this.maxCD=cooldown;
		this.name=name;
		this.attack=false;
		this.cdState=false;
		this.velocity=VELOCITY;
	}
	
	/**update behaviour if player is within aggro range*/
	public void update(Player player,World world,int delta){
		if(this.scan(player, AGGRO_RANGE) && 
				this.scan(player, PROXIMITY)==false){
			
			runOrFight(player,world,delta,true);
		}
		if(this.scan(player, PROXIMITY)){
			this.isAttacking(true,delta,this.maxCD);
		}
		this.damaged(player);
		
	}
	
}
