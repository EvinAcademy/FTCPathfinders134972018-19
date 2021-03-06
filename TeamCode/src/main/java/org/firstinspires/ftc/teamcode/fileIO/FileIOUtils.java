package org.firstinspires.ftc.teamcode.fileIO;

import android.icu.text.SimpleDateFormat;
import android.os.Environment;

import org.firstinspires.ftc.teamcode.errorIO.LogUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public final class FileIOUtils {

    private static final String PREFERRED_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";

    public static final String CONFIGURATION_DIRECTORY = "FIRST";
    private static final String CONFIGURATION_EXTENSION = ".xml";

    public static final String ERROR_LOG_DIRECTORY = "FTCRobotController Error Logs";

    public static final String AUTONOMOUS_GENERATION_DIRECTORY = "FTCRobotController Generated " +
            "Autonomous";

    private static final String GENERIC_TEXT_EXTENSION = ".txt";

    public static File generatePath(String directory) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = new File(Environment.getExternalStorageDirectory(), directory);
            if (!path.exists()) {
                if (path.mkdirs()) {
                    return path;
                }
                return null;
            }
            return path;
        }
        return null;
    }

    public static File generateFile(File path, Purpose purpose, String name) {
        String givenName = name == null ? getSimpleDateFormat() : name;
        if (path != null && purpose != null) {
            switch (purpose) {
                case CONFIGURATION_FILE:
                    return generateFile(path, givenName, CONFIGURATION_EXTENSION);
                case AUTONOMOUS_GENERATION:
                    return generateFile(path, givenName, GENERIC_TEXT_EXTENSION);
                case ERROR_LOGGING:
                    return generateFile(path, getSimpleDateFormat(), GENERIC_TEXT_EXTENSION);
                default:
                    return generateFile(path, givenName, GENERIC_TEXT_EXTENSION);
            }
        }
        return null;
    }

    public static boolean writeToFile(File file, Transformer transformer, DOMSource source) {
        if (file != null) {
            if (transformer != null) {
                if (source != null) {
                    try {
                        transformer.transform(source, new StreamResult(file));
                    } catch (TransformerException e) {
                        LogUtils.logError(e.getMessage());
                    }
                }
            }
        }
        return false;
    }

    public static boolean writeToFile(File file, String input) {
        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.append(input);
                writer.flush();
                writer.close();
                return true;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return false;
    }

    public static String getSimpleDateFormat() {
        return new SimpleDateFormat(PREFERRED_DATE_FORMAT)
                .format(new Timestamp(System.currentTimeMillis()));
    }

    private static File generateFile(File path, String name, String fileExtension) {
        if (path != null) {
            return new File(path, name + fileExtension);
        }
        return null;
    }
}
