package dao;

import model.Obstaculo;
import persistence.FileManager;

import java.util.ArrayList;
import java.util.List;

public class ObstaculoDAO {
    private final FileManager fm;

    public ObstaculoDAO(FileManager fm) {
        this.fm = fm;
    }

    public List<Obstaculo> cargar() {
        List<String> lines = fm.readLines("obstacles.csv");
        List<Obstaculo> out = new ArrayList<>();
        for (String l : lines) {
            if (l.trim().isEmpty() || l.startsWith("#")) continue;
            String[] p = l.split(";");
            if (p.length < 4) continue;
            int x = Integer.parseInt(p[0]);
            int y = Integer.parseInt(p[1]);
            int w = Integer.parseInt(p[2]);
            int h = Integer.parseInt(p[3]);
            out.add(new Obstaculo(x, y, w, h));
        }
        return out;
    }

    public void guardar(List<Obstaculo> obs) {
        List<String> out = new ArrayList<>();
        out.add("# x;y;w;h");
        for (Obstaculo o : obs) {
            out.add(o.getX() + ";" + o.getY() + ";" + o.getW() + ";" + o.getH());
        }
        fm.writeLines("obstacles.csv", out);
    }
}