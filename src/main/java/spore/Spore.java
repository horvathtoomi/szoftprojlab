package main.java.spore;

import main.java.Geometry;
import main.java.Nameable;
import main.java.tecton.Tecton;
import main.java.insect.Insect;
import main.java.mushroom.Mushroom;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * Az absztrakt Spore osztály egy általános spóra entitást reprezentál,
 * amely rendelkezik tápértékkel, elhelyezkedéssel, szülő gombával és "élet" státusszal.
 * Konkrét hatását az örökölt osztályok valósítják meg.
 */
public abstract class Spore extends Nameable implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected int nutrientValue;     // A spóra által biztosított tápanyagmennyiség
    protected Mushroom mushroom;     // A gomba, amelyből a spóra származik
    protected Tecton location;       // A spóra jelenlegi elhelyezkedése a játéktéren
    protected boolean dead;          // A spóra él-e (false) vagy már elpusztult (true)
    protected Geometry geometry;

    /**
     * Konstruktor a Spore példány létrehozására.
     * 
     * @param nutrientValue   A spóra által biztosított tápanyag értéke.
     * @param mushroom        A gomba, amelyből a spóra származik.
     * @param location        A Tecton, ahol a spóra található.
     * @param name            A spóra neve.
     */
    public Spore(int nutrientValue, Mushroom mushroom, Tecton location, String name) {
        this.nutrientValue = nutrientValue;
        this.mushroom = mushroom;
        this.location = location;
        setName(name);
        this.dead = false;
    }

    /**
     * Generál a spóra számára egy véletlen tápértéket.
     */
    public static int randomNutrientValue() {
        Random rand = new Random();
        int result = rand.nextInt(3);
        return ++result;
    }

    //Getterek, Setterek

    public Tecton getLocation() {
        return location;
    }

    public Mushroom getMushroom() {
        return mushroom;
    }

    public boolean getDead() {
        return dead;
    }
    
    public Geometry getGeometry() {
    	return geometry;
    }
    
    public void setGeometry(Geometry geometry) {
    	this.geometry = geometry;
    }

    /**
     * Megjelöli a spórát elpusztultként.
     */
    public void die() {
        dead = true;
    }

    /**
     * A spóra hatásának alkalmazása egy rovarra.
     * Ezt a konkrét spóra típusok valósítják meg.
     * 
     * @param insect Az érintett rovar
     */
    public abstract void applyEffect(Insect insect);
}
