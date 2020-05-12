package DataBaseUtil;

import Util.HandleError;

import java.sql.SQLException;

public class SerialNum {

    private static int[] serialNum;
    private static String[] tableNames = {"entries"};

    /**
     * called when program first start, make sure file exists.
     */
    public static void initSerialNum() {
        serialNum = new int[tableNames.length];
        try {
            for (int i = 0; i < tableNames.length; i++) {
                serialNum[i] = DatabaseUtil.GetNewestSerialNum(tableNames[i]);
            }
        } catch (SQLException e) {
            new HandleError(SerialNum.class.getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    e.getMessage(), e.getStackTrace(), false);
        }
    }

    /**
     * This function returns the newest serialNum
     * @return the current serialNum
     */
    public static int getSerialNum(DBOrder table) {
        serialNum[table.getValue()]++;
        return serialNum[table.getValue()];
    }
}
