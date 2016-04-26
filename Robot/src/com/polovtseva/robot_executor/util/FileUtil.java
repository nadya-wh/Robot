package com.polovtseva.robot_executor.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Scanner;

/**
 * Created by User on 26.03.2016.
 */
public class FileUtil {

    static final Logger LOG = Logger.getLogger(FileUtil.class);

    public static String readFile(File file) {
        String code = new String();
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            LOG.error(e);
        }
        while (scanner.hasNext()) {
            code += scanner.nextLine() + "\n";
        }
        return code;
    }

    public static boolean writeFile(File file, String text) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "utf-8"));
            writer.write(text);
            return true;
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    LOG.error(e);
                }
            }
        }
        return false;

    }
}
