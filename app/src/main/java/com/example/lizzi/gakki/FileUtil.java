package com.example.lizzi.gakki;

import java.text.SimpleDateFormat;

/**
 * Created by LiZzi on 2018/6/26.
 */

public class FileUtil {
    /*public static final String ADDRESS= "10.0.2.2";

    public static final int PORT = 12345;

    public static final String SAVEFILE= "10086";

    public static final String QUERYCONTENT= "10010";

    public static final String DELETEJOURNAL = "11111";

    public static final String UPDATEQUERYJOURNAL = "12345";

    public static final String UPDATESAVEJOURNAL = "12306";
*/

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年-MM月-dd号");

    public static String getDate(){
            return sdf.format(System.currentTimeMillis());
        }

}
