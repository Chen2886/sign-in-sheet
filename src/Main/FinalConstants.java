package Main;

import java.io.File;

public class FinalConstants {

    // entry table headers
    public static final String[] entryHeader = new String[]{"Serial Number", "Title", "Name", "Email", "Suggestions",
            "Check in Date"};

    // entry property headers
    public static final String[] entryPropertyHeader = new String[]{"serialNum", "title", "name", "email", "suggestions",
            "checkInDate"};

    public static File adminInputWhite;
    public static File powerInputWhite;
    public static File backWhite;
    public static File adminInputBlack;
    public static File powerInputBlack;
    public static File backBlack;

    public static void init() {
        adminInputWhite = new File("resources/images/adminIcon.png");
        powerInputWhite = new File("resources/images/powerIcon.png");
        backWhite = new File("resources/images/backIcon.png");
        adminInputBlack = new File("resources/images/adminIconBlack.png");
        powerInputBlack = new File("resources/images/powerIconBlack.png");
        backBlack = new File("resources/images/backIconBlack.png");

    }

}
