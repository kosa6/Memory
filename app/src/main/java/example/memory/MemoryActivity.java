package example.memory;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad Sowisz on 2017-11-18.
 */

public class MemoryActivity extends AppCompatActivity {
    private DataBase mDbHelper;
    private List<TableRow> listOfRow;
    private List<ImageView> listOfImage;
    private int numberOfPhotos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mamory_layout);
        final TextView score = (TextView) findViewById(R.id.score);
        mDbHelper = new DataBase(this);
        listOfRow = new ArrayList<>();
       // numberOfPhotos = getIntent().getIntExtra("numberOfPhotos",-1);
        //createGame(8);
        createGameTest();
    }
    private TableRow newRow() {
        TableRow row = new TableRow(this);
        TableLayout myLayout = (TableLayout) findViewById(R.id.tableLayout);
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        myLayout.addView(row,lp);
        return row;
    }
    private void newImageButton(TableRow rowToPutImageView, int whichPicture) {
        ImageButton image = new ImageButton(this);
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        image.setImageURI(Uri.parse(mDbHelper.getData().get(whichPicture).toString()));
        rowToPutImageView.addView(image,lp);
    }
    /*private void createGame(float numberOfPhotos) {
        numberOfPhotos = numberOfPhotos * 2;
        float numberOfColuumns=0;
        int numberOfRows=0;
        while (numberOfPhotos%2==0 && numberOfPhotos >4) {
            numberOfPhotos = numberOfPhotos/2;
            if(numberOfPhotos >=4) {
                numberOfRows+=2;
            }
            else {
                break;
            }
        }
        numberOfColuumns = numberOfPhotos;
        for(int i=0; i<numberOfRows; i++) {
            TableRow temp = newRow();
            listOfRow.add(temp);
            for(int j=0; j<numberOfColuumns; j++) {
                ImageView tempView = newImageButton(temp);
                tempView.setImageURI(Uri.parse(mDbHelper.getData().toString()));
                listOfImage.add(tempView);
            }
        }
    }*/
    private void createGameTest() {
        ImageButton testButton = (ImageButton) findViewById(R.id.imageButton);
        ImageButton testButton1 = (ImageButton) findViewById(R.id.imageButton5);
        ImageButton testButton2 = (ImageButton) findViewById(R.id.imageButton6);
        ImageButton testButton3 = (ImageButton) findViewById(R.id.imageButton7);
        /*for (int j = 0; j < 4; j++) {
            TableRow testRow = (TableRow) findViewById(R.id.testRow) ;
            newImageButton(testRow,j);
            toastMessage(Integer.toString(mDbHelper.getData().size()));
        }*/
        testButton.setImageURI(Uri.parse(mDbHelper.getData().get(0).toString()));
        testButton1.setImageURI(Uri.parse(mDbHelper.getData().get(1).toString()));
        testButton2.setImageURI(Uri.parse(mDbHelper.getData().get(2).toString()));
        testButton3.setImageURI(Uri.parse(mDbHelper.getData().get(3).toString()));
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
