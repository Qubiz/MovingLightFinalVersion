package qubiz.movinglightfinalversion.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

import java.util.ArrayList;
import java.util.List;

import qubiz.movinglightfinalversion.presets.Preset;
import qubiz.movinglightfinalversion.R;
import qubiz.movinglightfinalversion.adapters.PresetsListAdapter;
import qubiz.movinglightfinalversion.database.PresetSQLiteHelper;

/**
 * Author: Mathijs de Groot (S1609483)
 *
 * Info:
 * De class ManualModeFragment bevat de content voor de handmatige stand en bevat alle
 * functionaliteit voor het maken, veranderen en verwijderen van presets.
 */
public class ManualModeFragment extends Fragment {

    /**
     * Button voor het toevoegen van een nieuwe preset.
     */
    private AppCompatButton addPresetButton;

    /**
     * De adapter voor de lijst met presets.
     */
    PresetsListAdapter presetsListAdapter;

    /**
     * De lijst met preset data.
     */
    private List<Preset> presets;

    /**
     * De list view waarin de presets worden gezet.
     */
    private ListView presetsListView;

    /**
     * De databasehelper voor het schrijven en lezen uit de database.
     */
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

        // Initialiseer de lijst adapter en de list view.
        presetsListAdapter = new PresetsListAdapter(getActivity(), presets);
        presetsListView = (ListView) view.findViewById(R.id.list_view_presets);

        // Zet de adapter als de adapter voor de list view.
        presetsListView.setAdapter(presetsListAdapter);

        // Initialiseer de button en voeg functionaliteit toe voor het toevoeggen van een preset.
        addPresetButton = (AppCompatButton) view.findViewById(R.id.button_add_new_preset);
        addPresetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPreset();
            }
        });

        // Voeg functionaliteit toe voor het klikken op een preset uit de lijst.
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

    /**
     * Het aanroepen van deze methode opent een dialoogscherm voor het creëeren van een nieuwe preset.
     */
    private void addNewPreset() {
        // Creëer een layout voor het dialoogscherm.
        final View newPresetDialogView = getLayoutInflater(getArguments()).inflate(R.layout.dialog_fragment_new_preset, null);
        final EditText presetName = (EditText) newPresetDialogView.findViewById(R.id.dialog_fragment_new_preset_edit_text);

        // Creëer een nieuwe kleurenkiezer, verzadigings-zoeker en helderheid-zoeker.
        final ColorPicker colorPicker = (ColorPicker) newPresetDialogView.findViewById(R.id.color_picker);
        SaturationBar saturationBar = (SaturationBar) newPresetDialogView.findViewById(R.id.saturation_bar);
        ValueBar valueBar = (ValueBar) newPresetDialogView.findViewById(R.id.value_bar);

        colorPicker.setShowOldCenterColor(false);

        // Voeg de verzadiging en helderheids zoekers toe aan de kleurenkiezer.
        colorPicker.addSaturationBar(saturationBar);
        colorPicker.addValueBar(valueBar);

        // Creëer een nieuw dialoogscherm.
        AlertDialog newPresetDialog = new AlertDialog.Builder(getActivity())
                // Zet de layout als layout voor het dialoogscherm.
                .setView(newPresetDialogView)
                // Creëer een 'OK' button en voeg functionaliteit.
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Creëer een nieuwe preset om toe te voegen aan de lijst.
                        Preset newPreset;
                        if(presetName.getText().toString().isEmpty()) {
                            // Wanneer er geen naam is opgegeven in het dialoogscherm verander de
                            // naam van de preset in 'Preset *nummer*'. Waarbij nummer de positie
                            // in de lijst is.
                            newPreset = new Preset("Preset " + (presets.size() + 1), colorPicker.getColor(), false);
                        } else {
                            // Wanneer er een naam is opgegeven in het dialoogscherm wordt dat de
                            // naam van de preset.
                            newPreset = new Preset(presetName.getText().toString(), colorPicker.getColor(), false);
                        }
                        // Voeg de preset toe aan de lijst.
                        presets.add(newPreset);
                        // Voeg de preset toe aan de database.
                        db.addPreset(newPreset);
                        // Vertel de adapter dat de lijst-data is aangepast en update de list view.
                        presetsListAdapter.notifyDataSetChanged();
                        Log.d("OK", "OK");
                    }
                })
                // Voeg een cancel button toe aan het dialoogscherm.
                .setNegativeButton("CANCEL", null)
                // Creëer het dialoogscherm en toon deze aan de gebruiker.
                .create();
        newPresetDialog.show();
    }

    /**
     * Het aanroepen van deze methode opent een dialoogscherm die hetzelfde is als bij het aanmaken
     * van een nieuwe preset (zie addPreset()) met één verschil: het veranderd de instellingen van
     * een preset die al bestaat en het voegt de optie toe om de preset te verwijderen.
     * @param position de positie van de te veranderen preset.
     */
    private void changePreset(final int position) {
        // Haal de te verandere preset op.
        final Preset preset = presets.get(position);

        // Creëer een layout voor het dialoogscherm.
        final View newPresetDialogView = getLayoutInflater(getArguments()).inflate(R.layout.dialog_fragment_new_preset, null);
        final EditText presetName = (EditText) newPresetDialogView.findViewById(R.id.dialog_fragment_new_preset_edit_text);

        // Creëer een nieuwe kleurenkiezer, verzadigings-zoeker en helderheid-zoeker.
        final ColorPicker colorPicker = (ColorPicker) newPresetDialogView.findViewById(R.id.color_picker);
        SaturationBar saturationBar = (SaturationBar) newPresetDialogView.findViewById(R.id.saturation_bar);
        ValueBar valueBar = (ValueBar) newPresetDialogView.findViewById(R.id.value_bar);

        colorPicker.setShowOldCenterColor(false);

        // Voeg de verzadiging en helderheids zoekers toe aan de kleurenkiezer.
        colorPicker.addSaturationBar(saturationBar);
        colorPicker.addValueBar(valueBar);

        // Verander de huidge kleur van de kleurkiezer naar de huidige kleur van de te veranderen
        // preset en de naam van de preset.
        colorPicker.setColor(preset.getColor());
        presetName.setText(preset.getName());

        // Creëer een nieuw dialoogscherm.
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
                // Creëer een button voor het verwijderen van de geselecteerde preset.
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

    /**
     * Het aanroepen van deze methode zorgt voor het verwijderen van de preset op de opgegeven
     * positie.
     * @param position de positie in de lijst van de te verwijderen preset.
     */
    private void removePreset(final int position) {
        // Creëer een nieuw dialoogscherm om de gebruiker te waarsschuwen dat hij op het punt staat
        // om een preset te verwijderen en vraag hem of hij dit zeker weet.
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


