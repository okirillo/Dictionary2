import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        String pathWord = "/Users/Teerachet/IdeaProjects/Dictionary/lib/words20k.txt";
        String source = "/Users/Teerachet/IdeaProjects/Dictionary/lib";
        File file20k = new File(pathWord); // ไฟล์คำศัพท์ 20000 คำ

        try {
            System.out.println("Create folder and text file...");
            BufferedReader br = new BufferedReader(new FileReader(file20k));
            String line, firstLetter, secondLetter;
            while ((line = br.readLine()) != null) { // วน loop อ่านทุกบรรทัด
                line = line.toLowerCase();

                firstLetter = Character.toString(line.charAt(0)).toUpperCase();
                secondLetter = Character.toString(line.charAt(1)).toUpperCase();

                File file1Letter = new File(source +"/"+ firstLetter);
                File file2Letter = new File(source +"/"+ firstLetter +"/"+ secondLetter);

                if (!file1Letter.exists()) { // เช็กว่าโฟเดอร์แรกยังไม่ถูกสร้างใช่ไหม
                    if (file1Letter.mkdirs()) { // สร้างโฟเดอร์แรก
                        if (file2Letter.mkdirs()) { // สร้างโฟเดอร์สอง
                            createTxt(line, firstLetter, secondLetter);
                        } else {
                            System.out.println("Failed to create second directory!");
                        }
                    } else {
                        System.out.println("Failed to create first directory!");
                    }

                } else { // โฟเดอร์แรกถูกสร้างแล้ว
                    if (!file2Letter.exists()) { // เช็กว่าโฟเดอร์สองยังไม่ถูกสร้างใช่ไหม
                        if (file2Letter.mkdirs()) { // สร้างโฟเดอร์สอง
                            createTxt(line, firstLetter, secondLetter);
                        } else {
                            System.out.println("Failed to create second directory!");
                        }
                    } else { // โฟเดอร์สองถูกสร้างแล้ว
                        createTxt(line, firstLetter, secondLetter);
                    }
                }
            }
            System.out.println("Create folder and text file success!");
            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println();
        folderSize();
        System.out.println();

        Database.main(args);
    }

    private static void createTxt(String line, String firstLetter, String secondLetter) {
        String path = "/Users/Teerachet/IdeaProjects/Dictionary/lib/"+ firstLetter +"/"+ secondLetter +"/"+ line +".txt";
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(file);
            for (int i = 0; i < 100; i++) {
                fw.write(i +"\t"+ line +"\r\n");
            }
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void folderSize() {
        String path = "/Users/Teerachet/IdeaProjects/Dictionary/lib";

        try {
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            assert listOfFiles != null;
            for (File file : listOfFiles) {
                if (file.isDirectory()) {
                    long sizeBytes = FileUtils.sizeOfDirectory(new File(file.getPath()));
                    float kb = sizeBytes / 1000;
                    System.out.println("Folder: " + file.getName() + "\t" + kb + " kilobytes");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}