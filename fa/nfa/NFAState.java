package fa.nfa;


import java.util.*;

import fa.State;

/**
 * Represents a state within a NFA.
 * Extends the State class to include comparison capabilities to support
 * in ordering collections.
 * @author Axel Murillo
 * @author Julia Melchert
 */
public class NFAState extends State implements Comparable<NFAState> {

    public String name;
    public Map<Character, HashSet<NFAState>> transitions;

    /**
     * Constructor for an NFAState object
     * @param name name for state
     */
    public NFAState(String name) {
        this.name = name;
        this.transitions = new HashMap<Character, HashSet<NFAState>>();
    }

    public String getName() {
        return this.name;
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
     * Adds all the transitions given in toStates to update this object's transitions map.
     * This is used internally in NFA.java's addTransition() method, so it assumes toStates 
     * and onSymb are valid (since they're checked in NFA.java's addTransition() before this 
     * is called).
	 * @param toStates is the set of NFAStates where the transitions end
	 * @param onSymb is the symbol from the NFA's alphabet that the transition is taken on
    */
    public void addTransition(HashSet<NFAState> toStates, char onSymb) {
        // If a transition on onSymb already exists, add to its set of destination states
        if (this.transitions.containsKey(onSymb)) {
            HashSet<NFAState> currentVal = this.transitions.get(onSymb);
            currentVal.addAll(toStates);
            this.transitions.put(onSymb, currentVal);
        
        // Otherwise, create a new transition with the symbol and states
        } else {
            this.transitions.put(onSymb, toStates);
        }
    }

}