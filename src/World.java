/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.newdawn.slick.Color;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
    /** TILE_OFFSET is used to check the next 1/3 tile.
     * this constant is used during terrain blocking to anticipate 
     * blocked tile 1/3 tile pixel in front of the player during movement */
    public final static int TILE=72,PLAYER_X=756,PLAYER_Y=684,
    		SCREENWIDTH=800,SCREENHEIGHT=600,TILE_OFFSET=TILE/3,
    		TileRenderedX=13,TileRenderedY=10, MAX_TILE=96;
    private final int [][] BAT_POS=new int [][] {{1431, 864}, {1154, 1321},
		{807, 2315}, {833, 2657}, {1090, 3200}, {676, 3187}, {836, 3966},
		{653, 4367}, {1343, 2731}, {1835, 3017}, {1657, 3954}, {1054, 5337},
		{801, 5921}, {560, 6682}, {1275, 5696}, {1671, 5991}, {2248, 6298},
		{2952, 6373}, {3864, 6695}, {4554, 6443}, {4683, 6228}, {5312, 6606},
		{5484, 5946}, {6371, 5634},{5473,3544},{5944,3339},{6301,3414},
		{6388,1994},{6410,1584},{5314,274}};
	private final int [][] ZOM_POS=new int [][] {{681,3201},{691,4360},{2166,2650},
		{2122,2725},{2284,2962},{2072,4515},{2006,4568},{2385,4629},{2446,4590},
		{2517,4532},{4157,6730},{4247,6620},{4137,6519},{4234,6449},{5879,4994},
		{5954,4928},{6016,4866},{5860,4277},{5772,4277},{5715,4277},{5653,4277},
		{5787,797},{5668,720},{5813,454},{5236,917},{5048,1062},{4845,996},
		{4496,575},{3457,273},{3506,779},{3624,1192},{2931,1396},{2715,1326},
		{2442,1374},{2579,1159},{2799,1269},{2768,739},{2099,956}};
	private final int ZOMBIE_HEALTH=60,ZOMBIE_DMG=10,ZOMBIE_CD=800;
	
	private final int [][] BAN_POS=new int [][] {{1889,2581},{4502,6283},{5248,6581},
		{5345,6678},{5940,3412},{6335,3459},{6669,260},{6598,339},{6598,528},{6435,528},
		{6435,678},{5076,1082},{5191,1187},{4940,1175},{4760,1039},{4883,889},{4427,553},
		{3559,162},{3779,1553},{3573,1553},{3534,2464},{3635,2464},{3402,2861},{3151,2857},
        {3005,2997},{2763,2263},{2648,2263},{2621,1337},{2907,1270},{2331,598},{2987,394},
		{1979,394},{2045,693},{2069,1028}};
		private final int BANDIT_HEALTH=40,BANDIT_DMG=8,BANDIT_CD=200;
		
	private final int [][] SKEL_POS=new int [][] {{1255,2924},{2545,4708},{4189,6585},
		{5720,622},{5649,767},{5291,312},{5256,852},{4790,976},{4648,401},{3628,1181},
		{3771,1181},{3182,2892},{3116,3033},{2803,2901},{2850,2426},{2005,1524},
		{2132,1427},{2242,1343},{2428,771},{2427,907},{2770,613},{2915,477},{1970,553},
		{2143,1048}};
	private final int SKELETON_HEALTH=100,SKELETON_DMG=16,SKELETON_CD=500;
	
	private final int DRAELIC_POS [] = new int [] {2069,510};
	private final int DRAELIC_HEALTH=140,DRAELIC_DMG=30,DRAELIC_CD=400;
	
	private final int ELVIRA_POS [] = new int [] {738,549};
	private final int ALDRIC_POS [] = new int [] {467,679};
	private final int GARTH_POS [] = new int [] {756,870};
	
	private final int AMULET_OF_VITALITY_POS []= new int [] {965, 3563};
	private final int AMULET_OF_VITALITY_MOD= 80;
	
	private final int SWORD_OF_STRENGTH_POS []= new int [] {546, 6707};
	private final int SWORD_OF_STRENGTH_MOD= 30;
	
	private final int TOME_OF_AGILITY_POS []= new int [] {4791, 1253};
	private final int TOME_OF_AGILITY_MOD= 300;
	
	private final int ELIXIR_OF_LIFE_POS [] = new int [] {1976, 402};
	private final int ELIXIR_OF_LIFE_MOD= 0;
	
	/**List of objects for testing collision for each other*/
    private List<Unit> objectList = new ArrayList<Unit>();
    
    /**array lists for each subgroups*/
    protected List<AggresiveMonsters> enemyList = new ArrayList<AggresiveMonsters>();
    protected List<Giant_bat> batList = new ArrayList<Giant_bat>();
    protected List<Item> itemList = new ArrayList<Item>();
    
	private TiledMap map;
	private Player player;
	private Aldric aldric;
	private Elvira elvira;
	private Garth garth;
	private Camera camera;
	private Image panel;
	private int NextTileID_x,NextTileID_y,try_x,try_y,count=0;
	private boolean ending=false,help=false;
	
	 /** Create a new World object. */
    public World()
    throws SlickException
    {
    	int index;
    	map = new TiledMap("assets/map.tmx");
    	player = new Player(PLAYER_X,PLAYER_Y);
    	aldric = new Aldric(ALDRIC_POS[0],ALDRIC_POS[1]);
    	elvira = new Elvira(ELVIRA_POS[0],ELVIRA_POS[1]);
    	garth= new Garth(GARTH_POS[0],GARTH_POS[1]);
    	panel=new Image("assets/panel.png");
    	
    	/**Creating each item in the game*/
    	this.itemList.add(new VitalityItem(AMULET_OF_VITALITY_POS [0], 
    			AMULET_OF_VITALITY_POS [1],"assets/items/amulet.png",
    			"Amulet of Vitality",AMULET_OF_VITALITY_MOD));
    	
    	this.itemList.add(new StrengthItem(SWORD_OF_STRENGTH_POS [0], 
    			SWORD_OF_STRENGTH_POS [1],"assets/items/sword.png",""
    					+ "Sword of Strength",SWORD_OF_STRENGTH_MOD));
    	
    	this.itemList.add(new AgilityItem(TOME_OF_AGILITY_POS [0], 
    			TOME_OF_AGILITY_POS [1],"assets/items/tome.png","Tome of Agility",
    			TOME_OF_AGILITY_MOD));
    	
    	this.itemList.add(new Item(ELIXIR_OF_LIFE_POS [0], ELIXIR_OF_LIFE_POS [1],
    			"assets/items/elixir.png","Elixir of Life",ELIXIR_OF_LIFE_MOD));
    	
    	/**add each unit for testing collision later*/
    	this.objectList.add(aldric);
    	this.objectList.add(elvira);
    	this.objectList.add(garth);
    	this.objectList.add(player);
    	
    	/**spawning each enemy unit and put them into object 
    	 * list for collision testing*/
    	for (index = 0; index < BAT_POS.length; index++) {
        	this.batList.add(new Giant_bat(BAT_POS[index][0], BAT_POS[index][1]));
        	this.objectList.add((this.batList).get(index));
        }
    	for (index = 0; index < ZOM_POS.length; index++) {
    		this.enemyList.add(new AggresiveMonsters(ZOM_POS[index][0], 
    				ZOM_POS[index][1],"assets/units/zombie.png","Zombie",
    				ZOMBIE_HEALTH,ZOMBIE_DMG,ZOMBIE_CD));
    		this.objectList.add((this.enemyList).get(count));
    		count+=1;
    	}
    	for (index = 0; index < BAN_POS.length; index++) {
    		this.enemyList.add(new AggresiveMonsters(BAN_POS[index][0], 
    				BAN_POS[index][1],"assets/units/bandit.png","Bandit",
    				BANDIT_HEALTH,BANDIT_DMG,BANDIT_CD));
    		this.objectList.add((this.enemyList).get(count));
    		count+=1;
    	}
    	for (index = 0; index < SKEL_POS.length; index++) {
    		this.enemyList.add(new AggresiveMonsters(SKEL_POS[index][0], 
    				SKEL_POS[index][1],"assets/units/skeleton.png","Skeleton",
    				SKELETON_HEALTH,SKELETON_DMG,SKELETON_CD));
    		this.objectList.add((this.enemyList).get(count));
    		count+=1;
    	}
    	this.enemyList.add(new AggresiveMonsters(DRAELIC_POS[0],
    			DRAELIC_POS[1],"assets/units/necromancer.png","Draelic",
    			DRAELIC_HEALTH,DRAELIC_DMG,DRAELIC_CD));
    	this.objectList.add((this.enemyList).get(count));
		count+=1;
		
    	camera = new Camera(player,SCREENWIDTH,SCREENHEIGHT);
    	
    }

    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     * @param attack to check if the button 'A' is pressed for attack
     * @param interact to check if the button 'T' is pressed for interacting with NPC
     * @param help to check if the button 'H' is pressed for viewing tips and objective
     */
    public void update(double dir_x, double dir_y, int delta,
    		boolean attack,boolean interact,boolean help)
    throws SlickException
    {
    	
    	
    	int index;
    	 /** updates the player and the camera that follows it. */
        player.update(itemList,enemyList,this,dir_x, dir_y, delta,attack);
        
        /**update each npc action based on input*/
        elvira.action(player,interact,delta);
        aldric.action(player,interact,delta);
        garth.action(player,interact,delta);
        
        /**update each enemy and purge the one with 0 hp from the game*/
        for (index = 0; index < this.enemyList.size(); index++) {
        	this.enemyList.get(index).update(player,this,delta);
        	if(this.enemyList.get(index).health==0){
        		/**trigger the boolean switch to spawn item 
        		 * "Elixir of Life" indicating near ending of the game*/
        		if(this.enemyList.get(index).name.equals("Draelic")){
        			this.ending=true;
        		}
    			this.enemyList.remove(index);	
    		}
        }
        for (index = 0; index < batList.size(); index++) {
    		this.batList.get(index).update(player,this,delta);
    		if(this.batList.get(index).health==0){
    			this.batList.remove(index);
    		}
    	}
        for (index = 0; index < objectList.size(); index++){
        	if(this.objectList.get(index).gethealth()==0)
        		this.objectList.remove(index);	
        }
        
        /**updating item and removing them from the 
         * map after being retrieved by player*/
        for (index = 0; index < itemList.size(); index++){
        	this.itemList.get(index).applyEffect(player,this);
        	if(this.itemList.get(index).added){
        		this.itemList.remove(index);
        	}
        }
        
        camera.update();
        
        
        /**trigger the 'help menu' when user is holding 'H' button*/
        if(help){
        	this.help=true;
        }
        else{
        	this.help=false;
        }
    }

    /** Render the entire screen, so it reflects the current game state.
     * Render the map and the player character.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
    	int index;
    	map.render(-(camera.getMinX()%TILE),-(camera.getMinY()%TILE),
    			(camera.getMinX())/TILE,(camera.getMinY())/TILE,
    			TileRenderedX,TileRenderedY);
    	g.translate(-camera.getMinX(), -camera.getMinY());
    	
    	/**render all item except "Elixir of Life" 
    	 * if the ending requirement is not met*/
    	for (index = 0; index < itemList.size(); index++){
    		if(this.itemList.get(index).name.equals("Elixir of Life")==false){
        		this.itemList.get(index).render();
    		}
    		else if(this.ending){
    			this.itemList.get(index).render();
    		}
        }
 
    	player.render(g);
    	
    	for (index = 0; index < batList.size(); index++) {
        	this.batList.get(index).render(g);
        }
    	for (index = 0; index < enemyList.size(); index++) {
        	this.enemyList.get(index).render(g);
        }
    	
    	aldric.render(g);
    	elvira.render(g);
    	garth.render(g);
    	
    	/**re translate the camera so the panel will stay on screen*/
    	g.translate(camera.getMinX(), camera.getMinY());
    	renderPanel(g);
    	
    	

    }
    
    /**check blocking with map environment
     * @param unit is the unit that is being tested
     * @param dir_x unit movement to the right and left
     * @param dir_y unit movement up and down*/
    public double[] isBlock(Unit unit,double dir_x, double dir_y){
    	double[] dir={dir_x,dir_y};
    	 /** TRY_X and TRY_Y are used to check if the next tile is blocked.*/
    	try_x=((int)(unit.getpos_x()+(dir_x*TILE_OFFSET))/TILE);
    	try_y=((int)(unit.getpos_y()+(dir_y*TILE_OFFSET))/TILE);
    	
    	 /**Making sure that the player does not move outside the 
    	  * maximum map tile.*/
    	if(try_x<MAX_TILE && try_y<MAX_TILE){
    		
    		NextTileID_x=map.getTileId(try_x,(int)(unit.getpos_y()/TILE),0);
    		NextTileID_y=map.getTileId((int)(unit.getpos_x()/TILE),try_y,0);
    		
    		 /** Restrict player movements not to cross blocked tiles
    		  * which is the tile property "1". */
    		if(map.getTileProperty(NextTileID_x,"block","0").equals("1")){
    			dir[0]=0;
    		}
    		if(map.getTileProperty(NextTileID_y,"block","0").equals("1")){
    			dir[1]=0;
    		}
    	}
    	else{
    		dir[0]=0;
    		dir[1]=0;
    	}
    	return dir;
    }
    
    /**check collision between unit by spawning square for each unit*/
    public Unit isCollide(Unit unit){
    	for (Unit npc : objectList){
    		if (unit.bounds().intersects(npc.bounds()) && unit!=npc){
    			return npc;
    		}
    	}
    	return null;
    }
    
    /**generate random integer number between max and min*/
    public static int randInt(int min, int max) {
	    Random num = new Random();
	    int randomNum = num.nextInt((max-min)+1)+min;
	    return randomNum;
	}
    
    /**render status panel and help menu*/
    public void renderPanel(Graphics g)
    {
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp

        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle
        int inv_x, inv_y;           // Coordinates to draw inventory item

        float health_percent;       // Player's health, as a percentage

        // Panel background image
        panel.draw(0, RPG.screenheight - RPG.panelheight);

        // Display the player's health
        text_x = 15;
        text_y = RPG.screenheight - RPG.panelheight + 25;
        g.setColor(LABEL);
        g.drawString("Health:", text_x, text_y);
        text = ""+player.health+"/"+player.max_health;                               

        bar_x = 90;
        bar_y = RPG.screenheight - RPG.panelheight + 20;
        bar_width = 90;
        bar_height = 30;
        health_percent = player.health;                       
        hp_bar_width = (int) (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width/player.max_health, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's damage and cooldown
        text_x = 200;
        g.setColor(LABEL);
        g.drawString("Damage:", text_x, text_y);
        text_x += 80;
        text = ""+ player.damage;                                   
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
        text_x += 40;
        g.setColor(LABEL);
        g.drawString("Rate:", text_x, text_y);
        text_x += 55;
        text = ""+player.cooldown;                                 
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's inventory
        g.setColor(LABEL);
        g.drawString("Items:", 420, text_y);
        bar_x = 490;
        bar_y = RPG.screenheight - RPG.panelheight + 10;
        bar_width = 288;
        bar_height = bar_height + 20;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);

        inv_x = 490;
        inv_y = RPG.screenheight - RPG.panelheight
            + ((RPG.panelheight - 72) / 2);
        for (int index = 0; index < player.inventory.size(); index++)             
        {
            player.inventory.get(index).render(inv_x,inv_y);
            inv_x += 72;
        }
        
        /**help menu that display the objective and tips for new player*/
        if(this.help){
        	g.setColor(BAR_BG);
        	g.fillRect(500, 10, 300, 60);
        	g.setColor(LABEL);
        	g.drawString("- PRESS 'A' TO ATTACK ENEMIES", 500, 10);
        	g.drawString("- PRESS 'T' TO INTERACT WITH NPC", 500, 30);
        	g.drawString("- TALK TO 'ELVIRA' FOR HEALING", 500, 50);
        	if(garth.getItemProgress()<3 && this.ending==false){
        		g.setColor(BAR_BG);
        		g.fillRect(10, 10, 400, 40);
        		g.setColor(BAR);
        		g.fillRect(10, 10, 157, 20);
        		g.setColor(LABEL);
        		g.drawString("QUEST OBJECTIVE: COLLECT ALL EQUIPMENT", 10, 10);
        		g.drawString("WITH INSTRUCTIONS FROM 'GARTH'", 10, 30);
        	}
        	else if(aldric.getObjective()==false){
        		g.setColor(BAR_BG);
        		g.fillRect(10, 10, 325, 60);
        		g.setColor(BAR);
        		g.fillRect(10, 10, 157, 20);
        		g.setColor(LABEL);
        		g.drawString("QUEST OBJECTIVE: KILL 'DRAELIC' AND", 10, 10);
        		g.drawString("DELIVER 'ELIXIR OF LIFE' TO PRINCE", 10, 30);
        		g.drawString("ALDRIC!!", 10, 50);
        	}
        	else{
        		g.setColor(BAR);
        		g.fillRect(10, 10, 345, 40);
        		g.setColor(LABEL);
        		g.drawString("QUEST CLEAR YOU CAN FREE ROAM NOW", 10, 10);
        		g.drawString("THANKS FOR PLAYING !!", 10, 30);
        	}
        }
        else{
        	
        	g.setColor(LABEL);
        	g.drawString("HOLD 'H' FOR INSTRUCTIONS", (RPG.screenwidth/2)-115, 10);
        }
    }
    
    /**check if the game main objective has been cleared*/
    public boolean getEnding(){
    	return this.ending;
    }
}

