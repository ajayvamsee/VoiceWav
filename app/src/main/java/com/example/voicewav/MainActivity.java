package com.example.voicewav;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    public static final int REQ_CODE_WRITE_EXTERNAL_STORAGE = 404;
    public static final int REQ_CODE_REC_AUDIO_AND_WRITE_EXTERNAL = 101;
    public static final int REQ_CODE_RECORD_AUDIO = 303;
    public static final int REQ_CODE_READ_EXTERNAL_STORAGE_IMPORT = 405;

    private Button btnConvertWav;
    private TextView tvResult;

    private File pcmFile;
    private File wavFile;

    private OutputStream os;
    private String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AdoVoice";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());
    private String inFileName = basePath + "/bed_binary";
    private String birdFile = basePath + "/bird.bin";
    private String catFile = basePath + "/cat.bin";
    private String dogFile = basePath + "/dog.bin";
    private String fiveFile = basePath + "/five.bin";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //createFile();

        btnConvertWav = findViewById(R.id.btnConvertWav);
        tvResult = findViewById(R.id.tvResult);

        btnConvertWav.setOnClickListener(v -> {
            if (checkStoragePermission2()) {
                convertWaveFile();
            } else {
                Toast.makeText(getApplicationContext(), "No Storage permission", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void createFile() {
        File baseFile = new File(basePath);

        if (!baseFile.exists())
            baseFile.mkdirs();
        pcmFile = new File(basePath + "/Ajay.txt");
        wavFile = new File(basePath + "/door.wav");
        if (pcmFile.exists()) {
            pcmFile.delete();
        }
        if (wavFile.exists()) {
            wavFile.delete();
        }
        try {
            boolean i = pcmFile.createNewFile();
            boolean j = wavFile.createNewFile();
            os = new BufferedOutputStream(new FileOutputStream(wavFile));
        } catch (IOException e) {
            Log.i(TAG, e.toString());
        }
    }

    private void convertWaveFile() {
        FileInputStream in = null;
        FileOutputStream out = null;
        long totalAudioLen = 0;
        long totalDataLen = totalAudioLen + 36;
        long longSampleRate = 16000;
        int channels = 1;
        long byteRate = 16 * longSampleRate * channels / 8;
        byte[] data = new byte[512];
        try {
            in = new FileInputStream(catFile);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String outFileName_new = basePath + "/door_open " + this.format.format(date) + ".wav";
            out = new FileOutputStream(outFileName_new);
            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;
            WriteWaveFileHeader(out, totalAudioLen, totalDataLen, longSampleRate, channels, byteRate);
            while (in.read(data) != -1) {
                out.write(data);
            }
            in.close();
            out.close();

            tvResult.setText(outFileName_new);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void WriteWaveFileHeader(FileOutputStream out, long totalAudioLen, long totalDataLen, long longSampleRate, int channels, long byteRate) throws IOException {
        byte[] header = new byte[44];
        header[0] = 'R'; // RIFF
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);//数据大小
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';//WAVE
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        //FMT Chunk
        header[12] = 'f'; // 'fmt '
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';//过渡字节
        //数据大小
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        //编码方式 10H为PCM编码格式
        header[20] = 1; // format = 1
        header[21] = 0;
        //通道数
        header[22] = (byte) channels;
        header[23] = 0;
        //采样率，每个通道的播放速度
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        //音频数据传送速率,采样率*通道数*采样深度/8
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        // 确定系统一次要处理多少个这样字节的数据，确定缓冲区，通道数*采样位数
        header[32] = (byte) (1 * 16 / 8);
        header[33] = 0;
        //每个样本的数据位数
        header[34] = 16;
        header[35] = 0;
        //Data chunk
        header[36] = 'd';//data
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        out.write(header, 0, 44);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CODE_REC_AUDIO_AND_WRITE_EXTERNAL && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "receive 3 permission");
        } else if (requestCode == REQ_CODE_WRITE_EXTERNAL_STORAGE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "receive 2 permission");
        } else if (requestCode == REQ_CODE_WRITE_EXTERNAL_STORAGE && grantResults.length > 0
                && (grantResults[0] == PackageManager.PERMISSION_DENIED
                || grantResults[1] == PackageManager.PERMISSION_DENIED)) {
            Log.i(TAG, "deny 2 permission");
        }

    }

    public boolean checkStoragePermission2() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQ_CODE_READ_EXTERNAL_STORAGE_IMPORT);
                return false;
            }
        }
        return true;
    }

}