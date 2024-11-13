package org.lyq.select;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * ClassName: QuickSelectTest
 * Package: org.lyq.select
 * Description:
 *
 * @author 林宁
 * 2024/11/11 17:34
 */
public class QuickSelectTest {

    private static final Random RANDOM = new Random();
    private static final int[] SIZES = {100, 1000, 10000}; // 数组大小
    //private static final int[] SIZES = {100, 10000};
    private static final int TEST_COUNT = 50; // 测试次数
    private static final String[] HEADERS = {"Size", "Iteration", "Target","QuickSelect Time (us)"};

    // 生成随机数组
    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10000); // 生成随机整数
        }
        return array;
    }

    // 生成已排序数组
    private static int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    public static void main(String[] args) throws IOException {
        QuickSelect quickSelect = new QuickSelect();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Quick Select Results");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            headerRow.createCell(i).setCellValue(HEADERS[i]);
        }

        int rowIndex = 1; // 从第二行开始填充数据

        for (int size : SIZES) {
            int[] array = generateSortedArray(size);
            for (int i = 0; i < TEST_COUNT; i++) {
                int target = RANDOM.nextInt(size) + 1;

                long selectStartTime = System.nanoTime();
                quickSelect.select(array, target);
                long selectTime = System.nanoTime() - selectStartTime;

                // 写入数据
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(size);
                row.createCell(1).setCellValue(i + 1);
                row.createCell(2).setCellValue(target);
                row.createCell(3).setCellValue(selectTime/100);
            }
        }

        // 输出到Excel文件
        try (FileOutputStream outputStream = new FileOutputStream("./excel/QuickSelectResults.xlsx")) {
            workbook.write(outputStream);
        }

        // 关闭工作簿
        workbook.close();
    }
}
