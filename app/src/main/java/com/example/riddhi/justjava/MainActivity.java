package com.example.riddhi.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
     //  Log.v("MainActivity", "Name: " + name);

        CheckBox whippedCreamCheck = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheck.isChecked();
     //   Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject) + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void incrementOrder(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity  = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrementOrder(View view) {
        if(quantity == 1)
        {
            Toast.makeText(this, "You cannot have less than 1 coffees", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity  = quantity - 1;
        displayQuantity(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
//    private void displayPrice(int number) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance(new Locale("en","IN")).format(number));
//    }

    /**
     * Calculates the price of the order
     *
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream)
        {
            basePrice = basePrice + 1;
        }

        if (addChocolate)
        {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage  = priceMessage + "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        priceMessage  = priceMessage + "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage = priceMessage + "\nTotal: " + price;
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you) ;
        return priceMessage;
    }
}