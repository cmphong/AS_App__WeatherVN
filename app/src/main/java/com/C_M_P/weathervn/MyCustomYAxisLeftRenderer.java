package com.C_M_P.weathervn;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class MyCustomYAxisLeftRenderer extends YAxisRenderer {
    public MyCustomYAxisLeftRenderer(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer trans) {
        super(viewPortHandler, yAxis, trans);
    }
//    public MyCustomYAxisLeftRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
//        super(viewPortHandler, xAxis, trans);
//    }


    @Override
    protected void drawYLabels(Canvas c, float fixedPosition, float[] positions, float offset) {
//        super.drawYLabels(c, fixedPosition, positions, offset);
//
//        String line[] = formattedLabel.split("\n");
//        Utils.drawXAxisValue(c, line[0], x, y, mAxisLabelPaint, anchor, angleDegrees);
//        Utils.drawXAxisValue(
//                c,
//                line[1],
//                x,
//                y + mAxisLabelPaint.getTextSize() + 8,
//                mAxisLabelPaint,
//                anchor,
//                angleDegrees
//        );
    }
}
