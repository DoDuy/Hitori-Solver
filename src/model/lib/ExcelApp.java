package model.lib;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import model.Config;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * 16/10/2015
 * @author DoDuy
 */
public class ExcelApp {
    private final Workbook workbook;
    private Sheet sheet;
    private Sheet sheetAVG;
    private Row row;
    private int rowIndex;
    private int rowIndexAVG;
    public ExcelApp(){
        workbook = new XSSFWorkbook();
    }
    
    public void createSheet(String sheetName){
        sheet = workbook.createSheet(sheetName);
        rowIndex = 0;
    }
    
    public void createSheetAVG(String sheetName){
        sheetAVG = workbook.createSheet(sheetName);
        rowIndexAVG = 0;
    }
    
    public void autoSizeColumn(int index){
        sheet.autoSizeColumn(index);
    }
    
    public void autoSizeColumnAVG(int index){
        sheetAVG.autoSizeColumn(index);
    }
    
    public void createRow(){
        row = sheet.createRow(rowIndex++);
    }
    
    public void createRowAVG(){
        row = sheetAVG.createRow(rowIndexAVG++);
    }
    
    public void addCell(int index,double value){
        row.createCell(index).setCellValue(value);
    }
    
    public void addCell(int index,String value){
        row.createCell(index).setCellValue(value);
    }
    
    public void addFormula(int index,String value){
        Cell c = row.createCell(index);
        c.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        c.setCellFormula(value);
    }
    
    public int getRowIndex(){
        return row.getRowNum();
    }
    
    public void finish(){
        try {
            try (FileOutputStream fos = new FileOutputStream(Config.PATH_FILE_EXCEL)) {
                workbook.write(fos);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createChartAVG(int rows, int cols) {
         Drawing xlsx_drawing = sheetAVG.createDrawingPatriarch();
        ClientAnchor anchor = xlsx_drawing.createAnchor(0, 0, 0, 0, 1, rows+2, rows+7, rows+20);
        Chart my_line_chart = xlsx_drawing.createChart(anchor);
        ChartLegend legend = my_line_chart.getOrCreateLegend();
        legend.setPosition(LegendPosition.BOTTOM);
        LineChartData data = my_line_chart.getChartDataFactory().createLineChartData();
        ChartAxis bottomAxis = my_line_chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
        ValueAxis leftAxis = my_line_chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheetAVG, new CellRangeAddress(1, rows, 0, 0));
        //ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheetAVG, new CellRangeAddress(1, rows, 1, 1));
        Row titlerow =  sheetAVG.getRow(0);
        for(int i = 0; i < cols; i++){
            ChartDataSource<Number> ys = DataSources.fromNumericCellRange(sheetAVG, new CellRangeAddress(1, rows, 2+i, 2+i));
            data.addSeries(xs, ys).setTitle(titlerow.getCell(i+2).getStringCellValue());
        }
        my_line_chart.plot(data, new ChartAxis[] { bottomAxis, leftAxis });
    }
}
