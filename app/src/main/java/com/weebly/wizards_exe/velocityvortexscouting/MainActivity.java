package com.weebly.wizards_exe.velocityvortexscouting;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.weebly.wizards_exe.velocityvortexscouting.DataLogger.isExternalStorageAvailable;
import static com.weebly.wizards_exe.velocityvortexscouting.DataLogger.isExternalStorageReadOnly;

public class MainActivity extends Activity
{
    private TextView autoGlyphs, teleopGlyphs, teleopCiphers, score, autonomousLabel, jewel1Label, jewel2Label, autoGlyphsLabel, teleopLabel, teleopGlyphsLabel, cipherLabel, endGameLabel, relic1Label, relic2Label, scoreLabel, teleopRowLabel, teleopRows, teleopColumnLabel, teleopColumns;
    private Spinner jewel1Spinner, jewel2Spinner, relic1Spinner, relic2Spinner;
    private CheckBox FTAError, autoParked, autoVuforia, relic1Standing, relic2Standing, balanced;
    private Button addAutoGlyph, subAutoGlyph, addTeleopGlyph, subTeleopGlyph, addTeleopRow, subTeleopRow, addTeleopColumn, subTeleopColumn, submit, reset;
    private SeekBar cipherSeekBar;
    private EditText teamNumber, matchNumber, additionalInfo;
    private int teamNum, matchNum, autoGlyphsNumber, teleopGlyphNumber, scoreNumber, ciphersDoneNumber, teleopRowNumber, teleopColumnNumber;

    DataLogger data;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoGlyphs = (TextView) findViewById(R.id.AutoGlyphsNumber);
        teleopGlyphs = (TextView) findViewById(R.id.TeleopGlyphNumber);
        teleopCiphers = (TextView) findViewById(R.id.CipherNumber);
        score = (TextView) findViewById(R.id.Score);
        autonomousLabel = (TextView) findViewById(R.id.AutonomousLabel);
        jewel1Label = (TextView) findViewById(R.id.Jewel1Label);
        jewel2Label = (TextView) findViewById(R.id.Jewel2Label);
        autoGlyphsLabel = (TextView) findViewById(R.id.AutoGlyphsLabel);
        teleopLabel = (TextView) findViewById(R.id.TeleopLabel);
        teleopGlyphsLabel = (TextView) findViewById(R.id.TeleopGlyphLabel);
        cipherLabel = (TextView) findViewById(R.id.CipherLabel);
        endGameLabel = (TextView) findViewById(R.id.EndGameLabel);
        relic1Label = (TextView) findViewById(R.id.Relic1Label);
        relic2Label = (TextView) findViewById(R.id.Relic2Label);
        scoreLabel = (TextView) findViewById(R.id.ScoreLabel);
        teleopRowLabel = (TextView) findViewById(R.id.TeleopRowLabel);
        teleopRows = (TextView) findViewById(R.id.TeleopRowNumber);
        teleopColumnLabel = (TextView) findViewById(R.id.TeleopColumnLabel);
        teleopColumns = (TextView) findViewById(R.id.TeleopColumnNumber);

        addAutoGlyph = (Button) findViewById(R.id.AddAutoGlyphs);
        subAutoGlyph = (Button) findViewById(R.id.SubAutoGlyphs);
        addTeleopGlyph = (Button) findViewById(R.id.AddTeleopGlyph);
        subTeleopGlyph = (Button) findViewById(R.id.SubTeleopGlyph);
        addTeleopRow = (Button) findViewById(R.id.AddTeleopRows);
        subTeleopRow = (Button) findViewById(R.id.SubTeleopRow);
        addTeleopColumn = (Button) findViewById(R.id.AddTeleopColumns);
        subTeleopColumn = (Button) findViewById(R.id.SubTeleopColumn);
        submit = (Button) findViewById(R.id.Submit);
        reset = (Button) findViewById(R.id.Reset);

