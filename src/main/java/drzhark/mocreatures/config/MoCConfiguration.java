/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
/*
 * This software is provided under the terms of the Minecraft Forge Public
 * License v1.0.
 */

package drzhark.mocreatures.config;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableSet;
import drzhark.mocreatures.MoCTools;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static drzhark.mocreatures.config.MoCProperty.Type.*;

/**
 * This class offers advanced configurations capabilities, allowing to provide
 * various categories for MoCConfiguration variables.
 */
public class MoCConfiguration {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_BLOCK = "block";
    public static final String CATEGORY_ITEM = "item";
    public static final String ALLOWED_CHARS = "._-";
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String CATEGORY_SPLITTER = ".";
    public static final String NEW_LINE;
    public static final CharMatcher allowedProperties = CharMatcher.javaLetterOrDigit().or(CharMatcher.anyOf(ALLOWED_CHARS));
    private static final Pattern CONFIG_START = Pattern.compile("START: \"([^\"]+)\"");
    private static final Pattern CONFIG_END = Pattern.compile("END: \"([^\"]+)\"");
    private static MoCConfiguration PARENT = null;

    static {
        NEW_LINE = System.getProperty("line.separator");
    }

    private final Object2ObjectLinkedOpenHashMap<String, MoCConfiguration> children = new Object2ObjectLinkedOpenHashMap<>();
    public Object2ObjectLinkedOpenHashMap<String, MoCConfigCategory> categories = new Object2ObjectLinkedOpenHashMap<>();
    public String defaultEncoding = DEFAULT_ENCODING;
    public boolean isChild = false;
    File file;
    private boolean caseSensitiveCustomCategories;
    private String fileName = null;
    private boolean changed = false;

    public MoCConfiguration() {
    }

    /**
     * Create a configuration file for the file given in parameter.
     */
    public MoCConfiguration(File file) {
        this.file = file;
        String basePath = FMLLoader.getGamePath().toString().replace(File.separatorChar, '/').replace("/.", "");
        String path = file.getAbsolutePath().replace(File.separatorChar, '/').replace("/./", "/").replace(basePath, "");
        if (PARENT != null) {
            PARENT.setChild(path, this);
            this.isChild = true;
        } else {
            this.fileName = path;
            load();
        }
    }

    public MoCConfiguration(File file, boolean caseSensitiveCustomCategories) {
        this(file);
        this.caseSensitiveCustomCategories = caseSensitiveCustomCategories;
    }

    public MoCConfiguration(File file, boolean caseSensitiveCustomCategories, boolean useNewLine) {
        this(file);
    }

    public static void enableGlobalConfig() {
        PARENT = new MoCConfiguration(new File(FMLPaths.CONFIGDIR.get().toFile(), "global.cfg"));
        PARENT.load();
    }

    public MoCProperty get(String category, String key) {
        MoCConfigCategory cat = getCategory(category);
        if (cat.containsKey(key)) {
            return cat.get(key);
        }
        return null;
    }

    public MoCProperty get(String category, String key, int defaultValue) {
        return get(category, key, defaultValue, null);
    }

    public MoCProperty get(String category, String key, int defaultValue, String comment) {
        MoCProperty prop = get(category, key, Integer.toString(defaultValue), comment, INTEGER);
        if (!prop.isIntValue()) {
            prop.set(defaultValue);
        }
        return prop;
    }

    public MoCProperty get(String category, String key, boolean defaultValue) {
        return get(category, key, defaultValue, null);
    }

    public MoCProperty get(String category, String key, boolean defaultValue, String comment) {
        MoCProperty prop = get(category, key, Boolean.toString(defaultValue), comment, BOOLEAN);
        if (!prop.isBooleanValue()) {
            prop.set(defaultValue);
        }
        return prop;
    }

    public MoCProperty get(String category, String key, double defaultValue) {
        return get(category, key, defaultValue, null);
    }

