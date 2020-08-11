package com.juaracoding.iot.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.juaracoding.iot.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.os.Looper.getMainLooper;

public class ChartHelper implements OnChartValueSelectedListener {

    private LineChart mChart;

    public ChartHelper(LineChart chart) {

        mChart = chart;
        mChart.setOnChartValueSelectedListener(this);

        mChart.setNoDataText("Tidak ada Data!");
        mChart.setTransitionName("Nilai Sensor");

        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.animateXY(5000, 5000);
        mChart.animateY(3000);
        mChart.setPinchZoom(true);


        mChart.setBackgroundColor(Color.TRANSPARENT);
        mChart.setBorderColor(Color.rgb(67,164,34));

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(Typeface.MONOSPACE);

        l.setTextColor(Color.rgb(67, 164, 34));

        XAxis xl = mChart.getXAxis();
        xl.setTypeface(Typeface.MONOSPACE);
        xl.setTextColor(Color.rgb(67, 164, 150));
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(Typeface.MONOSPACE);
        leftAxis.setTextColor(Color.rgb(100, 164, 34));

        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

    }

    public void setChart(LineChart chart){ this.mChart = chart; }

    public void addEntry(float[] value, float[] value2, float[] value3) {
        LineData data = mChart.getData();
        if (data != null){

            ILineDataSet set = data.getDataSetByIndex(0);
            ILineDataSet set2 = data.getDataSetByIndex(1);
            ILineDataSet set3 = data.getDataSetByIndex(1);

            //  set.addEntry(value);
            if (set == null ) {
                set = createSet(Color.BLUE, Color.WHITE);
                set.setLabel("Temperature");
                set.setDrawIcons(true);
                set.setDrawValues(true);

                data.addDataSet(set);
                //data.addDataSet(set2);
            }
            if (set2 == null ) {
                set2 = createSet(Color.YELLOW, Color.BLUE);
                set2.setLabel("Light Intensity");
                set2.setDrawIcons(true);
                set2.setDrawValues(true);

                data.addDataSet(set2);
                //data.addDataSet(set2);
            }
            if (set3 == null ) {
                set3 = createSet(Color.GREEN, Color.BLACK);
                set3.setLabel("Humidity");
                set3.setDrawIcons(true);
                set3.setDrawValues(true);

                data.addDataSet(set3);
                //data.addDataSet(set2);
            }
            for (int i = 0; i < value.length; i++) {
                data.addEntry(new Entry(set.getEntryCount(), value[i] , "Temperature"),0);
                data.notifyDataChanged();
            }

            for (int i = 0; i < value2.length; i++) {
                data.addEntry(new Entry(set2.getEntryCount(), value2[i] , "Light Intensity"),1);
                data.notifyDataChanged();
            }

            for (int i = 0; i < value3.length; i++) {
                data.addEntry(new Entry(set3.getEntryCount(), value3[i] , "Humidity"),2);
                data.notifyDataChanged();
            }

            final Date currentTime = Calendar.getInstance().getTime();
            mChart.notifyDataSetChanged();
            final Handler someHandler = new Handler(getMainLooper());
            someHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mChart.getDescription().setText(currentTime.toString());
                    someHandler.postDelayed(this, 1000);
                }
            }, 10);

            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(10);
            mChart.setNoDataText("No Data Was Add!");
            mChart.setTag(set);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewTo(set.getEntryCount()-1, data.getYMax(), YAxis.AxisDependency.LEFT);

        }
    }


    private LineDataSet createSet(int lineColor, int circleColor) {
        LineDataSet set = new LineDataSet(null, "Sensor");
        set.setDrawIcons(true);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(lineColor);
        set.setCircleColor(circleColor);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setValueTextColor(Color.BLACK);
        set.setFillAlpha(65);
        set.setFillColor(Color.rgb(67, 164, 34));
        set.setHighLightColor(Color.rgb(67, 164, 34));
        set.setValueTextColor(Color.rgb(67, 164, 34));
        set.setValueTextSize(9f);
        set.setDrawValues(false);

        return set;
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
