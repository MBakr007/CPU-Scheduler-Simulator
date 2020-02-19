package cpu.scheduling;

import java.util.Scanner;

public class CPUScheduling {

    public static Scanner input = new Scanner (System.in);
    public static void main(String[] args) throws CloneNotSupportedException
    {
        System.out.println("CPU Scheduling.");
        System.out.println("1- Non-Preemptive Shortest Job First (SJF) Scheduling.");
        System.out.println("2- Shortest Remaining Time First (SRTF) Scheduling.");
        System.out.println("3- Non-preemptive  Priority Scheduling.");
        System.out.println("4- AG Sheduling.");
        System.out.println();
        System.out.print("Enter Yout Choice : ");
        int choice  = input.nextInt();
        if(choice == 1)
        {
            SJF_Scheduling sjf = new SJF_Scheduling();
            sjf.getInput();
            sjf.shortestJobFirst();
        }
        else if(choice == 2)
        {
            SRTF_Scheduling SRTF = new SRTF_Scheduling();
            SRTF.getInput();
            SRTF.SRTF();
            SRTF.getData();
        }
        else if(choice == 3)
        {
            Priority_Scheduling.getInput();
            Priority_Scheduling.PriorityScheduling();
        }
        else if(choice == 4)
        {
            AG_Scheduling.get_processes();
            AG_Scheduling.AG_scheduler();
            AG_Scheduling.set_turnaround();
            AG_Scheduling.print();
        }
    }
}
