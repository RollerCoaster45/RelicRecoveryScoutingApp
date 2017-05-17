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
    Workbook wb;
    static String TAG = "ExelLog";

    public DataLogger (String fileName) {
        this.row = 0;
        this.column = 0;
        this.name = fileName;
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
        }
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
        Cell c = currentRow.createCell(column);
        c.setCellValue(obj);
        column++;
    }
    public void addField(Double obj){
        Cell c = currentRow.createCell(column);
        c.setCellValue(obj);
        column++;
    }
    public void addField(boolean obj){
        Cell c = currentRow.createCell(column);
        c.setCellValue(obj);
        column++;
    }
    public void addField(Calendar obj){
        Cell c = currentRow.createCell(column);
        c.setCellValue(obj);
        column++;
    }
    public void addField(Date obj){
        Cell c = currentRow.createCell(column);
        c.setCellValue(obj);
        column++;
    }
    public void addField(RichTextString obj){
        Cell c = currentRow.createCell(column);
        c.setCellValue(obj);
        column++;
    }
    public void addField(int obj){
        Cell c = currentRow.createCell(column);
        c.setCellValue(obj);
        column++;
    }
    public void newLine() {
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
    public static String readExcelFile(Context context, String filename) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            return "FAKE";
        }
        String returnString = "";
        try{
            // Creating Input Stream
            File file = new File(context.getExternalFilesDir(null), filename);
            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();


            while(rowIter.hasNext()){
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                while(cellIter.hasNext()){
                    HSSFCell myCell = (HSSFCell) cellIter.next();

                    returnString+=("Cell Value: " +  myCell.toString());

                }
            }
        }catch (Exception e){e.printStackTrace();
            returnString = e.toString();}

        return returnString;
    }
}