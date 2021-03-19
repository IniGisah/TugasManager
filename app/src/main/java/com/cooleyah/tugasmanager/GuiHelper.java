package com.cooleyah.tugasmanager;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cooleyah.tugasmanager.db.Settings;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GuiHelper {

    private static String harinow;

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

    static GregorianCalendar getDateFromMandatoryButton(View view, int id) throws IllegalArgumentException {
        Button button = view.findViewById(id);

        String str = button.getText().toString();
        str = str.replaceAll(":", "-");
        str = str.replaceAll("\\.", "-");
        str = str.replaceAll(",", "-");
        str = str.replaceAll("/", "-");

        String[] date;
        if (str.contains("-")) {
            date = str.split("-");

            if (date.length != 3) {
                handleEmptyMandatoryButton(view, id);
                throw new IllegalArgumentException("date must be contain three elements");
            }
        } else {
            handleEmptyMandatoryButton(view, id);
            throw new IllegalArgumentException("unknown date divider");
        }

        try {
            return (GregorianCalendar) parseCalendarFromStringArray(date, view.getContext());
        } catch (IllegalArgumentException ex) {
            handleEmptyMandatoryButton(view, id,
                    view.getContext().getResources().getString(R.string.string_date_must_comply_with) +
                            " " + Settings.getInstance(view.getContext()).getActiveDateFormat());
            throw ex;
        }
    }

    private static Calendar parseCalendarFromStringArray(String[] date, Context context) throws IllegalArgumentException {
        Calendar calendar;
        String activeDateFormat = Settings.getInstance(context).getActiveDateFormat();
        switch (activeDateFormat) {
            case Settings.DATE_FORMAT_DDMMYYYY:
                if (date[0].length() <= 2 && date[1].length() <= 2 && date[2].length() == 4) {
                    calendar = new GregorianCalendar(
                            Integer.parseInt(date[2]),
                            Integer.parseInt(date[1]) - 1,
                            Integer.parseInt(date[0])
                    );
                } else {
                    throw new IllegalArgumentException(Arrays.toString(date) + " is no valid array for DATE_FORMAT_DDMMYYYY");
                }
                break;
            case Settings.DATE_FORMAT_MMDDYYYY:
                if (date[0].length() <= 2 && date[1].length() <= 2 && date[2].length() == 4) {
                    calendar = new GregorianCalendar(
                            Integer.parseInt(date[2]),
                            Integer.parseInt(date[0]) - 1,
                            Integer.parseInt(date[1])
                    );
                } else {
                    throw new IllegalArgumentException(Arrays.toString(date) + " is no valid array for DATE_FORMAT_MMDDYYYY");
                }
                break;
            case Settings.DATE_FORMAT_YYYYMMDD:
                if (date[0].length() == 4 && date[1].length() <= 2 && date[2].length() <= 2) {
                    calendar = new GregorianCalendar(
                            Integer.parseInt(date[0]),
                            Integer.parseInt(date[1]) - 1,
                            Integer.parseInt(date[2])
                    );
                } else {
                    throw new IllegalArgumentException(Arrays.toString(date) + " is no valid array for DATE_FORMAT_YYYYMMDD");
                }
                break;
            default:
                throw new IllegalArgumentException(Settings.getInstance(context).getActiveDateFormat() + " is not supported");
        }
        return calendar;
    }

    private static void handleEmptyMandatoryButton(View view, int id) {
        handleEmptyMandatoryButton(view, id, view.getContext().getResources().getString(R.string.string_mandatory_field));
    }

    private static void handleEmptyMandatoryButton(View view, int id, String message) {
        Button button = view.findViewById(id);
        handleEmptyMandatoryButton(button, message);
    }

    private static void handleEmptyMandatoryButton(Button button, String message) {
        button.setText("");
        button.setHint(message);
        button.setHintTextColor(Color.RED);
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

    public static String extractjudul(Catatan catatan) {
        return catatan.getJudul();
    }

    public static String extractjudul(Tugas tugas) {
        return tugas.getDeskripsi();
    }

    public static String extractcatatat(Tugas tugas) {
        return tugas.getCatatan();
    }

    public static int extractdeadline(Tugas tugas, int tglsel) {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        Calendar tgl = new GregorianCalendar();
        tgl.set(year,month,tglsel); //hari ini, 18
        Calendar detlen = tugas.getDeadline(); //detlen, 20

        return detlen.compareTo(tgl); //true
    }

    public static String extractGuiString(JadwalPelajaran subject) {
        return subject.getNamaPelajaran() + " - " + subject.getJamAwal() + " Sampai " + subject.getJamAkhir();
    }

    public static String extractjudul(JadwalPelajaran subject) {
        return subject.getNamaPelajaran();
    }

    public static String extracthari(JadwalPelajaran subject) {
        return subject.getHari();
    }

    public static int extractjamawal(JadwalPelajaran subject) {
        return subject.getJamAwalint();
    }

    public static int extractmenitawal(JadwalPelajaran subject) {
        return subject.getMenitAwalint();
    }

    public static int extractjamnow() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public static int extractmenitnow() {
        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get(Calendar.MINUTE);
        return minute;
    }

    public static String converthari(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                harinow = "Minggu";
                break;
            case 2:
                harinow = "Senin";
                break;
            case 3:
                harinow = "Selasa";
                break;
            case 4:
                harinow = "Rabu";
                break;
            case 5:
                harinow = "Kamis";
                break;
            case 6:
                harinow = "Jumat";
                break;
            case 7:
                harinow = "Sabtu";
                break;
        }
        return harinow;
    }

    public static String extractwaktu(JadwalPelajaran subject) {
        return subject.getJamAwal() + " sampai " + subject.getJamAkhir();
    }

    public static String extractwaktuawal(JadwalPelajaran subject) {
        return subject.getJamAwal();
    }

    public static String extractGuiString(Tugas homework, Context context) {
        String dateString = extractGuiString(homework.getDeadline(), false, context);

        return homework.getSubject().getNamaPelajaran() + " - " + dateString;
    }

    public static String extractDateGuiString(Tugas homework, Context context) {
        String dateString = extractGuiString(homework.getDeadline(), false, context);

        return dateString;
    }

    public static String extractPelGuiString(Tugas homework, Context context) {
        String dateString = extractGuiString(homework.getDeadline(), false, context);

        return homework.getSubject().getNamaPelajaran();
    }

    static String extractGuiString(Calendar calendar, boolean isTimeOnly, Context context) {
        String res = "";
        if (isTimeOnly) {
            if (calendar.get(Calendar.HOUR_OF_DAY) > 9 && calendar.get(Calendar.MINUTE) > 9) {
                res = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
            } else if (calendar.get(Calendar.HOUR_OF_DAY) <= 9 && calendar.get(Calendar.MINUTE) > 9) {
                res = "0" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
            } else if (calendar.get(Calendar.HOUR_OF_DAY) > 9 && calendar.get(Calendar.MINUTE) <= 9) {
                res = calendar.get(Calendar.HOUR_OF_DAY) + ":" + "0" + calendar.get(Calendar.MINUTE);
            } else {
                res = "0" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + "0" + calendar.get(Calendar.MINUTE);
            }
        } else {
            switch (Settings.getInstance(context).getActiveDateFormat()) {
                case Settings.DATE_FORMAT_DDMMYYYY:
                    res = calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + "." +
                            calendar.get(Calendar.YEAR);
                    break;
                case Settings.DATE_FORMAT_MMDDYYYY:
                    res = (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DAY_OF_MONTH) + "." +
                            calendar.get(Calendar.YEAR);
                    break;
                case Settings.DATE_FORMAT_YYYYMMDD:
                    res = calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1) + "." +
                            calendar.get(Calendar.DAY_OF_MONTH);
                    break;
            }
        }
        return res;
    }

    public static void fillListViewFromArray(View view, int id, String[] content) {
        ListView listView = view.findViewById(id);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, content);

        listView.setAdapter(adapter);
    }

    static void setTextToTextView(View view, int id, String text) {
        TextView textView = view.findViewById(id);
        textView.setText(text);
    }

    public static void setVisibility(View view, int id, int visibility) {
        View v = view.findViewById(id);
        v.setVisibility(visibility);
    }

    static Spinner fillSpinnerFromArray(View view, int id, String[] content) {
        Spinner spinner = view.findViewById(id);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, content);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        return spinner;
    }

    public static void defineButtonOnClickListener(View view, int id, View.OnClickListener onClickListener) {
        Button b = view.findViewById(id);
        b.setOnClickListener(onClickListener);
    }

    public static void setColorToButton(View view, int id, float colorId) {
        Button b = view.findViewById(id);
        b.setAlpha(colorId);
    }

    public static boolean extractisDone(Tugas tugas) {
        return tugas.isDone();
    }
}
