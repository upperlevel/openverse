package xyz.upperlevel.openverse.console;

import java.util.*;
import java.util.logging.Level;

/**
 * Usage:
 * <li>String msg = Ansi.Red.and(Ansi.BgYellow).format("Hello %s", name)</li>
 * <li>String msg = Ansi.Blink.colorize("BOOM!")</li>
 *
 * Or, if you are adverse to that, you can use the constants directly:
 * <li>String msg = new Ansi(Ansi.ITALIC, Ansi.GREEN).format("Green money")</li>
 * Or, even:
 * <li>String msg = Ansi.BLUE + "scientific"</li>
 *
 * NOTE: Nothing stops you from combining multiple FG colors or BG colors,
 *       but only the last one will display.
 *
 * @author dain (& SnowyCoder for some modifications)
 *
 */
public final class ChatColor {

    // Color code strings from:
    // http://www.topmudsites.com/forums/mud-coding/413-java-ansi.html
    public static final String RESET                = "\u001B[0m";

    public static final String BOLD                 = "\u001B[1m";
    public static final String LOW_INTENSITY        = "\u001B[2m";

    public static final String ITALIC				= "\u001B[3m";
    public static final String UNDERLINE			= "\u001B[4m";
    public static final String BLINK				= "\u001B[5m";
    public static final String RAPID_BLINK			= "\u001B[6m";
    public static final String REVERSE_VIDEO		= "\u001B[7m";
    public static final String INVISIBLE_TEXT		= "\u001B[8m";
    public static final String CROSSED_OUT		    = "\u001B[9m";

    public static final String ITALIC_OFF		    = "\u001B[23m";
    public static final String UNDERLINE_OFF	    = "\u001B[24m";
    public static final String BLINK_OFF		    = "\u001B[25m";
    public static final String REVERSE_VIDEO_OFF    = "\u001B[27m";
    public static final String INVISIBLE_TEXT_OFF   = "\u001B[28m";

    public static final String BLACK				= "\u001B[30m";
    public static final String RED					= "\u001B[31m";
    public static final String GREEN				= "\u001B[32m";
    public static final String YELLOW				= "\u001B[33m";
    public static final String BLUE				    = "\u001B[34m";
    public static final String MAGENTA				= "\u001B[35m";
    public static final String CYAN			    	= "\u001B[36m";
    public static final String WHITE				= "\u001B[37m";
    public static final String COLOR_CUSTOM		    = "\u001B[38;2;";
    public static final String COLOR_OFF		    = "\u001B[39m";


    public static final String BACKGROUND_BLACK	= "\u001B[40m";
    public static final String BACKGROUND_RED		= "\u001B[41m";
    public static final String BACKGROUND_GREEN	= "\u001B[42m";
    public static final String BACKGROUND_YELLOW	= "\u001B[43m";
    public static final String BACKGROUND_BLUE		= "\u001B[44m";
    public static final String BACKGROUND_MAGENTA	= "\u001B[45m";
    public static final String BACKGROUND_CYAN		= "\u001B[46m";
    public static final String BACKGROUND_WHITE	= "\u001B[47m";
    public static final String BACKGROUND_CUSTOM	= "\u001B[48;2;";
    public static final String BACKGROUND_OFF = "\u001B[49m";

    public static final String FRAMED      		= "\u001B[51m";
    public static final String ENCIRCLED   		= "\u001B[52m";
    public static final String OVERLINED   		= "\u001B[53m";

    public static final String FRAMED_ENCIRCLED_OFF= "\u001B[54m";
    public static final String OVERLINED_OFF		= "\u001B[55m";


    public static final ChatColor reset = new ChatColor(RESET);

    public static final ChatColor highIntensity = new ChatColor(BOLD);
    public static final ChatColor bold = highIntensity;
    public static final ChatColor lowIntensity = new ChatColor(LOW_INTENSITY);
    public static final ChatColor normal = lowIntensity;

    public static final ChatColor italic = new ChatColor(ITALIC);
    public static final ChatColor underline = new ChatColor(UNDERLINE);
    public static final ChatColor blink = new ChatColor(BLINK);
    public static final ChatColor rapidBlink = new ChatColor(RAPID_BLINK);
    public static final ChatColor reverseVideo = new ChatColor(REVERSE_VIDEO);
    public static final ChatColor invisibleText = new ChatColor(INVISIBLE_TEXT);
    public static final ChatColor crossedOut = new ChatColor(CROSSED_OUT);

