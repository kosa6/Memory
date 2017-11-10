package example.memory;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int minPhotos=4;
    private final static int maxPhotos=10;
    private static DataBase mDbHelper;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private List<ImageView> listOfImage;
    private List<String> listOfmCurrentPhotos;
    private ImageView img1,img2,img3,img4;
    private int index = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button newGameButton = (Button) findViewById(R.id.newGame);
        mDbHelper = new DataBase(this);
        img1 = (ImageView) findViewById(R.id.imageView1);
        img2 = (ImageView) findViewById(R.id.imageView2);
        img3 = (ImageView) findViewById(R.id.imageView3);
        img4 = (ImageView) findViewById(R.id.imageView4);
        listOfImage = new ArrayList<>();
        listOfmCurrentPhotos = new ArrayList<>();
        listOfImage.add(img1); listOfImage.add(img2); listOfImage.add(img3); listOfImage.add(img4);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText = createNumberInput();
                newGameButton.setVisibility(View.GONE);
                editTextListener();
            }
        });
    }

    private EditText createNumberInput() {
        EditText numberOfPhotos = new EditText(this);
        numberOfPhotos.setInputType(2);
        numberOfPhotos.setHint("Write number of photos");
        numberOfPhotos.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.layoutForText);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        myLayout.addView(numberOfPhotos,lp);
        return numberOfPhotos;
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
    private void editTextListener() {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                int number = Integer.parseInt(textView.getText().toString());
                if(number >= minPhotos && number <=maxPhotos) {
                    for(int k=0; k<4; k++) {
                        dispatchTakePictureIntent();
                    }
                }
                else if(number == 99) {
                    List kappa = mDbHelper.getData();
                    for(int j =0; j<kappa.size(); j++)
                    {
                        toastMessage(kappa.get(j).toString());
                    }
                }
                else {
                    toastMessage("Number must be between 4 to 10");
                }
                return  false;
            }
        });
    }
    /*public void onStop() {
        this.deleteDatabase(mDbHelper.DATABASE_NAME);
        super.onStop();
    }*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            listOfImage.get(this.index).setImageURI(Uri.parse(listOfmCurrentPhotos.get(index)));
            this.index++;
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        this.listOfmCurrentPhotos.add(mCurrentPhotoPath);
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                toastMessage("Error occurred while creating the File");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "example.memory",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
}
