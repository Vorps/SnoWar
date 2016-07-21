package me.vorps.snowar.utils;

import java.io.*;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class WorldUtils {
	
	private static void copyFolder(File src, File dest) throws IOException {
		if(src.isDirectory()){
			if(!dest.exists()){
				dest.mkdir();
			}
			String files[] = src.list();
			for(String file : files){
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(srcFile, destFile);
			}
		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			
			byte[] buffer = new byte[2048];
			
			int length;
			while((length = in.read(buffer)) > 0){
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}

    public static void initMap(String from, String to){
        File fromFile = new File(from);
        File toFile = new File(to);
        try{
            WorldUtils.copyFolder(fromFile, toFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
