package JavaFXTest.First;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;

public class CopyClass implements Callable<File>  {
	
	private File file;
	
	public CopyClass(File file) {
		this.file = file;
	}

	@Override
	public File call() throws Exception {
		
		File newFile = null;
		
		try {
			 
			 String newPath = Files.copy(file.toPath(),(new File(file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING).toString();
			 newFile = new File(newPath);								 
			 
		} catch (IOException e) {						
			e.printStackTrace();
		}
		
		return newFile;
	}

}
