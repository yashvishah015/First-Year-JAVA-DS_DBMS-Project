package Stadium;

import java.util.Stack;

public class HistoryStack {
    private Stack<String> historyStack;

    public HistoryStack() {
        historyStack = new Stack<>();
    }

    public synchronized void addHistory(String entry) {
        historyStack.push(entry);
    }

    public synchronized String getLastHistory() {
        return historyStack.isEmpty() ? "No history available" : historyStack.peek();
    }

    public synchronized void viewAllHistory() {
        if (historyStack.isEmpty()) {
            System.out.println("No history available");
        } else {
            for (String entry : historyStack) {
                System.out.println(entry);
            }
        }
    }
}
