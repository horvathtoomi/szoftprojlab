package main.java;

/**
 * Egy absztrakt osztály, amely névvel rendelkező objektumokat reprezentál.
 * Az osztály egy String name mezőt tartalmaz, valamint getter és setter metódusokat
 * a név eléréséhez és módosításához.
 */ 
public abstract class Nameable {

	private String name;

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

}
