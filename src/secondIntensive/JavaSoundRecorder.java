package secondIntensive;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JavaSoundRecorder {

    DbxClientV2 client;
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    TargetDataLine line;
    DataLine.Info info;
    AudioFormat format;

    public JavaSoundRecorder(DbxClientV2 clientV2) {
        this.client = clientV2;
        format = getAudioFormat();
        info = new DataLine.Info(TargetDataLine.class, format);
    }

    public void record(int milliseconds) {
        //TODO: file name like 20201215_202436.wav
        //  using class SimpleDateFormat
        SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd_HHmmss");
        Date now = new Date();
        String pathToFile = formatter.format(now) + ".wav";
        File file = new File("C:\\Users\\hp\\Desktop\\" + pathToFile);
        start(file);
        finish(file, milliseconds);
    }

    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        return format;
    }

    public void start(File file) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    if (!AudioSystem.isLineSupported(info)) {
                        System.out.println("Line not supported");
                        System.exit(0);
                    }
                    line = (TargetDataLine) AudioSystem.getLine(info);
                    line.open(format);
                    line.start();
                    AudioInputStream ais = new AudioInputStream(line);
                    AudioSystem.write(ais, fileType, file);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void finish(File file, int millis) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                line.stop();
                line.close();
                record(millis);

                try {
                    InputStream in = new FileInputStream("C:\\Users\\hp\\Desktop\\" + file.getName());
                    client.files().uploadBuilder("/" + file.getName()).uploadAndFinish(in);
                    in.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //TODO: upload file to Dropbox
                //TODO: delete file
                file.delete();
            }
        };
        thread.start();
    }
}