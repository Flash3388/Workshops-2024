package question6;

import java.io.IOException;

@SuppressWarnings("ALL")
public class Question6 {

    // This program uses some Java techniques (reflection) to
    // do what's called HOOKING. This is basically making our code
    // run from within someone else's code in a malicious way.
    // It basically replaces the field list in SomeObject with a different
    // instance which is ListHook. So any changes made to list, are actually
    // done to ListHook.
    // But there ia a problem with ListHook!
    //
    // Once ListHook is installed, all non-integer values in the list should
    // be hidden. That means that if one prints the list, only integers should
    // be shown.
    // ListHook is later uninstalled. This should return the original
    // list and list will now function normally and show both integer
    // and non-integer numbers.

    // In order, the prints should show:
    // [5.3, 2, 4.2, 1]
    // [2, 1]
    // [2, 1, 55]
    // [5.3, 2, 4.2, 1, 55, 12.1]

    public static void main(String[] args) throws IOException {
        SomeObject object = new SomeObject();

        // first print, before the hook is installed.
        object.printList();

        // installing the hook. DONT TOUCH
        ItemChecker checker = new IntegerOnlyItemChecker();
        ListHook hook = HookInstaller.installHookOnField(object, "list", checker);

        // now the hook is installed, and only integer values
        // are shown.
        object.printList();
        // this adds some numbers to the list.
        object.augmentList1();
        // this prints it again.
        object.printList();

        // Uninstalling the hook. DONT TOUCH
        HookInstaller.uninstallHook(object, "list", hook);

        // print the list again. Because the hook is no longer
        // installed, it should print all the values in the list,
        // not only integers.
        object.printList();
    }
}
