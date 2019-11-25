package agents;

import util.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import behaviours.player.EndBehaviour;
import behaviours.player.PlayBehaviour;
import behaviours.player.InitBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

public class Player extends Agent{
	private static final long serialVersionUID = 1709672583743068992L;
	public static AID  IDENTIFIER = new AID("player", AID.ISLOCALNAME);
	
	private static final String BEHAVIOUR_INIT = "initialisation";
	private static final String BEHAVIOUR_PLAY = "jeu";
	private static final String BEHAVIOUR_END = "end";
	private static final int LIMIT_GRID = 10;
	
	private Coord oldPos;
	private Coord pos;
	private List<Coord> candidates;
	private List<Coord> block;


	@Override
	public void setup(){
		FSMBehaviour behaviour = new FSMBehaviour(this);
		
		//States
		behaviour.registerFirstState(new InitBehaviour(this), BEHAVIOUR_INIT);
		behaviour.registerState(new PlayBehaviour(this), BEHAVIOUR_PLAY);
		behaviour.registerLastState(new EndBehaviour(this), BEHAVIOUR_END);
		
		//Transitions
		behaviour.registerDefaultTransition(BEHAVIOUR_INIT, BEHAVIOUR_PLAY);
		behaviour.registerTransition(BEHAVIOUR_PLAY, BEHAVIOUR_PLAY, 1);
		behaviour.registerTransition(BEHAVIOUR_PLAY, BEHAVIOUR_END, 0);
		
		addBehaviour(behaviour);
	}
	
	public void initGame() {
		setPos(new Coord(0, 0));
		candidates = new ArrayList<Coord>();
		
		for (int i = 0; i < LIMIT_GRID; i++) {
			for (int j = 0; j < LIMIT_GRID; j++) {
				candidates.add(new Coord(i, j));
			}
		}
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

	public String determineNextMove(String hint) {
		if(hint.equals("win")) return "win";		
		return getPos().determineDirectionTo();
	}

	public void move(String direction) {
		oldPos = getPos().clone();
		getPos().move(direction);
		createBlocks();
		for(int b=0; b<block.size()-1; b++) {
			if((pos.x==block.get(b).x && pos.y==block.get(b).y) || pos.x<0 || pos.y<0 || pos.x>LIMIT_GRID || pos.y>LIMIT_GRID) {
				pos = oldPos;
			}
		}
	}

	public Coord getPos() {
		return pos;
	}

	public void setPos(Coord pos) {
		this.pos = pos;
	}
}
