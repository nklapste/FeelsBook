package ca.klapstein.nklapste_feelsbook;

/**
 * Enum noting all the valid names of feelings that could exist in the Feel class.
 * <p>
 * The names of the feelings are defined/inspired by:
 * <p>
 * http://changingminds.org/explanations/emotions/basic%20emotions.htm from
 * Shaver, P., Schwartz, J., Kirson, D., & O'Connor, C. (2001).
 * Emotional Knowledge: Further Exploration of a Prototype Approach.
 * In G. Parrott (Eds.), Emotions in Social Psychology: Essential Readings (pp. 26-56).
 * Philadelphia, PA: Psychology Press.
 * <p>
 * Notes on implementation:
 * <p>
 * Instead of using subclassing I define all supported feelings by this enumerator.
 * Thus, all string values that are permitted to be a ``feeling`` in a {@code Feel} are controlled.
 * <p>
 * This also allows me to generically create other resources within FeelsBook such as the
 * stats page, tallies of all feelings, and add feeling buttons. This could be done with
 * subclassing, but, it could get quickly messy onto how to define the domain of all valid
 * feelings.
 * <p>
 * If I add another Feeling enumeration value the rest of FeelsBook should properly accommodate
 * it.
 * <p>
 * The only issue with this implementation is that adding additional ``feeling`` specific data
 * other than the string name value of the ``feeling`` is difficult. However, since this
 * app doesn't require such components I didn't figure this extensibility was a priority.
 */
public enum Feeling {
    Anger,
    Fear,
    Joy,
    Love,
    Sadness,
    Surprise,
}
