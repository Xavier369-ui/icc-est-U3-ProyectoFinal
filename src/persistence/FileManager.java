package persistence;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

public class FileManager {
    private final Path baseDir;

    public FileManager(String baseDir) {
        this.baseDir = Paths.get(baseDir);
        try { Files.createDirectories(this.baseDir); } catch (IOException ignored) {}
    }

    public List<String> readLines(String name) {
        Path p = baseDir.resolve(name);
        if (!Files.exists(p)) return Collections.emptyList();
        try {
            return Files.readAllLines(p, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public void writeLines(String name, List<String> lines) {
        Path p = baseDir.resolve(name);
        try {
            Files.createDirectories(p.getParent());
            Files.write(p, lines, StandardCharsets.UTF_8);
        } catch (IOException ignored) {}
    }

    public BufferedImage loadImage(String name) {
        Path p = baseDir.resolve(name);
        if (!Files.exists(p)) return null;
        try {
            return ImageIO.read(p.toFile());
        } catch (IOException e) {
            return null;
        }
    }

    public void appendTimingCsv(String algoritmo, int nodos, int aristas, long nanos) {
        Path p = baseDir.resolve("timings.csv");
        boolean exists = Files.exists(p);
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String line = ts + ";" + algoritmo + ";" + nodos + ";" + aristas + ";" + nanos;
        try (Writer w = new OutputStreamWriter(new FileOutputStream(p.toFile(), true), StandardCharsets.UTF_8)) {
            if (!exists) w.write("timestamp;algoritmo;nodos;aristas;nanos\n");
            w.write(line + "\n");
        } catch (IOException ignored) {}
    }
}