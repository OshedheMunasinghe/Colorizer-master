package com.teamtreehouse.colorizer;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    int[] imageResIds = {R.drawable.cuba1, R.drawable.cuba2, R.drawable.cuba3};
    int imageIndex = 0;
    boolean color = true;
    boolean red = true;
    boolean green = true;
    boolean blue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        loadImage();
    }

    private void loadImage() {
        Glide.with(this).load(imageResIds[imageIndex]).into(imageView);
    }

    //This method will be called when the activity is first created, and gives access to our app's menu
    //This will create three dots menue where we have three iteams red green blue with checkbox
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  MenuItem menuItem = menu.add("Next Image"); //This will show up at three dots menu
        // menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);        //if you pick if_room option it will shown if there is enough in the action bar!
        // menuItem.setIcon(R.drawable.ic_add_a_photo_black_24dp);
        // menuItem.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);        //SMART: Meaning that every non-transparents pixel will be changed to white, giving us a white icon.

        getMenuInflater().inflate(R.menu.options_menu, menu);
        Drawable nextImageDrawable = menu.findItem(R.id.nextImage).getIcon(); //another way to change icon camera and dots text color, we want to change it to white
        nextImageDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        menu.findItem(R.id.red).setChecked(red);
        menu.findItem(R.id.green).setChecked(green);
        menu.findItem(R.id.blue).setChecked(blue);

        menu.setGroupVisible(R.id.colorGroup, color);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //scroll image by clicking the icon
        switch (item.getItemId()) {
            case R.id.nextImage:
                imageIndex++;
                if (imageIndex >= imageResIds.length) {
                    imageIndex = 0;
                }//end if
                loadImage();
                break;
            case R.id.color:
                color = !color;
                updateSaturation();
                invalidateOptionsMenu();
                break;
            case R.id.red:
                red = !red;
                updateColors();
                item.setChecked(red);
                break;
            case R.id.green:
                green = !green;
                updateColors();
                item.setChecked(green);
                break;
            case R.id.blue:
                blue = !blue;
                updateColors();
                item.setChecked(blue);
                break;
            case R.id.reset:
                imageView.clearColorFilter();
                red = green = blue = color = true;
                invalidateOptionsMenu(); //to update the optionmenue
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateSaturation() {

        ColorMatrix colorMatrix = new ColorMatrix();
        if (color) {
            red = green = blue = true;
            colorMatrix.setSaturation(1);
        } else {
            colorMatrix.setSaturation(0);
        }
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(colorFilter);
    }

    private void updateColors() {
        ColorMatrix colorMatrix = new ColorMatrix();
        float[] matrix = {
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        };
        if (!red) matrix[0] = 0;
        if (!green) matrix[6] = 0;
        if (!blue) matrix[12] = 0;
        colorMatrix.set(matrix);
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(colorFilter);
    }
}
