// Copyright (c) 2020 Facebook, Inc. and its affiliates.
// All rights reserved.
//
// This source code is licensed under the BSD-style license found in the
// LICENSE file in the root directory of this source tree.

package org.pytorch.demo.objectdetection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;


public class ResultView extends View {

    private final static int TEXT_X = 40;
    private final static int TEXT_Y = 35;
    private final static int TEXT_WIDTH = 260;
    private final static int TEXT_HEIGHT = 50;

    private Paint mPaintRectangle;
    private Paint mPaintText;
    private ArrayList<Result> mResults;
    private ArrayList<String> mDistances;

    public ResultView(Context context) {
        super(context);
    }

    public ResultView(Context context, AttributeSet attrs){
        super(context, attrs);
        mPaintRectangle = new Paint();
        mPaintRectangle.setColor(Color.YELLOW);
        mPaintText = new Paint();
        mDistances = new ArrayList<>();
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        if (mResults == null) return;
//        for (Result result : mResults) {
//            mPaintRectangle.setStrokeWidth(5);
//            mPaintRectangle.setStyle(Paint.Style.STROKE);
//            canvas.drawRect(result.rect, mPaintRectangle);
//
//            Path mPath = new Path();
//            RectF mRectF = new RectF(result.rect.left, result.rect.top, result.rect.left + TEXT_WIDTH,  result.rect.top + TEXT_HEIGHT);
//            mPath.addRect(mRectF, Path.Direction.CW);
//            mPaintText.setColor(Color.MAGENTA);
//            canvas.drawPath(mPath, mPaintText);
//
//            mPaintText.setColor(Color.WHITE);
//            mPaintText.setStrokeWidth(0);
//            mPaintText.setStyle(Paint.Style.FILL);
//            mPaintText.setTextSize(32);
//            canvas.drawText(String.format("%s %.2f", PrePostProcessor.mClasses[result.classIndex], result.score), result.rect.left + TEXT_X, result.rect.top + TEXT_Y, mPaintText);
//        }
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mResults == null || mDistances == null) return;

        // Draw bounding boxes and class information
        for (int i = 0; i < mResults.size(); i++) {
            Result result = mResults.get(i);

            // Draw bounding box
            mPaintRectangle.setStrokeWidth(5);
            mPaintRectangle.setStyle(Paint.Style.STROKE);
            canvas.drawRect(result.rect, mPaintRectangle);

            // Draw text background for class and score information
            Path mPath = new Path();
            RectF mRectF = new RectF(result.rect.left, result.rect.top, result.rect.left + TEXT_WIDTH, result.rect.top + TEXT_HEIGHT);
            mPath.addRect(mRectF, Path.Direction.CW);
            mPaintText.setColor(Color.MAGENTA);
            canvas.drawPath(mPath, mPaintText);

            // Display the class name and detection score
            mPaintText.setColor(Color.WHITE);
            mPaintText.setStrokeWidth(0);
            mPaintText.setStyle(Paint.Style.FILL);
            mPaintText.setTextSize(32);
            canvas.drawText(String.format("%s %.2f", PrePostProcessor.mClasses[result.classIndex], result.score), result.rect.left + TEXT_X, result.rect.top + TEXT_Y, mPaintText);

            // Display the distance if available (only for pairs)
            // Iterate through the distances and draw them where needed
            if (i < mDistances.size()) {
                String distance = mDistances.get(i);
                mPaintText.setColor(Color.WHITE);
                // Display the distance below the detection information
                canvas.drawText(distance, result.rect.left + TEXT_X, result.rect.top + TEXT_Y + TEXT_HEIGHT + 20, mPaintText);
            }
        }
    }

    public void setResults(ArrayList<Result> results, ArrayList<String> distances) {
        mResults = results;
        mDistances = distances;
    }
}