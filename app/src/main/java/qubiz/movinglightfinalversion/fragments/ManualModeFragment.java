package qubiz.movinglightfinalversion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qubiz.movinglightfinalversion.R;

/**
 * Created by Mathijs on 14/06/2015.
 */
public class ManualModeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual_mode, container, false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            // SET ARGUMENTS
        }
        return view;
    }

}
