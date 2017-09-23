package com.example.steven.loveym;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by STEVEN on 2017/3/30.
 */

public class Tools {

    public static final String APPNAME = "loveym";
    public static final String FILE_PATH_ROOT = "/data/data/com.example.steven.loveym/files";


    public static double formatDouble1(double d) {
        return (double)Math.round(d*100)/100;
    }

    public static List<Activity> activities = new ArrayList<>();


    public static  void addActivity(Activity activity){
        activities.add(activity);
    }


    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }


    public static void finishAll(){
        for (Activity activity : activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }



    protected static PersonOneLine getCustomer(int customerIDinqueue, Context context){//CustomerTable

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =db.query(DatabaseContract.CutormerTable.TABLE_NAME,
                new String[]{DatabaseContract.CutormerTable.COLUMN_NAME_NAME,
                        DatabaseContract.CutormerTable.COLUMN_NAME_TEL,DatabaseContract.CutormerTable.COLUMN_NAME_ADDRESS},
                DatabaseContract.CutormerTable._ID + "= ?",new String[]{String.valueOf(customerIDinqueue)},null,null,null);
        cursor.moveToNext();
        String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_NAME));
        int telephone = cursor.getInt(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_TEL));
        String address = cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_ADDRESS));
        cursor.close();

        PersonOneLine person = new PersonOneLine();
        person.setCustomer_ID(customerIDinqueue);
        person.setName(name);
        person.setTelephone(telephone);
        person.setAddress(address);



        return person;
    }



    protected static String showTime(long time){

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
        Date transactionDate = new Date(time);
        return dateFormat.format(transactionDate);

    }







    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        filePath = FILE_PATH_ROOT + filePath;
        makeDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
