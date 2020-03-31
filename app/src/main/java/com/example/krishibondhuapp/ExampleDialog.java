package com.example.krishibondhuapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ExampleDialog extends AppCompatDialogFragment {
    ImageButton recodingstart,recodingstop;
    private static final long COUNTDOWN_IN_MILLIS = 60000;
    private CountDownTimer countDownTimer;
    private long timeLeftMillis;
    TextView textViewCountDown;
    ExampleDialogListener listener;

    String audioPath,resultUri;
    MediaRecorder mediaRecorder;
    boolean recodring = false;
    StorageReference mstorageReference;
    DatabaseReference mdatabaseReference;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        textViewCountDown = view.findViewById(R.id.countdown_id);
        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());

        //database reference
        mstorageReference = FirebaseStorage.getInstance().getReference();
        mdatabaseReference = FirebaseDatabase.getInstance().getReference().child("Audio");

        audioPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+myCurrentDateTime+".aac";
       // audioPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+myCurrentDateTime+".aac";

        //findView by id
        recodingstart = view.findViewById(R.id.start_record_id);
        recodingstop = view.findViewById(R.id.stop_record_id);
        recodingstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Start Recoding", Toast.LENGTH_SHORT).show();
                recodingstart.setVisibility(View.GONE);
                recodingstop.setVisibility(View.VISIBLE);
                recodring = true;
                recodingAudio();
            }
        });
        recodingstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Stop Recoding", Toast.LENGTH_SHORT).show();
                recodingstart.setVisibility(View.VISIBLE);
                recodingstop.setVisibility(View.GONE);
                stopAudio();

            }
        });

        builder.setView(view);
        builder.setTitle("Recoding");
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Cancel Recoding", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (recodring == true) {
                    uploadAudio();
                    //listener.applyText(resultUri);
                }else {
                    Toast.makeText(getContext(), "Start Recording First", Toast.LENGTH_SHORT).show();
                    recodring = false;
                }

            }
        });
        builder.setCancelable(false);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ExampleDialogListener) context;
    }

    public interface ExampleDialogListener{
        void applyText(Uri audioURL);
    }
    private void recodingAudio() {
        recodring = true;
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        mediaRecorder.setOutputFile(audioPath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//        File outputFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MediaMaster/Dub/");
//        Log.i(TAG, "startRecording: creating output file " + outputFolder.mkdirs());
//        File output = new File(outputFolder.getAbsolutePath()+"out" + new Date().getTime() + ".3gpp");
//        mediaRecorder.setOutputFile(output.getAbsolutePath());
        mediaRecorder.setMaxDuration(60000);
        try {

            mediaRecorder.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
        mediaRecorder.start();
        //Time countdown ....
        timeLeftMillis = COUNTDOWN_IN_MILLIS;
        startCountdown();
    }
    public void stopAudio(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        stopCoundow();

    }

    private void uploadAudio() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        final Uri uri = Uri.fromFile(new File(audioPath));
        StorageReference filepath = mstorageReference.child("Audio").child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri url = uriTask.getResult();
                resultUri = url.toString();
                listener.applyText(url);
//                    Intent intent = new Intent(getContext(),MainActivity.class);
//                    intent.putExtra("AudioUrl",resultUri);
//                    startActivity(intent);
                progressDialog.dismiss();

            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeftMillis,1000) {
            @Override
            public void onTick(long lon) {
                timeLeftMillis = lon;
                updateCoundown();
            }

            @Override
            public void onFinish() {
                timeLeftMillis = 0;
                recodingstop.setVisibility(View.GONE);
                recodingstart.setVisibility(View.VISIBLE);
                textViewCountDown.setText("");
                uploadAudio();

            }
        }.start();
    }
    private void updateCoundown() {
        int min = (int)(timeLeftMillis / 1000) / 60;
        int sec = (int)(timeLeftMillis / 1000) % 60;
        String timeFor = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        textViewCountDown.setText(timeFor);
    }
    private void stopCoundow(){
        countDownTimer.cancel();
    }
}
