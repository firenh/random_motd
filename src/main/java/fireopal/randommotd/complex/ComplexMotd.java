package fireopal.randommotd.complex;

import java.util.Arrays;
import java.util.List;

import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class ComplexMotd {
    public List<StringWithFormatting> strings;

    public ComplexMotd(List<StringWithFormatting> strings) {
        this.strings = strings;
    }

    public ComplexMotd(StringWithFormatting... strings) {
        this.strings = Arrays.asList(strings);
    }

    public ComplexMotd() {}

    public Text asText() {
        MutableText text = new LiteralText("");

        for (StringWithFormatting s : strings) {
            text.append(s.asText());
        }

        return text;
    }

    @Override
    public String toString() {
        String str = "{ ";

        for (StringWithFormatting s : this.strings) {
            str += s.toString() + " } {";
        }

        str = str.substring(0, str.length() - 1);
        return str;
    }
}
