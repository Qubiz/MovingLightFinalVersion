package qubiz.movinglightfinalversion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import qubiz.movinglightfinalversion.R;

/**
 * Author: Mathijs de Groot (S1609483)
 *
 * Info:
 * De class AutomaticModeFragment bevat de content van de automatische stand.
 */
public class AutomaticModeFragment extends Fragment {

    TextView textViewBrightness;
    TextView textViewBrightness2;
    SeekBar seekBarBrightness;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_automatic_mode, container, false);

        textViewBrightness = (TextView) view.findViewById(R.id.text_view_brightness);
        textViewBrightness2 = (TextView) view.findViewById(R.id.text_view_brightness_2);
        seekBarBrightness = (SeekBar) view.findViewById(R.id.seek_bar_brightness);
        textViewBrightness2.setText("" + seekBarBrightness.getProgress() + "%");

        // Update de brightness text views wanneer de waarde van de seekBar veranderd.
        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewBrightness.setText("" + progress + "%");
                textViewBrightness2.setText("" + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Bundle bundle = getArguments();
        if(bundle != null) {
            // SET ARGUMENTS
        }
        return view;
    }
}
