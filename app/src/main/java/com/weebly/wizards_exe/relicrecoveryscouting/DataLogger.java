package com.weebly.wizards_exe.relicrecoveryscouting;

/**
 * Created by Olavi Kamppari on 9/9/2015.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class DataLogger {
    private String name;
    private int row;
    private Row currentRow;
    private int column;
    private Sheet currentSheet;
    private int lastColumnNumber;
    Workbook wb;
    static String TAG = "ExelLog";

    public DataLogger (String fileName) {
        this.row = 0;
        this.column = 0;
        this.name = fileName;
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
        }

    }
    public void createNewWorkbook(){
        //New Workbook
        wb = new HSSFWorkbook();
        currentSheet = wb.createSheet("Sheet1");
        currentRow = currentSheet.createRow(0);
    }
    public boolean saveDataLogger(Context context) {
        File file = new File(context.getExternalFilesDir(null), name);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            return true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
            return false;
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
            return false;
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    public void addField(String obj){
        Cell c = currentRow.createCell(column + lastColumnNumber);
        c.setCellValue(obj);
        column++;

    }
    public void addField(Double obj){
        Cell c = currentRow.createCell(column + lastColumnNumber);
        c.setCellValue(obj);
        column++;
    }
    public void addField(boolean obj){
        Cell c = currentRow.createCell(column + lastColumnNumber);
        c.setCellValue(obj);
        column++;
    }
    public void addField(Calendar obj){
        Cell c = currentRow.createCell(column + lastColumnNumber);
        c.setCellValue(obj);
        column++;
    }
    public void addField(Date obj){
        Cell c = currentRow.createCell(column + lastColumnNumber);
        c.setCellValue(obj);
        column++;
    }
    public void addField(RichTextString obj){
        Cell c = currentRow.createCell(column + lastColumnNumber);
        c.setCellValue(obj);
        column++;
    }
    public void addField(int obj){
        Cell c = currentRow.createCell(column + lastColumnNumber);
        c.setCellValue(obj);
        column++;
    }
    public void newLine() {
        try{
            row++;
            column = 0;
            currentRow = currentSheet.getRow(row);
        }catch(Exception e){
            addNewLine();
        }

    }
    private void addNewLine(){
        row++;
        column = 0;
        currentRow = currentSheet.createRow(row);
    }
    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
    public void resetAndNextRow(Context context){
        readExcelFile(context);

        currentSheet = wb.getSheetAt(0);
        lastColumnNumber = 0;
        currentRow = currentSheet.getRow(0);
        column = 0;
        int columnFinal = currentRow.getLastCellNum();
        if(columnFinal == - 1){
            setupSpreadsheet();
            currentRow = currentSheet.getRow(0);
        }
        columnFinal = currentRow.getLastCellNum();
        lastColumnNumber = columnFinal;
        currentRow = currentSheet.getRow(0);
        row = 0;

    }
    private void setupSpreadsheet(){
        this.addField("Team Number");
        this.addNewLine();
        this.addField("Match Number");
        this.addNewLine();
        this.addField("Jewel 1");
        this.addNewLine();
        this.addField("Jewel 2");
        this.addNewLine();
        this.addField("Auto Glyphs");
        this.addNewLine();
        this.addField("Auto Parked");
        this.addNewLine();
        this.addField("Auto Vuforia");
        this.addNewLine();
        this.addField("Teleop Glyphs");
        this.addNewLine();
        this.addField("Teleop Rows");
        this.addNewLine();
        this.addField("Teleop Columns");
        this.addNewLine();
        this.addField("Teleop Ciphers");
        this.addNewLine();
        this.addField("Relic 1");
        this.addNewLine();
        this.addField("Relic 1 Standing");
        this.addNewLine();
        this.addField("Relic 2");
        this.addNewLine();
        this.addField("Relic 2 Standing");
        this.addNewLine();
        this.addField("Balanced");
        this.addNewLine();
        this.addField("Score");
        this.addNewLine();
        this.addField("FTA Error");
        this.addNewLine();
        this.addField("Additional Comments");
        this.addNewLine();

    }
    private void readExcelFile(Context context) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            return;
        }
        try{
            // Creating Input Stream
            File file = new File(context.getExternalFilesDir(null), name);
            FileInputStream myInput = new FileInputStream(file);
            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
           wb = new HSSFWorkbook(myFileSystem);

        }catch (Exception e){
            createNewWorkbook();
        }


    }
}