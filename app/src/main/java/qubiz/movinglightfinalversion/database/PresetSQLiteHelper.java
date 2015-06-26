package qubiz.movinglightfinalversion.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import qubiz.movinglightfinalversion.presets.Preset;

/**
 * Author: Mathijs de Groot (S1609483)
 *
 * Info:
 * De class PresetSQLiteHelper is de helper class voor het lezen en schrijven van en naar de
 * database.
 */
public class PresetSQLiteHelper extends SQLiteOpenHelper {

    /**
     * DATABASE NAME
     */
    private static final String DATABASE_NAME = "PresetsDB";

    /**
     * DATABASE VERSION
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * TABEL NAAM
     */
    private static final String TABLE_PRESETS = "presets";

    /**
     * KEYS VOOR HET VERKRIJGEN VAN DE ATTRIBUTEN VAN DE PRESETS UIT DE DATABASE
     */
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_COLOR = "color";
    private static final String KEY_ENABLED = "enabled";

    /**
     * DE
     */
    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_COLOR, KEY_ENABLED};

    public PresetSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRESET_TABLE = "CREATE TABLE presets ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "color INTEGER, " +
                "enabled INTEGER )";

        db.execSQL(CREATE_PRESET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS books");
        this.onCreate(db);
    }

    /**
     * Voegt een nieuwe preset toe aan de database.
     * @param preset de toe te voegen preset.
     */
    public void addPreset(Preset preset) {
        Log.d("addPreset", preset.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, preset.getName());
        values.put(KEY_COLOR, preset.getColor());
        values.put(KEY_ENABLED, ((preset.isEnabled()) ? 1 : 0));

        db.insert(TABLE_PRESETS, null, values);

        db.close();
    }

    /**
     * Verkrijgt een preset uit de database met opgegeven ID.
     * @param id het id van de te verkrijgen preset.
     * @return de preset.
     */
    public Preset getPreset(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRESETS,
                COLUMNS,
                " id = ?",
                null,
                null,
                null,
                null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        Preset preset = new Preset("", 1, false);
        preset.setId(Integer.parseInt(cursor.getString(0)));
        preset.setName(cursor.getString(1));
        preset.setColor(Integer.parseInt(cursor.getString(2)));
        preset.setEnabled((Integer.parseInt(cursor.getString(3)) == 1));

        Log.d("getPreset(" + id + ")", preset.toString());

        return preset;
    }

    /**
     * Verkrijgt een lijst met alle presets die op dit moment in de database zitten.
     * @return de lijst met presets.
     */
    public List<Preset> getAllPresets() {
        List<Preset> presets = new ArrayList<Preset>();

        String query = "SELECT * FROM " + TABLE_PRESETS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Preset preset = null;
        if(cursor.moveToFirst()) {
            do {
                preset = new Preset("", 1, false);
                preset.setId(Integer.parseInt(cursor.getString(0)));
                preset.setName(cursor.getString(1));
                preset.setColor(Integer.parseInt(cursor.getString(2)));
                preset.setEnabled((Integer.parseInt(cursor.getString(3)) == 1));

                presets.add(preset);
            } while(cursor.moveToNext());
        }

        Log.d("getAllPresets", presets.toString());

        return presets;
    }

    /**
     * Update de attributen van een bestaande preset in de database.
     * @param preset de preset die moet worden geupdate.
     * @return het aantal items die geupdate zijn.
     */
    public int updatePreset(Preset preset) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, preset.getName());
        values.put(KEY_COLOR, preset.getColor());
        values.put(KEY_ENABLED, ((preset.isEnabled()) ? 1 : 0));

        int i = db.update(TABLE_PRESETS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(preset.getId()) });

        db.close();

        Log.d("updatePreset", preset.toString());
        return i;
    }

    /**
     * Verwijdert een preset uit de database.
     * @param preset de te verwijderen preset.
     */
    public void deletePreset(Preset preset) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PRESETS, KEY_ID + " = ?",
                new String[] { String.valueOf(preset.getId()) });

        db.close();

        Log.d("deletePreset", preset.toString());
    }
}
