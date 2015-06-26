package qubiz.movinglightfinalversion.presets;

/**
 * Author: Mathijs de Groot (S1609483)
 *
 * Info:
 * De class Preset bevat alle attributen en methodes voor het maken van een preset.
 */
public class Preset {

    /**
     * De ID van de preset.
     */
    private int id;

    /**
     * De naam van de preset.
     */
    private String name;

    /**
     * De kleur van de preset.
     */
    private int color;

    /**
     * De flag om te kijken of de preset geactiveerd is.
     */
    private boolean enabled;

    /**
     * Constructor voor het creëeren van een nieuwe preset.
     * @param name naam van de preset.
     * @param color kleur van de preset.
     * @param enabled aan of uit?
     */
    public Preset(String name, int color, boolean enabled) {
        this.name = name;
        this.color = color;
        this.enabled = enabled;
    }


    /* GETTERS EN SETTERS*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * toString methode voor het gemakelijk printen van een preset.
     * @return String met alle informatie over de preset.
     */
    @Override
    public String toString() {
        return ("Preset [name= " + name + ", color= " + color + ", enabled=" + enabled + "]");
    }
}
