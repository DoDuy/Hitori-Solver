package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration
 * @author DoDuy
 */
public class Config {
    public static final int MIN_ROWS;
    public static final int MIN_COLUMNS;
    public static final int MAX_ROWS;
    public static final int MAX_COLUMNS;
    public static final int SELECTED_ROWS;
    public static final int SELECTED_COLUMNS;
    
    public static final String[][] SOLVER_NAME = {{"ChainAndCircle1v0",         "CC"},             //1
                                                  {"ConnecFrom1stCell1v0",      "C1st"},        //2
                                                  {"ConnecFrom1stCell2v0",      "2.2.0 Connect from 1st Cell Plus"},   //3
                                                  {"ConnectivityEncoding1v0",   "3.1.0 Connectivity Encoding use Zone"},        //4
                                                  {"ConnectivityEncoding1v1",   "CE"},   //5
                                                  {"ConnectivityEncoding2v0",   "3.2.0 Connectivity Encoding + SwP"},  //6
                                                  {"ConnectivityEncoding2v1",   "3.2.1 ConnectEnc use Zones + SwP"},   //7
                                                  {"ConnectivityEncoding2v2",   "CE+"}, //8
                                                  {"SearchWithPatterns0v0",     "4.0.1 SWP Old version Tiep"},         //9
                                                  {"SearchWithPatterns0v1",     "4.0.2 SWP Old version Anh"},          //10
                                                  {"SearchWithPatterns1v0",     "4.1.0 SWP - 8 Pattern - Normal cter"},//11
                                                  {"SearchWithPatterns1v1",     "4.1.1 SWP - more EasyPattern - cter"},//12
                                                  {"SearchWithPatterns2v0",     "4.2.0 SWP - use Create"},             //13
                                                  {"SearchWithPatterns2v1",     "SWP"},          //14
                                                  {"PatternEncoding1v0",        "5.1.0 Pattern Encoding"}};            //15
    public static final int SOLVER_SELECTED;
    
    public static final int TAB_SELECTED;
    
    public static final int NUMBER_OF_PREY;
    public static final int TAB_MULTI_SELECTED_NCIRCLE;
    public static final int TAB_MULTI_SELECTED_NMAP;
    public static final int TAB_MULTI_SELECTED_NMAPSIZE;
    
    public static final int TAB_CREATE_MAP_SELECTED_NMAP;
    public static final int TAB_CREATE_MAP_SELECTED_FROM_PERCENT;//%
    public static final int TAB_CREATE_MAP_SELECTED_TO_PERCENT;//%
    public static final String PATH_CREATE_MAP_DATA;
    
    public static final String PATH_GLUEMINISAT;
    public static final String PATH_LINGELING;
    public static final int SELECTED_SATSOLVER;
    
    public static final int SELECTED_TIMER;
    
    public static final String PATH_MAP;
    public static final String PATH_ICON_GAME;
    public static final int HEADER_LENGTH_FILE_CNF;
    public static final String PATH_FILE_CNF;
    public static final String PATH_FILE_CNF_OUT;
    public static final String PATH_SAT4J_JAR_FILE;
    public static final String PATH_FILE_EXCEL;
    //set timeout
    public static final long TIMEOUT;//s
    public static final int TIMEOUT_SAT4J;
    //String in view
    public static final String STROUT_ERROR_IOFILE = "Error while write file cnf!";
    public static final String STROUT_ERROR_GETCLASS_FORNAME = "This solver can not find!";
    public static final String STROUT_ERROR_STOPWATCH = "An error while StopWatch run<br> Please repair your code!";
    public static final String STROUT_MAP_ERROR = "One map is error !";
    public static final String STROUT_NO_MORE_SOLUTION = "No more solution !";
    public static final String STROUT_NO_PUZZLE = "No map in size ";
    public static final String STROUT_NO_SAT_SOLVER = "Not found Sat solver";
    public static final String STROUT_NO_RESULT = "This puzzle don't have solution!";
    public static final String STROUT_CHECK_OK = "This solution is perfect!";
    public static final String STROUT_CHECK_ERROR = "An error in this!";
    public static final String STROUT_RULE = "<u>Rule 1</u>: numbers must not appear<br>&nbsp;&nbsp;&nbsp;"
            + "&nbsp;more than once in each row and<br>&nbsp;&nbsp;&nbsp;&nbsp;each column.<br><br><u>Rule"
            + " 2</u>: painted cells are never<br>&nbsp;&nbsp;&nbsp;&nbsp;adjacent in a row or a column.<br><br><u>"
            + "Rule 3</u>: unpainted cells create a single<br>&nbsp;&nbsp;&nbsp;&nbsp;continuous area, undivided"
            + " by<br>&nbsp;&nbsp;&nbsp;&nbsp;painted cells.";
    
