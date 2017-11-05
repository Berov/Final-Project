package com.artgallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artgallery.Model.DBManager;
import com.artgallery.Model.User;
import com.artgallery.Util.Util;

public class WalletActivity extends AppCompatActivity {
    private User user;
    private Button deposit, confirm, cancel;
    private TextView sum, availability;
    private double transferSum;
    public static final int MAX_SUM_IN_THE_BANK = 999999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        user = (User) bundle.getSerializable("USER");
        deposit = (Button) findViewById(R.id.wallet_btn_deposit);
        confirm = (Button) findViewById(R.id.wallet_btn_confirm);
        cancel = (Button) findViewById(R.id.wallet_btn_cancel);
        sum = (TextView) findViewById(R.id.wallet_text_sum);
        availability = (TextView) findViewById(R.id.wallet_text_availability);

        sum.setText("0");
        availability.setText(String.valueOf(Util.twoDecimalPlaces(user.getWallet())));


        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(WalletActivity.this);
                builder.setTitle("Enter the deposite sum:");

                final EditText inputEuro = new EditText(WalletActivity.this);
                inputEuro.setHint("euro          ");


                LinearLayout layout = new LinearLayout(WalletActivity.this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layout.setGravity(Gravity.CENTER);
                layout.addView(inputEuro);


                inputEuro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                inputEuro.setSingleLine();
                inputEuro.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
                builder.setView(layout);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            double euro = 0;
                            euro = Double.parseDouble(inputEuro.getText().toString());
                            transferSum = Util.twoDecimalPlaces(euro);

                            if (transferSum + user.getWallet() > MAX_SUM_IN_THE_BANK) {

                                Toast.makeText(WalletActivity.this, "In the IT Talents Bank max sum of the wallet is 9999999!\nChange your deposit please! You can deposit max " + (MAX_SUM_IN_THE_BANK - user.getWallet()) + " euro!", Toast.LENGTH_LONG).show();
                            } else {

                                sum.setText(String.valueOf(Util.twoDecimalPlaces(Double.parseDouble(inputEuro.getText().toString()))));
                            }
                        } catch (ParseException e) {
                            Toast.makeText(WalletActivity.this, "Enter a regular digits please!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double transfer = user.getWallet() + Double.parseDouble(sum.getText().toString());
                DBManager.getInstance(WalletActivity.this).updateUserWallet(user, Util.twoDecimalPlaces(transfer));
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
