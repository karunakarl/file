package com.tesco.colleague.integration.test.util;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import junit.framework.AssertionFailedError;

/**
 * Extend in order to test a class's functional compliance with the
 * <code>equals</code> and <code>hashCode</code> contract.
 * <p>
 * Override {@link #createInstance() createInstance} and
 * {@link #createNotEqualInstance() createNotEqualInstance} methods to provide
 * me with objects to test against. Both methods should return objects that are
 * of the same class.
 * <p>
 * Override {@link #createDifferentClassInstance() createDifferentClassInstance}
 * method to test equality against different class instance
 * <p>
 * <b>WARNING</b>: Extend only if your class overrides <code>equals</code> to
 * test for equivalence.
 * 
 * @see java.lang.Object#equals(Object)
 * @see java.lang.Object#hashCode()
 *
 */
public abstract class AbstractEqualsHashCodeTestCase {

    private Object objectWithEqualState1;

    private Object objectWithEqualState2;

    private Object objectWithEqualState3;

    private Object objectWithUnequalState;

    private Object objectWithDiffClass;

    private static final int NUM_ITERATIONS = 20;

    /**
     * Creates and returns an instance of the class under test.
     * 
     * @return a new instance of the class under test; each object returned from
     *         this method should compare equal to each other.
     * @throws Exception
     */
    protected abstract Object createInstance() throws Exception;

    /**
     * Creates and returns an instance of the class under test.
     * 
     * @return a new instance of the class under test; each object returned from
     *         this method should compare equal to each other, but not to the
     *         objects returned from {@link #createInstance() createInstance}.
     * @throws Exception
     */
    protected abstract Object createNotEqualInstance() throws Exception;

    /**
     * Creates and returns an instance of different class.
     * 
     * @return a new instance of different class; each object returned from this
     *         method should compare equal to object of class under test.
     * @throws Exception
     */
    protected abstract Object createDifferentClassInstance() throws Exception;

    /**
     * Sets up the test fixture.
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
	objectWithEqualState1 = createInstance();
	objectWithEqualState2 = createInstance();
	objectWithEqualState3 = createInstance();
	objectWithUnequalState = createNotEqualInstance();
	objectWithDiffClass = createDifferentClassInstance();
	// We want these assertions to yield errors, not failures.
	try {
	    assertNotNull("createInstance() returned null", objectWithEqualState1);
	    assertNotNull("2nd createInstance() returned null", objectWithEqualState2);
	    assertNotNull("3rd createInstance() returned null", objectWithEqualState3);
	    assertNotNull("createNotEqualInstance() returned null", objectWithUnequalState);
	    assertNotSame(objectWithEqualState1, objectWithEqualState2);
	    assertNotSame(objectWithEqualState1, objectWithEqualState3);
	    assertNotSame(objectWithEqualState1, objectWithUnequalState);
	    assertNotSame(objectWithEqualState2, objectWithEqualState3);
	    assertNotSame(objectWithEqualState2, objectWithUnequalState);
	    assertNotSame(objectWithEqualState3, objectWithUnequalState);
	    assertEquals("1st and 2nd equal instances of different classes", objectWithEqualState1.getClass(),
		    objectWithEqualState2.getClass());
	    assertEquals("1st and 3rd equal instances of different classes", objectWithEqualState1.getClass(),
		    objectWithEqualState3.getClass());
	    assertEquals("1st equal instance and not-equal instance of different classes",
		    objectWithEqualState1.getClass(), objectWithUnequalState.getClass());
	} catch (final AssertionFailedError ex) {
	    throw new IllegalArgumentException(ex.getMessage());
	}
    }

    /**
     * Tests whether <code>equals</code> holds up against a new
     * <code>Object</code> (should always be <code>false</code>).
     */
    @Test
    public final void testEqualsAgainstNewObject() {
	final Object o = new Object();
	assertNotEquals(o, objectWithEqualState1);
	assertNotEquals(o, objectWithEqualState2);
	assertNotEquals(o, objectWithEqualState3);
	assertNotEquals(o, objectWithUnequalState);
    }

    /**
     * Asserts that two objects are not equal. Throws an
     * <tt>AssertionFailedError</tt> if they are equal.
     */
    public void assertNotEquals(final Object expected, final Object actual) {
	if (expected == null && actual == null || expected != null && expected.equals(actual)) {
	    fail("expected not equals to: <" + expected + ">");
	}
    }

