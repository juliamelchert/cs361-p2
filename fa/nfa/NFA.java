package fa.dfa;

import fa.NFAState;
import fa.nfa.NFAInterface;

import java.util.*;

import java.util.Set;

/**
 * Represents a nondeterministic finite automaton (NFA) comprised of NFAState objects and transitions.
 * Manages the appropriate data for an NFA like the alphabet, states, start/final states, and transition table.
 * @author Axel Murillo
 * @author Julia Melchert
 */
public class NFA implements NFAInterface {
    private AbstractSet<Character> alphabet;
    private AbstractSet<NFAState> allStates;
    private AbstractSet<String> allStateNames;
    private NFAState startState;
    private AbstractSet<NFAState> finalStates;
    // transitions are tracked in each individual NFAState

    // Constructor
    public DFA() {
        this.alphabet = new AbstractSet<Character>();
        this.allStates = new AbstractSet<NFAState>();
        this.allStateNames = new AbstractSet<String>();
        this.startState = null;
        this.finalStates = new AbstractSet<NFAState>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public void addSigma(char symbol) {
        this.alphabet.add(symbol);
    }
	
    /**
     * {@inheritDoc}
     */
    @Override
	public Set<Character> getSigma() {
        return this.alphabet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public boolean addState(String name) {
        // If the name can be added to the set, it is added
        if (this.allStateNames.add(name) == true) {
            this.allStates.add(NFAState(name));
            return true;
        }

        // Otherwise, we assume it cannot be added and return false
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public NFAState getState(String name) {
        // Iterate through all states and if the name matches, return the object
        for (NFAState state : this.allStates) {
            if (state.getName() == name) {
                return state;
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public boolean setStart(String name) {
        // If the state name exists, updates the start state
        if (this.allStateNames.contains(name)) {
            this.startState = this.getState(name);
            return true;
        }

        // Otherwise, no state with the given name exists so we return false
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public boolean setFinal(String name) {
        // If the state name exists, updates the final state set
        if (this.allStateNames.contains(name)) {
            this.finalStates.add(this.getState(name));
            return true;
        }

        // Otherwise, no state with the given name exists so we return false
        return false;
    }
	
    /**
     * {@inheritDoc}
     */
    @Override
	public boolean isStart(String name) {
        if (this.startState == name) {
            return true;
        }

        return false;
    }
	
    /**
     * {@inheritDoc}
     */
    @Override
	public boolean isFinal(String name) {
        // Iterate through all final states and if one has the given name, return true
        for (NFAState state : this.finalStates) {
            if (state.getName() == name) {
                return true;
            }
        }

        // None of the final states have the given name, so return false
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        // Check if the fromState does not exist
        if (!this.allStateNames.contains(fromState)) {
            return false;
        }

        // Check if any of the toStates do not exist
        for (String stateName : toStates) {
            if (!this.allStateNames.contains(stateName)) {
                return false;
            }
        }
        
        // Check if the symbol is not in the alphabet (and it's not an epsilon)
        if (!this.alphabet.contains(onSymb) && onSymb != 'e') {
            return false;
        }

        // Ensured valid inputs, so proceed with adding the transition(s)

        // Create set of NFAStates based on strings in toStates
        AbstractSet<NFAState> destStates = new AbstractSet<NFAState>();
        for (String stateName : toStates) {
            destStates.add(this.getState(stateName));
        }

        // Pass set of NFAStates to NFAState method to add transitions
        this.getState(fromState).addTransition(destStates, onSymb);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public Set<NFAState> getToState(NFAState from, char onSymb);
	
    /**
     * {@inheritDoc}
     */
    @Override
	public Set<NFAState> eClosure(NFAState s) {
        // Uses depth-first search (DFS)
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public abstract boolean accepts(String s);
	

    /**
     * {@inheritDoc}
     */
    @Override
	public int maxCopies(String s) {
        // NOTE: This method does not need to account for infinite loops from epsilon transitions

        // Uses breadth-first search (BFS)

    }
	
    /**
     * {@inheritDoc}
     */
    @Override
	public boolean isDFA();

}