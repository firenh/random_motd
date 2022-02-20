package fireopal.randommotd.complex;

public class Color {
    int red, green, blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRGB() {
        return this.red * 0x010000 + this.green * 0x000100 + this.blue * 0x000001;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
