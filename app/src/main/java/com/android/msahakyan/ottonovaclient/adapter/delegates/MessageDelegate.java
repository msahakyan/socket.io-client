package com.android.msahakyan.ottonovaclient.adapter.delegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.msahakyan.ottonovaclient.R;
import com.android.msahakyan.ottonovaclient.model.AdapterItem;
import com.android.msahakyan.ottonovaclient.model.Message;
import com.android.msahakyan.ottonovaclient.util.ICommandCommunicationListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author msahakyan
 */
public class MessageDelegate extends BaseAdapterDelegate {

    public MessageDelegate(Context ctx, ICommandCommunicationListener listener) {
        super(ctx, TYPE_MESSAGE, listener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new MessageViewHolder((ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.list_item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<AdapterItem> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        Message message = (Message) items.get(position);
        MessageViewHolder viewHolder = (MessageViewHolder) holder;
        viewHolder.author.setText(message.getAuthor());
        viewHolder.message.setText(message.getMessage());
    }

    @Override
    public boolean isForViewType(@NonNull List<AdapterItem> items, int position) {
        return items.get(position) instanceof Message && super.isForViewType(items, position);
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.message)
        TextView message;

        MessageViewHolder(ViewGroup view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
