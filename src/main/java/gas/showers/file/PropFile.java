package gas.showers.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropFile {
	private Properties prop;
	private String path;
	
	public static Properties getProperties(String filePath) {
		Properties _prop = new Properties();
		InputStream input = null;
	
		try {
			input = new FileInputStream(filePath);	
			_prop.load(input);
		} catch (IOException ex) {
			//ex.printStackTrace();
		} finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
		return _prop;
	}
	
	public static void saveProperties(String _path, Properties _prop) {
		File f = new File(_path);
		OutputStream out = null;
		try {
			out = new FileOutputStream(f);
			_prop.store(out, "");
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
	
	public PropFile(String _path) {
		path = _path;
		prop = PropFile.getProperties(path);
	}
	
	public void save(String _path) {
		PropFile.saveProperties(path, prop);
	}
	
	public void save() {
		this.save(path);
	}
	
	public Properties getProp() {
		return prop;
	}
}
