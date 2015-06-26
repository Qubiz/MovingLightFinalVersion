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

/**
 * Author: Mathijs de Groot (S1609483)
 *
 * Info:
 * De class MainActivity is de activity die aan wordt geroepen bij het starten van de app.
 */
public class MainActivity extends ActionBarActivity {

    /**
     * De toolbar bovenin de app.
     */
    private Toolbar toolbar;

    /**
     * De buttons voor het switchen tussen automatisch en handmatige modes.
     */
    private AppCompatButton buttonAutomatic;
    private AppCompatButton buttonManual;

    /**
     * De fragments voor de content van de autmatische en handmatige modes.
     */
    private Fragment automaticModeFragment;
    private Fragment manualModeFragment;

    private FragmentTransaction fragmentTransaction;

    /**
     * Flag om te kijken in welke toestand de app/lamp zich op dit moment bevindt.
     *
     * true = automatisch
     * false = handmatig
     */
    private boolean automaticModeOn = true;

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

        // CREËER DE FRAGMENTEN
        automaticModeFragment = new AutomaticModeFragment();
        manualModeFragment = new ManualModeFragment();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // STEL DE AUTOMATISCHE MODES IN ALS HUIDIGE CONTENT
        fragmentTransaction.replace(R.id.fragment_container, automaticModeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        // INITIAL SETTINGS
        buttonAutomatic.setBackgroundColor(Color.parseColor("#FF5722"));
        buttonManual.setBackgroundColor(Color.parseColor("#B6B6B6"));

        // ONCLICKLISTNER VOOR HET SWITCHEN TUSSEN DE VERSCHILLENDE MODES.
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

        // ONCLICKLISTNER VOOR HET SWITCHEN TUSSEN DE VERSCHILLENDE MODES.
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

        // VOEG DE 'ON-OFF' SWITCH TOE AAN DE TOOLBAR
        MenuItem item = menu.findItem(R.id.toolbar_on_off_switch);
        item.setActionView(R.layout.toolbar_on_off_switch);
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
