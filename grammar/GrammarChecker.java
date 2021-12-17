/* -- JFLAP 4.0 --
 *
 * Copyright information:
 *
 * Susan H. Rodger, Thomas Finley
 * Computer Science Department
 * Duke University
 * April 24, 2003
 * Supported by National Science Foundation DUE-9752583.
 *
 * Copyright (c) 2003
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are
 * duplicated in all such forms and that any documentation,
 * advertising materials, and other materials related to such
 * distribution and use acknowledge that the software was developed
 * by the author.  The name of the author may not be used to
 * endorse or promote products derived from this software without
 * specific prior written permission.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
 
package grammar;

import grammar.*;
import java.util.*;

/**
 * The Grammar checker object can be used to check certain properties of
 * grammar objects.
 *
 * @author Ryan Cavalcante
 */

public class GrammarChecker {
    /**
     * Creates an instance of <CODE>GrammarChecker</CODE>.
     */
    public GrammarChecker() {

    }

    /**
     * Returns true if <CODE>grammar</CODE> is a regular grammar
     * (i.e. if it is either a right or left linear grammar).
     * @param grammar the grammar.
     * @return true if <CODE>grammar</CODE> is a regular grammar.
     */
    public static boolean isRegularGrammar(Grammar grammar) {
	if(isRightLinearGrammar(grammar) || isLeftLinearGrammar(grammar))
	    return true;
	return false;
    }

    /**
     * Returns true if <CODE>grammar</CODE> is a right-linear grammar
     * (i.e. all productions are of the form A->xB or A->x).
     * @param grammar the grammar.
     * @return true if <CODE>grammar</CODE> is a right-linear grammar.
     */
    public static boolean isRightLinearGrammar(Grammar grammar) {
	ProductionChecker pc = new ProductionChecker();
	Production[] productions = grammar.getProductions();
	for(int k = 0; k < productions.length; k++) {
	    if(!pc.isRightLinear(productions[k])) return false;
	}
	return true;
    }

    /**
     * Returns true if <CODE>grammar</CODE> is a left-linear grammar
     * (i.e. all productions are of the form A->Bx or A->x).
     * @param grammar the grammar.
     * @return true if <CODE>grammar</CODE> is a left-linear grammar.
     */
    public static boolean isLeftLinearGrammar(Grammar grammar) {
	ProductionChecker pc = new ProductionChecker();
	Production[] productions = grammar.getProductions();
	for(int k = 0; k < productions.length; k++) {
	    if(!pc.isLeftLinear(productions[k])) return false;
	}
	return true;
    }

    /**
     * Returns true if <CODE>grammar</CODE> is a context-free grammar
     * (i.e. all productions are of the form A->x).
     * @param grammar the grammar.
     * @return true if <CODE>grammar</CODE> is a context-free grammar.
     */
    public static boolean isContextFreeGrammar(Grammar grammar) {
	ProductionChecker pc = new ProductionChecker();
	Production[] productions = grammar.getProductions();
	for(int k = 0; k < productions.length; k++) {
	    if(!pc.isRestrictedOnLHS(productions[k])) return false;
	}
	return true;
    }

    /**
     * Returns true if <CODE>variable</CODE> is in any 
     * production, either on the right or left hand side
     * of the production, of <CODE>grammar</CODE>.
     * @param variable the variable.
     * @return true if <CODE>variable</CODE> is in any
     * production of <CODE>grammar</CODE>.
     */
    public static boolean isVariableInProductions
	(Grammar grammar, String variable) {
	ProductionChecker pc = new ProductionChecker();
	Production[] productions = grammar.getProductions();
	for(int k = 0; k < productions.length; k++) {
	    if(pc.isVariableInProduction(variable, productions[k])) {
		return true;
	    }
	}
	return false;
    }
     
    /**
     * Returns true if <CODE>terminal</CODE> is in any 
     * production, either on the right or left hand side
     * of the production, of <CODE>grammar</CODE>.
     * @param terminal the terminal.
     * @return true if <CODE>terminal</CODE> is in any
     * production in <CODE>grammar</CODE>.
     */
    public static boolean isTerminalInProductions
	(Grammar grammar, String terminal) {
	ProductionChecker pc = new ProductionChecker();
	Production[] productions = grammar.getProductions();
	for(int k = 0; k < productions.length; k++) {
	    if(pc.isTerminalInProduction(terminal, productions[k])) {
		return true;
	    }
	}
	return false;
    }
   
