package com.itc209.assignment4.graph;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class NoOpAxisRenderer extends YAxisRenderer {

    public NoOpAxisRenderer(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer trans) {
        super(viewPortHandler, yAxis, trans);
    }

    @Override
    public void renderAxisLabels(Canvas c) {

    }

    @Override
    public void renderGridLines(Canvas c) {

    }

    @Override
    public void renderAxisLine(Canvas c) {

    }

    @Override
    public void renderLimitLines(Canvas c) {

    }
}
