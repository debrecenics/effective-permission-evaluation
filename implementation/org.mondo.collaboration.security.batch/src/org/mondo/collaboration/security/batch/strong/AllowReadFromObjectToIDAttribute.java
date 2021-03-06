package org.mondo.collaboration.security.batch.strong;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.mondo.collaboration.policy.rules.AccessibilityLevel;
import org.mondo.collaboration.policy.rules.OperationType;
import org.mondo.collaboration.security.batch.Asset;
import org.mondo.collaboration.security.batch.Asset.AttributeAsset;
import org.mondo.collaboration.security.batch.Asset.ObjectAsset;
import org.mondo.collaboration.security.batch.Consequence;
import org.mondo.collaboration.security.batch.Judgement;

import com.google.common.collect.Sets;

public class AllowReadFromObjectToIDAttribute extends Consequence{
	private AllowReadFromObjectToIDAttribute() {
	}
	
	public static Consequence instance = new AllowReadFromObjectToIDAttribute();

	@Override
	public Set<Judgement> propagate(Judgement judgement) {
		HashSet<Judgement> consequences = Sets.newHashSet();

		if(judgement.getAsset() instanceof ObjectAsset) {
			if(judgement.getAccess() == AccessibilityLevel.ALLOW) {
				if(judgement.getOperation() == OperationType.READ) {
					EObject object = ((ObjectAsset)judgement.getAsset()).getObject();
				    EList<EAttribute> eAllAttributes = object.eClass().getEAllAttributes();
			        for (EAttribute eAttribute : eAllAttributes) {
			        	if(eAttribute.isID()){
			        		AttributeAsset attrAsset = new Asset.AttributeAsset(object, eAttribute);
				    	    consequences.add(new Judgement(judgement.getAccess(), judgement.getOperation(), attrAsset, judgement.getPriority()));
			        	}
			        }
			    }
		    }
		}
		
		return consequences;
	}

}
