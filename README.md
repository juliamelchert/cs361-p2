# Project 2: Nondeterministic Finite Automata

* Authors: Julia Melchert and Axel Murillo
* Class: CS361 Section 002
* Semester: Spring 2024

## Overview

This project uses Java to mimic a nondeterministic finite automaton (NFA).

## Reflection

This project definitely went better than Project 1. We think this is because everything was new to us
at the start of Project 1, but with this project being similar to that one, we had some familiarity
with the format and codebase for this project. We also had experience working together, so we understood 
each others' strengths and general workflows better. This made it easier to split up the work effectively.

* What worked well and what was a struggle?
Our workflow worked well. We knew how we'd handle version control and when each of us would work on the project, so we managed a lot less merge conflicts and each contributed meaningfully to the code. The only struggle for us seemed to be finding time to complete the project, since we had exams and other assigments to finish up before break. Still, we were able to finish it with plenty of time to test, debug, and document.

* What concepts still aren't quite clear?
For Julia, the idea behind the maxCopies function still isn't totally clear (even after writing it). She understands what information it's requesting, but she doesn't really see what it could be useful for. While it traverses the NFA, it doesn't provide useful information about the acceptance/rejection of a string like the accepts function does.

* What techniques did you use to make your code easy to debug and modify?
We used test-driven development and object-oriented principles to make our code easier to debug and modify. Testing functions as we wrote them allowed us to make sure each piece worked before attempting to use them in conjunction. Furthermore, making object-oriented decisions, like storing and updating transitions in each individual NFAState instead of in the NFA, prevented overcomplication of our code.

* What would you change about your design process?
We really wouldn't change much about our design, as we think it served us well for this project. If we had to change something, though, we may consider a way to break up the number of methods in the NFA.java file in order to make things more clear and object-oriented.

* If you could go back in time, what would you tell yourself about doing this project?
We would tell ourselves that it's not so bad, so don't be afraid to get started! The first project took awhile longer to get started as we had to get used to the system, project outline, and general coding expectations. However, now that we have that experience, this project went a lot smoother and came together easily.


## Compiling and Using

In order to compile and run the test suite, use the following commands:
```
javac -cp .:/usr/share/java/junit.jar ./test/nfa/NFATest.java
java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar org.junit.runner.JUnitCore test.nfa.NFATest
```

If you want to make an NFA of your own, follow the structure of NFATest.java to import all the appropriate packages/libraries and initialize the NFA object correctly.

## Sources Used

* https://docs.oracle.com/javase/8/docs/api/java/util/Set.html
* https://www.tutorialspoint.com/set-vs-hashset-vs-treeset-in-java
* https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
* https://stackoverflow.com/questions/12455737/how-to-iterate-over-a-set-hashset-without-an-iterator