package firstIntensive;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import java.awt.*;


public class Main {
    public static void main(String[] args) throws AWTException, InterruptedException {
        String ACCESS_TOKEN = "rMRL_LX9C-kAAAAAAAAAAV9_RmzdjH9UR_spP3m7agoKBi5zaFR8DxpLNraHREKn";
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);


        for (;;) {
            MyThread sendToDbx = new MyThread(client);
            Thread.sleep(5000);
        }
    }
}
