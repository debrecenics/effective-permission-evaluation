package org.mondo.collaboration.security.batch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.viatra.query.patternlanguage.patternLanguage.Pattern;
import org.eclipse.viatra.query.patternlanguage.patternLanguage.PatternModel;
import org.eclipse.viatra.query.patternlanguage.patternLanguage.Variable;
import org.eclipse.viatra.query.runtime.api.AdvancedViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;
import org.eclipse.viatra.query.runtime.api.ViatraQueryMatcher;
import org.eclipse.viatra.query.runtime.emf.EMFScope;
import org.eclipse.viatra.query.runtime.exception.ViatraQueryException;
import org.mondo.collaboration.policy.rules.AccessControlModel;
import org.mondo.collaboration.policy.rules.AccessibilityLevel;
import org.mondo.collaboration.policy.rules.Binding;
import org.mondo.collaboration.policy.rules.Group;
import org.mondo.collaboration.policy.rules.OperationType;
import org.mondo.collaboration.policy.rules.ResolutionType;
import org.mondo.collaboration.policy.rules.Role;
import org.mondo.collaboration.policy.rules.Rule;
import org.mondo.collaboration.policy.rules.User;
import org.mondo.collaboration.security.batch.Asset.AttributeAsset;
import org.mondo.collaboration.security.batch.Asset.Factory;
import org.mondo.collaboration.security.batch.Asset.ObjectAsset;
import org.mondo.collaboration.security.batch.Asset.ReferenceAsset;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class RuleManager {
	private Logger LOGGER = Logger.getLogger(RuleManager.class);

	private JudgementStorage judgementStorage;
	private Map<Asset, Map<OperationType, Judgement>> effectivePermissionsMap;
	private Collection<Judgement> effectiveJudgements;

	private Collection<Consequence> weakConsequences = Consequence.DefaultConsequenceTypes.DefaultWeakConsequences;
	private Collection<Consequence> strongConsequences = Consequence.DefaultConsequenceTypes.DefaultStrongConsequences;

	private Resource instanceModel;
	private AccessControlModel accessControlModel;

	private AdvancedViatraQueryEngine advancedQueryEngine;
	private Map<Pattern, ViatraQueryMatcher<IPatternMatch>> matchers = Maps.newHashMap();
	private Map<String, IQuerySpecification<ViatraQueryMatcher<IPatternMatch>>> querySpecifications;

	private Map<Rule, Map<Variable, Object>> bindingMap = Maps.newHashMap();
	private Multimap<EObject, ReferenceAsset> incomingReferenceMap = ArrayListMultimap.create();
	
	private int numOfConsequences;
	private int numOfAssets;
	private int numOfExplicits;
	
	public RuleManager(Resource model, AccessControlModel rules) {
		this.instanceModel = model;
		this.accessControlModel = rules;
	}

	public void setQuerySpecifications(
			Collection<IQuerySpecification<ViatraQueryMatcher<IPatternMatch>>> specifications) {
		
		querySpecifications = Maps.newHashMap();
		for (IQuerySpecification<ViatraQueryMatcher<IPatternMatch>> specification : specifications) {
			querySpecifications.put(specification.getFullyQualifiedName(), specification);
		}		
	}
	
	public void dispose() {
		judgementStorage.dispose();
		effectiveJudgements.clear();
		effectivePermissionsMap.clear();
		advancedQueryEngine.dispose();
	}
	
	public void setWeakConsequences(Collection<Consequence> weakConsequences) {
		this.weakConsequences = weakConsequences;
	}

	public void setStrongConsequences(Collection<Consequence> strongConsequences) {
		this.strongConsequences = strongConsequences;
	}

	public void initialize() throws ViatraQueryException {
		LOGGER.info("Inizialize ViatraQueryEngine with Rules on the Model");
		long start = System.nanoTime();
		advancedQueryEngine = AdvancedViatraQueryEngine.createUnmanagedEngine(new EMFScope(instanceModel));
		
		for (Rule rule : accessControlModel.getPolicy().getRules()) {
			IQuerySpecification<ViatraQueryMatcher<IPatternMatch>> querySpecification = querySpecifications.get(patternQualifiedName(rule.getPattern()));
			ViatraQueryMatcher<IPatternMatch> queryMatcher = advancedQueryEngine.getMatcher(querySpecification);
			matchers.put(rule.getPattern(), queryMatcher);
			initializeBindings(rule);
		}
		
		for (Consequence consequence : weakConsequences) {
			consequence.setRuleManager(this);
		}
		for (Consequence consequence : strongConsequences) {
			consequence.setRuleManager(this);
		}
		
		long end = System.nanoTime();
		LOGGER.info(String.format("ViatraQueryEngine is initialized in %d nanosec", end - start));
	}

	public Collection<Judgement> calculateEffectivePermissions(User user) throws ViatraQueryException {
		ResolutionType resolution = accessControlModel.getPolicy().getResolution();
		judgementStorage = new JudgementStorage(resolution);
		effectivePermissionsMap = Maps.newHashMap();
		effectiveJudgements = Sets.newHashSet();

		addInitialPermissions(user);
		LOGGER.info("Number of initial permissions: " + judgementStorage.size());
		LOGGER.info("Calculating effective permissions");
		long start = System.nanoTime();

		numOfConsequences = 0;
		for (Judgement dominant = null; !judgementStorage.isEmpty();) {
			dominant = judgementStorage.last(); 
			dominant = judgementStorage.resolveConflict(dominant);
			if (dominant.getPriority() > -1) {
				propagateStrongConsequences(dominant);
				propagateWeakConsequences(dominant);
			}
			addEffectivePermission(dominant);
		}
		long end = System.nanoTime();
		LOGGER.info(String.format("Effective Permissions are calculated in %d nanosec", end - start));
		LOGGER.info(String.format("Number of propagated consequences: %d", numOfConsequences));
		return effectiveJudgements;
	}

	private boolean isEffectivePermissionsContains(Judgement j) {
		Map<OperationType, Judgement> operationMap = effectivePermissionsMap.get(j.getAsset());
		if(operationMap == null) {
			return false;
		}
		if(!operationMap.containsKey(j.getOperation())) {
			return false;
		}
		return true; 
	}
	
	private void addEffectivePermission(Judgement j) {
		Map<OperationType, Judgement> operationMap = effectivePermissionsMap.get(j.getAsset());
		if(operationMap == null) {
			operationMap = Maps.newHashMap();
			effectivePermissionsMap.put(j.getAsset(), operationMap);
		}
		if(!operationMap.containsKey(j.getOperation())) {
			operationMap.put(j.getOperation(), j);
			effectiveJudgements.add(j);
		}
	}
	
	private void addInitialPermissions(User user) throws ViatraQueryException {
		addExplicitPermissions(user);
		addDefaultPermissions();
	}

	private void addExplicitPermissions(User user) throws ViatraQueryException {
		numOfExplicits = 0;
		for (Rule rule : accessControlModel.getPolicy().getRules()) {
			for (Role role : rule.getRoles()) {
				if (getRolesOfUser(accessControlModel, user).contains(role)) {
					for (IPatternMatch match : matchesOf(rule)) {
						Factory factory = AssetFactory.factoryFrom(rule);
						for (Asset asset : factory.apply(match)) {
							addExplicitPermission(rule, asset);
							numOfExplicits++;
						}
					}
					LOGGER.info(String.format("Number of explicit judgements: %s", numOfExplicits));
					break;
				}
			}
		}
	}

	private void addExplicitPermission(Rule rule, Asset asset) {
		AccessibilityLevel access = rule.getAccess();
		int priority = rule.getPriority();
		if (access == AccessibilityLevel.OBFUSCATE) {
			judgementStorage.add(new Judgement(access, OperationType.READ, asset, priority));
		} else {
			OperationType operation = rule.getOperation();
			if (operation == OperationType.READWRITE) {
				judgementStorage.add(new Judgement(access, OperationType.READ, asset, priority));
				judgementStorage.add(new Judgement(access, OperationType.WRITE, asset, priority));
			} else if (operation == OperationType.READ || operation == OperationType.WRITE) {
				judgementStorage.add(new Judgement(access, operation, asset, priority));
			}
		}
	}
	
	private void addDefaultPermissions() {
		numOfAssets = 0;
		TreeIterator<EObject> allContents = instanceModel.getAllContents();
		while (allContents.hasNext()) {
			EObject object = allContents.next();

			// object
			ObjectAsset objAsset = new Asset.ObjectAsset(object);
			numOfAssets++;
			addDefaultPermission(objAsset);

			// attributes
			for (EAttribute attribute : object.eClass().getEAllAttributes()) {
				AttributeAsset attrAsset = new Asset.AttributeAsset(object, attribute);
				numOfAssets++;
				addDefaultPermission(attrAsset);
			}

			// references
			for (EReference reference : object.eClass().getEAllReferences()) {
				if (reference.isMany()) {
					@SuppressWarnings("unchecked")
					EList<EObject> targets = (EList<EObject>) object.eGet(reference);
					for (EObject target : targets) {
						ReferenceAsset refAsset = new Asset.ReferenceAsset(object, reference, target);
						numOfAssets++;
						addDefaultPermission(refAsset);
					}
				} else {
					EObject target = (EObject) object.eGet(reference);
					if (target != null) {
						ReferenceAsset refAsset = new Asset.ReferenceAsset(object, reference, target);
						numOfAssets++;
						addDefaultPermission(refAsset);
					}
				}
			}
		}
		LOGGER.info(String.format("Number of assets: %d", numOfAssets));
	}

	private void addDefaultPermission(Asset asset) {
		AccessibilityLevel access = accessControlModel.getPolicy().getAccess();
		judgementStorage.add(new Judgement(access, OperationType.READ, asset, -1));
		judgementStorage.add(new Judgement(access, OperationType.WRITE, asset, -1));
		
		if(asset instanceof ReferenceAsset) {
			ReferenceAsset referenceAsset = (ReferenceAsset) asset;
			if(!referenceAsset.getReference().isContainment())
				incomingReferenceMap.put(referenceAsset.getTarget(), referenceAsset);
		}
	}

	private void propagateWeakConsequences(Judgement judgement) {
		for (Consequence weakConsequence : weakConsequences) {
			Set<Judgement> consequences = weakConsequence.propagate(judgement);
			for (Judgement j : consequences) {
				if(!isEffectivePermissionsContains(j)) {
					judgementStorage.add(j);
					numOfConsequences++;
				}
			}
		}
	}

	private void propagateStrongConsequences(Judgement judgement) {
		for (Consequence strongConsequence : strongConsequences) {
			Set<Judgement> consequences = strongConsequence.propagate(judgement);
			for (Judgement j : consequences) {
				if(!isEffectivePermissionsContains(j)) {
					judgementStorage.add(j);
					numOfConsequences++;
				}
			}
		}
	}

	private List<Role> getRolesOfUser(AccessControlModel rules, User user) {
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(user);
		for (Role role : rules.getRoles()) {
			if (role instanceof Group) {
				for (User u : getUsersOfGroup((Group) role)) {
					if (u.equals(user)) {
						roleList.add(role);
					}
				}
			}
		}
		return roleList;
	}

	public List<User> getUsersOfGroup(Group group) {
		List<User> userList = new ArrayList<User>();
		for (Role role : group.getRoles()) {
			if (role instanceof User) {
				userList.add((User) role);
			} else {
				userList.addAll(getUsersOfGroup((Group) role));
			}
		}
		return userList;
	}

	private Collection<IPatternMatch> matchesOf(Rule rule) throws ViatraQueryException {
		ViatraQueryMatcher<IPatternMatch> queryMatcher = matchers.get(rule.getPattern());
		IPatternMatch filterMatch = buildFilterMatch(rule);
		return queryMatcher.getAllMatches(filterMatch);
	}
	
	private static String patternQualifiedName(Pattern pattern) {
		PatternModel patternModel = (PatternModel) pattern.eContainer();
		return patternModel.getPackageName() + "." + pattern.getName();
	}
	
	private IPatternMatch buildFilterMatch(Rule rule) {
		ViatraQueryMatcher<IPatternMatch> queryMatcher = matchers.get(rule.getPattern());
		IPatternMatch filterMatch = queryMatcher.newEmptyMatch();
		Map<Variable, Object> bindings = bindingMap.get(rule);
		for (Variable variable : bindings.keySet()) {
			filterMatch.set(variable.getName(), bindings.get(variable));
		}
		return filterMatch.toImmutable();
	}
	
	private void initializeBindings(Rule rule) {
		Map<Variable, Object> bindings = Maps.newHashMap();
		bindingMap.put(rule, bindings);
		
		for (Binding binding : rule.getBindings()) {
			bindings.put(binding.getVariable(), getBoundValue(binding));
		}
	}
	
	private Object getBoundValue(Binding binding) {
		String valueString = binding.getBind().getValueString();
		if(valueString == null) {
			return binding.getBind().getValueInteger();
		} 
		return valueString;
	}
	
	
	
	public Collection<ReferenceAsset> getIncomingReferences(EObject obj) {
		return incomingReferenceMap.get(obj);
	}
	
	public int getNumOfAssets() {
		return numOfAssets;
	}
	
	public int getNumOfConsequences() {
		return numOfConsequences;
	}
	
	public int getNumOfExplicits() {
		return numOfExplicits;
	}
}
