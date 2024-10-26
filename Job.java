public class Job implements Comparable<Job> {
    int id;
    int processingTime;
    int priorityLevel;
    int arrivalTime;

    // Constructor for a general job
    public Job(int id, int processingTime, int priorityLevel, int arrivalTime) {
        this.id = id;
        this.processingTime = processingTime;
        this.priorityLevel = priorityLevel;
        this.arrivalTime = arrivalTime;
    }

    // We'll use this for comparisons in our priority queue (SPT or by priority)
    @Override
    public int compareTo(Job other) {
        if (this.priorityLevel != other.priorityLevel) {
            return this.priorityLevel - other.priorityLevel; // Higher priority first
        }
        return this.processingTime - other.processingTime; // SPT for tasks 1 and 3
    }

    // For debugging purposes
    @Override
    public String toString() {
        return "Job ID: " + id + " Processing Time: " + processingTime + " Priority: " + priorityLevel + " Arrival Time: " + arrivalTime;
    }
}
