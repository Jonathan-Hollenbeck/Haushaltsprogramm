package View;

import java.awt.*;
import java.util.HashMap;

public class GraphGenerator extends Canvas {

    private HashMap<String, Double> data;

    private HashMap<String, Integer> xAxisKeyPositions;

    private String graphMode = "line", xAxisName = "Date";

    @Override
    public void paint(Graphics g) {
        if (data != null && data.keySet().size() != 0) {
            int xAxisOffset = 30, yAxisOffset = 40;
            int xMin = 0, xMax = data.keySet().size();
            int yMin = 0, yMax = 0;
            int bottomY = getHeight() - yAxisOffset;
            int rightX = getWidth() - xAxisOffset;
            int xAxisWidth = rightX - xAxisOffset;
            int yAxisHeight = bottomY - yAxisOffset;

            //draw Axis
            drawAxis(g, xAxisOffset, yAxisOffset);

            //getting maxValue from data
            for (String key : data.keySet()) {
                if (yMax < data.get(key)) {
                    yMax = (int) Math.round(data.get(key));
                }
            }
            //draw marker Y and Values
            g.drawString("Amount", xAxisOffset - 20, yAxisOffset - 5);
            int markerCountY = 10;
            float markerDistY = (float) (yMax - yMin) / markerCountY;
            int offsetY = scaleValueToMinMax(markerDistY, yMin, yMax, yAxisOffset, bottomY) - yAxisOffset;
            for (int i = 0; i <= markerCountY; i++) {
                g.drawLine(xAxisOffset - 5, bottomY - (i * offsetY), xAxisOffset, bottomY - (i * offsetY));
                g.drawString("" + Math.floor(10 * (i * markerDistY)) / 10, xAxisOffset - 30, bottomY - (i * offsetY));
            }
            //draw Graph
            //helper variables
            int markerCountX = data.keySet().size();
            int markerDistX = (xMax - xMin) / markerCountX;
            int startingOffsetX = xAxisWidth / (data.keySet().size() + 1);
            int offsetX = scaleValueToMinMax(markerDistX, xMin, xMax, xAxisOffset, rightX - startingOffsetX) - xAxisOffset;
            startingOffsetX += xAxisOffset;

            //draw marker Values and Graph
            int[][] positions = new int[xAxisKeyPositions.keySet().size()][2];
            for (String key : data.keySet()) {
                //draw Marker + Markervalues
                g.drawLine(startingOffsetX + (xAxisKeyPositions.get(key) * offsetX), bottomY + 5, startingOffsetX + (xAxisKeyPositions.get(key) * offsetX), bottomY);
                int keyStringWidth = g.getFontMetrics().stringWidth(key);
                if(xAxisKeyPositions.get(key) % 2 == 0)
                    g.drawString(key, (startingOffsetX + (xAxisKeyPositions.get(key) * offsetX)) - (keyStringWidth / 2), bottomY + 17);
                else
                    g.drawString(key, (startingOffsetX + (xAxisKeyPositions.get(key) * offsetX)) - (keyStringWidth / 2), bottomY + 27);
                //draw Graphvalues
                int valueOffsetY = scaleValueToMinMax((int) Math.round(data.get(key)), yMin, yMax, yAxisOffset, bottomY) - yAxisOffset;
                String valueString = "" + Math.floor(100 * data.get(key)) / 100;
                int valueStringWidth = g.getFontMetrics().stringWidth(valueString);
                g.drawString(valueString, (startingOffsetX + (xAxisKeyPositions.get(key) * offsetX)) - (valueStringWidth / 2), bottomY - valueOffsetY - 5);
                positions[xAxisKeyPositions.get(key)][0] = startingOffsetX + (xAxisKeyPositions.get(key) * offsetX);
                positions[xAxisKeyPositions.get(key)][1] = bottomY - valueOffsetY;
            }
            //draw Graph depending on graphMode
            if (graphMode.equals("Line")) {
                int ovalRadius = 8;
                g.setColor(Color.BLUE);
                g.fillOval(positions[0][0] - (ovalRadius / 2), positions[0][1] - (ovalRadius / 2), ovalRadius, ovalRadius);
                g.setColor(Color.BLACK);
                for (int i = 1; i < positions.length; i++) {
                    g.drawLine(positions[i - 1][0], positions[i - 1][1], positions[i][0], positions[i][1]);
                    g.setColor(Color.BLUE);
                    g.fillOval(positions[i][0] - (ovalRadius / 2), positions[i][1] - (ovalRadius / 2), ovalRadius, ovalRadius);
                    g.setColor(Color.BLACK);
                }
            } else if (graphMode.equals("Bar")) {
                for (int i = 0; i < positions.length; i++) {
                    int barWidth = 20;
                    g.setColor(new Color(i % 3 * 127, i % 2 * 255, i % 4 * 63));
                    g.fillRect(positions[i][0] - (barWidth / 2), positions[i][1], barWidth, bottomY - positions[i][1]);
                    g.setColor(Color.BLACK);
                }
            }
        }

    }

    private void drawBorder(Graphics g, Color color, int thickness) {
        Color original = g.getColor();
        g.setColor(color);
        for (int i = 0; i < thickness; i++) {
            g.drawRect(0 + i, 0 + i, getWidth() - i * 2, getHeight() - i * 2);
        }
        g.setColor(original);
    }

    private void drawAxis(Graphics g, int xAxisOffset, int yAxisOffset) {
        if (g != null) {
            int bottomY = getHeight() - yAxisOffset;
            int rightX = getWidth() - xAxisOffset;

            //y axis
            g.drawLine(xAxisOffset, yAxisOffset, xAxisOffset, bottomY);
            g.drawLine(xAxisOffset, yAxisOffset, xAxisOffset - 3, yAxisOffset + 3);
            g.drawLine(xAxisOffset, yAxisOffset, xAxisOffset + 3, yAxisOffset + 3);
            //x axis
            g.drawLine(xAxisOffset, bottomY, rightX, bottomY);
            g.drawLine(rightX, bottomY, rightX - 3, bottomY - 3);
            g.drawLine(rightX, bottomY, rightX - 3, bottomY + 3);
        }
    }

    private int scaleValueToMinMax(float value, int originalMin, int originalMax, int scaleMin, int scaleMax) {
        float originalPercentage = (value - originalMin) / (originalMax - originalMin);
        return scaleMin + (int) ((scaleMax - scaleMin) * originalPercentage);
    }

    public void setData(HashMap<String, Double> data, HashMap<String, Integer> xAxisKeyPositions, String graphMode, String xAxisName) {
        this.data = data;
        this.xAxisKeyPositions = xAxisKeyPositions;
        this.graphMode = graphMode;
        this.xAxisName = xAxisName;
        repaint();
    }
}
