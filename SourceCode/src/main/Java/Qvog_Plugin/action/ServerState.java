package Qvog_Plugin.action;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.project.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@State(name = "config.xml")
class ServerState implements PersistentStateComponent<ServerState.State> {

    private static final Logger log = LoggerFactory.getLogger(ServerState.class);

    public static ServerState getInstance(Project project) {
        // implementation according to Application/Project level service
        return project.getService(ServerState.class);
    }

    static class State {
        public String ip;
        public int port;
        public String toolsPath;
//        public String targetFileName;
    }

    private State myState = new State();

    @Override
    public State getState() {
        return myState;
    }

    @Override
    public void loadState(@NotNull State state) {
        myState = state;
    }

    public void load(String ip, int port, String toolsPath) {
        State newState = new State();
        newState.ip = ip;
        newState.port = port;
        newState.toolsPath = toolsPath;
        loadState(newState);
    }

//    public void loadFilename(String filename) {
//        State state = getState();
//        state.targetFileName = filename;
//        loadState(state);
//    }
}
