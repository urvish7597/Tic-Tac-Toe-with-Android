package com.example.tictactoe;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
//        public static void writeFileOnInternalStorage(Context context, String sFileName, String sBody) {
//            try {
//                File root = new File(Environment.getExternalStorageDirectory(), "Notes");
//                if (!root.exists()) {
//                    root.mkdirs();
//                }
//                File gpxfile = new File(root, sFileName);
//                FileWriter writer = new FileWriter(gpxfile);
//                writer.append(sBody);
//                writer.flush();
//                writer.close();
//                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    public static List<String> ReadFileFromInternalStorage(Context mcoContext, String fileName) {
//        List<String> data = new ArrayList<String>();
//        try {
//                FileInputStream fis = mcoContext.openFileInput(fileName);
//                InputStreamReader isr = new InputStreamReader(fis);
//                BufferedReader bufferedReader = new BufferedReader(isr);
//                String touple;
//                while ((touple = bufferedReader.readLine()) != null) {
//                    data.add(touple);
//                }
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        return  data;
//    }

    public static void save(Context myContect, String fileName, String data) {
        //String text = mEditText.getText().toString();
        FileOutputStream fos = null;

        try {
            fos = myContect.openFileOutput(fileName, myContect.MODE_APPEND);
            fos.write(data.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean deleteLog(Context context,String fileName)
    {
        File dir = context.getFilesDir();
        File file = new File(dir, fileName);
        return file.delete();
    }

    public static List<String> load(Context myContect, String fileName) {
        FileInputStream fis = null;
        List<String> data = new ArrayList<String>();
        try {
            fis = myContect.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                data.add(text.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  data;
    }
}
