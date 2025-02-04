package org.opencds.cqf.ruler.cdshooks.discovery;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("serial")
public class PrefetchUrlList extends CopyOnWriteArrayList<String> {

    @Override
    public boolean add(String element) {
        for (String s : this) {
            if (s.equals(element)) return false;
            if (element.startsWith(s)) return false;
            if (s.equals(element)) this.remove(s);
        }
        return super.add(element);
    }

    @Override
    public boolean addAll(Collection<? extends String> toAdd) {
        if (toAdd != null) {
            for (String s : toAdd) {
                add(s);
            }
        }
        return true;
    }
}
