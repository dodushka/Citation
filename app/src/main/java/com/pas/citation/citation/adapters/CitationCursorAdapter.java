package com.pas.citation.citation.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pas.citation.citation.R;
import com.pas.citation.citation.adapters.CursorRecyclerAdapter;
import com.pas.citation.citation.database.DatabaseTables;


/**
 * Created by Paskal on 18.12.2014.
 */
public class CitationCursorAdapter extends CursorRecyclerAdapter<CitationCursorAdapter.CitationListRowHolder> {
    private int authorColumnIndex;
    private int citationColumnIndex;

    public CitationCursorAdapter(Cursor cursor) {
        super(cursor);
        setIndexes(cursor);
    }

    private void setIndexes(Cursor cursor) {
        if (cursor != null) {
            authorColumnIndex = cursor.getColumnIndex(DatabaseTables.Authors.AUTHOR);
            citationColumnIndex = cursor.getColumnIndex(DatabaseTables.Citation.TEXT);


        }
    }

    @Override
    public void onBindViewHolderCursor(CitationListRowHolder holder, Cursor cursor) {


        holder.authorName.setText(cursor.getString(authorColumnIndex));
        holder.citation.setText(cursor.getString(citationColumnIndex));

    }

    @Override
    public CitationListRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.citation_item, null);
        CitationListRowHolder mh = new CitationListRowHolder(v);
        return mh;

    }

    @Override
    public Cursor swapCursor(Cursor cursor) {
        setIndexes(cursor);
        return super.swapCursor(cursor);
    }

    public static class CitationListRowHolder extends RecyclerView.ViewHolder {
        protected TextView authorName;
        protected TextView citation;

        public CitationListRowHolder(View view) {
            super(view);
            this.authorName = (TextView) view.findViewById(R.id.author_name);
            this.citation = (TextView) view.findViewById(R.id.citation);
        }
    }

}