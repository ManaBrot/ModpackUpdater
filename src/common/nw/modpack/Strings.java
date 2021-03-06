package common.nw.modpack;

public class Strings {
	
	private final static  String directDownload = "directDownload";
	private final static String userDownload = "userDownload";
	private final static String localFile = "localFile";
	
	public final static String jarDirectDownload = directDownload;
	public final static String jarUserDownload = userDownload;
	public final static String jarLocalFile = localFile; 

	public final static String jsonDirectDownload = directDownload;
	public final static String jsonUserDownload = userDownload;
	public final static String jsonLocalFile = localFile;
	
	public final static String modDirectDownload = directDownload;
	public final static String modUserDownload = userDownload;
	

	/** use filename as name */
	public final static String nameTypeFileName = "file";
	/** use forge/.litemod name */
	public final static String nameTypeZipEntry = "zipEntry";
	
	/** use filename as version */
	public final static String versionTypeFileName = "file";
	/** use forge/.litemod version */
	public final static String versionTypeZipEntry = "zipEntry";
	/** use file-md5 as version */
	public final static String versionTypeMD5 = "md5";
	/** tracks last updates and update on version String change */
	public final static String versionTypeTracked = "tracked";

}
