package com.realapps.engine.core.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore.Images;

import com.realapps.engine.Game;
import com.realapps.engine.core.debug.exception.RuntimeException;

public class BitmapManager {
	// Exception Classes
	@SuppressWarnings("serial")
	public static class NotFoundException extends RuntimeException {
		public NotFoundException(String message) {
			super(message);
		}
	}
	
	// Singleton Definition
	private BitmapManager() {
	}
	
	private static final BitmapManager mInstance = new BitmapManager();
	public static BitmapManager getManager() {
		return mInstance;
	}
	
	// Resource Managing
	private HashMap<String, Bitmap> mResourceTable =  new HashMap<String, Bitmap>();
	
	private boolean loadImage(String imageID, int resourceID) {
		Bitmap bitmap = ((BitmapDrawable)Game.getContext().getResources().getDrawable(resourceID)).getBitmap();
		if(bitmap == null) 
			throw new NotFoundException("Can't Find Resource ["+imageID+"]");
		
		mResourceTable.put(imageID, bitmap);
		return true;
	}
	private boolean loadImage(String imageID, String local_path) {
		try {
			Bitmap bitmap = Images.Media.getBitmap(Game.getContext().getContentResolver(), Uri.parse("file://"+local_path));
			if(bitmap == null) 
				throw new NotFoundException("Can't Find Resource ["+imageID+"]");
			
			mResourceTable.put(imageID, bitmap);
			return true;
		} catch (FileNotFoundException e) {
			throw new NotFoundException("Can't Find Resource ["+imageID+"]");
		} catch (IOException e) {
			throw new NotFoundException("Can't Find Resource ["+imageID+"]");
		}
	}

	public Bitmap getImage(int resourceID) {
		if(!mResourceTable.containsKey("@resource/"+resourceID)) {
			loadImage("@resource/"+resourceID, resourceID);
		}
		
		return mResourceTable.get("@resource/"+resourceID);
	}
	public Bitmap getImage(String local_path) {
		if(!mResourceTable.containsKey("@file/"+local_path)) {
			loadImage("@file/"+local_path, local_path);
		}
		
		return mResourceTable.get("@file/"+local_path);
	}
}
