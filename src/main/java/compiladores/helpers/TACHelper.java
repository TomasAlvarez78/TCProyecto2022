package compiladores.helpers;

import compiladores.Clases.SharedVariable;
import compiladores.Clases.TACLevel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TACHelper {
    
    private static TACHelper instance;    
    private final FileOutputStream tacFO;

    int tempVarsCount;
    int labelsCount;
    int sharedCount;

    Map<String,List<SharedVariable>> sharedVariables;

    public List<TACLevel> levels;

    public static TACHelper getInstance() {
        if(instance == null) {
            try {
                File tacDir = new File("TAC");
                if(!tacDir.exists()) {
                    tacDir.mkdir();
                }

                instance = new TACHelper();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }
    
    private TACHelper() throws FileNotFoundException {
        tacFO = new FileOutputStream("TAC/output.tac",false);

        tempVarsCount = 0;
        labelsCount = 0;
        sharedVariables = new HashMap<>();

        levels = new ArrayList<>();
    }

    public void writeTAC(String tac) {
        try {
            tacFO.write((tac + '\n').getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SharedVariable> getFunctionVariables(String function){
        List<SharedVariable> variables = sharedVariables.get(function);

        if(variables == null){
            variables = new ArrayList<>();
            sharedVariables.put(function, variables);
        }

        return variables;
    }

    public String getNextLabel(){
        return "L" + ++labelsCount;
    }

    public String getNextSharedVariable(){
        return "S" + ++sharedCount;
    }

    public String getNextTempVariable(){
        return "T" + ++tempVarsCount;
    }

    public String getCurrentTempVariable() {
        return "T" + tempVarsCount;
    }

    public List<TACLevel> getLevels() {
        return levels;
    }

    public TACLevel getLastLevel(){
        if(levels.size() > 0)
            return levels.get(levels.size()-1);
        return null;
    }

    public TACLevel addLevel(){
        TACLevel level = new TACLevel();
        levels.add(level);

        return level;
    }

    public void removeLastLevel(){
        if(levels.size() > 0)
            levels.remove(levels.size()-1);
    }
}