    public MoCProperty get(String category, String key, double defaultValue, String comment) {
        MoCProperty prop = get(category, key, Double.toString(defaultValue), comment, DOUBLE);
        if (!prop.isDoubleValue()) {
            prop.set(defaultValue);
        }
        return prop;
    }

    public MoCProperty get(String category, String key, String defaultValue) {
        return get(category, key, defaultValue, null);
    }

    public MoCProperty get(String category, String key, String defaultValue, String comment) {
        return get(category, key, defaultValue, comment, STRING);
    }

    public MoCProperty get(String category, String key, List<String> defaultValue) {
        return get(category, key, defaultValue, null);
    }

    public MoCProperty get(String category, String key, List<String> defaultValue, String comment) {
        return get(category, key, defaultValue, comment, STRING);
    }

    public MoCProperty get(String category, String key, int[] defaultValue) {
        return get(category, key, defaultValue, null);
    }

    public MoCProperty get(String category, String key, int[] defaultValue, String comment) {
        List<String> values = new ArrayList<>();
        for (int j : defaultValue) {
            values.add(Integer.toString(j));
        }

        MoCProperty prop = get(category, key, values, comment, INTEGER);
        if (!prop.isIntList()) {
            prop.valueList = values;
        }

        return prop;
    }

    public MoCProperty get(String category, String key, double[] defaultValue) {
        return get(category, key, defaultValue, null);
    }

    public MoCProperty get(String category, String key, double[] defaultValue, String comment) {
        List<String> values = new ArrayList<>();
        for (double v : defaultValue) {
            values.add(Double.toString(v));
        }

        MoCProperty prop = get(category, key, values, comment, DOUBLE);

        if (!prop.isDoubleList()) {
            prop.valueList = values;
        }

        return prop;
    }

    public MoCProperty get(String category, String key, boolean[] defaultValue) {
        return get(category, key, defaultValue, null);
    }

    public MoCProperty get(String category, String key, boolean[] defaultValue, String comment) {
        List<String> values = new ArrayList<>();
        for (boolean b : defaultValue) {
            values.add(Boolean.toString(b));
        }

        MoCProperty prop = get(category, key, values, comment, BOOLEAN);

        if (!prop.isBooleanList()) {
            prop.valueList = values;
        }

        return prop;
    }

    public MoCProperty get(String category, String key, String defaultValue, String comment, MoCProperty.Type type) {
        if (!this.caseSensitiveCustomCategories) {
            category = category.toLowerCase(Locale.ENGLISH);
        }

        MoCConfigCategory cat = getCategory(category);

        if (cat.containsKey(key)) {
            MoCProperty prop = cat.get(key);

            if (prop.getTypeMoC() == null) {
                prop = new MoCProperty(prop.getName(), prop.value, type);
                cat.set(key, prop);
            }

            prop.comment = comment;
            return prop;
        } else if (defaultValue != null) {
            MoCProperty prop = new MoCProperty(key, defaultValue, type);
            prop.set(defaultValue); //Set and mark as dirty to signify it should save
            cat.put(key, prop);
            prop.comment = comment;
            return prop;
        } else {
            return null;
        }
    }

    public MoCProperty get(String category, String key, List<String> defaultValue, String comment, MoCProperty.Type type) {
        if (!this.caseSensitiveCustomCategories) {
            category = category.toLowerCase(Locale.ENGLISH);
        }

        MoCConfigCategory cat = getCategory(category);

        if (cat.containsKey(key)) {
            MoCProperty prop = cat.get(key);

            if (prop.getTypeMoC() == null) {
                prop = new MoCProperty(prop.getName(), prop.getString(), type);
                cat.put(key, prop);
            }

            prop.comment = comment;

            return prop;
        } else if (defaultValue != null) {
            MoCProperty prop = new MoCProperty(key, defaultValue, type);
            prop.comment = comment;
            cat.put(key, prop);
            return prop;
        } else {
            return null;
        }
    }

    public boolean hasCategory(String category) {
        return this.categories.get(category) != null;
    }

