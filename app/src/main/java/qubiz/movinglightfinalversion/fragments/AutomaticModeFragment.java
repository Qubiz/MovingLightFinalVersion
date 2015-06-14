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
 * Created by Mathijs on 14/06/2015.
 */
public class AutomaticModeFragment extends Fragment {

    TextView textViewBrightness;
    SeekBar seekBarBrightness;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_automatic_mode, container, false);

        textViewBrightness = (TextView) view.findViewById(R.id.text_view_brightness);
        seekBarBrightness = (SeekBar) view.findViewById(R.id.seek_bar_brightness);

        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewBrightness.setText("" + progress + "%");
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
