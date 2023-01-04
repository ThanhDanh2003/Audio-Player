
package mp3;
import java.io.File;

public class FilesDic {
	private static String directory;
	
	public FilesDic() {
		//directory  =s;
	}
	
	public static String getMusicFolderPath() {
		String Path = "";
		File origin = new File("");
		String during_development_src = "";
		int i =0;
		while(i<=1) {
			String currentFolder = origin.getAbsolutePath()+during_development_src;
			//Check if MusicFolder is in currentFolder, which is a Path.
			String returnedPath = returnMusicFolderDirectory(currentFolder);
			System.out.println("[0]"+returnedPath+" - "+currentFolder);
			//If we don't return '-1', not found,then break the sequence and 
			//Assign the Path.
			if(!returnedPath.equals("-1")) {
				Path = returnedPath;
				break;
			}
			// Else remove last folder from the current Path, then 
			//repeat with the new altered directory.
			String newPath = removeLastFolderFromPath(currentFolder);
			origin = new File(newPath);
			i++;
		}	
		if(i>2)
			Path = "Could Not find MusicFolder, Please create this directory within the scope of MusicTest!";
		return Path;
	}
	
	private static String removeLastFolderFromPath(String currentPath) {
	
		String revisedPath="";
	
		String[] dividedPath = currentPath.split("\\\\");
		for(int i =0; i<dividedPath.length-1; i++)
		{
			revisedPath +=dividedPath[i]+"\\";
		}
		return revisedPath;
	}

	private static String returnMusicFolderDirectory(String Directory) {

		File currentFolder = new File(Directory);
		String target = "MusicFolder";
		for(File files: currentFolder.listFiles()) {
			String file = files.getName();
			if(file.equals(target)) {
				return files.getAbsolutePath();
			}
		}
		return "-1";
	}
}
