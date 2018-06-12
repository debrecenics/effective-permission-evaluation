/*
 * generated by Xtext 2.13.0
 */
package org.mondo.collaboration.policy.delegation.scoping

import com.google.common.collect.Lists
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.TreeIterator
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EEnumLiteral
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.viatra.query.patternlanguage.patternLanguage.Pattern
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.mondo.collaboration.policy.delegation.delegation.DelegationModel
import org.mondo.collaboration.policy.delegation.delegation.DelegationPackage
import org.mondo.collaboration.policy.delegation.delegation.Rule
import org.mondo.collaboration.policy.rules.AccessControlModel
import org.mondo.collaboration.policy.rules.AttributeFact
import org.mondo.collaboration.policy.rules.Binding
import org.mondo.collaboration.policy.rules.Import
import org.mondo.collaboration.policy.rules.ObjectFact
import org.mondo.collaboration.policy.rules.OperationType
import org.mondo.collaboration.policy.rules.ReferenceFact
import org.mondo.collaboration.policy.rules.Role
import org.mondo.collaboration.policy.rules.RulesPackage

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class DelegationScopeProvider extends AbstractDelegationScopeProvider {
    override IScope getScope(EObject context, EReference reference) {
		if (reference == RulesPackage.eINSTANCE.getRule_Operation()) {
			if (context instanceof Rule) {
				val literals = Lists.newArrayList()
				for (EEnumLiteral literal : RulesPackage.eINSTANCE.getOperationType().getELiterals()) {
					if(!literal.getLiteral().equals(OperationType.UNSET)) literals.add(literal)
				}
				return Scopes.scopeFor(literals)
			}
		}
		if (reference == RulesPackage.eINSTANCE.getRule_Pattern() && context instanceof Rule) {
			return context.scopeRule_Pattern(reference)
		}
		if ((reference == RulesPackage.eINSTANCE.getBinding_Variable() && context instanceof Binding) ||
			(reference == RulesPackage.eINSTANCE.getObjectFact_Variable() && context instanceof ObjectFact) ||
			(reference == RulesPackage.eINSTANCE.getReferenceFact_SourceVar() && context instanceof ReferenceFact) ||
			(reference == RulesPackage.eINSTANCE.getReferenceFact_TargetVar() && context instanceof ReferenceFact) ||
			(reference == RulesPackage.eINSTANCE.getAttributeFact_Variable() && context instanceof AttributeFact)) {
			return context.scopeVariable(reference)
		}
		if (reference == RulesPackage.eINSTANCE.getReferenceFact_Reference() && context instanceof ReferenceFact) {
			return context.scopeReferenceFact_Reference(reference)
		}
		if (reference == RulesPackage.eINSTANCE.getAttributeFact_Attribute() && context instanceof AttributeFact) {
			return context.scopeAttributeFact_Attribute(reference)
		}
		if((reference == DelegationPackage.eINSTANCE.getRule_Source() && context instanceof Rule) ||
		   (reference == DelegationPackage.eINSTANCE.getRule_Targets() && context instanceof Rule)) {
			return context.scopeRule_Role(reference)
		}
		return super.getScope(context, reference)
	}
	
	override IScope scopeRule_Pattern(EObject context, EReference reference) {
	    return Scopes.scopeFor(context.queries.filter(Pattern).toList)
	}
	
	override TreeIterator<Notifier> queries(EObject context){
		val delegationModel = context.eResource().contents.get(0) as DelegationModel
		val resourceSet = context.eResource.resourceSet
	    resourceSet.getResource(URI.createFileURI(delegationModel.import.importURI), true)
	    val accessControlModel = resourceSet.allContents.findFirst[it instanceof AccessControlModel] as AccessControlModel
	    for(Import im : accessControlModel.imports) {
			resourceSet.getResource(URI.createFileURI(im.importURI), true)
		}
		return resourceSet.allContents
	}
	
	def IScope scopeRule_Role(EObject context, EReference reference) {
		return Scopes.scopeFor(context.roles.filter(Role).toList)
	}
	
	def TreeIterator<Notifier> roles(EObject context) {
		val model = context.eResource().contents.get(0) as DelegationModel
		val resourceSet = context.eResource.resourceSet
		resourceSet.getResource(URI.createFileURI(model.import.importURI), true)
		return resourceSet.allContents
		
	}
}
