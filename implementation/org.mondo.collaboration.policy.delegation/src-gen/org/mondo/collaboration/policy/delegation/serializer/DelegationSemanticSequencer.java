/*
 * generated by Xtext 2.14.0
 */
package org.mondo.collaboration.policy.delegation.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.mondo.collaboration.policy.delegation.delegation.Delegation;
import org.mondo.collaboration.policy.delegation.delegation.DelegationModel;
import org.mondo.collaboration.policy.delegation.delegation.DelegationPackage;
import org.mondo.collaboration.policy.delegation.services.DelegationGrammarAccess;
import org.mondo.collaboration.policy.rules.AccessControlModel;
import org.mondo.collaboration.policy.rules.AttributeFact;
import org.mondo.collaboration.policy.rules.Bind;
import org.mondo.collaboration.policy.rules.Binding;
import org.mondo.collaboration.policy.rules.Group;
import org.mondo.collaboration.policy.rules.Import;
import org.mondo.collaboration.policy.rules.ObjectFact;
import org.mondo.collaboration.policy.rules.Policy;
import org.mondo.collaboration.policy.rules.ReferenceFact;
import org.mondo.collaboration.policy.rules.Rule;
import org.mondo.collaboration.policy.rules.RulesPackage;
import org.mondo.collaboration.policy.rules.User;
import org.mondo.collaboration.policy.serializer.RulesSemanticSequencer;

@SuppressWarnings("all")
public class DelegationSemanticSequencer extends RulesSemanticSequencer {

	@Inject
	private DelegationGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == DelegationPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case DelegationPackage.DELEGATION:
				sequence_Delegation(context, (Delegation) semanticObject); 
				return; 
			case DelegationPackage.DELEGATION_MODEL:
				sequence_DelegationModel(context, (DelegationModel) semanticObject); 
				return; 
			}
		else if (epackage == RulesPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case RulesPackage.ACCESS_CONTROL_MODEL:
				sequence_AccessControlModel(context, (AccessControlModel) semanticObject); 
				return; 
			case RulesPackage.ATTRIBUTE_FACT:
				sequence_AttributeFact(context, (AttributeFact) semanticObject); 
				return; 
			case RulesPackage.BIND:
				sequence_Bind(context, (Bind) semanticObject); 
				return; 
			case RulesPackage.BINDING:
				sequence_Binding(context, (Binding) semanticObject); 
				return; 
			case RulesPackage.GROUP:
				sequence_Group(context, (Group) semanticObject); 
				return; 
			case RulesPackage.IMPORT:
				sequence_Import(context, (Import) semanticObject); 
				return; 
			case RulesPackage.OBJECT_FACT:
				sequence_ObjectFact(context, (ObjectFact) semanticObject); 
				return; 
			case RulesPackage.POLICY:
				sequence_Policy(context, (Policy) semanticObject); 
				return; 
			case RulesPackage.REFERENCE_FACT:
				sequence_ReferenceFact(context, (ReferenceFact) semanticObject); 
				return; 
			case RulesPackage.RULE:
				sequence_Rule(context, (Rule) semanticObject); 
				return; 
			case RulesPackage.USER:
				sequence_User(context, (User) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     DelegationModel returns DelegationModel
	 *
	 * Constraint:
	 *     (import=Import delegations+=Delegation*)
	 */
	protected void sequence_DelegationModel(ISerializationContext context, DelegationModel semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Delegation returns Delegation
	 *
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         source=[User|ID] 
	 *         access=AccessibilityLevel 
	 *         operation=OperationType? 
	 *         targets+=[Role|ID] 
	 *         targets+=[Role|ID]* 
	 *         pattern=[Pattern|STRING] 
	 *         asset=ModelFact 
	 *         bindings+=Binding*
	 *     )
	 */
	protected void sequence_Delegation(ISerializationContext context, Delegation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
