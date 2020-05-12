package Main;

import java.io.File;

public class FinalConstants {

    // entry table headers
    public static final String[] entryHeader = new String[]{"Serial Number", "Title", "Name", "Email", "Suggestions",
            "Check in Date"};

    // entry property headers
    public static final String[] entryPropertyHeader = new String[]{"serialNum", "title", "name", "email", "suggestions",
            "checkInDate"};

    static File adminInputWhite;
    static File powerInputWhite;
    static File adminInputBlack;
    static File powerInputBlack;

    public static void init() {
        adminInputWhite = new File("resources/images/adminIcon.png");
        powerInputWhite = new File("resources/images/powerIcon.png");
        adminInputBlack = new File("resources/images/adminIconBlack.png");
        powerInputBlack = new File("resources/images/powerIconBlack.png");
    }

}
