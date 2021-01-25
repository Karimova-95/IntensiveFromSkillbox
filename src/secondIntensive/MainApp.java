package secondIntensive;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.awt.*;

public class MainApp {
    public static void main(String[] args) throws AWTException, InterruptedException {
        String ACCESS_TOKEN = "rMRL_LX9C-kAAAAAAAAAAV9_RmzdjH9UR_spP3m7agoKBi5zaFR8DxpLNraHREKn";
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        JavaSoundRecorder recorder = new JavaSoundRecorder(client);
        recorder.record(30000);
    }
}
