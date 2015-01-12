package demo;

import persistentDataStructures.PersistentHashMap;
import persistentDataStructures.PersistentVector;

/**
 *
 * @author laurikin
 */
public class History {

    private final int maxCount;
    int head;
    int current;
    int tail;
    private final PixelCursor cursor;
    private PersistentHashMap<Integer, PersistentVector<Boolean>> history;

    public History(int maxCount, PixelCursor cursor) {
        this.maxCount = maxCount;
        this.history = new PersistentHashMap<>();
        this.cursor = cursor;
        this.head = 0;
        this.tail = 0;
        this.current = 0;
        
        this.history = this.history.assoc(0, this.cursor.current());

        this.cursor.addUpdateListener(new AddToHistoryCallback(this));
    } 

    public void add(PersistentVector<Boolean> pixels) {
        current += 1;
        history = history.assoc(current, pixels);
        head = current;

        if (head - tail > maxCount) {
            history = history.dissoc(tail);
            tail++;
        }
    }

    public void undo() {
        if (tail < current) {
            current -= 1;
        }

        this.cursor.set(history.get(current));
    }

    public void redo() {
        if (head > current) {
            current++;
        }

        this.cursor.set(history.get(current));
    }

    public PixelCursor getCursor() {
        return this.cursor;
    }
    
}
