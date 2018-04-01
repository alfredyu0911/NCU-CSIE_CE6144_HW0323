package com.example.alfred.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Page2 extends AppCompatActivity
{
    private TextView m_textView_birthday, m_textView_name, m_textView_hight;
    private TextView m_textView_weight, m_textView_bloodtype, m_textView_gender, m_txtView_BMI;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        this.viewObjectConnect();
        this.restoreDataFromBundle();
        this.calcData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(Page2.this, Main.class);
        this.setResult(RESULT_OK,intent);
        this.finish();
        return true;
    }

    private void restoreDataFromBundle()
    {
        Bundle bundle = this.getIntent().getExtras();

        m_textView_name.setText(bundle.getString(getString(R.string.KEY_NAME)));
        m_textView_weight.setText(bundle.getString(getString(R.string.KEY_WEIGHT)));
        m_textView_hight.setText(bundle.getString(getString(R.string.KEY_HIGHT)));
        m_textView_gender.setText(bundle.getString(getString(R.string.KEY_GENDER)));
        m_textView_birthday.setText(bundle.getString(getString(R.string.KEY_BIRTHDAY)));
        m_textView_bloodtype.setText(bundle.getString(getString(R.string.KEY_BLOODTYPE)));
    }

    private void calcData()
    {
        m_txtView_BMI.setText(getBMI());
    }

    private void viewObjectConnect()
    {
        m_textView_birthday = (TextView)findViewById(R.id.txtView_birthday);
        m_textView_bloodtype = (TextView)findViewById(R.id.txtView_bloodtype);
        m_textView_gender = (TextView)findViewById(R.id.txtView_gender);
        m_textView_hight = (TextView)findViewById(R.id.txtView_hight);
        m_textView_name = (TextView)findViewById(R.id.txtView_name);
        m_textView_weight = (TextView)findViewById(R.id.txtView_weight);
        m_txtView_BMI = (TextView)findViewById(R.id.txtView_BMI);
    }

    private String getBMI()
    {
        double dHight = Double.parseDouble(m_textView_hight.getText().toString()) / 100.0;
        double dWeight= Double.parseDouble(m_textView_weight.getText().toString());

        return String.valueOf(dWeight/dHight/dHight);
    }
}
