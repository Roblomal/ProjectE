package moze_intel.utils;

import moze_intel.MozeCore;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MozeLogger 
{
	private static Logger logger;
	
	public MozeLogger()
	{
		logger = LogManager.getLogger(MozeCore.MODID);
	}
	
	public static void log(Level level, String msg)
	{
		logger.log(level, msg);
	}
	
	public static void logInfo(String msg)
	{
		logger.info(msg);
	}
	
	public static void logWarn(String msg)
	{
		logger.warn(msg);
	}
	
	public static void logFatal(String msg)
	{
		logger.fatal(msg);
	}
}
