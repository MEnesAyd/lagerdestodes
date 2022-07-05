//Describes a FSM of the current action
public enum ActionType {
    NONE, //Idle
    STORE,
    OUTSOURCE,
    RESTORE_SRC,
    RESTORE_DEST,
    DESTROY
}
