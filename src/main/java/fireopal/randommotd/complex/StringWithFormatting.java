package fireopal.randommotd.complex;

import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

public abstract class StringWithFormatting {
    private String string;
    private Color color;
    private boolean bold, italic, underlined, strikethrough, obfusicated;

    public StringWithFormatting(String string, Color color, boolean bold, boolean italic, boolean underlined, 
            boolean strikethrough, boolean obfusicated) {
        this.string = string;
        this.color = color;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.strikethrough = strikethrough;
        this.obfusicated = obfusicated;
    }

    public StringWithFormatting() {}

    public abstract MutableText asText();

    public static class Uniform extends StringWithFormatting {
        public Uniform(String string, Color color, boolean bold, boolean italic, boolean underlined, 
                boolean strikethrough, boolean obfusicated) {
            super(string, color, bold, italic, underlined, strikethrough, obfusicated);
        }

        public Uniform() {}

        @Override
        public MutableText asText() {
            return new LiteralText(this.getString()).styled(style -> style
                .withColor(this.getColor().getRGB())
                .withBold(this.isBold())
                .withItalic(this.isItalic())
                .withUnderline(this.isUnderlined())
                .withStrikethrough(this.isStrikethrough())
                .withObfuscated(this.isObfusicated())
            );
        }
    }

    public static class Gradient extends StringWithFormatting {
        private Color color2;

        public Color getColor2() {
            return color2;
        }

        public void setColor2(Color color2) {
            this.color2 = color2;
        }

        public Gradient(String string, Color color, Color color2, boolean bold, boolean italic, boolean underlined, 
                boolean strikethrough, boolean obfusicated) {
            super(string, color, bold, italic, underlined, strikethrough, obfusicated);
            this.color2 = color2;
        }

        public Gradient() {}

        private Color getColorInGradient(double p) {
            final int red = (int) (this.getColor().getRed() * p + this.getColor2().getRed() * (1 - p));
            final int green = (int) (this.getColor().getGreen() * p + this.getColor2().getGreen() * (1 - p));
            final int blue = (int) (this.getColor().getBlue() * p + this.getColor2().getBlue() * (1 - p));

            return new Color(red, green, blue);
        }

        @Override
        public MutableText asText() {
            MutableText text = new LiteralText("");
            final int stringLength = this.getString().length();

            for (int i = 0; i < stringLength; i += 1) {
                final int i2 = i;
                text.append(new LiteralText("" + this.getString().charAt(i)).styled(style -> style
                    .withColor(this.getColorInGradient((double) i2 / (double) stringLength).getRGB())
                    .withBold(this.isBold())
                    .withItalic(this.isItalic())
                    .withUnderline(this.isUnderlined())
                    .withStrikethrough(this.isStrikethrough())
                    .withObfuscated(this.isObfusicated())
                ));
            }

            return text;
        }
    }

    public String getString() {
        return string;
    }

    public StringWithFormatting setString(String string) {
        this.string = string;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public StringWithFormatting setColor(Color color) {
        this.color = color;
        return this;
    }

    public boolean isBold() {
        return bold;
    }

    public StringWithFormatting setBold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public boolean isItalic() {
        return italic;
    }

    public StringWithFormatting setItalic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public StringWithFormatting setUnderlined(boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }

    public StringWithFormatting setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    public boolean isObfusicated() {
        return obfusicated;
    }

    public StringWithFormatting setObfusicated(boolean obfusicated) {
        this.obfusicated = obfusicated;
        return this;
    }
}
 