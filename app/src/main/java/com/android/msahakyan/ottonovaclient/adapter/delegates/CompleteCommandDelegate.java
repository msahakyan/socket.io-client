package com.android.msahakyan.ottonovaclient.adapter.delegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.msahakyan.ottonovaclient.R;
import com.android.msahakyan.ottonovaclient.model.AdapterItem;
import com.android.msahakyan.ottonovaclient.model.Command;
import com.android.msahakyan.ottonovaclient.model.ServerCommand;
import com.android.msahakyan.ottonovaclient.model.data.CompleteData;
import com.android.msahakyan.ottonovaclient.util.ICommandCommunicationListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author msahakyan
 */
public class CompleteCommandDelegate extends BaseAdapterDelegate {

    public CompleteCommandDelegate(Context ctx, ICommandCommunicationListener listener) {
        super(ctx, TYPE_COMMAND_COMPLETE, listener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new CompleteCommandViewHolder((ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.list_item_complete, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<AdapterItem> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        Command command = (Command) items.get(position);
        CompleteCommandViewHolder viewHolder = (CompleteCommandViewHolder) holder;

        String[] suggestions = ((CompleteData) command.getCommand().getData()).getSuggestions();

        viewHolder.buttonOne.setText(suggestions[0]);
        viewHolder.buttonTwo.setText(suggestions[1]);

        viewHolder.buttonOne.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCheckmarkSelected(viewHolder.buttonOne.getText().toString(), holder.getAdapterPosition());
            }
        });
        viewHolder.buttonTwo.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCheckmarkSelected(viewHolder.buttonTwo.getText().toString(), holder.getAdapterPosition());
            }
        });
    }

    @Override
    public boolean isForViewType(@NonNull List<AdapterItem> items, int position) {
        if (!(items.get(position) instanceof Command)) {
            return false;
        }
        Command command = (Command) items.get(position);
        return command.getCommand().getType().equals(ServerCommand.TYPE_COMPLETE) && super.isForViewType(items, position);
    }

    static class CompleteCommandViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.button_one)
        Button buttonOne;
        @Bind(R.id.button_two)
        TextView buttonTwo;

        CompleteCommandViewHolder(ViewGroup view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
