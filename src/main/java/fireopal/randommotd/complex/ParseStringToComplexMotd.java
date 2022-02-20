package fireopal.randommotd.complex;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.Formatting;

public class ParseStringToComplexMotd {
    public static ComplexMotd parse(String string) {
        List<StringWithFormatting> motd = new ArrayList<>();
        final int length = string.length();

        String currentContent = "";

        Color currentColor1 = new Color(Formatting.DARK_GRAY.getColorValue());
        Color currentColor2 = currentColor1;

        boolean bold = false;
        boolean italic = false;
        boolean underlined = false;
        boolean strikethrough = false;
        boolean obfusicated = false;

        for (int index = 0; index < length; index += 1) {
            final Character cha = string.charAt(index);
            final Character prevCha = index > 0 ? string.charAt(index - 1) : ' ';

            if ((cha.equals('<') && !prevCha.equals('\\')) || (index == string.length() - 1)) {
                if (currentColor1.equals(currentColor2)) {
                    motd.add(new StringWithFormatting.Uniform(currentContent, currentColor1, bold, italic, underlined, strikethrough, obfusicated));
                } else {
                    motd.add(new StringWithFormatting.Gradient(currentContent, currentColor1, currentColor2, bold, italic, underlined, strikethrough, obfusicated));
                }


                int tagEndIndex = length;
                for (int j = index; j < length; j += 1) {
                    if (string.charAt(j) == '>') {
                        tagEndIndex = j;
                        break;
                    }
                }

                String substring = string.substring(index + 1, tagEndIndex);

                System.out.println(substring);

                if (substring.length () == 0) {

                } else if (substring.charAt(0) == 'c') {
                    int c1index = substring.length();

                    for (int j = 0; j < c1index; j += 1) {
                        if (substring.charAt(j) == '#') {
                            c1index = j;
                            break;
                        }
                    }

                    if (substring.length() < 8) {
                        currentColor1 = new Color(Formatting.DARK_GRAY.getColorValue());
                        currentColor2 = currentColor1;
                    } else {
                        String colSubstring = substring.substring(2, 8);

                        try {
                            currentColor1 = new Color(Integer.parseInt(colSubstring, 16));
                            currentColor2 = currentColor1;
                        } catch (NumberFormatException e) {}

                        if (substring.length() >= 15 ) {
                            int c2index = substring.length();

                            for (int j = c1index + 1; j < c2index; j += 1) {
                                if (substring.charAt(j) == '#') {
                                    c2index = j;
                                    break;
                                }
                            }

                            String col2Substring = substring.substring(c2index + 1, c2index + 7);

                            System.out.println(colSubstring);
                            System.out.println(col2Substring);

                            try {
                                currentColor2 = new Color(Integer.parseInt(col2Substring, 16));
                            } catch (NumberFormatException e) {}
                        }
                    }
                } else if (substring.length() == 1) {
                    switch (substring) {
                        case "b" -> {
                            bold = bold ? false : true;
                        }

                        case "i" -> {
                            italic = italic ? false : true;
                        }

                        case "u" -> {
                            underlined = underlined ? false : true;
                        }

                        case "s" -> {
                            strikethrough = strikethrough ? false : true;
                        }

                        case "o" -> {
                            obfusicated = obfusicated ? false : true;
                        }
                    }
                }

                index = tagEndIndex;
                currentContent = "";
            } else {
                currentContent += cha;
            }
        }

        return new ComplexMotd(motd);
    }
}
