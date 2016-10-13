package com.android.msahakyan.ottonovaclient.adapter;

import android.content.Context;

import com.android.msahakyan.ottonovaclient.adapter.delegates.CompleteCommandDelegate;
import com.android.msahakyan.ottonovaclient.adapter.delegates.DateCommandDelegate;
import com.android.msahakyan.ottonovaclient.adapter.delegates.MapCommandDelegate;
import com.android.msahakyan.ottonovaclient.adapter.delegates.MessageDelegate;
import com.android.msahakyan.ottonovaclient.adapter.delegates.RateCommandDelegate;
import com.android.msahakyan.ottonovaclient.model.AdapterItem;
import com.android.msahakyan.ottonovaclient.util.ICommandCommunicationListener;

import java.util.Arrays;
import java.util.List;

/**
 * @author msahakyan
 */

public class ChatAdapter extends BaseListAdapter<AdapterItem> {

    public ChatAdapter(Context context, List<AdapterItem> items, ICommandCommunicationListener listener) {
        super(context, items);

        setAdapterDelegates(Arrays.asList(
            new MessageDelegate(context, listener),
            new CompleteCommandDelegate(context, listener),
            new RateCommandDelegate(context, listener),
            new DateCommandDelegate(context, listener),
            new MapCommandDelegate(context, listener)
        ));
    }
}