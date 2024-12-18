package com.cosc3p97.newshub.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cosc3p97.newshub.Adapter;
import com.cosc3p97.newshub.dao.AppDatabase;
import com.cosc3p97.newshub.models.Model;
import com.cosc3p97.newshub.R;

import java.util.ArrayList;
import java.util.List;

public class BookmarkFragment extends Fragment {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<Model> modelList;
    private AppDatabase database;  // Room database reference

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bookmark, container, false);

        recyclerView = v.findViewById(R.id.bookmark_recycleView);
        modelList = new ArrayList<>();
        adapter = new Adapter(getContext(), modelList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        database = AppDatabase.getDatabase(getContext());  // Initialize the Room database

        loadBookmarks();  // Load bookmarks from the database

        return v;
    }

    /**
     * Load bookmarks from the Room database and update the RecyclerView.
     */
    private void loadBookmarks() {
        new Thread(() -> {
            List<Model> bookmarks = database.bookmarkDao().getAllBookmarks(); // Fetch all bookmarked articles
            if (bookmarks != null && !bookmarks.isEmpty()) {
                modelList.clear();
                modelList.addAll(bookmarks);
                // Update the UI on the main thread
                getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
            } else {
                // Show a Toast message on the UI thread if no bookmarks are found
                getActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "No bookmarks available", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}
