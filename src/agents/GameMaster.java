package agents;

import java.util.ArrayList;
import java.util.List;

import behaviours.gamemaster.EndBehaviour;
import behaviours.gamemaster.PlayBehaviour;
import behaviours.gamemaster.StartBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import util.Coord;

public class GameMaster extends Agent {
	private static final long serialVersionUID = 3776482759573939188L;
	
	private static final String BEHAVIOUR_START = "start";
	private static final String BEHAVIOUR_PLAY = "play";
	private static final String BEHAVIOUR_END = "end";
	private static final int LIMIT_GRID = 9;

	public static AID IDENTIFIER = new AID("game_master", AID.ISLOCALNAME);
	
	private Coord treasure;
	private Coord playerPos;
	private List<Coord> block;
	private boolean isBlock = false;
	
	public void setup(){
		FSMBehaviour behaviour = new FSMBehaviour(this);
		
		//States
		behaviour.registerFirstState(new StartBehaviour(this), BEHAVIOUR_START);
		behaviour.registerState(new PlayBehaviour(this), BEHAVIOUR_PLAY);
		behaviour.registerLastState(new EndBehaviour(this), BEHAVIOUR_END);
		
		//Transitions
		behaviour.registerDefaultTransition(BEHAVIOUR_START, BEHAVIOUR_PLAY);
		behaviour.registerTransition(BEHAVIOUR_PLAY, BEHAVIOUR_PLAY, 1);
		behaviour.registerTransition(BEHAVIOUR_PLAY, BEHAVIOUR_END, 0);
		
		addBehaviour(behaviour);
	}
	
	public void createBlocks() {
		block = new ArrayList<Coord>();
		
		block.add(new Coord(1,1));
		block.add(new Coord(1,2));
		block.add(new Coord(1,4));
		block.add(new Coord(1,5));
		block.add(new Coord(1,6));
		block.add(new Coord(1,8));
		block.add(new Coord(2,6));
		block.add(new Coord(3,1));
		block.add(new Coord(3,2));
		block.add(new Coord(3,3));
		block.add(new Coord(3,4));
		block.add(new Coord(3,6));
		block.add(new Coord(3,7));
		block.add(new Coord(3,8));
		block.add(new Coord(3,9));
		block.add(new Coord(4,3));
		block.add(new Coord(4,9));
		block.add(new Coord(5,1));
		block.add(new Coord(5,3));
		block.add(new Coord(5,5));
		block.add(new Coord(5,6));
		block.add(new Coord(5,7));
		block.add(new Coord(5,8));
		block.add(new Coord(5,9));
		block.add(new Coord(6,1));
		block.add(new Coord(6,3));
		block.add(new Coord(7,1));
		block.add(new Coord(7,5));
		block.add(new Coord(7,7));
		block.add(new Coord(7,9));
		block.add(new Coord(8,1));
		block.add(new Coord(8,2));
		block.add(new Coord(8,3));
		block.add(new Coord(8,5));
		block.add(new Coord(8,7));
		block.add(new Coord(8,9));
		block.add(new Coord(9,3));
		block.add(new Coord(9,6));
		block.add(new Coord(9,7));

	}
	
	public void initGame() {
		setTreasure(new Coord(9, 9));
		playerPos = new Coord(0, 0);
	}
	
	public void suggestDirection(String hint, String moviment) {
		
		String[] possibleDirections={"up", "down", "left", "right"};
		
		switch(hint){
		
		case "wall":
			System.out.println("você encontrou uma parede, siga para outra direção!");
			break;
		//if player got warmer, they can remove all
		//candidates they got farther away from
		case "warmer":
			System.out.println("continue em frente!");
			break;
		//if player got colder, they can remove all
		//candidates they got closer to
		case "colder":
			System.out.println("você está se distanciando do tesouro, mude seu caminho para outra direção!");
			break;
		default: // case "same"
			System.out.println("digite um comando válido!");
			break;
		} 
	}
	
	public String evaluateProximity(String playerMoveDirection) {
		Coord newPlayerPos = playerPos.clone();
		newPlayerPos.move(playerMoveDirection);
		createBlocks();
		
		for(int b=0; b<block.size()-1; b++) {
			if((newPlayerPos.x==block.get(b).x && newPlayerPos.y==block.get(b).y) || newPlayerPos.x<0 || newPlayerPos.y<0 || newPlayerPos.x>LIMIT_GRID || newPlayerPos.y>LIMIT_GRID) {
				newPlayerPos = playerPos;
				isBlock = true;
			}
		}
		
		String hint = "colder";
		
		if(isBlock) {
			hint = "wall";
			isBlock = false;
		}
		else if(newPlayerPos.distanceTo(treasure)==0)
			hint = "win";
		else if (newPlayerPos.distanceTo(getTreasure())<playerPos.distanceTo(getTreasure()))
			hint = "warmer";
		else if(newPlayerPos.distanceTo(getTreasure())>playerPos.distanceTo(getTreasure()))
			hint = "colder";
		else
			hint = "same";
		
		playerPos = newPlayerPos;
		return hint;
	}

	public Coord getTreasure() {
		return treasure;
	}

	public void setTreasure(Coord treasure) {
		this.treasure = treasure;
	}	
}
