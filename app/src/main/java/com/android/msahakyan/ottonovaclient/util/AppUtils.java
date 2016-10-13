package com.android.msahakyan.ottonovaclient.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * @author msahakyan
 */

public class AppUtils {

    // Date formatter constants
    private static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String DAY_OF_WEEK = "EEEE";
    private static final String DAY_OF_WEEK_SHORT = "EEE";

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    private static void hideKeyboard(@NonNull Context context, @Nullable View view) {
        if (view == null) {
            return;
        }
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideKeyboard(Activity activity) {
        hideKeyboard(activity, activity.getCurrentFocus());
    }

    public static String[] getWeekDaysWithStartedDate(String datetime) {

        String dayOfWeak = getDayOfWeek(datetime);
        String[] daysOfWeekShort = getDayNamesOfWeek(true);
        String[] noEmptyDaysOfWeek = cleanEmptyValues(daysOfWeekShort);

        int startPosition = 0;
        for (String day : noEmptyDaysOfWeek) {
            if (day.equals(dayOfWeak)) {
                break;
            }
            startPosition++;
        }
        ArrayRotator.leftRotate(noEmptyDaysOfWeek, startPosition, noEmptyDaysOfWeek.length);

        return noEmptyDaysOfWeek;
    }

    private static Date formatDateISO8601(String datetime) {
        DateFormat format = new SimpleDateFormat(ISO8601, Locale.getDefault());

        Date date = null;
        try {
            date = format.parse(datetime);
        } catch (ParseException e) {
            Timber.e(e, "Date Format exception");
        }
        return date;
    }

    private static String getDayOfWeek(String datetime) {
        Date date = formatDateISO8601(datetime);
        DateFormat dayOfWeekFormat = new SimpleDateFormat(DAY_OF_WEEK_SHORT, Locale.getDefault());
        return dayOfWeekFormat.format(date);
    }

    private static String[] getDayNamesOfWeek(boolean shortened) {
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
        return shortened ? symbols.getShortWeekdays() : symbols.getWeekdays();
    }

    private static String[] cleanEmptyValues(final String[] source) {
        List<String> list = new ArrayList<>(Arrays.asList(source));
        list.remove("");
        return list.toArray(new String[list.size()]);
    }

    private static class ArrayRotator {
        //Function to left rotate String arr[] of size n by d positions
        static void leftRotate(String arr[], int d, int n) {
            for (int i = 0; i < d; i++) {
                leftRotateByOne(arr, n);
            }
        }

        static void leftRotateByOne(String arr[], int n) {
            int i;
            String temp;
            temp = arr[0];
            for (i = 0; i < n - 1; i++) {
                arr[i] = arr[i + 1];
            }
            arr[i] = temp;
        }
    }

    public static Locale getDeviceLocale() {
        return Locale.getDefault();
    }

    public static int dpToPixels(Resources resources, int pixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, resources.getDisplayMetrics());
    }

    public static Point getScreenSizeInPixels(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }
}
