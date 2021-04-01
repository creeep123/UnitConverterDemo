package com.example.unitconverterdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    Spinner unitSpinner;
    private String[] unitArray;
    TextView unit1, unit2, unit3,
            convertedNumber1, convertedNumber2, convertedNumber3;
    EditText inputTextView;
    String currentConverter = "NONE";
    String number1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unitArray = getResources().getStringArray(R.array.unit_array);

        inputTextView = findViewById(R.id.input_number);

        unit1 = findViewById(R.id.convertedUnit1);
        unit2 = findViewById(R.id.convertedUnit2);
        unit3 = findViewById(R.id.convertedUnit3);

        convertedNumber1 = findViewById(R.id.convertedNumber1);
        convertedNumber2 = findViewById(R.id.convertedNumber2);
        convertedNumber3 = findViewById(R.id.convertedNumber3);

        unitSpinner = findViewById(R.id.unitSpinner);
        initSpinner();
    }

    private void initSpinner() {
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this, R.layout.item_select, unitArray);
        //设置数组适配器的布局样式
        unitAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        Spinner sp = findViewById(R.id.unitSpinner);
        //设置下拉框的标题，不设置就没有难看的标题了
        sp.setPrompt("");
        //设置下拉框的数组适配器
        sp.setAdapter(unitAdapter);
        //设置下拉框默认的显示第一项
        sp.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp.setOnItemSelectedListener(new MySelectedListener());
    }

    public void metreClick(View view) {
        String inputStr = inputTextView.getText().toString();
        inputStr = inputStr.isEmpty() ? "0" : inputStr;
        double inputNumber = Double.parseDouble(inputStr);
        if (currentConverter.equals("Metres")) {
            convertedNumber1.setText(processResult(inputNumber * 100));
            convertedNumber2.setText(processResult(inputNumber * 3.28));
            convertedNumber3.setText(processResult(inputNumber * 39.37));
        } else {
            Toast.makeText(MainActivity.this, "Please select the correct conversion icon", Toast.LENGTH_SHORT).show();
        }
    }

    public void celsiusClick(View view) {
        String inputStr = inputTextView.getText().toString();
        inputStr = inputStr.isEmpty() ? "0" : inputStr;
        double inputNumber = Double.parseDouble(inputStr);
        if (currentConverter.equals("Celsius")) {
            convertedNumber1.setText(processResult(inputNumber * 1.8 + 32));
            convertedNumber2.setText(processResult(inputNumber + 273.15));
        } else {
            Toast.makeText(MainActivity.this, "Please select the correct conversion icon", Toast.LENGTH_SHORT).show();
        }
    }

    public void kilogramsClick(View view) {
        String inputStr = inputTextView.getText().toString();
        //校验是否为空
        inputStr = inputStr.isEmpty() ? "0" : inputStr;
        //转换为 double 类型
        double inputNum = Double.parseDouble(inputStr);
        if (currentConverter.equals("Kilograms")) {
            convertedNumber1.setText(processResult(inputNum * 1000));
            convertedNumber2.setText(processResult(inputNum * 35.274));
            convertedNumber3.setText(processResult(inputNum * 2.2046226));
        } else {
            Toast.makeText(MainActivity.this, "Please select the correct conversion icon", Toast.LENGTH_SHORT).show();
        }
    }

    //处理计算结果精度并返回为 String
    public String processResult(double inputNum) {

        BigDecimal bigDecimal = new BigDecimal(inputNum);
        //这里的 2 就是你要保留几位小数。
        double inputNumber = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return Double.toString(inputNumber);
    }

    class MySelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(MainActivity.this, "You chose the：" + unitArray[i], Toast.LENGTH_SHORT).show();
            //切换converter时清空当前结果
            unit1.setText("");
            unit2.setText("");
            unit3.setText("");
            convertedNumber1.setText("");
            convertedNumber2.setText("");
            convertedNumber3.setText("");
            //修改当前 converter
            currentConverter = unitArray[i];
            //根据当前 converter 切换单位
            switch (currentConverter) {
                case "Metres":
                    unit1.setText("Centimetre");
                    unit2.setText("Foot");
                    unit3.setText("inch");
                    break;
                case "Kilograms":
                    unit1.setText("Gram");
                    unit2.setText("Ounce(Oz)");
                    unit3.setText("Pound(lb)");
                    break;
                case "Celsius":
                    unit1.setText("Fahrenheit");
                    unit2.setText("Kelvin");
//                    unit3.setText("");
//                    convertedNumber3.setText("");
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    }
}



