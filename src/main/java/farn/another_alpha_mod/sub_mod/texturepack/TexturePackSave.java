package farn.another_alpha_mod.sub_mod.texturepack;

import java.io.*;
import net.minecraft.client.Minecraft;

public class TexturePackSave {
    private static final String FILE_NAME = "texturepack_save.txt";

    public static File getConfigFile() {
        File dir = new File(Minecraft.getRunDirectory(), "texturepacks");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, FILE_NAME);
    }

    // Save currently selected texture pack
    public static void saveSelectedPack(String packName) {
        try {
            File file = getConfigFile();
            FileWriter writer = new FileWriter(file);
            writer.write(packName);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load selected texture pack name
    public static String loadSelectedPack() {
        File file = getConfigFile();
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String pack = reader.readLine();
                reader.close();
                return pack;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; // means default
    }
}
