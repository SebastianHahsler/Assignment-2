import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Task 1: Shortest Processing Time
        System.out.println("Task 1: Job Id, Processing time");
        runTask1("task1-input.txt");

        // Task 2: Priority Processing Time
        System.out.println("Task 2: Job Id, Processing time, Priority class");
        runTask2("task2-input.txt");

        // Task 3: Dynamic Processing Time
        System.out.println("Task 3: Job Id, Processing time, Arrival Time");
        runTask3("task3-input.txt");
    }

    // Task 1: Scheduling jobs by processing time
    public static void runTask1(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new File(fileName));
        Priority_Queue<Job> pq = new Priority_Queue<>(); // Priority queue for processing time

        while (in.hasNextLine()) {
            String[] tokens = in.nextLine().split(" ");
            int jobId = Integer.parseInt(tokens[0]);
            int processingTime = Integer.parseInt(tokens[1]);
            pq.insert(new Job(jobId, processingTime, 0, -1)); // No priority or arrival time for Task 1
        }
        in.close();

        executeAndPrint(pq);
    }

    // Task 2: Scheduling jobs by priority and processing time
    public static void runTask2(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new File(fileName));
        Priority_Queue<Job> pq = new Priority_Queue<>();

        while (in.hasNextLine()) {
            String[] tokens = in.nextLine().split(" ");
            int jobId = Integer.parseInt(tokens[0]);
            int processingTime = Integer.parseInt(tokens[1]);
            int priority = Integer.parseInt(tokens[2]);
            pq.insert(new Job(jobId, processingTime, priority, -1));
        }
        in.close();

        executeAndPrint(pq);
    }

    // Task 3: Scheduling jobs with dynamic arrivals
    public static void runTask3(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new File(fileName));

        // Step 1: Read all jobs and store them in an array
        int jobCount = 0;
        while (in.hasNextLine()) {
            in.nextLine();
            jobCount++;
        }
        in.close();

        Job[] jobs = new Job[jobCount];
        in = new Scanner(new File(fileName));
        int i = 0;

        while (in.hasNextLine()) {
            String[] tokens = in.nextLine().split(" ");
            int jobId = Integer.parseInt(tokens[0].trim());
            int processingTime = Integer.parseInt(tokens[1].trim());
            int arrivalTime = Integer.parseInt(tokens[2].trim());
            jobs[i++] = new Job(jobId, processingTime, 0, arrivalTime); // Priority not used here
        }
        in.close();

        // Step 2: Sort jobs by arrival time
        Arrays.sort(jobs, Comparator.comparingInt(job -> job.arrivalTime));

        // Step 3: Initialize priority queue and begin dynamic job processing
        Priority_Queue<Job> pq = new Priority_Queue<>();
        int currentTime = 0;
        int index = 0;
        int totalProcessingTime = 0;
        int sumCompletionTime = 0;
        int processedJobs = 0;

        System.out.print("Execution order: [");

        // Step 4: Process jobs as they arrive and execute them dynamically
        while (index < jobs.length || !pq.isEmpty()) {
            // Add all jobs that have arrived by the current time
            while (index < jobs.length && jobs[index].arrivalTime <= currentTime) {
                pq.insert(jobs[index]);
                index++;
            }

            if (!pq.isEmpty()) {
                // Process the job with the shortest processing time (min-heap property)
                Job nextJob = pq.delMin();
                currentTime = Math.max(currentTime, nextJob.arrivalTime);  // Ensure current time meets job arrival
                currentTime += nextJob.processingTime;  // Advance time by the job's processing time
                totalProcessingTime += nextJob.processingTime;
                sumCompletionTime += currentTime;
                processedJobs++;

                // Print job id in execution order
                System.out.print(nextJob.id + (pq.isEmpty() && index >= jobs.length ? "" : ", "));
            } else if (index < jobs.length) {
                // If no jobs are ready, advance time to the next job's arrival time
                currentTime = jobs[index].arrivalTime;
            }
        }

        System.out.println("]");
        System.out.println("Average completion time: " + (double) sumCompletionTime / processedJobs);
    }





    // Utility method to execute jobs and print execution order and average completion time
    // Only handles task 1 and 2
    public static void executeAndPrint(Priority_Queue<Job> pq) {
        int totalProcessing = 0, sum = 0, size = pq.size();
        System.out.print("Execution order: [");
        while (!pq.isEmpty()) {
            Job nextJob = pq.delMin();
            totalProcessing += nextJob.processingTime;
            sum += totalProcessing;
            System.out.print(nextJob.id + (pq.isEmpty() ? "" : ", "));
        }
        System.out.println("]");
        System.out.println("Average completion time: " + (double) sum / size);
        System.out.println(" ");
    }
}
