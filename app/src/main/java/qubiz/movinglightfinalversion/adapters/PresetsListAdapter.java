package qubiz.movinglightfinalversion.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import qubiz.movinglightfinalversion.Preset;
import qubiz.movinglightfinalversion.R;
import qubiz.movinglightfinalversion.database.PresetSQLiteHelper;

/**
 * Created by Mathijs on 18/06/2015.
 */
public class PresetsListAdapter extends BaseAdapter {

    private Context context;
    private List<Preset> presets;

    private int selectedPosition = 0;

    private PresetSQLiteHelper db;

    public PresetsListAdapter(Context context, List<Preset> presets) {
        this.context = context;
        this.presets = presets;
        this.db = new PresetSQLiteHelper(context);
    }

    @Override
    public int getCount() {
        return presets.size();
    }

    @Override
    public Object getItem(int position) {
        return presets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listView = inflater.inflate(R.layout.presets_list_item, parent, false);

        TextView textView = (TextView) listView.findViewById(R.id.presets_list_item_text_view);
        FrameLayout colorFrame = (FrameLayout) listView.findViewById(R.id.preset_color_frame);
        final AppCompatRadioButton radioButton = (AppCompatRadioButton) listView.findViewById(R.id.preset_enabled_radio_button);

        final Preset preset = presets.get(position);

        textView.setText(preset.getName());
        colorFrame.setBackgroundColor(preset.getColor());
        radioButton.setChecked(preset.isEnabled());
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presets.get(position).isEnabled()) {
                    presets.get(position).setEnabled(false);
                    db.updatePreset(presets.get(position));
                } else {
                    presets.get(position).setEnabled(true);
                    db.updatePreset(presets.get(position));
                    for (int i = 0; i < presets.size(); i++) {
                        if (i != position) {
                            presets.get(i).setEnabled(false);
                            db.updatePreset(presets.get(i));
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
        return listView;
    }
}
