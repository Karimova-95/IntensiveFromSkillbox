package firstIntensive;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread{
    DbxClientV2 client;
    public MyThread(DbxClientV2 client) throws AWTException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd_HHmmss");
            Date now = new Date();
            String imageName = "/" + formatter.format(now) + ".png";
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit
                    .getDefaultToolkit().getScreenSize()));
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            FileMetadata metadata = client.files().uploadBuilder(imageName).uploadAndFinish(is);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