        jewel1Spinner = (Spinner) findViewById(R.id.Jewel1Spinner);
        jewel2Spinner = (Spinner) findViewById(R.id.Jewel2Spinner);
        relic1Spinner = (Spinner) findViewById(R.id.Relic1Spinner);
        relic2Spinner = (Spinner) findViewById(R.id.Relic2Spinner);
        ArrayAdapter<CharSequence> relicAdapter = ArrayAdapter.createFromResource(this,
                R.array.relicSpinnerItems, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> jewelAdapter = ArrayAdapter.createFromResource(this,
                R.array.jewelSpinnerItems, android.R.layout.simple_spinner_item);
        relicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jewelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jewel1Spinner.setAdapter(jewelAdapter);
        jewel2Spinner.setAdapter(jewelAdapter);
        relic1Spinner.setAdapter(relicAdapter);
        relic2Spinner.setAdapter(relicAdapter);
        jewel1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                update();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        jewel2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                update();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        relic1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                update();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        relic2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                update();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        teamNumber = (EditText)findViewById(R.id.teamNumber);
        matchNumber = (EditText)findViewById(R.id.matchNumber);
        additionalInfo = (EditText) findViewById(R.id.AdditionalComments);

        // Apply the adapter to the spinner
        FTAError = (CheckBox) findViewById(R.id.FTAError);
        autoParked = (CheckBox) findViewById(R.id.AutoParkCheckBox);
        relic1Standing = (CheckBox) findViewById(R.id.Relic1CheckBox);
        relic2Standing = (CheckBox) findViewById(R.id.Relic2CheckBox);
        autoVuforia = (CheckBox) findViewById(R.id.AutoVuforiaGlyphCheckbox);
        balanced = (CheckBox) findViewById(R.id.BalancedPark);

        cipherSeekBar = (SeekBar) findViewById(R.id.CipherSeekBar);

        int width = (getResources().getDisplayMetrics().widthPixels)-32;

        ViewGroup.LayoutParams layout;
        View[] quarterWidgets = {autoGlyphsLabel, addAutoGlyph, subAutoGlyph, autoGlyphs, teleopGlyphsLabel, teleopGlyphs, addTeleopGlyph, subTeleopGlyph, cipherSeekBar, teleopCiphers, addTeleopColumn, addTeleopRow, subTeleopColumn, subTeleopRow, teleopRowLabel, teleopRows, teleopColumnLabel, teleopColumns};
        View[] halfWidgets = {scoreLabel, score, jewel1Label, jewel1Spinner, jewel2Label, jewel2Spinner, cipherLabel, relic1Label, relic1Spinner, relic1Standing, relic2Label, relic2Spinner, relic2Standing, submit, reset};
        View[] fullWidgets = {autonomousLabel, FTAError, teamNumber, matchNumber, teleopLabel, autoParked, autoVuforia, endGameLabel, additionalInfo, balanced};

        for(View widget:quarterWidgets){
            layout = widget.getLayoutParams();
            layout.width = width/4;
            widget.setLayoutParams(layout);
        }for(View widget:halfWidgets){
            layout = widget.getLayoutParams();
            layout.width = width/2;
            widget.setLayoutParams(layout);

        }
        for(View widget:fullWidgets){
            layout = widget.getLayoutParams();
            layout.width = width;
            widget.setLayoutParams(layout);

        }
        reset();
    }
    public void addAutoGlyph(View view){
        if(teleopGlyphNumber+autoGlyphsNumber<24){
            autoGlyphsNumber++;
            update();
        }
    }
    public void subAutoGlyph(View view){
        if(autoGlyphsNumber>0){
            autoGlyphsNumber--;
            update();
        }
    }
    public void addTeleopGlyphs(View view){
        if(teleopGlyphNumber+autoGlyphsNumber<24){
            teleopGlyphNumber++;
            update();
        }

    }
    public void subTeleopGlyphs(View view){
        if(teleopGlyphNumber>0){
            teleopGlyphNumber--;
            update();
        }
    }
    public void autoParkClick(View view){
        update();
    }
    public void autoVuforiaClick(View view){
        update();
    }
    public void standing1Update(View view){
        update();
    }
    public void standing2Update(View view){
        update();
    }
    public void update(){
        scoreNumber = 0;
        scoreNumber+=autoGlyphsNumber*15;
        scoreNumber += teleopGlyphNumber*2;
        if(autoParked.isChecked()){
            scoreNumber += 10;
        }
        if(balanced.isChecked()){
            scoreNumber += 20;
        }
        if(relic1Standing.isChecked()&&(relic1Spinner.getSelectedItem().toString().equals("Not Attempted")||relic1Spinner.getSelectedItem().toString().equals("Attempted Fail"))){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Can't stand without being in zone");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
            relic1Standing.setChecked(false);
        }else if(relic1Standing.isChecked()){
            scoreNumber+=15;
        }
        if(relic2Standing.isChecked()&&(relic2Spinner.getSelectedItem().toString().equals("Not Attempted")||relic2Spinner.getSelectedItem().toString().equals("Attempted Fail"))){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Can't stand without being in zone");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
            relic2Standing.setChecked(false);
        }else if(relic2Standing.isChecked()){
            scoreNumber+=15;
        }
        if(autoVuforia.isChecked()&&autoGlyphsNumber==0){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Vuforia not possible without glyph");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
            autoVuforia.setChecked(false);
        }else if(autoVuforia.isChecked()){
            scoreNumber += 30;
        }
        if(jewel1Spinner.getSelectedItem().toString().equals("Right Color")){
            scoreNumber+=30;
        }else if(jewel1Spinner.getSelectedItem().toString().equals("Wrong Color")){
            scoreNumber-=30;
        }
        if(jewel2Spinner.getSelectedItem().toString().equals("Right Color")){
            scoreNumber+=30;
        }else if(jewel2Spinner.getSelectedItem().toString().equals("Wrong Color")){
            scoreNumber-=30;
        }
        if(relic1Spinner.getSelectedItem().toString().equals("Zone 1")){
            scoreNumber+=10;
        }else if(relic1Spinner.getSelectedItem().toString().equals("Zone 2")){
            scoreNumber+=20;
        }else if(relic1Spinner.getSelectedItem().toString().equals("Zone 3")){
            scoreNumber+=40;
        }
        if(relic2Spinner.getSelectedItem().toString().equals("Zone 1")){
            scoreNumber+=10;
        }else if(relic2Spinner.getSelectedItem().toString().equals("Zone 2")){
            scoreNumber+=20;
        }else if(relic2Spinner.getSelectedItem().toString().equals("Zone 3")){
            scoreNumber+=40;
        }
        autoGlyphs.setText(autoGlyphsNumber+"");
        teleopGlyphs.setText(teleopGlyphNumber+"");
        teleopCiphers.setText(ciphersDoneNumber+"");
        score.setText(scoreNumber + "");
        teleopRows.setText(teleopRowNumber+"");
        teleopColumns.setText(teleopColumnNumber+"");

    }
    public void balanceUpdate(View view){
        update();
    }
    public void reset(){
        autoGlyphsNumber = 0;
        teleopGlyphNumber = 0;
        ciphersDoneNumber = 0;
        scoreNumber = 0;
        teleopColumnNumber = 0;
        teleopRowNumber = 0;
        jewel1Spinner.setSelection(0);
        jewel2Spinner.setSelection(0);
        relic1Spinner.setSelection(0);
        relic2Spinner.setSelection(0);
        autoParked.setChecked(false);
        autoVuforia.setChecked(false);
        relic1Standing.setChecked(false);
        relic2Standing.setChecked(false);
        teamNumber.setText("");
        matchNumber.setText("");
        additionalInfo.setText("");
        update();
    }/*

    public void submitData(View view){
        if(!teamNumber.getText().toString().trim().equals("")&&!matchNumber.getText().toString().trim().equals("")){
            data = new DataLogger("ScoutingData.xls");
            data.resetAndNextRow(this);

            data.addField(teamNumber.getText().toString());

            data.newLine();
            data.addField(matchNumber.getText().toString());

            data.newLine();
            data.addField(beacon1.getSelectedItem().toString());
            data.newLine();
            data.addField(beacon2.getSelectedItem().toString());
            data.newLine();
            data.addField(autoParticles);
            data.newLine();
            data.addField(autoParticlesMissed);
            data.newLine();
            data.addField(teleopParticles);
             data.newLine();
            data.addField(teleopParticlesMissed);
            data.newLine();
            data.addField(teleopBeacons);
            data.newLine();
            data.addField(teleopBeaconsMissed);
            data.newLine();
            data.addField(capBall.getSelectedItem().toString());
            data.newLine();
            data.addField(FTAError.isChecked());
            data.saveDataLogger(this);
            reset();

        }else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("No Team Number or Match Number");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

    }
    public void reset(View view){
        reset();
    }
    public void reset(){
        autoParticles = 0;
        autoParticlesMissed = 0;
        teleopParticles = 0;
        teleopParticlesMissed = 0;
        teleopBeacons = 0;
        teleopBeaconsMissed = 0;
        teamNumber.setText("");
        matchNumber.setText("");
        FTAError.setChecked(false);
        beacon1.setSelection(0);
        beacon2.setSelection(0);
        capBall.setSelection(0);
        update();
    }
    public void update(){
        autoParticle.setText(autoParticles+"");

        autoParticleMissed.setText(autoParticlesMissed+"");
        teleopParticle.setText(teleopParticles+"");
        teleopParticleMissed.setText(teleopParticlesMissed+"");
        teleopBeacon.setText(teleopBeacons+"");
        teleopBeaconMissed.setText(teleopBeaconsMissed+"");
    }
    public void addAutoParticle(View view){
        autoParticles++;
        update();
    }
    public void addAutoParticleMissed(View view){
        autoParticlesMissed++;
        update();
    }
    public void addTeleopParticle(View view){
        teleopParticles++;
        update();
    }
    public void addTeleopParticleMissed(View view){
        teleopParticlesMissed++;
        update();
    }
    public void addTeleopBeacon(View view){
        teleopBeacons++;
        update();
    }
    public void addTeleopBeaconMissed(View view){
        teleopBeaconsMissed++;
        update();
    }
    public void subAutoParticle(View view){
        if(autoParticles>0){
            autoParticles--;
        }
        update();
    }
    public void subAutoParticleMissed(View view){
        if(autoParticlesMissed>0){
            autoParticlesMissed--;
        }
        update();
    }
    public void subTeleopParticle(View view){
        if(teleopParticles>0){
            teleopParticles--;
        }
        update();
    }
    public void subTeleopParticleMissed(View view){
        if(teleopParticlesMissed>0){
            teleopParticlesMissed--;
        }
        update();
    }
    public void subTeleopBeacon(View view){
        if(teleopBeacons>0){
            teleopBeacons--;
        }
        update();
    }
    public void subTeleopBeaconMissed(View view){
    if(teleopBeaconsMissed>0){
        teleopBeaconsMissed--;
    }
    update();
}*/




}