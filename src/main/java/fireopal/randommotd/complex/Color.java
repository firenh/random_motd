package fireopal.randommotd.complex;

public class Color {
    int red, green, blue;

    // public static void main(String[] args) {
    //     Color[] colors = {
    //         new Color(0xFFFFFF),
    //         new Color(0xFF0000),
    //         new Color(0x00FF55),
    //         new Color(0x123456)
    //     };

    //     for (Color color : colors) {
    //         System.out.println(color.toString());
    //         System.out.println(color.getRed() + " " + color.getGreen() + " " + color.getBlue());
    //     }
    // }

    public Color(int code) {
        if (code < 0) {
            this.red = 0;
            this.green = 0;
            this.blue = 0;
            return;
        } else if (code > 0xFFFFFF) {
            this.red = 255;
            this.green = 255;
            this.blue = 255;
            return;
        }

        this.red = getDigitAt(256, -3, code);
        this.green = getDigitAt(256, -2, code);
        this.blue = getDigitAt(256, -1, code);
    }

    public Color(int red, int green, int blue) {
        this.setRed(red);
        this.setGreen(green);
        this.setBlue(blue);
    }

    private int getDigitAt(int base, int digit, int x) {
        return (int) Math.floor(base * (Math.pow(base, digit) * x - Math.floor(Math.pow(base, digit) * x)));
    }

    public String toString() {
        return Integer.toHexString(this.getRGB());
    }

    public int getRGB() {
        return this.red * 0x010000 + this.green * 0x000100 + this.blue * 0x000001;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Color)) {
            return false;
        }

        if (this.getRGB() == ((Color) obj).getRGB()) {
            return true;
        }

        return false;
    }

    public void setRed(int red) {
        if (red < 0) red = 0;
        else if (red > 0xFF) red = 0xFF;
        this.red = red;
    }

    public void setGreen(int green) {
        if (green < 0) green = 0;
        else if (green > 0xFF) green = 0xFF;
        this.green = green;
    }

    public void setBlue(int blue) {
        if (blue < 0) blue = 0;
        else if (blue > 0xFF) blue = 0xFF;
        this.blue = blue;
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
