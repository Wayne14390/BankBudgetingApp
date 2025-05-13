package com.example.bankbudgetingapp.ui.theme.screens.analysis

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


@Composable
fun LineChartView(modifier: Modifier = Modifier, entries: List<Entry>) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        factory = { context ->
            LineChart(context).apply {
                val dataSet = LineDataSet(entries, "Budget Trend").apply {
                    color = android.graphics.Color.BLUE
                    valueTextColor = android.graphics.Color.BLACK
                    lineWidth = 2f
                    circleRadius = 4f
                    setCircleColor(android.graphics.Color.RED)
                }

                val lineData = LineData(dataSet)
                this.data = lineData
                this.invalidate()
            }
        }
    )
}