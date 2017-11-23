package crypto.org.crypto;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.lang.reflect.Method;

import crypto.org.crypto.Classes.User;
import crypto.org.crypto.Classes.UserValuta;
import crypto.org.crypto.Classes.UserValutaJson;
import crypto.org.crypto.volley.AppController;
import crypto.org.crypto.volley.BetterStringRequest;

public class AddUservaluta extends AppCompatActivity {
    private UserValuta userValuta;
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    View rootLayout;

    private int revealX;
    private int revealY;

    private EditText etAmount;
    private EditText etPrice;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_uservaluta);
        userValuta = (UserValuta) getIntent().getSerializableExtra("userValuta");
        setTitle(String.format("Add new %s purchase", userValuta.getValuta().getName()));
        handleIntent(savedInstanceState);

        etAmount = findViewById(R.id.etAmount);
        etPrice = findViewById(R.id.etPrice);
        tvError = findViewById(R.id.tvError);

        etPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {

                    case EditorInfo.IME_ACTION_DONE:
                        btnAdd_clicked(v);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void handleIntent(Bundle savedInstanceState) {
        final Intent intent = getIntent();
        rootLayout = findViewById(R.id.root_layout);

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);
            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }
    }

    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(800);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    rootLayout, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(1600);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(View.INVISIBLE);
                    finish();
                }
            });


            circularReveal.start();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call

                //stopActivityTask();  // finish() here.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        unRevealActivity();
        super.onBackPressed();
    }

    public void btnAdd_clicked(View view) {
        if (etAmount.getText().toString().equals("")) {
            tvError.setText(R.string.amountRq);
            tvError.setVisibility(View.VISIBLE);
            return;
        }
        if (etPrice.getText().toString().equals("")) {
            tvError.setText(R.string.priceRq);
            tvError.setVisibility(View.VISIBLE);
            return;
        }
        double amount = 0;
        double price = 0;

        try {
            amount = Double.parseDouble(etAmount.getText().toString());
            price = Double.parseDouble(etPrice.getText().toString());
        } catch (NumberFormatException nfe){
            tvError.setText(R.string.wrongFormat);
            tvError.setVisibility(View.VISIBLE);
            return;
        }
        final UserValutaJson userValutaJson = new UserValutaJson(amount, price, userValuta.getValuta().getId(), 1);
        String url = "https://i329146.venus.fhict.nl/api/uservalutas";
        BetterStringRequest jsObjRequest = new BetterStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvError.setText(error.getMessage());
                tvError.setVisibility(View.VISIBLE);

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError{
                Gson gson = new Gson();
                String json = gson.toJson(userValutaJson);
                return gson.toJson(userValutaJson).getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}
