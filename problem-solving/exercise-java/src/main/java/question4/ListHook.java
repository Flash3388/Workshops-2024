package question4;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked", "UseBulkOperation"})
public class ListHook extends ArrayList {

    final List originalList;
    private final ItemChecker itemChecker;

    public ListHook(List originalList, ItemChecker itemChecker) {
        addOriginalList();

        this.originalList = originalList;
        this.itemChecker = itemChecker;
    }

    @Override
    public boolean add(Object o) {
        originalList.add(o);

        if (itemChecker.shouldShow(o)) {
            super.add(o);
        }

        return true;
    }

    private void addOriginalList() {
        for (Object o : originalList) {
            super.add(o);
        }
    }
}
