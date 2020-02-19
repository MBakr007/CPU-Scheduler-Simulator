package cpu.scheduling;

import java.util.Scanner;
import java.util.Vector;

public class Priority_Scheduling {

    public static Vector<Process> Processes = new Vector<Process>();
    public static int numberOfProcesses;

    public static void PriorityScheduling() 
    {
        Process ProcessWillbeExcuted = new Process();
        Process LeastPriority = new Process();
        int end;
        int start;
        int HighestPriorityIndex = 0;
        int leasetpriorityIndex = 0;
        boolean found = false;
        int c = numberOfProcesses - 2;
        int counter = 0;
        int excuted_index = 0;
        int multi = 1;
        double TotalwaitingTime = 0;
        double TotalTurnaroundTime = 0;
        boolean check = false;
        Vector<Process> Executed_Processes = new Vector<Process>();
        ///Entrance of the first process by the least arrival time
        //FIRST calculating the least arrival time
        ProcessWillbeExcuted = Processes.get(0);
        for (int i = 1; i < Processes.size(); i++) 
        {
            if (Processes.get(i).arrival_time < ProcessWillbeExcuted.arrival_time) 
            {
                ProcessWillbeExcuted = Processes.get(i);
                //to know the process that excuted to remove it from the vector     
                excuted_index = i;
            }
            //IF THIER ARE MANY PROCESSES HAVE THE SAME ARRIVAL TIME SO WE WILL COMPARE THIER PRIORITIES
            if (Processes.get(i).arrival_time == ProcessWillbeExcuted.arrival_time)
            {
                if (Processes.get(i).priority < ProcessWillbeExcuted.priority)
                {
                    ProcessWillbeExcuted = Processes.get(i);
                    //to know the process that excuted to remove it from the vector          
                    excuted_index = i;
                }

            }

        }

        ProcessWillbeExcuted.waiting_time = 0;
        start = ProcessWillbeExcuted.arrival_time;

        end = start + ProcessWillbeExcuted.burst_time;
        ProcessWillbeExcuted.turnaround_time = end - ProcessWillbeExcuted.arrival_time;
        Executed_Processes.add(ProcessWillbeExcuted);
        Processes.remove(excuted_index);

        while (c != 0)
        {
            found = false;
            counter = 0;
            check = false;
            ///AGING
            ///to know THE LEAST PRIORITY AND INCREASE ITS PRIORITY 
            LeastPriority = Processes.get(0);
            leasetpriorityIndex = 0;
            for (int i = 1; i < Processes.size(); i++) 
            {
                if (Processes.get(i).priority > LeastPriority.priority) 
                {
                    leasetpriorityIndex = i;
                }

            }
            if (end >= (10 * multi)) 
            {
                Processes.get(leasetpriorityIndex).priority--;
                multi++;
            }
            ///To know the process that have arrival time less than the end of the last excuted process
            for (int i = 0; i < Processes.size(); i++) 
            {
                if (Processes.get(i).arrival_time < end) 
                {
                    ProcessWillbeExcuted = Processes.get(i);
                    HighestPriorityIndex = i;
                    check = true;
                }
            }
            //If thier is a process arrived before the last excuted finish
            if (check) 
            {
                for (int i = 0; i < Processes.size(); i++)
                {

                    if ((Processes.get(i).priority < ProcessWillbeExcuted.priority)
                            && Processes.get(i).arrival_time < end) 
                    {
                        ProcessWillbeExcuted = Processes.get(i);

                        excuted_index = i;
                    } 
                    else if (Processes.get(i).priority == ProcessWillbeExcuted.priority)
                    {
                        if ((Processes.get(i).arrival_time <= ProcessWillbeExcuted.arrival_time)
                                && Processes.get(i).arrival_time < end)
                        {
                            ProcessWillbeExcuted = Processes.get(i);

                            excuted_index = i;
                        }

                    }
                }

                start = end;

                ProcessWillbeExcuted.waiting_time = start - ProcessWillbeExcuted.arrival_time;
                end = start + ProcessWillbeExcuted.burst_time;
                ProcessWillbeExcuted.turnaround_time = end - ProcessWillbeExcuted.arrival_time;
                Executed_Processes.add(ProcessWillbeExcuted);

            }
            ///IF ALL PROCESSES COMING ARE LATE(COME AFTER THE LAST EXCUTED HAS FINISHED )WILL BE TREATED AS THE FIRST
            if (!check) 
            {
                ProcessWillbeExcuted = Processes.get(0);
                for (int i = 1; i < Processes.size(); i++) 
                {

                    if (Processes.get(i).arrival_time < ProcessWillbeExcuted.arrival_time) 
                    {
                        ProcessWillbeExcuted = Processes.get(i);

                        excuted_index = i;
                    }
                    if (Processes.get(i).arrival_time == ProcessWillbeExcuted.arrival_time) 
                    {
                        if (Processes.get(i).priority < ProcessWillbeExcuted.priority) 
                        {
                            ProcessWillbeExcuted = Processes.get(i);

                            excuted_index = i;
                        }

                    }

                }

                ProcessWillbeExcuted.waiting_time = 0;
                start = ProcessWillbeExcuted.arrival_time;

                end = start + ProcessWillbeExcuted.burst_time;
                ProcessWillbeExcuted.turnaround_time = end - ProcessWillbeExcuted.arrival_time;
                Executed_Processes.add(ProcessWillbeExcuted);
            }

            Processes.remove(excuted_index);
            c--;

        }
        ///THE LAST PROCESS TO BE EXCUTED
        if (end < Processes.get(Processes.size() - 1).arrival_time) 
        {
            start = Processes.get(Processes.size() - 1).arrival_time;
        } 
        else 
        {
            start = end;
        }
        Processes.get(Processes.size() - 1).waiting_time = start - Processes.get(Processes.size() - 1).arrival_time;

        end = start + Processes.get(Processes.size() - 1).burst_time;
        Processes.get(Processes.size() - 1).turnaround_time = end - Processes.get(Processes.size() - 1).arrival_time;
        Executed_Processes.add(Processes.get(Processes.size() - 1));
//CALCULATING THE TOTAL WAITING TIME AND TURN AROUND TIME
        for (int i = 0; i < Executed_Processes.size(); i++) 
        {
            System.out.println(Executed_Processes.get(i).process_name + " ");
            System.out.println("Its Waiting Time is " + Executed_Processes.get(i).waiting_time + " ");
            System.out.println("Its Turnaround Time  is " + Executed_Processes.get(i).turnaround_time + " ");
            TotalwaitingTime += Executed_Processes.get(i).waiting_time;
            TotalTurnaroundTime += Executed_Processes.get(i).turnaround_time;
        }

        System.out.print("Average waiting Time equals ");
        System.out.println(TotalwaitingTime / (double) numberOfProcesses);
        System.out.print("Average TurnAroundTime Time equals ");
        System.out.println(TotalTurnaroundTime / (double) numberOfProcesses);

    }

    public static void getInput() 
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of processes : ");
        numberOfProcesses = input.nextInt();
        for (int i = 0; i < numberOfProcesses; i++) 
        {
            Process P = new Process();
            System.out.print("Enter Process No." + (i + 1) + " Name : ");
            P.process_name = input.next();
            System.out.print("Enter Process No." + (i + 1) + " Arrival Time : ");
            P.arrival_time = input.nextInt();

            System.out.print("Enter Process No." + (i + 1) + " Busrt Time : ");
            P.burst_time = input.nextInt();
            System.out.print("Enter Process No." + (i + 1) + " Priority : ");
            P.priority = input.nextInt();
            Processes.add(P);
            System.out.println();
        }
    }
}
