/*
package com.example.voicewav;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {
    private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date date=new Date(System.currentTimeMillis());

    private String basePath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/ADO Voice";

    private String inFileName = basePath+"/Ajay.txt";
    private String outFileName = basePath+"/dooropen_"+format.format(date)+".wav";


    private Button btnConvertWav;
    private TextView tvResult;

    private File pcmFile;
    private File wavFile;
    private OutputStream os;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       */
/* super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);*//*


        createFile();

     */
/*   btnConvertWav=findViewById(R.id.btnConvertWav);
        tvResult=findViewById(R.id.tvResult);*//*


        btnConvertWav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertWaveFile();
            }
        });

    }

    private void createFile() {
        File baseFile=new File(basePath);

        if (!baseFile.exists()) {
            baseFile.mkdirs();
        }
        pcmFile = new File(basePath + "/yinfu.pcm");
        wavFile = new File(basePath + "/dooropen.txt");
        if (pcmFile.exists()) {
            pcmFile.delete();
        }
        if (wavFile.exists()) {
            wavFile.delete();
        }
        try {
            boolean i = pcmFile.createNewFile();
            boolean j = wavFile.createNewFile();
            os = new BufferedOutputStream(new FileOutputStream(inFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void convertWaveFile() {
        FileInputStream in=null;
        FileOutputStream out=null;
        long totalAudioLen=0;
        long totalDataLen=totalAudioLen+36;
        long longSampleRate=16000;
        int channels=1;
        long byteRate=16*longSampleRate*channels/8;
        byte[] data=new byte[512];

        try {
            in = new FileInputStream(inFileName); // sample raw data of Wav file
            String outFileName_new=basePath+"wave_voice"+format.format(date)+".wav";
            out=new FileOutputStream(outFileName_new); // outFileName_new means new wav gen file
            totalAudioLen=in.getChannel().size();
            totalDataLen=totalAudioLen+36;
            writeWaveFileHeader(out,totalAudioLen,totalDataLen,longSampleRate,channels,byteRate);

            while (in.read(data)!=-1){
                out.write(data);
            }
            in.close();
            out.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void writeWaveFileHeader(FileOutputStream out, long totalAudioLen, long totalDataLen, long longSampleRate, int channels, long byteRate) throws IOException {
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

}
*/
