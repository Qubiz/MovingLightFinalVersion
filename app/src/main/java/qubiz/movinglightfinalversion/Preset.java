package qubiz.movinglightfinalversion;

/**
 * Created by Mathijs on 18/06/2015.
 */
public class Preset {

    private int id;
    private String name;
    private int color;
    private boolean enabled;

    public Preset(String name, int color, boolean enabled) {
        this.name = name;
        this.color = color;
        this.enabled = enabled;
    }

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

    @Override
    public String toString() {
        return ("Preset [name= " + name + ", color= " + color + ", enabled=" + enabled + "]");
    }
}
