package com.cooleyah.tugasmanager;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class GuiHelper {

    static String getInputFromMandatoryEditText(View view, int id) throws IllegalArgumentException {
        EditText editText = view.findViewById(id);

        String input = editText.getText().toString();
        if (input.replaceAll("\\s+", "").replaceAll("\\s", "").isEmpty()) {
            handleEmptyMandatoryEditText(view, id);
            throw new IllegalArgumentException();
        } else {
            return input;
        }
    }


    private static void handleEmptyMandatoryEditText(View view, int id) {
        handleEmptyMandatoryEditText(view, id, view.getContext().getResources().getString(R.string.string_mandatory_field));
    }

    private static void handleEmptyMandatoryEditText(View view, int id, String message) {
        EditText editText = view.findViewById(id);
        handleEmptyMandatoryEditText(editText, message);
    }

    private static void handleEmptyMandatoryEditText(EditText editText) {
        handleEmptyMandatoryEditText(editText, editText.getContext().getResources().getString(R.string.string_mandatory_field));
    }

    private static void handleEmptyMandatoryEditText(EditText editText, String message) {
        editText.setText("");
        editText.setHint(message);
        editText.setHintTextColor(Color.RED);
    }

    public static String extractGuiString(JadwalPelajaran subject) {
        return subject.getNamaPelajaran() + " - " + subject.getJamAwal() + " Sampai " + subject.getJamAkhir();
    }

    public static void fillListViewFromArray(View view, int id, String[] content) {
        ListView listView = view.findViewById(id);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, content);

        listView.setAdapter(adapter);
    }
}
