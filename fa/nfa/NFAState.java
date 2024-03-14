package fa.nfa;


import java.util.Set;

import fa.State;

/**
 * Represents a state within a NFA.
 * Extends the State class to include comparison capabilities to support
 * in ordering collections.
 * @author Axel Murillo
 * @author Julia Melchert
 */
public interface NFAState extends State {

    public Map<Character, AbstractSet<NFAState>> transitions;

    /**
     *
     * @param name name for state
     */
    public NFAState(String name) {
        super(name);
        this.transitions = new Map<Character, AbstractSet<NFAState>>();
    }

    /**
     * Compares the current object's name to the passed in object's name
     * @param state the object to be compared
     * @return int for comparison of states, -1 if less than (less characters), 0 if equal, and 1 if greater than (more characters)
     */
    public int compareTo(NFAState state) {
        return state.getName().compareTo(this.getName());
    }

    /*
     * Assumes toStates and onSymb are valid
    */
    public addTransition(AbstractSet<NFAState> toStates, char onSymb) {
        // If a transition on onSymb already exists, add to its set of destination states
        if (this.transitions.containsKey(onSymb)) {
            AbstractSet<NFAState> currentVal = this.transitions.get(onSymb);
            currentVal.addAll(toStates);
            this.transitions.put(onSymb, currentVal);
        
        // Otherwise, create a new transition with the symbol and states
        } else {
            this.transitions.put(onSymb, toStates);
        }
    }

}