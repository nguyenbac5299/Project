package com.example.projecti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.TuAdapter;
import com.example.model.Tu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class  MainActivity extends AppCompatActivity {

    public static String DATABASE_NAME="ProjectI";
    public static SQLiteDatabase database= null;
    public static String TABLENAME="projectI";
    String DB_PATH_SUFFIX="/databases/";

    GridView gridView;
    TuAdapter adapter;
    Button buttonExit;
    Tu tuSelection;
    Button buttonSearch;
    int image=0;
    ArrayList<Integer>()

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processCopy();
        addControls();


        addEvents();
    }

    private void addEvents() {
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tuSelection= adapter.getItem(position) ;
                System.out.println("BacNT"+ tuSelection.getLabel());
                if(tuSelection.getLabel()==null|| tuSelection.getLabel().equals(""))
                {
                displayDialog();}
                else {
                displayDialog1();
                }
            }
        });
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog2();
            }
        });
    }
    private void displayDialog2(){
        final Dialog dialog= new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog2);
        final EditText editTen= dialog.findViewById(R.id.edit_ten);

        final Button buttonSearch= dialog.findViewById(R.id.button_search);
        Button buttonCancel= dialog.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("");
                String ten= editTen.getText().toString();

                for(int i=0; i<24;i++){
                    Tu tu= adapter.getItem(i);

                    if(tu!=null&&tu.getLabel()!=null&&tu.getLabel().equalsIgnoreCase(ten))
                    {
                        int k=0;

                        arr.add
                    }

                }
                dialog.dismiss();
                String result = "";
                for(int i=0;i<arr.length;i++)
                {
                    result=","+arr[i];
                }
                if(result.equals(""))
                {
                    Toast.makeText(MainActivity.this,"Bạn không để đồ ở tủ nào cả "+result, Toast.LENGTH_LONG);
                }
                else
                {
                Toast.makeText(MainActivity.this,"Bạn để đồ ở tủ: "+result, Toast.LENGTH_LONG);}
            }
        });
        dialog.show();

    }
    private void displayDialog1(){
        final Dialog dialog= new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog1);
        final TextView textThongBao= dialog.findViewById(R.id.text_thongbao);
        textThongBao.setText(textThongBao.getText()+" "+tuSelection.getLabel());
        Button buttonDelete= dialog.findViewById(R.id.button_yes);
        Button buttonCancel= dialog.findViewById(R.id.button_no);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String label="";
                String sign= "";
                ContentValues values= new ContentValues();
                values.put("label", label);
                values.put("sign",sign);

                long result= database.update("ProjectI",values,"position=?", new String[]{tuSelection.getPosition()});
                if(result>0)
                    Toast.makeText(MainActivity.this,"Successful", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"UnSuccessful", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                display();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void displayDialog() {
        final Dialog dialog= new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog);
        final EditText editLabel= dialog.findViewById(R.id.edit_label);
        final EditText editSign= dialog.findViewById(R.id.edit_sign);

        editLabel.setText(tuSelection.getLabel());
        editSign.setText(tuSelection.getSign());

        Button buttonDelete= dialog.findViewById(R.id.button_delete);
        Button buttonSave= dialog.findViewById(R.id.button_save);
        Button buttonCancel= dialog.findViewById(R.id.button_cancel);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String label= editLabel.getText().toString();
                String sign= editSign.getText().toString();
                ContentValues values= new ContentValues();
                values.put("label", label);
                values.put("sign",sign);

                long result= database.update("ProjectI",values,"position=?", new String[]{tuSelection.getPosition()});
                if(result>0)
                    Toast.makeText(MainActivity.this,"Successful", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"UnSuccessful", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                display();

            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String label="";
                String sign= "";
                ContentValues values= new ContentValues();
                values.put("label", label);
                values.put("sign",sign);

                long result= database.update("ProjectI",values,"position=?", new String[]{tuSelection.getPosition()});
                if(result>0)
                    Toast.makeText(MainActivity.this,"Successful", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"UnSuccessful", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                display();


            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void display() {
        database= openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor= database.query(TABLENAME, null, null, null,null,null,null);
        adapter.clear();
        while (cursor.moveToNext())
        {
            String position= cursor.getString(0);
            String lable= cursor.getString(1);
            String sign= cursor.getString(2);

            Tu tu= new Tu(position,image,lable,sign);
            adapter.add(tu);
        }
    }

    private void processCopy() {
        try{
            File file= getDatabasePath(DATABASE_NAME);
            if(!file.exists()){
                coppyDatabaseFromAsset();
            }
        }
        catch (Exception ex){
            Log.e("ERRO",ex.toString());
        }
    }

    private String getDatabasePath(){

        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }

    private void coppyDatabaseFromAsset() {
        try{
            InputStream myInput= getAssets().open(DATABASE_NAME);

            String outFileName= getDatabasePath();
            File file= new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!file.exists())
            {
                file.mkdir();
            }
            OutputStream myOutput= new FileOutputStream(outFileName);
            byte[]buffer= new byte[1024];
            int length;
            while ((length= myInput.read(buffer))>0)
            {
                myOutput.write(buffer,0,length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        }
        catch (Exception ex)
        {
            Log.e("ERRO", ex.toString());
        }
    }

    private void addControls() {

        buttonExit= findViewById(R.id.button_exit);
        buttonSearch= findViewById(R.id.button_search);
        gridView= this.<GridView>findViewById(R.id.grid_tu);
        adapter= new TuAdapter(MainActivity.this,R.layout.item);
        gridView.setAdapter(adapter);
        adapter.add(new Tu("1","1"));


    }

    @Override
    protected void onResume() {
        super.onResume();
        display();
    }
}
