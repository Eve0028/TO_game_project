package pk.rpgame.model.living;

import pk.rpgame.Visitor;
import pk.rpgame.model.items.Armor;
import pk.rpgame.model.items.Item;
import pk.rpgame.model.living.state.HealthStatus;

import java.util.List;

public class Hero extends LivingEntity {
    private double defense;
    private double armor;
    private double stamina;

    public Hero(String name, int level, double strength, double health, double maxHealth, double defense, List<Item> items, HealthStatus liveState, double armor, double stamina) {
        super(name, level, strength, health, maxHealth, items, liveState);
        this.defense = defense;
        this.armor = armor;
        this.stamina = stamina;
    }

    @Override
    public double dealDamage() {
        return this.getStrength();
    }

    @Override
    public void receiveDamage(double damage) {
        this.setHealth((this.getHealth() + this.getArmor()) - damage);
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getArmor() {

        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public double getStamina() {
        return stamina;
    }

    public void setStamina(double stamina) {
        this.stamina = stamina;
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public String toString() {
        return super.toString()
                + "defense: " + this.getDefense() + ", "
                + "armor: " + this.getArmor() + ", "
                + "stamina: " + this.getStamina() + "]";
    }
}
