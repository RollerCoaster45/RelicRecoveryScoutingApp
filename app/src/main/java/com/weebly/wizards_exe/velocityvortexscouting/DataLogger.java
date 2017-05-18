package com.weebly.wizards_exe.velocityvortexscouting;

/**
 * Created by Olavi Kamppari on 9/9/2015.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
        row++;
        column = 0;
        currentRow = currentSheet.getRow(row);
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
        this.addField("Team Name");
        this.addNewLine();
        this.addField("Auto Particles");
        this.addNewLine();
        this.addField("Teleop Particles");
        this.addNewLine();
        this.addField("Auto Particles");
        this.addNewLine();
        this.addField("Auto Particles");
        this.addNewLine();
        this.addField("Auto Particles");
        this.addNewLine();
        this.addField("Auto Particles");
        this.addNewLine();
        this.addField("Auto Particles");
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