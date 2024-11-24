package org.lyq.backtracking;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lyq.recursion.sort.MergeSort;
import org.lyq.recursion.sort.QuickSort;

import java.io.FileOutputStream;
import java.util.Arrays;

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

        Knapsack knapsack = new Knapsack(capacity, values, weights);

        // 创建一个新的工作簿和工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Knapsack Times");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Round");
        headerRow.createCell(1).setCellValue("greedy Time (us)");
        headerRow.createCell(2).setCellValue("normal Time (us)");


        // 初始化行索引
        int rowIdx = 1;

        for (int round = 0; round < rounds; round++) {
            // 创建新的一行
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(round + 1);


            long startTime, endTime;

            startTime = System.nanoTime();
            int maxProfit2 = knapsack.solveWithBound2();
            endTime = System.nanoTime();

            row.createCell(1).setCellValue((endTime - startTime) / 1000.0);

            startTime = System.nanoTime();
            knapsack.solveWithBound1();
            endTime = System.nanoTime();

            row.createCell(2).setCellValue((endTime - startTime) / 1000.0);

        }


        // 将结果写入 Excel 文件
        try (FileOutputStream outputStream = new FileOutputStream("./excel/KnapsackTestResults.xlsx")) {
            workbook.write(outputStream);
        }

        // 关闭工作簿
        workbook.close();
    }
}
