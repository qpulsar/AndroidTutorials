package tr.gen.korkusuz.bm_03_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etDisplay;
    TextView tvOp;
    TextView tvNumber;

    Double number1 = 0d;
    Double number2 = 0d;

    String pendingOp = "";

    boolean clear = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewSettings();
    }

    private void viewSettings() {
        Button btn0 = findViewById(R.id.btn_0);
        Button btn1 = findViewById(R.id.btn_1);
        Button btn2 = findViewById(R.id.btn_2);
        Button btn3 = findViewById(R.id.btn_3);
        Button btn4 = findViewById(R.id.btn_4);
        Button btn5 = findViewById(R.id.btn_5);
        Button btn6 = findViewById(R.id.btn_6);
        Button btn7 = findViewById(R.id.btn_7);
        Button btn8 = findViewById(R.id.btn_8);
        Button btn9 = findViewById(R.id.btn_9);
        Button btnC = findViewById(R.id.btn_c);
        Button btnDiv = findViewById(R.id.btn_div);
        Button btnMul = findViewById(R.id.btn_multiply);
        Button btnMin = findViewById(R.id.btn_minus);
        Button btnPls = findViewById(R.id.btn_plus);
        Button btnEq = findViewById(R.id.btn_equal);
        Button btnDot = findViewById(R.id.btn_dot);

        etDisplay = findViewById(R.id.et_display);
        tvOp = findViewById(R.id.tv_operator);
        tvNumber = findViewById(R.id.tv_number);

        View.OnClickListener numberLister = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                String text = btn.getText().toString();

                if(clear){
                    etDisplay.setText("0");
                    clear = false;
                }

                if (etDisplay.getText().toString().equals("0") && !text.equals("."))
                    etDisplay.setText(text);
                else
                    etDisplay.append(text);
            }
        };

        btn0.setOnClickListener(numberLister);
        btn1.setOnClickListener(numberLister);
        btn2.setOnClickListener(numberLister);
        btn3.setOnClickListener(numberLister);
        btn4.setOnClickListener(numberLister);
        btn5.setOnClickListener(numberLister);
        btn6.setOnClickListener(numberLister);
        btn7.setOnClickListener(numberLister);
        btn8.setOnClickListener(numberLister);
        btn9.setOnClickListener(numberLister);
        btnDot.setOnClickListener(numberLister);

        View.OnClickListener operationListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                String op = btn.getText().toString();
                String value = etDisplay.getText().toString();
                Double dv = Double.valueOf(value);
                performOp(dv, op);
            }
        };

        btnDiv.setOnClickListener(operationListener);
        btnMul.setOnClickListener(operationListener);
        btnMin.setOnClickListener(operationListener);
        btnPls.setOnClickListener(operationListener);
        btnEq.setOnClickListener(operationListener);

    }

    private void performOp(Double dv, String op) {
        Double result = 0d;
        if (number1 == 0) {
            number1 = dv;
            etDisplay.setText("0");
        } else {
            number2 = dv;

            if(pendingOp.equals("="))
                pendingOp = op;

            switch (pendingOp) {
                case "+":
                    result = number1 + number2;
                    break;
                case "-":
                    result = number1 - number2;
                    break;
                case "/":
                    result = number1 / number2;
                    break;
                case "*":
                    result = number1 * number2;
                    break;
            }

            if(Math.round(result) == result)
                etDisplay.setText(String.valueOf(Math.round(result)));
            else
                etDisplay.setText(String.valueOf(result));

            number1 = result;
            number2 = 0d;
            clear = true;

        }
        pendingOp = op;
        tvNumber.setText(String.valueOf(number1));
        tvOp.setText(pendingOp);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("-----","onSaveInstanceState");
        outState.putString("OPERATOR", pendingOp);
        outState.putDouble("VALUE", number1);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        Log.d("-----","onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        number1 = savedInstanceState.getDouble("VALUE");
        tvOp.setText(savedInstanceState.getString("OPERATOR"));
        tvNumber.setText(String.valueOf(number1));
    }

}