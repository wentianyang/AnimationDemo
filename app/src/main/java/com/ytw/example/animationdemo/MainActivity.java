package com.ytw.example.animationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

  private boolean mTarget = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final MyDrawingView myDrawingView = (MyDrawingView) findViewById(R.id.my_view);

    final ImageView button = (ImageView) findViewById(R.id.btn_start);

    //通过此方法过去View的真实高度
    button.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            float startX = button.getX() + button.getWidth() / 2;
            float startY = button.getY() + button.getHeight() / 2;
            myDrawingView.initStartPosition(startX, startY);
          }
        });

    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mTarget = !mTarget;
        if (mTarget) {
          myDrawingView.startAnima();
        } else {
          myDrawingView.endAnima();
        }
      }
    });
  }
}
