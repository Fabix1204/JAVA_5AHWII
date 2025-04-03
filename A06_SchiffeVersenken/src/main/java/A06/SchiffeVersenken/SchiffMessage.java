package A06.SchiffeVersenken;

public class SchiffMessage {

	private ACTIONS action;
	private Position position;
	private int id;
	
	public SchiffMessage(ACTIONS a, Position p) {
		this.action = a;
		this.position = p;
	}
	
	public SchiffMessage(ACTIONS a, Position p, int id) {
		this.action = a;
		this.position = p;
		this.id = id;
	}

	public ACTIONS getAction() {
		return action;
	}
	
	public Position getPosition() {
		return position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}