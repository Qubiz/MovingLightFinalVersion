package qubiz.movinglightfinalversion;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import qubiz.movinglightfinalversion.fragments.AutomaticModeFragment;
import qubiz.movinglightfinalversion.fragments.ManualModeFragment;

public class MainActivity extends ActionBarActivity {

    Toolbar toolbar;

    AppCompatButton buttonAutomatic;
    AppCompatButton buttonManual;

    Fragment automaticModeFragment;
    Fragment manualModeFragment;

    FragmentTransaction fragmentTransaction;

    boolean automaticModeOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SET THE TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // INITIALIZE VARIABLES
        buttonAutomatic = (AppCompatButton) findViewById(R.id.button_automatic);
        buttonManual = (AppCompatButton) findViewById(R.id.button_manual);

        automaticModeFragment = new AutomaticModeFragment();
        manualModeFragment = new ManualModeFragment();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, automaticModeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        // INITIAL SETTINGS
        buttonAutomatic.setBackgroundColor(Color.parseColor("#FF5722"));
        buttonManual.setBackgroundColor(Color.parseColor("#B6B6B6"));

        buttonAutomatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!automaticModeOn) {
                    // SWITCH COLORS
                    buttonAutomatic.setBackgroundColor(Color.parseColor("#FF5722"));
                    buttonManual.setBackgroundColor(Color.parseColor("#B6B6B6"));

                    // SWITCH FRAGMENT
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, automaticModeFragment);
                    fragmentTransaction.commit();

                    automaticModeOn = true;
                }
            }
        });

        buttonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (automaticModeOn) {
                    // SWITCH COLOR
                    buttonManual.setBackgroundColor(Color.parseColor("#FF5722"));
                    buttonAutomatic.setBackgroundColor(Color.parseColor("#B6B6B6"));

                    // SWITCH FRAGMENT
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, manualModeFragment);
                    fragmentTransaction.commit();

                    automaticModeOn = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
