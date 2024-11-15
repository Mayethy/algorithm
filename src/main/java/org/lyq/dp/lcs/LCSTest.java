package org.lyq.dp.lcs;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lyq.recursion.binary.BinarySearch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * ClassName: LCSTest
 * Package: org.lyq.dp.lcs
 * Description:
 *
 * @author 林宁
 * 2024/11/15 21:13
 */
public class LCSTest {

    private static final int TEST_COUNT = 50; // 测试次数
    private static final String[] HEADERS = {"Iteration", "BruteForce Time (us)", "Recursive Time (us)", "Memoization Time (us)", "Dp Time (us)"};

    public static void main(String[] args) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("LCS Results");
        BruteForceLongestCommonSubsequence bfLcs = new BruteForceLongestCommonSubsequence();
        DpLongestCommonSubsequence dpLcs = new DpLongestCommonSubsequence();
        MemoizationLongestCommonSubsequence memLcs = new MemoizationLongestCommonSubsequence();
        RecursiveLongestCommonSubsequence reLcs = new RecursiveLongestCommonSubsequence();

        // 创建表头
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            headerRow.createCell(i).setCellValue(HEADERS[i]);
        }

        int rowIndex = 1; // 从第二行开始填充数据
        String X = "ABCBDAB";
        String Y = "BDCABA";

        for (int i = 0; i < TEST_COUNT; i++) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(i + 1);

            long startTime = System.nanoTime();
            bfLcs.getLCS(X, Y);
            long endTime = System.nanoTime() - startTime;
            row.createCell(1).setCellValue(endTime/100);

            startTime = System.nanoTime();
            reLcs.getLCS(X, Y);
            endTime = System.nanoTime() - startTime;
            row.createCell(2).setCellValue(endTime/100);

            startTime = System.nanoTime();
            memLcs.getLCS(X, Y);
            endTime = System.nanoTime() - startTime;
            row.createCell(3).setCellValue(endTime/100);

            startTime = System.nanoTime();
            dpLcs.getLCS(X, Y);
            endTime = System.nanoTime() - startTime;
            row.createCell(4).setCellValue(endTime/100);

        }

        // 输出到Excel文件
        try (FileOutputStream outputStream = new FileOutputStream("./excel/LCSResults.xlsx")) {
            workbook.write(outputStream);
        }

        // 关闭工作簿
        workbook.close();
    }
}
