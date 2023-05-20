package hctk.core.orm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RepoBackendFile extends AbstractRepoBackend {

    protected int lastId;
    protected HashMap<Integer, AbstractEntity> map;

    public RepoBackendFile(String name, AbstractEntity prototype) {
        super(name, prototype);
        map = new HashMap<>();
    }

    @Override
    public void init() {
        readFile();
    }

    @Override
    public AbstractEntity get(int id) {
        return map.get(id);
    }

    @Override
    public AbstractEntity save(AbstractEntity entity) {
        int id = ++lastId;
        entity.setId(id);
        map.put(id, entity);
        writeFile();
        return entity;
    }

    @Override
    public void saveList(List<AbstractEntity> list) {
        map.clear();
        map.putAll(list.stream().collect(Collectors.toMap(AbstractEntity::getId, item -> item)));
        writeFile();
    }

    @Override
    public void list(List<AbstractEntity> list) {
        map.forEach((key, val) -> {
            list.add(val);
        });
    }

    protected void writeFile() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(getRepoFilePath()));
            List<PropDef> props = prototype.getProps();
            for (Map.Entry<Integer, AbstractEntity> set : map.entrySet()) {
                writer.write(makeLine(set.getKey(), set.getValue(), props));
            };
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(RepoBackendFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void readFile() {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(getRepoFilePath()));
            map.clear();
            List<PropDef> props = prototype.getProps();
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> params = parseLine(line, props);
                int id = Integer.valueOf(params.remove(0));
                if(lastId < id) {
                    lastId = id;
                }
                addEntity(id, props, params);
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(RepoBackendFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<String> parseLine(String line, List<PropDef> props) {
        List<String> params = new LinkedList<>();
        int len = line.length();
        int state = 0;
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < len; ++i) {
            boolean isFirst = (i == 0);
            boolean isLast = (i == len - 1);
            char cur = line.charAt(i);
            char prev = (isFirst ? 0 : line.charAt(i - 1));
            char next = (isLast ? 0 : line.charAt(i + 1));
            if (cur == '\r' && state != 2) {
                continue;
            }
            if (cur == '"') {
                if (state == 0) {
                    state = 2;
                    continue;
                } else {
                    if (prev == '"') {
                        buf.append(cur);
                    }
                    if (next == ',') {
                        state = 0;
                    }
                    continue;
                }
            }
            if (isLast) {
                buf.append(cur);
            }
            if (((cur == ',' || cur == '\n') && state != 2) || isLast) {
                params.add(buf.toString());
                buf.delete(0, buf.length());
                state = 0;
                continue;
            }
            buf.append(cur);
            if (state == 0) {
                state = 1;
            }
        }
        return params;
    }

    private String makeLine(int id, AbstractEntity entity, List<PropDef> props) {
        StringBuilder buf = new StringBuilder();
        buf.append(id);
        buf.append(',');
        List<String> data = entity.getPropsData(props);
        for (int i = 0; i < props.size(); ++i) {
            String type = props.get(i).getType();
            switch (type) {
                case "string" ->
                    buf.append(quoteString(data.get(i)));
                default ->
                    buf.append(data.get(i));
            }
            buf.append(',');
        }
        buf.deleteCharAt(buf.length() - 1).append(System.lineSeparator());
        return buf.toString();
    }

    protected String getRepoFilePath() {
        return "./data/repo/" + name + ".csv";
    }

    private String quoteString(String str) {
        if (str.indexOf(',') != -1 || str.indexOf('"') != -1) {
            StringBuilder buf = new StringBuilder();
            buf.append('"');
            int len = str.length();
            for (int i = 0; i < len; ++i) {
                char cur = str.charAt(i);
                buf.append(cur);
                if (cur == '"' && i != 0 && i < (len - 1)) {
                    buf.append('"');
                }
            }
            buf.append('"');
            return buf.toString();
        }
        return str;
    }

    private void addEntity(int id, List<PropDef> props, List<String> params) {
        AbstractEntity entity = prototype.newInstance();
        entity.setId(id);
        entity.setPropsData(props, params);
        map.put(id, entity);
    }

}
