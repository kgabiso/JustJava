package com.example.kaysherman.justjava;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tv_quantity, tv_price;
    EditText edName;
    int quantity = 0;
    CheckBox topping,chocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_price = (TextView) findViewById(R.id.price);
        tv_quantity = (TextView) findViewById(R.id.quantity);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void ButtonsClick(View v) {
//Order button onClick
        if (v.getId() == R.id.orderDisplay) {
            topping = (CheckBox)findViewById(R.id.topping);
            chocolate = (CheckBox)findViewById(R.id.chocolate);
            edName = (EditText)findViewById(R.id.name);
            String name = edName.getText().toString();
            boolean hasWhippedCream = topping.isChecked();
            boolean hasChocolate = chocolate.isChecked();
            int price = calculatePrice(hasWhippedCream,hasChocolate);
          String Message =  createOrderSummery(name,price,hasWhippedCream,hasChocolate);
            Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
            intentEmail.setData(Uri.parse("mailto:"));
            intentEmail.putExtra(intentEmail.EXTRA_SUBJECT,"Just Java order for "+name);
            intentEmail.putExtra(intentEmail.EXTRA_TEXT,Message);
            if(intentEmail.resolveActivity(getPackageManager())!=null)
            {
                startActivity(intentEmail);
            }
            displayMessage(Message);

        } else if (v.getId() == R.id.increment) {//increment button onClick

            if(quantity == 100)
            {
                Toast.makeText(getApplicationContext(),"You can not have more than 100 coffee",Toast.LENGTH_LONG).show();
                return;
            }
            quantity = quantity + 1;
            displayQuantity(quantity);
        } else if (v.getId() == R.id.decrement) {//decrement button onClick
            if (quantity > 1) {
                quantity = quantity - 1;
                displayQuantity(quantity);
            }
            else {
                Toast.makeText(getApplicationContext(),"You can not have less than 1 coffee",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void displayQuantity(int numberOfCoffee) {


        tv_quantity.setText("" + numberOfCoffee);

    }

    private int calculatePrice(boolean whippedCream,boolean chocolate) {
        int price = 5;
        if(whippedCream == true) {
            price = price   + 1;

       }
        if(chocolate==true)
        {
            price = price + 2 ;
        }
        return quantity * price ;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private String createOrderSummery(String name,int price,boolean addWhippedCream,boolean addChocolate) {
        String priceMessage = name;
        priceMessage = priceMessage +"\nAdd whipped cream ?  "+addWhippedCream;
        priceMessage = priceMessage +"\nAdd chocolate ? "+addChocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage +"\nAmount Due: R" + price;
        priceMessage = priceMessage + "\nThank you";


        return priceMessage;
    }

    public void displayMessage(String message) {
        tv_price.setText(message);
    }

}
