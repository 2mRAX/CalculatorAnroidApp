package com.leshnevsksy.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.Expression;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private int openBrackets = 0;
    final static String tempTextView = "TEMP_TEXTVIEW1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView_input_numbers);
        textView.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TextView nameView = (TextView) findViewById(R.id.textView_input_numbers);
        outState.putString(tempTextView, nameView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String textViewText= savedInstanceState.getString(tempTextView);
        TextView nameView = (TextView) findViewById(R.id.textView_input_numbers);
        nameView.setText(textViewText);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_zero:
                textView.append("0");
                break;
            case R.id.button_one:
                textView.append("1");
                break;
            case R.id.button_two:
                textView.append("2");
                break;
            case R.id.button_three:
                textView.append("3");
                break;
            case R.id.button_four:
                textView.append("4");
                break;
            case R.id.button_five:
                textView.append("5");
                break;
            case R.id.button_six:
                textView.append("6");
                break;
            case R.id.button_seven:
                textView.append("7");
                break;
            case R.id.button_eight:
                textView.append("8");
                break;
            case R.id.button_nine:
                textView.append("9");
                break;
            case R.id.button_addition:
                addOperand("+");
                break;
            case R.id.button_subtraction:
                addOperand("-");
                break;
            case R.id.button_multiplication:
                addOperand("*");
                break;
            case R.id.button_division:
                addOperand("/");
                break;
            case R.id.button_dot:
                addDot();
                break;
            case R.id.button_clear:
                textView.setText("");
                openBrackets = 0;
                break;
            case R.id.button_clear_entry:
                String string = textView.getText().toString();
                if (string.length() > 0) {
                    textView.setText(string.substring(0, string.length()-1));
                }
                break;
            case R.id.button_percent:
                addPPF("%");
                break;
            case R.id.button_brackets:
                addBracket();
                break;
            case R.id.button_equal:
                forEqualButton();
                break;
            case R.id.button_euler:
                addConstant("e");
                break;
            case R.id.button_pi:
                addConstant("pi");
                break;
            case R.id.button_negativeNumber:
                forEqualButton();
                toNegativeNumber();
                forEqualButton();
                break;
            case R.id.button_sin:
                addFunction("sin(");
                break;
            case R.id.button_cos:
                addFunction("cos(");
                break;
            case R.id.button_tan:
                addFunction("tan(");
                break;
            case R.id.button_ctg:
                addFunction("cot(");
                break;
            case R.id.button_log10:
                addFunction("log10(");
                break;
            case R.id.button_ln:
                addFunction("ln(");
                break;
            case R.id.button_abs:
                addFunction("abs(");
                break;
            case R.id.button_sqrt:
                addFunction("sqrt(");
                break;
            case R.id.button_rad:
                addFunction("rad(");
                break;
            case R.id.button_exponentiation:
                addFunction("exp(");
                break;
            case R.id.button_inverse:
                forEqualButton();
                toInverseNumber();
                forEqualButton();
                break;
            case R.id.button_power:
                addPPF("^");
                break;
            case R.id.button_factorial:
                addPPF("!");
                break;
        }
    }


    private void addBracket() {
        if (textView.getText().toString().equals("")) {
            textView.append("(");
            openBrackets++;
            return;
        }

        String txt = textView.getText().toString();
        int num = txt.length();
        String text =  txt.substring(num - 1);
        char last_c = text.toCharArray()[0];
        String last_t = String.valueOf(last_c);

        if ((text.equals(")") || last_t.matches("\\d") ) && openBrackets > 0) {
            textView.append(")");
            openBrackets--;
        } else if (text.equals("(") || text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/") || text.equals("^")) {
            textView.append("(");
            openBrackets++;
        } else if ((text.equals(")") || last_t.matches("\\d") ) && openBrackets == 0) {
            textView.append("*(");
            openBrackets++;
        }
    }
    private void addOperand(String operand) {
        if (textView.getText().toString().equals("")) {
            return;
        }
        String txt = textView.getText().toString();
        int num = txt.length();
        String text =  txt.substring(num - 1);
        if (text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/") || text.equals(".")) {
            return;
        }

        textView.append(operand);
    }

    private void addFunction(String func) {
        if (textView.getText().toString().equals("")) {
            textView.append(func);
            openBrackets++;
            return;
        }

        String txt = textView.getText().toString();
        int num = txt.length();
        String text =  txt.substring(num - 1);
        char last_c = text.toCharArray()[0];
        String last_t = String.valueOf(last_c);

        if (last_t.matches("\\d") ) {
            textView.append("*");
            textView.append(func);
            openBrackets++;
        }

        if (text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/") ) {
            textView.append(func);
            openBrackets++;
        }

        if (text.equals("(")) {
            textView.append(func);
            openBrackets++;
            return;
        }
    }

    private void addConstant(String constant) {
        if (textView.getText().toString().equals("")) {
            textView.append(constant);
            return;
        }

        String txt = textView.getText().toString();
        int num = txt.length();
        String text = txt.substring(num - 1);
        char last_c = text.toCharArray()[0];
        String last_t = String.valueOf(last_c);

        if (last_t.matches("\\d") || last_t.matches("i") || last_t.matches("e") ) {
            textView.append("*");
            textView.append(constant);
        }

        if (text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/") ) {
            textView.append(constant);
        }

        if (text.equals("(")) {
            textView.append(constant);
        }
    }

    private void addDot() {

        if (textView.getText().toString().equals("")) {
            textView.append("0.");
        }

        String txt = textView.getText().toString();
        int num = txt.length();
        String text =  txt.substring(num - 1);
        char last_c = text.toCharArray()[0];
        String last_t = String.valueOf(last_c);

        if (last_t.matches("\\d") ) {
            textView.append(".");
        }

        if (text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/") || text.equals("(")) {
            textView.append("0.");
        }

    }

    private void addPPF(String str_PPF){
        if (textView.getText().toString().equals("")) {
            return;
        }

        String txt = textView.getText().toString();
        int num = txt.length();
        String text =  txt.substring(num - 1);

        if (text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/") ||
                text.equals("%") || text.equals("!") || text.equals("^")|| text.equals("(") ) {
            return;
        }

        textView.append(str_PPF);
    }

    private void toNegativeNumber() {
        textView.append("*(-1)");
        forEqualButton();
    }

    private void toInverseNumber() {
        textView.append("^(-1)");
        forEqualButton();
    }

    private void forEqualButton() {
        if (textView.getText().toString().equals("")) {
            textView.append("0");
        }

        String txt = textView.getText().toString();
        int num = txt.length();
        String text =  txt.substring(num - 1);
        String str = textView.getText().toString();
        StringBuffer strBuff = new StringBuffer(str);

        if (text.equals("+") || text.equals("-") ) {
            strBuff.append("0.0");
        }

        if (text.equals("*") || text.equals("/") || text.equals("^")) {
            strBuff.append("1.0");
        }

        if (openBrackets != 0) {
            for (int i=0; i<openBrackets; i++) {
                strBuff.append(")");
            }
            openBrackets = 0;
        }

        double result = calc(strBuff.toString());
        if (result == Math.floor(result)) {
            long temp_long = Math.round(result);
            String str_temp = Long.toString(temp_long);
            textView.setText(str_temp);
        }  else {
            String str_temp = Double.toString(result);
            textView.setText(str_temp);
        }
    }

    private static Double calc(String str) {
        Expression result = new Expression(str);
        return result.calculate();
    }

}

