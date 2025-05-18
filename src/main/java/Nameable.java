package main.java;

import java.io.Serial;
import java.io.Serializable;

/**
 * Egy absztrakt osztály, amely névvel rendelkező objektumokat reprezentál.
 * Az osztály egy String name mezőt tartalmaz, valamint getter és setter metódusokat
 * a név eléréséhez és módosításához.
 */ 
public abstract class Nameable implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private String name;

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

}