    /**
     * Returns all productions in <CODE>grammar</CODE> whose lhs is
     * <CODE>variable</CODE>.
     * @param variable the variable
     * @param grammar the grammar
     * @return all productions in <CODE>grammar</CODE> whose lhs is
     * <CODE>variable</CODE>.
     */
    public static Production[] getProductionsOnVariable(String variable,
							Grammar grammar) {
	ArrayList list = new ArrayList();
	ProductionChecker pc = new ProductionChecker();
	Production[] productions = grammar.getProductions();
	for(int k = 0; k < productions.length; k++) {
	    if(variable.equals(productions[k].getLHS())) {
		list.add(productions[k]);
	    }
	}
	return (Production[]) list.toArray(new Production[0]);
    }

    /**
     * Returns all productions in <CODE>grammar</CODE> that have
     * <CODE>variable</CODE> as the only character on the left
     * hand side and that are not unit productions.
     * @param variable the variable
     * @param grammar the grammar
     * @return all productions in <CODE>grammar</CODE> that have
     * <CODE>variable</CODE> as the only character on the left
     * hand side and are not unit productions.
     */
    public static Production[] getNonUnitProductionsOnVariable
	(String variable,Grammar grammar) {
	ArrayList list = new ArrayList();
	ProductionChecker pc = new ProductionChecker();
	Production[] productions = grammar.getProductions();
	for(int k = 0; k < productions.length; k++) {
	    if(variable.equals(productions[k].getLHS()) &&
	       !pc.isUnitProduction(productions[k])) {
		list.add(productions[k]);
	    }
	}
	return (Production[]) list.toArray(new Production[0]);
    }
 
    /**
     * Returns true if <CODE>production</CODE>, or an identical
     * production, is already in <CODE>grammar</CODE>.
     * @param production the production
     * @param grammar the grammar
     * @return true if <CODE>production</CODE>, or an identical
     * production, is already in <CODE>grammar</CODE>.
     */
    public static boolean isProductionInGrammar(Production production,
						Grammar grammar) {
	Production[] productions = grammar.getProductions();
	for(int k = 0; k < productions.length; k++) {
	    if(production.equals(productions[k])) return true;
	}
	return false;
    }

    /**
     * Returns all productions in <CODE>grammar</CODE> that have
     * <CODE>variable</CODE> in them, either on the rhs or lhs.
     * @param variable the variable
     * @param grammar the grammar
     * @return all productions in <CODE>grammar</CODE> that have
     * <CODE>variable</CODE> in them, either on the rhs or lhs.
     */
    public static Production[] getProductionsWithVariable(String variable,
						   Grammar grammar) {
	ArrayList list = new ArrayList();
	ProductionChecker pc = new ProductionChecker();
	Production[] productions = grammar.getProductions();
	for(int k = 0; k < productions.length; k++) {
	    if(pc.isVariableInProduction(variable, productions[k])) {
		list.add(productions[k]);
	    }
	}
	return (Production[]) list.toArray(new Production[0]);
    }

    /**
     * Returns all productions in <CODE>grammar</CODE> that have
     * <CODE>variable</CODE> on the right hand side.
     * @param variable the variable
     * @param grammar the grammar
     * @return all productions in <CODE>grammar</CODE> that have
     * <CODE>variable</CODE> on the right hand side.
     */
    public static Production[] getProductionsWithVariableOnRHS
	(String variable, Grammar grammar) {
	ProductionChecker pc = new ProductionChecker();
	ArrayList list = new ArrayList();
	Production[] productions = grammar.getProductions();
	for(int k = 0; k < productions.length; k++) {
	    if(pc.isVariableOnRHS(productions[k],variable)) 
		list.add(productions[k]);
	}
	return (Production[]) list.toArray(new Production[0]);
    }
    
    /**
     * Returns a list of those variables which are unresolved, i.e.,
     * which appears in the right hand side but do not appear in the
     * left hand side.
     * @param grammar the grammar to check
     * @return an array of the unresolved variables
     */
    public static String[] getUnresolvedVariables(Grammar grammar) {
	String[] variables = grammar.getVariables();
	HashSet variableSet = new HashSet();
	for (int i=0; i<variables.length; i++)
	    variableSet.add(variables[i]);
	Production[] productions = grammar.getProductions();
	for (int i=0; i<productions.length; i++) {
	    String[] lhsVariables = productions[i].getVariablesOnLHS();
	    for (int j=0; j<lhsVariables.length; j++)
		variableSet.remove(lhsVariables[j]);
	}
	return (String[]) variableSet.toArray(new String[0]);
    }
}
