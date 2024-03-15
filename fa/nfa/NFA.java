package fa.nfa;

import fa.nfa.NFAState;
import fa.nfa.NFAInterface;

import java.util.*;

/**
 * Represents a nondeterministic finite automaton (NFA) comprised of NFAState objects and transitions.
 * Manages the appropriate data for an NFA like the alphabet, states, start/final states, and transition table.
 * @author Axel Murillo
 * @author Julia Melchert
 */
public class NFA implements NFAInterface {
    private HashSet<Character> alphabet;
    private HashSet<NFAState> allStates;
    private HashSet<String> allStateNames;
    private NFAState startState;
    private HashSet<NFAState> finalStates;
    // transitions are tracked in each individual NFAState

    // Constructor
    public NFA() {
        this.alphabet = new HashSet<Character>();
        this.allStates = new HashSet<NFAState>();
        this.allStateNames = new HashSet<String>();
        this.startState = null;
        this.finalStates = new HashSet<NFAState>();
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
            this.allStates.add(new NFAState(name));
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
        if (this.startState.getName() == name) {
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
        HashSet<NFAState> destStates = new HashSet<NFAState>();
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
	public Set<NFAState> getToState(NFAState from, char onSymb) {
        Set<NFAState> result = new HashSet<>();
        if(from.transitions.containsKey(onSymb)) {
            result.addAll(from.transitions.get(onSymb));
        }
        result.addAll(eClosure(from));
        return result;
    }
	
    /**
     * {@inheritDoc}
     */
    @Override
	public Set<NFAState> eClosure(NFAState s) {
        // Uses depth-first search (DFS)
        Set<NFAState> closure = new HashSet<>();
        Stack<NFAState> stack = new Stack<>();
        stack.push(s);
        closure.add(s);
        
        while (!stack.isEmpty()) {
            NFAState current = stack.pop();
            //Check epsilon transitions
            if (current.transitions.containsKey('e')) {
                for (NFAState nextState : current.transitions.get('e')) {
                    if (!closure.contains(nextState)) {
                        stack.push(nextState);
                        closure.add(nextState);
                    }

                }
            }
        }
        
        return closure;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public boolean accepts(String s) {
        // Performs BFS

        Set<NFAState> currentStates = eClosure(startState);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Set<NFAState> nextStates = new HashSet<>();

            // For every starting state, check if there's a transition for the current character 
            for (NFAState state : currentStates) {
                if (state.transitions.containsKey(c)) {
                    // If there is a transition on the current character, add all possible destinations to a set
                    for (NFAState targetState : state.transitions.get(c)) {
                        nextStates.addAll(eClosure(targetState));
                    }
                }
            }

            // Update the set of states we still need to look through with the last iteration's destinations
            currentStates = nextStates;
        }
        
        // Return true if at least one of the destination states is a final state 
        for (NFAState state : currentStates) {
            if (finalStates.contains(state)) {
                return true;
            }
        }

        return false;
    }
	

    /**
     * {@inheritDoc}
     */
    @Override
	public int maxCopies(String s) {
        // Follows similar process as accepts, but keeps track of max copies while performing BFS

        // Find all possible start states by using the eClosure of the start state
        Set<NFAState> currentStates = eClosure(startState);

        // Initialize variable to hold the max number of copies through all iterations
        int copyMax = currentStates.size();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Set<NFAState> nextStates = new HashSet<>();

            // For every starting state, check if there's a transition for the current character 
            for (NFAState state : currentStates) {
                if (state.transitions.containsKey(c)) {
                    // If there is a transition on the current character, add all possible destinations to a set
                    for (NFAState targetState : state.transitions.get(c)) {
                        nextStates.addAll(eClosure(targetState));
                    }

                    // Update the max copy tracker if more states can be reached than before
                    if (nextStates.size() > copyMax) {
                        copyMax = nextStates.size();
                    }
                }
            }

            // Update the set of states we still need to look through with the last iteration's destinations
            currentStates = nextStates;
        }

        return copyMax;
    }
	
    /**
     * {@inheritDoc}
     */
    @Override
	public boolean isDFA() {
        for (NFAState state : allStates) {
            for (char symbol : alphabet) {
                if (state.transitions.containsKey(symbol)) {
                    if (state.transitions.get(symbol).size() > 1) return false;
                }
            }

            //Check for epsilon
            if (state.transitions.containsKey('e')) {
                if (!state.transitions.get('e').isEmpty()) return false;
            }
        }
        return true;
    }

}