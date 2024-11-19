package org.lyq.greedy.huffman;

import java.io.*;
import java.util.*;

class Node implements Comparable<Node> {
    char ch;
    int freq;
    Node left, right;

    Node(char ch, int freq, Node left, Node right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    boolean isLeaf() {
        return (this.left == null && this.right == null);
    }

    @Override
    public int compareTo(Node other) {
        return this.freq - other.freq;
    }
}

public class HuffmanCoding {

    // Step 1: Generate frequency table
    public static Map<Character, Integer> buildFrequencyTable(String filePath) throws IOException {
        Map<Character, Integer> frequencyTable = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                char c = (char) ch;
                frequencyTable.put(c, frequencyTable.getOrDefault(c, 0) + 1);
            }
        }
        return frequencyTable;
    }

    // Step 1.1: Save frequency table to file
    public static void saveFrequencyTable(Map<Character, Integer> frequencyTable, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        }
    }

    // Step 2: Build Huffman tree
    public static Node buildHuffmanTree(Map<Character, Integer> frequencyTable) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue(), null, null));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.add(parent);
        }

        return pq.poll();
    }

    // Step 3: Generate Huffman codes
    public static void generateCodes(Node node, String str, Map<Character, String> huffmanCode) {
        if (node == null) {
            return;
        }

        if (node.isLeaf()) {
            huffmanCode.put(node.ch, str);
        }

        generateCodes(node.left, str + "0", huffmanCode);
        generateCodes(node.right, str + "1", huffmanCode);
    }

    // Step 4: Save Huffman codes to file
    public static void saveHuffmanCodes(Map<Character, String> huffmanCode, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        }
    }

    // Step 5: Encode the source file
    public static void encodeFile(String inputFilePath, String outputFilePath, Map<Character, String> huffmanCode) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            int ch;
            StringBuilder encodedStringBuilder = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                char c = (char) ch;
                encodedStringBuilder.append(huffmanCode.get(c));
            }
            writer.write(encodedStringBuilder.toString());
        }
    }

    // Step 6: Get file length
    public static long getFileLength(String filePath) throws IOException {
        File file = new File(filePath);
        return file.length();
    }

    // Step 7: Load Huffman codes from file
    public static Map<String, Character> loadHuffmanCodes(String filePath) throws IOException {
        Map<String, Character> reverseHuffmanCode = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                char ch = parts[0].charAt(0);
                String code = parts[1];
                reverseHuffmanCode.put(code, ch);
            }
        }
        return reverseHuffmanCode;
    }

    // Step 8: Decode the file
    public static void decodeFile(String inputFilePath, String outputFilePath, Map<String, Character> reverseHuffmanCode) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String encodedString = reader.readLine();
            StringBuilder decodedStringBuilder = new StringBuilder();
            StringBuilder tempCode = new StringBuilder();

            for (char bit : encodedString.toCharArray()) {
                tempCode.append(bit);
                if (reverseHuffmanCode.containsKey(tempCode.toString())) {
                    decodedStringBuilder.append(reverseHuffmanCode.get(tempCode.toString()));
                    tempCode.setLength(0); // Clear the temporary code
                }
            }

            writer.write(decodedStringBuilder.toString());
        }
    }

    // Step 9: Compare original and decoded files
    public static boolean compareFiles(String file1Path, String file2Path) throws IOException {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1Path));
             BufferedReader reader2 = new BufferedReader(new FileReader(file2Path))) {
            String line1, line2;
            while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
                if (!line1.equals(line2)) {
                    return false;
                }
            }
            return reader1.readLine() == null && reader2.readLine() == null;
        }
    }

    public static void main(String[] args) throws IOException {
        String sourceFilePath = "src/main/java/org/lyq/greedy/huffman/example.txt";
        String huffmanCodeFilePath = "src/main/java/org/lyq/greedy/huffman/code.txt";
        String encodedFilePath = "src/main/java/org/lyq/greedy/huffman/encoded.txt";
        String decodedFilePath = "src/main/java/org/lyq/greedy/huffman/decoded.txt";
        String frequencyTableFilePath = "src/main/java/org/lyq/greedy/huffman/frequency.txt"; // 新增的频率表文件路径

        // Step 1: Generate frequency table
        Map<Character, Integer> frequencyTable = buildFrequencyTable(sourceFilePath);

        // Step 1.1: Save frequency table to file
        saveFrequencyTable(frequencyTable, frequencyTableFilePath);

        // Step 2: Build Huffman tree
        Node huffmanTree = buildHuffmanTree(frequencyTable);

        // Step 3: Generate Huffman codes
        Map<Character, String> huffmanCode = new HashMap<>();
        generateCodes(huffmanTree, "", huffmanCode);

        // Step 4: Save Huffman codes to file
        saveHuffmanCodes(huffmanCode, huffmanCodeFilePath);

        // Step 5: Encode the source file
        encodeFile(sourceFilePath, encodedFilePath, huffmanCode);

        // Step 6: Get file length
        long encodedFileLength = getFileLength(encodedFilePath);
        System.out.println("Encoded file length: " + encodedFileLength + " bits");

        // Step 7: Decode the file
        Map<String, Character> reverseHuffmanCode = loadHuffmanCodes(huffmanCodeFilePath);
        decodeFile(encodedFilePath, decodedFilePath, reverseHuffmanCode);

        // Step 8: Compare original and decoded files
        boolean filesMatch = compareFiles(sourceFilePath, decodedFilePath);
        System.out.println("Files match: " + filesMatch);

        // Step 9: Compare with other encoding schemes
        // Example: Fixed-length encoding
        int fixedLength = 8; // Assuming 8-bit ASCII encoding
        long originalFileLength = getFileLength(sourceFilePath);
        long fixedLengthEncodedFileLength = originalFileLength * fixedLength ;
        System.out.println("Fixed-length encoded file length: " + fixedLengthEncodedFileLength + " bits");

        // Compare lengths
        System.out.println("Huffman encoding is " + (fixedLengthEncodedFileLength - encodedFileLength) + " bytes more efficient than fixed-length encoding.");
    }
}