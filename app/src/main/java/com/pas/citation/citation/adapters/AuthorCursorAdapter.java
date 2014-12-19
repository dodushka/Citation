package com.pas.citation.citation.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pas.citation.citation.database.DatabaseTables;
import com.pas.citation.citation.R;
import com.squareup.picasso.Picasso;

/**
 * The AuthorCursorAdapter for fetching data to RecyclerView
 * Created by Pascal on 18.12.2014.
 */
public class AuthorCursorAdapter extends CursorRecyclerAdapter<AuthorCursorAdapter.AuthorListRowHolder> {
    private Context cxt;
    private  int authorColumnIndex ;
    private  int imageColumnIndex ;

    public AuthorCursorAdapter(Cursor cursor,Context context) {
        super(cursor);
        cxt=context;
        setIndexes(cursor);
    }

    private void setIndexes(Cursor cursor) {
        if(cursor!=null){
        authorColumnIndex=cursor.getColumnIndex(DatabaseTables.Authors.AUTHOR);
        imageColumnIndex=cursor.getColumnIndex(DatabaseTables.Authors.IMAGE_URL);


        }
    }

    @Override
    public void onBindViewHolderCursor(AuthorListRowHolder holder, Cursor cursor) {
        Picasso.with(cxt)
                .load(cursor.getString(imageColumnIndex))
                .into(holder.thumbnail);

        holder.title.setText(cursor.getString(authorColumnIndex));

    }

    @Override
    public AuthorListRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_item, null);
        AuthorListRowHolder mHolder = new AuthorListRowHolder(v);
        return mHolder;

    }
@Override
public Cursor swapCursor(Cursor cursor){
    setIndexes(cursor);
    return super.swapCursor(cursor);
}
    public static class AuthorListRowHolder extends RecyclerView.ViewHolder {
        private final ImageView thumbnail;
        private final TextView title;

        public AuthorListRowHolder(View view) {
            super(view);
            this.thumbnail = (ImageView) view.findViewById(R.id.author_icon);
            this.title = (TextView) view.findViewById(R.id.author);
        }
    }

    }
