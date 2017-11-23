package crypto.org.crypto;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;

import crypto.org.crypto.Classes.UserValuta;

public class SummaryActivity extends AppCompatActivity {

    private UserValuta userValuta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        userValuta = (UserValuta) getIntent().getSerializableExtra("userValuta");
        Bundle bundle = new Bundle();
        bundle.putSerializable("userValuta", userValuta);

        this.setTitle(userValuta.getValuta().getName());

        FragmentManager manager = getSupportFragmentManager();
        SummaryPagerAdapter adapter = new SummaryPagerAdapter(manager,bundle);
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);

    }

    public void addNewUservaluta_clicked(View view){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX());
        int revealY = (int) (height - view.getY());
        if (revealX > revealY)
            revealY = (int) view.getY() * 2;

        Intent addUserValuta = new Intent(this, AddUservaluta.class);
        addUserValuta.putExtra("userValuta", (Serializable) userValuta);
        addUserValuta.putExtra(AddUservaluta.EXTRA_CIRCULAR_REVEAL_X, revealX);
        addUserValuta.putExtra(AddUservaluta.EXTRA_CIRCULAR_REVEAL_Y, revealY);
        ActivityCompat.startActivity(this, addUserValuta, options.toBundle());
    }
}
