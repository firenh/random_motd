package fireopal.randommotd.util;

import net.minecraft.text.Text;

public class WeightedMotd<T> {
    private final T motd;
    private final int weight;
    
    public T getMotd() {
        return motd;
    }

    public int getWeight() {
        return weight;
    }

    public WeightedMotd(T motd, int weight) {
        this.motd = motd;
        this.weight = weight;
    }
}
