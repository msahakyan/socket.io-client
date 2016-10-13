package com.android.msahakyan.ottonovaclient.util;

/**
 * @author msahakyan
 *         <p>
 *         Simple interface for firing corresponding callback events when user selects one of the
 *         command values received from the server
 */
public interface ICommandCommunicationListener {

    /**
     * Fires on selection of new rate value
     *
     * @param value    The new value selected by the user
     * @param position The clicked item position
     */
    void onRateEventUpdated(int value, int position);

    /**
     * Fires on selection of day of week
     *
     * @param day      The name of the day selected by the user
     * @param position The clicked item position
     */
    void onWeekDaySelected(String day, int position);

    /**
     * Fires on selection of the checkmark value (yes/no)
     *
     * @param value    The name of the value selected by the user
     * @param position The clicked item position
     */
    void onCheckmarkSelected(String value, int position);
}