    /**
     * Tests whether <code>equals</code> holds up against <code>null</code>.
     */
    @Test
    public final void testEqualsAgainstNull() {
	assertThat("null vs. 1st", null, not(equalTo(objectWithEqualState1)));
	assertThat("null vs. 2nd", null, not(equalTo(objectWithEqualState2)));
	assertThat("null vs. 3rd", null, not(equalTo(objectWithEqualState3)));
	assertThat("null vs. not-equal", null, not(equalTo(objectWithUnequalState)));
    }

    /**
     * Tests whether <code>equals</code> holds up against objects that should
     * not compare equal.
     */
    @Test
    public final void testEqualsAgainstUnequalObjects() {
	assertThat("1st vs. not-equal", objectWithEqualState1, not(equalTo(objectWithUnequalState)));
	assertThat("2nd vs. not-equal", objectWithEqualState2, not(equalTo(objectWithUnequalState)));
	assertThat("3rd vs. not-equal", objectWithEqualState3, not(equalTo(objectWithUnequalState)));
	assertThat("not-equal vs. 1st", objectWithUnequalState, not(equalTo(objectWithEqualState1)));
	assertThat("not-equal vs. 2nd", objectWithUnequalState, not(equalTo(objectWithEqualState2)));
	assertThat("not-equal vs. 3rd", objectWithUnequalState, not(equalTo(objectWithEqualState3)));
    }

    /**
     * Tests whether <code>equals</code> is <em>consistent</em>.
     */
    @Test
    public final void testEqualsIsConsistentAcrossInvocations() {
	for (int i = 0; i < NUM_ITERATIONS; ++i) {
	    testEqualsAgainstNewObject();
	    testEqualsAgainstNull();
	    testEqualsAgainstUnequalObjects();
	    testEqualsIsReflexive();
	    testEqualsIsSymmetricAndTransitive();
	}
    }

    /**
     * Tests whether <code>equals</code> is <em>reflexive</em>.
     */
    @Test
    public final void testEqualsIsReflexive() {
	assertEquals("1st equal instance", objectWithEqualState1, objectWithEqualState1);
	assertEquals("2nd equal instance", objectWithEqualState2, objectWithEqualState2);
	assertEquals("3rd equal instance", objectWithEqualState3, objectWithEqualState3);
	assertEquals("not-equal instance", objectWithUnequalState, objectWithUnequalState);
    }

    /**
     * Tests whether <code>equals</code> is <em>symmetric</em> and
     * <em>transitive</em>.
     */
    @Test
    public final void testEqualsIsSymmetricAndTransitive() {
	assertEquals("1st vs. 2nd", objectWithEqualState1, objectWithEqualState2);
	assertEquals("2nd vs. 1st", objectWithEqualState2, objectWithEqualState1);
	assertEquals("1st vs. 3rd", objectWithEqualState1, objectWithEqualState3);
	assertEquals("3rd vs. 1st", objectWithEqualState3, objectWithEqualState1);
	assertEquals("2nd vs. 3rd", objectWithEqualState2, objectWithEqualState3);
	assertEquals("3rd vs. 2nd", objectWithEqualState3, objectWithEqualState2);
    }

    /**
     * Tests the <code>hashCode</code> contract.
     */
    @Test
    public final void testHashCodeContract() {
	assertEquals("1st vs. 2nd", objectWithEqualState1.hashCode(), objectWithEqualState2.hashCode());
	assertEquals("1st vs. 3rd", objectWithEqualState1.hashCode(), objectWithEqualState3.hashCode());
	assertEquals("2nd vs. 3rd", objectWithEqualState2.hashCode(), objectWithEqualState3.hashCode());
    }

    /**
     * Tests the consistency of <code>hashCode</code>.
     */
    @Test
    public final void testHashCodeIsConsistentAcrossInvocations() {
	final int eq1Hash = objectWithEqualState1.hashCode();
	final int eq2Hash = objectWithEqualState2.hashCode();
	final int eq3Hash = objectWithEqualState3.hashCode();
	final int neqHash = objectWithUnequalState.hashCode();
	for (int i = 0; i < NUM_ITERATIONS; ++i) {
	    assertEquals("1st equal instance", eq1Hash, objectWithEqualState1.hashCode());
	    assertEquals("2nd equal instance", eq2Hash, objectWithEqualState2.hashCode());
	    assertEquals("3rd equal instance", eq3Hash, objectWithEqualState3.hashCode());
	    assertEquals("not-equal instance", neqHash, objectWithUnequalState.hashCode());
	}
    }

    /**
     * Tests equals method with two different class types
     */
    @Test
    public final void testEqualsWithDifferentClasses() {
	assertFalse("1st and 4th instances should be of different classes",
		objectWithEqualState1.equals(objectWithDiffClass));
    }
}
