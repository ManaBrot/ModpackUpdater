package common.nw.creator.gui_legacy;

import java.io.File;

public interface IDropFileHandler {

	/**
	 * performs the file-drop handling
	 * @param file
	 * @return
	 */
	boolean dropFile(File file);
}
