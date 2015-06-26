package qubiz.movinglightfinalversion.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import qubiz.movinglightfinalversion.presets.Preset;
import qubiz.movinglightfinalversion.R;
import qubiz.movinglightfinalversion.database.PresetSQLiteHelper;

/**
 * Author: Mathijs de Groot (S1609483)
 *
 * Info:
 * De class PresetListAdapter is de brug tussen de ListView en de data die in de lijst moet komen
 * te staan. In dit geval gaat het om een lijst met presets die moet worden getoond op het scherm.
 *
 * De class maakt gebruik van een SQLite database waarin de data voor de presets worden opgeslagen
 * zodat de gemaakte presets niet verloren gaan wanneer de app wordt afgesloten.
 */
public class PresetsListAdapter extends BaseAdapter {

    /**
     * De context van de applicatie.
     */
    private Context context;

    /**
     * De lijst met presets.
     */
    private List<Preset> presets;

    /**
     * De database helper voor het lezen uit en schrijven naar de database.
     */
    private PresetSQLiteHelper db;

    /**
     * Constructor voor het maken van een nieuwe PresetListAdapter.
     * @param context de context van de applicatie waarin de adapter wordt gebruikt.
     * @param presets de data voor de ListView.
     */
    public PresetsListAdapter(Context context, List<Preset> presets) {
        this.context = context;
        this.presets = presets;
        this.db = new PresetSQLiteHelper(context);
    }

    /**
     * Geeft het aantal presets dat op dit moment aanwezig zijn in de ListView.
     * @return het aantal presets in de lijst.
     */
    @Override
    public int getCount() {
        return presets.size();
    }

    /**
     * Verkrijgt de data van een preset op een bepaalde positie in de ListView.
     * @param position de positie in de lijst.
     * @return de preset data.
     */
    @Override
    public Object getItem(int position) {
        return presets.get(position);
    }

    /**
     * Verkrijgt het ID van het item op de opgegeven positie.
     * @param position
     * @return het ID.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Creëert de view voor een item in de lijst. Deze methode wordt automatisch aangeroepen bij het
     * maken van de lijst.
     * @return de gecreëerde view.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.presets_list_item, parent, false);

        TextView textView = (TextView) view.findViewById(R.id.presets_list_item_text_view);
        FrameLayout colorFrame = (FrameLayout) view.findViewById(R.id.preset_color_frame);
        final AppCompatRadioButton radioButton = (AppCompatRadioButton) view.findViewById(R.id.preset_enabled_radio_button);

        final Preset preset = presets.get(position);

        textView.setText(preset.getName());
        colorFrame.setBackgroundColor(preset.getColor());
        radioButton.setChecked(preset.isEnabled());

        // Deze onClickListener zorgt ervoor dat er slechts één radiobutton in de presetlijst
        // gelijktijdig actief kan zijn.
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presets.get(position).isEnabled()) {
                    // De radio button was aan op het moment van klikken --> zet de radio button uit.
                    presets.get(position).setEnabled(false);
                    db.updatePreset(presets.get(position));
                } else {
                    // De radio button was uit op het moment van klikken --> zet de radio button aan
                    // en zet alle andere radio buttons uit.
                    presets.get(position).setEnabled(true);
                    db.updatePreset(presets.get(position));
                    for (int i = 0; i < presets.size(); i++) {
                        if (i != position) {
                            presets.get(i).setEnabled(false);
                            db.updatePreset(presets.get(i));
                        }
                    }
                }

                // Vertel de adapter dat de data in de lijst is aangepast en update de lijst.
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
