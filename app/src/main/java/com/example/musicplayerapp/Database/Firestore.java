package com.example.musicplayerapp.Database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayerapp.Config;
import com.example.musicplayerapp.Format.Format;
import com.example.musicplayerapp.MainActivity;
import com.example.musicplayerapp.Entity.MusicFiles;
import com.example.musicplayerapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Firestore extends AppCompatActivity {

    public static ArrayList<MusicFiles> musicFilesOnline;
    public static FirestoreRecyclerOptions<MusicFiles> options;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private FirestoreAdapter adapter;

    private ProgressBar progressBarLoadingMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore);

        initView();

        progressBarLoadingMusic.setVisibility(View.GONE);
        Query query = firebaseFirestore.collection("Music");

        query.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<MusicFiles> song = value.toObjects(MusicFiles.class);
                musicFilesOnline.addAll(song);
            }
        });

        options = new FirestoreRecyclerOptions.Builder<MusicFiles>()
                .setLifecycleOwner(this)
                .setQuery(query, MusicFiles.class)
                .build();

        adapter = new FirestoreAdapter(options, this, progressBarLoadingMusic);

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

        //Log.d("firebase", "onCreate: " + musicFilesOnline.toString());

        /*firebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });*/
    }

    /*private String formatTime(int mCurrentPosition) {
        String minutes = String.valueOf(mCurrentPosition / 60);
        String second = String.valueOf(mCurrentPosition % 60);

        if (second.length() == 1) {
            return minutes + ":0" + second;
        }

        return minutes + ":" + second;
    }*/

    @Override
    protected void onResume() {
        super.onResume();

        progressBarLoadingMusic.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void initView() {
        musicFilesOnline = new ArrayList<>();
        mFirestoreList = findViewById(R.id.firestore_list);
        progressBarLoadingMusic = findViewById(R.id.progressBarLoadingMusic);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Config.playOnline = true;
    }

    /*public FirestoreRecyclerOptions getSongsOnline(LifecycleOwner lifecycleOwner) {
        FirebaseFirestore firebaseFirestore;

        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query = firebaseFirestore.collection("Music");

        final FirestoreRecyclerOptions<MusicFiles> options = new FirestoreRecyclerOptions.Builder<MusicFiles>()
                .setLifecycleOwner(lifecycleOwner)
                .setQuery(query, MusicFiles.class)
                .build();

        return options;
    }*/

    /*public static ArrayList<MusicFiles> getListSongOnl(ObservableSnapshotArray<MusicFiles> observableSnapshotArray) {
        ArrayList<MusicFiles> result = new ArrayList<>();
        for (MusicFiles musicFiles : observableSnapshotArray) {
            result.add(musicFiles);
        }
        return result;
    }*/
}