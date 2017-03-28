package ayy.shopping.entering;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import ayy.shopping.R;
import ayy.shopping.cashdisplay.CashDisplay;
import ayy.shopping.textreading.OcrCaptureActivity;

public class EnterAmountActivity extends AppCompatActivity {

    // A TextToSpeech engine for speaking a String value.
    private TextToSpeech tts;

    private final int REQ_CODE_SPEECH_OUTPUT = 143;

    //Underlining Euro and Cent text
    EditText euroTxt, centTxt;
    //Declaring variables
    ImageView cameraBtn;
    Button clearBtn, submitBtn;
    Button enterAmount2;
    ImageView speakBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_amount);



        euroTxt = (EditText)findViewById(R.id.euroTxt);
        centTxt = (EditText)findViewById(R.id.centTxt);
        centTxt.setPaintFlags(centTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        euroTxt.setPaintFlags(euroTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //This lets the user press the enter amount button and it will let the user know
        //Exactly what they have to do with audio
        enterAmount2 = (Button)findViewById(R.id.enterAmount2);
        enterAmount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Text to speech on button press
                tts = new TextToSpeech(EnterAmountActivity.this, new TextToSpeech.OnInitListener() {
                    //Getting text from the button and storing it in s
                    String s=enterAmount2.getText().toString();
                    @Override
                    public void onInit(int status) {
                        //Instead of using the text in the button we are using our own message
                        tts.speak("Please Enter Your Total Below", TextToSpeech.QUEUE_FLUSH, null);
                    }
                });
            }
        });

        submitBtn = (Button)findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String euroStr = euroTxt.getText().toString();
                String centStr = centTxt.getText().toString();
                if (euroStr.matches("")){
                    //Text to speech on button press
                    tts = new TextToSpeech(EnterAmountActivity.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            //Instead of using the text in the button we are using our own message
                            tts.speak("You Did not enter anything", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    });
                }
                else {
                    Intent intent  = new Intent(EnterAmountActivity.this, CashDisplay.class);
                    int euro = Integer.parseInt(euroStr);
                    int cent = Integer.parseInt(centStr);
                    intent.putExtra("euromsg", euro);
                    intent.putExtra("centmsg", cent);
                    startActivity(intent);
                }
            }
        });

        /**
         * This allows the camera button to be pressed which will bring the user back to
         * the previous screen so they can take a picture of their total.
         * This will let them know that they are going back to take a picture of their total
         * and where the total is located
         * **/
        cameraBtn = (ImageView) findViewById(R.id.captureBtn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Text to speech on button press
                tts = new TextToSpeech(EnterAmountActivity.this, new TextToSpeech.OnInitListener() {
                    //Getting text from the button and storing it in s
                    String s=enterAmount2.getText().toString();
                    @Override
                    public void onInit(int status) {
                        //Instead of using the text in the button we are using our own message
                        tts.speak("Take a picture of your total." +
                                "This is on the cash register", TextToSpeech.QUEUE_FLUSH, null);
                    }
                });
                Intent intent = new Intent(EnterAmountActivity.this, OcrCaptureActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /**
         * This allows the user to clear the input.
         * It will prompted the user with a dialog box asking if they want to delete the data
         * If the user presses no then it will just continue as normal.
         * If the user presses yes to delete the input then it will tts them saying the input
         * is being deleted. It will just open the same activity and kill the old one**/
        clearBtn = (Button)findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EnterAmountActivity.this);
                //TextToSpeech letting the user know what each option does
                tts = new TextToSpeech(EnterAmountActivity.this,
                        new TextToSpeech.OnInitListener() {
                            String s2 = enterAmount2.getText().toString();
                            @Override
                            public void onInit(int status) {
                                tts.speak("To delete Press Yes." +
                                        "To go back pres No", TextToSpeech.QUEUE_FLUSH, null);
                            }
                        });
                //Alert box asking the user if they want to delete their input
                alertDialog.setMessage("Delete?").setCancelable(false)
                        //If the user presses yes the app will close
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Will open the same activity and finish the last one so they can't go back
                                Intent intent = new Intent(EnterAmountActivity.this,
                                        EnterAmountActivity.class);
                                startActivity(intent);
                                finish();
                                //Speaking to the user to let them know the text has been cleared
                                tts = new TextToSpeech(EnterAmountActivity.this,
                                        new TextToSpeech.OnInitListener() {
                                    String s2 = enterAmount2.getText().toString();
                                    @Override
                                    public void onInit(int status) {
                                        tts.speak("Deleting", TextToSpeech.QUEUE_FLUSH, null);
                                    }
                                });
                            }
                        })//end of yes button
                        //If the user presses no the dialog box will close
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });//end of no button
                AlertDialog alert = alertDialog.create();
                alert.setTitle("Delete What You Typed");
                alert.show();
            }//end of on click
        });//end of clearBtn listener

        //Speech To text
        speakBtn = (ImageView)findViewById(R.id.speakBtn);
        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnToOpenMic();
            }
        });

    }//end of onCreate

    //Speech to Text method
    private void btnToOpenMic(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please Say Your Total");


        try{
            startActivityForResult(intent, REQ_CODE_SPEECH_OUTPUT);
        }catch(ActivityNotFoundException tim){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQ_CODE_SPEECH_OUTPUT: {
                if (requestCode == RESULT_OK && null != data){
                    ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    euroTxt.setText(voiceInText.get(0));
                }
                break;
            }
        }
    }
}
