package Main;

import java.io.File;

public class FinalConstants {

    // entry table headers
    public static final String[] entryHeader = new String[]{"Check in Date", "Title", "Name", "Email", "Suggestions"};

    // entry property headers
    public static final String[] entryPropertyHeader = new String[]{"checkInDate", "title", "name", "email", "suggestions"};

    public static File adminInputWhite;
    public static File powerInputWhite;
    public static File backWhite;
    public static File createWhite;
    public static File exportWhite;
    public static File adminInputBlack;
    public static File powerInputBlack;
    public static File backBlack;
    public static File createBlack;
    public static File exportBlack;

    public static void init() {
        adminInputWhite = new File("resources/images/adminIcon.png");
        powerInputWhite = new File("resources/images/powerIcon.png");
        backWhite = new File("resources/images/backIcon.png");
        createWhite = new File("resources/images/createIcon.png");
        exportWhite = new File("resources/images/exportIcon.png");
        adminInputBlack = new File("resources/images/adminIconBlack.png");
        powerInputBlack = new File("resources/images/powerIconBlack.png");
        backBlack = new File("resources/images/backIconBlack.png");
        createBlack = new File("resources/images/createIconBlack.png");
        exportBlack = new File("resources/images/exportIconBlack.png");
    }

}
