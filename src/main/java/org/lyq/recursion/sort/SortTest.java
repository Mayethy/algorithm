package org.lyq.recursion.sort;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Random;

/**
 * ClassName: SortTest
 * Package: org.lyq.sort
 * Description:
 *
 * @author 林宁
 * 2024/11/11 11:58
 */
public class SortTest {
    public static void main(String[] args) throws Exception {
        // 定义要测试的不同数组大小
        int[] sizes = {100, 1000, 10000};
        // 每个大小的数组进行测试的轮数
        int rounds = 50;

        // 创建一个新的工作簿和工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sorting Times");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Size");
        headerRow.createCell(1).setCellValue("Round");
        headerRow.createCell(2).setCellValue("Merge Sort Time (us)");
        headerRow.createCell(3).setCellValue("Quick Sort Time (us)");

        // 初始化行索引
        int rowIdx = 1;

        // 对每个大小的数组进行多轮测试
        for (int size : sizes) {
            for (int round = 0; round < rounds; round++) {
                // 创建新的一行
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(size);
                row.createCell(1).setCellValue(round + 1);

                // 生成随机数组
                int[] data = generateRandomArray(size);

                // 测试归并排序
                int[] copyForMerge = Arrays.copyOf(data, data.length);
                long startTime, endTime;

                // 记录归并排序的开始时间
                startTime = System.nanoTime();
                MergeSort.mergeSort(copyForMerge);
                // 记录归并排序的结束时间
                endTime = System.nanoTime();
                // 计算归并排序所用的时间（微秒）
                row.createCell(2).setCellValue((endTime - startTime) / 1000);

                // 测试快速排序
                int[] copyForQuick = Arrays.copyOf(data, data.length);

                // 记录快速排序的开始时间
                startTime = System.nanoTime();
                QuickSort.quickSort(copyForQuick, 0, copyForQuick.length - 1);
                // 记录快速排序的结束时间
                endTime = System.nanoTime();
                // 计算快速排序所用的时间（微秒）
                row.createCell(3).setCellValue((endTime - startTime) / 1000);
            }
        }

        // 将结果写入 Excel 文件
        try (FileOutputStream outputStream = new FileOutputStream("./excel/SortTestResults1.xlsx")) {
            workbook.write(outputStream);
        }

        // 关闭工作簿
        workbook.close();
    }

    // 生成指定大小的随机数组
    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10000); // 生成随机整数
        }
        return array;
    }
}
