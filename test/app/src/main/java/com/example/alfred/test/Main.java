package com.example.alfred.test;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Main extends AppCompatActivity
{
    private Button m_btn_date, m_btn_confirm, m_btn_clear, m_btn_auto;
    private TextView m_textView_date;
    private EditText m_textView_name, m_textView_height, m_textView_weight;
    private Calendar m_dialog_datePick;
    private Spinner m_spinner_bloodType;
    private ArrayAdapter<CharSequence> m_spinnerDataAdapter;
    private RadioGroup m_genderRadioGroup;
    private RadioButton m_radioButton_male;//, m_radioButton_female;
    private CheckBox m_check_dataConfirm;
    private String m_strBloodType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.viewObjectConnect();
        this.setBloodTypeSpinner();
        this.setButtonListener();
    }

    private void viewObjectConnect()
    {
        m_btn_date = (Button)findViewById(R.id.btn_datepick);
        m_btn_confirm = (Button)findViewById(R.id.btn_confirm);
        m_btn_clear = (Button)findViewById(R.id.btn_clear);
        m_textView_date = (TextView)findViewById(R.id.textView_date);
        m_dialog_datePick = Calendar.getInstance();
        m_textView_name = (EditText)findViewById(R.id.txtView_name);
        m_textView_height = (EditText)findViewById(R.id.textView_height);
        m_textView_weight = (EditText)findViewById(R.id.txtView_weight);
        m_genderRadioGroup = (RadioGroup)findViewById(R.id.gender_radioGroup);
        m_radioButton_male = (RadioButton)findViewById(R.id.gender_male);
//        m_radioButton_female = (RadioButton)findViewById(R.id.gender_female);
        m_check_dataConfirm = (CheckBox)findViewById(R.id.checkBox_comfirm);
        m_btn_auto = (Button)findViewById(R.id.btn_auto);
    }

    private void setBloodTypeSpinner()
    {
        m_spinner_bloodType = findViewById(R.id.spinner_blood);
        m_spinnerDataAdapter = ArrayAdapter.createFromResource(Main.this, R.array.dataArrayOfSpinner, android.R.layout.simple_spinner_item);
        m_spinner_bloodType.setAdapter(m_spinnerDataAdapter);

        m_spinner_bloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
//                m_textView_debugOutput.setText(m_spinnerDataAdapter.getItem(position));
                m_strBloodType = m_spinnerDataAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    private void setButtonListener()
    {
        m_btn_date.setOnClickListener(btn_dateListener);
        m_btn_confirm.setOnClickListener(btn_confirmListener);
        m_btn_clear.setOnClickListener(btn_clearListener);
        m_btn_auto.setOnClickListener(btn_autoListener);
    }

    private boolean isDataAllSet()
    {
        boolean bCheckFail = false;
        String strErrorMessage="";

        boolean bNameFail = m_textView_name.getText().length()==0 ? true : false;
        bCheckFail |= bNameFail;
        strErrorMessage += bNameFail ? "姓名" : "";
        strErrorMessage += bCheckFail ? "、" : "";

        boolean bHightFail= m_textView_height.getText().length()==0 ? true : false;
        bCheckFail |= bHightFail;
        strErrorMessage += bHightFail ? "身高" : "";
        strErrorMessage += bCheckFail ? "、" : "";

        boolean bWeightFail = m_textView_weight.getText().length()==0 ? true : false;
        bCheckFail |= bWeightFail;
        strErrorMessage += bWeightFail ? "體重" : "";
        strErrorMessage += bCheckFail ? "、" : "";

        boolean bDateFail = m_textView_date.getText().equals(getString(R.string.TextView_Brithday_default)) ? true : false;
        bCheckFail |= bDateFail;
        strErrorMessage += bDateFail ? "生日" : "";
        strErrorMessage += bCheckFail ? "、" : "";

        strErrorMessage += bCheckFail ? "尚未輸入完成！\n" : "";

        boolean bGenderFail = m_genderRadioGroup.getCheckedRadioButtonId()==-1 ? true : false;
        bCheckFail |= bGenderFail;
        strErrorMessage += bGenderFail ? "性別尚未點選！\n" : "";

        boolean bConfirmFail = m_check_dataConfirm.isChecked() ? false : true;
        bCheckFail |= bConfirmFail;
        strErrorMessage += bConfirmFail ? "資料確認未勾選！\n" : "";

        Toast.makeText(Main.this, strErrorMessage, Toast.LENGTH_SHORT).show();

        return !bCheckFail;
    }

    private Bundle saveDataAsBundle()
    {
        Bundle bundle = new Bundle();

        bundle.putString(getString(R.string.KEY_NAME), m_textView_name.getText().toString());
        bundle.putString(getString(R.string.KEY_BIRTHDAY), m_textView_date.getText().toString());
        bundle.putString(getString(R.string.KEY_GENDER), m_radioButton_male.isChecked() ? "男" : "女");
        bundle.putString(getString(R.string.KEY_HIGHT), m_textView_height.getText().toString());
        bundle.putString(getString(R.string.KEY_WEIGHT), m_textView_weight.getText().toString());
        bundle.putString(getString(R.string.KEY_BLOODTYPE), m_strBloodType);

        return bundle;
    }

    Button.OnClickListener btn_dateListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int year = m_dialog_datePick.get(Calendar.YEAR);
            int month = m_dialog_datePick.get(Calendar.MONTH);
            int day = m_dialog_datePick.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(Main.this, dateSet, year, month, day).show();
        }
    };

    Button.OnClickListener btn_confirmListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if ( isDataAllSet() == false )
                return;

            Intent intent = new Intent(Main.this, Page2.class);
            intent.putExtras(saveDataAsBundle());
            startActivityForResult(intent, RESULT_OK);
        }
    };

    Button.OnClickListener btn_clearListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            m_textView_date.setText(R.string.TextView_Brithday_default);
            m_textView_name.setText("");
            m_textView_height.setText("");
            m_textView_weight.setText("");
            m_genderRadioGroup.clearCheck();
            m_check_dataConfirm.setChecked(false);
        }
    };

    Button.OnClickListener btn_autoListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            m_textView_date.setText("1989/09/11");
            m_textView_name.setText("Alfred");
            m_textView_height.setText("172.0");
            m_textView_weight.setText("65.0");
            m_radioButton_male.setChecked(true);
            m_check_dataConfirm.setChecked(true);
        }
    };

    DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            m_textView_date.setText(year + "/" + (month+1) + "/" + day);
        }
    };

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        switch(requestCode)
        {
            case RESULT_OK:
                Bundle bundle = data.getExtras();
                break;
        }
    }
}
