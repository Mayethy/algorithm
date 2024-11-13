package org.lyq.binary;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lyq.select.QuickSelect;
import org.lyq.sort.QuickSort;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * ClassName: BinaryResearch
 * Package: org.lyq.binary
 * Description:
 *
 * @author 林宁
 * 2024/11/10 13:51
 */
public class BinarySearchTest {

    private static final Random RANDOM = new Random();
    private static final int[] SIZES = {100, 1000, 10000,}; // 数组大小
    private static final int TEST_COUNT = 50; // 测试次数
    private static final String[] HEADERS = {"Size", "Iteration", "Target", "Iterative Time (ns)", "Recursive Time (ns)"};

    // 生成已排序数组
    private static int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    public static void main(String[] args) throws IOException {
        BinarySearch binarySearch = new BinarySearch();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Binary Search Results");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            headerRow.createCell(i).setCellValue(HEADERS[i]);
        }

        int rowIndex = 1; // 从第二行开始填充数据

        for (int size : SIZES) {
            int[] array = generateSortedArray(size);
            for (int i = 0; i < TEST_COUNT; i++) {
                int target = RANDOM.nextInt(size);
                long iterativeStartTime = System.nanoTime();
                binarySearch.binarySearchIterative(array, target, 0, size - 1);
                long iterativeTime = System.nanoTime() - iterativeStartTime;

                // 测量递归二分搜索时间
                long recursiveStartTime = System.nanoTime();
                binarySearch.binarySearchRecursive(array, target, 0, size - 1);
                long recursiveTime = System.nanoTime() - recursiveStartTime;

                // 写入数据
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(size);
                row.createCell(1).setCellValue(i + 1);
                row.createCell(2).setCellValue(target);
                row.createCell(3).setCellValue(iterativeTime);
                row.createCell(4).setCellValue(recursiveTime);
            }
        }

        // 输出到Excel文件
        try (FileOutputStream outputStream = new FileOutputStream("./excel/BinarySearchResults.xlsx")) {
            workbook.write(outputStream);
        }

        // 关闭工作簿
        workbook.close();
    }
}

