/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;



public class Unit extends GameObject{
	/**maxCD is the maximum cooldown of unit*/
	protected int max_health,health,damage,cooldown,hit,maxCD;
	
	/**cd state is true if cooldown is counting down*/
	protected boolean attack,cdState;
	private final double [][] DIRECTION=new double [][]
			{{0,0},{1,0},{0,1},{-1,0},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
	protected float velocity;
	private int bar_width = 70;
	private int bar_height = 20;
	private int height_from_char=50;
	
	/**how long in delta does an attack command valid*/
	private int attack_window = 50;
	

	/** Return x position of character in the map. */
	
	public int getHit(){
		return this.hit;
	}
	public boolean getAttack(){
		return this.attack;
	}
	public int gethealth(){
		return this.health;
	}
	public int getdamage(){
		return this.damage;
	}
	public int getcooldown(){
		return this.cooldown;
	}
	

	/**render unit and its health bar*/
	public void render (Graphics g){
		Image pic=this.sprite;
		pic.drawCentered(pos_x, pos_y);
		renderHealth(g);
		
	}
	
	/**render health bar for each unit that needs it*/
	public void renderHealth(Graphics g){
		Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);		// Red, transp
        
        /**magic number from specification algorithm for health bar width */
        if(g.getFont().getWidth(this.name)+6>70){
        	bar_width = g.getFont().getWidth(this.name)+6;
        }
        
        int bar_x = (int)(this.pos_x)-bar_width/2;
        int bar_y = (int)(this.pos_y)-height_from_char;
        int health_percent = this.health;                         
        int hp_bar_width = (int) (bar_width * health_percent);
        int text_x = bar_x + (bar_width - g.getFont().getWidth(this.name)) / 2;
        int text_y = (int)(this.pos_y)-height_from_char;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width/this.max_health, bar_height);
        g.setColor(VALUE);
        g.drawString(this.name, text_x, text_y);
	}
	
	/**makes unit move in respect to other unit and environment collision*/
	public void safeMove(World world,double dir_x,double dir_y,int delta,
			float speed){
		double[] dir;
    	dir=world.isBlock(this,dir_x, dir_y);
		float xtemp=this.pos_x,ytemp=this.pos_y;
		this.pos_x+=dir[0]*speed*delta;
		if (world.isCollide(this)!=null){
			this.pos_x=xtemp;
		}
		
		this.pos_y+=dir[1]*speed*delta;
		if (world.isCollide(this)!=null){
			this.pos_y=ytemp;
		}
	}
	
	/**make this unit health goes down if the attacker attacks and 
	 * it is close enough
	 * @param unit = attacker*/
	public boolean damaged(Unit unit){
		if(unit.cooldown<unit.maxCD-attack_window){
			unit.attack=false;
		}
		if(this.scan(unit, PROXIMITY) && unit.attack){
			if(this.health-unit.hit<0){
				this.health=0;
			}
			else{
				this.health=this.health-unit.hit;
			}
			unit.attack=false;
			return true;
		}
		else{
			return false;
		}
	}
	
	/**initiate battle if other unit is close enough in respect 
	 * to unit's attack cooldown
	 * @param attack_state =  check if unit initiate battle 
	 * @param delta = time passed between frames
	 * @param cd = attacking unit's max attack cooldown
	 * */
	public void isAttacking(boolean attack_state,int delta,int cd){
		if(attack_state){
			if(this.cdState==false){
				this.attack=attack_state;
				this.hit= World.randInt(0, this.damage);
			}
			this.cdState=true;
		}
		if(this.cdState==true){
			if(this.cooldown>0){
				this.cooldown-=delta;
			}
			else{
				this.cooldown=cd;
				this.cdState=false;
			}
		}
		
	}
	
	/**AI for enemy unit either to run after being attacked 
	 * or fight when approached
	 * @param approach = true if aggresive and false if passive*/
	public void runOrFight(Player player,World world,int delta,boolean approach){
		double [] chosenDir={0,0};
		double maxDistance=calcDistance(player),distanceNow;
		float xtemp=this.pos_x,ytemp=this.pos_y;
		
		/**test each move and choose one that is more desirable
		 * */
		for(int index = 0; index < DIRECTION.length; index++){
			safeMove(world,DIRECTION[index][0],DIRECTION[index][1],delta,velocity);
			distanceNow=calcDistance(player);
			if(approach==false){
				/**choose the farthest one*/
				if(Double.compare(maxDistance, distanceNow)<0){
					maxDistance=distanceNow;
					chosenDir[0]=(DIRECTION[index][0])*
							(Math.abs((player.pos_x)-(this.pos_x)))/maxDistance;
					chosenDir[1]=(DIRECTION[index][1])*
							(Math.abs((player.pos_y)-(this.pos_y)))/maxDistance;
				}
			}
			else{
				/**choose the closest one*/
				if(Double.compare(maxDistance, distanceNow)>0){
					maxDistance=distanceNow;
					chosenDir[0]=(DIRECTION[index][0])*
							(Math.abs((player.pos_x)-(this.pos_x)))/maxDistance;
					chosenDir[1]=(DIRECTION[index][1])*
							(Math.abs((player.pos_y)-(this.pos_y)))/maxDistance;
				}
			}
			
			this.pos_x=xtemp;
			this.pos_y=ytemp;
		}
		safeMove(world,chosenDir[0],chosenDir[1],delta,velocity);
	}
}