    public boolean contains(String category, String key) {
        MoCConfigCategory cat = this.categories.get(category);
        return cat != null && cat.containsKey(key);
    }

    public void load() {
        if (PARENT != null && PARENT != this) {
            return;
        }

        BufferedReader buffer = null;
        UnicodeInputStreamReader input = null;
        try {
            if (this.file.getParentFile() != null) {
                this.file.getParentFile().mkdirs();
            }

            if (!this.file.exists() && !this.file.createNewFile()) {
                return;
            }

            if (this.file.canRead()) {
                input = new UnicodeInputStreamReader(Files.newInputStream(this.file.toPath()), this.defaultEncoding);
                this.defaultEncoding = input.getEncoding();
                buffer = new BufferedReader(input);

                String line;
                MoCConfigCategory currentCat = null;
                MoCProperty.Type type = null;
                ArrayList<String> tmpList = null;
                int lineNum = 0;
                String name = null;

                while (true) {
                    lineNum++;
                    line = buffer.readLine();
                    if (line == null) {
                        break;
                    }

                    Matcher start = CONFIG_START.matcher(line);
                    Matcher end = CONFIG_END.matcher(line);

                    if (start.matches()) {
                        this.fileName = start.group(1);
                        this.categories = new Object2ObjectLinkedOpenHashMap<>();
                        continue;
                    } else if (end.matches()) {
                        this.fileName = end.group(1);
                        MoCConfiguration child = new MoCConfiguration();
                        child.categories = this.categories;
                        this.children.put(this.fileName, child);
                        continue;
                    }

                    int nameStart = -1, nameEnd = -1;
                    boolean skip = false;
                    boolean quoted = false;

                    for (int i = 0; i < line.length() && !skip; ++i) {
                        if (Character.isLetterOrDigit(line.charAt(i)) || ALLOWED_CHARS.indexOf(line.charAt(i)) != -1 || (quoted && line.charAt(i) != '"')) {
                            if (nameStart == -1) {
                                nameStart = i;
                            }

                            nameEnd = i;
                        } else if (Character.isWhitespace(line.charAt(i))) {
                            // ignore space charaters
                        } else {
                            switch (line.charAt(i)) {
                                case '#':
                                    skip = true;
                                    continue;

                                case '"':
                                    if (quoted) {
                                        quoted = false;
                                    }
                                    if (nameStart == -1) {
                                        quoted = true;
                                    }
                                    break;

                                case '{':
                                    name = line.substring(nameStart, nameEnd + 1);
                                    String qualifiedName = MoCConfigCategory.getQualifiedName(name, currentCat);

                                    MoCConfigCategory cat = this.categories.get(qualifiedName);
                                    if (cat == null) {
                                        currentCat = new MoCConfigCategory(name, currentCat);
                                        this.categories.put(qualifiedName, currentCat);
                                    } else {
                                        currentCat = cat;
                                    }
                                    name = null;

                                    break;

                                case '}':
                                    if (currentCat == null) {
                                        throw new RuntimeException(String.format("Config file corrupt, attepted to close to many categories '%s:%d'", this.fileName, lineNum));
                                    }
                                    currentCat = currentCat.parent;

                                    break;

                                case '=':
                                    name = line.substring(nameStart, nameEnd + 1);

                                    if (currentCat == null) {
                                        throw new RuntimeException(String.format("'%s' has no scope in '%s:%d'", name, this.fileName, lineNum));
                                    }

                                    MoCProperty prop = new MoCProperty(name, line.substring(i + 1), type, true);
                                    i = line.length();

                                    currentCat.set(name, prop);

                                    break;

                                case ':':
                                    type = MoCProperty.Type.tryParse(line.substring(nameStart, nameEnd + 1).charAt(0));
                                    nameStart = nameEnd = -1;
                                    break;

                                case '<':

                                    if (tmpList != null) {
                                        throw new RuntimeException(String.format("Malformed list MoCProperty \"%s:%d\"", this.fileName, lineNum));
                                    }

                                    name = line.substring(nameStart, nameEnd + 1);

                                    if (currentCat == null) {
                                        throw new RuntimeException(String.format("'%s' has no scope in '%s:%d'", name, this.fileName, lineNum));
                                    }

                                    tmpList = new ArrayList<>();

                                    if ((line.length() > i + 1)) {
                                        if (line.charAt(i + 1) == '>') {
                                            i++;
                                        } else {
                                            line = line.substring(i + 1);
                                            String[] values = line.split("[:>]");
                                            Collections.addAll(tmpList, values);
                                            i = line.length() - 1;
                                        }
                                    } else {
                                        skip = true;
                                        break;
                                    }

                                case '>':
                                    if (tmpList == null) {
                                        throw new RuntimeException(String.format("Corrupted config detected! Malformed list MoCProperty \"%s:%d\". Please delete your MoCreatures configs and restart to fix error.", this.fileName, lineNum));
                                    }
                                    currentCat.set(name, new MoCProperty(name, tmpList, type));
                                    name = null;
                                    tmpList = null;
                                    type = null;
                                    break;

                                default:
                                    throw new RuntimeException(String.format("Corrupted config detected! Unknown character '%s' in '%s:%d'. Please delete your MoCreatures configs and restart to fix error.", line.charAt(i), this.fileName, lineNum));
                            }
                        }
                    }

                    if (quoted) {
                        throw new RuntimeException(String.format("Corrupted config detected! Unmatched quote in '%s:%d'. Please delete your MoCreatures configs and restart to fix error.", this.fileName, lineNum));
                    } else if (tmpList != null && !skip) {
                        tmpList.add(line.trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException ignored) {
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignored) {
                }
            }
        }

        resetChangedState();
    }

    public void save() {
        if (PARENT != null && PARENT != this) {
            PARENT.save();
            return;
        }

        try {
            if (this.file.getParentFile() != null) {
                this.file.getParentFile().mkdirs();
            }

            if (!this.file.exists() && !this.file.createNewFile()) {
                return;
            }
            if (this.file.canWrite()) {
                FileOutputStream fos = new FileOutputStream(this.file);
                BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(fos, this.defaultEncoding));

                buffer.write("# Configuration file" + NEW_LINE + NEW_LINE);

                if (this.fileName.contains("MoCreatures.cfg")) {
                    buffer.write("# Valid biome tags:" + NEW_LINE);
                    for (String tag : MoCTools.getAllBiomeTags()) {
                        buffer.write("# • " + tag + NEW_LINE);
                    }

                }

                if (this.children.isEmpty()) {
                    save(buffer);
                } else {
                    for (Map.Entry<String, MoCConfiguration> entry : this.children.entrySet()) {
                        buffer.write("START: \"" + entry.getKey() + "\"" + NEW_LINE);
                        entry.getValue().save(buffer);
                        buffer.write("END: \"" + entry.getKey() + "\"" + NEW_LINE + NEW_LINE);
                    }
                }

                buffer.close();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save(BufferedWriter out) throws IOException {
        for (MoCConfigCategory cat : this.categories.values()) {
            if (!cat.isChild()) // if category has no parent categories
            {
                cat.write(out, 0);
                out.newLine();
            }
        }
    }

    public MoCConfigCategory getCategory(String category) {
        MoCConfigCategory ret = this.categories.get(category.toLowerCase());

        if (ret == null) {
            if (category.contains(CATEGORY_SPLITTER)) {
                String[] hierarchy = category.split("\\" + CATEGORY_SPLITTER);
                MoCConfigCategory parent = this.categories.get(hierarchy[0]);

                if (parent == null) {
                    parent = new MoCConfigCategory(hierarchy[0]);
                    this.categories.put(parent.getQualifiedName(), parent);
                    this.changed = true;
                }

                for (int i = 1; i < hierarchy.length; i++) {
                    String name = MoCConfigCategory.getQualifiedName(hierarchy[i], parent);
                    MoCConfigCategory child = this.categories.get(name);

                    if (child == null) {
                        child = new MoCConfigCategory(hierarchy[i], parent);
                        this.categories.put(name, child);
                        this.changed = true;
                    }

                    ret = child;
                    parent = child;
                }
            } else {
                ret = new MoCConfigCategory(category);
                this.categories.put(category, ret);
                this.changed = true;
            }
        }

        return ret;
    }

    public void removeCategory(MoCConfigCategory category) {
        for (MoCConfigCategory child : category.getChildren()) {
            removeCategory(child);
        }

        if (this.categories.containsKey(category.getQualifiedName())) {
            this.categories.remove(category.getQualifiedName());
            if (category.parent != null) {
                category.parent.removeChild(category);
            }
            this.changed = true;
        }
    }

    public void addCustomCategoryComment(String category, String comment) {
        if (!this.caseSensitiveCustomCategories) {
            category = category.toLowerCase(Locale.ENGLISH);
        }
        getCategory(category).setComment(comment);
    }

    private void setChild(String name, MoCConfiguration child) {
        if (!this.children.containsKey(name)) {
            this.children.put(name, child);
            this.changed = true;
        } else {
            MoCConfiguration old = this.children.get(name);
            child.categories = old.categories;
            child.fileName = old.fileName;
            old.changed = true;
        }
    }

    public boolean hasChanged() {
        if (this.changed) {
            return true;
        }

        for (MoCConfigCategory cat : this.categories.values()) {
            if (cat.hasChanged()) {
                return true;
            }
        }

        for (MoCConfiguration child : this.children.values()) {
            if (child.hasChanged()) {
                return true;
            }
        }

        return false;
    }

    private void resetChangedState() {
        this.changed = false;
        for (MoCConfigCategory cat : this.categories.values()) {
            cat.resetChangedState();
        }

        for (MoCConfiguration child : this.children.values()) {
            child.resetChangedState();
        }
    }

    public Set<String> getCategoryNames() {
        return ImmutableSet.copyOf(this.categories.keySet());
    }

    public String getFileName() {
        if (this.file != null) {
            String fullName = this.file.getName();
            return fullName.substring(0, fullName.indexOf('.'));
        }
        return "undefined";
    }

    public File getFile() {
        return this.file;
    }

    public static class UnicodeInputStreamReader extends Reader {

        private final InputStreamReader input;

        public UnicodeInputStreamReader(InputStream source, String encoding) throws IOException {
            String enc = encoding;
            byte[] data = new byte[4];

            PushbackInputStream pbStream = new PushbackInputStream(source, data.length);
            int read = pbStream.read(data, 0, data.length);
            int size = 0;

            int bom16 = (data[0] & 0xFF) << 8 | (data[1] & 0xFF);
            int bom24 = bom16 << 8 | (data[2] & 0xFF);
            int bom32 = bom24 << 8 | (data[3] & 0xFF);

            if (bom24 == 0xEFBBBF) {
                enc = "UTF-8";
                size = 3;
            } else if (bom16 == 0xFEFF) {
                enc = "UTF-16BE";
                size = 2;
            } else if (bom16 == 0xFFFE) {
                enc = "UTF-16LE";
                size = 2;
            } else if (bom32 == 0x0000FEFF) {
                enc = "UTF-32BE";
                size = 4;
            } else if (bom32 == 0xFFFE0000) //This will never happen as it'll be caught by UTF-16LE,
            { //but if anyone ever runs across a 32LE file, I'd like to dissect it.
                enc = "UTF-32LE";
                size = 4;
            }

            if (size < read) {
                pbStream.unread(data, size, read - size);
            }

            this.input = new InputStreamReader(pbStream, enc);
        }

        public String getEncoding() {
            return this.input.getEncoding();
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            return this.input.read(cbuf, off, len);
        }

        @Override
        public void close() throws IOException {
            this.input.close();
        }
    }
}
