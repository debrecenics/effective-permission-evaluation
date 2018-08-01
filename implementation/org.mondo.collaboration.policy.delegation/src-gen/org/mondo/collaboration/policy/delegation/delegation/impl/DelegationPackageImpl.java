/**
 * generated by Xtext 2.14.0
 */
package org.mondo.collaboration.policy.delegation.delegation.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.viatra.query.patternlanguage.emf.vql.PatternLanguagePackage;
import org.eclipse.xtext.common.types.TypesPackage;
import org.eclipse.xtext.xbase.XbasePackage;
import org.eclipse.xtext.xtype.XtypePackage;
import org.mondo.collaboration.policy.delegation.delegation.Delegation;
import org.mondo.collaboration.policy.delegation.delegation.DelegationFactory;
import org.mondo.collaboration.policy.delegation.delegation.DelegationModel;
import org.mondo.collaboration.policy.delegation.delegation.DelegationPackage;
import org.mondo.collaboration.policy.rules.RulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DelegationPackageImpl extends EPackageImpl implements DelegationPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass delegationModelEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass delegationEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.mondo.collaboration.policy.delegation.delegation.DelegationPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private DelegationPackageImpl()
  {
    super(eNS_URI, DelegationFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   *
   * <p>This method is used to initialize {@link DelegationPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static DelegationPackage init()
  {
    if (isInited) return (DelegationPackage)EPackage.Registry.INSTANCE.getEPackage(DelegationPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredDelegationPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    DelegationPackageImpl theDelegationPackage = registeredDelegationPackage instanceof DelegationPackageImpl ? (DelegationPackageImpl)registeredDelegationPackage : new DelegationPackageImpl();

    isInited = true;

    // Initialize simple dependencies
    PatternLanguagePackage.eINSTANCE.eClass();
    RulesPackage.eINSTANCE.eClass();
    XtypePackage.eINSTANCE.eClass();
    EcorePackage.eINSTANCE.eClass();
    TypesPackage.eINSTANCE.eClass();
    XbasePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theDelegationPackage.createPackageContents();

    // Initialize created meta-data
    theDelegationPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theDelegationPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(DelegationPackage.eNS_URI, theDelegationPackage);
    return theDelegationPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDelegationModel()
  {
    return delegationModelEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDelegationModel_Import()
  {
    return (EReference)delegationModelEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDelegationModel_Delegations()
  {
    return (EReference)delegationModelEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDelegation()
  {
    return delegationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDelegation_Name()
  {
    return (EAttribute)delegationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDelegation_Source()
  {
    return (EReference)delegationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDelegation_Access()
  {
    return (EAttribute)delegationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDelegation_Operation()
  {
    return (EAttribute)delegationEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDelegation_Targets()
  {
    return (EReference)delegationEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDelegation_Pattern()
  {
    return (EReference)delegationEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDelegation_Asset()
  {
    return (EReference)delegationEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDelegation_Bindings()
  {
    return (EReference)delegationEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DelegationFactory getDelegationFactory()
  {
    return (DelegationFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    delegationModelEClass = createEClass(DELEGATION_MODEL);
    createEReference(delegationModelEClass, DELEGATION_MODEL__IMPORT);
    createEReference(delegationModelEClass, DELEGATION_MODEL__DELEGATIONS);

    delegationEClass = createEClass(DELEGATION);
    createEAttribute(delegationEClass, DELEGATION__NAME);
    createEReference(delegationEClass, DELEGATION__SOURCE);
    createEAttribute(delegationEClass, DELEGATION__ACCESS);
    createEAttribute(delegationEClass, DELEGATION__OPERATION);
    createEReference(delegationEClass, DELEGATION__TARGETS);
    createEReference(delegationEClass, DELEGATION__PATTERN);
    createEReference(delegationEClass, DELEGATION__ASSET);
    createEReference(delegationEClass, DELEGATION__BINDINGS);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    RulesPackage theRulesPackage = (RulesPackage)EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI);
    PatternLanguagePackage thePatternLanguagePackage = (PatternLanguagePackage)EPackage.Registry.INSTANCE.getEPackage(PatternLanguagePackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes and features; add operations and parameters
    initEClass(delegationModelEClass, DelegationModel.class, "DelegationModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getDelegationModel_Import(), theRulesPackage.getImport(), null, "import", null, 0, 1, DelegationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDelegationModel_Delegations(), this.getDelegation(), null, "delegations", null, 0, -1, DelegationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(delegationEClass, Delegation.class, "Delegation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDelegation_Name(), ecorePackage.getEString(), "name", null, 0, 1, Delegation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDelegation_Source(), theRulesPackage.getUser(), null, "source", null, 0, 1, Delegation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDelegation_Access(), theRulesPackage.getAccessibilityLevel(), "access", null, 0, 1, Delegation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDelegation_Operation(), theRulesPackage.getOperationType(), "operation", null, 0, 1, Delegation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDelegation_Targets(), theRulesPackage.getRole(), null, "targets", null, 0, -1, Delegation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDelegation_Pattern(), thePatternLanguagePackage.getPattern(), null, "pattern", null, 0, 1, Delegation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDelegation_Asset(), theRulesPackage.getModelFact(), null, "asset", null, 0, 1, Delegation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDelegation_Bindings(), theRulesPackage.getBinding(), null, "bindings", null, 0, -1, Delegation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //DelegationPackageImpl
