package fr.technicalgames.entity;

public abstract class Entity {

	private String name = "";
	private int id = 0;
	
	private float px,py,pz,rx,ry,rz;
	
	public Entity(String name,int id){
		this.name = name;
		px = 0;
		py = 0;
		pz = 0;
		rx = 0;
		ry = 0;
		rz = 0;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getPx() {
		return px;
	}

	public void setPx(float px) {
		this.px = px;
	}

	public float getPy() {
		return py;
	}

	public void setPy(float py) {
		this.py = py;
	}

	public float getPz() {
		return pz;
	}

	public void setPz(float pz) {
		this.pz = pz;
	}

	public float getRx() {
		return rx;
	}

	public void setRx(float rx) {
		this.rx = rx;
	}

	public float getRy() {
		return ry;
	}

	public void setRy(float ry) {
		this.ry = ry;
	}

	public float getRz() {
		return rz;
	}

	public void setRz(float rz) {
		this.rz = rz;
	}
	
	
	
}
