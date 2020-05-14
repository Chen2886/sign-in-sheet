package Main;

import java.net.URL;

public class FinalConstants {

    // entry table headers
    public static final String[] entryHeader = new String[]{"Check in Date", "Title", "Name", "Email", "Suggestions"};

    // entry property headers
    public static final String[] entryPropertyHeader = new String[]{"checkInDate", "title", "name", "email", "suggestions"};

    public static URL adminInputWhite;
    public static URL powerInputWhite;
    public static URL backWhite;
    public static URL createWhite;
    public static URL exportWhite;
    public static URL adminInputBlack;
    public static URL powerInputBlack;
    public static URL backBlack;
    public static URL createBlack;
    public static URL exportBlack;

    public static void init() {
        adminInputWhite = FinalConstants.class.getResource("/images/adminIcon.png");
        powerInputWhite = FinalConstants.class.getResource("/images/powerIcon.png");
        backWhite = FinalConstants.class.getResource("/images/backIcon.png");
        createWhite = FinalConstants.class.getResource("/images/createIcon.png");
        exportWhite = FinalConstants.class.getResource("/images/exportIcon.png");
        adminInputBlack = FinalConstants.class.getResource("/images/adminIconBlack.png");
        powerInputBlack = FinalConstants.class.getResource("/images/powerIconBlack.png");
        backBlack = FinalConstants.class.getResource("/images/backIconBlack.png");
        createBlack = FinalConstants.class.getResource("/images/createIconBlack.png");
        exportBlack = FinalConstants.class.getResource("/images/exportIconBlack.png");
    }

}
