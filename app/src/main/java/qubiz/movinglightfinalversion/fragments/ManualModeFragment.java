package qubiz.movinglightfinalversion.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import qubiz.movinglightfinalversion.Preset;
import qubiz.movinglightfinalversion.R;
import qubiz.movinglightfinalversion.adapters.PresetsListAdapter;
import qubiz.movinglightfinalversion.database.PresetSQLiteHelper;

/**
 * Created by Mathijs on 14/06/2015.
 */
public class ManualModeFragment extends Fragment {

    private AppCompatButton addPresetButton;

    PresetsListAdapter presetsListAdapter;
    private List<Preset> presets;
    private ListView presetsListView;

    private PresetSQLiteHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new PresetSQLiteHelper(getActivity());
        presets = new ArrayList<Preset>();
        presets.addAll(db.getAllPresets());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual_mode, container, false);

        presetsListAdapter = new PresetsListAdapter(getActivity(), presets);
        presetsListView = (ListView) view.findViewById(R.id.list_view_presets);

        presetsListView.setAdapter(presetsListAdapter);

        addPresetButton = (AppCompatButton) view.findViewById(R.id.button_add_new_preset);
        addPresetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPreset();
            }
        });

        presetsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changePreset(position);
            }
        });

        Bundle bundle = getArguments();
        if(bundle != null) {
            // SET ARGUMENTS
        }
        return view;
    }

    private void addNewPreset() {
        final View newPresetDialogView = getLayoutInflater(getArguments()).inflate(R.layout.dialog_fragment_new_preset, null);
        final EditText presetName = (EditText) newPresetDialogView.findViewById(R.id.dialog_fragment_new_preset_edit_text);

        final ColorPicker colorPicker = (ColorPicker) newPresetDialogView.findViewById(R.id.color_picker);
        SaturationBar saturationBar = (SaturationBar) newPresetDialogView.findViewById(R.id.saturation_bar);
        ValueBar valueBar = (ValueBar) newPresetDialogView.findViewById(R.id.value_bar);

        colorPicker.setShowOldCenterColor(false);
        colorPicker.addSaturationBar(saturationBar);
        colorPicker.addValueBar(valueBar);

        AlertDialog newPresetDialog = new AlertDialog.Builder(getActivity())
                .setView(newPresetDialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Preset newPreset;
                        if(presetName.getText().toString().isEmpty()) {
                            newPreset = new Preset("Preset " + (presets.size() + 1), colorPicker.getColor(), false);
                        } else {
                            newPreset = new Preset(presetName.getText().toString(), colorPicker.getColor(), false);
                        }
                        presets.add(newPreset);
                        db.addPreset(newPreset);
                        presetsListAdapter.notifyDataSetChanged();
                        Log.e("OK", "OK");
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create();
        newPresetDialog.show();
    }

    private void changePreset(final int position) {
        final Preset preset = presets.get(position);

        final View newPresetDialogView = getLayoutInflater(getArguments()).inflate(R.layout.dialog_fragment_new_preset, null);
        final EditText presetName = (EditText) newPresetDialogView.findViewById(R.id.dialog_fragment_new_preset_edit_text);

        final ColorPicker colorPicker = (ColorPicker) newPresetDialogView.findViewById(R.id.color_picker);
        SaturationBar saturationBar = (SaturationBar) newPresetDialogView.findViewById(R.id.saturation_bar);
        ValueBar valueBar = (ValueBar) newPresetDialogView.findViewById(R.id.value_bar);

        colorPicker.setShowOldCenterColor(false);
        colorPicker.addSaturationBar(saturationBar);
        colorPicker.addValueBar(valueBar);
        colorPicker.setColor(preset.getColor());

        presetName.setText(preset.getName());

        AlertDialog newPresetDialog = new AlertDialog.Builder(getActivity())
                .setView(newPresetDialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(presetName.getText().toString().isEmpty()) {
                            preset.setName("Preset " + (position + 1));
                        } else {
                            preset.setName(presetName.getText().toString());
                        }
                        preset.setColor(colorPicker.getColor());
                        db.updatePreset(preset);
                        presetsListAdapter.notifyDataSetChanged();
                    }
                })
                .setNeutralButton("REMOVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removePreset(position);
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create();
        newPresetDialog.show();
    }

    private void removePreset(final int position) {
        AlertDialog removePresetWarning = new AlertDialog.Builder(getActivity())
                .setMessage("Remove the preset?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deletePreset(presets.get(position));
                        presets.remove(position);
                        presetsListAdapter.notifyDataSetChanged();
                    }
                })
                .setNeutralButton("NO", null)
                .create();
        removePresetWarning.show();
    }

}