    static {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("config.properties"));
        } catch (IOException ex) {
            //System.out.println("IOException config.properties");
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        MIN_ROWS = Integer.parseInt(prop.getProperty("MIN_ROWS"));
        MIN_COLUMNS = Integer.parseInt(prop.getProperty("MIN_COLUMNS"));
        MAX_ROWS = Integer.parseInt(prop.getProperty("MAX_ROWS"));
        MAX_COLUMNS = Integer.parseInt(prop.getProperty("MAX_COLUMNS"));
        SELECTED_ROWS = Integer.parseInt(prop.getProperty("SELECTED_ROWS"));
        SELECTED_COLUMNS = Integer.parseInt(prop.getProperty("SELECTED_COLUMNS"));
        SOLVER_SELECTED = Integer.parseInt(prop.getProperty("SOLVER_SELECTED"));
        TAB_SELECTED = Integer.parseInt(prop.getProperty("TAB_SELECTED"));
        NUMBER_OF_PREY = Integer.parseInt(prop.getProperty("NUMBER_OF_PREY"));
        TAB_MULTI_SELECTED_NCIRCLE = Integer.parseInt(prop.getProperty("TAB_MULTI_SELECTED_NCIRCLE"));
        TAB_MULTI_SELECTED_NMAP = Integer.parseInt(prop.getProperty("TAB_MULTI_SELECTED_NMAP"));
        TAB_MULTI_SELECTED_NMAPSIZE = Integer.parseInt(prop.getProperty("TAB_MULTI_SELECTED_NMAPSIZE"));
        TAB_CREATE_MAP_SELECTED_NMAP = Integer.parseInt(prop.getProperty("TAB_CREATE_MAP_SELECTED_NMAP"));
        TAB_CREATE_MAP_SELECTED_FROM_PERCENT = Integer.parseInt(prop.getProperty("TAB_CREATE_MAP_SELECTED_FROM_PERCENT"));
        TAB_CREATE_MAP_SELECTED_TO_PERCENT = Integer.parseInt(prop.getProperty("TAB_CREATE_MAP_SELECTED_TO_PERCENT"));
        PATH_CREATE_MAP_DATA = prop.getProperty("PATH_CREATE_MAP_DATA");
        PATH_GLUEMINISAT = prop.getProperty("PATH_GLUEMINISAT");
        PATH_LINGELING = prop.getProperty("PATH_LINGELING");
        SELECTED_SATSOLVER = Integer.parseInt(prop.getProperty("SELECTED_SATSOLVER"));
        SELECTED_TIMER = Integer.parseInt(prop.getProperty("SELECTED_TIMER"));
        PATH_MAP = prop.getProperty("PATH_MAP");
        PATH_ICON_GAME = prop.getProperty("PATH_ICON_GAME");
        HEADER_LENGTH_FILE_CNF = Integer.parseInt(prop.getProperty("HEADER_LENGTH_FILE_CNF"));
        PATH_FILE_CNF = prop.getProperty("PATH_FILE_CNF");
        PATH_FILE_CNF_OUT = prop.getProperty("PATH_FILE_CNF_OUT");
        PATH_SAT4J_JAR_FILE = prop.getProperty("PATH_SAT4J_JAR_FILE");
        PATH_FILE_EXCEL = prop.getProperty("PATH_FILE_EXCEL");
        TIMEOUT = Integer.parseInt(prop.getProperty("TIMEOUT"));
        TIMEOUT_SAT4J = Integer.parseInt(prop.getProperty("TIMEOUT_SAT4J"));
    }
}