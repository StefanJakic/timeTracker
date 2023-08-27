package org.example;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;


public class FileManager {
    Gson gson = new Gson();
    JsonReader reader;
    private String fileName = "progress_data_history";
    String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    public void addLine(String line) {
        try (FileWriter fileWriter = new FileWriter(fileName, true);

             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        } catch (IOException e) {
            //todo better handling, more description
            e.printStackTrace();
        }
    }

    public ArrayList<String> readAllLines() {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            //todo better handling, more description
            e.printStackTrace();
        }
        return lines;
    }

    public String readLastLine() {
        String lastLine = null;
        ArrayList<String> lines = readAllLines();
        if (lines.size() > 0) {
            lastLine = lines.get(lines.size() - 1);
        }
        return lastLine;
    }

    private ProgressData getObjectFromJsonLine(String jsonStr) {
        reader = new JsonReader(new StringReader(jsonStr));
        reader.setLenient(true);

        return gson.fromJson(reader, ProgressData.class);
    }

    private String getJsonLineFromObject(ProgressData progressData) {
        return gson.toJson(progressData);
    }

    public Boolean overwriteLastObject(ProgressData progressData) throws IOException {

        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile("progress_data_history", "rw");
            long length = randomAccessFile.length() - 1;
            byte b;

            if (length < 0) {
                //if in file no data, then just add
                addLine(getJsonLineFromObject(progressData));
                return true;
            }
            do {
                length -= 1;
                randomAccessFile.seek(length);
                b = randomAccessFile.readByte();
            } while (b != 10);
            randomAccessFile.setLength(length + 1);
            addLine(getJsonLineFromObject(progressData));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            randomAccessFile.close();
        }
        return true;
    }


    //todo return Boolean to confurm that file is saved? or handle if something go wrong, some retry mechanisam?
    public void appendObject(ProgressData progressData) {
        addLine(getJsonLineFromObject(progressData));
    }


    public ProgressData getLastObject() {
        String lastLine = readLastLine();
        if (lastLine == null) {
            return null;
        }
        return getObjectFromJsonLine(lastLine);
    }

    public void writeCurrentMomentToNote(String newText) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.newLine();
            //sleep added to notepad++ have chance to auto-save on autofocus :P
            Thread.sleep(1000);
            writer.write(newText);
            System.out.println("Note added to the text file." + newText);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