    public static final ChatColor italicOff = new ChatColor(ITALIC_OFF);
    public static final ChatColor underlineOff = new ChatColor(UNDERLINE_OFF);
    public static final ChatColor blinkOff = new ChatColor(BLINK_OFF);
    public static final ChatColor reverseVideoOff = new ChatColor(REVERSE_VIDEO_OFF);
    public static final ChatColor invisibleTextOff = new ChatColor(INVISIBLE_TEXT_OFF);

    public static final ChatColor black = new ChatColor(BLACK);
    public static final ChatColor red = new ChatColor(RED);
    public static final ChatColor green = new ChatColor(GREEN);
    public static final ChatColor yellow = new ChatColor(YELLOW);
    public static final ChatColor blue = new ChatColor(BLUE);
    public static final ChatColor magenta = new ChatColor(MAGENTA);
    public static final ChatColor cyan = new ChatColor(CYAN);
    public static final ChatColor white = new ChatColor(WHITE);
    public static final ChatColor colorOff = new ChatColor(COLOR_OFF);

    public static final ChatColor bgBlack = new ChatColor(BACKGROUND_BLACK);
    public static final ChatColor bgRed = new ChatColor(BACKGROUND_RED);
    public static final ChatColor bgGreen = new ChatColor(BACKGROUND_GREEN);
    public static final ChatColor bgYellow = new ChatColor(BACKGROUND_YELLOW);
    public static final ChatColor bgBlue = new ChatColor(BACKGROUND_BLUE);
    public static final ChatColor bgMagenta = new ChatColor(BACKGROUND_MAGENTA);
    public static final ChatColor bgCyan = new ChatColor(BACKGROUND_CYAN);
    public static final ChatColor bgWhite = new ChatColor(BACKGROUND_WHITE);
    public static final ChatColor bgOff = new ChatColor(BACKGROUND_OFF);

    public static final ChatColor framed = new ChatColor(FRAMED);
    public static final ChatColor encircled = new ChatColor(ENCIRCLED);
    public static final ChatColor overlined = new ChatColor(OVERLINED);

    public static final ChatColor framedEnircledOff = new ChatColor(FRAMED_ENCIRCLED_OFF);
    public static final ChatColor overlinedOff = new ChatColor(OVERLINED_OFF);


    public static final Map<Level, ChatColor> LEVEL_MAP = new HashMap<>();


    static {
        LEVEL_MAP.put(Level.SEVERE, and(bold, red));
        LEVEL_MAP.put(Level.WARNING, and(bold, yellow));
        LEVEL_MAP.put(Level.INFO, green);
        LEVEL_MAP.put(Level.CONFIG, and(bold, cyan));
        LEVEL_MAP.put(Level.FINE, cyan);
        LEVEL_MAP.put(Level.FINER, cyan);
        LEVEL_MAP.put(Level.FINEST, cyan);
    }

    final private String codes_str;
    public ChatColor(String code) {
        this.codes_str = code;
    }


    public ChatColor(String... codes) {
        StringBuilder res = new StringBuilder();
        for (String code : codes)
            res.append(code);
        codes_str = res.toString();
    }

    public ChatColor(ChatColor... codes) {
        StringBuilder res = new StringBuilder();
        for (ChatColor code : codes)
            res.append(code.codes_str);
        codes_str = res.toString();
    }

    public ChatColor and(ChatColor other) {
        return new ChatColor(codes_str, other.codes_str);
    }

    public static ChatColor and(ChatColor... colors) {
        return new ChatColor(colors);
    }

    public String colorize(String original) {
        return codes_str + original + RESET;
    }

    public String format(String template, Object... args) {
        return colorize(String.format(template, args));
    }

    public String toString() {
        return codes_str;
    }

    public static String ofLevel(Level level) {
        ChatColor color = LEVEL_MAP.get(level);
        return color != null ? color.codes_str : "";
    }

    public static ChatColor ofRgb(int r, int g, int b) {
        return new ChatColor(COLOR_CUSTOM + r + ";" + g + ";" + b + "m");
    }
    public static ChatColor ofBackground(int r, int g, int b) {
        return new ChatColor(BACKGROUND_CUSTOM + r + ";" + g + ";" + b + "m");
    }
}