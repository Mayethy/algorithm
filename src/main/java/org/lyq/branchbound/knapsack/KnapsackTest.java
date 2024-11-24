package org.lyq.branchbound.knapsack;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

/**
 * ClassName: Test
 * Package: org.lyq.backtracking
 * Description:
 *
 * @author 林宁
 * 2024/11/23 15:20
 */
public class KnapsackTest {
    public static void main(String[] args) throws Exception {

        int rounds = 50;

        int capacity = 20;
        int[] values = {25, 24, 15};
        int[] weights = {18, 15, 10};

        org.lyq.branchbound.knapsack.Knapsack solver = new Knapsack(values, weights, capacity);

        // 创建一个新的工作簿和工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Knapsack Times");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Round");
        headerRow.createCell(1).setCellValue("UpperBoundPriority Time (us)");
        headerRow.createCell(2).setCellValue("CurrentValuePriority (us)");


        // 初始化行索引
        int rowIdx = 1;

        for (int round = 0; round < rounds; round++) {
            // 创建新的一行
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(round + 1);


            long startTime, endTime;

            startTime = System.nanoTime();
            solver.solveWithUpperBoundPriority();
            endTime = System.nanoTime();

            row.createCell(1).setCellValue((endTime - startTime) / 1000.0);

            startTime = System.nanoTime();
            solver.solveWithCurrentValuePriority();
            endTime = System.nanoTime();

            row.createCell(2).setCellValue((endTime - startTime) / 1000.0);

        }


        // 将结果写入 Excel 文件
        try (FileOutputStream outputStream = new FileOutputStream("./excel/bbKnapsackTestResults.xlsx")) {
            workbook.write(outputStream);
        }

        // 关闭工作簿
        workbook.close();
    }
}
