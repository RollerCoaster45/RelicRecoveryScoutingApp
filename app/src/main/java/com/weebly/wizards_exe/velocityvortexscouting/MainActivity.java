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
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.weebly.wizards_exe.velocityvortexscouting.DataLogger.isExternalStorageAvailable;
import static com.weebly.wizards_exe.velocityvortexscouting.DataLogger.isExternalStorageReadOnly;

public class MainActivity extends Activity
{
    private int autoParticles, autoParticlesMissed, teleopParticles, teleopParticlesMissed, teleopBeacons, teleopBeaconsMissed;
    private TextView autoParticle, autoParticleMissed, teleopParticle, teleopParticleMissed, teleopBeacon, teleopBeaconMissed, autoLabel, autoMissedLabel, teleopLabel, teleopMissedLabel, beaconLabel, beaconMissedLabel, beacon1Label, beacon2Label, capBallLabel;
    private EditText teamNumber, matchNumber;
    private Button addAuto, subAuto, addAutoMissed, subAutoMissed, addTeleop, subTeleop, addTeleopMissed, subTeleopMissed, addBeacon, subBeacon, addBeaconMissed, subBeaconMissed, submit, reset;
    Spinner beacon1, beacon2, capBall;
    CheckBox FTAError;


    DataLogger data;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoParticle = (TextView) findViewById(R.id.AutoParticles);
        autoParticleMissed = (TextView) findViewById(R.id.AutoParticlesMissed);
        teleopParticle = (TextView) findViewById(R.id.TeleopParticles);
        teleopParticleMissed = (TextView) findViewById(R.id.TeleopParticlesMissed);
        teleopBeacon = (TextView) findViewById(R.id.TeleopBeacons);
        teleopBeaconMissed = (TextView) findViewById(R.id.TeleopMissedBeacons);
        autoLabel = (TextView) findViewById(R.id.AutoParticlesLabel);
        autoMissedLabel = (TextView) findViewById(R.id.AutoParticlesMissedLabel);
        teleopLabel = (TextView) findViewById(R.id.TeleopParticlesLabel);
        teleopMissedLabel = (TextView) findViewById(R.id.TeleopParticlesMissedLabel);
        beaconLabel = (TextView) findViewById(R.id.TeleopBeaconLabel);
        beaconMissedLabel = (TextView) findViewById(R.id.TeleopMissedBeaconLabel);
        beacon1Label = (TextView) findViewById(R.id.AutoBeacon1Label);
        beacon2Label = (TextView) findViewById(R.id.AutoBeacon2Label);
        capBallLabel = (TextView) findViewById(R.id.CapBallLabel);

        addAuto = (Button) findViewById(R.id.AddAutoParticles);
        subAuto = (Button) findViewById(R.id.SubAutoParticles);
        addAutoMissed = (Button) findViewById(R.id.AddAutoParticlesMissed);
        subAutoMissed = (Button) findViewById(R.id.SubAutoParticlesMissed);
        addTeleop = (Button) findViewById(R.id.AddTeleopParticles);
        subTeleop = (Button) findViewById(R.id.SubTeleopParticles);
        addTeleopMissed = (Button) findViewById(R.id.AddTeleopParticlesMissed);
        subTeleopMissed = (Button) findViewById(R.id.SubTeleopParticlesMissed);
        addBeacon = (Button) findViewById(R.id.AddTeleopBeacons);
        subBeacon = (Button) findViewById(R.id.SubTeleopBeacons);
        addBeaconMissed = (Button) findViewById(R.id.AddTeleopBeaconsMisssed);
        subBeaconMissed = (Button) findViewById(R.id.SubTeleopBeaconsMissed);
        submit = (Button) findViewById(R.id.Submit);
        reset = (Button) findViewById(R.id.Reset);

        beacon1 = (Spinner) findViewById(R.id.AutoBeacon1Spinner);
        beacon2 = (Spinner) findViewById(R.id.AutoBeacon2Spinner);
        capBall = (Spinner) findViewById(R.id.CapBallSpinner);
        ArrayAdapter<CharSequence> beaconAdapter = ArrayAdapter.createFromResource(this,
                R.array.beaconSpinnerItems, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> capBallAdapter = ArrayAdapter.createFromResource(this,
                R.array.capBallSpinnerItems, android.R.layout.simple_spinner_item);
        beaconAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capBallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        teamNumber = (EditText)findViewById(R.id.teamNumber);
        matchNumber = (EditText)findViewById(R.id.matchNumber);
// Apply the adapter to the spinner
        beacon1.setAdapter(beaconAdapter);
        beacon2.setAdapter(beaconAdapter);
        capBall.setAdapter(capBallAdapter);
        FTAError = (CheckBox) findViewById(R.id.FTAFailure);
        int width = (getResources().getDisplayMetrics().widthPixels)-32;
        ConstraintLayout.LayoutParams layout;
        View[] quarterWidgets = {addAuto, subAuto, addAutoMissed, subAutoMissed, addTeleop, subTeleop, addTeleopMissed, subTeleopMissed, addBeacon, subBeacon, addBeaconMissed, subBeaconMissed, submit, reset,
                autoParticle, autoParticleMissed, teleopParticle, teleopParticleMissed, teleopBeacon, teleopBeaconMissed, autoLabel, autoMissedLabel, teleopLabel, teleopMissedLabel, beaconLabel, beaconMissedLabel};
        View[] halfWidgets = {beacon1Label, beacon2Label, beacon1, beacon2};
        View[] fullWidgets = {capBall, capBallLabel, FTAError, teamNumber, matchNumber};
        for(View widget:quarterWidgets){
            layout = (ConstraintLayout.LayoutParams) widget.getLayoutParams();
            layout.width = width/4;
            widget.setLayoutParams(layout);
        }
        for(View widget:halfWidgets){
            layout = (ConstraintLayout.LayoutParams) widget.getLayoutParams();
            layout.width = width/2;
            widget.setLayoutParams(layout);

        }
        for(View widget:fullWidgets){
            layout = (ConstraintLayout.LayoutParams) widget.getLayoutParams();
            layout.width = width;
            widget.setLayoutParams(layout);

        }
        reset();
    }
    public void submitData(View view){
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
}




}