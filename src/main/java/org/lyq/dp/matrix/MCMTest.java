package org.lyq.dp.matrix;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lyq.dp.lcs.BruteForceLongestCommonSubsequence;
import org.lyq.dp.lcs.DpLongestCommonSubsequence;
import org.lyq.dp.lcs.MemoizationLongestCommonSubsequence;
import org.lyq.dp.lcs.RecursiveLongestCommonSubsequence;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ClassName: MCMTest
 * Package: org.lyq.dp.matrix
 * Description:
 *
 * @author 林宁
 * 2024/11/17 16:16
 */
public class MCMTest {

    private static final int TEST_COUNT = 50; // 测试次数
    private static final String[] HEADERS = {"Iteration", "BruteForce Time (us)", "Recursive Time (us)", "Memoization Time (us)", "Dp Time (us)"};

    public static void main(String[] args) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("LCS Results");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            headerRow.createCell(i).setCellValue(HEADERS[i]);
        }

        int rowIndex = 1; // 从第二行开始填充数据
        int[] p = {30, 35, 15, 5, 10, 20, 25}; // 示例矩阵的维度
        int n = p.length; // 矩阵的个数

        for (int i = 0; i < TEST_COUNT; i++) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(i + 1);

            long startTime = System.nanoTime();
            BruteForceMatrixChainMultiplication.matrixChainOrder(p);
            long endTime = System.nanoTime() - startTime;
            row.createCell(1).setCellValue(endTime/100);

            startTime = System.nanoTime();
            RecursiveMatrixChainMultiplication.matrixChainOrder(p, n);
            endTime = System.nanoTime() - startTime;
            row.createCell(2).setCellValue(endTime/100);

            startTime = System.nanoTime();
            MemoizationMatrixChainMultiplication.matrixChainOrder(p, n);
            endTime = System.nanoTime() - startTime;
            row.createCell(3).setCellValue(endTime/100);

            startTime = System.nanoTime();
            DpMatrixChainMultiplication.matrixChainOrder(p, n);
            endTime = System.nanoTime() - startTime;
            row.createCell(4).setCellValue(endTime/100);

        }

        // 输出到Excel文件
        try (FileOutputStream outputStream = new FileOutputStream("./excel/MCMResults.xlsx")) {
            workbook.write(outputStream);
        }

        // 关闭工作簿
        workbook.close();
    }
}
