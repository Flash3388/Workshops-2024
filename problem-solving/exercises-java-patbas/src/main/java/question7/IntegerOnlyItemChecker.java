package question7;

public class IntegerOnlyItemChecker implements ItemChecker {

    @Override
    public boolean shouldShow(Object o) {
        // returns true if o is an instance of Integer class, i.e. an int
        return o instanceof Integer;
    }
}
